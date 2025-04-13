package com.fractal.browser.collective.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Optional;
import com.fractal.browser.collective.model.Insight;

public interface DistributedInsightRepository {
    Optional<Insight> getInsight(String insightId, String contextId);
    
    String storeInsight(String id, String contextId, Set<String> tags, Map<String, Object> data, double confidence);
    
    List<Insight> findRecent(int limit, String contextId);
    
    List<Insight> findByTag(String tag, String contextId);
    
    void updateInsight(String insightId, Map<String, Object> updates, String contextId);
    
    void deleteInsight(String insightId, String contextId);
} 