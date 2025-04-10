package com.fractal.browser.collective.memory;

import java.util.List;
import java.util.Map;

/**
 * PersistenceStrategy defines an interface for storing and retrieving data in the
 * collective memory system. This interface allows for different persistence implementations
 * (file, database, distributed storage, etc.) while providing a consistent API.
 * 
 * The strategy applies fractal storage patterns to manage data across different scales,
 * from individual nodes to the full collective.
 */
public interface PersistenceStrategy {
    
    /**
     * Stores data under the specified type and ID.
     * 
     * @param dataType The type of data (e.g., "insights", "patterns")
     * @param id The unique identifier for the data
     * @param data The data to store
     * @throws Exception If there is an error during storage
     */
    void store(String dataType, String id, Map<String, Object> data) throws Exception;
    
    /**
     * Loads data for the specified type and ID.
     * 
     * @param dataType The type of data to load
     * @param id The unique identifier
     * @return The loaded data, or null if not found
     * @throws Exception If there is an error during loading
     */
    Map<String, Object> load(String dataType, String id) throws Exception;
    
    /**
     * Loads all data of the specified type.
     * 
     * @param dataType The type of data to load
     * @return A list of data maps
     * @throws Exception If there is an error during loading
     */
    List<Map<String, Object>> loadAll(String dataType) throws Exception;
    
    /**
     * Deletes data for the specified type and ID.
     * 
     * @param dataType The type of data to delete
     * @param id The unique identifier
     * @throws Exception If there is an error during deletion
     */
    void delete(String dataType, String id) throws Exception;
    
    /**
     * Checks if data exists for the specified type and ID.
     * 
     * @param dataType The type of data to check
     * @param id The unique identifier
     * @return true if data exists, false otherwise
     * @throws Exception If there is an error during the check
     */
    boolean exists(String dataType, String id) throws Exception;
    
    /**
     * Initializes the persistence system.
     * 
     * @throws Exception If there is an error during initialization
     */
    void initialize() throws Exception;
    
    /**
     * Shuts down the persistence system.
     * 
     * @throws Exception If there is an error during shutdown
     */
    void shutdown() throws Exception;
    
    /**
     * Performs a query against the stored data.
     * 
     * @param dataType The type of data to query
     * @param query A map of query parameters
     * @return A list of matching data maps
     * @throws Exception If there is an error during the query
     */
    List<Map<String, Object>> query(String dataType, Map<String, Object> query) throws Exception;
    
    /**
     * Creates a backup of the stored data.
     * 
     * @param backupLocation The location for the backup
     * @return true if successful, false otherwise
     * @throws Exception If there is an error during backup
     */
    boolean backup(String backupLocation) throws Exception;
    
    /**
     * Restores data from a backup.
     * 
     * @param backupLocation The location of the backup
     * @return true if successful, false otherwise
     * @throws Exception If there is an error during restoration
     */
    boolean restore(String backupLocation) throws Exception;
}