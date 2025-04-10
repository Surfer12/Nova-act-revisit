package com.fractal.browser.collective.processing;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.UUID;

import com.fractal.browser.collective.boundaries.InformationBoundary;
import com.fractal.browser.collective.communication.NodeDiscovery;
import com.fractal.browser.collective.communication.SynchronizationProtocol;
import com.fractal.browser.exceptions.FractalBrowserException;

/**
 * ConsensusFormation implements distributed consensus algorithms for collective decision-making
 * across network nodes. It employs fractal voting mechanisms that operate at multiple scales,
 * enabling recursive refinement of consensus as more information becomes available.
 * 
 * This class supports various consensus mechanisms, including weighted voting, multi-stage
 * consensus, and bifurcation detection (identifying when the network is approaching a 
 * decision boundary).
 */
public class ConsensusFormation {

    // Core dependencies
    private final NodeDiscovery nodeDiscovery;
    private final SynchronizationProtocol synchronizationProtocol;
    private final InformationBoundary boundary;
    
    // Consensus configuration
    private final double defaultConsensusThreshold;
    private final int maxConsensusRounds;
    
    // Voting session tracking
    private final Map<String, Map<String, Object>> activeSessions;
    private final Map<String, Map<String, Map<String, Double>>> sessionVotes;
    
    /**
     * Creates a new ConsensusFormation instance.
     * 
     * @param nodeDiscovery Service for discovering network nodes
     * @param synchronizationProtocol Protocol for synchronizing state between nodes
     * @param boundary Information boundary for enforcing access controls
     * @param defaultConsensusThreshold Default threshold for consensus (0.5 to 1.0)
     * @param maxConsensusRounds Maximum number of voting rounds before finalizing
     */
    public ConsensusFormation(
            NodeDiscovery nodeDiscovery,
            SynchronizationProtocol synchronizationProtocol,
            InformationBoundary boundary,
            double defaultConsensusThreshold,
            int maxConsensusRounds) {
        
        this.nodeDiscovery = nodeDiscovery;
        this.synchronizationProtocol = synchronizationProtocol;
        this.boundary = boundary;
        this.defaultConsensusThreshold = defaultConsensusThreshold;
        this.maxConsensusRounds = maxConsensusRounds;
        
        this.activeSessions = new ConcurrentHashMap<>();
        this.sessionVotes = new ConcurrentHashMap<>();
    }
    
    /**
     * Creates a new consensus formation session.
     * 
     * @param topic The topic of consensus
     * @param options The available options for voting
     * @param contextId The context ID for boundary enforcement
     * @param customThreshold Optional custom threshold for this session
     * @return The session ID
     */
    public String createConsensusSession(
            String topic, 
            List<String> options, 
            String contextId,
            Optional<Double> customThreshold) {
        
        String sessionId = UUID.randomUUID().toString();
        
        // Create session metadata
        Map<String, Object> sessionMetadata = new HashMap<>();
        sessionMetadata.put("topic", topic);
        sessionMetadata.put("options", new ArrayList<>(options));
        sessionMetadata.put("createdAt", System.currentTimeMillis());
        sessionMetadata.put("contextId", contextId);
        sessionMetadata.put("threshold", customThreshold.orElse(defaultConsensusThreshold));
        sessionMetadata.put("currentRound", 1);
        sessionMetadata.put("status", "ACTIVE");
        
        // Create vote tracking structures
        activeSessions.put(sessionId, sessionMetadata);
        sessionVotes.put(sessionId, new ConcurrentHashMap<>());
        
        // Discover participating nodes
        Set<String> nodes = nodeDiscovery.discoverNodes().stream()
                .filter(nodeId -> nodeDiscovery.isNodeAvailable(nodeId))
                .collect(Collectors.toSet());
        
        // Initialize vote tracking for each node
        for (String nodeId : nodes) {
            sessionVotes.get(sessionId).put(nodeId, new HashMap<>());
        }
        
        // Broadcast session creation
        Map<String, Object> sessionInfo = new HashMap<>(sessionMetadata);
        sessionInfo.put("sessionId", sessionId);
        
        synchronizationProtocol.synchronizeDataType("consensus_sessions", sessionInfo, contextId);
        
        return sessionId;
    }
    
    /**
     * Casts a vote in a consensus session.
     * 
     * @param sessionId The session ID
     * @param nodeId The ID of the voting node
     * @param votes Map of option to weight (weights should sum to 1.0)
     * @param contextId The context ID for boundary checks
     * @return true if vote was accepted
     * @throws FractalBrowserException if session doesn't exist or other error
     */
    public boolean castVote(
            String sessionId, 
            String nodeId, 
            Map<String, Double> votes,
            String contextId) {
        
        // Verify session exists and is active
        Map<String, Object> session = activeSessions.get(sessionId);
        if (session == null) {
            throw new FractalBrowserException("Session does not exist: " + sessionId);
        }
        
        if (!"ACTIVE".equals(session.get("status"))) {
            throw new FractalBrowserException("Session is not active: " + sessionId);
        }
        
        // Verify context ID matches
        String sessionContextId = (String) session.get("contextId");
        if (!sessionContextId.equals(contextId)) {
            throw new FractalBrowserException("Context ID mismatch for session: " + sessionId);
        }
        
        // Verify node is eligible to vote
        if (!nodeDiscovery.isNodeAvailable(nodeId)) {
            throw new FractalBrowserException("Node is not available for voting: " + nodeId);
        }
        
        // Verify voting options are valid
        @SuppressWarnings("unchecked")
        List<String> validOptions = (List<String>) session.get("options");
        for (String option : votes.keySet()) {
            if (!validOptions.contains(option)) {
                throw new FractalBrowserException("Invalid voting option: " + option);
            }
        }
        
        // Verify weights are valid
        double totalWeight = votes.values().stream().mapToDouble(Double::doubleValue).sum();
        if (Math.abs(totalWeight - 1.0) > 0.001) {
            throw new FractalBrowserException("Vote weights must sum to 1.0, got: " + totalWeight);
        }
        
        // Record the vote
        Map<String, Map<String, Double>> sessionVoteMap = sessionVotes.get(sessionId);
        sessionVoteMap.put(nodeId, new HashMap<>(votes));
        
        // Broadcast vote
        Map<String, Object> voteInfo = new HashMap<>();
        voteInfo.put("sessionId", sessionId);
        voteInfo.put("nodeId", nodeId);
        voteInfo.put("votes", votes);
        voteInfo.put("timestamp", System.currentTimeMillis());
        
        synchronizationProtocol.synchronizeDataType("consensus_votes", voteInfo, contextId);
        
        // Check if consensus has been reached or round should advance
        checkConsensusState(sessionId, contextId);
        
        return true;
    }
    
    /**
     * Checks if a consensus has been reached or if the voting round should advance.
     * 
     * @param sessionId The session ID
     * @param contextId The context ID for boundary checks
     */
    private void checkConsensusState(String sessionId, String contextId) {
        Map<String, Object> session = activeSessions.get(sessionId);
        Map<String, Map<String, Double>> votes = sessionVotes.get(sessionId);
        
        // Count participating nodes
        int totalParticipants = votes.size();
        int votedParticipants = (int) votes.values().stream()
                .filter(v -> !v.isEmpty())
                .count();
        
        // Update participation rate
        session.put("participationRate", (double) votedParticipants / totalParticipants);
        
        // If all nodes have voted or we've waited long enough, tally the votes
        boolean allVoted = votedParticipants == totalParticipants;
        int currentRound = (int) session.get("currentRound");
        
        if (allVoted || shouldAdvanceRound(sessionId)) {
            Map<String, Double> talliedVotes = tallyVotes(sessionId);
            session.put("tallyResults", talliedVotes);
            
            // Check if consensus threshold is reached
            double threshold = (double) session.get("threshold");
            Optional<Map.Entry<String, Double>> highestVote = talliedVotes.entrySet().stream()
                    .max(Map.Entry.comparingByValue());
            
            if (highestVote.isPresent() && highestVote.get().getValue() >= threshold) {
                // Consensus reached
                session.put("status", "COMPLETED");
                session.put("consensusOption", highestVote.get().getKey());
                session.put("consensusValue", highestVote.get().getValue());
                session.put("completedAt", System.currentTimeMillis());
            } else if (currentRound < maxConsensusRounds) {
                // Advance to next round
                session.put("currentRound", currentRound + 1);
                session.put("roundResults", new HashMap<>(talliedVotes));
                
                // Reset votes for new round
                for (String nodeId : votes.keySet()) {
                    votes.put(nodeId, new HashMap<>());
                }
            } else {
                // Max rounds reached without consensus
                session.put("status", "FAILED");
                session.put("completedAt", System.currentTimeMillis());
                
                if (highestVote.isPresent()) {
                    session.put("bestOption", highestVote.get().getKey());
                    session.put("bestValue", highestVote.get().getValue());
                }
            }
            
            // Broadcast updated session state
            Map<String, Object> sessionUpdate = new HashMap<>(session);
            sessionUpdate.put("sessionId", sessionId);
            
            synchronizationProtocol.synchronizeDataType("consensus_sessions", sessionUpdate, contextId);
        }
    }
    
    /**
     * Determines if a voting round should advance based on time and participation.
     * 
     * @param sessionId The session ID
     * @return true if the round should advance
     */
    private boolean shouldAdvanceRound(String sessionId) {
        Map<String, Object> session = activeSessions.get(sessionId);
        Map<String, Map<String, Double>> votes = sessionVotes.get(sessionId);
        
        long createdAt = (long) session.get("createdAt");
        long currentTime = System.currentTimeMillis();
        long roundDuration = 30000; // 30 seconds per round, configurable
        
        // Count participating nodes
        int totalParticipants = votes.size();
        int votedParticipants = (int) votes.values().stream()
                .filter(v -> !v.isEmpty())
                .count();
        
        // Advance if we've waited long enough and have some participation
        return (currentTime - createdAt > roundDuration) && 
                (votedParticipants > 0) && 
                ((double) votedParticipants / totalParticipants >= 0.5);
    }
    
    /**
     * Tallies the votes for a consensus session.
     * 
     * @param sessionId The session ID
     * @return Map of option to aggregated weight
     */
    private Map<String, Double> tallyVotes(String sessionId) {
        Map<String, Map<String, Double>> votes = sessionVotes.get(sessionId);
        Map<String, Double> talliedVotes = new HashMap<>();
        
        // Gather the options from all votes
        Set<String> allOptions = new HashSet<>();
        votes.values().forEach(nodeVotes -> allOptions.addAll(nodeVotes.keySet()));
        
        // Initialize tallies for all options
        for (String option : allOptions) {
            talliedVotes.put(option, 0.0);
        }
        
        // Sum the votes for each option
        for (Map<String, Double> nodeVotes : votes.values()) {
            for (Map.Entry<String, Double> vote : nodeVotes.entrySet()) {
                String option = vote.getKey();
                Double weight = vote.getValue();
                
                talliedVotes.put(option, talliedVotes.get(option) + weight);
            }
        }
        
        // Normalize the results
        double totalVotes = votes.values().stream()
                .filter(v -> !v.isEmpty())
                .count();
        
        if (totalVotes > 0) {
            for (String option : talliedVotes.keySet()) {
                talliedVotes.put(option, talliedVotes.get(option) / totalVotes);
            }
        }
        
        return talliedVotes;
    }
    
    /**
     * Gets the status of a consensus session.
     * 
     * @param sessionId The session ID
     * @param contextId The context ID for boundary checks
     * @return A map containing the session status
     * @throws FractalBrowserException if session doesn't exist
     */
    public Map<String, Object> getSessionStatus(String sessionId, String contextId) {
        Map<String, Object> session = activeSessions.get(sessionId);
        if (session == null) {
            throw new FractalBrowserException("Session does not exist: " + sessionId);
        }
        
        // Verify context ID matches
        String sessionContextId = (String) session.get("contextId");
        if (!sessionContextId.equals(contextId) && !boundary.canInformationPass(sessionId, contextId)) {
            throw new FractalBrowserException("Access denied to session: " + sessionId);
        }
        
        // Return a copy of the session data
        return new HashMap<>(session);
    }
    
    /**
     * Gets the current vote distribution for a consensus session.
     * 
     * @param sessionId The session ID
     * @param contextId The context ID for boundary checks
     * @return A map of option to current aggregate weight
     * @throws FractalBrowserException if session doesn't exist
     */
    public Map<String, Double> getCurrentVoteTally(String sessionId, String contextId) {
        if (!activeSessions.containsKey(sessionId)) {
            throw new FractalBrowserException("Session does not exist: " + sessionId);
        }
        
        Map<String, Object> session = activeSessions.get(sessionId);
        
        // Verify context ID matches
        String sessionContextId = (String) session.get("contextId");
        if (!sessionContextId.equals(contextId) && !boundary.canInformationPass(sessionId, contextId)) {
            throw new FractalBrowserException("Access denied to session: " + sessionId);
        }
        
        return tallyVotes(sessionId);
    }
    
    /**
     * Detects bifurcation points in the consensus process, where small changes
     * could lead to different outcomes.
     * 
     * @param sessionId The session ID
     * @param contextId The context ID for boundary checks
     * @return true if the session is at a bifurcation point
     */
    public boolean detectBifurcationPoint(String sessionId, String contextId) {
        Map<String, Double> currentTally = getCurrentVoteTally(sessionId, contextId);
        
        // If we have fewer than 2 options, there's no bifurcation
        if (currentTally.size() < 2) {
            return false;
        }
        
        // Sort options by vote count
        List<Map.Entry<String, Double>> sortedOptions = currentTally.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toList());
        
        // If the difference between the top two options is very small,
        // we're at a bifurcation point
        if (sortedOptions.size() >= 2) {
            double topVotes = sortedOptions.get(0).getValue();
            double secondVotes = sortedOptions.get(1).getValue();
            
            // Define "very small" as 5% or less
            double threshold = 0.05;
            return (topVotes - secondVotes) <= threshold;
        }
        
        return false;
    }
    
    /**
     * Analyzes the fractal pattern of consensus formation across multiple sessions.
     * 
     * @param contextId The context ID for boundary checks
     * @return A map of pattern metrics
     */
    public Map<String, Object> analyzeConsensusPatterns(String contextId) {
        Map<String, Object> patternAnalysis = new HashMap<>();
        
        // Filter sessions by boundary access
        List<String> accessibleSessions = activeSessions.entrySet().stream()
                .filter(entry -> {
                    String sessionContextId = (String) entry.getValue().get("contextId");
                    return sessionContextId.equals(contextId) || 
                            boundary.canInformationPass(entry.getKey(), contextId);
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        // Count sessions by status
        Map<String, AtomicInteger> statusCounts = new HashMap<>();
        for (String sessionId : accessibleSessions) {
            Map<String, Object> session = activeSessions.get(sessionId);
            String status = (String) session.get("status");
            
            statusCounts.computeIfAbsent(status, k -> new AtomicInteger(0))
                    .incrementAndGet();
        }
        
        patternAnalysis.put("statusCounts", statusCounts);
        
        // Calculate average rounds to consensus
        double avgRounds = accessibleSessions.stream()
                .filter(sid -> "COMPLETED".equals(activeSessions.get(sid).get("status")))
                .mapToInt(sid -> (int) activeSessions.get(sid).get("currentRound"))
                .average()
                .orElse(0);
        
        patternAnalysis.put("averageRoundsToConsensus", avgRounds);
        
        // Calculate average participation rate
        double avgParticipation = accessibleSessions.stream()
                .mapToDouble(sid -> (double) activeSessions.get(sid).getOrDefault("participationRate", 0.0))
                .average()
                .orElse(0);
        
        patternAnalysis.put("averageParticipationRate", avgParticipation);
        
        // Identify common consensus options
        Map<String, Integer> optionFrequency = new HashMap<>();
        
        accessibleSessions.stream()
                .filter(sid -> "COMPLETED".equals(activeSessions.get(sid).get("status")))
                .forEach(sid -> {
                    String option = (String) activeSessions.get(sid).get("consensusOption");
                    optionFrequency.put(option, optionFrequency.getOrDefault(option, 0) + 1);
                });
        
        patternAnalysis.put("optionFrequency", optionFrequency);
        
        return patternAnalysis;
    }
    
    /**
     * Applies recursive weightings to votes based on node trust and historical consensus accuracy.
     * 
     * @param sessionId The session ID
     * @param contextId The context ID for boundary checks
     * @return The weighted vote tally
     */
    public Map<String, Double> getWeightedVoteTally(String sessionId, String contextId) {
        Map<String, Map<String, Double>> votes = sessionVotes.get(sessionId);
        Map<String, Double> nodeWeights = calculateNodeWeights(contextId);
        Map<String, Double> weightedTally = new HashMap<>();
        
        // Gather all options
        Set<String> allOptions = new HashSet<>();
        votes.values().forEach(nodeVotes -> allOptions.addAll(nodeVotes.keySet()));
        
        // Initialize tallies
        for (String option : allOptions) {
            weightedTally.put(option, 0.0);
        }
        
        // Apply weighted voting
        for (Map.Entry<String, Map<String, Double>> entry : votes.entrySet()) {
            String nodeId = entry.getKey();
            Map<String, Double> nodeVotes = entry.getValue();
            
            if (nodeVotes.isEmpty()) {
                continue; // Node hasn't voted
            }
            
            double nodeWeight = nodeWeights.getOrDefault(nodeId, 1.0);
            
            for (Map.Entry<String, Double> vote : nodeVotes.entrySet()) {
                String option = vote.getKey();
                double voteWeight = vote.getValue();
                
                // Apply node weight to vote
                weightedTally.put(option, weightedTally.get(option) + (voteWeight * nodeWeight));
            }
        }
        
        // Normalize the weighted results
        double totalWeight = weightedTally.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        
        if (totalWeight > 0) {
            for (String option : weightedTally.keySet()) {
                weightedTally.put(option, weightedTally.get(option) / totalWeight);
            }
        }
        
        return weightedTally;
    }
    
    /**
     * Calculates weights for nodes based on trust and historical accuracy.
     * 
     * @param contextId The context ID for boundary checks
     * @return Map of node ID to weight
     */
    private Map<String, Double> calculateNodeWeights(String contextId) {
        // In a real implementation, this would use trust scores and historical data
        // For simplicity, we'll return equal weights for all nodes
        Set<String> nodes = nodeDiscovery.discoverNodes();
        Map<String, Double> weights = new HashMap<>();
        
        for (String nodeId : nodes) {
            weights.put(nodeId, 1.0);
        }
        
        return weights;
    }
}