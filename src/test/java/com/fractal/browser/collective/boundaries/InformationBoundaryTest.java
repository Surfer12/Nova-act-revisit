package com.fractal.browser.collective.boundaries;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class InformationBoundaryTest {
    
    private InformationBoundary boundary;
    
    @BeforeEach
    public void setup() {
        boundary = new InformationBoundary();
    }

    @Test
    public void testInformationTransformationWithDefaultLocale() {
        boundary.registerContext("low-access", 2);
        boundary.registerContext("medium-access", 5);
        boundary.registerContext("high-access", 8);

        String testInfo = "This is sensitive information that should be handled carefully";

        // Test low access level - should be redacted
        String lowAccess = boundary.transformInformationForContext(testInfo, "low-access");
        assertEquals("[Redacted - Insufficient Access Level]", lowAccess);

        // Test medium access level - should be summarized
        String mediumAccess = boundary.transformInformationForContext(testInfo, "medium-access");
        assertTrue(mediumAccess.startsWith("Summary: "));
        assertTrue(mediumAccess.endsWith("..."));

        // Test high access level - should show full content
        String highAccess = boundary.transformInformationForContext(testInfo, "high-access");
        assertEquals(testInfo, highAccess);
    }

    @Test
    public void testInformationTransformationWithSpanishLocale() {
        boundary = new InformationBoundary(new Locale("es"));
        boundary.registerContext("low-access", 2);
        boundary.registerContext("medium-access", 5);

        String testInfo = "This is sensitive information that should be handled carefully";

        // Test low access level - should be redacted in Spanish
        String lowAccess = boundary.transformInformationForContext(testInfo, "low-access");
        assertEquals("[Redactado - Nivel de Acceso Insuficiente]", lowAccess);

        // Test medium access level - should be summarized in Spanish
        String mediumAccess = boundary.transformInformationForContext(testInfo, "medium-access");
        assertTrue(mediumAccess.startsWith("Resumen: "));
        assertTrue(mediumAccess.endsWith("..."));
    }

    @Test
    public void testConcurrentAccessToContextLevels() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        // Create multiple threads that simultaneously update and read context levels
        for (int i = 0; i < threadCount; i++) {
            final int accessLevel = i;
            executor.submit(() -> {
                try {
                    String contextId = "context-" + accessLevel;
                    boundary.registerContext(contextId, accessLevel);
                    
                    // Verify the context was registered correctly
                    Map<String, Object> metadata = boundary.getBoundaryMetadata(contextId);
                    assertEquals(accessLevel, metadata.get("accessLevel"));
                    
                    // Test information access
                    String testInfo = "Test information " + accessLevel;
                    String result = boundary.transformInformationForContext(testInfo, contextId);
                    
                    // Verify the result based on access level
                    if (accessLevel < 3) {
                        assertEquals("[Redacted - Insufficient Access Level]", result);
                    } else if (accessLevel < 7) {
                        assertTrue(result.startsWith("Summary: "));
                    } else {
                        assertEquals(testInfo, result);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        // Wait for all threads to complete
        assertTrue(latch.await(10, TimeUnit.SECONDS));
        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));
    }

    @Test
    public void testNullInputHandling() {
        // Test null context ID
        assertFalse(boundary.registerContext(null, 5));
        
        // Test null information
        String result = boundary.transformInformationForContext(null, "test-context");
        assertEquals("[Redacted - Insufficient Access Level]", result);
        
        // Test null trust manager
        assertFalse(boundary.integrateWithTrustManager(null, "test-context"));
    }

    @Test
    public void testAccessLevelBoundaries() {
        // Test invalid access levels
        assertFalse(boundary.registerContext("test-negative", -1));
        assertFalse(boundary.registerContext("test-too-high", 11));
        
        // Test valid boundary values
        assertTrue(boundary.registerContext("test-min", 0));
        assertTrue(boundary.registerContext("test-max", 10));
    }
}