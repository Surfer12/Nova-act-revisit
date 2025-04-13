package com.fractal.browser.collective.discovery;

import java.util.Set;
import java.util.Map;
import java.util.function.Predicate;

public interface NodeDiscovery {
    Set<String> discoverNodes(Predicate<Map<String, Object>> filter);
    
    boolean isNodeAvailable(String nodeId);
    
    Map<String, Object> getNodeInfo(String nodeId);
    
    void registerNode(String nodeId, Map<String, Object> info);
    
    void unregisterNode(String nodeId);
} 