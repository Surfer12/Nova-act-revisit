package com.fractal.browser.collective.core;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MetaAwarenessNetwork manages meta-level awareness across the collective network.
 * It tracks patterns, insights, and relationships at a higher level of abstraction.
 */
public class MetaAwarenessNetwork {
    private final Map<String, Set<String>> patternConnections;
    private final Map<String, Map<String, Double>> nodeAffinities;
    private final Map<String, List<String>> insightChains;
    
    public MetaAwarenessNetwork() {
        this.patternConnections = new ConcurrentHashMap<>();
        this.nodeAffinities = new ConcurrentHashMap<>();
        this.insightChains = new ConcurrentHashMap<>();
    }
    
    /**
     * Records a connection between patterns.
     * 
     * @param pattern1 First pattern ID
     * @param pattern2 Second pattern ID
     */
    public void connectPatterns(String pattern1, String pattern2) {
        patternConnections.computeIfAbsent(pattern1, k -> new HashSet<>()).add(pattern2);
        patternConnections.computeIfAbsent(pattern2, k -> new HashSet<>()).add(pattern1);
    }
    
    /**
     * Updates affinity between nodes based on their interaction.
     * 
     * @param node1 First node ID
     * @param node2 Second node ID
     * @param affinityDelta Change in affinity (-1.0 to 1.0)
     */
    public void updateNodeAffinity(String node1, String node2, double affinityDelta) {
        nodeAffinities.computeIfAbsent(node1, k -> new HashMap<>())
                     .merge(node2, affinityDelta, Double::sum);
        nodeAffinities.computeIfAbsent(node2, k -> new HashMap<>())
                     .merge(node1, affinityDelta, Double::sum);
    }
    
    /**
     * Records a chain of related insights.
     * 
     * @param insightId The insight ID
     * @param relatedInsightId The related insight ID
     */
    public void recordInsightChain(String insightId, String relatedInsightId) {
        insightChains.computeIfAbsent(insightId, k -> new ArrayList<>())
                    .add(relatedInsightId);
    }
    
    /**
     * Gets patterns connected to the given pattern.
     * 
     * @param patternId The pattern ID
     * @return Set of connected pattern IDs
     */
    public Set<String> getConnectedPatterns(String patternId) {
        return new HashSet<>(patternConnections.getOrDefault(patternId, new HashSet<>()));
    }
    
    /**
     * Gets affinity between nodes.
     * 
     * @param node1 First node ID
     * @param node2 Second node ID
     * @return Affinity value, or 0.0 if no affinity recorded
     */
    public double getNodeAffinity(String node1, String node2) {
        return nodeAffinities.getOrDefault(node1, new HashMap<>())
                           .getOrDefault(node2, 0.0);
    }
    
    /**
     * Gets insights in the chain starting from the given insight.
     * 
     * @param insightId The starting insight ID
     * @return List of related insight IDs in chain order
     */
    public List<String> getInsightChain(String insightId) {
        return new ArrayList<>(insightChains.getOrDefault(insightId, new ArrayList<>()));
    }
}
