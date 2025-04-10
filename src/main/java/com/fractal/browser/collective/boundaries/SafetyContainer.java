package com.fractal.browser.collective.boundaries;

import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;

/**
 * SafetyContainer provides mechanisms to ensure the safety of information processing
 * within the fractal browser collective. It implements containment policies that 
 * enforce safety boundaries at multiple levels of the system.
 * 
 * This class creates safe execution contexts for potentially dangerous operations,
 * working in a fractal pattern to contain risks at various scales.
 */
public class SafetyContainer {
    
    private final Set<String> safetyTerms;
    private final List<Function<String, String>> safetyTransformers;
    private int safetyLevel; // 1-5, where 5 is most restrictive
    private boolean strictModeEnabled;
    
    /**
     * Creates a new SafetyContainer with default settings (medium safety level).
     */
    public SafetyContainer() {
        this.safetyTerms = new HashSet<>();
        this.safetyTransformers = new ArrayList<>();
        this.safetyLevel = 3; // Default medium safety level
        this.strictModeEnabled = false;
        
        initializeDefaultSafetyTerms();
        initializeDefaultTransformers();
    }
    
    /**
     * Creates a new SafetyContainer with a specific safety level.
     * 
     * @param safetyLevel The safety level (1-5) where 5 is most restrictive
     */
    public SafetyContainer(int safetyLevel) {
        this.safetyTerms = new HashSet<>();
        this.safetyTransformers = new ArrayList<>();
        this.safetyLevel = Math.max(1, Math.min(5, safetyLevel));
        this.strictModeEnabled = false;
        
        initializeDefaultSafetyTerms();
        initializeDefaultTransformers();
    }
    
    /**
     * Initializes default safety terms.
     */
    private void initializeDefaultSafetyTerms() {
        safetyTerms.add("malware");
        safetyTerms.add("exploit");
        safetyTerms.add("vulnerability");
        safetyTerms.add("attack");
    }
    
    /**
     * Initializes default safety transformers that sanitize content.
     */
    private void initializeDefaultTransformers() {
        // Basic HTML sanitizer
        safetyTransformers.add(content -> 
            content.replaceAll("<script[^>]*>.*?</script>", "[REMOVED-UNSAFE-SCRIPT]"));
        
        // Remove executable code patterns
        safetyTransformers.add(content -> 
            content.replaceAll("(?i)exec\\s*\\(", "[REMOVED-EXEC]"));
    }
    
    /**
     * Adds a safety term to be monitored.
     * 
     * @param term The safety term to add
     * @return true if added, false if it already exists
     */
    public boolean addSafetyTerm(String term) {
        return safetyTerms.add(term.toLowerCase());
    }
    
    /**
     * Adds a custom safety transformer.
     * 
     * @param transformer A function that transforms content for safety
     */
    public void addSafetyTransformer(Function<String, String> transformer) {
        safetyTransformers.add(transformer);
    }
    
    /**
     * Sets the safety level for this container.
     * 
     * @param level The safety level (1-5) where 5 is most restrictive
     */
    public void setSafetyLevel(int level) {
        this.safetyLevel = Math.max(1, Math.min(5, level));
    }
    
    /**
     * Enables or disables strict safety mode.
     * When enabled, more aggressive safety measures are applied.
     * 
     * @param enabled true to enable strict mode, false to disable
     */
    public void setStrictMode(boolean enabled) {
        this.strictModeEnabled = enabled;
    }
    
    /**
     * Gets the current safety level.
     * 
     * @return The safety level (1-5)
     */
    public int getSafetyLevel() {
        return safetyLevel;
    }
    
    /**
     * Gets an unmodifiable set of all safety terms.
     * 
     * @return An unmodifiable set of safety terms
     */
    public Set<String> getSafetyTerms() {
        return Collections.unmodifiableSet(safetyTerms);
    }
    
    /**
     * Applies safety transformations to the content.
     * 
     * @param content The content to transform
     * @return The transformed content with safety measures applied
     */
    public String applySafetyMeasures(String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }
        
        String result = content;
        
        // Apply all transformers
        for (Function<String, String> transformer : safetyTransformers) {
            result = transformer.apply(result);
        }
        
        // Apply additional safety measures based on safety level
        if (safetyLevel >= 4 || strictModeEnabled) {
            // For high safety levels, remove URLs or mark them as potentially unsafe
            result = result.replaceAll("https?://[^\\s]+", "[EXTERNAL-URL]");
        }
        
        return result;
    }
    
    /**
     * Checks if content contains potentially unsafe elements.
     * 
     * @param content The content to check
     * @return true if unsafe elements are detected, false otherwise
     */
    public boolean containsUnsafeElements(String content) {
        if (content == null || content.isEmpty()) {
            return false;
        }
        
        // Check for safety terms
        for (String term : safetyTerms) {
            if (content.toLowerCase().contains(term)) {
                return true;
            }
        }
        
        // Check for common unsafe patterns
        if (content.contains("<script") || 
            content.toLowerCase().contains("exec(") ||
            content.toLowerCase().contains("eval(")) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Creates a contained execution environment for the provided operation.
     * This provides a higher level of isolation for potentially risky operations.
     * 
     * @param <T> The return type of the operation
     * @param operation The operation to execute in the safety container
     * @param fallback The fallback value if the operation fails or is unsafe
     * @return The result of the operation or the fallback value
     */
    public <T> T executeInContainer(Function<SafetyContainer, T> operation, T fallback) {
        try {
            // Create a more restrictive container for the operation
            SafetyContainer subContainer = new SafetyContainer(this.safetyLevel + 1);
            subContainer.safetyTerms.addAll(this.safetyTerms);
            subContainer.strictModeEnabled = true;
            
            // Execute the operation in the sub-container
            return operation.apply(subContainer);
        } catch (Exception e) {
            // Safely contain any exceptions and return the fallback
            return fallback;
        }
    }
    
    /**
     * Integrates this container with a TrustManager for enhanced safety evaluation.
     * 
     * @param trustManager The TrustManager to integrate with
     * @param contextId The context to evaluate
     * @return true if the integration affects safety settings, false otherwise
     */
    public boolean integrateWithTrustManager(TrustManager trustManager, String contextId) {
        if (trustManager == null) {
            return false;
        }
        
        trustManager.getTrustScore(contextId).ifPresent(trustScore -> {
            // Adjust safety level based on trust score (inversely)
            // Lower trust = higher safety restrictions
            this.safetyLevel = 5 - (int) Math.round(trustScore * 4);
            
            // Enable strict mode for low trust scores
            this.strictModeEnabled = trustScore < 0.4;
        });
        
        return true;
    }
}