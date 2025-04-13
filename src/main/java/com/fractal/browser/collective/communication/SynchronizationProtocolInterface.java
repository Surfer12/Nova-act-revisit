package com.fractal.browser.collective.communication;

import java.util.Map;

/**
 * Interface defining the contract for synchronization protocols in the fractal processing system.
 */
public interface SynchronizationProtocolInterface {
    /**
     * Synchronizes data across nodes
     * @param dataType The type of data to synchronize
     * @param contextId The context identifier
     */
    void synchronizeDataType(String dataType, String contextId);
    
    /**
     * Gets synchronized data
     * @param dataType The type of data
     * @param contextId The context identifier
     * @return Map containing synchronized data
     */
    Map<String, Object> getSynchronizedData(String dataType, String contextId);
} 