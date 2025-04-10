package com.fractal.browser.integration;

import com.fractal.browser.model.AnalysisResult;

public interface IntegrationHandler {
    boolean canHandle(AnalysisResult result);
    IntegrationResult handle(AnalysisResult result);
}