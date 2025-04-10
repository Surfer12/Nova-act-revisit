package com.fractal.browser.metrics;

public interface MetricsListener {
    void onMetricUpdated(String name, double value);
}