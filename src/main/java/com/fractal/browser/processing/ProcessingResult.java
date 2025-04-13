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
        this.results = new HashMap<>(results);
        this.iterations = iterations;
        this.convergenceValue = convergenceValue;
    }
    
    public ProcessingResult() {
        this.results = new HashMap<>();
        this.iterations = 0;
        this.convergenceValue = 0.0;
    }
    
    public void setResults(Map<String, Object> results) {
        this.results = new HashMap<>(results);
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
        return new HashMap<>(results);
    }
    
    public int getIterations() {
        return iterations;
    }
    
    public double getConvergenceValue() {
        return convergenceValue;
    }
    
    public Optional<String> getContextId() {
        return Optional.ofNullable(contextId);
    }

    @Override
    public String toString() {
        return "ProcessingResult{" +
                "results=" + results +
                ", iterations=" + iterations +
                ", convergenceValue=" + convergenceValue +
                ", contextId='" + contextId + '\'' +
                '}';
    }
}