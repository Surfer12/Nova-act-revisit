package com.fractal.browser.collective.boundaries;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * PrivacyFilter implements mechanisms to filter and protect sensitive information
 * within the fractal browser collective. It applies privacy rules recursively across
 * different scales of information, ensuring appropriate data protection while 
 * allowing necessary information exchange.
 * 
 * This class works in conjunction with other boundary mechanisms to create a 
 * multi-layered approach to privacy protection.
 */
public class PrivacyFilter {
    
    private final Map<String, Pattern> sensitivePatterns;
    private final Set<String> sensitiveTerms;
    private boolean strictFilteringEnabled;
    private int privacyLevel; // 1-5, where 5 is most restrictive
    
    /**
     * Creates a new PrivacyFilter with default settings (medium privacy level).
     */
    public PrivacyFilter() {
        this.sensitivePatterns = new HashMap<>();
        this.sensitiveTerms = new HashSet<>();
        this.strictFilteringEnabled = false;
        this.privacyLevel = 3; // Default medium privacy level
        
        initializeDefaultPatterns();
    }
    
    /**
     * Creates a new PrivacyFilter with specific privacy level.
     * 
     * @param privacyLevel The privacy level (1-5) where 5 is most restrictive
     */
    public PrivacyFilter(int privacyLevel) {
        this.sensitivePatterns = new HashMap<>();
        this.sensitiveTerms = new HashSet<>();
        this.strictFilteringEnabled = false;
        this.privacyLevel = Math.max(1, Math.min(5, privacyLevel));
        
        initializeDefaultPatterns();
    }
    
    /**
     * Initializes default patterns for sensitive information detection.
     */
    private void initializeDefaultPatterns() {
        // Email pattern
        sensitivePatterns.put("email", 
            Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"));
        
        // Phone number pattern (simplified)
        sensitivePatterns.put("phone", 
            Pattern.compile("\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}"));
        
        // Add default sensitive terms
        sensitiveTerms.add("password");
        sensitiveTerms.add("secret");
        sensitiveTerms.add("private");
        sensitiveTerms.add("confidential");
    }
    
    /**
     * Adds a custom pattern for identifying sensitive information.
     * 
     * @param patternName A unique name for the pattern
     * @param pattern The regex pattern to detect sensitive information
     * @return true if successfully added, false if the name already exists
     */
    public boolean addSensitivePattern(String patternName, String pattern) {
        if (sensitivePatterns.containsKey(patternName)) {
            return false;
        }
        
        try {
            Pattern compiledPattern = Pattern.compile(pattern);
            sensitivePatterns.put(patternName, compiledPattern);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Adds a sensitive term to be filtered.
     * 
     * @param term The sensitive term to add
     * @return true if added, false if it already exists
     */
    public boolean addSensitiveTerm(String term) {
        return sensitiveTerms.add(term.toLowerCase());
    }
    
    /**
     * Sets the privacy level for this filter.
     * 
     * @param level The privacy level (1-5) where 5 is most restrictive
     */
    public void setPrivacyLevel(int level) {
        this.privacyLevel = Math.max(1, Math.min(5, level));
    }
    
    /**
     * Enables or disables strict filtering mode.
     * When enabled, even borderline cases are filtered.
     * 
     * @param enabled true to enable strict filtering, false to disable
     */
    public void setStrictFiltering(boolean enabled) {
        this.strictFilteringEnabled = enabled;
    }
    
    /**
     * Filters the input content based on privacy rules.
     * 
     * @param content The content to filter
     * @return The filtered content with sensitive information redacted
     */
    public String filterContent(String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }
        
        String result = content;
        
        // Apply pattern-based filtering
        for (Map.Entry<String, Pattern> entry : sensitivePatterns.entrySet()) {
            Matcher matcher = entry.getValue().matcher(result);
            result = matcher.replaceAll("[REDACTED-" + entry.getKey().toUpperCase() + "]");
        }
        
        // Apply term-based filtering if privacy level is high enough
        if (privacyLevel >= 3) {
            for (String term : sensitiveTerms) {
                // Case insensitive replace
                String pattern = "(?i)" + Pattern.quote(term);
                result = result.replaceAll(pattern, "[REDACTED-TERM]");
            }
        }
        
        // Apply stricter filtering based on privacy level
        if (privacyLevel >= 4) {
            // For high privacy levels, redact numeric sequences that might be sensitive
            if (strictFilteringEnabled) {
                result = result.replaceAll("\\b\\d{4,}\\b", "[REDACTED-NUMBER]");
            }
        }
        
        return result;
    }
    
    /**
     * Evaluates if the content contains sensitive information.
     * 
     * @param content The content to check
     * @return true if sensitive information is detected, false otherwise
     */
    public boolean containsSensitiveInformation(String content) {
        if (content == null || content.isEmpty()) {
            return false;
        }
        
        // Check patterns
        for (Pattern pattern : sensitivePatterns.values()) {
            if (pattern.matcher(content).find()) {
                return true;
            }
        }
        
        // Check terms
        for (String term : sensitiveTerms) {
            if (content.toLowerCase().contains(term)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Gets the current privacy level.
     * 
     * @return The privacy level (1-5)
     */
    public int getPrivacyLevel() {
        return privacyLevel;
    }
    
    /**
     * Integrates this filter with the SafetyContainer to provide multi-layered protection.
     * 
     * @param container The SafetyContainer to integrate with
     * @return A composite filter that combines both protections
     */
    public PrivacyFilter integrateWithSafetyContainer(SafetyContainer container) {
        // Create a new filter with the higher privacy level between the two
        PrivacyFilter compositeFilter = new PrivacyFilter(
            Math.max(this.privacyLevel, container.getSafetyLevel()));
        
        // Copy all patterns and terms from this filter
        for (Map.Entry<String, Pattern> entry : this.sensitivePatterns.entrySet()) {
            compositeFilter.sensitivePatterns.put(entry.getKey(), entry.getValue());
        }
        compositeFilter.sensitiveTerms.addAll(this.sensitiveTerms);
        
        // Add safety-specific patterns and terms
        for (String safetyTerm : container.getSafetyTerms()) {
            compositeFilter.addSensitiveTerm(safetyTerm);
        }
        
        return compositeFilter;
    }
}