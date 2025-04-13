package com.fractal.browser.collective.processing;

import com.fractal.browser.collective.memory.DistributedInsightRepository;
import java.util.*;

public class CrossNodeIntegration {
    private DistributedInsightRepository repository;
    
    public CrossNodeIntegration(DistributedInsightRepository repository) {
        this.repository = repository;
    }
    
    private Set<String> extractInsightTypes(Map<String, Map<String, Object>> insights) {
        Set<String> types = new HashSet<>();
        for (Map.Entry<String, Map<String, Object>> entry : insights.entrySet()) {
            types.add((String) entry.getValue().get("type"));
        }
        return types;
    }
    
    public void processInsight(DistributedInsightRepository.Insight insight) {
        // Process the insight
        Map<String, Object> insightData = new HashMap<>();
        insightData.put("id", insight.getId());
        insightData.put("type", insight.getType());
        insightData.put("attributes", insight.getAttributes());
        insightData.put("timestamp", insight.getTimestamp());
        
        // Store the processed insight
        repository.storeInsight(
            insight.getId(),
            insight.getType(),
            new HashSet<>(),  // Empty tags set
            insight.getAttributes(),
            1.0  // Default confidence
        );
    }
    
    public void integrateInsights(Map<String, Map<String, Object>> insights) {
        Set<String> types = extractInsightTypes(insights);
        // Integration logic...
    }
}