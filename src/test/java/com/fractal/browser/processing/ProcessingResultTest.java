package com.fractal.browser.processing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

class ProcessingResultTest {
    @Test
    void testDefaultConstructor() {
        ProcessingResult result = new ProcessingResult();
        
        assertNotNull(result.getResults());
        assertTrue(result.getResults().isEmpty());
        assertEquals(0, result.getIterations());
        assertEquals(0.0, result.getConvergenceValue());
        assertFalse(result.getContextId().isPresent());
    }
    
    @Test
    void testSettersAndGetters() {
        ProcessingResult result = new ProcessingResult();
        
        // Test results
        Map<String, Object> results = new HashMap<>();
        results.put("key1", "value1");
        results.put("key2", 42);
        result.setResults(results);
        
        assertEquals(results, result.getResults());
        assertEquals("value1", result.getResults().get("key1"));
        assertEquals(42, result.getResults().get("key2"));
        
        // Test iterations
        result.setIterations(100);
        assertEquals(100, result.getIterations());
        
        // Test convergence value
        result.setConvergenceValue(0.5);
        assertEquals(0.5, result.getConvergenceValue());
        
        // Test context ID
        result.setContextId("test-context");
        Optional<String> contextId = result.getContextId();
        assertTrue(contextId.isPresent());
        assertEquals("test-context", contextId.get());
    }
    
    @Test
    void testNullContextId() {
        ProcessingResult result = new ProcessingResult();
        result.setContextId(null);
        
        assertFalse(result.getContextId().isPresent());
    }
    
    @Test
    void testImmutableResults() {
        ProcessingResult result = new ProcessingResult();
        Map<String, Object> originalResults = new HashMap<>();
        originalResults.put("key", "value");
        
        result.setResults(originalResults);
        
        // Modify the original map
        originalResults.put("key", "new value");
        
        // The result's map should not be affected
        assertEquals("value", result.getResults().get("key"));
    }
} 