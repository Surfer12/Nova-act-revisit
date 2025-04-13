package com.fractal.browser.collective.visualization;

import java.util.Map;
import java.util.HashMap;

/**
 * Represents a node in the emergent pattern visualization.
 */
public class PatternNode {
    private double x;
    private double y;
    private String id;
    private Map<String, Object> properties;
    
    public PatternNode(String id) {
        this.id = id;
        this.properties = new HashMap<>();
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public String getId() {
        return id;
    }
    
    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }
    
    public Object getProperty(String key) {
        return properties.get(key);
    }
    
    public Map<String, Object> getProperties() {
        return new HashMap<>(properties);
    }
} 