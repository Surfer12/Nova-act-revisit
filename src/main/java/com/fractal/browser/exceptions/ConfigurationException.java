package com.fractal.browser.exceptions;

/**
 * Exception thrown when configuration validation fails.
 */
public class ConfigurationException extends FractalBrowserException {
    
    public ConfigurationException(String message) {
        super(message);
    }
    
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
} 