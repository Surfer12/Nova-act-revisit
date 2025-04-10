package com.fractal.browser.exceptions;

public class FractalBrowserException extends RuntimeException {
    public FractalBrowserException(String message) {
        super(message);
    }
    
    public FractalBrowserException(String message, Throwable cause) {
        super(message, cause);
    }
}