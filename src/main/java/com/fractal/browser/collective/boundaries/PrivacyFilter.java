package com.fractal.browser.collective.boundaries;

import java.util.Map;
import java.util.Set;
import java.util.Locale;
import java.util.ResourceBundle;
import java.text.MessageFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
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
    private volatile boolean strictFilteringEnabled;
    private volatile int privacyLevel; // 1-5, where 5 is most restrictive
    private final ResourceBundle messages;
    private final Locale locale;
    
    /**
     * Creates a new PrivacyFilter with default settings (medium privacy level).
     */
    public PrivacyFilter() {
        this(Locale.getDefault());
    }

    /**
     * Creates a new PrivacyFilter with specific privacy level.
     * 
     * @param privacyLevel The privacy level (1-5) where 5 is most restrictive
     */
    public PrivacyFilter(int privacyLevel) {
        this(privacyLevel, Locale.getDefault());
    }

    /**
     * Creates a new PrivacyFilter with default settings and specified locale.
     * 
     * @param locale The locale to use for internationalization
     */
    public PrivacyFilter(Locale locale) {
        this(3, locale); // Default medium privacy level
    }

    /**
     * Creates a new PrivacyFilter with specific privacy level and locale.
     * 
     * @param privacyLevel The privacy level (1-5) where 5 is most restrictive
     * @param locale The locale to use for internationalization
     */
    public PrivacyFilter(int privacyLevel, Locale locale) {
        this.sensitivePatterns = new ConcurrentHashMap<>();
        this.sensitiveTerms = new ConcurrentSkipListSet<>(String.CASE_INSENSITIVE_ORDER);
        this.strictFilteringEnabled = false;
        this.privacyLevel = Math.max(1, Math.min(5, privacyLevel));
        this.locale = locale;
        this.messages = ResourceBundle.getBundle("PrivacyFilter", locale);
        
        initializeDefaultPatterns();
    }
    
    /**
     * Initializes default patterns for sensitive information detection.
     */
    private void initializeDefaultPatterns() {
        // Email pattern
        sensitivePatterns.put("email", 
            Pattern.compile(messages.getString("pattern.email")));
        
        // Phone number pattern
        sensitivePatterns.put("phone", 
            Pattern.compile(messages.getString("pattern.phone")));
        
        // Add default sensitive terms
        sensitiveTerms.add(messages.getString("term.password"));
        sensitiveTerms.add(messages.getString("term.secret"));
        sensitiveTerms.add(messages.getString("term.private"));
        sensitiveTerms.add(messages.getString("term.confidential"));
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
        if (term == null || term.isEmpty()) {
            return false;
        }
        return sensitiveTerms.add(term.toLowerCase(locale));
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
            Pattern pattern = entry.getValue();
            if (pattern != null) {
                Matcher matcher = pattern.matcher(result);
                result = matcher.replaceAll(messages.getString("redacted." + entry.getKey()));
            }
        }
        
        // Apply term-based filtering if privacy level is high enough
        if (privacyLevel >= 3) {
            // Create a copy of the terms to avoid concurrent modification
            for (String term : sensitiveTerms.toArray(new String[0])) {
                if (term != null) {
                    // Case insensitive replace using the current locale
                    String pattern = "(?i)" + Pattern.quote(term);
                    result = result.replaceAll(pattern, messages.getString("redacted.term"));
                }
            }
        }
        
        // Apply stricter filtering based on privacy level
        if (privacyLevel >= 4) {
            // For high privacy levels, redact numeric sequences that might be sensitive
            if (strictFilteringEnabled) {
                result = result.replaceAll("\\b\\d{4,}\\b", messages.getString("redacted.number"));
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
            if (pattern != null && pattern.matcher(content).find()) {
                return true;
            }
        }
        
        // Convert content to lowercase using the current locale for comparison
        String lowerContent = content.toLowerCase(locale);
        
        // Check terms using a snapshot to avoid concurrent modification
        for (String term : sensitiveTerms.toArray(new String[0])) {
            if (term != null && lowerContent.contains(term.toLowerCase(locale))) {
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
        if (container == null) {
            return this;
        }

        // Create a new filter with the higher privacy level between the two
        PrivacyFilter compositeFilter = new PrivacyFilter(
            Math.max(this.privacyLevel, container.getSafetyLevel()),
            this.locale);
        
        // Copy all patterns and terms from this filter atomically
        for (Map.Entry<String, Pattern> entry : this.sensitivePatterns.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                compositeFilter.sensitivePatterns.put(entry.getKey(), entry.getValue());
            }
        }
        
        // Add all terms atomically
        for (String term : this.sensitiveTerms.toArray(new String[0])) {
            if (term != null) {
                compositeFilter.sensitiveTerms.add(term);
            }
        }
        
        // Add safety-specific patterns and terms
        for (String safetyTerm : container.getSafetyTerms()) {
            if (safetyTerm != null) {
                compositeFilter.addSensitiveTerm(safetyTerm);
            }
        }
        
        return compositeFilter;
    }
}






