package com.fractal.browser.processing;

import com.fractal.browser.model.SemanticInstruction;
import java.util.Map;

/**
 * Interface for fractal processing operations.
 */
public interface FractalProcessor {
    /**
     * Processes a semantic instruction using the fractal algorithm (z = z² + c)
     * @param instruction The semantic instruction to process
     * @param contextId Optional context identifier for tracking
     * @return ProcessingResult containing the results and metadata
     */
    ProcessingResult process(SemanticInstruction instruction, String contextId);
    
    /**
     * Gets the current processing parameters
     * @return Map containing processing parameters
     */
    Map<String, Object> getParameters();
    
    /**
     * Processes a node-specific instruction
     * @param instruction The semantic instruction to process
     * @param nodeId The node identifier
     * @param contextId The context identifier
     * @return ProcessingResult containing node-specific results
     */
    ProcessingResult processNode(SemanticInstruction instruction, String nodeId, String contextId);
}