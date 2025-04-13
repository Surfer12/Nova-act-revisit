package com.fractal.browser.collective.exchange;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InsightExchange {
    List<Map<String, Object>> exchangeInsights(String contextId, Set<String> nodeIds, String tag);
    
    void broadcastInsight(Map<String, Object> insight, String contextId);
    
    void sendInsightToNode(Map<String, Object> insight, String nodeId, String contextId);
    
    List<Map<String, Object>> receiveInsights(String contextId);
    
    void acknowledgeInsight(String insightId, String nodeId, String contextId);
} 