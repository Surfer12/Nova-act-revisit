package com.fractal.browser.collective.boundaries;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PrivacyFilterTest {
    
    private PrivacyFilter filter;
    
    @BeforeEach
    public void setup() {
        filter = new PrivacyFilter();
    }
    
    @Test
    public void testDefaultPatternFiltering() {
        String testContent = "Contact me at test@example.com or call 123-456-7890";
        String filtered = filter.filterContent(testContent);
        
        // Verify email and phone are redacted
        assertFalse(filtered.contains("test@example.com"));
        assertFalse(filtered.contains("123-456-7890"));
        assertTrue(filtered.contains("[REDACTED-EMAIL]"));
        assertTrue(filtered.contains("[REDACTED-PHONE]"));
    }
    
    @Test
    public void testSpanishLocaleFiltering() {
        PrivacyFilter spanishFilter = new PrivacyFilter(new Locale("es"));
        String testContent = "Contact me at test@example.com or call 123-456-7890";
        String filtered = spanishFilter.filterContent(testContent);
        
        // Verify Spanish redaction messages
        assertTrue(filtered.contains("[CORREO-REDACTADO]"));
        assertTrue(filtered.contains("[TELÉFONO-REDACTADO]"));
    }
    
    @Test
    public void testConcurrentTermAddition() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        // Add terms concurrently
        for (int i = 0; i < threadCount; i++) {
            final String term = "sensitive" + i;
            executor.submit(() -> {
                try {
                    filter.addSensitiveTerm(term);
                    
                    // Verify term was added
                    String testContent = "This contains " + term + " information";
                    String filtered = filter.filterContent(testContent);
                    assertTrue(filtered.contains("[REDACTED-TERM]"));
                    assertFalse(filtered.contains(term));
                } finally {
                    latch.countDown();
                }
            });
        }
        
        assertTrue(latch.await(10, TimeUnit.SECONDS));
        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));
    }
    
    @Test
    public void testConcurrentContentFiltering() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        // Filter content concurrently
        for (int i = 0; i < threadCount; i++) {
            final String email = "test" + i + "@example.com";
            executor.submit(() -> {
                try {
                    String testContent = "Contact: " + email;
                    String filtered = filter.filterContent(testContent);
                    assertTrue(filtered.contains("[REDACTED-EMAIL]"));
                    assertFalse(filtered.contains(email));
                } finally {
                    latch.countDown();
                }
            });
        }
        
        assertTrue(latch.await(10, TimeUnit.SECONDS));
        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));
    }
    
    @Test
    public void testNullInputHandling() {
        // Test null content
        assertNull(filter.filterContent(null));
        assertFalse(filter.containsSensitiveInformation(null));
        
        // Test null term addition
        assertFalse(filter.addSensitiveTerm(null));
        
        // Test null pattern addition
        assertFalse(filter.addSensitivePattern("test", null));
        assertFalse(filter.addSensitivePattern(null, "test"));
    }
    
    @Test
    public void testPrivacyLevelBehavior() {
        String testContent = "Password123 and some 12345 numbers";
        
        // Test low privacy level
        PrivacyFilter lowPrivacy = new PrivacyFilter(1);
        String lowFiltered = lowPrivacy.filterContent(testContent);
        assertTrue(lowFiltered.contains("Password123"));
        assertTrue(lowFiltered.contains("12345"));
        
        // Test high privacy level with strict filtering
        PrivacyFilter highPrivacy = new PrivacyFilter(5);
        highPrivacy.setStrictFiltering(true);
        String highFiltered = highPrivacy.filterContent(testContent);
        assertFalse(highFiltered.contains("Password123"));
        assertFalse(highFiltered.contains("12345"));
        assertTrue(highFiltered.contains("[REDACTED-TERM]"));
        assertTrue(highFiltered.contains("[REDACTED-NUMBER]"));
    }
    
    @Test
    public void testSafetyContainerIntegration() {
        SafetyContainer container = new SafetyContainer();
        container.addSafetyTerm("dangerous");
        
        PrivacyFilter integrated = filter.integrateWithSafetyContainer(container);
        String testContent = "This is dangerous content";
        String filtered = integrated.filterContent(testContent);
        
        assertTrue(filtered.contains("[REDACTED-TERM]"));
        assertFalse(filtered.contains("dangerous"));
    }
    
    @Test
    public void testCaseInsensitiveMatching() {
        filter.addSensitiveTerm("secret");
        
        // Test various cases
        String[] variants = {
            "secret", "SECRET", "Secret", "sEcReT"
        };
        
        for (String variant : variants) {
            String testContent = "This is a " + variant + " message";
            String filtered = filter.filterContent(testContent);
            assertFalse(filtered.contains(variant));
            assertTrue(filtered.contains("[REDACTED-TERM]"));
        }
    }
}