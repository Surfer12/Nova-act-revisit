package com.fractal.browser.collective.sync;

import java.util.Map;

public interface SynchronizationProtocol {
    void synchronizeDataType(String dataType, String contextId);
    
    void synchronizeDataType(String dataType, Map<String, Object> data, String contextId);
    
    Map<String, Object> getSynchronizedData(String dataType, String contextId);
    
    void updateSynchronizedData(String dataType, Map<String, Object> updates, String contextId);
    
    void removeSynchronizedData(String dataType, String contextId);
} 