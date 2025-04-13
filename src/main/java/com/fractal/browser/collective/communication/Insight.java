package com.fractal.browser.collective.communication;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * Represents an insight in the collective communication system.
 */
public class Insight {
    private final String id;
    private final String contextId;
    private final Set<String> tags;
    private final Map<String, Object> data;
    private final double confidence;
    
    public Insight(String id, String contextId, Set<String> tags, Map<String, Object> data, double confidence) {
        this.id = id;
        this.contextId = contextId;
        this.tags = new HashSet<>(tags);
        this.data = new HashMap<>(data);
        this.confidence = confidence;
    }
    
    public String getId() {
        return id;
    }
    
    public String getContextId() {
        return contextId;
    }
    
    public Set<String> getTags() {
        return new HashSet<>(tags);
    }
    
    public Map<String, Object> getData() {
        return new HashMap<>(data);
    }
    
    public double getConfidence() {
        return confidence;
    }
    
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("contextId", contextId);
        map.put("tags", tags);
        map.put("data", data);
        map.put("confidence", confidence);
        return map;
    }
    
    public static Insight fromMap(Map<String, Object> map) {
        String id = (String) map.get("id");
        String contextId = (String) map.get("contextId");
        @SuppressWarnings("unchecked")
        Set<String> tags = new HashSet<>((Set<String>) map.get("tags"));
        @SuppressWarnings("unchecked")
        Map<String, Object> data = new HashMap<>((Map<String, Object>) map.get("data"));
        double confidence = ((Number) map.get("confidence")).doubleValue();
        return new Insight(id, contextId, tags, data, confidence);
    }
} 