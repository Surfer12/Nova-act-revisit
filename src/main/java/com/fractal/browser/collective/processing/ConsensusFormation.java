package com.fractal.browser.collective.processing;

import com.fractal.browser.collective.communication.SynchronizationProtocol;
import java.util.*;

public class ConsensusFormation {
    private SynchronizationProtocol syncProtocol;
    
    public ConsensusFormation(SynchronizationProtocol syncProtocol) {
        this.syncProtocol = syncProtocol;
    }
    
    private Set<String> extractInsightTypes(Map<String, Map<String, Object>> insights) {
        Set<String> types = new HashSet<>();
        for (Map.Entry<String, Map<String, Object>> entry : insights.entrySet()) {
            types.add((String) entry.getValue().get("type"));
        }
        return types;
    }
    
    public void synchronizeInsight(String type, Map<String, Object> data, String nodeId) {
        syncProtocol.synchronizeDataType(type, data, nodeId);
    }
    
    public void processConsensus(Map<String, Map<String, Object>> insights) {
        Set<String> types = extractInsightTypes(insights);
        // Process consensus...
    }
}