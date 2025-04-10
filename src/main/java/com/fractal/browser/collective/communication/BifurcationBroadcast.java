package com.fractal.browser.collective.communication;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.Collections;

import com.fractal.browser.collective.boundaries.InformationBoundary;
import com.fractal.browser.model.Pattern;

/**
 * BifurcationBroadcast implements a special communication mechanism that optimizes
 * for transformative moments in collective cognition. It identifies and propagates
 * bifurcation points - moments where small inputs can create significant shifts in 
 * collective understanding.
 * 
 * This class applies the mathematics of bifurcation theory to communication patterns,
 * enabling efficient transmission of high-leverage insights across the network.
 */
public class BifurcationBroadcast {
    
    // Minimum significance threshold for bifurcation events
    private static final double MIN_SIGNIFICANCE = 0.7;
    
    // Maximum hop count for propagation
    private static final int MAX_PROPAGATION_HOPS = 5;
    
    // Maps broadcast IDs to their data
    private final Map<String, BifurcationEvent> eventRegistry;
    
    // Maps node IDs to their subscription topics
    private final Map<String, Set<String>> subscriptions;
    
    // Tracks propagation paths to avoid loops
    private final Map<String, Set<String>> propagationPaths;
    
    // Node discovery for broadcast routing
    private final NodeDiscovery nodeDiscovery;
    
    // Information boundary for access control
    private final InformationBoundary boundary;
    
    /**
     * Represents a bifurcation event in the collective.
     */
    public static class BifurcationEvent {
        private final String eventId;
        private final String sourceNodeId;
        private final String topic;
        private final String content;
        private final Map<String, Object> metadata;
        private final double significance;
        private final Instant timestamp;
        private int propagationHops;
        private final List<Pattern> associatedPatterns;
        
        /**
         * Creates a new bifurcation event.
         */
        public BifurcationEvent(String sourceNodeId, String topic, String content, 
                Map<String, Object> metadata, double significance) {
            this.eventId = UUID.randomUUID().toString();
            this.sourceNodeId = sourceNodeId;
            this.topic = topic;
            this.content = content;
            this.metadata = new HashMap<>(metadata);
            this.significance = Math.max(0.0, Math.min(1.0, significance));
            this.timestamp = Instant.now();
            this.propagationHops = 0;
            this.associatedPatterns = new ArrayList<>();
        }
        
        /**
         * Creates a new bifurcation event with specified ID (for receiving from network).
         */
        public BifurcationEvent(String eventId, String sourceNodeId, String topic, String content,
                Map<String, Object> metadata, double significance, Instant timestamp, int propagationHops) {
            this.eventId = eventId;
            this.sourceNodeId = sourceNodeId;
            this.topic = topic;
            this.content = content;
            this.metadata = new HashMap<>(metadata);
            this.significance = Math.max(0.0, Math.min(1.0, significance));
            this.timestamp = timestamp;
            this.propagationHops = propagationHops;
            this.associatedPatterns = new ArrayList<>();
        }
        
        // Getters
        public String getEventId() { return eventId; }
        public String getSourceNodeId() { return sourceNodeId; }
        public String getTopic() { return topic; }
        public String getContent() { return content; }
        public Map<String, Object> getMetadata() { return Collections.unmodifiableMap(metadata); }
        public double getSignificance() { return significance; }
        public Instant getTimestamp() { return timestamp; }
        public int getPropagationHops() { return propagationHops; }
        public List<Pattern> getAssociatedPatterns() { return Collections.unmodifiableList(associatedPatterns); }
        
        /**
         * Adds an associated pattern to this event.
         */
        public void addAssociatedPattern(Pattern pattern) {
            this.associatedPatterns.add(pattern);
        }
        
        /**
         * Increments the propagation hops counter.
         */
        public void incrementPropagationHops() {
            this.propagationHops++;
        }
    }
    
    /**
     * Creates a new BifurcationBroadcast with the specified components.
     */
    public BifurcationBroadcast(NodeDiscovery nodeDiscovery, InformationBoundary boundary) {
        this.eventRegistry = new ConcurrentHashMap<>();
        this.subscriptions = new ConcurrentHashMap<>();
        this.propagationPaths = new ConcurrentHashMap<>();
        this.nodeDiscovery = nodeDiscovery;
        this.boundary = boundary;
    }
    
    /**
     * Subscribes a node to a topic.
     * 
     * @param nodeId The node ID
     * @param topic The topic to subscribe to
     * @return true if subscribed, false if already subscribed
     */
    public boolean subscribeTopic(String nodeId, String topic) {
        subscriptions.putIfAbsent(nodeId, Collections.synchronizedSet(new HashSet<>()));
        return subscriptions.get(nodeId).add(topic);
    }
    
    /**
     * Unsubscribes a node from a topic.
     * 
     * @param nodeId The node ID
     * @param topic The topic to unsubscribe from
     * @return true if unsubscribed, false if not subscribed
     */
    public boolean unsubscribeTopic(String nodeId, String topic) {
        if (!subscriptions.containsKey(nodeId)) {
            return false;
        }
        
        return subscriptions.get(nodeId).remove(topic);
    }
    
    /**
     * Gets the topics a node is subscribed to.
     * 
     * @param nodeId The node ID
     * @return A set of subscribed topics
     */
    public Set<String> getSubscribedTopics(String nodeId) {
        if (!subscriptions.containsKey(nodeId)) {
            return Collections.emptySet();
        }
        
        return new HashSet<>(subscriptions.get(nodeId));
    }
    
    /**
     * Creates a new bifurcation event.
     * 
     * @param sourceNodeId The source node ID
     * @param topic The topic of the event
     * @param content The event content
     * @param metadata Additional metadata about the event
     * @param significance The significance level (0.0-1.0)
     * @return The created event if successful, empty if below significance threshold
     */
    public Optional<BifurcationEvent> createBifurcationEvent(String sourceNodeId, String topic, 
            String content, Map<String, Object> metadata, double significance) {
        
        // Check if this event meets the significance threshold
        if (significance < MIN_SIGNIFICANCE) {
            return Optional.empty();
        }
        
        BifurcationEvent event = new BifurcationEvent(sourceNodeId, topic, content, metadata, significance);
        eventRegistry.put(event.getEventId(), event);
        
        // Initialize propagation path for this event
        propagationPaths.put(event.getEventId(), new HashSet<>());
        propagationPaths.get(event.getEventId()).add(sourceNodeId);
        
        return Optional.of(event);
    }
    
    /**
     * Broadcasts a bifurcation event to subscribed nodes.
     * 
     * @param eventId The event ID to broadcast
     * @param filter Optional filter for eligible nodes
     * @return A map of node IDs to future results of the broadcast
     */
    public Map<String, CompletableFuture<Boolean>> broadcastEvent(String eventId, 
            Predicate<Map<String, Object>> filter) {
        
        if (!eventRegistry.containsKey(eventId)) {
            return Collections.emptyMap();
        }
        
        BifurcationEvent event = eventRegistry.get(eventId);
        
        // Check if we've reached max propagation hops
        if (event.getPropagationHops() >= MAX_PROPAGATION_HOPS) {
            return Collections.emptyMap();
        }
        
        // Find eligible nodes (connected and subscribed to this topic)
        Map<String, Map<String, Object>> connectedNodes = nodeDiscovery.getConnectedNodes(filter);
        Map<String, CompletableFuture<Boolean>> broadcastResults = new HashMap<>();
        
        for (String nodeId : connectedNodes.keySet()) {
            // Skip broadcasting back to the source or nodes we've already sent to
            if (nodeId.equals(event.getSourceNodeId()) || 
                    (propagationPaths.containsKey(eventId) && 
                     propagationPaths.get(eventId).contains(nodeId))) {
                continue;
            }
            
            // Check if node is subscribed to this topic
            if (subscriptions.containsKey(nodeId) && 
                    subscriptions.get(nodeId).contains(event.getTopic())) {
                
                // Check if event can pass information boundary
                if (boundary.canInformationPass(eventId, nodeId)) {
                    // Track this node in the propagation path
                    propagationPaths.get(eventId).add(nodeId);
                    
                    // Create future for async broadcast
                    CompletableFuture<Boolean> future = new CompletableFuture<>();
                    broadcastResults.put(nodeId, future);
                    
                    // In a real implementation, this would be an async network call
                    // For now, we'll just complete the future with success
                    future.complete(true);
                }
            }
        }
        
        // Increment propagation hops after broadcasting
        event.incrementPropagationHops();
        
        return broadcastResults;
    }
    
    /**
     * Receives a bifurcation event from another node.
     * 
     * @param sourceNodeId The node sending the event
     * @param eventData Map containing event data
     * @return The ID of the received event if successful, empty Optional otherwise
     */
    public Optional<String> receiveEvent(String sourceNodeId, Map<String, Object> eventData) {
        try {
            String eventId = (String) eventData.get("eventId");
            String topic = (String) eventData.get("topic");
            String content = (String) eventData.get("content");
            Map<String, Object> metadata = (Map<String, Object>) eventData.get("metadata");
            double significance = (double) eventData.get("significance");
            Instant timestamp = Instant.parse((String) eventData.get("timestamp"));
            int propagationHops = (int) eventData.get("propagationHops");
            
            // Create event from received data
            BifurcationEvent receivedEvent = new BifurcationEvent(
                eventId, sourceNodeId, topic, content, metadata, significance, timestamp, propagationHops
            );
            
            // Store the received event
            eventRegistry.put(eventId, receivedEvent);
            
            // Initialize propagation path for this event if not exists
            propagationPaths.putIfAbsent(eventId, new HashSet<>());
            propagationPaths.get(eventId).add(sourceNodeId);
            
            return Optional.of(eventId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    /**
     * Gets a bifurcation event by ID.
     * 
     * @param eventId The event ID
     * @return An Optional containing the event, or empty if not found
     */
    public Optional<BifurcationEvent> getEvent(String eventId) {
        return Optional.ofNullable(eventRegistry.get(eventId));
    }
    
    /**
     * Gets all bifurcation events for a specific topic that pass boundary checks.
     * 
     * @param topic The topic
     * @param contextId The context ID for boundary checks
     * @return A list of relevant events
     */
    public List<BifurcationEvent> getEventsForTopic(String topic, String contextId) {
        List<BifurcationEvent> topicEvents = new ArrayList<>();
        
        for (BifurcationEvent event : eventRegistry.values()) {
            if (event.getTopic().equals(topic)) {
                // Ensure event can pass this boundary for the context
                if (boundary.canInformationPass(event.getEventId(), contextId)) {
                    topicEvents.add(event);
                }
            }
        }
        
        // Sort by significance (highest first)
        topicEvents.sort((e1, e2) -> Double.compare(e2.getSignificance(), e1.getSignificance()));
        
        return topicEvents;
    }
    
    /**
     * Gets the propagation path for an event.
     * 
     * @param eventId The event ID
     * @return The set of node IDs in the propagation path
     */
    public Set<String> getPropagationPath(String eventId) {
        if (!propagationPaths.containsKey(eventId)) {
            return Collections.emptySet();
        }
        
        return new HashSet<>(propagationPaths.get(eventId));
    }
    
    /**
     * Analyzes the significance of an event based on its content and metadata.
     * 
     * @param content The event content
     * @param metadata The event metadata
     * @return A significance score (0.0-1.0)
     */
    public double analyzeSignificance(String content, Map<String, Object> metadata) {
        // This would typically involve a more sophisticated analysis algorithm
        // For now, we'll use a simple heuristic based on content length and metadata
        
        double significanceScore = 0.5; // Base score
        
        // Adjust based on content length (longer content might be more significant)
        if (content != null) {
            int contentLength = content.length();
            if (contentLength > 1000) {
                significanceScore += 0.2;
            } else if (contentLength > 500) {
                significanceScore += 0.1;
            }
        }
        
        // Adjust based on metadata
        if (metadata != null) {
            // Check for explicitly provided significance
            if (metadata.containsKey("significance") && metadata.get("significance") instanceof Number) {
                double providedSignificance = ((Number) metadata.get("significance")).doubleValue();
                significanceScore = Math.max(significanceScore, providedSignificance);
            }
            
            // Check for priority indicator
            if (metadata.containsKey("priority") && metadata.get("priority") instanceof String) {
                String priority = (String) metadata.get("priority");
                if (priority.equalsIgnoreCase("high")) {
                    significanceScore += 0.2;
                } else if (priority.equalsIgnoreCase("critical")) {
                    significanceScore += 0.3;
                }
            }
        }
        
        // Ensure score is in valid range
        return Math.max(0.0, Math.min(1.0, significanceScore));
    }
    
    /**
     * Finds the most significant events across all topics.
     * 
     * @param limit Maximum number of events to return
     * @param contextId The context ID for boundary checks
     * @return A list of the most significant events
     */
    public List<BifurcationEvent> getMostSignificantEvents(int limit, String contextId) {
        List<BifurcationEvent> allEvents = new ArrayList<>();
        
        for (BifurcationEvent event : eventRegistry.values()) {
            // Ensure event can pass this boundary for the context
            if (boundary.canInformationPass(event.getEventId(), contextId)) {
                allEvents.add(event);
            }
        }
        
        // Sort by significance (highest first)
        allEvents.sort((e1, e2) -> Double.compare(e2.getSignificance(), e1.getSignificance()));
        
        // Return up to the limit
        return allEvents.size() <= limit ? allEvents : allEvents.subList(0, limit);
    }
    
    /**
     * Gets nodes that would receive an event if broadcasted.
     * 
     * @param eventId The event ID
     * @return A set of node IDs that would receive the event
     */
    public Set<String> getEligibleReceivers(String eventId) {
        if (!eventRegistry.containsKey(eventId)) {
            return Collections.emptySet();
        }
        
        BifurcationEvent event = eventRegistry.get(eventId);
        Set<String> eligibleNodes = new HashSet<>();
        
        // Find all connected nodes
        Map<String, Map<String, Object>> connectedNodes = nodeDiscovery.getConnectedNodes(null);
        
        for (String nodeId : connectedNodes.keySet()) {
            // Skip the source and nodes that already received this event
            if (nodeId.equals(event.getSourceNodeId()) || 
                    (propagationPaths.containsKey(eventId) && 
                     propagationPaths.get(eventId).contains(nodeId))) {
                continue;
            }
            
            // Check if node is subscribed to this topic
            if (subscriptions.containsKey(nodeId) && 
                    subscriptions.get(nodeId).contains(event.getTopic())) {
                
                // Check if event can pass information boundary
                if (boundary.canInformationPass(eventId, nodeId)) {
                    eligibleNodes.add(nodeId);
                }
            }
        }
        
        return eligibleNodes;
    }
}