package com.fractal.browser.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Pattern represents identified patterns in the fractal browser system.
 * It encapsulates a set of attributes, a type, and descriptive information
 * about the pattern.
 */
public class Pattern {
    private final String id;
    private final String type;
    private final String description;
    private final Map<String, Object> attributes;
    private final long timestamp;
    
    /**
     * Creates a new Pattern instance.
     * 
     * @param id Unique identifier for the pattern
     * @param type The type of pattern (e.g., TEMPORAL, ATTRIBUTE, SEQUENCE, META)
     * @param description Human-readable description of the pattern
     * @param attributes Map of pattern attributes and values
     * @param timestamp When the pattern was detected
     */
    public Pattern(
            String id,
            String type,
            String description,
            Map<String, Object> attributes,
            long timestamp) {
        
        this.id = id;
        this.type = type;
        this.description = description;
        this.attributes = new HashMap<>(attributes);
        this.timestamp = timestamp;
    }
    
    /**
     * Gets the pattern's unique identifier.
     * 
     * @return The pattern ID
     */
    public String getId() {
        return id;
    }
    
    /**
     * Gets the pattern's type.
     * 
     * @return The pattern type
     */
    public String getType() {
        return type;
    }
    
    /**
     * Gets the pattern's description.
     * 
     * @return The pattern description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Gets the pattern's attributes.
     * 
     * @return An unmodifiable view of the pattern attributes
     */
    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }
    
    /**
     * Gets the pattern's detection timestamp.
     * 
     * @return The timestamp in milliseconds since epoch
     */
    public long getTimestamp() {
        return timestamp;
    }
    
    /**
     * Gets a specific attribute value.
     * 
     * @param key The attribute key
     * @return The attribute value or null if not found
     */
    public Object getAttribute(String key) {
        return attributes.get(key);
    }
    
    /**
     * Checks if the pattern has a specific attribute.
     * 
     * @param key The attribute key
     * @return true if the attribute exists
     */
    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }
    
    /**
     * Gets a string representation of the pattern.
     * 
     * @return String representation
     */
    @Override
    public String toString() {
        return "Pattern{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", attributes=" + attributes.size() + " items" +
                ", timestamp=" + timestamp +
                '}';
    }
}