package com.fractal.browser.collective.boundaries;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * InformationBoundary defines the interface and mechanisms for managing information flow 
 * between collective nodes in a fractal communication network. This class implements
 * boundary conditions that determine what information can pass between nodes based on
 * context, trust levels, and information sensitivity.
 * 
 * It serves as a recursive boundary system, operating at multiple scales to ensure
 * appropriate information sharing while maintaining system integrity.
 */
public class InformationBoundary {
    
    private final Map<String, Integer> contextualAccessLevels;
    private final Map<String, Double> informationSensitivityMap;
    
    /**
     * Creates a new InformationBoundary with default settings.
     */
    public InformationBoundary() {
        this.contextualAccessLevels = new ConcurrentHashMap<>();
        this.informationSensitivityMap = new ConcurrentHashMap<>();
    }
    
    /**
     * Registers a context with a specific access level.
     * Higher access levels indicate greater access to sensitive information.
     *
     * @param contextId The unique identifier for the context
     * @param accessLevel The access level (0-10, where 10 is highest access)
     * @return true if successfully registered, false otherwise
     */
    public boolean registerContext(String contextId, int accessLevel) {
        if (accessLevel < 0 || accessLevel > 10) {
            return false;
        }
        
        contextualAccessLevels.put(contextId, accessLevel);
        return true;
    }
    
    /**
     * Sets the sensitivity level for a piece of information.
     * Higher sensitivity requires higher access levels for distribution.
     *
     * @param informationId The unique identifier for the information
     * @param sensitivityLevel The sensitivity level (0.0-1.0, where 1.0 is highest sensitivity)
     * @return true if successfully set, false otherwise
     */
    public boolean setSensitivity(String informationId, double sensitivityLevel) {
        if (sensitivityLevel < 0.0 || sensitivityLevel > 1.0) {
            return false;
        }
        
        informationSensitivityMap.put(informationId, sensitivityLevel);
        return true;
    }
    
    /**
     * Evaluates whether information can pass through the boundary in the given context.
     *
     * @param informationId The unique identifier for the information
     * @param contextId The context in which the information is being accessed
     * @return true if information can pass, false otherwise
     */
    public boolean canInformationPass(String informationId, String contextId) {
        Integer accessLevel = contextualAccessLevels.getOrDefault(contextId, 0);
        Double sensitivityLevel = informationSensitivityMap.getOrDefault(informationId, 1.0);
        
        // Calculate the required access level based on sensitivity
        int requiredAccessLevel = (int) Math.ceil(sensitivityLevel * 10);
        
        return accessLevel >= requiredAccessLevel;
    }
    
    /**
     * Gets the metadata about information passing constraints for a given context,
     * including required access levels and current permissions.
     *
     * @param contextId The context to evaluate
     * @return A map of metadata about the boundary conditions for this context
     */
    public Map<String, Object> getBoundaryMetadata(String contextId) {
        Map<String, Object> metadata = new ConcurrentHashMap<>();
        
        Integer accessLevel = contextualAccessLevels.getOrDefault(contextId, 0);
        metadata.put("contextId", contextId);
        metadata.put("accessLevel", accessLevel);
        metadata.put("maxSensitivityAllowed", accessLevel / 10.0);
        
        return metadata;
    }
    
    /**
     * Transforms information to comply with boundary conditions, potentially
     * redacting or summarizing sensitive content that cannot be shared fully.
     *
     * @param information The original information
     * @param contextId The context in which the information is being accessed
     * @return The transformed information that complies with boundary conditions
     */
    public String transformInformationForContext(String information, String contextId) {
        Integer accessLevel = contextualAccessLevels.getOrDefault(contextId, 0);
        
        // Basic transformation logic - in a real implementation, this would be more sophisticated
        if (accessLevel < 3) {
            return "[Redacted - Insufficient Access Level]";
        } else if (accessLevel < 7) {
            // Return a summarized version for medium access levels
            return "Summary: " + information.substring(0, Math.min(50, information.length())) 
                + (information.length() > 50 ? "..." : "");
        }
        
        // Full access for high access levels
        return information;
    }
    
    /**
     * Integrates with the TrustManager to adjust boundary conditions based on trust levels.
     *
     * @param trustManager The TrustManager to integrate with
     * @param contextId The context to update based on trust
     * @return true if successfully integrated, false otherwise
     */
    public boolean integrateWithTrustManager(TrustManager trustManager, String contextId) {
        Optional<Double> trustScore = trustManager.getTrustScore(contextId);
        
        if (trustScore.isPresent()) {
            // Adjust access level based on trust score (0.0-1.0)
            int newAccessLevel = (int) Math.round(trustScore.get() * 10);
            contextualAccessLevels.put(contextId, newAccessLevel);
            return true;
        }
        
        return false;
    }
}