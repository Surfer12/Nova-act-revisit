package com.fractal.browser.processing;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Objects;

/**
 * Represents the result of a fractal processing operation.
 * Contains the processing results, iteration count, convergence value, and context information.
 */
public class ProcessingResult {
    private final Map<String, Object> results;
    private int iterations;
    private double convergenceValue;
    private String contextId;
    
    /**
     * Creates a new ProcessingResult with the specified values.
     * @param results Map containing processing results
     * @param iterations Number of iterations performed
     * @param convergenceValue Final convergence value
     * @throws NullPointerException if results is null
     */
    public ProcessingResult(Map<String, Object> results, int iterations, double convergenceValue) {
        this.results = new HashMap<>(Objects.requireNonNull(results, "results cannot be null"));
        this.iterations = iterations;
        this.convergenceValue = convergenceValue;
    }
    
    /**
     * Creates a new empty ProcessingResult.
     */
    public ProcessingResult() {
        this.results = new HashMap<>();
        this.iterations = 0;
        this.convergenceValue = 0.0;
    }
    
    /**
     * Sets the processing results.
     * @param results Map containing processing results
     * @throws NullPointerException if results is null
     */
    public void setResults(Map<String, Object> results) {
        this.results.clear();
        this.results.putAll(Objects.requireNonNull(results, "results cannot be null"));
    }
    
    /**
     * Sets the number of iterations performed.
     * @param iterations Number of iterations
     */
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
    
    /**
     * Sets the convergence value.
     * @param convergenceValue Final convergence value
     */
    public void setConvergenceValue(double convergenceValue) {
        this.convergenceValue = convergenceValue;
    }
    
    /**
     * Sets the context identifier.
     * @param contextId Context identifier
     */
    public void setContextId(String contextId) {
        this.contextId = contextId;
    }
    
    /**
     * Gets the processing results.
     * @return Map containing processing results
     */
    public Map<String, Object> getResults() {
        return new HashMap<>(results);
    }
    
    /**
     * Gets the number of iterations performed.
     * @return Number of iterations
     */
    public int getIterations() {
        return iterations;
    }
    
    /**
     * Gets the convergence value.
     * @return Final convergence value
     */
    public double getConvergenceValue() {
        return convergenceValue;
    }
    
    /**
     * Gets the context identifier.
     * @return Optional containing the context identifier
     */
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