package com.fractal.browser.collective.memory;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.time.Instant;
import java.util.UUID;
import java.util.Optional;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fractal.browser.collective.boundaries.InformationBoundary;
import com.fractal.browser.model.Pattern;

/**
 * DistributedInsightRepository implements a distributed memory system for the collective, 
 * storing insights, patterns, and other cognitive artifacts. It manages distributed storage
 * and retrieval across multiple nodes while maintaining coherence.
 * 
 * This class provides mechanisms for recursive storage and access to collective insights
 * at multiple scales.
 */
public class DistributedInsightRepository {
    
    // Maps insight IDs to their data
    private final Map<String, Insight> insightRegistry;
    
    // Maps tags to insight IDs
    private final Map<String, Set<String>> tagIndex;
    
    // Maps pattern IDs to insight IDs
    private final Map<String, Set<String>> patternIndex;
    
    // Maps contexts to insight IDs
    private final Map<String, Set<String>> contextIndex;
    
    // Information boundary for access control
    private final InformationBoundary boundary;
    
    // Persistence strategy for durable storage
    private final PersistenceStrategy persistenceStrategy;
    
    /**
     * Represents an insight stored in the repository.
     */
    public static class Insight {
        private final String insightId;
        private final String sourceNodeId;
        private final String content;
        private final Set<String> tags;
        private final Map<String, Object> metadata;
        private final Instant timestamp;
        private final Instant lastAccessed;
        private final List<Pattern> associatedPatterns;
        private final double significance;
        private int accessCount;
        
        /**
         * Creates a new insight.
         */
        public Insight(String sourceNodeId, String content, Set<String> tags, 
                Map<String, Object> metadata, double significance) {
            this.insightId = UUID.randomUUID().toString();
            this.sourceNodeId = sourceNodeId;
            this.content = content;
            this.tags = new HashSet<>(tags);
            this.metadata = new HashMap<>(metadata);
            this.timestamp = Instant.now();
            this.lastAccessed = this.timestamp;
            this.associatedPatterns = new ArrayList<>();
            this.significance = significance;
            this.accessCount = 0;
        }
        
        /**
         * Creates an insight with an existing ID (for loading from persistence).
         */
        public Insight(String insightId, String sourceNodeId, String content, Set<String> tags,
                Map<String, Object> metadata, Instant timestamp, Instant lastAccessed,
                List<Pattern> associatedPatterns, double significance, int accessCount) {
            this.insightId = insightId;
            this.sourceNodeId = sourceNodeId;
            this.content = content;
            this.tags = new HashSet<>(tags);
            this.metadata = new HashMap<>(metadata);
            this.timestamp = timestamp;
            this.lastAccessed = lastAccessed;
            this.associatedPatterns = new ArrayList<>(associatedPatterns);
            this.significance = significance;
            this.accessCount = accessCount;
        }
        
        // Getters
        public String getInsightId() { return insightId; }
        public String getSourceNodeId() { return sourceNodeId; }
        public String getContent() { return content; }
        public Set<String> getTags() { return Collections.unmodifiableSet(tags); }
        public Map<String, Object> getMetadata() { return Collections.unmodifiableMap(metadata); }
        public Instant getTimestamp() { return timestamp; }
        public Instant getLastAccessed() { return lastAccessed; }
        public List<Pattern> getAssociatedPatterns() { return Collections.unmodifiableList(associatedPatterns); }
        public double getSignificance() { return significance; }
        public int getAccessCount() { return accessCount; }
    }
    
    /**
     * Creates a new DistributedInsightRepository with the specified components.
     */
    public DistributedInsightRepository(InformationBoundary boundary, PersistenceStrategy persistenceStrategy) {
        this.insightRegistry = new ConcurrentHashMap<>();
        this.tagIndex = new ConcurrentHashMap<>();
        this.patternIndex = new ConcurrentHashMap<>();
        this.contextIndex = new ConcurrentHashMap<>();
        this.boundary = boundary;
        this.persistenceStrategy = persistenceStrategy;
        
        // Load existing insights from persistence if available
        loadFromPersistence();
    }
    
    /**
     * Loads existing insights from the persistence layer.
     */
    private void loadFromPersistence() {
        if (persistenceStrategy != null) {
            try {
                List<Map<String, Object>> persistedInsights = persistenceStrategy.loadAll("insights");
                
                for (Map<String, Object> data : persistedInsights) {
                    // Parse and create Insight from persisted data
                    // In a real implementation, this would handle all the necessary conversions
                    
                    // For now, just log that we would load insights
                    System.out.println("Would load insight: " + data.get("insightId"));
                }
            } catch (Exception e) {
                System.err.println("Error loading insights from persistence: " + e.getMessage());
            }
        }
    }
    
    /**
     * Stores a new insight in the repository.
     * 
     * @param sourceNodeId The source node ID
     * @param content The insight content
     * @param tags Tags associated with the insight
     * @param metadata Additional metadata
     * @param significance The significance level (0.0-1.0)
     * @return The created insight
     */
    public Insight storeInsight(String sourceNodeId, String content, Set<String> tags,
            Map<String, Object> metadata, double significance) {
        
        // Create the insight
        Insight insight = new Insight(sourceNodeId, content, tags, metadata, significance);
        
        // Store in registry
        insightRegistry.put(insight.getInsightId(), insight);
        
        // Index by tags
        for (String tag : tags) {
            tagIndex.putIfAbsent(tag, Collections.synchronizedSet(new HashSet<>()));
            tagIndex.get(tag).add(insight.getInsightId());
        }
        
        // Index by context if provided
        if (metadata.containsKey("context")) {
            String context = metadata.get("context").toString();
            contextIndex.putIfAbsent(context, Collections.synchronizedSet(new HashSet<>()));
            contextIndex.get(context).add(insight.getInsightId());
        }
        
        // Persist the insight
        if (persistenceStrategy != null) {
            try {
                Map<String, Object> persistData = new HashMap<>();
                persistData.put("insightId", insight.getInsightId());
                persistData.put("sourceNodeId", insight.getSourceNodeId());
                persistData.put("content", insight.getContent());
                persistData.put("tags", new ArrayList<>(insight.getTags()));
                persistData.put("metadata", insight.getMetadata());
                persistData.put("timestamp", insight.getTimestamp().toString());
                persistData.put("significance", insight.getSignificance());
                
                persistenceStrategy.store("insights", insight.getInsightId(), persistData);
            } catch (Exception e) {
                System.err.println("Error persisting insight: " + e.getMessage());
            }
        }
        
        return insight;
    }
    
    /**
     * Retrieves an insight by ID, if accessible in the given context.
     * 
     * @param insightId The insight ID
     * @param contextId The context ID for boundary checks
     * @return An Optional containing the insight if found and accessible
     */
    public Optional<Insight> getInsight(String insightId, String contextId) {
        if (!insightRegistry.containsKey(insightId)) {
            return Optional.empty();
        }
        
        // Check if insight can pass information boundary
        if (!boundary.canInformationPass(insightId, contextId)) {
            return Optional.empty();
        }
        
        // Record access
        recordAccess(insightId);
        
        return Optional.of(insightRegistry.get(insightId));
    }
    
    /**
     * Records that an insight was accessed.
     */
    private void recordAccess(String insightId) {
        Insight insight = insightRegistry.get(insightId);
        if (insight != null) {
            insight.accessCount++;
            
            // In a real implementation, we would update the lastAccessed timestamp
            // and persist the updated access information
        }
    }
    
    /**
     * Associates a pattern with an insight.
     * 
     * @param insightId The insight ID
     * @param pattern The pattern to associate
     * @return true if successful, false otherwise
     */
    public boolean associatePattern(String insightId, Pattern pattern) {
        if (!insightRegistry.containsKey(insightId)) {
            return false;
        }
        
        Insight insight = insightRegistry.get(insightId);
        insight.associatedPatterns.add(pattern);
        
        // Index by pattern ID
        String patternId = pattern.getName(); // Assuming name is used as ID
        patternIndex.putIfAbsent(patternId, Collections.synchronizedSet(new HashSet<>()));
        patternIndex.get(patternId).add(insightId);
        
        return true;
    }
    
    /**
     * Finds insights by tags.
     * 
     * @param tags The tags to search for
     * @param matchAll If true, all tags must match; if false, any tag can match
     * @param contextId The context ID for boundary checks
     * @return A list of matching insights
     */
    public List<Insight> findByTags(Set<String> tags, boolean matchAll, String contextId) {
        Set<String> matchingIds = new HashSet<>();
        
        if (matchAll) {
            // All tags must match - start with all insights for the first tag
            boolean first = true;
            
            for (String tag : tags) {
                Set<String> taggedInsights = tagIndex.getOrDefault(tag, Collections.emptySet());
                
                if (first) {
                    matchingIds.addAll(taggedInsights);
                    first = false;
                } else {
                    // Retain only insights that have all tags
                    matchingIds.retainAll(taggedInsights);
                }
            }
        } else {
            // Any tag can match - union of all insights with any tag
            for (String tag : tags) {
                Set<String> taggedInsights = tagIndex.getOrDefault(tag, Collections.emptySet());
                matchingIds.addAll(taggedInsights);
            }
        }
        
        // Filter by boundary check
        return matchingIds.stream()
                .filter(id -> boundary.canInformationPass(id, contextId))
                .map(id -> {
                    recordAccess(id);
                    return insightRegistry.get(id);
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Finds insights by pattern.
     * 
     * @param patternId The pattern ID
     * @param contextId The context ID for boundary checks
     * @return A list of insights associated with the pattern
     */
    public List<Insight> findByPattern(String patternId, String contextId) {
        Set<String> matchingIds = patternIndex.getOrDefault(patternId, Collections.emptySet());
        
        // Filter by boundary check
        return matchingIds.stream()
                .filter(id -> boundary.canInformationPass(id, contextId))
                .map(id -> {
                    recordAccess(id);
                    return insightRegistry.get(id);
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Finds insights by context.
     * 
     * @param context The context to search for
     * @param contextId The context ID for boundary checks
     * @return A list of insights in the specified context
     */
    public List<Insight> findByContext(String context, String contextId) {
        Set<String> matchingIds = contextIndex.getOrDefault(context, Collections.emptySet());
        
        // Filter by boundary check
        return matchingIds.stream()
                .filter(id -> boundary.canInformationPass(id, contextId))
                .map(id -> {
                    recordAccess(id);
                    return insightRegistry.get(id);
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Finds insights by a custom filter.
     * 
     * @param filter The filter predicate
     * @param contextId The context ID for boundary checks
     * @return A list of insights matching the filter
     */
    public List<Insight> findByFilter(Predicate<Insight> filter, String contextId) {
        return insightRegistry.values().stream()
                .filter(filter)
                .filter(insight -> boundary.canInformationPass(insight.getInsightId(), contextId))
                .peek(insight -> recordAccess(insight.getInsightId()))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets the most significant insights.
     * 
     * @param limit Maximum number of insights to return
     * @param contextId The context ID for boundary checks
     * @return A list of the most significant insights
     */
    public List<Insight> getMostSignificantInsights(int limit, String contextId) {
        return insightRegistry.values().stream()
                .filter(insight -> boundary.canInformationPass(insight.getInsightId(), contextId))
                .sorted((i1, i2) -> Double.compare(i2.getSignificance(), i1.getSignificance()))
                .limit(limit)
                .peek(insight -> recordAccess(insight.getInsightId()))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets the most frequently accessed insights.
     * 
     * @param limit Maximum number of insights to return
     * @param contextId The context ID for boundary checks
     * @return A list of the most accessed insights
     */
    public List<Insight> getMostAccessedInsights(int limit, String contextId) {
        return insightRegistry.values().stream()
                .filter(insight -> boundary.canInformationPass(insight.getInsightId(), contextId))
                .sorted((i1, i2) -> Integer.compare(i2.getAccessCount(), i1.getAccessCount()))
                .limit(limit)
                .peek(insight -> recordAccess(insight.getInsightId()))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets the most recent insights.
     * 
     * @param limit Maximum number of insights to return
     * @param contextId The context ID for boundary checks
     * @return A list of the most recent insights
     */
    public List<Insight> getMostRecentInsights(int limit, String contextId) {
        return insightRegistry.values().stream()
                .filter(insight -> boundary.canInformationPass(insight.getInsightId(), contextId))
                .sorted((i1, i2) -> i2.getTimestamp().compareTo(i1.getTimestamp()))
                .limit(limit)
                .peek(insight -> recordAccess(insight.getInsightId()))
                .collect(Collectors.toList());
    }
    
    /**
     * Deletes an insight from the repository.
     * 
     * @param insightId The insight ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteInsight(String insightId) {
        if (!insightRegistry.containsKey(insightId)) {
            return false;
        }
        
        Insight insight = insightRegistry.remove(insightId);
        
        // Remove from tag index
        for (String tag : insight.getTags()) {
            if (tagIndex.containsKey(tag)) {
                tagIndex.get(tag).remove(insightId);
            }
        }
        
        // Remove from pattern index
        for (Pattern pattern : insight.getAssociatedPatterns()) {
            String patternId = pattern.getName();
            if (patternIndex.containsKey(patternId)) {
                patternIndex.get(patternId).remove(insightId);
            }
        }
        
        // Remove from context index
        if (insight.getMetadata().containsKey("context")) {
            String context = insight.getMetadata().get("context").toString();
            if (contextIndex.containsKey(context)) {
                contextIndex.get(context).remove(insightId);
            }
        }
        
        // Remove from persistence
        if (persistenceStrategy != null) {
            try {
                persistenceStrategy.delete("insights", insightId);
            } catch (Exception e) {
                System.err.println("Error deleting insight from persistence: " + e.getMessage());
            }
        }
        
        return true;
    }
}