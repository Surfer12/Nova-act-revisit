package com.fractal.browser.processing;

import java.util.Map;

public class ProcessingResult {
    private final Map<String, Object> results;
    private final int iterations;
    private final double convergenceValue;
    
    public ProcessingResult(Map<String, Object> results, int iterations, double convergenceValue) {
        this.results = results;
        this.iterations = iterations;
        this.convergenceValue = convergenceValue;
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
}