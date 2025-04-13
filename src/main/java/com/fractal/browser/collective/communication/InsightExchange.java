package com.fractal.browser.collective.communication;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;
import java.util.Optional;
import java.time.Instant;
import java.util.function.Function;
import java.util.concurrent.CompletableFuture;
import java.util.Collections;
import java.util.Set;

import com.fractal.browser.collective.boundaries.InformationBoundary;
import com.fractal.browser.collective.boundaries.PrivacyFilter;
import com.fractal.browser.model.Pattern;

/**
 * InsightExchange implements mechanisms for exchanging insights between nodes in the
 * collective. It allows for fractal insight propagation across the network, with
 * security and privacy controls enforced at multiple scales.
 * 
 * This class manages the bidirectional flow of insights, including recursive transformations,
 * integration, and emergent pattern recognition through collective interaction.
 */
public class InsightExchange {
    
    // Maps insight IDs to their data
    private final Map<String, Insight> insightRegistry;
    
    // Maps exchange session IDs to their data
    private final Map<String, ExchangeSession> activeSessions;
    
    // Privacy filter for outgoing insights
    private final PrivacyFilter privacyFilter;
    
    // Information boundary for access control
    private final InformationBoundary informationBoundary;
    
    // Node discovery instance for locating exchange partners
    private final NodeDiscovery nodeDiscovery;
    
    private final SynchronizationProtocol synchronizationProtocol;
    
    /**
     * Represents an insight being exchanged.
     */
    public static class Insight {
        private final String insightId;
        private final String sourceNodeId;
        private final String contextId;
        private final String content;
        private final Map<String, Object> metadata;
        private final Instant timestamp;
        private final List<Pattern> associatedPatterns;
        private int propagationLevel; // How far the insight has propagated
        
        /**
         * Creates a new Insight.
         * 
         * @param sourceNodeId The source node ID
         * @param contextId The context ID
         * @param content The insight content
         * @param metadata Additional metadata about the insight
         */
        public Insight(String sourceNodeId, String contextId, String content, Map<String, Object> metadata) {
            this.insightId = UUID.randomUUID().toString();
            this.sourceNodeId = sourceNodeId;
            this.contextId = contextId;
            this.content = content;
            this.metadata = new HashMap<>(metadata);
            this.timestamp = Instant.now();
            this.associatedPatterns = new ArrayList<>();
            this.propagationLevel = 0;
        }
        
        /**
         * Creates a new Insight with specified ID (for receiving from network).
         */
        public Insight(String insightId, String sourceNodeId, String contextId, String content, 
                Map<String, Object> metadata, Instant timestamp, int propagationLevel) {
            this.insightId = insightId;
            this.sourceNodeId = sourceNodeId;
            this.contextId = contextId;
            this.content = content;
            this.metadata = new HashMap<>(metadata);
            this.timestamp = timestamp;
            this.associatedPatterns = new ArrayList<>();
            this.propagationLevel = propagationLevel;
        }
        
        // Getters
        public String getInsightId() { return insightId; }
        public String getSourceNodeId() { return sourceNodeId; }
        public String getContextId() { return contextId; }
        public String getContent() { return content; }
        public Map<String, Object> getMetadata() { return Collections.unmodifiableMap(metadata); }
        public Instant getTimestamp() { return timestamp; }
        public List<Pattern> getAssociatedPatterns() { return Collections.unmodifiableList(associatedPatterns); }
        public int getPropagationLevel() { return propagationLevel; }
        
        /**
         * Adds an associated pattern to this insight.
         * 
         * @param pattern The pattern to associate
         */
        public void addAssociatedPattern(Pattern pattern) {
            this.associatedPatterns.add(pattern);
        }
        
        /**
         * Increments the propagation level of this insight.
         */
        public void incrementPropagationLevel() {
            this.propagationLevel++;
        }
    }
    
    /**
     * Represents an active exchange session between nodes.
     */
    private static class ExchangeSession {
        private final String sessionId;
        private final String initiatorNodeId;
        private final String responderNodeId;
        private final String contextId;
        private final Instant startTime;
        private Instant lastActivity;
        private final List<String> exchangedInsightIds;
        private SessionState state;
        
        public enum SessionState {
            INITIATED,
            ACTIVE,
            COMPLETED,
            FAILED
        }
        
        public ExchangeSession(String initiatorNodeId, String responderNodeId, String contextId) {
            this.sessionId = UUID.randomUUID().toString();
            this.initiatorNodeId = initiatorNodeId;
            this.responderNodeId = responderNodeId;
            this.contextId = contextId;
            this.startTime = Instant.now();
            this.lastActivity = this.startTime;
            this.exchangedInsightIds = new ArrayList<>();
            this.state = SessionState.INITIATED;
        }
        
        public void addExchangedInsight(String insightId) {
            exchangedInsightIds.add(insightId);
            lastActivity = Instant.now();
        }
        
        public void updateState(SessionState newState) {
            this.state = newState;
            this.lastActivity = Instant.now();
        }
    }
    
    /**
     * Creates a new InsightExchange with the specified components.
     * 
     * @param nodeDiscovery The NodeDiscovery component for finding exchange partners
     * @param privacyFilter The PrivacyFilter for outgoing insights
     * @param informationBoundary The InformationBoundary for access control
     */
    public InsightExchange(NodeDiscovery nodeDiscovery, PrivacyFilter privacyFilter, 
            InformationBoundary informationBoundary, SynchronizationProtocol synchronizationProtocol) {
        this.insightRegistry = new ConcurrentHashMap<>();
        this.activeSessions = new ConcurrentHashMap<>();
        this.nodeDiscovery = nodeDiscovery;
        this.privacyFilter = privacyFilter;
        this.informationBoundary = informationBoundary;
        this.synchronizationProtocol = synchronizationProtocol;
    }
    
    /**
     * Creates a new insight to share with the collective.
     * 
     * @param nodeId The source node ID
     * @param contextId The context in which the insight is relevant
     * @param content The insight content
     * @param metadata Additional metadata about the insight
     * @return The created Insight
     */
    public Insight createInsight(String nodeId, String contextId, String content, Map<String, Object> metadata) {
        // Apply privacy filter to content before storing
        String filteredContent = privacyFilter.filterContent(content);
        
        Insight insight = new Insight(nodeId, contextId, filteredContent, metadata);
        insightRegistry.put(insight.getInsightId(), insight);
        
        return insight;
    }
    
    /**
     * Initiates an insight exchange session with another node.
     * 
     * @param initiatorNodeId The node initiating the exchange
     * @param responderNodeId The node to exchange with
     * @param contextId The context for the exchange
     * @return The session ID if successful, empty Optional otherwise
     */
    public Optional<String> initiateExchange(String initiatorNodeId, String responderNodeId, String contextId) {
        // Check if responder node is available
        if (!nodeDiscovery.isNodeConnected(responderNodeId)) {
            return Optional.empty();
        }
        
        // Create new exchange session
        ExchangeSession session = new ExchangeSession(initiatorNodeId, responderNodeId, contextId);
        activeSessions.put(session.sessionId, session);
        
        return Optional.of(session.sessionId);
    }
    
    /**
     * Shares an insight with another node in the context of an exchange session.
     * 
     * @param sessionId The exchange session ID
     * @param insightId The insight to share
     * @return true if shared successfully, false otherwise
     */
    public boolean shareInsight(String sessionId, String insightId) {
        if (!activeSessions.containsKey(sessionId) || !insightRegistry.containsKey(insightId)) {
            return false;
        }
        
        ExchangeSession session = activeSessions.get(sessionId);
        Insight insight = insightRegistry.get(insightId);
        
        // Check if the insight can be shared in this context
        if (!informationBoundary.canInformationPass(insightId, session.contextId)) {
            return false;
        }
        
        // Record the exchange
        session.addExchangedInsight(insightId);
        session.updateState(ExchangeSession.SessionState.ACTIVE);
        
        return true;
    }
    
    /**
     * Receives an insight from another node.
     * 
     * @param sourceNodeId The node sending the insight
     * @param insightData Map containing insight data
     * @return The ID of the received insight if successful, empty Optional otherwise
     */
    public Optional<String> receiveInsight(String sourceNodeId, Map<String, Object> insightData) {
        try {
            String insightId = (String) insightData.get("insightId");
            String contextId = (String) insightData.get("contextId");
            String content = (String) insightData.get("content");
            Map<String, Object> metadata = (Map<String, Object>) insightData.get("metadata");
            Instant timestamp = Instant.parse((String) insightData.get("timestamp"));
            int propagationLevel = (int) insightData.get("propagationLevel");
            
            // Create insight from received data
            Insight receivedInsight = new Insight(
                insightId, sourceNodeId, contextId, content, metadata, timestamp, propagationLevel
            );
            
            // Store the received insight
            insightRegistry.put(insightId, receivedInsight);
            
            return Optional.of(insightId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    /**
     * Transforms an insight using the provided transformation function.
     * 
     * @param insightId The insight to transform
     * @param transformer The transformation function
     * @param newContextId The context ID for the transformed insight
     * @return The ID of the transformed insight if successful, empty Optional otherwise
     */
    public Optional<String> transformInsight(String insightId, Function<String, String> transformer, String newContextId) {
        if (!insightRegistry.containsKey(insightId)) {
            return Optional.empty();
        }
        
        Insight original = insightRegistry.get(insightId);
        
        try {
            // Apply transformation to content
            String transformedContent = transformer.apply(original.getContent());
            
            // Create new metadata based on original
            Map<String, Object> newMetadata = new HashMap<>(original.getMetadata());
            newMetadata.put("derivedFrom", original.getInsightId());
            newMetadata.put("transformationType", "custom");
            
            // Create transformed insight
            Insight transformedInsight = createInsight(
                original.getSourceNodeId(), 
                newContextId, 
                transformedContent, 
                newMetadata
            );
            
            // Set propagation level for the new insight
            transformedInsight.incrementPropagationLevel();
            
            return Optional.of(transformedInsight.getInsightId());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    /**
     * Propagates an insight to multiple nodes matching filter criteria.
     * 
     * @param insightId The insight to propagate
     * @param nodeFilter Filter to determine eligible nodes
     * @param maxPropagationLevel Maximum propagation level (how many hops)
     * @return A map of node IDs to future results of the propagation
     */
    public Map<String, CompletableFuture<Boolean>> propagateInsight(
            String insightId, 
            Function<Map<String, Object>, Boolean> nodeFilter, 
            int maxPropagationLevel) {
        
        if (!insightRegistry.containsKey(insightId)) {
            return Collections.emptyMap();
        }
        
        Insight insight = insightRegistry.get(insightId);
        
        // Check if we've reached max propagation level
        if (insight.getPropagationLevel() >= maxPropagationLevel) {
            return Collections.emptyMap();
        }
        
        // Find eligible nodes
        Map<String, Map<String, Object>> connectedNodes = nodeDiscovery.getConnectedNodes(nodeFilter::apply);
        Map<String, CompletableFuture<Boolean>> propagationResults = new HashMap<>();
        
        // Propagate to each eligible node
        for (String nodeId : connectedNodes.keySet()) {
            // Skip propagating back to the source
            if (nodeId.equals(insight.getSourceNodeId())) {
                continue;
            }
            
            // Create future for async propagation
            CompletableFuture<Boolean> future = new CompletableFuture<>();
            propagationResults.put(nodeId, future);
            
            // In a real implementation, this would be an async network call
            // For now, we'll just complete the future with success
            future.complete(true);
        }
        
        // Increment propagation level after propagating
        insight.incrementPropagationLevel();
        
        return propagationResults;
    }
    
    /**
     * Gets an insight by ID.
     * 
     * @param insightId The insight ID
     * @return An Optional containing the insight, or empty if not found
     */
    public Optional<Insight> getInsight(String insightId) {
        return Optional.ofNullable(insightRegistry.get(insightId));
    }
    
    /**
     * Gets all insights for a specific context.
     * 
     * @param contextId The context ID
     * @return A list of insights relevant to the context
     */
    public List<Insight> getInsightsForContext(String contextId) {
        List<Insight> contextInsights = new ArrayList<>();
        
        for (Insight insight : insightRegistry.values()) {
            if (insight.getContextId().equals(contextId)) {
                // Ensure information can pass this boundary for the context
                if (informationBoundary.canInformationPass(insight.getInsightId(), contextId)) {
                    contextInsights.add(insight);
                }
            }
        }
        
        return contextInsights;
    }
    
    /**
     * Completes an exchange session.
     * 
     * @param sessionId The session ID
     * @param success Whether the session completed successfully
     * @return true if session was successfully closed, false otherwise
     */
    public boolean completeExchangeSession(String sessionId, boolean success) {
        if (!activeSessions.containsKey(sessionId)) {
            return false;
        }
        
        ExchangeSession session = activeSessions.get(sessionId);
        session.updateState(success ? ExchangeSession.SessionState.COMPLETED : ExchangeSession.SessionState.FAILED);
        
        return true;
    }
    
    /**
     * Cleans up expired sessions.
     * 
     * @param maxSessionAge Maximum session age in milliseconds
     * @return The number of sessions cleaned up
     */
    public int cleanupExpiredSessions(long maxSessionAge) {
        int cleanedCount = 0;
        Instant now = Instant.now();
        
        List<String> expiredSessions = new ArrayList<>();
        
        for (Map.Entry<String, ExchangeSession> entry : activeSessions.entrySet()) {
            ExchangeSession session = entry.getValue();
            long sessionAgeMs = java.time.Duration.between(session.startTime, now).toMillis();
            
            if (sessionAgeMs > maxSessionAge) {
                expiredSessions.add(entry.getKey());
            }
        }
        
        for (String sessionId : expiredSessions) {
            activeSessions.remove(sessionId);
            cleanedCount++;
        }
        
        return cleanedCount;
    }
    
    /**
     * Exchanges insights with specified nodes in the collective.
     *
     * @param contextId The context identifier for the exchange
     * @param targetNodes The set of nodes to exchange insights with
     * @param insightType The type of insights to exchange
     * @return A list of exchanged insights
     */
    public List<Map<String, Object>> exchangeInsights(String contextId, Set<String> targetNodes, String insightType) {
        List<Map<String, Object>> exchangedInsights = new ArrayList<>();
        
        for (String nodeId : targetNodes) {
            if (nodeDiscovery.isNodeConnected(nodeId)) {
                try {
                    Map<String, Object> nodeData = nodeDiscovery.getNodeMetadata(nodeId)
                        .orElseThrow(() -> new IllegalStateException("Node metadata not found: " + nodeId));
                    
                    // Prepare exchange data
                    Map<String, Object> exchangeData = Map.of(
                        "contextId", contextId,
                        "insightType", insightType,
                        "sourceNode", nodeData.get(NodeDiscovery.NODE_ID)
                    );
                    
                    // Synchronize data with the target node
                    synchronizationProtocol.synchronizeDataType(
                        "insight_exchange",
                        exchangeData,
                        contextId
                    );
                    
                    // Add exchanged insights to the result list
                    exchangedInsights.add(exchangeData);
                } catch (Exception e) {
                    // Log error and continue with next node
                    System.err.println("Failed to exchange insights with node " + nodeId + ": " + e.getMessage());
                }
            }
        }
        
        return exchangedInsights;
    }
    
    /**
     * Asynchronously exchanges insights with specified nodes.
     *
     * @param contextId The context identifier for the exchange
     * @param targetNodes The set of nodes to exchange insights with
     * @param insightType The type of insights to exchange
     * @return A future that completes with the list of exchanged insights
     */
    public CompletableFuture<List<Map<String, Object>>> exchangeInsightsAsync(
            String contextId,
            Set<String> targetNodes,
            String insightType) {
        return CompletableFuture.supplyAsync(() -> exchangeInsights(contextId, targetNodes, insightType));
    }
}