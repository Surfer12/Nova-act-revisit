package com.fractal.browser.performance;

import com.fractal.browser.processing.NodeFractalProcessor;
import com.fractal.browser.model.SemanticInstruction;
import com.fractal.browser.processing.ProcessingResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.List;

class ShadowWorkspacePerformanceTest {
    private NodeFractalProcessor processor;
    private static final int MAX_ITERATIONS = 100;
    private static final double CONVERGENCE_THRESHOLD = 0.001;
    private static final int WARMUP_ITERATIONS = 10;
    private static final int MEASUREMENT_ITERATIONS = 100;
    private static final int SHADOW_DEPTH = 5;

    @BeforeEach
    void setUp() {
        processor = new NodeFractalProcessor(MAX_ITERATIONS, CONVERGENCE_THRESHOLD);
    }

    @Test
    void testSingleShadowOperation() {
        // Create a base instruction with metadata
        SemanticInstruction baseInstruction = new SemanticInstruction(0.5, 0.3);
        baseInstruction.addMetadata("depth", 0);
        baseInstruction.addMetadata("shadow_id", "base");

        // Measure single shadow operation
        long startTime = System.nanoTime();
        ProcessingResult result = processor.process(baseInstruction, "test-context");
        long endTime = System.nanoTime();
        
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Single shadow operation took: " + duration + " ms");
        
        assertNotNull(result);
        assertTrue(duration < 100); // Should complete within 100ms
    }

    @Test
    void testNestedShadowOperations() {
        List<Long> durations = new ArrayList<>();
        
        // Warmup phase
        IntStream.range(0, WARMUP_ITERATIONS).forEach(i -> {
            createNestedShadows(SHADOW_DEPTH);
        });

        // Measurement phase
        IntStream.range(0, MEASUREMENT_ITERATIONS).forEach(i -> {
            long startTime = System.nanoTime();
            createNestedShadows(SHADOW_DEPTH);
            long endTime = System.nanoTime();
            durations.add(TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
        });

        // Calculate statistics
        double avgDuration = durations.stream()
            .mapToLong(Long::longValue)
            .average()
            .orElse(0.0);
            
        double maxDuration = durations.stream()
            .mapToLong(Long::longValue)
            .max()
            .orElse(0L);
            
        System.out.println("Nested shadow operations (depth " + SHADOW_DEPTH + "):");
        System.out.println("Average duration: " + avgDuration + " ms");
        System.out.println("Maximum duration: " + maxDuration + " ms");
        
        assertTrue(avgDuration < 500); // Average should be under 500ms
        assertTrue(maxDuration < 1000); // Max should be under 1000ms
    }

    private void createNestedShadows(int depth) {
        if (depth <= 0) return;

        // Create instruction for current depth
        SemanticInstruction instruction = new SemanticInstruction(0.5, 0.3);
        instruction.addMetadata("depth", depth);
        instruction.addMetadata("shadow_id", "shadow_" + depth);

        // Process current shadow
        ProcessingResult result = processor.process(instruction, "shadow-context-" + depth);
        assertNotNull(result);

        // Recursively create nested shadows
        createNestedShadows(depth - 1);
    }

    @Test
    void testParallelShadowOperations() {
        List<Long> durations = new ArrayList<>();
        
        // Warmup phase
        IntStream.range(0, WARMUP_ITERATIONS).forEach(i -> {
            runParallelShadows(5); // 5 parallel shadows
        });

        // Measurement phase
        IntStream.range(0, MEASUREMENT_ITERATIONS).forEach(i -> {
            long startTime = System.nanoTime();
            runParallelShadows(5);
            long endTime = System.nanoTime();
            durations.add(TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
        });

        // Calculate statistics
        double avgDuration = durations.stream()
            .mapToLong(Long::longValue)
            .average()
            .orElse(0.0);
            
        System.out.println("Parallel shadow operations (5 parallel):");
        System.out.println("Average duration: " + avgDuration + " ms");
        
        assertTrue(avgDuration < 300); // Should complete within 300ms
    }

    private void runParallelShadows(int count) {
        List<Thread> threads = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            final int shadowId = i;
            Thread thread = new Thread(() -> {
                SemanticInstruction instruction = new SemanticInstruction(0.5, 0.3);
                instruction.addMetadata("shadow_id", "parallel_" + shadowId);
                ProcessingResult result = processor.process(instruction, "parallel-context-" + shadowId);
                assertNotNull(result);
            });
            threads.add(thread);
            thread.start();
        }

        // Wait for all threads to complete
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    @Test
    void testShadowMemoryUsage() {
        List<ProcessingResult> results = new ArrayList<>();
        Runtime runtime = Runtime.getRuntime();
        
        // Initial memory usage
        long initialMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Create multiple shadow operations
        for (int i = 0; i < 1000; i++) {
            SemanticInstruction instruction = new SemanticInstruction(0.5, 0.3);
            instruction.addMetadata("shadow_id", "memory_test_" + i);
            ProcessingResult result = processor.process(instruction, "memory-test-context");
            results.add(result);
        }
        
        // Final memory usage
        long finalMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = finalMemory - initialMemory;
        
        System.out.println("Memory used by 1000 shadow operations: " + 
            (memoryUsed / 1024 / 1024) + " MB");
            
        assertTrue(memoryUsed < 100 * 1024 * 1024); // Should use less than 100MB
    }
} 