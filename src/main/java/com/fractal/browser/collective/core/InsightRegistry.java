package com.fractal.browser.collective.core;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.time.Instant;

/**
 * Registry for managing insights in the collective system.
 * Provides functionality for storing, retrieving, and managing insights.
 */
public class InsightRegistry {
    private final Map<String, Map<String, Object>> insights;
    private final Map<String, Set<String>> tagIndex;
    private final Map<String, Instant> timeIndex;
    
    public InsightRegistry() {
        this.insights = new ConcurrentHashMap<>();
        this.tagIndex = new ConcurrentHashMap<>();
        this.timeIndex = new ConcurrentHashMap<>();
    }
    
    /**
     * Stores a new insight in the registry.
     * 
     * @param id The insight ID
     * @param data The insight data
     * @param tags Associated tags
     * @return true if stored successfully, false if ID already exists
     */
    public boolean storeInsight(String id, Map<String, Object> data, Set<String> tags) {
        if (insights.containsKey(id)) {
            return false;
        }
        
        insights.put(id, new HashMap<>(data));
        timeIndex.put(id, Instant.now());
        
        for (String tag : tags) {
            tagIndex.computeIfAbsent(tag, k -> new HashSet<>()).add(id);
        }
        
        return true;
    }
    
    /**
     * Retrieves an insight by ID.
     * 
     * @param id The insight ID
     * @return The insight data, or null if not found
     */
    public Map<String, Object> getInsight(String id) {
        Map<String, Object> data = insights.get(id);
        return data != null ? new HashMap<>(data) : null;
    }
    
    /**
     * Finds insights by tag.
     * 
     * @param tag The tag to search for
     * @return List of insight IDs with the given tag
     */
    public List<String> findByTag(String tag) {
        Set<String> ids = tagIndex.get(tag);
        return ids != null ? new ArrayList<>(ids) : new ArrayList<>();
    }
    
    /**
     * Updates an existing insight.
     * 
     * @param id The insight ID
     * @param updates The updates to apply
     * @return true if updated successfully, false if ID not found
     */
    public boolean updateInsight(String id, Map<String, Object> updates) {
        if (!insights.containsKey(id)) {
            return false;
        }
        
        Map<String, Object> data = insights.get(id);
        data.putAll(updates);
        return true;
    }
    
    /**
     * Removes an insight from the registry.
     * 
     * @param id The insight ID
     * @return true if removed successfully, false if ID not found
     */
    public boolean removeInsight(String id) {
        if (!insights.containsKey(id)) {
            return false;
        }
        
        insights.remove(id);
        timeIndex.remove(id);
        
        // Remove from tag index
        for (Set<String> taggedIds : tagIndex.values()) {
            taggedIds.remove(id);
        }
        
        return true;
    }
    
    /**
     * Gets all insight IDs in the registry.
     * 
     * @return Set of all insight IDs
     */
    public Set<String> getAllInsightIds() {
        return new HashSet<>(insights.keySet());
    }
}
