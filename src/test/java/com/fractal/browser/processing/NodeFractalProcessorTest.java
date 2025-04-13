package com.fractal.browser.processing;

import com.fractal.browser.model.SemanticInstruction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

class NodeFractalProcessorTest {
    private NodeFractalProcessor processor;
    private static final int MAX_ITERATIONS = 100;
    private static final double CONVERGENCE_THRESHOLD = 0.001;

    @BeforeEach
    void setUp() {
        processor = new NodeFractalProcessor(MAX_ITERATIONS, CONVERGENCE_THRESHOLD);
    }

    @Test
    void testProcess_ConvergesQuickly() {
        // Test with values that should converge quickly
        SemanticInstruction instruction = new SemanticInstruction(0.0, 0.0);
        ProcessingResult result = processor.process(instruction, "test-context");

        assertNotNull(result);
        assertEquals("test-context", result.getContextId().orElse(null));
        assertTrue(result.getConvergenceValue() < CONVERGENCE_THRESHOLD);
        assertTrue(result.getIterations() < MAX_ITERATIONS);
        
        Map<String, Object> results = result.getResults();
        assertTrue((Boolean) results.get("converged"));
        assertEquals(0.0, (Double) results.get("finalValue"), 0.0001);
    }

    @Test
    void testProcess_ReachesMaxIterations() {
        // Test with values that should not converge
        SemanticInstruction instruction = new SemanticInstruction(2.0, 2.0);
        ProcessingResult result = processor.process(instruction, "test-context");

        assertNotNull(result);
        assertEquals(MAX_ITERATIONS, result.getIterations());
        assertFalse((Boolean) result.getResults().get("converged"));
    }

    @Test
    void testGetParameters() {
        Map<String, Object> params = processor.getParameters();
        
        assertEquals(MAX_ITERATIONS, params.get("maxIterations"));
        assertEquals(CONVERGENCE_THRESHOLD, params.get("convergenceThreshold"));
    }

    @Test
    void testProcess_WithMetadata() {
        SemanticInstruction instruction = new SemanticInstruction(0.5, 0.5);
        instruction.addMetadata("testKey", "testValue");
        
        ProcessingResult result = processor.process(instruction, "test-context");
        assertNotNull(result);
        assertTrue(result.getConvergenceValue() < CONVERGENCE_THRESHOLD);
    }
} 