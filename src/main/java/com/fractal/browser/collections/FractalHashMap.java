package com.fractal.browser.collections;

import java.util.HashMap;
import com.fractal.browser.exceptions.FractalBrowserException;

public class FractalHashMap<K, V> extends HashMap<K, V> {
    private final int maxDepth;
    
    public FractalHashMap(int maxDepth) {
        this.maxDepth = maxDepth;
    }
    
    public void putRecursive(K key, V value, int depth) {
        if (depth > maxDepth) {
            throw new FractalBrowserException("Maximum recursion depth exceeded");
        }
        // Implementation
    }
}