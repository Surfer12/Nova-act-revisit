package com.fractal.browser.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class AnalysisResult {
    private final List<Pattern> patterns;
    private final Map<String, Double> metrics;
    private final List<String> insights;
    
    private AnalysisResult(Builder builder) {
        this.patterns = new ArrayList<>(builder.patterns);
        this.metrics = new HashMap<>(builder.metrics);
        this.insights = new ArrayList<>(builder.insights);
    }
    
    public List<Pattern> getPatterns() {
        return new ArrayList<>(patterns);
    }
    
    public Map<String, Double> getMetrics() {
        return new HashMap<>(metrics);
    }
    
    public List<String> getInsights() {
        return new ArrayList<>(insights);
    }
    
    public static class Builder {
        private List<Pattern> patterns = new ArrayList<>();
        private Map<String, Double> metrics = new HashMap<>();
        private List<String> insights = new ArrayList<>();
        
        public Builder patterns(List<Pattern> patterns) {
            this.patterns = new ArrayList<>(patterns);
            return this;
        }
        
        public Builder addPattern(Pattern pattern) {
            this.patterns.add(pattern);
            return this;
        }
        
        public Builder metrics(Map<String, Double> metrics) {
            this.metrics = new HashMap<>(metrics);
            return this;
        }
        
        public Builder addMetric(String key, Double value) {
            this.metrics.put(key, value);
            return this;
        }
        
        public Builder insights(List<String> insights) {
            this.insights = new ArrayList<>(insights);
            return this;
        }
        
        public Builder addInsight(String insight) {
            this.insights.add(insight);
            return this;
        }
        
        public AnalysisResult build() {
            return new AnalysisResult(this);
        }
    }
}