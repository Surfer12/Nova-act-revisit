package com.fractal.browser.model;

import java.util.Map;
import java.util.HashMap;

/**
 * Class representing a semantic instruction for browser automation.
 * This uses the builder pattern for fluent interface construction.
 */
public class SemanticInstruction {
    private final String intent;
    private final String context;
    private final String action;
    private final double initialValue;
    private final double constantValue;
    private final Map<String, Object> metadata;

    private SemanticInstruction(Builder builder) {
        this.intent = builder.intent;
        this.context = builder.context;
        this.action = builder.action;
        this.initialValue = builder.initialValue;
        this.constantValue = builder.constantValue;
        this.metadata = builder.metadata != null ? new HashMap<>(builder.metadata) : new HashMap<>();
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

    @Override
    public String toString() {
        return "SemanticInstruction{" +
                "intent='" + intent + '\'' +
                ", context='" + context + '\'' +
                ", action='" + action + '\'' +
                ", initialValue=" + initialValue +
                ", constantValue=" + constantValue +
                ", metadata=" + metadata +
                '}';
    }

    /**
     * Builder class for constructing SemanticInstruction instances.
     */
    public static class Builder {
        private String intent;
        private String context;
        private String action;
        private double initialValue;
        private double constantValue;
        private Map<String, Object> metadata;

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

        public Builder withInitialValue(double initialValue) {
            this.initialValue = initialValue;
            return this;
        }

        public Builder withConstantValue(double constantValue) {
            this.constantValue = constantValue;
            return this;
        }

        public Builder withMetadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder addMetadata(String key, Object value) {
            if (this.metadata == null) {
                this.metadata = new HashMap<>();
            }
            this.metadata.put(key, value);
            return this;
        }

        public SemanticInstruction build() {
            if (intent == null || intent.isEmpty()) {
                throw new IllegalArgumentException("Intent cannot be null or empty");
            }
            return new SemanticInstruction(this);
        }
    }
}