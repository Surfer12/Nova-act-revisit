package com.fractal.browser.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fractal.browser.core.NovaActClient;
import com.fractal.browser.core.Act;

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