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
import java.time.Instant;

import com.fractal.browser.collective.boundaries.InformationBoundary;
import com.fractal.browser.collective.memory.DistributedInsightRepository;
import com.fractal.browser.collective.memory.TemporalIndexing;
import com.fractal.browser.collective.processing.EmergentPatternDetector;
import com.fractal.browser.exceptions.FractalBrowserException;
import com.fractal.browser.model.Pattern;

/**
 * InsightFlowMapper visualizes how insights propagate, transform, and connect throughout 
 * the collective system. This class generates flow maps that show the journey of insights
 * through various nodes and transformations.
 * 
 * The visualization uses fractal mapping techniques to represent insight propagation at
 * multiple scales, from individual transformations to system-wide knowledge flows.
 */
public class InsightFlowMapper {

    // Core dependencies
    private final DistributedInsightRepository insightRepository;
    private final TemporalIndexing temporalIndexing;
    private final EmergentPatternDetector patternDetector;
    private final InformationBoundary boundary;
    
    // Visualization state
    private final Map<String, FlowNode> flowNodes;
    private final Map<String, FlowEdge> flowEdges;
    private final Map<String, FlowMap> flowMaps;
    
    // Visualization configuration
    private final int maxFlowMapSize;
    private final int defaultTimeWindowMs;
    private final double edgeThicknessMultiplier;
    
    /**
     * Represents a node in the insight flow map.
     */
    public static class FlowNode {
        private final String nodeId;
        private final String type;
        private final Map<String, Object> attributes;
        private final Instant timestamp;
        private double x;
        private double y;
        private double size;
        private String color;
        private final Map<String, Object> metadata;
        private final Set<String> relatedInsights;
        private final Set<String> relatedPatterns;
        
        /**
         * Creates a new flow node.
         */
        public FlowNode(
                String nodeId,
                String type,
                Map<String, Object> attributes,
                Instant timestamp) {
            
            this.nodeId = nodeId;
            this.type = type;
            this.attributes = new HashMap<>(attributes);
            this.timestamp = timestamp;
            this.x = 0;
            this.y = 0;
            this.size = 10;
            this.color = "#3366cc";
            this.metadata = new HashMap<>();
            this.relatedInsights = new HashSet<>();
            this.relatedPatterns = new HashSet<>();
        }
        
        // Getters and setters
        public String getNodeId() { return nodeId; }
        public String getType() { return type; }
        public Map<String, Object> getAttributes() { return attributes; }
        public Instant getTimestamp() { return timestamp; }
        public double getX() { return x; }
        public void setX(double x) { this.x = x; }
        public double getY() { return y; }
        public void setY(double y) { this.y = y; }
        public double getSize() { return size; }
        public void setSize(double size) { this.size = size; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        public Map<String, Object> getMetadata() { return metadata; }
        public Set<String> getRelatedInsights() { return relatedInsights; }
        public Set<String> getRelatedPatterns() { return relatedPatterns; }
    }
    
    /**
     * Represents an edge in the insight flow map.
     */
    public static class FlowEdge {
        private final String edgeId;
        private final String sourceNodeId;
        private final String targetNodeId;
        private final String relationType;
        private final Instant timestamp;
        private double weight;
        private double thickness;
        private String color;
        private String style;
        private final Map<String, Object> metadata;
        
        /**
         * Creates a new flow edge.
         */
        public FlowEdge(
                String edgeId,
                String sourceNodeId,
                String targetNodeId,
                String relationType,
                Instant timestamp) {
            
            this.edgeId = edgeId;
            this.sourceNodeId = sourceNodeId;
            this.targetNodeId = targetNodeId;
            this.relationType = relationType;
            this.timestamp = timestamp;
            this.weight = 1.0;
            this.thickness = 1.0;
            this.color = "#999999";
            this.style = "solid";
            this.metadata = new HashMap<>();
        }
        
        // Getters and setters
        public String getEdgeId() { return edgeId; }
        public String getSourceNodeId() { return sourceNodeId; }
        public String getTargetNodeId() { return targetNodeId; }
        public String getRelationType() { return relationType; }
        public Instant getTimestamp() { return timestamp; }
        public double getWeight() { return weight; }
        public void setWeight(double weight) { this.weight = weight; }
        public double getThickness() { return thickness; }
        public void setThickness(double thickness) { this.thickness = thickness; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        public String getStyle() { return style; }
        public void setStyle(String style) { this.style = style; }
        public Map<String, Object> getMetadata() { return metadata; }
    }
    
    /**
     * Represents a complete flow map visualization.
     */
    public static class FlowMap {
        private final String mapId;
        private final String title;
        private final String description;
        private final long creationTime;
        private final Map<String, FlowNode> nodes;
        private final Map<String, FlowEdge> edges;
        private final long timeWindowMs;
        private final Map<String, Object> metadata;
        
        /**
         * Creates a new flow map.
         */
        public FlowMap(
                String mapId,
                String title,
                String description,
                Map<String, FlowNode> nodes,
                Map<String, FlowEdge> edges,
                long timeWindowMs) {
            
            this.mapId = mapId;
            this.title = title;
            this.description = description;
            this.creationTime = System.currentTimeMillis();
            
            // Create deep copies of nodes and edges
            this.nodes = new HashMap<>();
            for (Map.Entry<String, FlowNode> entry : nodes.entrySet()) {
                this.nodes.put(entry.getKey(), copyNode(entry.getValue()));
            }
            
            this.edges = new HashMap<>();
            for (Map.Entry<String, FlowEdge> entry : edges.entrySet()) {
                this.edges.put(entry.getKey(), copyEdge(entry.getValue()));
            }
            
            this.timeWindowMs = timeWindowMs;
            this.metadata = new HashMap<>();
        }
        
        // Create a deep copy of a node
        private FlowNode copyNode(FlowNode original) {
            FlowNode copy = new FlowNode(
                    original.getNodeId(),
                    original.getType(),
                    new HashMap<>(original.getAttributes()),
                    original.getTimestamp());
            
            copy.setX(original.getX());
            copy.setY(original.getY());
            copy.setSize(original.getSize());
            copy.setColor(original.getColor());
            copy.getMetadata().putAll(original.getMetadata());
            copy.getRelatedInsights().addAll(original.getRelatedInsights());
            copy.getRelatedPatterns().addAll(original.getRelatedPatterns());
            
            return copy;
        }
        
        // Create a deep copy of an edge
        private FlowEdge copyEdge(FlowEdge original) {
            FlowEdge copy = new FlowEdge(
                    original.getEdgeId(),
                    original.getSourceNodeId(),
                    original.getTargetNodeId(),
                    original.getRelationType(),
                    original.getTimestamp());
            
            copy.setWeight(original.getWeight());
            copy.setThickness(original.getThickness());
            copy.setColor(original.getColor());
            copy.setStyle(original.getStyle());
            copy.getMetadata().putAll(original.getMetadata());
            
            return copy;
        }
        
        // Getters
        public String getMapId() { return mapId; }
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public long getCreationTime() { return creationTime; }
        public Map<String, FlowNode> getNodes() { return nodes; }
        public Map<String, FlowEdge> getEdges() { return edges; }
        public long getTimeWindowMs() { return timeWindowMs; }
        public Map<String, Object> getMetadata() { return metadata; }
    }
    
    /**
     * Creates a new InsightFlowMapper instance.
     * 
     * @param insightRepository Repository for storing and retrieving insights
     * @param temporalIndexing Service for temporal pattern analysis
     * @param patternDetector Service for detecting patterns
     * @param boundary Information boundary for enforcing access controls
     * @param maxFlowMapSize Maximum number of nodes in a flow map
     * @param defaultTimeWindowMs Default time window for flow map generation
     * @param edgeThicknessMultiplier Multiplier for edge thickness based on weight
     */
    public InsightFlowMapper(
            DistributedInsightRepository insightRepository,
            TemporalIndexing temporalIndexing,
            EmergentPatternDetector patternDetector,
            InformationBoundary boundary,
            int maxFlowMapSize,
            int defaultTimeWindowMs,
            double edgeThicknessMultiplier) {
        
        this.insightRepository = insightRepository;
        this.temporalIndexing = temporalIndexing;
        this.patternDetector = patternDetector;
        this.boundary = boundary;
        
        this.maxFlowMapSize = maxFlowMapSize;
        this.defaultTimeWindowMs = defaultTimeWindowMs;
        this.edgeThicknessMultiplier = edgeThicknessMultiplier;
        
        this.flowNodes = new ConcurrentHashMap<>();
        this.flowEdges = new ConcurrentHashMap<>();
        this.flowMaps = new ConcurrentHashMap<>();
    }
    
    /**
     * Generates a flow map visualization for all insights within a time window.
     * 
     * @param timeWindowMs Time window in milliseconds
     * @param contextId The context ID for boundary enforcement
     * @return The ID of the generated flow map
     */
    public String generateFlowMap(long timeWindowMs, String contextId) {
        try {
            // Clear existing nodes and edges
            flowNodes.clear();
            flowEdges.clear();
            
            // Get insights within the time window
            Instant endTime = Instant.now();
            Instant startTime = endTime.minusMillis(timeWindowMs);
            
            List<String> insightIds = temporalIndexing.findItemsInTimeRange(
                    startTime, endTime, contextId);
            
            if (insightIds.isEmpty()) {
                throw new FractalBrowserException("No insights found within the specified time window");
            }
            
            // Limit to maximum flow map size if needed
            if (insightIds.size() > maxFlowMapSize) {
                insightIds = insightIds.subList(0, maxFlowMapSize);
            }
            
            // Get insights and create flow nodes
            List<Map<String, Object>> insights = new ArrayList<>();
            
            for (String insightId : insightIds) {
                Optional<Map<String, Object>> insight = insightRepository.getInsight(insightId, contextId);
                if (insight.isPresent()) {
                    insights.add(insight.get());
                    createFlowNodeFromInsight(insight.get(), contextId);
                }
            }
            
            // Find and create connections between insights
            createFlowEdges(insights, contextId);
            
            // Apply patterns for additional connections
            applyPatternsToFlowMap(timeWindowMs, contextId);
            
            // Apply layout algorithm to position nodes
            applyTimeBasedLayout();
            
            // Create the flow map
            String mapId = UUID.randomUUID().toString();
            String title = "Insight Flow: " + startTime + " to " + endTime;
            String description = "Visualization of insight flow patterns over a " + 
                    (timeWindowMs / (60 * 1000)) + " minute window";
            
            FlowMap flowMap = new FlowMap(mapId, title, description, 
                    flowNodes, flowEdges, timeWindowMs);
            
            // Add metadata
            flowMap.getMetadata().put("nodeCount", flowNodes.size());
            flowMap.getMetadata().put("edgeCount", flowEdges.size());
            flowMap.getMetadata().put("startTime", startTime.toEpochMilli());
            flowMap.getMetadata().put("endTime", endTime.toEpochMilli());
            
            // Store the flow map
            flowMaps.put(mapId, flowMap);
            
            return mapId;
            
        } catch (Exception e) {
            throw new FractalBrowserException("Failed to generate flow map: " + e.getMessage(), e);
        }
    }
    
    /**
     * Creates a flow node from an insight.
     * 
     * @param insight The insight data
     * @param contextId The context ID for boundary enforcement
     * @return The created flow node
     */
    private FlowNode createFlowNodeFromInsight(Map<String, Object> insight, String contextId) {
        String insightId = insight.getOrDefault("_id", UUID.randomUUID().toString()).toString();
        
        // Skip if already exists
        if (flowNodes.containsKey(insightId)) {
            return flowNodes.get(insightId);
        }
        
        // Extract node attributes
        String type = insight.getOrDefault("type", "insight").toString();
        
        Map<String, Object> attributes = new HashMap<>();
        for (Map.Entry<String, Object> entry : insight.entrySet()) {
            // Include all meaningful attributes (skip internal ones)
            if (!entry.getKey().startsWith("_") && !entry.getKey().equals("content")) {
                attributes.put(entry.getKey(), entry.getValue());
            }
        }
        
        // Extract timestamp
        Instant timestamp;
        if (insight.containsKey("_timestamp")) {
            Object timestampObj = insight.get("_timestamp");
            if (timestampObj instanceof Number) {
                timestamp = Instant.ofEpochMilli(((Number) timestampObj).longValue());
            } else {
                timestamp = Instant.now();
            }
        } else {
            timestamp = Instant.now();
        }
        
        // Create the flow node
        FlowNode node = new FlowNode(insightId, type, attributes, timestamp);
        
        // Set basic visual properties
        setNodeVisualProperties(node);
        
        // Add to related insights
        node.getRelatedInsights().add(insightId);
        
        // Store in flow nodes
        flowNodes.put(insightId, node);
        
        return node;
    }
    
    /**
     * Sets visual properties for a flow node based on its type and attributes.
     * 
     * @param node The flow node to set properties for
     */
    private void setNodeVisualProperties(FlowNode node) {
        // Set size based on attributes
        if (node.getAttributes().containsKey("importance")) {
            Object importanceObj = node.getAttributes().get("importance");
            if (importanceObj instanceof Number) {
                double importance = ((Number) importanceObj).doubleValue();
                node.setSize(10 + importance * 20);
            }
        }
        
        // Set color based on type
        switch (node.getType().toLowerCase()) {
            case "insight":
                node.setColor("#3366cc"); // Blue
                break;
            case "pattern":
                node.setColor("#33cc33"); // Green
                break;
            case "cluster":
                node.setColor("#cc3333"); // Red
                break;
            case "meta":
                node.setColor("#cc33cc"); // Purple
                break;
            default:
                node.setColor("#999999"); // Gray
        }
    }
    
    /**
     * Creates flow edges between related insights.
     * 
     * @param insights List of insights
     * @param contextId The context ID for boundary enforcement
     */
    private void createFlowEdges(List<Map<String, Object>> insights, String contextId) {
        // Track created edges to avoid duplicates
        Set<String> createdEdgeKeys = new HashSet<>();
        
        // Sort insights by timestamp for chronological analysis
        insights.sort((i1, i2) -> {
            long t1 = getInsightTimestamp(i1);
            long t2 = getInsightTimestamp(i2);
            return Long.compare(t1, t2);
        });
        
        // For each insight, find related insights based on different criteria
        for (int i = 0; i < insights.size(); i++) {
            Map<String, Object> insight = insights.get(i);
            String insightId = insight.getOrDefault("_id", "").toString();
            
            if (!flowNodes.containsKey(insightId)) {
                continue;
            }
            
            // 1. Temporal sequence - connect to previous insights in time
            if (i > 0) {
                for (int j = Math.max(0, i - 5); j < i; j++) {
                    Map<String, Object> previousInsight = insights.get(j);
                    String previousId = previousInsight.getOrDefault("_id", "").toString();
                    
                    if (flowNodes.containsKey(previousId)) {
                        createFlowEdge(previousId, insightId, "sequence", 0.5, contextId);
                    }
                }
            }
            
            // 2. Shared tags/attributes - connect to insights with similar attributes
            for (int j = 0; j < insights.size(); j++) {
                if (i == j) continue;
                
                Map<String, Object> otherInsight = insights.get(j);
                String otherId = otherInsight.getOrDefault("_id", "").toString();
                
                if (!flowNodes.containsKey(otherId)) {
                    continue;
                }
                
                double similarity = calculateInsightSimilarity(insight, otherInsight);
                if (similarity > 0.6) {
                    createFlowEdge(insightId, otherId, "similarity", similarity, contextId);
                }
            }
            
            // 3. Referenced insights - connect if one references the other
            if (insight.containsKey("references")) {
                @SuppressWarnings("unchecked")
                List<String> references = (List<String>) insight.get("references");
                
                for (String refId : references) {
                    if (flowNodes.containsKey(refId)) {
                        createFlowEdge(insightId, refId, "reference", 0.9, contextId);
                    }
                }
            }
            
            // 4. Derived insights - connect if derived from another
            if (insight.containsKey("derivedFrom")) {
                @SuppressWarnings("unchecked")
                List<String> sources = (List<String>) insight.get("derivedFrom");
                
                for (String sourceId : sources) {
                    if (flowNodes.containsKey(sourceId)) {
                        createFlowEdge(sourceId, insightId, "derivation", 1.0, contextId);
                    }
                }
            }
        }
    }
    
    /**
     * Creates a flow edge between two nodes.
     * 
     * @param sourceId Source node ID
     * @param targetId Target node ID
     * @param relationType Type of relationship
     * @param weight Edge weight (0.0-1.0)
     * @param contextId The context ID for boundary enforcement
     * @return The created flow edge
     */
    private FlowEdge createFlowEdge(
            String sourceId, 
            String targetId, 
            String relationType, 
            double weight,
            String contextId) {
        
        // Skip if either node doesn't exist
        if (!flowNodes.containsKey(sourceId) || !flowNodes.containsKey(targetId)) {
            return null;
        }
        
        // Skip if either node is not accessible
        if (!boundary.canInformationPass(sourceId, contextId) || 
                !boundary.canInformationPass(targetId, contextId)) {
            return null;
        }
        
        // Create a unique edge ID
        String edgeId = sourceId + "->" + targetId + ":" + relationType;
        
        // Skip if already exists
        if (flowEdges.containsKey(edgeId)) {
            FlowEdge existingEdge = flowEdges.get(edgeId);
            
            // Update weight if greater
            if (weight > existingEdge.getWeight()) {
                existingEdge.setWeight(weight);
                existingEdge.setThickness(Math.max(1.0, weight * edgeThicknessMultiplier));
            }
            
            return existingEdge;
        }
        
        // Get source and target nodes
        FlowNode sourceNode = flowNodes.get(sourceId);
        FlowNode targetNode = flowNodes.get(targetId);
        
        // Determine timestamp (use latest of the two nodes)
        Instant timestamp = sourceNode.getTimestamp().isAfter(targetNode.getTimestamp()) ?
                sourceNode.getTimestamp() : targetNode.getTimestamp();
        
        // Create the edge
        FlowEdge edge = new FlowEdge(edgeId, sourceId, targetId, relationType, timestamp);
        
        // Set visual properties
        edge.setWeight(weight);
        edge.setThickness(Math.max(1.0, weight * edgeThicknessMultiplier));
        
        // Set color based on relation type
        switch (relationType.toLowerCase()) {
            case "sequence":
                edge.setColor("#999999"); // Gray
                edge.setStyle("dashed");
                break;
            case "similarity":
                edge.setColor("#3366cc"); // Blue
                edge.setStyle("dotted");
                break;
            case "reference":
                edge.setColor("#33cc33"); // Green
                edge.setStyle("solid");
                break;
            case "derivation":
                edge.setColor("#cc3333"); // Red
                edge.setStyle("solid");
                break;
            case "pattern":
                edge.setColor("#cc33cc"); // Purple
                edge.setStyle("solid");
                break;
            default:
                edge.setColor("#999999"); // Gray
                edge.setStyle("solid");
        }
        
        // Store the edge
        flowEdges.put(edgeId, edge);
        
        return edge;
    }
    
    /**
     * Gets the timestamp of an insight as a long value.
     * 
     * @param insight The insight data
     * @return Timestamp in milliseconds
     */
    private long getInsightTimestamp(Map<String, Object> insight) {
        if (insight.containsKey("_timestamp")) {
            Object timestampObj = insight.get("_timestamp");
            if (timestampObj instanceof Number) {
                return ((Number) timestampObj).longValue();
            }
        }
        return 0;
    }
    
    /**
     * Calculates similarity between two insights based on shared attributes.
     * 
     * @param insight1 First insight
     * @param insight2 Second insight
     * @return Similarity score (0.0-1.0)
     */
    private double calculateInsightSimilarity(Map<String, Object> insight1, Map<String, Object> insight2) {
        Set<String> attrs1 = insight1.keySet().stream()
                .filter(k -> !k.startsWith("_") && !k.equals("content"))
                .collect(Collectors.toSet());
        
        Set<String> attrs2 = insight2.keySet().stream()
                .filter(k -> !k.startsWith("_") && !k.equals("content"))
                .collect(Collectors.toSet());
        
        // Count matching attribute keys
        int matchingKeys = 0;
        for (String key : attrs1) {
            if (attrs2.contains(key)) {
                matchingKeys++;
                
                // Bonus for matching values
                Object val1 = insight1.get(key);
                Object val2 = insight2.get(key);
                
                if (val1 != null && val1.equals(val2)) {
                    matchingKeys++;
                }
            }
        }
        
        // Calculate Jaccard similarity
        int totalAttrs = attrs1.size() + attrs2.size() - matchingKeys;
        if (totalAttrs == 0) {
            return 0.0;
        }
        
        return (double) matchingKeys / totalAttrs;
    }
    
    /**
     * Applies patterns to enhance the flow map.
     * 
     * @param timeWindowMs Time window in milliseconds
     * @param contextId The context ID for boundary enforcement
     */
    private void applyPatternsToFlowMap(long timeWindowMs, String contextId) {
        // Detect patterns within the time window
        List<Pattern> patterns = patternDetector.detectEmergentPatterns(timeWindowMs, contextId);
        
        // For each pattern, create a node and connect to related insights
        for (Pattern pattern : patterns) {
            String patternId = pattern.getId();
            
            // Skip if not accessible
            if (!boundary.canInformationPass(patternId, contextId)) {
                continue;
            }
            
            // Create pattern node
            Map<String, Object> attributes = new HashMap<>(pattern.getAttributes());
            attributes.put("description", pattern.getDescription());
            
            FlowNode patternNode = new FlowNode(
                    patternId,
                    "pattern_" + pattern.getType(),
                    attributes,
                    Instant.ofEpochMilli(pattern.getTimestamp()));
            
            // Set visual properties
            patternNode.setSize(15);
            patternNode.setColor("#33cc33"); // Green for patterns
            
            // Extract related insight IDs from pattern
            Set<String> relatedInsightIds = extractInsightIdsFromPattern(pattern);
            
            // Connect to related insights
            for (String insightId : relatedInsightIds) {
                if (flowNodes.containsKey(insightId)) {
                    // Connect pattern to insight
                    createFlowEdge(patternId, insightId, "pattern", 0.8, contextId);
                    
                    // Add to related insights
                    patternNode.getRelatedInsights().add(insightId);
                    
                    // Add to related patterns of the insight node
                    flowNodes.get(insightId).getRelatedPatterns().add(patternId);
                }
            }
            
            // Connect meta-patterns to other patterns
            if (pattern.getType().equals("META")) {
                Map<String, Object> patternAttrs = pattern.getAttributes();
                
                if (patternAttrs.containsKey("pattern1Id") && patternAttrs.containsKey("pattern2Id")) {
                    String pattern1Id = patternAttrs.get("pattern1Id").toString();
                    String pattern2Id = patternAttrs.get("pattern2Id").toString();
                    
                    if (flowNodes.containsKey(pattern1Id)) {
                        createFlowEdge(patternId, pattern1Id, "meta_pattern", 0.9, contextId);
                        patternNode.getRelatedPatterns().add(pattern1Id);
                    }
                    
                    if (flowNodes.containsKey(pattern2Id)) {
                        createFlowEdge(patternId, pattern2Id, "meta_pattern", 0.9, contextId);
                        patternNode.getRelatedPatterns().add(pattern2Id);
                    }
                }
            }
            
            // Store the pattern node
            flowNodes.put(patternId, patternNode);
        }
    }
    
    /**
     * Extracts insight IDs from a pattern.
     * 
     * @param pattern The pattern
     * @return Set of insight IDs
     */
    private Set<String> extractInsightIdsFromPattern(Pattern pattern) {
        Set<String> insightIds = new HashSet<>();
        Map<String, Object> attributes = pattern.getAttributes();
        
        // Extract from various pattern attribute types
        if (attributes.containsKey("insightIds")) {
            @SuppressWarnings("unchecked")
            List<String> ids = (List<String>) attributes.get("insightIds");
            insightIds.addAll(ids);
        }
        
        if (attributes.containsKey("items")) {
            @SuppressWarnings("unchecked")
            List<String> items = (List<String>) attributes.get("items");
            insightIds.addAll(items);
        }
        
        if (attributes.containsKey("insightSequences")) {
            @SuppressWarnings("unchecked")
            List<List<String>> sequences = (List<List<String>>) attributes.get("insightSequences");
            
            for (List<String> sequence : sequences) {
                insightIds.addAll(sequence);
            }
        }
        
        if (attributes.containsKey("commonInsights")) {
            @SuppressWarnings("unchecked")
            List<String> commonInsights = (List<String>) attributes.get("commonInsights");
            insightIds.addAll(commonInsights);
        }
        
        return insightIds;
    }
    
    /**
     * Applies a time-based layout to position flow nodes.
     */
    private void applyTimeBasedLayout() {
        // Sort nodes by timestamp
        List<FlowNode> sortedNodes = flowNodes.values().stream()
                .sorted((n1, n2) -> n1.getTimestamp().compareTo(n2.getTimestamp()))
                .collect(Collectors.toList());
        
        // Group nodes by type
        Map<String, List<FlowNode>> nodesByType = sortedNodes.stream()
                .collect(Collectors.groupingBy(FlowNode::getType));
        
        // Calculate vertical positions for each type
        Map<String, Double> typeYPositions = new HashMap<>();
        double ySpacing = 100.0;
        int typeCount = nodesByType.size();
        
        int i = 0;
        for (String type : nodesByType.keySet()) {
            double y = (i - (typeCount - 1) / 2.0) * ySpacing;
            typeYPositions.put(type, y);
            i++;
        }
        
        // Calculate horizontal position based on timestamp
        if (!sortedNodes.isEmpty()) {
            long minTime = sortedNodes.get(0).getTimestamp().toEpochMilli();
            long maxTime = sortedNodes.get(sortedNodes.size() - 1).getTimestamp().toEpochMilli();
            
            // Avoid division by zero
            if (maxTime == minTime) {
                maxTime = minTime + 1;
            }
            
            double timeRange = maxTime - minTime;
            double xRange = 1000.0; // Arbitrary width of the visualization
            
            for (FlowNode node : sortedNodes) {
                long time = node.getTimestamp().toEpochMilli();
                double x = ((time - minTime) / timeRange) * xRange;
                double y = typeYPositions.get(node.getType());
                
                // Add some jitter to y position to avoid overlap
                y += (Math.random() - 0.5) * 30.0;
                
                node.setX(x);
                node.setY(y);
            }
        }
    }
    
    /**
     * Gets a flow map by ID.
     * 
     * @param mapId The flow map ID
     * @param contextId The context ID for boundary enforcement
     * @return An Optional containing the flow map if found and accessible
     */
    public Optional<FlowMap> getFlowMap(String mapId, String contextId) {
        if (!flowMaps.containsKey(mapId)) {
            return Optional.empty();
        }
        
        // Check if flow map can pass information boundary
        if (!boundary.canInformationPass(mapId, contextId)) {
            return Optional.empty();
        }
        
        return Optional.of(flowMaps.get(mapId));
    }
    
    /**
     * Gets all flow maps accessible in the given context.
     * 
     * @param contextId The context ID for boundary enforcement
     * @return List of accessible flow maps
     */
    public List<FlowMap> getAllFlowMaps(String contextId) {
        return flowMaps.entrySet().stream()
                .filter(entry -> boundary.canInformationPass(entry.getKey(), contextId))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
    
    /**
     * Renders the flow map visualization.
     * 
     * @param mapId The flow map ID
     * @param level The detail level (macro, meso, or micro)
     * @param contextId The context ID for boundary enforcement
     * @return A map containing the visualization data at the specified level
     */
    public Map<String, Object> renderFlowMap(String mapId, String level, String contextId) {
        // Get the flow map
        Optional<FlowMap> optionalMap = getFlowMap(mapId, contextId);
        if (!optionalMap.isPresent()) {
            throw new FractalBrowserException("Flow map not found or not accessible: " + mapId);
        }
        
        FlowMap flowMap = optionalMap.get();
        
        // Set detail parameters based on level
        double nodeSizeMultiplier;
        double edgeThicknessMultiplier;
        boolean includeNodeAttributes;
        boolean includeEdgeAttributes;
        
        switch (level.toLowerCase()) {
            case "macro":
                // High-level overview
                nodeSizeMultiplier = 0.8;
                edgeThicknessMultiplier = 0.7;
                includeNodeAttributes = false;
                includeEdgeAttributes = false;
                break;
                
            case "meso":
                // Medium detail
                nodeSizeMultiplier = 1.0;
                edgeThicknessMultiplier = 1.0;
                includeNodeAttributes = true;
                includeEdgeAttributes = false;
                break;
                
            case "micro":
                // Full detail
                nodeSizeMultiplier = 1.2;
                edgeThicknessMultiplier = 1.5;
                includeNodeAttributes = true;
                includeEdgeAttributes = true;
                break;
                
            default:
                throw new FractalBrowserException("Invalid detail level: " + level);
        }
        
        // Transform nodes for visualization
        List<Map<String, Object>> nodes = flowMap.getNodes().values().stream()
                .filter(node -> boundary.canInformationPass(node.getNodeId(), contextId))
                .map(node -> {
                    Map<String, Object> nodeData = new HashMap<>();
                    nodeData.put("id", node.getNodeId());
                    nodeData.put("type", node.getType());
                    nodeData.put("x", node.getX());
                    nodeData.put("y", node.getY());
                    nodeData.put("size", node.getSize() * nodeSizeMultiplier);
                    nodeData.put("color", node.getColor());
                    nodeData.put("timestamp", node.getTimestamp().toEpochMilli());
                    
                    if (includeNodeAttributes) {
                        nodeData.put("attributes", node.getAttributes());
                        nodeData.put("metadata", node.getMetadata());
                        nodeData.put("relatedInsights", new ArrayList<>(node.getRelatedInsights()));
                        nodeData.put("relatedPatterns", new ArrayList<>(node.getRelatedPatterns()));
                    }
                    
                    return nodeData;
                })
                .collect(Collectors.toList());
        
        // Filter and transform edges
        List<Map<String, Object>> edges = flowMap.getEdges().values().stream()
                .filter(edge -> {
                    String sourceId = edge.getSourceNodeId();
                    String targetId = edge.getTargetNodeId();
                    
                    // Only include edges where both endpoints are accessible
                    return boundary.canInformationPass(sourceId, contextId) &&
                           boundary.canInformationPass(targetId, contextId) &&
                           flowMap.getNodes().containsKey(sourceId) &&
                           flowMap.getNodes().containsKey(targetId);
                })
                .map(edge -> {
                    Map<String, Object> edgeData = new HashMap<>();
                    edgeData.put("id", edge.getEdgeId());
                    edgeData.put("source", edge.getSourceNodeId());
                    edgeData.put("target", edge.getTargetNodeId());
                    edgeData.put("type", edge.getRelationType());
                    edgeData.put("timestamp", edge.getTimestamp().toEpochMilli());
                    edgeData.put("thickness", edge.getThickness() * edgeThicknessMultiplier);
                    edgeData.put("color", edge.getColor());
                    edgeData.put("style", edge.getStyle());
                    
                    if (includeEdgeAttributes) {
                        edgeData.put("weight", edge.getWeight());
                        edgeData.put("metadata", edge.getMetadata());
                    }
                    
                    return edgeData;
                })
                .collect(Collectors.toList());
        
        // Compile visualization data
        Map<String, Object> visualizationData = new HashMap<>();
        visualizationData.put("mapId", flowMap.getMapId());
        visualizationData.put("title", flowMap.getTitle());
        visualizationData.put("description", flowMap.getDescription());
        visualizationData.put("creationTime", flowMap.getCreationTime());
        visualizationData.put("timeWindowMs", flowMap.getTimeWindowMs());
        visualizationData.put("nodes", nodes);
        visualizationData.put("edges", edges);
        visualizationData.put("nodeCount", nodes.size());
        visualizationData.put("edgeCount", edges.size());
        visualizationData.put("level", level);
        
        return visualizationData;
    }
    
    /**
     * Focuses the flow map on a specific insight and its connections.
     * 
     * @param mapId The flow map ID
     * @param focusNodeId The node ID to focus on
     * @param radius The connection radius (how many degrees of separation to include)
     * @param contextId The context ID for boundary enforcement
     * @return A map containing the focused visualization data
     */
    public Map<String, Object> focusFlowMapOnNode(
            String mapId, 
            String focusNodeId, 
            int radius, 
            String contextId) {
        
        // Get the flow map
        Optional<FlowMap> optionalMap = getFlowMap(mapId, contextId);
        if (!optionalMap.isPresent()) {
            throw new FractalBrowserException("Flow map not found or not accessible: " + mapId);
        }
        
        FlowMap flowMap = optionalMap.get();
        
        // Check if focus node exists and is accessible
        if (!flowMap.getNodes().containsKey(focusNodeId) || 
                !boundary.canInformationPass(focusNodeId, contextId)) {
            throw new FractalBrowserException("Focus node not found or not accessible: " + focusNodeId);
        }
        
        // Find connected nodes within radius
        Set<String> includedNodeIds = new HashSet<>();
        includedNodeIds.add(focusNodeId);
        
        // Start with nodes directly connected to focus node
        Set<String> currentLayer = new HashSet<>();
        currentLayer.add(focusNodeId);
        
        // Expand outward by radius
        for (int i = 0; i < radius; i++) {
            Set<String> nextLayer = new HashSet<>();
            
            for (String nodeId : currentLayer) {
                // Find all edges connected to this node
                for (FlowEdge edge : flowMap.getEdges().values()) {
                    if (edge.getSourceNodeId().equals(nodeId)) {
                        String targetId = edge.getTargetNodeId();
                        if (boundary.canInformationPass(targetId, contextId) && 
                                !includedNodeIds.contains(targetId)) {
                            nextLayer.add(targetId);
                        }
                    } else if (edge.getTargetNodeId().equals(nodeId)) {
                        String sourceId = edge.getSourceNodeId();
                        if (boundary.canInformationPass(sourceId, contextId) && 
                                !includedNodeIds.contains(sourceId)) {
                            nextLayer.add(sourceId);
                        }
                    }
                }
            }
            
            includedNodeIds.addAll(nextLayer);
            currentLayer = nextLayer;
        }
        
        // Filter nodes to only included ones
        List<Map<String, Object>> nodes = flowMap.getNodes().entrySet().stream()
                .filter(entry -> includedNodeIds.contains(entry.getKey()))
                .filter(entry -> boundary.canInformationPass(entry.getKey(), contextId))
                .map(entry -> {
                    FlowNode node = entry.getValue();
                    Map<String, Object> nodeData = new HashMap<>();
                    nodeData.put("id", node.getNodeId());
                    nodeData.put("type", node.getType());
                    nodeData.put("x", node.getX());
                    nodeData.put("y", node.getY());
                    nodeData.put("size", node.getNodeId().equals(focusNodeId) ? 
                            node.getSize() * 1.5 : node.getSize());
                    nodeData.put("color", node.getNodeId().equals(focusNodeId) ? 
                            "#ff9900" : node.getColor()); // Highlight focus node
                    nodeData.put("timestamp", node.getTimestamp().toEpochMilli());
                    nodeData.put("attributes", node.getAttributes());
                    
                    return nodeData;
                })
                .collect(Collectors.toList());
        
        // Filter edges to only those connecting included nodes
        List<Map<String, Object>> edges = flowMap.getEdges().entrySet().stream()
                .filter(entry -> {
                    FlowEdge edge = entry.getValue();
                    return includedNodeIds.contains(edge.getSourceNodeId()) && 
                           includedNodeIds.contains(edge.getTargetNodeId()) &&
                           boundary.canInformationPass(edge.getSourceNodeId(), contextId) &&
                           boundary.canInformationPass(edge.getTargetNodeId(), contextId);
                })
                .map(entry -> {
                    FlowEdge edge = entry.getValue();
                    Map<String, Object> edgeData = new HashMap<>();
                    edgeData.put("id", edge.getEdgeId());
                    edgeData.put("source", edge.getSourceNodeId());
                    edgeData.put("target", edge.getTargetNodeId());
                    edgeData.put("type", edge.getRelationType());
                    edgeData.put("thickness", edge.getThickness());
                    
                    // Highlight edges connected to focus node
                    boolean connectedToFocus = edge.getSourceNodeId().equals(focusNodeId) || 
                            edge.getTargetNodeId().equals(focusNodeId);
                    
                    edgeData.put("color", connectedToFocus ? "#ff9900" : edge.getColor());
                    edgeData.put("style", edge.getStyle());
                    
                    return edgeData;
                })
                .collect(Collectors.toList());
        
        // Compile focused visualization data
        Map<String, Object> focusedData = new HashMap<>();
        focusedData.put("mapId", flowMap.getMapId());
        focusedData.put("title", "Focus on " + focusNodeId);
        focusedData.put("focusNodeId", focusNodeId);
        focusedData.put("radius", radius);
        focusedData.put("nodes", nodes);
        focusedData.put("edges", edges);
        focusedData.put("nodeCount", nodes.size());
        focusedData.put("edgeCount", edges.size());
        
        return focusedData;
    }
    
    /**
     * Analyzes flow patterns to identify key insights and connections.
     * 
     * @param mapId The flow map ID
     * @param contextId The context ID for boundary enforcement
     * @return Analysis metrics and highlights
     */
    public Map<String, Object> analyzeFlowPatterns(String mapId, String contextId) {
        // Get the flow map
        Optional<FlowMap> optionalMap = getFlowMap(mapId, contextId);
        if (!optionalMap.isPresent()) {
            throw new FractalBrowserException("Flow map not found or not accessible: " + mapId);
        }
        
        FlowMap flowMap = optionalMap.get();
        
        Map<String, Object> analysis = new HashMap<>();
        
        // Calculate node metrics
        Map<String, FlowNode> accessibleNodes = flowMap.getNodes().entrySet().stream()
                .filter(entry -> boundary.canInformationPass(entry.getKey(), contextId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        
        // Calculate edge metrics
        List<FlowEdge> accessibleEdges = flowMap.getEdges().values().stream()
                .filter(edge -> 
                        boundary.canInformationPass(edge.getSourceNodeId(), contextId) &&
                        boundary.canInformationPass(edge.getTargetNodeId(), contextId))
                .collect(Collectors.toList());
        
        // Group nodes by type
        Map<String, Long> nodeTypeCount = accessibleNodes.values().stream()
                .collect(Collectors.groupingBy(FlowNode::getType, Collectors.counting()));
        
        // Group edges by relation type
        Map<String, Long> edgeTypeCount = accessibleEdges.stream()
                .collect(Collectors.groupingBy(FlowEdge::getRelationType, Collectors.counting()));
        
        // Calculate connectivity metrics
        Map<String, Integer> nodeDegrees = new HashMap<>();
        
        for (FlowNode node : accessibleNodes.values()) {
            int degree = 0;
            for (FlowEdge edge : accessibleEdges) {
                if (edge.getSourceNodeId().equals(node.getNodeId()) ||
                        edge.getTargetNodeId().equals(node.getNodeId())) {
                    degree++;
                }
            }
            nodeDegrees.put(node.getNodeId(), degree);
        }
        
        // Find key nodes (high connectivity)
        List<Map<String, Object>> keyNodes = nodeDegrees.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .map(entry -> {
                    String nodeId = entry.getKey();
                    FlowNode node = accessibleNodes.get(nodeId);
                    
                    Map<String, Object> keyNodeData = new HashMap<>();
                    keyNodeData.put("id", nodeId);
                    keyNodeData.put("type", node.getType());
                    keyNodeData.put("degree", entry.getValue());
                    keyNodeData.put("timestamp", node.getTimestamp().toEpochMilli());
                    
                    // Include key attributes
                    Map<String, Object> keyAttrs = new HashMap<>();
                    for (Map.Entry<String, Object> attr : node.getAttributes().entrySet()) {
                        if (attr.getKey().equals("title") || 
                                attr.getKey().equals("description") ||
                                attr.getKey().equals("category") ||
                                attr.getKey().equals("importance")) {
                            keyAttrs.put(attr.getKey(), attr.getValue());
                        }
                    }
                    keyNodeData.put("attributes", keyAttrs);
                    
                    return keyNodeData;
                })
                .collect(Collectors.toList());
        
        // Find key patterns
        List<Map<String, Object>> keyPatterns = accessibleNodes.values().stream()
                .filter(node -> node.getType().toLowerCase().contains("pattern"))
                .sorted((n1, n2) -> {
                    int size1 = n1.getRelatedInsights().size();
                    int size2 = n2.getRelatedInsights().size();
                    return Integer.compare(size2, size1); // Descending order
                })
                .limit(5)
                .map(node -> {
                    Map<String, Object> patternData = new HashMap<>();
                    patternData.put("id", node.getNodeId());
                    patternData.put("type", node.getType());
                    patternData.put("relatedInsightCount", node.getRelatedInsights().size());
                    patternData.put("timestamp", node.getTimestamp().toEpochMilli());
                    
                    if (node.getAttributes().containsKey("description")) {
                        patternData.put("description", node.getAttributes().get("description"));
                    }
                    
                    return patternData;
                })
                .collect(Collectors.toList());
        
        // Calculate flow directionality
        int forwardEdges = 0;
        int bidirectionalPairs = 0;
        
        // Track edge pairs
        Map<String, Set<String>> connectionMap = new HashMap<>();
        
        for (FlowEdge edge : accessibleEdges) {
            String source = edge.getSourceNodeId();
            String target = edge.getTargetNodeId();
            
            // Skip if source or target is not accessible
            if (!accessibleNodes.containsKey(source) || !accessibleNodes.containsKey(target)) {
                continue;
            }
            
            forwardEdges++;
            
            // Track connections
            connectionMap.computeIfAbsent(source, k -> new HashSet<>()).add(target);
            
            // Check if bidirectional
            if (connectionMap.containsKey(target) && connectionMap.get(target).contains(source)) {
                bidirectionalPairs++;
            }
        }
        
        double directionalityRatio = forwardEdges > 0 ? 
                (double) (forwardEdges - bidirectionalPairs) / forwardEdges : 0;
        
        // Compile analysis data
        analysis.put("nodeCount", accessibleNodes.size());
        analysis.put("edgeCount", accessibleEdges.size());
        analysis.put("nodeTypeDistribution", nodeTypeCount);
        analysis.put("edgeTypeDistribution", edgeTypeCount);
        analysis.put("averageDegree", nodeDegrees.values().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0));
        analysis.put("directionalityRatio", directionalityRatio);
        analysis.put("keyNodes", keyNodes);
        analysis.put("keyPatterns", keyPatterns);
        analysis.put("timestamp", System.currentTimeMillis());
        
        return analysis;
    }
}