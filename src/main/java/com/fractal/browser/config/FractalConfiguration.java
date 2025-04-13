package com.fractal.browser.config;

import java.util.Map;
import java.util.HashMap;
import com.fractal.browser.exceptions.ConfigurationException;

public class FractalConfiguration {
    private final int maxDepth;
    private final int maxIterations;
    private final double convergenceThreshold;
    private final Map<String, Object> parameters;
    private final Map<String, Object> browserConfig;
    private final Map<String, Object> observationConfig;
    
    private FractalConfiguration(Builder builder) {
        this.maxDepth = builder.maxDepth;
        this.maxIterations = builder.maxIterations;
        this.convergenceThreshold = builder.convergenceThreshold;
        this.parameters = new HashMap<>(builder.parameters);
        this.browserConfig = new HashMap<>(builder.browserConfig);
        this.observationConfig = new HashMap<>(builder.observationConfig);
    }
    
    public static class Builder {
        private int maxDepth = 100;
        private int maxIterations = 1000;
        private double convergenceThreshold = 0.0001;
        private Map<String, Object> parameters = new HashMap<>();
        private Map<String, Object> browserConfig = new HashMap<>();
        private Map<String, Object> observationConfig = new HashMap<>();
        
        public Builder() {
            // Set default browser configuration
            browserConfig.put("screenWidth", 1920);
            browserConfig.put("screenHeight", 1080);
            browserConfig.put("headless", false);
            browserConfig.put("startingPage", "https://example.com");
            
            // Set default observation configuration
            observationConfig.put("screenshotEnabled", true);
            observationConfig.put("domCaptureEnabled", true);
        }
        
        public Builder maxDepth(int maxDepth) {
            this.maxDepth = maxDepth;
            return this;
        }
        
        public Builder maxIterations(int maxIterations) {
            this.maxIterations = maxIterations;
            return this;
        }
        
        public Builder convergenceThreshold(double convergenceThreshold) {
            this.convergenceThreshold = convergenceThreshold;
            return this;
        }
        
        public Builder parameter(String key, Object value) {
            this.parameters.put(key, value);
            return this;
        }
        
        public Builder browserConfig(Map<String, Object> config) {
            this.browserConfig.putAll(config);
            return this;
        }
        
        public Builder observationConfig(Map<String, Object> config) {
            this.observationConfig.putAll(config);
            return this;
        }
        
        public FractalConfiguration build() throws ConfigurationException {
            validate();
            return new FractalConfiguration(this);
        }
        
        private void validate() throws ConfigurationException {
            if (maxDepth <= 0) {
                throw new ConfigurationException("maxDepth must be greater than 0");
            }
            if (maxIterations <= 0) {
                throw new ConfigurationException("maxIterations must be greater than 0");
            }
            if (convergenceThreshold <= 0) {
                throw new ConfigurationException("convergenceThreshold must be greater than 0");
            }
            if (!browserConfig.containsKey("startingPage")) {
                throw new ConfigurationException("Browser configuration must include starting page");
            }
            if (!observationConfig.containsKey("screenshotEnabled")) {
                throw new ConfigurationException("Observation configuration must specify if screenshots are enabled");
            }
        }
    }
    
    public int getMaxDepth() {
        return maxDepth;
    }
    
    public int getMaxIterations() {
        return maxIterations;
    }
    
    public double getConvergenceThreshold() {
        return convergenceThreshold;
    }
    
    public Map<String, Object> getParameters() {
        return new HashMap<>(parameters);
    }
    
    public Map<String, Object> getBrowserConfig() {
        return new HashMap<>(browserConfig);
    }
    
    public Map<String, Object> getObservationConfig() {
        return new HashMap<>(observationConfig);
    }
    
    public void validate() throws ConfigurationException {
        if (maxDepth <= 0) {
            throw new ConfigurationException("maxDepth must be greater than 0");
        }
        if (maxIterations <= 0) {
            throw new ConfigurationException("maxIterations must be greater than 0");
        }
        if (convergenceThreshold <= 0) {
            throw new ConfigurationException("convergenceThreshold must be greater than 0");
        }
        if (!browserConfig.containsKey("startingPage")) {
            throw new ConfigurationException("Browser configuration must include starting page");
        }
        if (!observationConfig.containsKey("screenshotEnabled")) {
            throw new ConfigurationException("Observation configuration must specify if screenshots are enabled");
        }
    }
}