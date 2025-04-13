package com.fractal.browser.collective.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class Insight {
    private String id;
    private String contextId;
    private Set<String> tags;
    private Map<String, Object> data;
    private double confidence;
    
    public Insight(String id, String contextId, Set<String> tags, Map<String, Object> data, double confidence) {
        this.id = id;
        this.contextId = contextId;
        this.tags = tags;
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
        return tags;
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
        map.put("data", new HashMap<>(data));
        map.put("confidence", confidence);
        return map;
    }
    
    public static Insight fromMap(Map<String, Object> map) {
        return new Insight(
            (String) map.get("id"),
            (String) map.get("contextId"),
            (Set<String>) map.get("tags"),
            (Map<String, Object>) map.get("data"),
            (double) map.get("confidence")
        );
    }
} 