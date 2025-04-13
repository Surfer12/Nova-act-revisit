package com.fractal.browser.collective.processing;

import com.fractal.browser.model.SemanticInstruction;
import com.fractal.browser.processing.ProcessingResult;
import com.fractal.browser.collective.communication.NodeDiscovery;
import com.fractal.browser.collective.communication.InsightExchange;
import com.fractal.browser.collective.memory.DistributedInsightRepository;
import com.fractal.browser.collective.boundaries.InformationBoundary;
import com.fractal.browser.collective.core.InsightRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollectiveFractalProcessorTest {
    private CollectiveFractalProcessor processor;
    
    @Mock private NodeDiscovery nodeDiscovery;
    @Mock private InsightExchange insightExchange;
    @Mock private DistributedInsightRepository insightRepository;
    @Mock private InformationBoundary boundary;
    @Mock private InsightRegistry insightRegistry;
    
    private static final int MAX_ITERATIONS = 100;
    private static final double CONVERGENCE_THRESHOLD = 0.001;
    private static final int MIN_NODE_PARTICIPATION = 2;
    private static final int CONSENSUS_THRESHOLD = 75;
    
    @BeforeEach
    void setUp() {
        processor = new CollectiveFractalProcessor(
            MAX_ITERATIONS,
            CONVERGENCE_THRESHOLD,
            nodeDiscovery,
            insightExchange,
            insightRepository,
            boundary,
            insightRegistry,
            MIN_NODE_PARTICIPATION,
            CONSENSUS_THRESHOLD
        );
    }
    
    @Test
    void testDiscoverNodes() {
        // Setup
        Set<String> expectedNodes = new HashSet<>();
        expectedNodes.add("node1");
        expectedNodes.add("node2");
        
        when(nodeDiscovery.discoverNodes(any())).thenReturn(expectedNodes);
        
        // Test
        Set<String> discoveredNodes = processor.discoverNodes(node -> true);
        
        // Verify
        assertEquals(expectedNodes, discoveredNodes);
        verify(nodeDiscovery).discoverNodes(any());
    }
    
    @Test
    void testIsNodeAvailable() {
        // Setup
        when(nodeDiscovery.isNodeAvailable("node1")).thenReturn(true);
        
        // Test
        boolean isAvailable = processor.isNodeAvailable("node1");
        
        // Verify
        assertTrue(isAvailable);
        verify(nodeDiscovery).isNodeAvailable("node1");
    }
    
    @Test
    void testProcessCollectively_Success() {
        // Setup
        Set<String> availableNodes = new HashSet<>();
        availableNodes.add("node1");
        availableNodes.add("node2");
        
        when(nodeDiscovery.discoverNodes(any())).thenReturn(availableNodes);
        when(nodeDiscovery.isNodeAvailable(any())).thenReturn(true);
        
        SemanticInstruction instruction = new SemanticInstruction(0.0, 0.0);
        String contextId = "test-context";
        
        // Test
        ProcessingResult result = processor.processCollectively(instruction, contextId);
        
        // Verify
        assertNotNull(result);
        assertEquals(contextId, result.getContextId().orElse(null));
        verify(nodeDiscovery, times(1)).discoverNodes(any());
    }
    
    @Test
    void testProcessCollectively_InsufficientNodes() {
        // Setup
        Set<String> availableNodes = new HashSet<>();
        availableNodes.add("node1"); // Only one node available
        
        when(nodeDiscovery.discoverNodes(any())).thenReturn(availableNodes);
        
        SemanticInstruction instruction = new SemanticInstruction(0.0, 0.0);
        String contextId = "test-context";
        
        // Test and Verify
        assertThrows(com.fractal.browser.exceptions.FractalBrowserException.class, () -> {
            processor.processCollectively(instruction, contextId);
        });
    }
    
    @Test
    void testIdentifyEmergentPatterns() {
        // Setup
        String contextId = "test-context";
        List<ProcessingResult> mockResults = new ArrayList<>();
        
        ProcessingResult result1 = new ProcessingResult();
        result1.setContextId(contextId);
        Map<String, Object> results1 = new HashMap<>();
        results1.put("patternId", "pattern1");
        result1.setResults(results1);
        mockResults.add(result1);
        
        // Test
        Map<String, List<ProcessingResult>> patterns = processor.identifyEmergentPatterns(contextId);
        
        // Verify
        assertNotNull(patterns);
        // Additional verification would depend on the specific implementation of pattern identification
    }
    
    @Test
    void testGetParameters() {
        // Test
        Map<String, Object> params = processor.getParameters();
        
        // Verify
        assertEquals(MAX_ITERATIONS, params.get("maxIterations"));
        assertEquals(CONVERGENCE_THRESHOLD, params.get("convergenceThreshold"));
        assertEquals(MIN_NODE_PARTICIPATION, params.get("minNodeParticipation"));
        assertEquals(CONSENSUS_THRESHOLD, params.get("consensusThreshold"));
    }
} 