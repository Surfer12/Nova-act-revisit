package com.fractal.browser.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.HashMap;

class SemanticInstructionTest {
    @Test
    void testConstructor_NoMetadata() {
        SemanticInstruction instruction = new SemanticInstruction(0.5, 0.3);
        
        assertEquals(0.5, instruction.getInitialValue());
        assertEquals(0.3, instruction.getConstantValue());
        assertTrue(instruction.getMetadata().isEmpty());
    }
    
    @Test
    void testConstructor_WithMetadata() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", 42);
        
        SemanticInstruction instruction = new SemanticInstruction(0.5, 0.3, metadata);
        
        assertEquals(0.5, instruction.getInitialValue());
        assertEquals(0.3, instruction.getConstantValue());
        assertEquals(metadata, instruction.getMetadata());
    }
    
    @Test
    void testAddMetadata() {
        SemanticInstruction instruction = new SemanticInstruction(0.5, 0.3);
        
        instruction.addMetadata("key1", "value1");
        instruction.addMetadata("key2", 42);
        
        assertEquals("value1", instruction.getMetadata("key1"));
        assertEquals(42, instruction.getMetadata("key2"));
        assertTrue(instruction.hasMetadata("key1"));
        assertTrue(instruction.hasMetadata("key2"));
        assertFalse(instruction.hasMetadata("nonexistent"));
    }
    
    @Test
    void testGetMetadata_Nonexistent() {
        SemanticInstruction instruction = new SemanticInstruction(0.5, 0.3);
        
        assertNull(instruction.getMetadata("nonexistent"));
    }
    
    @Test
    void testImmutableMetadata() {
        Map<String, Object> originalMetadata = new HashMap<>();
        originalMetadata.put("key", "value");
        
        SemanticInstruction instruction = new SemanticInstruction(0.5, 0.3, originalMetadata);
        
        // Modify the original map
        originalMetadata.put("key", "new value");
        
        // The instruction's metadata should not be affected
        assertEquals("value", instruction.getMetadata("key"));
    }
} 