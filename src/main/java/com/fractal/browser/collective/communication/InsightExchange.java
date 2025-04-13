package com.fractal.browser.collective.communication;

import com.fractal.browser.collective.memory.DistributedInsightRepository;
import java.util.*;

public class InsightExchange {
    private SynchronizationProtocol syncProtocol;
    private DistributedInsightRepository repository;
    
    public InsightExchange(SynchronizationProtocol syncProtocol, DistributedInsightRepository repository) {
        this.syncProtocol = syncProtocol;
        this.repository = repository;
    }
    
    public void exchangeInsight(Map<String, Object> insight, String nodeId) {
        // Convert insight map to proper format before synchronizing
        String type = (String) insight.get("type");
        syncProtocol.synchronizeDataType(type, insight, nodeId);
    }
    
    // Other methods...
}