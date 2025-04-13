package com.fractal.browser.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class JournalEntry {
    private String content;
    private LocalDateTime timestamp;
    private Map<String, Object> metadata;
    
    // Default constructor
    public JournalEntry() {
        this.content = "";
        this.timestamp = LocalDateTime.now();
        this.metadata = new HashMap<>();
    }
    
    // Constructor that accepts Builder
    private JournalEntry(Builder builder) {
        this.content = builder.content;
        this.timestamp = builder.timestamp;
        this.metadata = builder.metadata;
    }
    
    // Getters
    public String getContent() {
        return content;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public Map<String, Object> getMetadata() {
        return new HashMap<>(metadata);
    }
    
    // Builder pattern implementation
    public static class Builder {
        private String content;
        private LocalDateTime timestamp;
        private Map<String, Object> metadata = new HashMap<>();
        
        public Builder content(String content) {
            this.content = content;
            return this;
        }
        
        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        
        public Builder addMetadata(String key, Object value) {
            this.metadata.put(key, value);
            return this;
        }
        
        public JournalEntry build() {
            return new JournalEntry(this);
        }
    }
}