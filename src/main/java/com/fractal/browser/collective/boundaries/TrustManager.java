package com.fractal.browser.collective.boundaries;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.time.Instant;
import java.util.List;
import java.util.ArrayList;

/**
 * TrustManager implements a fractal trust model for the collective system, 
 * managing trust relationships between nodes and contexts. It provides mechanisms
 * for trust assessment, trust evolution over time, and trust propagation through
 * the network.
 * 
 * This class works with other boundary components to create a comprehensive
 * trust boundary system that operates recursively across different scales.
 */
public class TrustManager {
    
    // Trust score map: contextId -> TrustData
    private final Map<String, TrustData> trustScores;
    
    // Trust decay rate per day (reduces trust if no interactions)
    private double trustDecayRate;
    
    // Threshold below which trust is considered insufficient
    private double minTrustThreshold;
    
    /**
     * Internal class to store trust data and history.
     */
    private static class TrustData {
        private double score; // 0.0 to 1.0
        private Instant lastUpdated;
        private final List<TrustInteraction> interactions;
        
        public TrustData(double initialScore) {
            this.score = Math.max(0.0, Math.min(1.0, initialScore));
            this.lastUpdated = Instant.now();
            this.interactions = new ArrayList<>();
        }
        
        public void recordInteraction(TrustInteraction interaction) {
            interactions.add(interaction);
            lastUpdated = Instant.now();
        }
    }
    
    /**
     * Represents a trust interaction that affects trust scores.
     */
    public static class TrustInteraction {
        private final String description;
        private final double impact; // Positive or negative impact on trust
        private final Instant timestamp;
        
        public TrustInteraction(String description, double impact) {
            this.description = description;
            this.impact = impact;
            this.timestamp = Instant.now();
        }
        
        public String getDescription() {
            return description;
        }
        
        public double getImpact() {
            return impact;
        }
        
        public Instant getTimestamp() {
            return timestamp;
        }
    }
    
    /**
     * Creates a new TrustManager with default settings.
     */
    public TrustManager() {
        this.trustScores = new ConcurrentHashMap<>();
        this.trustDecayRate = 0.01; // 1% decay per day
        this.minTrustThreshold = 0.3;
    }
    
    /**
     * Creates a new TrustManager with the specified trust decay rate.
     * 
     * @param trustDecayRate The daily trust decay rate (0.0-1.0)
     * @param minTrustThreshold The minimum trust threshold (0.0-1.0)
     */
    public TrustManager(double trustDecayRate, double minTrustThreshold) {
        this.trustScores = new ConcurrentHashMap<>();
        this.trustDecayRate = Math.max(0.0, Math.min(1.0, trustDecayRate));
        this.minTrustThreshold = Math.max(0.0, Math.min(1.0, minTrustThreshold));
    }
    
    /**
     * Initializes a trust relationship with the given context.
     * 
     * @param contextId The unique identifier for the context
     * @param initialTrustScore The initial trust score (0.0-1.0)
     * @return true if successfully initialized, false if already exists
     */
    public boolean initializeTrust(String contextId, double initialTrustScore) {
        if (trustScores.containsKey(contextId)) {
            return false; // Trust already initialized
        }
        
        trustScores.put(contextId, new TrustData(initialTrustScore));
        return true;
    }
    
    /**
     * Records a trust interaction for the specified context.
     * 
     * @param contextId The context ID
     * @param description A description of the interaction
     * @param impact The impact on trust (-1.0 to 1.0)
     * @return The new trust score
     */
    public double recordInteraction(String contextId, String description, double impact) {
        // Ensure context exists
        trustScores.putIfAbsent(contextId, new TrustData(0.5)); // Default moderate trust
        
        TrustData data = trustScores.get(contextId);
        TrustInteraction interaction = new TrustInteraction(description, impact);
        data.recordInteraction(interaction);
        
        // Update trust score
        double newScore = data.score + impact;
        // Ensure score stays within bounds
        newScore = Math.max(0.0, Math.min(1.0, newScore));
        data.score = newScore;
        
        return newScore;
    }
    
    /**
     * Gets the current trust score for the context.
     * 
     * @param contextId The context ID
     * @return An Optional containing the trust score, or empty if not found
     */
    public Optional<Double> getTrustScore(String contextId) {
        if (!trustScores.containsKey(contextId)) {
            return Optional.empty();
        }
        
        // Apply trust decay based on time since last update
        TrustData data = trustScores.get(contextId);
        applyTrustDecay(data);
        
        return Optional.of(data.score);
    }
    
    /**
     * Applies trust decay based on time elapsed since last update.
     * 
     * @param data The trust data to update
     */
    private void applyTrustDecay(TrustData data) {
        Instant now = Instant.now();
        long daysSinceUpdate = java.time.Duration.between(data.lastUpdated, now).toDays();
        
        if (daysSinceUpdate > 0) {
            // Apply compound decay: score = score * (1 - decayRate)^days
            double decayFactor = Math.pow(1.0 - trustDecayRate, daysSinceUpdate);
            data.score = data.score * decayFactor;
            data.lastUpdated = now;
        }
    }
    
    /**
     * Sets the trust decay rate.
     * 
     * @param rate The daily decay rate (0.0-1.0)
     */
    public void setTrustDecayRate(double rate) {
        this.trustDecayRate = Math.max(0.0, Math.min(1.0, rate));
    }
    
    /**
     * Sets the minimum trust threshold.
     * 
     * @param threshold The minimum threshold (0.0-1.0)
     */
    public void setMinTrustThreshold(double threshold) {
        this.minTrustThreshold = Math.max(0.0, Math.min(1.0, threshold));
    }
    
    /**
     * Checks if the trust level is sufficient for the given context.
     * 
     * @param contextId The context ID
     * @return true if trust is sufficient, false otherwise
     */
    public boolean isTrustSufficient(String contextId) {
        Optional<Double> trustScore = getTrustScore(contextId);
        return trustScore.isPresent() && trustScore.get() >= minTrustThreshold;
    }
    
    /**
     * Gets the trust level categorization for a context.
     * 
     * @param contextId The context ID
     * @return A string representing the trust level category
     */
    public String getTrustLevel(String contextId) {
        Optional<Double> trustScore = getTrustScore(contextId);
        
        if (!trustScore.isPresent()) {
            return "UNKNOWN";
        }
        
        double score = trustScore.get();
        
        if (score < 0.2) return "VERY_LOW";
        if (score < 0.4) return "LOW";
        if (score < 0.6) return "MODERATE";
        if (score < 0.8) return "HIGH";
        return "VERY_HIGH";
    }
    
    /**
     * Gets a map of metadata about the trust relationship.
     * 
     * @param contextId The context ID
     * @return A map of trust metadata, or empty map if context not found
     */
    public Map<String, Object> getTrustMetadata(String contextId) {
        Map<String, Object> metadata = new HashMap<>();
        
        if (!trustScores.containsKey(contextId)) {
            return metadata;
        }
        
        TrustData data = trustScores.get(contextId);
        applyTrustDecay(data);
        
        metadata.put("contextId", contextId);
        metadata.put("trustScore", data.score);
        metadata.put("trustLevel", getTrustLevel(contextId));
        metadata.put("lastUpdated", data.lastUpdated.toString());
        metadata.put("interactionCount", data.interactions.size());
        metadata.put("trustThreshold", minTrustThreshold);
        metadata.put("isTrustSufficient", data.score >= minTrustThreshold);
        
        return metadata;
    }
    
    /**
     * Propagates trust from one context to another based on a transitive relationship.
     * 
     * @param sourceContextId The source context with established trust
     * @param targetContextId The target context to propagate trust to
     * @param propagationFactor How much trust is propagated (0.0-1.0)
     * @return true if propagation succeeded, false otherwise
     */
    public boolean propagateTrust(String sourceContextId, String targetContextId, double propagationFactor) {
        Optional<Double> sourceTrust = getTrustScore(sourceContextId);
        
        if (!sourceTrust.isPresent()) {
            return false;
        }
        
        double adjustedPropagation = Math.max(0.0, Math.min(1.0, propagationFactor));
        double propagatedTrust = sourceTrust.get() * adjustedPropagation;
        
        // If target already exists, blend the trust scores
        if (trustScores.containsKey(targetContextId)) {
            TrustData targetData = trustScores.get(targetContextId);
            applyTrustDecay(targetData);
            
            // Blend existing trust with propagated trust
            double blendedTrust = (targetData.score + propagatedTrust) / 2.0;
            targetData.score = blendedTrust;
            targetData.lastUpdated = Instant.now();
        } else {
            // Initialize new trust relationship with propagated trust
            trustScores.put(targetContextId, new TrustData(propagatedTrust));
        }
        
        return true;
    }
    
    /**
     * Integrates with the InformationBoundary to create trust-based information control.
     * 
     * @param boundary The InformationBoundary to integrate with
     * @param contextId The context to apply
     * @return true if integration succeeded, false otherwise
     */
    public boolean integrateWithInformationBoundary(InformationBoundary boundary, String contextId) {
        Optional<Double> trustScore = getTrustScore(contextId);
        
        if (!trustScore.isPresent()) {
            return false;
        }
        
        // Convert trust score to access level (0-10)
        int accessLevel = (int) Math.round(trustScore.get() * 10);
        
        // Register the context with the boundary
        return boundary.registerContext(contextId, accessLevel);
    }
}