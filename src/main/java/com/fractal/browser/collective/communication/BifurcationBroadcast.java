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
            this(UUID.randomUUID().toString(), sourceNodeId, topic, content, 
                    new HashMap<>(metadata), significance, Instant.now(), 0);
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
            if (pattern != null) {
                this.associatedPatterns.add(pattern);
            }
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
        if (nodeId == null || topic == null) {
            return false;
        }
        
        subscriptions.computeIfAbsent(nodeId, k -> new HashSet<>()).add(topic);
        return true;
    }
    
    /**
     * Unsubscribes a node from a topic.
     * 
     * @param nodeId The node ID
     * @param topic The topic to unsubscribe from
     * @return true if unsubscribed, false if not subscribed
     */
    public boolean unsubscribeTopic(String nodeId, String topic) {
        if (nodeId == null || topic == null) {
            return false;
        }
        
        Set<String> nodeSubscriptions = subscriptions.get(nodeId);
        if (nodeSubscriptions != null) {
            return nodeSubscriptions.remove(topic);
        }
        return false;
    }
    
    /**
     * Gets the topics a node is subscribed to.
     * 
     * @param nodeId The node ID
     * @return A set of subscribed topics
     */
    public Set<String> getSubscribedTopics(String nodeId) {
        if (nodeId == null) {
            return Collections.emptySet();
        }
        
        Set<String> nodeSubscriptions = subscriptions.get(nodeId);
        return nodeSubscriptions != null ? new HashSet<>(nodeSubscriptions) : Collections.emptySet();
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
        if (sourceNodeId == null || topic == null || content == null || metadata == null) {
            return Optional.empty();
        }
        
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
        if (eventId == null) {
            return Collections.emptyMap();
        }
        
        BifurcationEvent event = eventRegistry.get(eventId);
        if (event == null) {
            return Collections.emptyMap();
        }
        
        // Check if we've reached max propagation hops
        if (event.getPropagationHops() >= MAX_PROPAGATION_HOPS) {
            return Collections.emptyMap();
        }
        
        Map<String, CompletableFuture<Boolean>> results = new HashMap<>();
        Set<String> eligibleReceivers = getEligibleReceivers(eventId);
        
        for (String receiverId : eligibleReceivers) {
            if (filter == null || filter.test(Collections.singletonMap("nodeId", receiverId))) {
                CompletableFuture<Boolean> future = new CompletableFuture<>();
                results.put(receiverId, future);
                // In a real implementation, this would involve actual network communication
                future.complete(true);
            }
        }
        
        // Increment propagation hops after broadcasting
        event.incrementPropagationHops();
        
        return results;
    }
    
    /**
     * Receives a bifurcation event from another node.
     * 
     * @param sourceNodeId The node sending the event
     * @param eventData Map containing event data
     * @return The ID of the received event if successful, empty Optional otherwise
     */
    public Optional<String> receiveEvent(String sourceNodeId, Map<String, Object> eventData) {
        if (sourceNodeId == null || eventData == null) {
            return Optional.empty();
        }
        
        String eventId = (String) eventData.get("eventId");
        if (eventId == null) {
            return Optional.empty();
        }
        
        BifurcationEvent event = eventRegistry.get(eventId);
        if (event == null) {
            return Optional.empty();
        }
        
        event.incrementPropagationHops();
        return Optional.of(eventId);
    }
    
    /**
     * Gets a bifurcation event by ID.
     * 
     * @param eventId The event ID
     * @return An Optional containing the event, or empty if not found
     */
    public Optional<BifurcationEvent> getEvent(String eventId) {
        if (eventId == null) {
            return Optional.empty();
        }
        
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
        if (topic == null) {
            return Collections.emptyList();
        }
        
        List<BifurcationEvent> events = new ArrayList<>();
        for (BifurcationEvent event : eventRegistry.values()) {
            if (topic.equals(event.getTopic()) && 
                (contextId == null || boundary.canInformationPass(event.getEventId(), contextId))) {
                events.add(event);
            }
        }
        
        // Sort by significance (highest first)
        events.sort((e1, e2) -> Double.compare(e2.getSignificance(), e1.getSignificance()));
        
        return events;
    }
    
    /**
     * Gets the propagation path for an event.
     * 
     * @param eventId The event ID
     * @return The set of node IDs in the propagation path
     */
    public Set<String> getPropagationPath(String eventId) {
        if (eventId == null) {
            return Collections.emptySet();
        }
        
        Set<String> path = propagationPaths.get(eventId);
        return path != null ? new HashSet<>(path) : Collections.emptySet();
    }
    
    /**
     * Analyzes the significance of an event based on its content and metadata.
     * 
     * @param content The event content
     * @param metadata The event metadata
     * @return A significance score (0.0-1.0)
     */
    public double analyzeSignificance(String content, Map<String, Object> metadata) {
        if (content == null || metadata == null) {
            return 0.0;
        }
        
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
        if (limit <= 0) {
            return Collections.emptyList();
        }
        
        List<BifurcationEvent> events = new ArrayList<>(eventRegistry.values());
        events.sort((e1, e2) -> Double.compare(e2.getSignificance(), e1.getSignificance()));
        
        if (contextId != null) {
            events.removeIf(event -> !boundary.canInformationPass(event.getEventId(), contextId));
        }
        
        return events.subList(0, Math.min(limit, events.size()));
    }
    
    /**
     * Gets nodes that would receive an event if broadcasted.
     * 
     * @param eventId The event ID
     * @return A set of node IDs that would receive the event
     */
    public Set<String> getEligibleReceivers(String eventId) {
        if (eventId == null) {
            return Collections.emptySet();
        }
        
        BifurcationEvent event = eventRegistry.get(eventId);
        if (event == null) {
            return Collections.emptySet();
        }
        
        Set<String> receivers = new HashSet<>();
        for (Map.Entry<String, Set<String>> entry : subscriptions.entrySet()) {
            if (entry.getValue().contains(event.getTopic())) {
                receivers.add(entry.getKey());
            }
        }
        
        return receivers;
    }
}