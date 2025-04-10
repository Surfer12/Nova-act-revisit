package com.fractal.browser.transform;

public class TransformationRule {
    private final String pattern;
    private final String replacement;
    
    public TransformationRule(String pattern, String replacement) {
        this.pattern = pattern;
        this.replacement = replacement;
    }
    
    public String apply(String input) {
        // Implementation
        return input;
    }
}