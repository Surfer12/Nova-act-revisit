package com.fractal.browser.model;

/**
 * Class representing a semantic instruction for browser automation.
 * This uses the builder pattern for fluent interface construction.
 */
public class SemanticInstruction {
    private final String intent;
    private final String context;
    private final String action;

    private SemanticInstruction(Builder builder) {
        this.intent = builder.intent;
        this.context = builder.context;
        this.action = builder.action;
    }

    public String getIntent() {
        return intent;
    }

    public String getContext() {
        return context;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "SemanticInstruction{" +
               "intent='" + intent + '\'' +
               ", context='" + context + '\'' +
               ", action='" + action + '\'' +
               '}';
    }

    /**
     * Builder class for constructing SemanticInstruction instances.
     */
    public static class Builder {
        private String intent;
        private String context;
        private String action;

        public Builder withIntent(String intent) {
            this.intent = intent;
            return this;
        }

        public Builder withContext(String context) {
            this.context = context;
            return this;
        }

        public Builder withAction(String action) {
            this.action = action;
            return this;
        }

        public SemanticInstruction build() {
            if (intent == null || intent.isEmpty()) {
                throw new IllegalArgumentException("Intent cannot be null or empty");
            }
            return new SemanticInstruction(this);
        }
import java.util.Map;
import java.util.HashMap;

public class SemanticInstruction {
    private final double initialValue;
    private final double constantValue;
    private final Map<String, Object> metadata;
    
    public SemanticInstruction(double initialValue, double constantValue) {
        this(initialValue, constantValue, new HashMap<>());
    }
    
    public SemanticInstruction(double initialValue, double constantValue, Map<String, Object> metadata) {
        this.initialValue = initialValue;
        this.constantValue = constantValue;
        this.metadata = new HashMap<>(metadata);
    }
    
    public double getInitialValue() {
        return initialValue;
    }
    
    public double getConstantValue() {
        return constantValue;
    }
    
    public Map<String, Object> getMetadata() {
        return new HashMap<>(metadata);
    }
    
    public void addMetadata(String key, Object value) {
        metadata.put(key, value);
    }
    
    public Object getMetadata(String key) {
        return metadata.get(key);
    }
    
    public boolean hasMetadata(String key) {
        return metadata.containsKey(key);
    }
}