package com.fractal.browser.collective.processing;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fractal.browser.collective.boundaries.InformationBoundary;
import com.fractal.browser.collective.memory.DistributedInsightRepository;
import com.fractal.browser.collective.memory.TemporalIndexing;
import com.fractal.browser.exceptions.FractalBrowserException;
import com.fractal.browser.model.Pattern;

/**
 * EmergentPatternDetector identifies emergent patterns across the collective system using
 * multi-scale fractal analysis. It detects patterns that emerge at various levels of 
 * organization that may not be visible at individual node level.
 * 
 * This class applies techniques from complexity science to identify self-organizing behavior,
 * bifurcation points, and phase transitions in the collective system dynamics.
 */
public class EmergentPatternDetector {

    // Core dependencies
    private final DistributedInsightRepository insightRepository;
    private final TemporalIndexing temporalIndexing;
    private final InformationBoundary boundary;
    
    // Pattern detection configuration
    private final int minPatternFrequency;
    private final double patternSimilarityThreshold;
    private final int maxPatternSize;
    
    // Pattern tracking
    private final Map<String, Pattern> detectedPatterns;
    
    /**
     * Creates a new EmergentPatternDetector instance.
     * 
     * @param insightRepository Repository for storing and retrieving insights
     * @param temporalIndexing Service for temporal pattern analysis
     * @param boundary Information boundary for enforcing access controls
     * @param minPatternFrequency Minimum frequency for pattern detection
     * @param patternSimilarityThreshold Threshold for determining pattern similarity (0.0-1.0)
     * @param maxPatternSize Maximum number of elements in a pattern
     */
    public EmergentPatternDetector(
            DistributedInsightRepository insightRepository,
            TemporalIndexing temporalIndexing,
            InformationBoundary boundary,
            int minPatternFrequency,
            double patternSimilarityThreshold,
            int maxPatternSize) {
        
        this.insightRepository = insightRepository;
        this.temporalIndexing = temporalIndexing;
        this.boundary = boundary;
        this.minPatternFrequency = minPatternFrequency;
        this.patternSimilarityThreshold = patternSimilarityThreshold;
        this.maxPatternSize = maxPatternSize;
        
        this.detectedPatterns = new ConcurrentHashMap<>();
    }
    
    /**
     * Detects emergent patterns from recent insights.
     * 
     * @param lookbackTimeMs Time window to analyze in milliseconds
     * @param contextId The context ID for boundary enforcement
     * @return A list of detected patterns
     */
    public List<Pattern> detectEmergentPatterns(long lookbackTimeMs, String contextId) {
        // Start with temporal patterns
        List<Map<String, Object>> temporalPatterns = detectTemporalPatterns(lookbackTimeMs, contextId);
        
        // Detect attribute patterns
        List<Map<String, Object>> attributePatterns = detectAttributePatterns(lookbackTimeMs, contextId);
        
        // Detect sequence patterns
        List<Map<String, Object>> sequencePatterns = detectSequencePatterns(lookbackTimeMs, contextId);
        
        // Combine and classify patterns
        List<Pattern> combinedPatterns = new ArrayList<>();
        
        // Convert temporal patterns to Pattern objects
        for (Map<String, Object> patternData : temporalPatterns) {
            Pattern pattern = createPatternFromData(patternData, "TEMPORAL", contextId);
            combinedPatterns.add(pattern);
            detectedPatterns.put(pattern.getId(), pattern);
        }
        
        // Convert attribute patterns to Pattern objects
        for (Map<String, Object> patternData : attributePatterns) {
            Pattern pattern = createPatternFromData(patternData, "ATTRIBUTE", contextId);
            combinedPatterns.add(pattern);
            detectedPatterns.put(pattern.getId(), pattern);
        }
        
        // Convert sequence patterns to Pattern objects
        for (Map<String, Object> patternData : sequencePatterns) {
            Pattern pattern = createPatternFromData(patternData, "SEQUENCE", contextId);
            combinedPatterns.add(pattern);
            detectedPatterns.put(pattern.getId(), pattern);
        }
        
        // Detect meta-patterns (patterns of patterns)
        List<Pattern> metaPatterns = detectMetaPatterns(combinedPatterns, contextId);
        combinedPatterns.addAll(metaPatterns);
        
        return combinedPatterns;
    }
    
    /**
     * Detects temporal patterns based on timestamp distributions.
     * 
     * @param lookbackTimeMs Time window to analyze
     * @param contextId The context ID for boundary enforcement
     * @return List of temporal pattern data
     */
    private List<Map<String, Object>> detectTemporalPatterns(long lookbackTimeMs, String contextId) {
        // Use the temporal indexing system to find patterns
        return temporalIndexing.findTemporalPatterns(lookbackTimeMs/10, 3, contextId);
    }
    
    /**
     * Detects patterns based on attribute combinations.
     * 
     * @param lookbackTimeMs Time window to analyze
     * @param contextId The context ID for boundary enforcement
     * @return List of attribute pattern data
     */
    private List<Map<String, Object>> detectAttributePatterns(long lookbackTimeMs, String contextId) {
        List<Map<String, Object>> attributePatterns = new ArrayList<>();
        
        // Get recent insights from the repository
        List<Map<String, Object>> recentInsights = retrieveRecentInsights(lookbackTimeMs, contextId);
        
        // Extract all attribute keys
        Set<String> attributeKeys = new HashSet<>();
        for (Map<String, Object> insight : recentInsights) {
            attributeKeys.addAll(insight.keySet().stream()
                    .filter(key -> !key.startsWith("_") && !key.equals("content"))
                    .collect(Collectors.toSet()));
        }
        
        // For each attribute, analyze value distributions
        for (String attribute : attributeKeys) {
            Map<Object, List<Map<String, Object>>> valueGroups = new HashMap<>();
            
            // Group insights by attribute value
            for (Map<String, Object> insight : recentInsights) {
                if (insight.containsKey(attribute)) {
                    Object value = insight.get(attribute);
                    
                    valueGroups.computeIfAbsent(value, k -> new ArrayList<>())
                            .add(insight);
                }
            }
            
            // Find frequent value combinations
            for (Map.Entry<Object, List<Map<String, Object>>> entry : valueGroups.entrySet()) {
                Object value = entry.getKey();
                List<Map<String, Object>> insights = entry.getValue();
                
                if (insights.size() >= minPatternFrequency) {
                    // Look for other attribute correlations within this group
                    Map<String, Map<Object, Integer>> attributeCorrelations = new HashMap<>();
                    
                    for (Map<String, Object> insight : insights) {
                        for (String otherAttr : attributeKeys) {
                            if (otherAttr.equals(attribute) || !insight.containsKey(otherAttr)) {
                                continue;
                            }
                            
                            Object otherValue = insight.get(otherAttr);
                            
                            attributeCorrelations.computeIfAbsent(otherAttr, k -> new HashMap<>())
                                    .put(otherValue, 
                                            attributeCorrelations.get(otherAttr)
                                                    .getOrDefault(otherValue, 0) + 1);
                        }
                    }
                    
                    // Find correlated attributes that appear frequently
                    Map<String, Object> correlatedAttrs = new HashMap<>();
                    
                    for (Map.Entry<String, Map<Object, Integer>> attrEntry : attributeCorrelations.entrySet()) {
                        String otherAttr = attrEntry.getKey();
                        Map<Object, Integer> valueCounts = attrEntry.getValue();
                        
                        Optional<Map.Entry<Object, Integer>> mostFrequent = valueCounts.entrySet().stream()
                                .max(Map.Entry.comparingByValue());
                        
                        if (mostFrequent.isPresent() && 
                                mostFrequent.get().getValue() >= minPatternFrequency) {
                            correlatedAttrs.put(otherAttr, mostFrequent.get().getKey());
                        }
                    }
                    
                    // If we found correlations, create a pattern
                    if (!correlatedAttrs.isEmpty()) {
                        Map<String, Object> patternData = new HashMap<>();
                        patternData.put("primaryAttribute", attribute);
                        patternData.put("primaryValue", value);
                        patternData.put("correlatedAttributes", correlatedAttrs);
                        patternData.put("frequency", insights.size());
                        patternData.put("insightIds", 
                                insights.stream()
                                        .map(i -> i.getOrDefault("_id", "unknown"))
                                        .collect(Collectors.toList()));
                        
                        attributePatterns.add(patternData);
                    }
                }
            }
        }
        
        return attributePatterns;
    }
    
    /**
     * Detects sequence patterns in the order of insights.
     * 
     * @param lookbackTimeMs Time window to analyze
     * @param contextId The context ID for boundary enforcement
     * @return List of sequence pattern data
     */
    private List<Map<String, Object>> detectSequencePatterns(long lookbackTimeMs, String contextId) {
        List<Map<String, Object>> sequencePatterns = new ArrayList<>();
        
        // Get recent insights in temporal order
        List<Map<String, Object>> orderedInsights = retrieveRecentInsightsByTime(lookbackTimeMs, contextId);
        
        // Apply a sliding window to detect n-grams (sequences)
        for (int n = 2; n <= maxPatternSize; n++) {
            // Skip if we don't have enough insights for this n-gram size
            if (orderedInsights.size() < n) {
                continue;
            }
            
            // Map to track n-gram frequencies
            Map<String, List<Integer>> ngramPositions = new HashMap<>();
            
            // Slide the window across the ordered insights
            for (int i = 0; i <= orderedInsights.size() - n; i++) {
                // Extract the n-gram signature (we'll use attribute patterns)
                StringBuilder signatureBuilder = new StringBuilder();
                
                for (int j = 0; j < n; j++) {
                    Map<String, Object> insight = orderedInsights.get(i + j);
                    
                    // Use the most distinctive attributes for the signature
                    String signature = generateInsightSignature(insight);
                    signatureBuilder.append(signature);
                    
                    if (j < n - 1) {
                        signatureBuilder.append("->");
                    }
                }
                
                String ngramSignature = signatureBuilder.toString();
                
                // Record this position
                ngramPositions.computeIfAbsent(ngramSignature, k -> new ArrayList<>())
                        .add(i);
            }
            
            // Find frequent n-grams
            for (Map.Entry<String, List<Integer>> entry : ngramPositions.entrySet()) {
                String ngramSignature = entry.getKey();
                List<Integer> positions = entry.getValue();
                
                if (positions.size() >= minPatternFrequency) {
                    // This is a frequent sequence pattern
                    Map<String, Object> patternData = new HashMap<>();
                    patternData.put("sequence", ngramSignature);
                    patternData.put("size", n);
                    patternData.put("frequency", positions.size());
                    
                    // Collect insight IDs in these sequences
                    List<List<String>> insightSequences = new ArrayList<>();
                    
                    for (int pos : positions) {
                        List<String> sequenceIds = new ArrayList<>();
                        
                        for (int j = 0; j < n; j++) {
                            Map<String, Object> insight = orderedInsights.get(pos + j);
                            sequenceIds.add((String) insight.getOrDefault("_id", "unknown"));
                        }
                        
                        insightSequences.add(sequenceIds);
                    }
                    
                    patternData.put("insightSequences", insightSequences);
                    sequencePatterns.add(patternData);
                }
            }
        }
        
        return sequencePatterns;
    }
    
    /**
     * Detects patterns of patterns (meta-patterns).
     * 
     * @param patterns List of already detected patterns
     * @param contextId The context ID for boundary enforcement
     * @return List of meta-patterns
     */
    private List<Pattern> detectMetaPatterns(List<Pattern> patterns, String contextId) {
        List<Pattern> metaPatterns = new ArrayList<>();
        
        // Group patterns by type
        Map<String, List<Pattern>> typeGroups = patterns.stream()
                .collect(Collectors.groupingBy(Pattern::getType));
        
        // Look for co-occurring patterns
        Set<String> processedCombos = new HashSet<>();
        
        for (int i = 0; i < patterns.size(); i++) {
            Pattern p1 = patterns.get(i);
            
            for (int j = i + 1; j < patterns.size(); j++) {
                Pattern p2 = patterns.get(j);
                
                // Skip if same type (we want cross-type meta-patterns)
                if (p1.getType().equals(p2.getType())) {
                    continue;
                }
                
                // Generate a unique key for this pair
                String comboKey = p1.getId() + ":" + p2.getId();
                if (processedCombos.contains(comboKey)) {
                    continue;
                }
                processedCombos.add(comboKey);
                
                // Check for overlap in insights
                Set<String> insightsInP1 = extractInsightIds(p1);
                Set<String> insightsInP2 = extractInsightIds(p2);
                
                // Find intersection
                Set<String> commonInsights = new HashSet<>(insightsInP1);
                commonInsights.retainAll(insightsInP2);
                
                // Calculate Jaccard similarity
                int intersectionSize = commonInsights.size();
                int unionSize = insightsInP1.size() + insightsInP2.size() - intersectionSize;
                double similarity = unionSize > 0 ? (double) intersectionSize / unionSize : 0;
                
                // If patterns are significantly related, create a meta-pattern
                if (similarity >= patternSimilarityThreshold && intersectionSize >= minPatternFrequency) {
                    String metaId = UUID.randomUUID().toString();
                    
                    Map<String, Object> metaAttributes = new HashMap<>();
                    metaAttributes.put("pattern1Id", p1.getId());
                    metaAttributes.put("pattern1Type", p1.getType());
                    metaAttributes.put("pattern2Id", p2.getId());
                    metaAttributes.put("pattern2Type", p2.getType());
                    metaAttributes.put("similarity", similarity);
                    metaAttributes.put("commonInsightCount", intersectionSize);
                    metaAttributes.put("commonInsights", new ArrayList<>(commonInsights));
                    
                    Pattern metaPattern = new Pattern(
                            metaId,
                            "META",
                            "Meta-pattern between " + p1.getType() + " and " + p2.getType(),
                            metaAttributes,
                            System.currentTimeMillis());
                    
                    metaPatterns.add(metaPattern);
                    detectedPatterns.put(metaId, metaPattern);
                }
            }
        }
        
        return metaPatterns;
    }
    
    /**
     * Generates a signature for an insight based on its most distinctive attributes.
     * 
     * @param insight The insight to generate a signature for
     * @return The signature string
     */
    private String generateInsightSignature(Map<String, Object> insight) {
        StringBuilder signature = new StringBuilder();
        
        // Use type if available
        if (insight.containsKey("type")) {
            signature.append("TYPE:").append(insight.get("type"));
        }
        
        // Use category if available
        if (insight.containsKey("category")) {
            if (signature.length() > 0) {
                signature.append(",");
            }
            signature.append("CAT:").append(insight.get("category"));
        }
        
        // Use most specific attribute if no type/category
        if (signature.length() == 0) {
            // Find the attribute with the lowest frequency
            String mostSpecificAttr = insight.keySet().stream()
                    .filter(key -> !key.startsWith("_") && !key.equals("content"))
                    .findFirst()
                    .orElse("_id");
            
            signature.append(mostSpecificAttr).append(":")
                    .append(insight.get(mostSpecificAttr));
        }
        
        return signature.toString();
    }
    
    /**
     * Extracts all insight IDs referenced in a pattern.
     * 
     * @param pattern The pattern to extract from
     * @return Set of insight IDs
     */
    private Set<String> extractInsightIds(Pattern pattern) {
        Set<String> insightIds = new HashSet<>();
        Map<String, Object> attributes = pattern.getAttributes();
        
        // Extract IDs from various pattern types
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
        
        return insightIds;
    }
    
    /**
     * Creates a Pattern object from detected pattern data.
     * 
     * @param patternData The raw pattern data
     * @param patternType The type of pattern
     * @param contextId The context ID for boundary enforcement
     * @return A Pattern object
     */
    private Pattern createPatternFromData(
            Map<String, Object> patternData, 
            String patternType,
            String contextId) {
        
        String patternId = UUID.randomUUID().toString();
        
        // Generate a description based on the pattern type and data
        String description = generatePatternDescription(patternData, patternType);
        
        // Create the pattern with all the data as attributes
        Pattern pattern = new Pattern(
                patternId,
                patternType,
                description,
                new HashMap<>(patternData),
                System.currentTimeMillis());
        
        // Store the pattern in the repository
        insightRepository.storeInsight(
                "pattern:" + patternType.toLowerCase(),
                patternData,
                Set.of("pattern", patternType.toLowerCase()),
                contextId);
        
        return pattern;
    }
    
    /**
     * Generates a human-readable description of a pattern.
     * 
     * @param patternData The pattern data
     * @param patternType The type of pattern
     * @return A description string
     */
    private String generatePatternDescription(Map<String, Object> patternData, String patternType) {
        StringBuilder description = new StringBuilder();
        
        switch (patternType) {
            case "TEMPORAL":
                description.append("Temporal pattern with ")
                        .append(patternData.get("itemCount"))
                        .append(" items between ")
                        .append(patternData.get("startTime"))
                        .append(" and ")
                        .append(patternData.get("endTime"));
                break;
                
            case "ATTRIBUTE":
                description.append("Attribute pattern: ")
                        .append(patternData.get("primaryAttribute"))
                        .append(" = ")
                        .append(patternData.get("primaryValue"))
                        .append(" correlates with ")
                        .append(((Map<?, ?>) patternData.get("correlatedAttributes")).size())
                        .append(" other attributes");
                break;
                
            case "SEQUENCE":
                description.append("Sequence pattern of ")
                        .append(patternData.get("size"))
                        .append(" steps occurring ")
                        .append(patternData.get("frequency"))
                        .append(" times: ")
                        .append(patternData.get("sequence"));
                break;
                
            default:
                description.append("Pattern of type ")
                        .append(patternType);
        }
        
        return description.toString();
    }
    
    /**
     * Retrieves recent insights based on the specified time window.
     * 
     * @param lookbackTimeMs Time window to look back
     * @param contextId The context ID for boundary enforcement
     * @return List of recent insights
     */
    private List<Map<String, Object>> retrieveRecentInsights(long lookbackTimeMs, String contextId) {
        long currentTime = System.currentTimeMillis();
        long startTime = currentTime - lookbackTimeMs;
        
        // Use timestamp-based retrieval if available
        if (temporalIndexing != null) {
            return temporalIndexing.findItemsInTimeRange(
                    java.time.Instant.ofEpochMilli(startTime),
                    java.time.Instant.ofEpochMilli(currentTime),
                    contextId)
                    .stream()
                    .map(id -> insightRepository.getInsight(id, contextId))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } else {
            // Fall back to repository's recent items functionality
            return insightRepository.findRecent(100, contextId);
        }
    }
    
    /**
     * Retrieves recent insights in temporal order.
     * 
     * @param lookbackTimeMs Time window to look back
     * @param contextId The context ID for boundary enforcement
     * @return List of recent insights ordered by time
     */
    private List<Map<String, Object>> retrieveRecentInsightsByTime(long lookbackTimeMs, String contextId) {
        List<Map<String, Object>> insights = retrieveRecentInsights(lookbackTimeMs, contextId);
        
        // Sort by timestamp
        insights.sort((i1, i2) -> {
            long t1 = i1.containsKey("_timestamp") 
                    ? ((Number) i1.get("_timestamp")).longValue() 
                    : 0;
            long t2 = i2.containsKey("_timestamp") 
                    ? ((Number) i2.get("_timestamp")).longValue() 
                    : 0;
            
            return Long.compare(t1, t2);
        });
        
        return insights;
    }
    
    /**
     * Gets a detected pattern by ID.
     * 
     * @param patternId The pattern ID
     * @param contextId The context ID for boundary checks
     * @return An Optional containing the pattern if found
     */
    public Optional<Pattern> getPattern(String patternId, String contextId) {
        Pattern pattern = detectedPatterns.get(patternId);
        
        if (pattern == null || !boundary.canInformationPass(patternId, contextId)) {
            return Optional.empty();
        }
        
        return Optional.of(pattern);
    }
    
    /**
     * Gets all detected patterns of a specific type.
     * 
     * @param patternType The pattern type to filter by
     * @param contextId The context ID for boundary checks
     * @return List of patterns of the specified type
     */
    public List<Pattern> getPatternsByType(String patternType, String contextId) {
        return detectedPatterns.values().stream()
                .filter(p -> p.getType().equals(patternType))
                .filter(p -> boundary.canInformationPass(p.getId(), contextId))
                .collect(Collectors.toList());
    }
    
    /**
     * Analyzes the fractal properties of detected patterns.
     * 
     * @param contextId The context ID for boundary checks
     * @return Map of fractal metrics
     */
    public Map<String, Object> analyzeFractalProperties(String contextId) {
        Map<String, Object> fractalMetrics = new HashMap<>();
        
        // Count patterns by type
        Map<String, Long> patternTypeCounts = detectedPatterns.values().stream()
                .filter(p -> boundary.canInformationPass(p.getId(), contextId))
                .collect(Collectors.groupingBy(Pattern::getType, Collectors.counting()));
        
        fractalMetrics.put("patternCounts", patternTypeCounts);
        
        // Calculate pattern complexity (average size/elements)
        Map<String, Double> complexityByType = new HashMap<>();
        
        // Calculate complexity for each pattern type
        for (String type : patternTypeCounts.keySet()) {
            double avgComplexity = detectedPatterns.values().stream()
                    .filter(p -> p.getType().equals(type))
                    .filter(p -> boundary.canInformationPass(p.getId(), contextId))
                    .mapToDouble(this::calculatePatternComplexity)
                    .average()
                    .orElse(0);
            
            complexityByType.put(type, avgComplexity);
        }
        
        fractalMetrics.put("patternComplexity", complexityByType);
        
        // Calculate pattern nesting (how deep the pattern hierarchy goes)
        int maxNestingLevel = calculateMaxNestingLevel(contextId);
        fractalMetrics.put("maxNestingLevel", maxNestingLevel);
        
        // Calculate self-similarity (patterns that repeat at different scales)
        double selfSimilarityIndex = calculateSelfSimilarityIndex(contextId);
        fractalMetrics.put("selfSimilarityIndex", selfSimilarityIndex);
        
        return fractalMetrics;
    }
    
    /**
     * Calculates the complexity of a pattern.
     * 
     * @param pattern The pattern to analyze
     * @return Complexity score
     */
    private double calculatePatternComplexity(Pattern pattern) {
        Map<String, Object> attributes = pattern.getAttributes();
        
        // Count the number of elements involved
        int elementCount = 0;
        
        if (attributes.containsKey("insightIds")) {
            @SuppressWarnings("unchecked")
            List<?> ids = (List<?>) attributes.get("insightIds");
            elementCount += ids.size();
        }
        
        if (attributes.containsKey("items")) {
            @SuppressWarnings("unchecked")
            List<?> items = (List<?>) attributes.get("items");
            elementCount += items.size();
        }
        
        if (attributes.containsKey("correlatedAttributes")) {
            @SuppressWarnings("unchecked")
            Map<?, ?> correlated = (Map<?, ?>) attributes.get("correlatedAttributes");
            elementCount += correlated.size();
        }
        
        if (attributes.containsKey("insightSequences")) {
            @SuppressWarnings("unchecked")
            List<List<?>> sequences = (List<List<?>>) attributes.get("insightSequences");
            elementCount += sequences.stream().mapToInt(List::size).sum();
        }
        
        return elementCount;
    }
    
    /**
     * Calculates the maximum nesting level in the pattern hierarchy.
     * 
     * @param contextId The context ID for boundary checks
     * @return Maximum nesting level
     */
    private int calculateMaxNestingLevel(String contextId) {
        // Get meta-patterns
        List<Pattern> metaPatterns = getPatternsByType("META", contextId);
        
        if (metaPatterns.isEmpty()) {
            return 1; // Only base patterns
        }
        
        // Build a graph of pattern dependencies
        Map<String, Set<String>> patternDependencies = new HashMap<>();
        
        for (Pattern meta : metaPatterns) {
            Map<String, Object> attrs = meta.getAttributes();
            String pattern1Id = (String) attrs.get("pattern1Id");
            String pattern2Id = (String) attrs.get("pattern2Id");
            
            patternDependencies.computeIfAbsent(meta.getId(), k -> new HashSet<>())
                    .add(pattern1Id);
            patternDependencies.computeIfAbsent(meta.getId(), k -> new HashSet<>())
                    .add(pattern2Id);
        }
        
        // Find the longest dependency chain
        int maxDepth = 0;
        
        for (String patternId : patternDependencies.keySet()) {
            int depth = calculateDependencyDepth(patternId, patternDependencies, new HashSet<>());
            maxDepth = Math.max(maxDepth, depth);
        }
        
        return maxDepth;
    }
    
    /**
     * Recursively calculates the dependency depth of a pattern.
     * 
     * @param patternId The pattern ID
     * @param dependencies Map of pattern dependencies
     * @param visited Set of already visited patterns
     * @return The dependency depth
     */
    private int calculateDependencyDepth(
            String patternId, 
            Map<String, Set<String>> dependencies,
            Set<String> visited) {
        
        if (visited.contains(patternId)) {
            return 0; // Avoid cycles
        }
        
        visited.add(patternId);
        
        if (!dependencies.containsKey(patternId)) {
            return 1; // Base pattern
        }
        
        int maxChildDepth = 0;
        
        for (String childId : dependencies.get(patternId)) {
            int childDepth = calculateDependencyDepth(childId, dependencies, visited);
            maxChildDepth = Math.max(maxChildDepth, childDepth);
        }
        
        return 1 + maxChildDepth;
    }
    
    /**
     * Calculates a self-similarity index for detected patterns.
     * 
     * @param contextId The context ID for boundary checks
     * @return Self-similarity index (0.0-1.0)
     */
    private double calculateSelfSimilarityIndex(String contextId) {
        // This would implement a proper self-similarity measure
        // For simplicity, we'll calculate a simple index based on
        // the distribution of pattern types
        
        long totalPatterns = detectedPatterns.values().stream()
                .filter(p -> boundary.canInformationPass(p.getId(), contextId))
                .count();
        
        if (totalPatterns == 0) {
            return 0.0;
        }
        
        // Get counts by type
        Map<String, Long> patternTypeCounts = detectedPatterns.values().stream()
                .filter(p -> boundary.canInformationPass(p.getId(), contextId))
                .collect(Collectors.groupingBy(Pattern::getType, Collectors.counting()));
        
        // Calculate Shannon entropy of the distribution
        double entropy = 0.0;
        
        for (Long count : patternTypeCounts.values()) {
            double probability = (double) count / totalPatterns;
            entropy -= probability * Math.log(probability) / Math.log(2);
        }
        
        // Normalize by maximum possible entropy
        double maxEntropy = Math.log(patternTypeCounts.size()) / Math.log(2);
        
        if (maxEntropy == 0) {
            return 1.0; // Only one type of pattern
        }
        
        // Self-similarity index is inverse of normalized entropy
        return 1.0 - (entropy / maxEntropy);
    }
}