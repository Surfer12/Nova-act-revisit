package com.fractal.browser.collective.visualization;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;

import com.fractal.browser.collective.boundaries.InformationBoundary;
import com.fractal.browser.collective.communication.NodeDiscovery;
import com.fractal.browser.collective.core.MetaAwarenessNetwork;
import com.fractal.browser.collective.memory.DistributedInsightRepository;
import com.fractal.browser.exceptions.FractalBrowserException;

/**
 * NetworkVisualization renders the collective node network topology, showing connections, 
 * information flow, and interaction patterns between nodes. This class applies fractal
 * visualization techniques that represent the system at multiple scales.
 * 
 * The visualization uses recursive rendering patterns to show macro, meso, and micro
 * network dynamics in a way that highlights emergent system behavior.
 */
public class NetworkVisualization {

    // Core dependencies
    private final NodeDiscovery nodeDiscovery;
    private final InformationBoundary boundary;
    private final MetaAwarenessNetwork metaAwarenessNetwork;
    private final DistributedInsightRepository insightRepository;
    
    // Visualization state
    private final Map<String, NodeVisualizationState> nodeStates;
    private final Map<String, EdgeVisualizationState> edgeStates;
    private final Map<String, VisualizationSnapshot> snapshots;
    
    // Visualization configuration
    private final int nodeSizeBaseScale;
    private final double edgeThicknessMultiplier;
    private final boolean showInactiveNodes;
    private final int maxVisualizationDepth;
    
    /**
     * Represents the visualization state of a node.
     */
    public static class NodeVisualizationState {
        private final String nodeId;
        private double x;
        private double y;
        private double size;
        private String color;
        private final Map<String, Object> metadata;
        private final Map<String, Double> metrics;
        private boolean visible;
        
        /**
         * Creates a new node visualization state.
         */
        public NodeVisualizationState(String nodeId, double x, double y, double size, String color) {
            this.nodeId = nodeId;
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = color;
            this.metadata = new HashMap<>();
            this.metrics = new HashMap<>();
            this.visible = true;
        }
        
        // Getters and setters
        public String getNodeId() { return nodeId; }
        public double getX() { return x; }
        public void setX(double x) { this.x = x; }
        public double getY() { return y; }
        public void setY(double y) { this.y = y; }
        public double getSize() { return size; }
        public void setSize(double size) { this.size = size; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        public Map<String, Object> getMetadata() { return metadata; }
        public Map<String, Double> getMetrics() { return metrics; }
        public boolean isVisible() { return visible; }
        public void setVisible(boolean visible) { this.visible = visible; }
    }
    
    /**
     * Represents the visualization state of an edge between nodes.
     */
    public static class EdgeVisualizationState {
        private final String edgeId;
        private final String sourceNodeId;
        private final String targetNodeId;
        private double thickness;
        private String color;
        private String style; // "solid", "dashed", etc.
        private final Map<String, Object> metadata;
        private boolean visible;
        private double informationFlowRate;
        
        /**
         * Creates a new edge visualization state.
         */
        public EdgeVisualizationState(String edgeId, String sourceNodeId, String targetNodeId, 
                double thickness, String color, String style) {
            this.edgeId = edgeId;
            this.sourceNodeId = sourceNodeId;
            this.targetNodeId = targetNodeId;
            this.thickness = thickness;
            this.color = color;
            this.style = style;
            this.metadata = new HashMap<>();
            this.visible = true;
            this.informationFlowRate = 0.0;
        }
        
        // Getters and setters
        public String getEdgeId() { return edgeId; }
        public String getSourceNodeId() { return sourceNodeId; }
        public String getTargetNodeId() { return targetNodeId; }
        public double getThickness() { return thickness; }
        public void setThickness(double thickness) { this.thickness = thickness; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        public String getStyle() { return style; }
        public void setStyle(String style) { this.style = style; }
        public Map<String, Object> getMetadata() { return metadata; }
        public boolean isVisible() { return visible; }
        public void setVisible(boolean visible) { this.visible = visible; }
        public double getInformationFlowRate() { return informationFlowRate; }
        public void setInformationFlowRate(double rate) { this.informationFlowRate = rate; }
    }
    
    /**
     * Represents a snapshot of the visualization state at a point in time.
     */
    public static class VisualizationSnapshot {
        private final String snapshotId;
        private final long timestamp;
        private final Map<String, NodeVisualizationState> nodeStates;
        private final Map<String, EdgeVisualizationState> edgeStates;
        private final String description;
        
        /**
         * Creates a new visualization snapshot.
         */
        public VisualizationSnapshot(String snapshotId, Map<String, NodeVisualizationState> nodeStates,
                Map<String, EdgeVisualizationState> edgeStates, String description) {
            this.snapshotId = snapshotId;
            this.timestamp = System.currentTimeMillis();
            // Create deep copies of the states
            this.nodeStates = nodeStates.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> copyNodeState(e.getValue())
                    ));
            this.edgeStates = edgeStates.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> copyEdgeState(e.getValue())
                    ));
            this.description = description;
        }
        
        // Create a deep copy of a node state
        private static NodeVisualizationState copyNodeState(NodeVisualizationState original) {
            NodeVisualizationState copy = new NodeVisualizationState(
                    original.getNodeId(),
                    original.getX(),
                    original.getY(),
                    original.getSize(),
                    original.getColor()
            );
            copy.setVisible(original.isVisible());
            copy.getMetadata().putAll(original.getMetadata());
            copy.getMetrics().putAll(original.getMetrics());
            return copy;
        }
        
        // Create a deep copy of an edge state
        private static EdgeVisualizationState copyEdgeState(EdgeVisualizationState original) {
            EdgeVisualizationState copy = new EdgeVisualizationState(
                    original.getEdgeId(),
                    original.getSourceNodeId(),
                    original.getTargetNodeId(),
                    original.getThickness(),
                    original.getColor(),
                    original.getStyle()
            );
            copy.setVisible(original.isVisible());
            copy.setInformationFlowRate(original.getInformationFlowRate());
            copy.getMetadata().putAll(original.getMetadata());
            return copy;
        }
        
        // Getters
        public String getSnapshotId() { return snapshotId; }
        public long getTimestamp() { return timestamp; }
        public Map<String, NodeVisualizationState> getNodeStates() { return nodeStates; }
        public Map<String, EdgeVisualizationState> getEdgeStates() { return edgeStates; }
        public String getDescription() { return description; }
    }
    
    /**
     * Creates a new NetworkVisualization instance.
     * 
     * @param nodeDiscovery Service for discovering network nodes
     * @param boundary Information boundary for enforcing access controls
     * @param metaAwarenessNetwork Network for meta-level system awareness
     * @param insightRepository Repository for storing and retrieving insights
     * @param nodeSizeBaseScale Base scale factor for node visualization
     * @param edgeThicknessMultiplier Multiplier for edge thickness based on flow
     * @param showInactiveNodes Whether to show inactive nodes
     * @param maxVisualizationDepth Maximum depth for recursive visualization
     */
    public NetworkVisualization(
            NodeDiscovery nodeDiscovery,
            InformationBoundary boundary,
            MetaAwarenessNetwork metaAwarenessNetwork,
            DistributedInsightRepository insightRepository,
            int nodeSizeBaseScale,
            double edgeThicknessMultiplier,
            boolean showInactiveNodes,
            int maxVisualizationDepth) {
        
        this.nodeDiscovery = nodeDiscovery;
        this.boundary = boundary;
        this.metaAwarenessNetwork = metaAwarenessNetwork;
        this.insightRepository = insightRepository;
        
        this.nodeSizeBaseScale = nodeSizeBaseScale;
        this.edgeThicknessMultiplier = edgeThicknessMultiplier;
        this.showInactiveNodes = showInactiveNodes;
        this.maxVisualizationDepth = maxVisualizationDepth;
        
        this.nodeStates = new ConcurrentHashMap<>();
        this.edgeStates = new ConcurrentHashMap<>();
        this.snapshots = new ConcurrentHashMap<>();
    }
    
    /**
     * Initializes the network visualization based on the current state of the network.
     * 
     * @param contextId The context ID for boundary enforcement
     * @return A map of visualization metadata
     * @throws FractalBrowserException if initialization fails
     */
    public Map<String, Object> initializeVisualization(String contextId) {
        // Clear existing state
        nodeStates.clear();
        edgeStates.clear();
        
        try {
            // Discover active nodes
            Set<String> activeNodes = nodeDiscovery.discoverNodes();
            
            // Initialize node states with default positions using a circular layout
            initializeNodeStates(activeNodes, contextId);
            
            // Initialize edge states based on node connections
            initializeEdgeStates(activeNodes, contextId);
            
            // Apply force-directed layout algorithm
            applyForceDirectedLayout(10, contextId); // 10 iterations
            
            // Create initial snapshot
            String snapshotId = createSnapshot("Initial network state", contextId);
            
            // Return visualization metadata
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("nodeCount", nodeStates.size());
            metadata.put("edgeCount", edgeStates.size());
            metadata.put("snapshotId", snapshotId);
            metadata.put("timestamp", System.currentTimeMillis());
            
            return metadata;
        } catch (Exception e) {
            throw new FractalBrowserException("Failed to initialize network visualization: " + e.getMessage(), e);
        }
    }
    
    /**
     * Initializes node states with default positions.
     * 
     * @param activeNodes Set of active node IDs
     * @param contextId The context ID for boundary enforcement
     */
    private void initializeNodeStates(Set<String> activeNodes, String contextId) {
        int nodeCount = activeNodes.size();
        double radius = nodeCount * 10; // Adjust for visualization size
        int i = 0;
        
        for (String nodeId : activeNodes) {
            // Skip if this node is not visible due to boundary constraints
            if (!boundary.canInformationPass(nodeId, contextId)) {
                continue;
            }
            
            // Calculate position on a circle
            double angle = (2 * Math.PI * i) / nodeCount;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            
            // Create node state with default values
            double size = nodeSizeBaseScale;
            String color = "#3366cc"; // Default blue color
            
            NodeVisualizationState state = new NodeVisualizationState(nodeId, x, y, size, color);
            
            // Add metadata about the node
            Optional<Map<String, Object>> nodeInfo = metaAwarenessNetwork.getNodeInfo(nodeId, contextId);
            if (nodeInfo.isPresent()) {
                state.getMetadata().putAll(nodeInfo.get());
                
                // Adjust node size based on importance/activity
                if (nodeInfo.get().containsKey("importance")) {
                    Object importanceObj = nodeInfo.get().get("importance");
                    if (importanceObj instanceof Number) {
                        double importance = ((Number) importanceObj).doubleValue();
                        state.setSize(nodeSizeBaseScale * (1 + importance));
                    }
                }
                
                // Set node color based on type
                if (nodeInfo.get().containsKey("type")) {
                    String type = nodeInfo.get().get("type").toString();
                    switch (type) {
                        case "processor":
                            state.setColor("#3366cc"); // Blue
                            break;
                        case "storage":
                            state.setColor("#33cc33"); // Green
                            break;
                        case "gateway":
                            state.setColor("#cc3333"); // Red
                            break;
                        case "analyzer":
                            state.setColor("#cc33cc"); // Purple
                            break;
                        default:
                            state.setColor("#999999"); // Gray for unknown
                    }
                }
            }
            
            // Store the node state
            nodeStates.put(nodeId, state);
            i++;
        }
        
        // Add inactive nodes if configured
        if (showInactiveNodes) {
            Set<String> allNodes = nodeDiscovery.getAllKnownNodes();
            Set<String> inactiveNodes = new HashSet<>(allNodes);
            inactiveNodes.removeAll(activeNodes);
            
            // Position inactive nodes in an outer ring
            double inactiveRadius = radius * 1.5;
            i = 0;
            
            for (String nodeId : inactiveNodes) {
                // Skip if this node is not visible due to boundary constraints
                if (!boundary.canInformationPass(nodeId, contextId)) {
                    continue;
                }
                
                double angle = (2 * Math.PI * i) / inactiveNodes.size();
                double x = inactiveRadius * Math.cos(angle);
                double y = inactiveRadius * Math.sin(angle);
                
                NodeVisualizationState state = new NodeVisualizationState(
                        nodeId, x, y, nodeSizeBaseScale * 0.7, "#cccccc");
                
                state.getMetadata().put("active", false);
                nodeStates.put(nodeId, state);
                i++;
            }
        }
    }
    
    /**
     * Initializes edge states based on node connections.
     * 
     * @param activeNodes Set of active node IDs
     * @param contextId The context ID for boundary enforcement
     */
    private void initializeEdgeStates(Set<String> activeNodes, String contextId) {
        // For each active node, get its connections
        for (String nodeId : activeNodes) {
            if (!nodeStates.containsKey(nodeId)) {
                continue; // Skip if node not in visualization
            }
            
            // Get connections for this node
            Map<String, Double> connections = getNodeConnections(nodeId, contextId);
            
            for (Map.Entry<String, Double> connection : connections.entrySet()) {
                String targetNodeId = connection.getKey();
                
                // Skip if target node not in visualization
                if (!nodeStates.containsKey(targetNodeId)) {
                    continue;
                }
                
                // Create a unique edge ID
                String edgeId = nodeId + "->" + targetNodeId;
                
                // Create edge state based on connection strength
                double strength = connection.getValue();
                double thickness = Math.max(1.0, strength * edgeThicknessMultiplier);
                
                // Color based on strength
                String color = getColorForStrength(strength);
                
                // Style based on connection type
                String style = "solid"; // Default solid line
                
                // Create the edge
                EdgeVisualizationState edge = new EdgeVisualizationState(
                        edgeId, nodeId, targetNodeId, thickness, color, style);
                
                // Set flow rate
                edge.setInformationFlowRate(strength);
                
                // Store the edge
                edgeStates.put(edgeId, edge);
            }
        }
    }
    
    /**
     * Gets connections for a specific node.
     * 
     * @param nodeId The node ID
     * @param contextId The context ID for boundary enforcement
     * @return Map of connected node IDs to connection strengths
     */
    private Map<String, Double> getNodeConnections(String nodeId, String contextId) {
        Map<String, Double> connections = new HashMap<>();
        
        // Use meta-awareness network to get connection information
        Optional<Map<String, Object>> nodeInfo = metaAwarenessNetwork.getNodeInfo(nodeId, contextId);
        
        if (nodeInfo.isPresent() && nodeInfo.get().containsKey("connections")) {
            @SuppressWarnings("unchecked")
            Map<String, Double> rawConnections = (Map<String, Double>) nodeInfo.get().get("connections");
            
            // Filter by boundary constraints
            for (Map.Entry<String, Double> entry : rawConnections.entrySet()) {
                String targetNodeId = entry.getKey();
                if (boundary.canInformationPass(targetNodeId, contextId)) {
                    connections.put(targetNodeId, entry.getValue());
                }
            }
        }
        
        return connections;
    }
    
    /**
     * Determines edge color based on connection strength.
     * 
     * @param strength Connection strength (0.0-1.0)
     * @return Hex color code
     */
    private String getColorForStrength(double strength) {
        if (strength >= 0.8) {
            return "#ff0000"; // Strong - Red
        } else if (strength >= 0.5) {
            return "#ff9900"; // Medium - Orange
        } else if (strength >= 0.2) {
            return "#ffcc00"; // Weak - Yellow
        } else {
            return "#cccccc"; // Very weak - Gray
        }
    }
    
    /**
     * Applies a force-directed layout algorithm to position nodes.
     * 
     * @param iterations Number of iterations to run
     * @param contextId The context ID for boundary enforcement
     */
    private void applyForceDirectedLayout(int iterations, String contextId) {
        // Simple force-directed layout implementation
        // In a real implementation, this would be more sophisticated
        
        // Configuration
        double repulsionForce = 500.0;
        double attractionForce = 0.01;
        double maxMovement = 5.0;
        
        for (int i = 0; i < iterations; i++) {
            // Calculate forces for each node
            Map<String, double[]> forces = new HashMap<>();
            
            // Initialize forces
            for (String nodeId : nodeStates.keySet()) {
                forces.put(nodeId, new double[] {0, 0});
            }
            
            // Calculate repulsion forces between all nodes
            List<String> nodeIds = new ArrayList<>(nodeStates.keySet());
            for (int j = 0; j < nodeIds.size(); j++) {
                String nodeId1 = nodeIds.get(j);
                NodeVisualizationState node1 = nodeStates.get(nodeId1);
                
                for (int k = j + 1; k < nodeIds.size(); k++) {
                    String nodeId2 = nodeIds.get(k);
                    NodeVisualizationState node2 = nodeStates.get(nodeId2);
                    
                    // Calculate distance
                    double dx = node2.getX() - node1.getX();
                    double dy = node2.getY() - node1.getY();
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    
                    // Avoid division by zero
                    if (distance < 0.1) {
                        distance = 0.1;
                    }
                    
                    // Calculate repulsion force
                    double force = repulsionForce / (distance * distance);
                    double fx = force * dx / distance;
                    double fy = force * dy / distance;
                    
                    // Apply to both nodes in opposite directions
                    forces.get(nodeId1)[0] -= fx;
                    forces.get(nodeId1)[1] -= fy;
                    forces.get(nodeId2)[0] += fx;
                    forces.get(nodeId2)[1] += fy;
                }
            }
            
            // Calculate attraction forces along edges
            for (EdgeVisualizationState edge : edgeStates.values()) {
                String sourceId = edge.getSourceNodeId();
                String targetId = edge.getTargetNodeId();
                
                if (!nodeStates.containsKey(sourceId) || !nodeStates.containsKey(targetId)) {
                    continue;
                }
                
                NodeVisualizationState source = nodeStates.get(sourceId);
                NodeVisualizationState target = nodeStates.get(targetId);
                
                // Calculate distance
                double dx = target.getX() - source.getX();
                double dy = target.getY() - source.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                
                // Avoid division by zero
                if (distance < 0.1) {
                    distance = 0.1;
                }
                
                // Calculate attraction force
                double force = attractionForce * distance;
                double fx = force * dx / distance;
                double fy = force * dy / distance;
                
                // Apply to both nodes
                forces.get(sourceId)[0] += fx;
                forces.get(sourceId)[1] += fy;
                forces.get(targetId)[0] -= fx;
                forces.get(targetId)[1] -= fy;
            }
            
            // Apply forces to update positions
            for (String nodeId : nodeStates.keySet()) {
                NodeVisualizationState node = nodeStates.get(nodeId);
                double[] force = forces.get(nodeId);
                
                // Limit maximum movement
                double magnitude = Math.sqrt(force[0] * force[0] + force[1] * force[1]);
                if (magnitude > maxMovement) {
                    force[0] = force[0] * maxMovement / magnitude;
                    force[1] = force[1] * maxMovement / magnitude;
                }
                
                // Update position
                node.setX(node.getX() + force[0]);
                node.setY(node.getY() + force[1]);
            }
        }
    }
    
    /**
     * Creates a snapshot of the current visualization state.
     * 
     * @param description Description of the snapshot
     * @param contextId The context ID for boundary enforcement
     * @return The snapshot ID
     */
    public String createSnapshot(String description, String contextId) {
        String snapshotId = UUID.randomUUID().toString();
        
        // Create snapshot with deep copies of the current state
        VisualizationSnapshot snapshot = new VisualizationSnapshot(
                snapshotId, nodeStates, edgeStates, description);
        
        // Store the snapshot
        snapshots.put(snapshotId, snapshot);
        
        // Store the snapshot as an insight if repository available
        if (insightRepository != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("snapshotId", snapshotId);
            data.put("description", description);
            data.put("timestamp", snapshot.getTimestamp());
            data.put("nodeCount", snapshot.getNodeStates().size());
            data.put("edgeCount", snapshot.getEdgeStates().size());
            
            insightRepository.storeInsight(
                    "network-visualization",
                    "Network visualization snapshot: " + description,
                    Set.of("visualization", "network", "snapshot"),
                    data,
                    contextId);
        }
        
        return snapshotId;
    }
    
    /**
     * Gets a visualization snapshot by ID.
     * 
     * @param snapshotId The snapshot ID
     * @param contextId The context ID for boundary enforcement
     * @return An Optional containing the snapshot if found and accessible
     */
    public Optional<VisualizationSnapshot> getSnapshot(String snapshotId, String contextId) {
        if (!snapshots.containsKey(snapshotId)) {
            return Optional.empty();
        }
        
        // Check if snapshot can pass information boundary
        if (!boundary.canInformationPass(snapshotId, contextId)) {
            return Optional.empty();
        }
        
        return Optional.of(snapshots.get(snapshotId));
    }
    
    /**
     * Gets all snapshots accessible in the given context.
     * 
     * @param contextId The context ID for boundary enforcement
     * @return List of accessible snapshots
     */
    public List<VisualizationSnapshot> getAllSnapshots(String contextId) {
        return snapshots.entrySet().stream()
                .filter(entry -> boundary.canInformationPass(entry.getKey(), contextId))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
    
    /**
     * Updates the network visualization based on current network state.
     * 
     * @param contextId The context ID for boundary enforcement
     * @return A map of visualization update metadata
     */
    public Map<String, Object> updateVisualization(String contextId) {
        try {
            // Discover active nodes
            Set<String> activeNodes = nodeDiscovery.discoverNodes();
            Set<String> currentNodes = new HashSet<>(nodeStates.keySet());
            
            // Find new and removed nodes
            Set<String> newNodes = new HashSet<>(activeNodes);
            newNodes.removeAll(currentNodes);
            
            Set<String> removedNodes = new HashSet<>(currentNodes);
            removedNodes.removeAll(activeNodes);
            removedNodes.removeAll(nodeDiscovery.getAllKnownNodes());
            
            // Update existing nodes
            for (String nodeId : currentNodes) {
                if (!removedNodes.contains(nodeId)) {
                    updateNodeState(nodeId, contextId);
                }
            }
            
            // Add new nodes
            for (String nodeId : newNodes) {
                addNewNode(nodeId, activeNodes, contextId);
            }
            
            // Remove completely inactive nodes
            for (String nodeId : removedNodes) {
                nodeStates.remove(nodeId);
                
                // Remove associated edges
                List<String> edgesToRemove = edgeStates.entrySet().stream()
                        .filter(e -> e.getValue().getSourceNodeId().equals(nodeId) ||
                                e.getValue().getTargetNodeId().equals(nodeId))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
                
                for (String edgeId : edgesToRemove) {
                    edgeStates.remove(edgeId);
                }
            }
            
            // Update edge states
            updateEdgeStates(activeNodes, contextId);
            
            // Apply layout adjustments
            applyForceDirectedLayout(5, contextId); // 5 iterations for updates
            
            // Create update snapshot
            String snapshotId = createSnapshot("Network update at " + 
                    java.time.Instant.now(), contextId);
            
            // Return update metadata
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("nodeCount", nodeStates.size());
            metadata.put("newNodes", newNodes.size());
            metadata.put("removedNodes", removedNodes.size());
            metadata.put("edgeCount", edgeStates.size());
            metadata.put("snapshotId", snapshotId);
            metadata.put("timestamp", System.currentTimeMillis());
            
            return metadata;
        } catch (Exception e) {
            throw new FractalBrowserException("Failed to update network visualization: " + e.getMessage(), e);
        }
    }
    
    /**
     * Updates the state of an existing node.
     * 
     * @param nodeId The node ID
     * @param contextId The context ID for boundary enforcement
     */
    private void updateNodeState(String nodeId, String contextId) {
        if (!nodeStates.containsKey(nodeId)) {
            return;
        }
        
        NodeVisualizationState state = nodeStates.get(nodeId);
        
        // Update metadata and metrics from meta-awareness network
        Optional<Map<String, Object>> nodeInfo = metaAwarenessNetwork.getNodeInfo(nodeId, contextId);
        if (nodeInfo.isPresent()) {
            // Update metadata
            state.getMetadata().clear();
            state.getMetadata().putAll(nodeInfo.get());
            
            // Update metrics
            if (nodeInfo.get().containsKey("metrics")) {
                @SuppressWarnings("unchecked")
                Map<String, Double> metrics = (Map<String, Double>) nodeInfo.get().get("metrics");
                state.getMetrics().clear();
                state.getMetrics().putAll(metrics);
            }
            
            // Update size based on importance
            if (nodeInfo.get().containsKey("importance")) {
                Object importanceObj = nodeInfo.get().get("importance");
                if (importanceObj instanceof Number) {
                    double importance = ((Number) importanceObj).doubleValue();
                    state.setSize(nodeSizeBaseScale * (1 + importance));
                }
            }
            
            // Update color based on activity
            if (nodeInfo.get().containsKey("active")) {
                boolean active = Boolean.TRUE.equals(nodeInfo.get().get("active"));
                if (!active) {
                    state.setColor("#cccccc"); // Gray for inactive
                }
            }
        }
    }
    
    /**
     * Adds a new node to the visualization.
     * 
     * @param nodeId The node ID
     * @param activeNodes Set of all active node IDs
     * @param contextId The context ID for boundary enforcement
     */
    private void addNewNode(String nodeId, Set<String> activeNodes, String contextId) {
        // Skip if this node is not visible due to boundary constraints
        if (!boundary.canInformationPass(nodeId, contextId)) {
            return;
        }
        
        // Find average position of connected nodes
        Set<String> connectedNodes = getConnectedNodes(nodeId, contextId);
        connectedNodes.retainAll(nodeStates.keySet());
        
        double avgX = 0;
        double avgY = 0;
        boolean hasConnections = false;
        
        if (!connectedNodes.isEmpty()) {
            for (String connectedId : connectedNodes) {
                NodeVisualizationState connectedState = nodeStates.get(connectedId);
                avgX += connectedState.getX();
                avgY += connectedState.getY();
            }
            avgX /= connectedNodes.size();
            avgY /= connectedNodes.size();
            hasConnections = true;
        } else {
            // If no connections, place at origin with random offset
            avgX = (Math.random() - 0.5) * 100;
            avgY = (Math.random() - 0.5) * 100;
        }
        
        // Create node state with default values
        double size = nodeSizeBaseScale;
        String color = "#3366cc"; // Default blue color
        
        NodeVisualizationState state = new NodeVisualizationState(nodeId, avgX, avgY, size, color);
        
        // Add metadata about the node
        Optional<Map<String, Object>> nodeInfo = metaAwarenessNetwork.getNodeInfo(nodeId, contextId);
        if (nodeInfo.isPresent()) {
            state.getMetadata().putAll(nodeInfo.get());
            
            // Adjust node size based on importance/activity
            if (nodeInfo.get().containsKey("importance")) {
                Object importanceObj = nodeInfo.get().get("importance");
                if (importanceObj instanceof Number) {
                    double importance = ((Number) importanceObj).doubleValue();
                    state.setSize(nodeSizeBaseScale * (1 + importance));
                }
            }
            
            // Set node color based on type
            if (nodeInfo.get().containsKey("type")) {
                String type = nodeInfo.get().get("type").toString();
                switch (type) {
                    case "processor":
                        state.setColor("#3366cc"); // Blue
                        break;
                    case "storage":
                        state.setColor("#33cc33"); // Green
                        break;
                    case "gateway":
                        state.setColor("#cc3333"); // Red
                        break;
                    case "analyzer":
                        state.setColor("#cc33cc"); // Purple
                        break;
                    default:
                        state.setColor("#999999"); // Gray for unknown
                }
            }
        }
        
        // Store the node state
        nodeStates.put(nodeId, state);
    }
    
    /**
     * Gets the set of nodes connected to a specific node.
     * 
     * @param nodeId The node ID
     * @param contextId The context ID for boundary enforcement
     * @return Set of connected node IDs
     */
    private Set<String> getConnectedNodes(String nodeId, String contextId) {
        Set<String> connectedNodes = new HashSet<>();
        
        // Check connections from node discovery
        Map<String, Double> connections = getNodeConnections(nodeId, contextId);
        connectedNodes.addAll(connections.keySet());
        
        // Also find nodes that connect to this node
        for (EdgeVisualizationState edge : edgeStates.values()) {
            if (edge.getTargetNodeId().equals(nodeId)) {
                connectedNodes.add(edge.getSourceNodeId());
            }
        }
        
        return connectedNodes;
    }
    
    /**
     * Updates edge states based on current node connections.
     * 
     * @param activeNodes Set of active node IDs
     * @param contextId The context ID for boundary enforcement
     */
    private void updateEdgeStates(Set<String> activeNodes, String contextId) {
        // Track current edges to identify those to remove
        Set<String> currentEdgeIds = new HashSet<>(edgeStates.keySet());
        Set<String> processedEdgeIds = new HashSet<>();
        
        // For each active node, update its connections
        for (String nodeId : activeNodes) {
            if (!nodeStates.containsKey(nodeId)) {
                continue; // Skip if node not in visualization
            }
            
            // Get connections for this node
            Map<String, Double> connections = getNodeConnections(nodeId, contextId);
            
            for (Map.Entry<String, Double> connection : connections.entrySet()) {
                String targetNodeId = connection.getKey();
                
                // Skip if target node not in visualization
                if (!nodeStates.containsKey(targetNodeId)) {
                    continue;
                }
                
                // Create a unique edge ID
                String edgeId = nodeId + "->" + targetNodeId;
                processedEdgeIds.add(edgeId);
                
                // Update or create edge
                if (edgeStates.containsKey(edgeId)) {
                    // Update existing edge
                    EdgeVisualizationState edge = edgeStates.get(edgeId);
                    
                    // Update thickness based on connection strength
                    double strength = connection.getValue();
                    double thickness = Math.max(1.0, strength * edgeThicknessMultiplier);
                    edge.setThickness(thickness);
                    
                    // Update color based on strength
                    edge.setColor(getColorForStrength(strength));
                    
                    // Update flow rate
                    edge.setInformationFlowRate(strength);
                } else {
                    // Create new edge
                    double strength = connection.getValue();
                    double thickness = Math.max(1.0, strength * edgeThicknessMultiplier);
                    String color = getColorForStrength(strength);
                    String style = "solid"; // Default solid line
                    
                    EdgeVisualizationState edge = new EdgeVisualizationState(
                            edgeId, nodeId, targetNodeId, thickness, color, style);
                    
                    edge.setInformationFlowRate(strength);
                    
                    edgeStates.put(edgeId, edge);
                }
            }
        }
        
        // Remove edges that no longer exist
        Set<String> edgesToRemove = new HashSet<>(currentEdgeIds);
        edgesToRemove.removeAll(processedEdgeIds);
        
        for (String edgeId : edgesToRemove) {
            edgeStates.remove(edgeId);
        }
    }
    
    /**
     * Renders the network visualization at a specific scale level.
     * 
     * @param level The scale level (macro, meso, or micro)
     * @param contextId The context ID for boundary enforcement
     * @return A map containing the visualization data at the specified level
     */
    public Map<String, Object> renderVisualization(String level, String contextId) {
        Map<String, Object> visualizationData = new HashMap<>();
        
        // Set scale parameters based on level
        double nodeSizeMultiplier;
        double edgeThicknessMultiplier;
        boolean includeNodeDetails;
        boolean includeEdgeDetails;
        int maxNodesShown;
        
        switch (level.toLowerCase()) {
            case "macro":
                // High-level overview
                nodeSizeMultiplier = 0.8;
                edgeThicknessMultiplier = 0.7;
                includeNodeDetails = false;
                includeEdgeDetails = false;
                maxNodesShown = 50;
                break;
                
            case "meso":
                // Medium detail
                nodeSizeMultiplier = 1.0;
                edgeThicknessMultiplier = 1.0;
                includeNodeDetails = true;
                includeEdgeDetails = false;
                maxNodesShown = 200;
                break;
                
            case "micro":
                // Full detail
                nodeSizeMultiplier = 1.2;
                edgeThicknessMultiplier = 1.5;
                includeNodeDetails = true;
                includeEdgeDetails = true;
                maxNodesShown = Integer.MAX_VALUE;
                break;
                
            default:
                throw new FractalBrowserException("Invalid visualization level: " + level);
        }
        
        // Filter and transform nodes
        List<Map<String, Object>> nodes = nodeStates.values().stream()
                .filter(node -> boundary.canInformationPass(node.getNodeId(), contextId))
                .filter(NodeVisualizationState::isVisible)
                .limit(maxNodesShown)
                .map(node -> {
                    Map<String, Object> nodeData = new HashMap<>();
                    nodeData.put("id", node.getNodeId());
                    nodeData.put("x", node.getX());
                    nodeData.put("y", node.getY());
                    nodeData.put("size", node.getSize() * nodeSizeMultiplier);
                    nodeData.put("color", node.getColor());
                    
                    if (includeNodeDetails) {
                        nodeData.put("metadata", node.getMetadata());
                        nodeData.put("metrics", node.getMetrics());
                    }
                    
                    return nodeData;
                })
                .collect(Collectors.toList());
        
        // Filter and transform edges
        List<Map<String, Object>> edges = edgeStates.values().stream()
                .filter(edge -> edge.isVisible())
                .filter(edge -> {
                    // Only include edges between visible nodes
                    return nodeStates.containsKey(edge.getSourceNodeId()) &&
                           nodeStates.containsKey(edge.getTargetNodeId()) &&
                           nodeStates.get(edge.getSourceNodeId()).isVisible() &&
                           nodeStates.get(edge.getTargetNodeId()).isVisible() &&
                           boundary.canInformationPass(edge.getSourceNodeId(), contextId) &&
                           boundary.canInformationPass(edge.getTargetNodeId(), contextId);
                })
                .map(edge -> {
                    Map<String, Object> edgeData = new HashMap<>();
                    edgeData.put("id", edge.getEdgeId());
                    edgeData.put("source", edge.getSourceNodeId());
                    edgeData.put("target", edge.getTargetNodeId());
                    edgeData.put("thickness", edge.getThickness() * edgeThicknessMultiplier);
                    edgeData.put("color", edge.getColor());
                    edgeData.put("style", edge.getStyle());
                    
                    if (includeEdgeDetails) {
                        edgeData.put("flowRate", edge.getInformationFlowRate());
                        edgeData.put("metadata", edge.getMetadata());
                    }
                    
                    return edgeData;
                })
                .collect(Collectors.toList());
        
        // Add metadata about the visualization
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("level", level);
        metadata.put("timestamp", System.currentTimeMillis());
        metadata.put("nodeCount", nodes.size());
        metadata.put("edgeCount", edges.size());
        
        // Compile final visualization data
        visualizationData.put("nodes", nodes);
        visualizationData.put("edges", edges);
        visualizationData.put("metadata", metadata);
        
        return visualizationData;
    }
    
    /**
     * Applies a filter to the visualization to show only specific nodes and their connections.
     * 
     * @param filter A predicate to filter nodes
     * @param includeConnections Whether to include connections to filtered-out nodes
     * @param contextId The context ID for boundary enforcement
     * @return The number of visible nodes after filtering
     */
    public int applyVisualizationFilter(
            java.util.function.Predicate<NodeVisualizationState> filter,
            boolean includeConnections,
            String contextId) {
        
        // Apply filter to nodes
        for (NodeVisualizationState node : nodeStates.values()) {
            boolean visible = filter.test(node) && boundary.canInformationPass(node.getNodeId(), contextId);
            node.setVisible(visible);
        }
        
        // Apply filter to edges
        for (EdgeVisualizationState edge : edgeStates.values()) {
            boolean sourceVisible = nodeStates.containsKey(edge.getSourceNodeId()) &&
                    nodeStates.get(edge.getSourceNodeId()).isVisible();
            boolean targetVisible = nodeStates.containsKey(edge.getTargetNodeId()) &&
                    nodeStates.get(edge.getTargetNodeId()).isVisible();
            
            if (includeConnections) {
                // Show edge if either source or target is visible
                edge.setVisible(sourceVisible || targetVisible);
            } else {
                // Show edge only if both source and target are visible
                edge.setVisible(sourceVisible && targetVisible);
            }
        }
        
        // Return count of visible nodes
        return (int) nodeStates.values().stream()
                .filter(NodeVisualizationState::isVisible)
                .count();
    }
    
    /**
     * Resets all visualization filters.
     * 
     * @param contextId The context ID for boundary enforcement
     * @return The number of visible nodes after reset
     */
    public int resetVisualizationFilters(String contextId) {
        // Make all nodes visible (subject to boundary constraints)
        for (NodeVisualizationState node : nodeStates.values()) {
            node.setVisible(boundary.canInformationPass(node.getNodeId(), contextId));
        }
        
        // Make all edges visible if both endpoints are visible
        for (EdgeVisualizationState edge : edgeStates.values()) {
            boolean sourceVisible = nodeStates.containsKey(edge.getSourceNodeId()) &&
                    nodeStates.get(edge.getSourceNodeId()).isVisible();
            boolean targetVisible = nodeStates.containsKey(edge.getTargetNodeId()) &&
                    nodeStates.get(edge.getTargetNodeId()).isVisible();
            
            edge.setVisible(sourceVisible && targetVisible);
        }
        
        // Return count of visible nodes
        return (int) nodeStates.values().stream()
                .filter(NodeVisualizationState::isVisible)
                .count();
    }
    
    /**
     * Analyzes the network topology to identify important structural properties.
     * 
     * @param contextId The context ID for boundary enforcement
     * @return A map of topology analysis metrics
     */
    public Map<String, Object> analyzeNetworkTopology(String contextId) {
        Map<String, Object> analysis = new HashMap<>();
        
        // Count visible nodes and edges
        int visibleNodeCount = (int) nodeStates.values().stream()
                .filter(NodeVisualizationState::isVisible)
                .filter(n -> boundary.canInformationPass(n.getNodeId(), contextId))
                .count();
        
        int visibleEdgeCount = (int) edgeStates.values().stream()
                .filter(EdgeVisualizationState::isVisible)
                .filter(e -> {
                    return boundary.canInformationPass(e.getSourceNodeId(), contextId) &&
                           boundary.canInformationPass(e.getTargetNodeId(), contextId);
                })
                .count();
        
        analysis.put("nodeCount", visibleNodeCount);
        analysis.put("edgeCount", visibleEdgeCount);
        
        // Calculate network density
        double density = 0;
        if (visibleNodeCount > 1) {
            int maxEdges = visibleNodeCount * (visibleNodeCount - 1) / 2;
            density = (double) visibleEdgeCount / maxEdges;
        }
        analysis.put("density", density);
        
        // Calculate degree distribution
        Map<String, Integer> nodeDegrees = new HashMap<>();
        
        for (NodeVisualizationState node : nodeStates.values()) {
            if (node.isVisible() && boundary.canInformationPass(node.getNodeId(), contextId)) {
                int degree = 0;
                for (EdgeVisualizationState edge : edgeStates.values()) {
                    if (edge.isVisible()) {
                        if (edge.getSourceNodeId().equals(node.getNodeId()) ||
                                edge.getTargetNodeId().equals(node.getNodeId())) {
                            degree++;
                        }
                    }
                }
                nodeDegrees.put(node.getNodeId(), degree);
            }
        }
        
        // Calculate average degree
        double avgDegree = nodeDegrees.values().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
        
        analysis.put("averageDegree", avgDegree);
        analysis.put("degreeDistribution", nodeDegrees);
        
        // Identify central nodes (high degree)
        List<String> centralNodes = nodeDegrees.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        analysis.put("centralNodes", centralNodes);
        
        // Calculate average edge flow rate
        double avgFlowRate = edgeStates.values().stream()
                .filter(EdgeVisualizationState::isVisible)
                .filter(e -> {
                    return boundary.canInformationPass(e.getSourceNodeId(), contextId) &&
                           boundary.canInformationPass(e.getTargetNodeId(), contextId);
                })
                .mapToDouble(EdgeVisualizationState::getInformationFlowRate)
                .average()
                .orElse(0);
        
        analysis.put("averageFlowRate", avgFlowRate);
        
        return analysis;
    }
    
    /**
     * Gets the current state of a node in the visualization.
     * 
     * @param nodeId The node ID
     * @param contextId The context ID for boundary enforcement
     * @return An Optional containing the node state if found and accessible
     */
    public Optional<NodeVisualizationState> getNodeState(String nodeId, String contextId) {
        if (!nodeStates.containsKey(nodeId)) {
            return Optional.empty();
        }
        
        // Check if node can pass information boundary
        if (!boundary.canInformationPass(nodeId, contextId)) {
            return Optional.empty();
        }
        
        return Optional.of(nodeStates.get(nodeId));
    }
    
    /**
     * Gets the current state of an edge in the visualization.
     * 
     * @param edgeId The edge ID
     * @param contextId The context ID for boundary enforcement
     * @return An Optional containing the edge state if found and accessible
     */
    public Optional<EdgeVisualizationState> getEdgeState(String edgeId, String contextId) {
        if (!edgeStates.containsKey(edgeId)) {
            return Optional.empty();
        }
        
        EdgeVisualizationState edge = edgeStates.get(edgeId);
        
        // Check if both source and target nodes can pass information boundary
        if (!boundary.canInformationPass(edge.getSourceNodeId(), contextId) ||
                !boundary.canInformationPass(edge.getTargetNodeId(), contextId)) {
            return Optional.empty();
        }
        
        return Optional.of(edge);
    }
}