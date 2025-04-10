// Core interfaces
package com.fractal.browser.core;

public interface NovaActClient {
    void initialize();
    void executeAct(Act act);
    void shutdown();
}

public interface Act {
    void execute();
    ActResult getResult();
}

// Custom exceptions
package com.fractal.browser.exceptions;

public class FractalBrowserException extends RuntimeException {
    public FractalBrowserException(String message) {
        super(message);
    }
    
    public FractalBrowserException(String message, Throwable cause) {
        super(message, cause);
    }
}

// Implementation classes
package com.fractal.browser.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultNovaActClient implements NovaActClient {
    private static final Logger logger = LoggerFactory.getLogger(DefaultNovaActClient.class);
    
    @Override
    public void initialize() {
        logger.info("Initializing NovaAct client");
        // Implementation
    }
    
    @Override
    public void executeAct(Act act) {
        logger.debug("Executing act: {}", act);
        // Implementation
    }
    
    @Override
    public void shutdown() {
        logger.info("Shutting down NovaAct client");
        // Implementation
    }
}

// Data structures
package com.fractal.browser.collections;

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

// Analysis components
package com.fractal.browser.analysis;

public interface Analyzer<T, R> {
    R analyze(T input);
    void setDepth(int depth);
}

public class JournalAnalyzer implements Analyzer<JournalEntry, AnalysisResult> {
    @Override
    public AnalysisResult analyze(JournalEntry input) {
        // Implementation
        return null;
    }
    
    @Override
    public void setDepth(int depth) {
        // Implementation
    }
}

// CLI interface
package com.fractal.browser.cli;

public class FractalBrowserCLI {
    private final NovaActClient client;
    
    public FractalBrowserCLI(NovaActClient client) {
        this.client = client;
    }
    
    public void start() {
        // Implementation
    }
}
