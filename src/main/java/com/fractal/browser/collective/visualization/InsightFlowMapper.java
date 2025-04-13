package com.fractal.browser.collective.visualization;

import com.fractal.browser.collective.memory.DistributedInsightRepository;
import java.util.*;

public class InsightFlowMapper {
    
    public Optional<Map<String, Object>> convertInsight(Optional<DistributedInsightRepository.Insight> insight) {
        if (!insight.isPresent()) {
            return Optional.empty();
        }
        
        DistributedInsightRepository.Insight i = insight.get();
        Map<String, Object> converted = new HashMap<>();
        converted.put("id", i.getId());
        converted.put("type", i.getType());
        converted.put("attributes", i.getAttributes());
        converted.put("timestamp", i.getTimestamp());
        
        return Optional.of(converted);
    }
}