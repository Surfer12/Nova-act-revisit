package com.fractal.browser.metrics;

import java.util.List;
import java.util.Map;

public class MetricsCollector {
    private final Map<String, Double> metrics;
    private final List<MetricsListener> listeners;
    
    public void recordMetric(String name, double value) {
        metrics.put(name, value);
        notifyListeners(name, value);
    }
    
    private void notifyListeners(String name, double value) {
        listeners.forEach(l -> l.onMetricUpdated(name, value));
    }
}