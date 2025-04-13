package com.fractal.browser.processing;

import com.fractal.browser.model.SemanticInstruction;
import java.util.Map;
import java.util.HashMap;

public class NodeFractalProcessor implements FractalProcessor {
    private final int maxIterations;
    private final double convergenceThreshold;
    
    public NodeFractalProcessor(int maxIterations, double convergenceThreshold) {
        this.maxIterations = maxIterations;
        this.convergenceThreshold = convergenceThreshold;
    }
    
    @Override
    public ProcessingResult process(SemanticInstruction instruction, String contextId) {
        ProcessingResult result = new ProcessingResult();
        result.setContextId(contextId);
        
        // Initialize z and c from the instruction
        double z = instruction.getInitialValue();
        double c = instruction.getConstantValue();
        
        int iterations = 0;
        double convergenceValue = 0.0;
        
        // Apply the fractal formula z = z² + c
        while (iterations < maxIterations && Math.abs(z) < 2.0) {
            z = z * z + c;
            iterations++;
            
            // Calculate convergence value
            convergenceValue = Math.abs(z);
            if (convergenceValue < convergenceThreshold) {
                break;
            }
        }
        
        // Store results
        Map<String, Object> results = new HashMap<>();
        results.put("finalValue", z);
        results.put("iterations", iterations);
        results.put("convergenceValue", convergenceValue);
        results.put("converged", convergenceValue < convergenceThreshold);
        
        result.setResults(results);
        result.setIterations(iterations);
        result.setConvergenceValue(convergenceValue);
        
        return result;
    }
    
    @Override
    public Map<String, Object> getParameters() {
        Map<String, Object> params = new HashMap<>();
        params.put("maxIterations", maxIterations);
        params.put("convergenceThreshold", convergenceThreshold);
        return params;
    }
} 