package com.fractal.browser.collective.processing;

import com.fractal.browser.collective.memory.DistributedInsightRepository;
import java.util.*;

public class EmergentPatternDetector {
    private DistributedInsightRepository insightRepository;
    
    public EmergentPatternDetector(DistributedInsightRepository repository) {
        this.insightRepository = repository;
    }
    
    public void detectPatterns(String type) {
        // Get recent insights of the specified type
        List<DistributedInsightRepository.Insight> recentInsights = 
            insightRepository.getRecentInsights(100, type);  // Get last 100 insights
        
        for (DistributedInsightRepository.Insight insight : recentInsights) {
            Map<String, Object> attributes = insight.getAttributes();
            // Pattern detection logic...
        }
    }
    
    public void processInsight(DistributedInsightRepository.Insight insight) {
        // Convert insight to proper format
        Map<String, Object> insightData = new HashMap<>();
        insightData.put("id", insight.getId());
        insightData.put("type", insight.getType());
        insightData.put("attributes", insight.getAttributes());
        insightData.put("timestamp", insight.getTimestamp());
        
        // Store the insight with proper parameters
        insightRepository.storeInsight(
            insight.getId(),  // id
            insight.getType(),  // type
            new HashSet<>(),  // tags
            insight.getAttributes(),  // attributes
            1.0  // default confidence
        );
    }
}