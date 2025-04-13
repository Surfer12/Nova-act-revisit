package com.fractal.browser.collective.processing;

import com.fractal.browser.model.SemanticInstruction;
import com.fractal.browser.processing.ProcessingResult;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.function.Predicate;

public interface CollectiveProcessor extends FractalProcessor {
    /**
     * Discovers available nodes in the collective network
     * @param filter Optional predicate to filter nodes
     * @return Set of node identifiers
     */
    Set<String> discoverNodes(Predicate<Map<String, Object>> filter);
    
    /**
     * Checks if a specific node is available
     * @param nodeId The node identifier
     * @return true if the node is available
     */
    boolean isNodeAvailable(String nodeId);
    
    /**
     * Processes an instruction collectively across the network
     * @param instruction The semantic instruction to process
     * @param contextId The context identifier
     * @return ProcessingResult containing collective results
     */
    ProcessingResult processCollectively(SemanticInstruction instruction, String contextId);
    
    /**
     * Identifies emergent patterns from processing results
     * @param contextId The context identifier
     * @return Map of pattern identifiers to lists of related results
     */
    Map<String, List<ProcessingResult>> identifyEmergentPatterns(String contextId);
} 