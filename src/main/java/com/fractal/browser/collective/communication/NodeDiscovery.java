package com.fractal.browser.collective.communication;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.Collections;

import com.fractal.browser.collective.boundaries.TrustManager;

/**
 * NodeDiscovery implements a fractal discovery mechanism for collective nodes in the network.
 * It manages node registration, discovery, and provides mechanisms for nodes to join and
 * leave the collective network at multiple scales.
 * 
 * This class utilizes recursive discovery patterns to efficiently maintain network topology
 * across different scales of interaction.
 */
public class NodeDiscovery {
    
    // Node metadata key constants
    public static final String NODE_ID = "nodeId";
    public static final String NODE_TYPE = "nodeType";
    public static final String CAPABILITIES = "capabilities";
    public static final String LAST_ACTIVE = "lastActive";
    public static final String CONNECTIVITY = "connectivity";
    
    // Maps node IDs to their metadata
    private final Map<String, Map<String, Object>> nodeRegistry;
    
    // Maps node IDs to their connection status
    private final Map<String, Boolean> connectionStatus;
    
    // Set of listeners for node events
    private final Set<Consumer<NodeEvent>> nodeEventListeners;
    
    // Discovery timeout in milliseconds
    private long discoveryTimeout;
    
    // Maximum number of recursive discovery hops
    private int maxDiscoveryHops;
    
    /**
     * Represents different types of node events.
     */
    public enum NodeEventType {
        NODE_JOINED,
        NODE_LEFT,
        NODE_DISCOVERED,
        NODE_UPDATED,
        CONNECTIVITY_CHANGED
    }
    
    /**
     * Represents a node event within the collective.
     */
    public static class NodeEvent {
        private final String nodeId;
        private final NodeEventType eventType;
        private final Instant timestamp;
        private final Map<String, Object> metadata;
        
        public NodeEvent(String nodeId, NodeEventType eventType, Map<String, Object> metadata) {
            this.nodeId = nodeId;
            this.eventType = eventType;
            this.timestamp = Instant.now();
            this.metadata = metadata != null ? new HashMap<>(metadata) : new HashMap<>();
        }
        
        public String getNodeId() {
            return nodeId;
        }
        
        public NodeEventType getEventType() {
            return eventType;
        }
        
        public Instant getTimestamp() {
            return timestamp;
        }
        
        public Map<String, Object> getMetadata() {
            return Collections.unmodifiableMap(metadata);
        }
    }
    
    /**
     * Creates a new NodeDiscovery instance with default settings.
     */
    public NodeDiscovery() {
        this.nodeRegistry = new ConcurrentHashMap<>();
        this.connectionStatus = new ConcurrentHashMap<>();
        this.nodeEventListeners = Collections.synchronizedSet(new HashSet<>());
        this.discoveryTimeout = 30000; // 30 seconds
        this.maxDiscoveryHops = 3;
    }
    
    /**
     * Creates a new NodeDiscovery instance with custom settings.
     * 
     * @param discoveryTimeout The timeout for discovery operations in milliseconds
     * @param maxDiscoveryHops The maximum number of recursive discovery hops
     */
    public NodeDiscovery(long discoveryTimeout, int maxDiscoveryHops) {
        this.nodeRegistry = new ConcurrentHashMap<>();
        this.connectionStatus = new ConcurrentHashMap<>();
        this.nodeEventListeners = Collections.synchronizedSet(new HashSet<>());
        this.discoveryTimeout = discoveryTimeout;
        this.maxDiscoveryHops = maxDiscoveryHops;
    }
    
    /**
     * Registers a new node in the collective.
     * 
     * @param nodeId The unique identifier for the node
     * @param metadata Metadata describing the node capabilities and properties
     * @return true if successfully registered, false if already exists
     */
    public boolean registerNode(String nodeId, Map<String, Object> metadata) {
        if (nodeRegistry.containsKey(nodeId)) {
            return false; // Node already registered
        }
        
        Map<String, Object> nodeMetadata = new HashMap<>(metadata);
        nodeMetadata.put(LAST_ACTIVE, Instant.now().toString());
        nodeRegistry.put(nodeId, nodeMetadata);
        connectionStatus.put(nodeId, true);
        
        // Notify listeners of new node
        NodeEvent event = new NodeEvent(nodeId, NodeEventType.NODE_JOINED, nodeMetadata);
        notifyListeners(event);
        
        return true;
    }
    
    /**
     * Updates an existing node's metadata.
     * 
     * @param nodeId The node identifier
     * @param metadata The updated metadata
     * @return true if successfully updated, false if node doesn't exist
     */
    public boolean updateNode(String nodeId, Map<String, Object> metadata) {
        if (!nodeRegistry.containsKey(nodeId)) {
            return false;
        }
        
        Map<String, Object> existingMetadata = nodeRegistry.get(nodeId);
        existingMetadata.putAll(metadata);
        existingMetadata.put(LAST_ACTIVE, Instant.now().toString());
        
        // Notify listeners of update
        NodeEvent event = new NodeEvent(nodeId, NodeEventType.NODE_UPDATED, existingMetadata);
        notifyListeners(event);
        
        return true;
    }
    
    /**
     * Unregisters a node from the collective.
     * 
     * @param nodeId The node identifier
     * @return true if successfully removed, false if node wasn't registered
     */
    public boolean unregisterNode(String nodeId) {
        if (!nodeRegistry.containsKey(nodeId)) {
            return false;
        }
        
        Map<String, Object> nodeMetadata = nodeRegistry.remove(nodeId);
        connectionStatus.remove(nodeId);
        
        // Notify listeners of node departure
        NodeEvent event = new NodeEvent(nodeId, NodeEventType.NODE_LEFT, nodeMetadata);
        notifyListeners(event);
        
        return true;
    }
    
    /**
     * Discovers nodes in the collective that match the given criteria.
     * 
     * @param filter Predicate to filter nodes by their metadata
     * @return A map of node IDs to their metadata
     */
    public Map<String, Map<String, Object>> discoverNodes(Predicate<Map<String, Object>> filter) {
        Map<String, Map<String, Object>> discoveredNodes = new HashMap<>();
        
        for (Map.Entry<String, Map<String, Object>> entry : nodeRegistry.entrySet()) {
            if (filter == null || filter.test(entry.getValue())) {
                discoveredNodes.put(entry.getKey(), new HashMap<>(entry.getValue()));
            }
        }
        
        return discoveredNodes;
    }
    
    /**
     * Gets metadata for a specific node.
     * 
     * @param nodeId The node identifier
     * @return An Optional containing the node metadata, or empty if not found
     */
    public Optional<Map<String, Object>> getNodeMetadata(String nodeId) {
        if (!nodeRegistry.containsKey(nodeId)) {
            return Optional.empty();
        }
        
        return Optional.of(new HashMap<>(nodeRegistry.get(nodeId)));
    }
    
    /**
     * Sets the connection status of a node.
     * 
     * @param nodeId The node identifier
     * @param connected true if connected, false otherwise
     * @return true if status was changed, false if no change or node doesn't exist
     */
    public boolean setNodeConnectionStatus(String nodeId, boolean connected) {
        if (!nodeRegistry.containsKey(nodeId)) {
            return false;
        }
        
        boolean previousStatus = connectionStatus.getOrDefault(nodeId, false);
        if (previousStatus == connected) {
            return false; // No change
        }
        
        connectionStatus.put(nodeId, connected);
        Map<String, Object> nodeMetadata = nodeRegistry.get(nodeId);
        nodeMetadata.put(CONNECTIVITY, connected ? "CONNECTED" : "DISCONNECTED");
        nodeMetadata.put(LAST_ACTIVE, Instant.now().toString());
        
        // Notify listeners of connectivity change
        NodeEvent event = new NodeEvent(nodeId, NodeEventType.CONNECTIVITY_CHANGED, nodeMetadata);
        notifyListeners(event);
        
        return true;
    }
    
    /**
     * Checks if a node is currently connected.
     * 
     * @param nodeId The node identifier
     * @return true if connected, false otherwise
     */
    public boolean isNodeConnected(String nodeId) {
        return connectionStatus.getOrDefault(nodeId, false);
    }
    
    /**
     * Gets all registered nodes in the collective.
     * 
     * @return A map of node IDs to their metadata
     */
    public Map<String, Map<String, Object>> getAllNodes() {
        Map<String, Map<String, Object>> result = new HashMap<>();
        
        for (Map.Entry<String, Map<String, Object>> entry : nodeRegistry.entrySet()) {
            result.put(entry.getKey(), new HashMap<>(entry.getValue()));
        }
        
        return result;
    }
    
    /**
     * Gets connected nodes that match the specified filter.
     * 
     * @param filter Predicate to filter nodes by their metadata
     * @return A map of node IDs to their metadata
     */
    public Map<String, Map<String, Object>> getConnectedNodes(Predicate<Map<String, Object>> filter) {
        Map<String, Map<String, Object>> result = new HashMap<>();
        
        for (Map.Entry<String, Map<String, Object>> entry : nodeRegistry.entrySet()) {
            String nodeId = entry.getKey();
            if (isNodeConnected(nodeId) && (filter == null || filter.test(entry.getValue()))) {
                result.put(nodeId, new HashMap<>(entry.getValue()));
            }
        }
        
        return result;
    }
    
    /**
     * Adds a listener for node events.
     * 
     * @param listener The listener to add
     * @return true if added, false if it was already registered
     */
    public boolean addNodeEventListener(Consumer<NodeEvent> listener) {
        return nodeEventListeners.add(listener);
    }
    
    /**
     * Removes a listener for node events.
     * 
     * @param listener The listener to remove
     * @return true if removed, false if it wasn't registered
     */
    public boolean removeNodeEventListener(Consumer<NodeEvent> listener) {
        return nodeEventListeners.remove(listener);
    }
    
    /**
     * Notifies all registered listeners of a node event.
     * 
     * @param event The event to notify about
     */
    private void notifyListeners(NodeEvent event) {
        for (Consumer<NodeEvent> listener : nodeEventListeners) {
            try {
                listener.accept(event);
            } catch (Exception e) {
                // Log exception but continue notifying other listeners
                System.err.println("Error notifying listener: " + e.getMessage());
            }
        }
    }
    
    /**
     * Sets the discovery timeout.
     * 
     * @param timeout The timeout in milliseconds
     */
    public void setDiscoveryTimeout(long timeout) {
        this.discoveryTimeout = timeout;
    }
    
    /**
     * Sets the maximum number of discovery hops.
     * 
     * @param maxHops The maximum hops
     */
    public void setMaxDiscoveryHops(int maxHops) {
        this.maxDiscoveryHops = maxHops;
    }
    
    /**
     * Integrates with the TrustManager to filter nodes based on trust level.
     * 
     * @param trustManager The TrustManager to integrate with
     * @param minTrustLevel The minimum trust level required (0.0-1.0)
     * @return A map of trusted node IDs to their metadata
     */
    public Map<String, Map<String, Object>> discoverTrustedNodes(TrustManager trustManager, double minTrustLevel) {
        Map<String, Map<String, Object>> result = new HashMap<>();
        
        if (trustManager == null) {
            return result;
        }
        
        for (Map.Entry<String, Map<String, Object>> entry : nodeRegistry.entrySet()) {
            String nodeId = entry.getKey();
            Optional<Double> trustScore = trustManager.getTrustScore(nodeId);
            
            if (trustScore.isPresent() && trustScore.get() >= minTrustLevel) {
                result.put(nodeId, new HashMap<>(entry.getValue()));
            }
        }
        
        return result;
    }
}