package com.fractal.browser.processing;

import com.fractal.browser.model.SemanticInstruction;
import java.util.Map;
import java.util.HashMap;

/**
 * Base implementation of the FractalProcessor interface.
 */
public class BaseFractalProcessor implements FractalProcessor {
    private final int maxIterations;
    private final double convergenceThreshold;
    private final Map<String, Object> parameters;
    
    public BaseFractalProcessor(int maxIterations, double convergenceThreshold) {
        this.maxIterations = maxIterations;
        this.convergenceThreshold = convergenceThreshold;
        this.parameters = new HashMap<>();
        this.parameters.put("maxIterations", maxIterations);
        this.parameters.put("convergenceThreshold", convergenceThreshold);
    }
    
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
    
    @Override
    public Map<String, Object> getParameters() {
        return new HashMap<>(parameters);
    }
} 