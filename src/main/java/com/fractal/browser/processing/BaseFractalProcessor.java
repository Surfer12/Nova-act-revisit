package com.fractal.browser.processing;

import com.fractal.browser.model.SemanticInstruction;
import java.util.Map;
import java.util.HashMap;

/**
 * Base implementation of the FractalProcessor interface.
 * Provides common functionality for fractal processing operations.
 */
public class BaseFractalProcessor implements FractalProcessor {
    private final int maxIterations;
    private final double convergenceThreshold;
    private final Map<String, Object> parameters;
    
    /**
     * Creates a new BaseFractalProcessor with specified parameters.
     * @param maxIterations Maximum number of iterations to perform
     * @param convergenceThreshold Threshold for determining convergence
     */
    public BaseFractalProcessor(int maxIterations, double convergenceThreshold) {
        this.maxIterations = maxIterations;
        this.convergenceThreshold = convergenceThreshold;
        this.parameters = new HashMap<>();
        this.parameters.put("maxIterations", maxIterations);
        this.parameters.put("convergenceThreshold", convergenceThreshold);
    }
    
    /**
     * Processes a semantic instruction using basic fractal processing.
     * @param instruction The semantic instruction to process
     * @param contextId Optional context identifier for tracking
     * @return ProcessingResult containing the results and metadata
     */
    @Override
    public ProcessingResult process(SemanticInstruction instruction, String contextId) {
        // Implementation of fractal processing logic
        Map<String, Object> results = new HashMap<>();
        results.put("initialValue", instruction.getInitialValue());
        results.put("constantValue", instruction.getConstantValue());
        results.put("metadata", instruction.getMetadata());
        
        ProcessingResult result = new ProcessingResult(results, maxIterations, convergenceThreshold);
        result.setContextId(contextId);
        return result;
    }
    
    /**
     * Processes a node-specific instruction using basic fractal processing.
     * @param instruction The semantic instruction to process
     * @param nodeId The node identifier
     * @param contextId The context identifier
     * @return ProcessingResult containing node-specific results
     */
    @Override
    public ProcessingResult processNode(SemanticInstruction instruction, String nodeId, String contextId) {
        // Node-specific processing implementation
        Map<String, Object> results = new HashMap<>();
        results.put("nodeId", nodeId);
        results.put("initialValue", instruction.getInitialValue());
        results.put("constantValue", instruction.getConstantValue());
        results.put("metadata", instruction.getMetadata());
        
        ProcessingResult result = new ProcessingResult(results, maxIterations, convergenceThreshold);
        result.setContextId(contextId);
        return result;
    }
    
    /**
     * Gets the current processing parameters.
     * @return Map containing processing parameters
     */
    @Override
    public Map<String, Object> getParameters() {
        return new HashMap<>(parameters);
    }
} 