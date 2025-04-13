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
import java.util.Collections;

import com.fractal.browser.collective.boundaries.InformationBoundary;
import com.fractal.browser.collective.processing.EmergentPatternDetector;
import com.fractal.browser.exceptions.FractalBrowserException;
import com.fractal.browser.model.Pattern;
import com.fractal.browser.util.FractalMath;

/**
 * EmergentPatternDisplay visualizes detected patterns and their relationships.
 * This class renders emergent patterns using fractal visualization techniques to 
 * help understand the self-similar structures that emerge in the collective system.
 * 
 * The visualization represents patterns at multiple scales (micro, meso, macro)
 * to show how local interactions lead to emergent system-level patterns.
 */
public class EmergentPatternDisplay {

    // Core dependencies
    private final EmergentPatternDetector patternDetector;
    private final InformationBoundary boundary;
    private final FractalMath fractalMath;
    
    // Visualization state
    private final Map<String, PatternNode> patternNodes;
    private final Map<String, PatternEdge> patternEdges;
    private final Map<String, PatternVisualization> visualizations;
    
    // Configuration
    private final double basePatternScale;
    private final int defaultIterationDepth;
    private final String[] patternColorPalette;
    
    /**
     * Represents a pattern node in the visualization.
     */
    public static class PatternNode {
        private final String nodeId;
        private final Pattern pattern;
        private final double x;
        private final double y;
        private double size;
        private String color;
        private double rotation;
        private final Map<String, Object> visualAttributes;
        
        /**
         * Creates a new pattern node.
         */
        public PatternNode(String nodeId, Pattern pattern, double x, double y) {
            this.nodeId = nodeId;
            this.pattern = pattern;
            this.x = x;
            this.y = y;
            this.size = 10;
            this.color = "#3366cc";
            this.rotation = 0;
            this.visualAttributes = new HashMap<>();
        }
        
        // Getters and setters
        public String getNodeId() { return nodeId; }
        public Pattern getPattern() { return pattern; }
        public double getX() { return x; }
        public double getY() { return y; }
        public double getSize() { return size; }
        public void setSize(double size) { this.size = size; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        public double getRotation() { return rotation; }
        public void setRotation(double rotation) { this.rotation = rotation; }
        public Map<String, Object> getVisualAttributes() { return visualAttributes; }
    }
    
    /**
     * Represents an edge between pattern nodes.
     */
    public static class PatternEdge {
        private final String edgeId;
        private final String sourceNodeId;
        private final String targetNodeId;
        private final String relationshipType;
        private double similarity;
        private double thickness;
        private String color;
        private String style;
        private final Map<String, Object> visualAttributes;
        
        /**
         * Creates a new pattern edge.
         */
        public PatternEdge(
                String edgeId, 
                String sourceNodeId, 
                String targetNodeId, 
                String relationshipType,
                double similarity) {
            
            this.edgeId = edgeId;
            this.sourceNodeId = sourceNodeId;
            this.targetNodeId = targetNodeId;
            this.relationshipType = relationshipType;
            this.similarity = similarity;
            this.thickness = 1.0;
            this.color = "#999999";
            this.style = "solid";
            this.visualAttributes = new HashMap<>();
        }
        
        // Getters and setters
        public String getEdgeId() { return edgeId; }
        public String getSourceNodeId() { return sourceNodeId; }
        public String getTargetNodeId() { return targetNodeId; }
        public String getRelationshipType() { return relationshipType; }
        public double getSimilarity() { return similarity; }
        public void setSimilarity(double similarity) { this.similarity = similarity; }
        public double getThickness() { return thickness; }
        public void setThickness(double thickness) { this.thickness = thickness; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        public String getStyle() { return style; }
        public void setStyle(String style) { this.style = style; }
        public Map<String, Object> getVisualAttributes() { return visualAttributes; }
    }
    
    /**
     * Represents a complete pattern visualization.
     */
    public static class PatternVisualization {
        private final String visualizationId;
        private final String title;
        private final String description;
        private final long timestamp;
        private final Map<String, PatternNode> nodes;
        private final Map<String, PatternEdge> edges;
        private final Map<String, Object> metadata;
        private final int fractalDepth;
        private final String focusPatternId;
        
        /**
         * Creates a new pattern visualization.
         */
        public PatternVisualization(
                String visualizationId,
                String title,
                String description,
                Map<String, PatternNode> nodes,
                Map<String, PatternEdge> edges,
                int fractalDepth,
                String focusPatternId) {
            
            this.visualizationId = visualizationId;
            this.title = title;
            this.description = description;
            this.timestamp = System.currentTimeMillis();
            
            // Create deep copies of nodes and edges
            this.nodes = new HashMap<>();
            for (Map.Entry<String, PatternNode> entry : nodes.entrySet()) {
                this.nodes.put(entry.getKey(), copyNode(entry.getValue()));
            }
            
            this.edges = new HashMap<>();
            for (Map.Entry<String, PatternEdge> entry : edges.entrySet()) {
                this.edges.put(entry.getKey(), copyEdge(entry.getValue()));
            }
            
            this.metadata = new HashMap<>();
            this.fractalDepth = fractalDepth;
            this.focusPatternId = focusPatternId;
        }
        
        // Create a deep copy of a node
        private PatternNode copyNode(PatternNode original) {
            PatternNode copy = new PatternNode(
                    original.getNodeId(),
                    original.getPattern(),
                    original.getX(),
                    original.getY());
            
            copy.setSize(original.getSize());
            copy.setColor(original.getColor());
            copy.setRotation(original.getRotation());
            copy.getVisualAttributes().putAll(original.getVisualAttributes());
            
            return copy;
        }
        
        // Create a deep copy of an edge
        private PatternEdge copyEdge(PatternEdge original) {
            PatternEdge copy = new PatternEdge(
                    original.getEdgeId(),
                    original.getSourceNodeId(),
                    original.getTargetNodeId(),
                    original.getRelationshipType(),
                    original.getSimilarity());
            
            copy.setThickness(original.getThickness());
            copy.setColor(original.getColor());
            copy.setStyle(original.getStyle());
            copy.getVisualAttributes().putAll(original.getVisualAttributes());
            
            return copy;
        }
        
        // Getters
        public String getVisualizationId() { return visualizationId; }
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public long getTimestamp() { return timestamp; }
        public Map<String, PatternNode> getNodes() { return nodes; }
        public Map<String, PatternEdge> getEdges() { return edges; }
        public Map<String, Object> getMetadata() { return metadata; }
        public int getFractalDepth() { return fractalDepth; }
        public String getFocusPatternId() { return focusPatternId; }
    }
    
    /**
     * Creates a new EmergentPatternDisplay instance.
     * 
     * @param patternDetector Service for detecting patterns
     * @param boundary Information boundary for enforcing access controls
     * @param fractalMath Utility for fractal math calculations
     * @param basePatternScale Base scale for pattern visualization
     * @param defaultIterationDepth Default depth for fractal iterations
     */
    public EmergentPatternDisplay(
            EmergentPatternDetector patternDetector,
            InformationBoundary boundary,
            FractalMath fractalMath,
            double basePatternScale,
            int defaultIterationDepth) {
        
        this.patternDetector = patternDetector;
        this.boundary = boundary;
        this.fractalMath = fractalMath;
        
        this.basePatternScale = basePatternScale;
        this.defaultIterationDepth = defaultIterationDepth;
        
        this.patternNodes = new ConcurrentHashMap<>();
        this.patternEdges = new ConcurrentHashMap<>();
        this.visualizations = new ConcurrentHashMap<>();
        
        // Define a color palette for different pattern types
        this.patternColorPalette = new String[] {
                "#3366cc", // Blue for temporal patterns
                "#33cc33", // Green for attribute patterns
                "#cc3333", // Red for sequence patterns
                "#cc33cc", // Purple for meta patterns
                "#cccc33", // Yellow for combined patterns
                "#33cccc"  // Cyan for special patterns
        };
    }
    
    /**
     * Generates a visualization of emergent patterns from a specified time window.
     * 
     * @param timeWindowMs Time window for pattern detection in milliseconds
     * @param maxPatterns Maximum number of patterns to include
     * @param iterationDepth Fractal iteration depth
     * @param contextId Context ID for boundary enforcement
     * @return The ID of the generated visualization
     */
    public String generatePatternVisualization(
            long timeWindowMs,
            int maxPatterns,
            int iterationDepth,
            String contextId) {
        
        try {
            // Clear existing nodes and edges
            patternNodes.clear();
            patternEdges.clear();
            
            // Detect emergent patterns
            List<Pattern> patterns = patternDetector.detectEmergentPatterns(timeWindowMs, contextId);
            
            // Filter by boundary constraints
            patterns = patterns.stream()
                    .filter(p -> boundary.canInformationPass(p.getId(), contextId))
                    .collect(Collectors.toList());
            
            // Limit to maximum number of patterns
            if (patterns.size() > maxPatterns) {
                // Sort by timestamp descending
                patterns.sort((p1, p2) -> Long.compare(p2.getTimestamp(), p1.getTimestamp()));
                patterns = patterns.subList(0, maxPatterns);
            }
            
            if (patterns.isEmpty()) {
                throw new FractalBrowserException("No accessible patterns found in the specified time window");
            }
            
            // Create pattern nodes
            for (Pattern pattern : patterns) {
                createPatternNode(pattern);
            }
            
            // Create edges between related patterns
            createPatternEdges(patterns);
            
            // Apply layout algorithm
            applyFractalLayout(patterns);
            
            // Create the visualization
            String visualizationId = UUID.randomUUID().toString();
            String title = "Emergent Patterns: " + java.time.Instant.now();
            String description = "Visualization of " + patterns.size() + 
                    " emergent patterns over a " + (timeWindowMs / (60 * 1000)) + " minute window";
            
            PatternVisualization viz = new PatternVisualization(
                    visualizationId,
                    title,
                    description,
                    patternNodes,
                    patternEdges,
                    iterationDepth,
                    null); // No specific focus
            
            // Add metadata
            viz.getMetadata().put("patternCount", patterns.size());
            viz.getMetadata().put("timeWindowMs", timeWindowMs);
            viz.getMetadata().put("creationTime", System.currentTimeMillis());
            
            // Add pattern type distribution
            Map<String, Long> typeDistribution = patterns.stream()
                    .collect(Collectors.groupingBy(Pattern::getType, Collectors.counting()));
            viz.getMetadata().put("typeDistribution", typeDistribution);
            
            // Store the visualization
            visualizations.put(visualizationId, viz);
            
            return visualizationId;
            
        } catch (Exception e) {
            throw new FractalBrowserException("Failed to generate pattern visualization: " + e.getMessage(), e);
        }
    }
    
    /**
     * Creates a pattern node for visualization.
     * 
     * @param pattern The pattern to visualize
     * @return The created pattern node
     */
    private PatternNode createPatternNode(Pattern pattern) {
        String nodeId = pattern.getId();
        
        // Initial position (will be updated by layout algorithm)
        double x = Math.random() * 1000;
        double y = Math.random() * 1000;
        
        PatternNode node = new PatternNode(nodeId, pattern, x, y);
        
        // Set size based on pattern complexity
        double complexity = calculatePatternComplexity(pattern);
        node.setSize(basePatternScale * (1 + complexity));
        
        // Set color based on pattern type
        String patternType = pattern.getType();
        node.setColor(getColorForPatternType(patternType));
        
        // Set rotation based on pattern attributes
        node.setRotation(Math.random() * 360);
        
        // Add visual attributes
        node.getVisualAttributes().put("timestamp", pattern.getTimestamp());
        node.getVisualAttributes().put("type", patternType);
        
        // Store the node
        patternNodes.put(nodeId, node);
        
        return node;
    }
    
    /**
     * Calculates the complexity of a pattern based on its attributes.
     * 
     * @param pattern The pattern to analyze
     * @return A complexity score (0.0-1.0)
     */
    private double calculatePatternComplexity(Pattern pattern) {
        Map<String, Object> attributes = pattern.getAttributes();
        
        // Base complexity on number of attributes and their depth
        int attributeCount = attributes.size();
        int nestedDepth = 0;
        
        // Check for nested structures
        for (Object value : attributes.values()) {
            if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> nestedMap = (Map<String, Object>) value;
                nestedDepth = Math.max(nestedDepth, 1 + getNestedDepth(nestedMap));
            } else if (value instanceof List) {
                @SuppressWarnings("unchecked")
                List<Object> nestedList = (List<Object>) value;
                nestedDepth = Math.max(nestedDepth, 1 + getNestedDepth(nestedList));
            }
        }
        
        // Calculate complexity score
        double score = (attributeCount / 10.0) + (nestedDepth / 5.0);
        
        // Add bonus for META patterns
        if (pattern.getType().equals("META")) {
            score += 0.3;
        }
        
        // Normalize to 0.0-1.0 range
        return Math.min(1.0, score);
    }
    
    /**
     * Gets the nested depth of a map structure.
     * 
     * @param map The map to analyze
     * @return The maximum nesting depth
     */
    private int getNestedDepth(Map<String, Object> map) {
        int maxDepth = 0;
        
        for (Object value : map.values()) {
            if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> nestedMap = (Map<String, Object>) value;
                maxDepth = Math.max(maxDepth, 1 + getNestedDepth(nestedMap));
            } else if (value instanceof List) {
                @SuppressWarnings("unchecked")
                List<Object> nestedList = (List<Object>) value;
                maxDepth = Math.max(maxDepth, 1 + getNestedDepth(nestedList));
            }
        }
        
        return maxDepth;
    }
    
    /**
     * Gets the nested depth of a list structure.
     * 
     * @param list The list to analyze
     * @return The maximum nesting depth
     */
    private int getNestedDepth(List<Object> list) {
        int maxDepth = 0;
        
        for (Object value : list) {
            if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> nestedMap = (Map<String, Object>) value;
                maxDepth = Math.max(maxDepth, 1 + getNestedDepth(nestedMap));
            } else if (value instanceof List) {
                @SuppressWarnings("unchecked")
                List<Object> nestedList = (List<Object>) value;
                maxDepth = Math.max(maxDepth, 1 + getNestedDepth(nestedList));
            }
        }
        
        return maxDepth;
    }
    
    /**
     * Gets a color for a pattern type.
     * 
     * @param patternType The pattern type
     * @return A hex color code
     */
    private String getColorForPatternType(String patternType) {
        switch (patternType) {
            case "TEMPORAL":
                return patternColorPalette[0];
            case "ATTRIBUTE":
                return patternColorPalette[1];
            case "SEQUENCE":
                return patternColorPalette[2];
            case "META":
                return patternColorPalette[3];
            default:
                return patternColorPalette[4];
        }
    }
    
    /**
     * Creates edges between related patterns.
     * 
     * @param patterns The patterns to analyze
     */
    private void createPatternEdges(List<Pattern> patterns) {
        // Compare each pattern with every other pattern
        for (int i = 0; i < patterns.size(); i++) {
            Pattern p1 = patterns.get(i);
            
            for (int j = i + 1; j < patterns.size(); j++) {
                Pattern p2 = patterns.get(j);
                
                // Calculate similarity between patterns
                double similarity = calculatePatternSimilarity(p1, p2);
                
                // Create an edge if similarity exceeds threshold
                if (similarity > 0.2) {
                    String edgeId = p1.getId() + "->" + p2.getId();
                    String sourceId = p1.getId();
                    String targetId = p2.getId();
                    
                    PatternEdge edge = new PatternEdge(
                            edgeId,
                            sourceId,
                            targetId,
                            determineRelationshipType(p1, p2),
                            similarity);
                    
                    // Set edge appearance based on similarity
                    edge.setThickness(Math.max(1.0, similarity * 5.0));
                    edge.setColor(getColorForSimilarity(similarity));
                    
                    if (similarity > 0.7) {
                        edge.setStyle("solid");
                    } else if (similarity > 0.4) {
                        edge.setStyle("dashed");
                    } else {
                        edge.setStyle("dotted");
                    }
                    
                    // Special case for META patterns
                    if (p1.getType().equals("META")) {
                        Map<String, Object> attrs = p1.getAttributes();
                        if (attrs.containsKey("pattern1Id") && attrs.containsKey("pattern2Id")) {
                            String pattern1Id = attrs.get("pattern1Id").toString();
                            String pattern2Id = attrs.get("pattern2Id").toString();
                            
                            if (pattern1Id.equals(p2.getId()) || pattern2Id.equals(p2.getId())) {
                                edge.setColor("#cc33cc"); // Purple for meta connections
                                edge.setThickness(3.0);
                                edge.setStyle("solid");
                            }
                        }
                    } else if (p2.getType().equals("META")) {
                        Map<String, Object> attrs = p2.getAttributes();
                        if (attrs.containsKey("pattern1Id") && attrs.containsKey("pattern2Id")) {
                            String pattern1Id = attrs.get("pattern1Id").toString();
                            String pattern2Id = attrs.get("pattern2Id").toString();
                            
                            if (pattern1Id.equals(p1.getId()) || pattern2Id.equals(p1.getId())) {
                                edge.setColor("#cc33cc"); // Purple for meta connections
                                edge.setThickness(3.0);
                                edge.setStyle("solid");
                            }
                        }
                    }
                    
                    // Store the edge
                    patternEdges.put(edgeId, edge);
                }
            }
        }
    }
    
    /**
     * Calculates similarity between two patterns.
     * 
     * @param p1 First pattern
     * @param p2 Second pattern
     * @return Similarity score (0.0-1.0)
     */
    private double calculatePatternSimilarity(Pattern p1, Pattern p2) {
        // If patterns are of same type, higher base similarity
        double similarity = p1.getType().equals(p2.getType()) ? 0.3 : 0.1;
        
        // Extract insight IDs from both patterns
        Set<String> insightsInP1 = extractInsightIds(p1);
        Set<String> insightsInP2 = extractInsightIds(p2);
        
        // Calculate Jaccard similarity for insight sets
        if (!insightsInP1.isEmpty() || !insightsInP2.isEmpty()) {
            // Find intersection
            Set<String> intersection = new HashSet<>(insightsInP1);
            intersection.retainAll(insightsInP2);
            
            // Find union
            Set<String> union = new HashSet<>(insightsInP1);
            union.addAll(insightsInP2);
            
            // Calculate Jaccard index
            if (!union.isEmpty()) {
                double jaccardSimilarity = (double) intersection.size() / union.size();
                similarity += jaccardSimilarity * 0.4;
            }
        }
        
        // Compare timestamps
        long timeDiff = Math.abs(p1.getTimestamp() - p2.getTimestamp());
        long maxTimeDiff = 30 * 60 * 1000; // 30 minutes
        if (timeDiff < maxTimeDiff) {
            similarity += 0.2 * (1 - (double) timeDiff / maxTimeDiff);
        }
        
        // Compare attributes
        Map<String, Object> attrs1 = p1.getAttributes();
        Map<String, Object> attrs2 = p2.getAttributes();
        
        Set<String> commonKeys = new HashSet<>(attrs1.keySet());
        commonKeys.retainAll(attrs2.keySet());
        
        double attrSimilarity = 0;
        for (String key : commonKeys) {
            Object val1 = attrs1.get(key);
            Object val2 = attrs2.get(key);
            
            if (val1 != null && val1.equals(val2)) {
                attrSimilarity += 0.1;
            }
        }
        
        similarity += Math.min(0.3, attrSimilarity);
        
        // Cap at 1.0
        return Math.min(1.0, similarity);
    }
    
    /**
     * Extracts insight IDs from a pattern.
     * 
     * @param pattern The pattern
     * @return Set of insight IDs
     */
    private Set<String> extractInsightIds(Pattern pattern) {
        Set<String> insightIds = new HashSet<>();
        Map<String, Object> attributes = pattern.getAttributes();
        
        // Extract IDs from various pattern attribute types
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
     * Determines the relationship type between two patterns.
     * 
     * @param p1 First pattern
     * @param p2 Second pattern
     * @return Relationship type string
     */
    private String determineRelationshipType(Pattern p1, Pattern p2) {
        // META patterns have special relationships
        if (p1.getType().equals("META")) {
            Map<String, Object> attrs = p1.getAttributes();
            if (attrs.containsKey("pattern1Id") && attrs.containsKey("pattern2Id")) {
                String pattern1Id = attrs.get("pattern1Id").toString();
                String pattern2Id = attrs.get("pattern2Id").toString();
                
                if (pattern1Id.equals(p2.getId()) || pattern2Id.equals(p2.getId())) {
                    return "meta_component";
                }
            }
        } else if (p2.getType().equals("META")) {
            Map<String, Object> attrs = p2.getAttributes();
            if (attrs.containsKey("pattern1Id") && attrs.containsKey("pattern2Id")) {
                String pattern1Id = attrs.get("pattern1Id").toString();
                String pattern2Id = attrs.get("pattern2Id").toString();
                
                if (pattern1Id.equals(p1.getId()) || pattern2Id.equals(p1.getId())) {
                    return "meta_component";
                }
            }
        }
        
        // Same type patterns
        if (p1.getType().equals(p2.getType())) {
            return "similar";
        }
        
        // Temporal relationship based on timestamps
        if (Math.abs(p1.getTimestamp() - p2.getTimestamp()) < 60000) { // Within 1 minute
            return "concurrent";
        } else if (p1.getTimestamp() < p2.getTimestamp()) {
            return "precedes";
        } else {
            return "follows";
        }
    }
    
    /**
     * Gets color for a similarity score.
     * 
     * @param similarity Similarity score (0.0-1.0)
     * @return Hex color code
     */
    private String getColorForSimilarity(double similarity) {
        if (similarity >= 0.8) {
            return "#ff0000"; // Strong - Red
        } else if (similarity >= 0.5) {
            return "#ff9900"; // Medium - Orange
        } else if (similarity >= 0.3) {
            return "#ffcc00"; // Weak - Yellow
        } else {
            return "#cccccc"; // Very weak - Gray
        }
    }
    
    /**
     * Applies a fractal layout algorithm to position pattern nodes.
     * 
     * @param patterns The patterns to lay out
     */
    private void applyFractalLayout(List<Pattern> patterns) {
        // Group patterns by type
        Map<String, List<Pattern>> patternsByType = patterns.stream()
                .collect(Collectors.groupingBy(Pattern::getType));
        
        // Calculate center positions for each type
        Map<String, double[]> typePositions = positionPatternTypes(patternsByType.keySet());
        
        // For each pattern type, layout patterns in a fractal pattern
        for (Map.Entry<String, List<Pattern>> entry : patternsByType.entrySet()) {
            String type = entry.getKey();
            List<Pattern> patternsOfType = entry.getValue();
            
            double[] centerPos = typePositions.get(type);
            double centerX = centerPos[0];
            double centerY = centerPos[1];
            
            // Create a fractal layout for this type
            layoutPatternsInFractalPattern(patternsOfType, centerX, centerY, 200, 0);
        }
    }
    
    /**
     * Positions pattern types in the layout.
     * 
     * @param types Set of pattern types
     * @return Map of type to position coordinates
     */
    private Map<String, double[]> positionPatternTypes(Set<String> types) {
        Map<String, double[]> positions = new HashMap<>();
        
        // Position types in a circle
        double radius = 300;
        int i = 0;
        
        for (String type : types) {
            double angle = (2 * Math.PI * i) / types.size();
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            
            positions.put(type, new double[] {x, y});
            i++;
        }
        
        return positions;
    }
    
    /**
     * Layouts patterns in a fractal pattern around a center point.
     * 
     * @param patterns The patterns to layout
     * @param centerX Center X coordinate
     * @param centerY Center Y coordinate
     * @param radius Radius of the layout
     * @param startAngle Starting angle in degrees
     */
    private void layoutPatternsInFractalPattern(
            List<Pattern> patterns, 
            double centerX, 
            double centerY, 
            double radius,
            double startAngle) {
        
        if (patterns.isEmpty()) {
            return;
        }
        
        int patternCount = patterns.size();
        
        if (patternCount == 1) {
            // Single pattern at center
            Pattern pattern = patterns.get(0);
            PatternNode node = patternNodes.get(pattern.getId());
            
            if (node != null) {
                node.setX(centerX);
                node.setY(centerY);
            }
        } else {
            // Multiple patterns in spiral
            double angleStep = 360.0 / patternCount;
            double radiusStep = radius / patternCount;
            
            // Sort patterns for consistent layout
            patterns.sort((p1, p2) -> p1.getId().compareTo(p2.getId()));
            
            for (int i = 0; i < patternCount; i++) {
                Pattern pattern = patterns.get(i);
                PatternNode node = patternNodes.get(pattern.getId());
                
                if (node != null) {
                    // Calculate position in logarithmic spiral
                    double theta = Math.toRadians(startAngle + i * angleStep);
                    double r = radiusStep * Math.sqrt(i + 1);
                    
                    double x = centerX + r * Math.cos(theta);
                    double y = centerY + r * Math.sin(theta);
                    
                    node.setX(x);
                    node.setY(y);
                    
                    // Set rotation to follow spiral
                    node.setRotation(startAngle + i * angleStep);
                }
            }
        }
    }
    
    /**
     * Generates a fractal visualization for a specific pattern.
     * 
     * @param patternId The pattern ID to focus on
     * @param iterationDepth The fractal iteration depth
     * @param contextId The context ID for boundary enforcement
     * @return The ID of the generated visualization
     */
    public String generateFractalPatternVisualization(
            String patternId,
            int iterationDepth,
            String contextId) {
        
        try {
            // Clear existing nodes and edges
            patternNodes.clear();
            patternEdges.clear();
            
            // Get the pattern to focus on
            Optional<Pattern> optionalPattern = patternDetector.getPattern(patternId, contextId);
            if (!optionalPattern.isPresent()) {
                throw new FractalBrowserException("Pattern not found or not accessible: " + patternId);
            }
            
            Pattern focusPattern = optionalPattern.get();
            
            // Create the focus pattern node
            PatternNode focusNode = createPatternNode(focusPattern);
            focusNode.setX(0);
            focusNode.setY(0);
            focusNode.setSize(focusNode.getSize() * 1.5); // Larger size for focus
            
            // Find related patterns
            List<Pattern> relatedPatterns = findRelatedPatterns(focusPattern, contextId);
            
            // Create nodes for related patterns
            for (Pattern pattern : relatedPatterns) {
                createPatternNode(pattern);
            }
            
            // Create edges between patterns
            List<Pattern> allPatterns = new ArrayList<>();
            allPatterns.add(focusPattern);
            allPatterns.addAll(relatedPatterns);
            createPatternEdges(allPatterns);
            
            // Apply fractal layout
            applyFractalLayoutForFocus(focusPattern, relatedPatterns, iterationDepth);
            
            // Create the visualization
            String visualizationId = UUID.randomUUID().toString();
            String title = "Fractal Pattern: " + focusPattern.getType() + " - " + java.time.Instant.now();
            String description = "Fractal visualization of pattern " + patternId + 
                    " with " + relatedPatterns.size() + " related patterns at depth " + iterationDepth;
            
            PatternVisualization viz = new PatternVisualization(
                    visualizationId,
                    title,
                    description,
                    patternNodes,
                    patternEdges,
                    iterationDepth,
                    patternId);
            
            // Add metadata
            viz.getMetadata().put("focusPatternType", focusPattern.getType());
            viz.getMetadata().put("focusPatternTimestamp", focusPattern.getTimestamp());
            viz.getMetadata().put("relatedPatternCount", relatedPatterns.size());
            viz.getMetadata().put("creationTime", System.currentTimeMillis());
            
            // Add additional fractal metrics
            Map<String, Object> fractalMetrics = new HashMap<>();
            fractalMetrics.put("self_similarity", calculateSelfSimilarity(allPatterns));
            fractalMetrics.put("complexity", calculatePatternComplexity(focusPattern));
            fractalMetrics.put("recursion_depth", iterationDepth);
            
            viz.getMetadata().put("fractalMetrics", fractalMetrics);
            
            // Store the visualization
            visualizations.put(visualizationId, viz);
            
            return visualizationId;
            
        } catch (Exception e) {
            throw new FractalBrowserException("Failed to generate fractal pattern visualization: " + e.getMessage(), e);
        }
    }
    
    /**
     * Finds patterns related to a focus pattern.
     * 
     * @param focusPattern The focus pattern
     * @param contextId The context ID for boundary enforcement
     * @return List of related patterns
     */
    private List<Pattern> findRelatedPatterns(Pattern focusPattern, String contextId) {
        List<Pattern> relatedPatterns = new ArrayList<>();
        
        // Get patterns of the same type
        List<Pattern> sameTypePatterns = patternDetector.getPatternsByType(
                focusPattern.getType(), contextId);
        
        // Filter out the focus pattern itself
        sameTypePatterns = sameTypePatterns.stream()
                .filter(p -> !p.getId().equals(focusPattern.getId()))
                .collect(Collectors.toList());
        
        // Add to related patterns (limit to 10)
        if (!sameTypePatterns.isEmpty()) {
            relatedPatterns.addAll(sameTypePatterns.subList(
                    0, Math.min(10, sameTypePatterns.size())));
        }
        
        // Special handling for META patterns
        if (focusPattern.getType().equals("META")) {
            Map<String, Object> attrs = focusPattern.getAttributes();
            
            if (attrs.containsKey("pattern1Id") && attrs.containsKey("pattern2Id")) {
                String pattern1Id = attrs.get("pattern1Id").toString();
                String pattern2Id = attrs.get("pattern2Id").toString();
                
                // Add the component patterns
                patternDetector.getPattern(pattern1Id, contextId)
                        .ifPresent(relatedPatterns::add);
                
                patternDetector.getPattern(pattern2Id, contextId)
                        .ifPresent(relatedPatterns::add);
            }
        } else {
            // Find META patterns that reference this pattern
            List<Pattern> metaPatterns = patternDetector.getPatternsByType("META", contextId);
            
            for (Pattern metaPattern : metaPatterns) {
                Map<String, Object> attrs = metaPattern.getAttributes();
                
                if (attrs.containsKey("pattern1Id") && attrs.containsKey("pattern2Id")) {
                    String pattern1Id = attrs.get("pattern1Id").toString();
                    String pattern2Id = attrs.get("pattern2Id").toString();
                    
                    if (pattern1Id.equals(focusPattern.getId()) || 
                            pattern2Id.equals(focusPattern.getId())) {
                        relatedPatterns.add(metaPattern);
                    }
                }
            }
        }
        
        // Find patterns with similar insights
        Set<String> focusInsightIds = extractInsightIds(focusPattern);
        
        if (!focusInsightIds.isEmpty()) {
            // Get all patterns (limited to avoid too many comparisons)
            List<Pattern> allPatterns = new ArrayList<>();
            
            for (String type : new String[] {"TEMPORAL", "ATTRIBUTE", "SEQUENCE"}) {
                if (!type.equals(focusPattern.getType())) {
                    List<Pattern> typedPatterns = patternDetector.getPatternsByType(type, contextId);
                    
                    if (!typedPatterns.isEmpty()) {
                        allPatterns.addAll(typedPatterns.subList(
                                0, Math.min(20, typedPatterns.size())));
                    }
                }
            }
            
            // Find patterns with overlapping insights
            for (Pattern pattern : allPatterns) {
                if (relatedPatterns.contains(pattern)) {
                    continue;
                }
                
                Set<String> insightIds = extractInsightIds(pattern);
                
                // Check for overlap
                Set<String> intersection = new HashSet<>(focusInsightIds);
                intersection.retainAll(insightIds);
                
                if (!intersection.isEmpty()) {
                    relatedPatterns.add(pattern);
                    
                    // Limit to reasonable number
                    if (relatedPatterns.size() >= 30) {
                        break;
                    }
                }
            }
        }
        
        return relatedPatterns;
    }
    
    /**
     * Applies a fractal layout algorithm for a focus pattern and related patterns.
     * 
     * @param focusPattern The focus pattern
     * @param relatedPatterns Related patterns
     * @param iterationDepth Fractal iteration depth
     */
    private void applyFractalLayoutForFocus(
            Pattern focusPattern,
            List<Pattern> relatedPatterns,
            int iterationDepth) {
        
        // Center the focus pattern
        PatternNode focusNode = patternNodes.get(focusPattern.getId());
        focusNode.setX(0);
        focusNode.setY(0);
        
        // Group related patterns by type
        Map<String, List<Pattern>> relatedByType = relatedPatterns.stream()
                .collect(Collectors.groupingBy(Pattern::getType));
        
        // Position each type in a fractal branch
        double angleStep = 360.0 / relatedByType.size();
        double currentAngle = 0;
        
        for (Map.Entry<String, List<Pattern>> entry : relatedByType.entrySet()) {
            String type = entry.getKey();
            List<Pattern> patterns = entry.getValue();
            
            // Generate a fractal branch for this type
            generateFractalBranch(
                    focusPattern,
                    patterns,
                    currentAngle,
                    200,
                    iterationDepth);
            
            currentAngle += angleStep;
        }
    }
    
    /**
     * Generates a fractal branch of pattern nodes.
     * 
     * @param focusPattern The center focus pattern
     * @param branchPatterns Patterns to position in this branch
     * @param angle Base angle for the branch
     * @param radius Base radius for the branch
     * @param depth Recursion depth
     */
    private void generateFractalBranch(
            Pattern focusPattern,
            List<Pattern> branchPatterns,
            double angle,
            double radius,
            int depth) {
        
        if (branchPatterns.isEmpty() || depth <= 0) {
            return;
        }
        
        // Sort patterns for consistent layout
        branchPatterns.sort((p1, p2) -> p1.getId().compareTo(p2.getId()));
        
        // Get focus node position
        PatternNode focusNode = patternNodes.get(focusPattern.getId());
        double centerX = focusNode.getX();
        double centerY = focusNode.getY();
        
        // Calculate fractal branching parameters
        double angelSpan = 120; // Angle span for branches
        double angelStep = angelSpan / Math.max(1, branchPatterns.size() - 1);
        double startAngle = angle - angelSpan / 2;
        
        // Position each pattern in the branch
        for (int i = 0; i < branchPatterns.size(); i++) {
            Pattern pattern = branchPatterns.get(i);
            PatternNode node = patternNodes.get(pattern.getId());
            
            if (node != null) {
                // Calculate position using Mandelbrot/Julia set inspired placement
                double branchAngle = Math.toRadians(startAngle + i * angelStep);
                
                // Use fractal math for complex coordinate calculation
                double[] coords = fractalMath.calculateMandelbrotPoint(
                        (double) i / branchPatterns.size(),
                        0.7, // Parameter c real part
                        0.3, // Parameter c imaginary part
                        depth);
                
                double distanceFactor = coords[0] * radius;
                double angleFactor = branchAngle + coords[1] * Math.PI / 4;
                
                // Calculate final position
                double x = centerX + distanceFactor * Math.cos(angleFactor);
                double y = centerY + distanceFactor * Math.sin(angleFactor);
                
                // Position the node
                node.setX(x);
                node.setY(y);
                node.setRotation(Math.toDegrees(angleFactor));
                
                // Recursively add sub-branches if needed
                if (depth > 1 && i < 2) { // Only create sub-branches for first few patterns
                    // Find related patterns for this node
                    List<Pattern> subBranchPatterns = findRelatedPatterns(pattern, new ArrayList<>(branchPatterns));
                    
                    if (!subBranchPatterns.isEmpty()) {
                        // Limit to avoid overcrowding
                        subBranchPatterns = subBranchPatterns.subList(
                                0, Math.min(3, subBranchPatterns.size()));
                        
                        // Create sub-branch
                        generateFractalBranch(
                                pattern,
                                subBranchPatterns,
                                Math.toDegrees(angleFactor),
                                radius * 0.5,
                                depth - 1);
                    }
                }
            }
        }
    }
    
    /**
     * Finds related patterns excluding those in the provided list.
     * 
     * @param pattern The pattern to find relations for
     * @param excludePatterns Patterns to exclude
     * @return List of related patterns
     */
    private List<Pattern> findRelatedPatterns(Pattern pattern, List<Pattern> excludePatterns) {
        List<Pattern> related = new ArrayList<>();
        
        // Check edges to find connected patterns
        for (PatternEdge edge : patternEdges.values()) {
            if (edge.getSourceNodeId().equals(pattern.getId())) {
                String targetId = edge.getTargetNodeId();
                
                // Get the target pattern
                PatternNode targetNode = patternNodes.get(targetId);
                if (targetNode != null) {
                    Pattern targetPattern = targetNode.getPattern();
                    
                    if (!targetPattern.getId().equals(pattern.getId()) && 
                            !excludePatterns.contains(targetPattern)) {
                        related.add(targetPattern);
                    }
                }
            } else if (edge.getTargetNodeId().equals(pattern.getId())) {
                String sourceId = edge.getSourceNodeId();
                
                // Get the source pattern
                PatternNode sourceNode = patternNodes.get(sourceId);
                if (sourceNode != null) {
                    Pattern sourcePattern = sourceNode.getPattern();
                    
                    if (!sourcePattern.getId().equals(pattern.getId()) && 
                            !excludePatterns.contains(sourcePattern)) {
                        related.add(sourcePattern);
                    }
                }
            }
        }
        
        return related;
    }
    
    /**
     * Calculates self-similarity metric for a set of patterns.
     * 
     * @param patterns The patterns to analyze
     * @return Self-similarity score (0.0-1.0)
     */
    private double calculateSelfSimilarity(List<Pattern> patterns) {
        if (patterns.size() < 2) {
            return 0.0;
        }
        
        // Calculate average similarity between patterns
        double totalSimilarity = 0;
        int comparisons = 0;
        
        for (int i = 0; i < patterns.size(); i++) {
            for (int j = i + 1; j < patterns.size(); j++) {
                totalSimilarity += calculatePatternSimilarity(patterns.get(i), patterns.get(j));
                comparisons++;
            }
        }
        
        return comparisons > 0 ? totalSimilarity / comparisons : 0.0;
    }
    
    /**
     * Gets a pattern visualization by ID.
     * 
     * @param visualizationId The visualization ID
     * @param contextId The context ID for boundary enforcement
     * @return An Optional containing the visualization if found and accessible
     */
    public Optional<PatternVisualization> getVisualization(
            String visualizationId, 
            String contextId) {
        
        if (!visualizations.containsKey(visualizationId)) {
            return Optional.empty();
        }
        
        // Check if visualization can pass information boundary
        if (!boundary.canInformationPass(visualizationId, contextId)) {
            return Optional.empty();
        }
        
        return Optional.of(visualizations.get(visualizationId));
    }
    
    /**
     * Renders a pattern visualization.
     * 
     * @param visualizationId The visualization ID
     * @param level The detail level (macro, meso, or micro)
     * @param contextId The context ID for boundary enforcement
     * @return A map containing the visualization data
     */
    public Map<String, Object> renderVisualization(
            String visualizationId,
            String level,
            String contextId) {
        
        // Get the visualization
        Optional<PatternVisualization> optionalViz = getVisualization(visualizationId, contextId);
        if (!optionalViz.isPresent()) {
            throw new FractalBrowserException(
                    "Visualization not found or not accessible: " + visualizationId);
        }
        
        PatternVisualization viz = optionalViz.get();
        
        // Set detail parameters based on level
        boolean includePatternDetails;
        boolean includeAttributes;
        double nodeSizeMultiplier;
        
        switch (level.toLowerCase()) {
            case "macro":
                includePatternDetails = false;
                includeAttributes = false;
                nodeSizeMultiplier = 0.8;
                break;
                
            case "meso":
                includePatternDetails = true;
                includeAttributes = false;
                nodeSizeMultiplier = 1.0;
                break;
                
            case "micro":
                includePatternDetails = true;
                includeAttributes = true;
                nodeSizeMultiplier = 1.2;
                break;
                
            default:
                throw new FractalBrowserException("Invalid detail level: " + level);
        }
        
        // Transform nodes for visualization
        List<Map<String, Object>> nodes = viz.getNodes().values().stream()
                .filter(node -> boundary.canInformationPass(node.getNodeId(), contextId))
                .map(node -> {
                    Map<String, Object> nodeData = new HashMap<>();
                    nodeData.put("id", node.getNodeId());
                    nodeData.put("x", node.getX());
                    nodeData.put("y", node.getY());
                    nodeData.put("size", node.getSize() * nodeSizeMultiplier);
                    nodeData.put("color", node.getColor());
                    nodeData.put("rotation", node.getRotation());
                    
                    // Special highlighting for focus pattern
                    if (viz.getFocusPatternId() != null && 
                            node.getNodeId().equals(viz.getFocusPatternId())) {
                        nodeData.put("focus", true);
                        nodeData.put("borderColor", "#ffffff");
                        nodeData.put("borderWidth", 2);
                    }
                    
                    if (includePatternDetails) {
                        Pattern pattern = node.getPattern();
                        nodeData.put("type", pattern.getType());
                        nodeData.put("description", pattern.getDescription());
                        nodeData.put("timestamp", pattern.getTimestamp());
                        
                        if (includeAttributes) {
                            nodeData.put("attributes", pattern.getAttributes());
                            nodeData.put("visualAttributes", node.getVisualAttributes());
                        }
                    }
                    
                    return nodeData;
                })
                .collect(Collectors.toList());
        
        // Transform edges for visualization
        List<Map<String, Object>> edges = viz.getEdges().values().stream()
                .filter(edge -> {
                    String sourceId = edge.getSourceNodeId();
                    String targetId = edge.getTargetNodeId();
                    
                    // Only include edges where both endpoints are accessible
                    return boundary.canInformationPass(sourceId, contextId) &&
                           boundary.canInformationPass(targetId, contextId) &&
                           viz.getNodes().containsKey(sourceId) &&
                           viz.getNodes().containsKey(targetId);
                })
                .map(edge -> {
                    Map<String, Object> edgeData = new HashMap<>();
                    edgeData.put("id", edge.getEdgeId());
                    edgeData.put("source", edge.getSourceNodeId());
                    edgeData.put("target", edge.getTargetNodeId());
                    edgeData.put("thickness", edge.getThickness());
                    edgeData.put("color", edge.getColor());
                    edgeData.put("style", edge.getStyle());
                    
                    // Special highlighting for edges connected to focus pattern
                    if (viz.getFocusPatternId() != null && 
                            (edge.getSourceNodeId().equals(viz.getFocusPatternId()) || 
                             edge.getTargetNodeId().equals(viz.getFocusPatternId()))) {
                        edgeData.put("focus", true);
                        edgeData.put("thickness", edge.getThickness() * 1.5);
                    }
                    
                    if (includeAttributes) {
                        edgeData.put("relationshipType", edge.getRelationshipType());
                        edgeData.put("similarity", edge.getSimilarity());
                        edgeData.put("visualAttributes", edge.getVisualAttributes());
                    }
                    
                    return edgeData;
                })
                .collect(Collectors.toList());
        
        // Compile final visualization data
        Map<String, Object> visualizationData = new HashMap<>();
        visualizationData.put("id", viz.getVisualizationId());
        visualizationData.put("title", viz.getTitle());
        visualizationData.put("description", viz.getDescription());
        visualizationData.put("timestamp", viz.getTimestamp());
        visualizationData.put("fractalDepth", viz.getFractalDepth());
        visualizationData.put("focusPatternId", viz.getFocusPatternId());
        visualizationData.put("nodes", nodes);
        visualizationData.put("edges", edges);
        visualizationData.put("nodeCount", nodes.size());
        visualizationData.put("edgeCount", edges.size());
        visualizationData.put("level", level);
        
        // Add a curated set of metadata
        Map<String, Object> renderedMetadata = new HashMap<>();
        viz.getMetadata().entrySet().stream()
                .filter(e -> !e.getKey().contains("internal"))
                .forEach(e -> renderedMetadata.put(e.getKey(), e.getValue()));
        
        visualizationData.put("metadata", renderedMetadata);
        
        return visualizationData;
    }
    
    /**
     * Gets all visualizations accessible in the given context.
     * 
     * @param contextId The context ID for boundary enforcement
     * @return List of visualization summaries
     */
    public List<Map<String, Object>> getAllVisualizations(String contextId) {
        return visualizations.entrySet().stream()
                .filter(entry -> boundary.canInformationPass(entry.getKey(), contextId))
                .map(entry -> {
                    PatternVisualization viz = entry.getValue();
                    
                    Map<String, Object> summary = new HashMap<>();
                    summary.put("id", viz.getVisualizationId());
                    summary.put("title", viz.getTitle());
                    summary.put("description", viz.getDescription());
                    summary.put("timestamp", viz.getTimestamp());
                    summary.put("nodeCount", viz.getNodes().size());
                    summary.put("edgeCount", viz.getEdges().size());
                    summary.put("fractalDepth", viz.getFractalDepth());
                    
                    if (viz.getFocusPatternId() != null) {
                        summary.put("focusPatternId", viz.getFocusPatternId());
                    }
                    
                    return summary;
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Analyzes a pattern visualization to extract insights.
     * 
     * @param visualizationId The visualization ID
     * @param contextId The context ID for boundary enforcement
     * @return Analysis results
     */
    public Map<String, Object> analyzeVisualization(String visualizationId, String contextId) {
        // Get the visualization
        Optional<PatternVisualization> optionalViz = getVisualization(visualizationId, contextId);
        if (!optionalViz.isPresent()) {
            throw new FractalBrowserException(
                    "Visualization not found or not accessible: " + visualizationId);
        }
        
        PatternVisualization viz = optionalViz.get();
        
        Map<String, Object> analysis = new HashMap<>();
        
        // Count patterns by type
        Map<String, Long> patternTypeCount = viz.getNodes().values().stream()
                .filter(node -> boundary.canInformationPass(node.getNodeId(), contextId))
                .map(node -> node.getPattern().getType())
                .collect(Collectors.groupingBy(type -> type, Collectors.counting()));
        
        analysis.put("patternTypeDistribution", patternTypeCount);
        
        // Calculate connectivity metrics
        Map<String, Integer> nodeDegrees = new HashMap<>();
        
        for (PatternNode node : viz.getNodes().values()) {
            if (!boundary.canInformationPass(node.getNodeId(), contextId)) {
                continue;
            }
            
            int degree = 0;
            for (PatternEdge edge : viz.getEdges().values()) {
                if ((edge.getSourceNodeId().equals(node.getNodeId()) &&
                     boundary.canInformationPass(edge.getTargetNodeId(), contextId)) ||
                    (edge.getTargetNodeId().equals(node.getNodeId()) &&
                     boundary.canInformationPass(edge.getSourceNodeId(), contextId))) {
                    degree++;
                }
            }
            
            nodeDegrees.put(node.getNodeId(), degree);
        }
        
        // Calculate average degree
        double avgDegree = nodeDegrees.values().stream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0);
        
        analysis.put("averageDegree", avgDegree);
        
        // Identify central patterns (high connectivity)
        List<Map<String, Object>> centralPatterns = nodeDegrees.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .map(entry -> {
                    PatternNode node = viz.getNodes().get(entry.getKey());
                    Pattern pattern = node.getPattern();
                    
                    Map<String, Object> patternData = new HashMap<>();
                    patternData.put("id", pattern.getId());
                    patternData.put("type", pattern.getType());
                    patternData.put("description", pattern.getDescription());
                    patternData.put("degree", entry.getValue());
                    
                    return patternData;
                })
                .collect(Collectors.toList());
        
        analysis.put("centralPatterns", centralPatterns);
        
        // Calculate average similarity
        double avgSimilarity = viz.getEdges().values().stream()
                .filter(edge -> 
                        boundary.canInformationPass(edge.getSourceNodeId(), contextId) &&
                        boundary.canInformationPass(edge.getTargetNodeId(), contextId))
                .mapToDouble(PatternEdge::getSimilarity)
                .average()
                .orElse(0);
        
        analysis.put("averageSimilarity", avgSimilarity);
        
        // Identify clusters of highly connected patterns
        List<Set<String>> clusters = identifyClusters(viz, 0.7, contextId);
        
        List<Map<String, Object>> clusterData = new ArrayList<>();
        for (int i = 0; i < clusters.size(); i++) {
            Set<String> cluster = clusters.get(i);
            
            Map<String, Object> clusterInfo = new HashMap<>();
            clusterInfo.put("id", "cluster_" + i);
            clusterInfo.put("size", cluster.size());
            clusterInfo.put("patterns", new ArrayList<>(cluster));
            
            // Count pattern types in cluster
            Map<String, Long> clusterTypeCount = cluster.stream()
                    .map(id -> viz.getNodes().get(id).getPattern().getType())
                    .collect(Collectors.groupingBy(type -> type, Collectors.counting()));
            
            clusterInfo.put("typeDistribution", clusterTypeCount);
            
            clusterData.add(clusterInfo);
        }
        
        analysis.put("clusters", clusterData);
        
        // Calculate fractal metrics
        Map<String, Object> fractalMetrics = new HashMap<>();
        
        // Self-similarity index
        double selfSimilarity = calculateSelfSimilarityForVisualization(viz, contextId);
        fractalMetrics.put("selfSimilarity", selfSimilarity);
        
        // Pattern complexity distribution
        Map<String, Double> complexityByType = viz.getNodes().values().stream()
                .filter(node -> boundary.canInformationPass(node.getNodeId(), contextId))
                .collect(Collectors.groupingBy(
                        node -> node.getPattern().getType(),
                        Collectors.averagingDouble(node -> calculatePatternComplexity(node.getPattern()))));
        
        fractalMetrics.put("complexityByType", complexityByType);
        
        // Recursion metrics
        fractalMetrics.put("recursionDepth", viz.getFractalDepth());
        fractalMetrics.put("focusDominated", 
                viz.getFocusPatternId() != null && nodeDegrees.getOrDefault(viz.getFocusPatternId(), 0) > avgDegree * 2);
        
        analysis.put("fractalMetrics", fractalMetrics);
        
        return analysis;
    }
    
    /**
     * Identifies clusters of highly connected patterns.
     * 
     * @param viz The pattern visualization
     * @param similarityThreshold Minimum similarity for cluster membership
     * @param contextId The context ID for boundary enforcement
     * @return List of pattern ID sets representing clusters
     */
    private List<Set<String>> identifyClusters(
            PatternVisualization viz, 
            double similarityThreshold,
            String contextId) {
        
        // Create a graph of pattern connections above threshold
        Map<String, Set<String>> connections = new HashMap<>();
        
        for (PatternEdge edge : viz.getEdges().values()) {
            if (edge.getSimilarity() >= similarityThreshold) {
                String sourceId = edge.getSourceNodeId();
                String targetId = edge.getTargetNodeId();
                
                // Skip if either endpoint is not accessible
                if (!boundary.canInformationPass(sourceId, contextId) ||
                        !boundary.canInformationPass(targetId, contextId)) {
                    continue;
                }
                
                connections.computeIfAbsent(sourceId, k -> new HashSet<>()).add(targetId);
                connections.computeIfAbsent(targetId, k -> new HashSet<>()).add(sourceId);
            }
        }
        
        // Identify connected components (clusters)
        List<Set<String>> clusters = new ArrayList<>();
        Set<String> processed = new HashSet<>();
        
        for (String nodeId : connections.keySet()) {
            if (processed.contains(nodeId)) {
                continue;
            }
            
            // Find all connected nodes (BFS)
            Set<String> cluster = new HashSet<>();
            Set<String> queue = new HashSet<>();
            queue.add(nodeId);
            
            while (!queue.isEmpty()) {
                String current = queue.iterator().next();
                queue.remove(current);
                
                if (processed.contains(current)) {
                    continue;
                }
                
                cluster.add(current);
                processed.add(current);
                
                // Add connected nodes to queue
                Set<String> connected = connections.getOrDefault(current, Collections.emptySet());
                for (String connectedId : connected) {
                    if (!processed.contains(connectedId)) {
                        queue.add(connectedId);
                    }
                }
            }
            
            // Add cluster if not empty
            if (!cluster.isEmpty()) {
                clusters.add(cluster);
            }
        }
        
        // Add isolated nodes as single-node clusters
        for (String nodeId : viz.getNodes().keySet()) {
            if (!processed.contains(nodeId) && boundary.canInformationPass(nodeId, contextId)) {
                Set<String> singletonCluster = new HashSet<>();
                singletonCluster.add(nodeId);
                clusters.add(singletonCluster);
            }
        }
        
        return clusters;
    }
    
    /**
     * Calculates self-similarity for a visualization.
     * 
     * @param viz The pattern visualization
     * @param contextId The context ID for boundary enforcement
     * @return Self-similarity score (0.0-1.0)
     */
    private double calculateSelfSimilarityForVisualization(
            PatternVisualization viz, 
            String contextId) {
        
        List<Pattern> patterns = viz.getNodes().values().stream()
                .filter(node -> boundary.canInformationPass(node.getNodeId(), contextId))
                .map(PatternNode::getPattern)
                .collect(Collectors.toList());
        
        return calculateSelfSimilarity(patterns);
    }
}