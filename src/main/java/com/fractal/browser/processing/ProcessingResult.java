package com.fractal.browser.processing;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

public class ProcessingResult {
    private Map<String, Object> results;
    private int iterations;
    private double convergenceValue;
    private String contextId;
    
    public ProcessingResult(Map<String, Object> results, int iterations, double convergenceValue) {
        this.results = results;
        this.iterations = iterations;
        this.convergenceValue = convergenceValue;
    }
    
    public ProcessingResult() {
        this.results = new HashMap<>();
        this.iterations = 0;
        this.convergenceValue = 0.0;
        this.contextId = null;
    }
    
    public void setResults(Map<String, Object> results) {
        this.results = results;
    }
    
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
    
    public void setConvergenceValue(double convergenceValue) {
        this.convergenceValue = convergenceValue;
    }
    
    public void setContextId(String contextId) {
        this.contextId = contextId;
    }
    
    public Map<String, Object> getResults() {
        return results;
    }
    
    public int getIterations() {
        return iterations;
    }
    
    public double getConvergenceValue() {
        return convergenceValue;
    }
<<<<<<< HEAD
=======
    
    public Optional<String> getContextId() {
        return Optional.ofNullable(contextId);
    }
>>>>>>> ec79ba2 (Enhance YAML therapeutic model with new core tags for meta-awareness, attentional flexibility, and iterative refinement. Improve structure and clarity)
}