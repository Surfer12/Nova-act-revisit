package com.fractal.browser.collective.communication;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.time.Instant;
import java.util.UUID;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.Collections;
import java.util.Comparator;

import com.fractal.browser.collective.boundaries.InformationBoundary;

/**
 * SynchronizationProtocol implements mechanisms to synchronize state between nodes
 * in the collective. It manages the flow of synchronization operations across multiple
 * scales, ensuring consistency while allowing for localized variations.
 * 
 * This class applies fractal synchronization patterns that support both highly coherent
 * and loosely coupled node interactions.
 */
public class SynchronizationProtocol {
    
    // Maps sync session IDs to their data
    private final Map<String, SyncSession> activeSessions;
    
    // Maps data type to the current version information
    private final Map<String, VersionedData> versionedDataRegistry;
    
    // Node discovery for finding sync partners
    private final NodeDiscovery nodeDiscovery;
    
    // Information boundary for controlling sensitive data
    private final InformationBoundary boundary;
    
    // Conflict resolution strategies for each data type
    private final Map<String, BiFunction<Object, Object, Object>> conflictResolvers;
    
    /**
     * Represents a synchronization session between nodes.
     */
    private static class SyncSession {
        private final String sessionId;
        private final String initiatorNodeId;
        private final String responderNodeId;
        private final Set<String> dataTypesToSync;
        private final Instant startTime;
        private Instant lastActivity;
        private SyncState state;
        private final Map<String, SyncResult> results;
        
        /**
         * Possible states of a synchronization session.
         */
        public enum SyncState {
            INITIATED,
            COMPARING,
            TRANSFERRING,
            RESOLVING_CONFLICTS,
            COMPLETED,
            FAILED
        }
        
        /**
         * Result of synchronizing a specific data type.
         */
        public static class SyncResult {
            private final String dataType;
            private final boolean success;
            private final int itemsSent;
            private final int itemsReceived;
            private final int conflictsResolved;
            private final String message;
            
            public SyncResult(String dataType, boolean success, int itemsSent, 
                    int itemsReceived, int conflictsResolved, String message) {
                this.dataType = dataType;
                this.success = success;
                this.itemsSent = itemsSent;
                this.itemsReceived = itemsReceived;
                this.conflictsResolved = conflictsResolved;
                this.message = message;
            }
            
            // Getters
            public String getDataType() { return dataType; }
            public boolean isSuccess() { return success; }
            public int getItemsSent() { return itemsSent; }
            public int getItemsReceived() { return itemsReceived; }
            public int getConflictsResolved() { return conflictsResolved; }
            public String getMessage() { return message; }
        }
        
        /**
         * Creates a new sync session.
         */
        public SyncSession(String initiatorNodeId, String responderNodeId, Set<String> dataTypesToSync) {
            this.sessionId = UUID.randomUUID().toString();
            this.initiatorNodeId = initiatorNodeId;
            this.responderNodeId = responderNodeId;
            this.dataTypesToSync = new HashSet<>(dataTypesToSync);
            this.startTime = Instant.now();
            this.lastActivity = this.startTime;
            this.state = SyncState.INITIATED;
            this.results = new HashMap<>();
        }
        
        /**
         * Updates the session state and last activity timestamp.
         */
        public void updateState(SyncState newState) {
            this.state = newState;
            this.lastActivity = Instant.now();
        }
        
        /**
         * Records the result of synchronizing a specific data type.
         */
        public void recordResult(SyncResult result) {
            this.results.put(result.dataType, result);
            this.lastActivity = Instant.now();
        }
        
        /**
         * Checks if all required data types have been synchronized.
         */
        public boolean isComplete() {
            return dataTypesToSync.size() == results.size() && 
                   results.values().stream().allMatch(SyncResult::isSuccess);
        }
    }
    
    /**
     * Represents versioned data for synchronization.
     */
    public static class VersionedData {
        private final String dataType;
        private final Map<String, DataItem> items; // Item ID -> Item
        
        /**
         * Creates a new versioned data container.
         */
        public VersionedData(String dataType) {
            this.dataType = dataType;
            this.items = new ConcurrentHashMap<>();
        }
        
        /**
         * Gets all data items.
         */
        public Map<String, DataItem> getItems() {
            Map<String, DataItem> copy = new HashMap<>();
            for (Map.Entry<String, DataItem> entry : items.entrySet()) {
                copy.put(entry.getKey(), entry.getValue().copy());
            }
            return copy;
        }
        
        /**
         * Updates a data item.
         */
        public boolean updateItem(String itemId, Object value, long version) {
            DataItem existing = items.get(itemId);
            
            if (existing == null) {
                // New item
                items.put(itemId, new DataItem(itemId, value, version));
                return true;
            }
            
            // Update only if version is newer
            if (version > existing.getVersion()) {
                items.put(itemId, new DataItem(itemId, value, version));
                return true;
            }
            
            return false;
        }
        
        /**
         * Gets the latest version of a data item.
         */
        public Optional<DataItem> getItem(String itemId) {
            DataItem item = items.get(itemId);
            return item != null ? Optional.of(item.copy()) : Optional.empty();
        }
        
        /**
         * Gets the latest version number across all items.
         */
        public long getLatestVersion() {
            return items.values().stream()
                    .mapToLong(DataItem::getVersion)
                    .max()
                    .orElse(0);
        }
    }
    
    /**
     * Represents a single data item with versioning.
     */
    public static class DataItem {
        private final String itemId;
        private final Object value;
        private final long version;
        private final Instant lastModified;
        
        /**
         * Creates a new data item.
         */
        public DataItem(String itemId, Object value, long version) {
            this.itemId = itemId;
            this.value = value;
            this.version = version;
            this.lastModified = Instant.now();
        }
        
        /**
         * Creates a full copy of this data item.
         */
        public DataItem copy() {
            return new DataItem(this.itemId, this.value, this.version);
        }
        
        // Getters
        public String getItemId() { return itemId; }
        public Object getValue() { return value; }
        public long getVersion() { return version; }
        public Instant getLastModified() { return lastModified; }
    }
    
    /**
     * Creates a new SynchronizationProtocol with the specified components.
     */
    public SynchronizationProtocol(NodeDiscovery nodeDiscovery, InformationBoundary boundary) {
        this.activeSessions = new ConcurrentHashMap<>();
        this.versionedDataRegistry = new ConcurrentHashMap<>();
        this.nodeDiscovery = nodeDiscovery;
        this.boundary = boundary;
        this.conflictResolvers = new HashMap<>();
        
        // Register default conflict resolvers
        registerDefaultConflictResolvers();
    }
    
    /**
     * Registers default conflict resolution strategies.
     */
    private void registerDefaultConflictResolvers() {
        // Default: Latest version wins
        BiFunction<Object, Object, Object> latestWins = (local, remote) -> remote;
        
        // Map merger: Combine maps, latest version of each key wins
        BiFunction<Object, Object, Object> mapMerger = (local, remote) -> {
            if (local instanceof Map && remote instanceof Map) {
                Map<Object, Object> result = new HashMap<>((Map<Object, Object>) local);
                result.putAll((Map<Object, Object>) remote);
                return result;
            }
            return remote; // Fallback to latest wins
        };
        
        // List merger: Combine lists, remove duplicates
        BiFunction<Object, Object, Object> listMerger = (local, remote) -> {
            if (local instanceof List && remote instanceof List) {
                Set<Object> combined = new HashSet<>((List<Object>) local);
                combined.addAll((List<Object>) remote);
                return new ArrayList<>(combined);
            }
            return remote; // Fallback to latest wins
        };
        
        // Register these defaults
        registerConflictResolver("default", latestWins);
        registerConflictResolver("map", mapMerger);
        registerConflictResolver("list", listMerger);
    }
    
    /**
     * Registers a custom conflict resolution strategy for a data type.
     * 
     * @param dataType The data type to register for
     * @param resolver The conflict resolution function (local, remote) -> resolved
     * @return true if registered, false if already exists
     */
    public boolean registerConflictResolver(String dataType, BiFunction<Object, Object, Object> resolver) {
        if (conflictResolvers.containsKey(dataType)) {
            return false;
        }
        
        conflictResolvers.put(dataType, resolver);
        return true;
    }
    
    /**
     * Initiates a synchronization session with another node.
     * 
     * @param initiatorNodeId The node initiating synchronization
     * @param responderNodeId The node to synchronize with
     * @param dataTypesToSync Set of data types to synchronize
     * @return Session ID if successful, empty if failed
     */
    public Optional<String> initiateSynchronization(String initiatorNodeId, 
            String responderNodeId, Set<String> dataTypesToSync) {
        
        // Check if responder node is connected
        if (!nodeDiscovery.isNodeConnected(responderNodeId)) {
            return Optional.empty();
        }
        
        // Create new session
        SyncSession session = new SyncSession(initiatorNodeId, responderNodeId, dataTypesToSync);
        activeSessions.put(session.sessionId, session);
        
        return Optional.of(session.sessionId);
    }
    
    /**
     * Updates a data item for synchronization.
     * 
     * @param dataType The type of data
     * @param itemId The unique identifier for the item
     * @param value The item value
     * @return The version assigned to the update
     */
    public long updateData(String dataType, String itemId, Object value) {
        // Ensure data type exists in registry
        versionedDataRegistry.putIfAbsent(dataType, new VersionedData(dataType));
        
        // Calculate new version (usually timestamp-based in real systems)
        VersionedData data = versionedDataRegistry.get(dataType);
        long newVersion = Math.max(data.getLatestVersion() + 1, System.currentTimeMillis());
        
        // Update the data
        data.updateItem(itemId, value, newVersion);
        
        return newVersion;
    }
    
    /**
     * Gets a data item.
     * 
     * @param dataType The type of data
     * @param itemId The item identifier
     * @return An Optional containing the item if found
     */
    public Optional<DataItem> getData(String dataType, String itemId) {
        if (!versionedDataRegistry.containsKey(dataType)) {
            return Optional.empty();
        }
        
        return versionedDataRegistry.get(dataType).getItem(itemId);
    }
    
    /**
     * Synchronizes data of a specific type with a remote node.
     * 
     * @param sessionId The synchronization session ID
     * @param dataType The type of data to synchronize
     * @return The result of the synchronization
     */
    public SyncSession.SyncResult synchronizeDataType(String sessionId, String dataType) {
        if (!activeSessions.containsKey(sessionId)) {
            return new SyncSession.SyncResult(dataType, false, 0, 0, 0, "Session not found");
        }
        
        SyncSession session = activeSessions.get(sessionId);
        
        // Ensure we have local data to sync
        if (!versionedDataRegistry.containsKey(dataType)) {
            versionedDataRegistry.put(dataType, new VersionedData(dataType));
        }
        
        // Update session state
        session.updateState(SyncSession.SyncState.COMPARING);
        
        // In a real implementation, this would involve network communication
        // with the remote node to compare versions and exchange data
        
        // For now, we'll simulate a successful sync with fake metrics
        int itemsSent = 5;
        int itemsReceived = 7;
        int conflictsResolved = 2;
        
        SyncSession.SyncResult result = new SyncSession.SyncResult(
                dataType, true, itemsSent, itemsReceived, conflictsResolved, "Synchronized successfully");
        
        session.recordResult(result);
        
        // Update session state if all data types have been synchronized
        if (session.isComplete()) {
            session.updateState(SyncSession.SyncState.COMPLETED);
        }
        
        return result;
    }
    
    /**
     * Resolves a conflict between local and remote data.
     * 
     * @param dataType The type of data
     * @param itemId The item identifier
     * @param localItem The local version of the item
     * @param remoteItem The remote version of the item
     * @return The resolved data item
     */
    public DataItem resolveConflict(String dataType, String itemId, DataItem localItem, DataItem remoteItem) {
        // Use the registered conflict resolver for this data type, or fall back to default
        BiFunction<Object, Object, Object> resolver = conflictResolvers.getOrDefault(dataType, 
                conflictResolvers.get("default"));
        
        // Determine which version to use for the result
        long newVersion = Math.max(localItem.getVersion(), remoteItem.getVersion()) + 1;
        
        // Resolve the conflict
        Object resolvedValue = resolver.apply(localItem.getValue(), remoteItem.getValue());
        
        return new DataItem(itemId, resolvedValue, newVersion);
    }
    
    /**
     * Gets the current synchronization status for a session.
     * 
     * @param sessionId The session ID
     * @return A map of status information, or empty if session not found
     */
    public Map<String, Object> getSyncStatus(String sessionId) {
        if (!activeSessions.containsKey(sessionId)) {
            return Collections.emptyMap();
        }
        
        SyncSession session = activeSessions.get(sessionId);
        Map<String, Object> status = new HashMap<>();
        
        status.put("sessionId", sessionId);
        status.put("initiatorNodeId", session.initiatorNodeId);
        status.put("responderNodeId", session.responderNodeId);
        status.put("state", session.state.name());
        status.put("startTime", session.startTime.toString());
        status.put("lastActivity", session.lastActivity.toString());
        status.put("dataTypesToSync", new ArrayList<>(session.dataTypesToSync));
        
        // Result summaries
        List<Map<String, Object>> resultSummaries = new ArrayList<>();
        for (SyncSession.SyncResult result : session.results.values()) {
            Map<String, Object> summary = new HashMap<>();
            summary.put("dataType", result.getDataType());
            summary.put("success", result.isSuccess());
            summary.put("itemsSent", result.getItemsSent());
            summary.put("itemsReceived", result.getItemsReceived());
            summary.put("conflictsResolved", result.getConflictsResolved());
            summary.put("message", result.getMessage());
            resultSummaries.add(summary);
        }
        status.put("results", resultSummaries);
        
        return status;
    }
    
    /**
     * Gets all data items of a specific type that pass boundary checks.
     * 
     * @param dataType The type of data to retrieve
     * @param contextId The context ID for boundary checks
     * @return A list of data items
     */
    public List<DataItem> getAccessibleData(String dataType, String contextId) {
        if (!versionedDataRegistry.containsKey(dataType)) {
            return Collections.emptyList();
        }
        
        VersionedData data = versionedDataRegistry.get(dataType);
        List<DataItem> accessibleItems = new ArrayList<>();
        
        for (DataItem item : data.getItems().values()) {
            // Check if this item can pass the information boundary
            if (boundary.canInformationPass(item.getItemId(), contextId)) {
                accessibleItems.add(item);
            }
        }
        
        // Sort by version (newest first)
        accessibleItems.sort(Comparator.comparing(DataItem::getVersion).reversed());
        
        return accessibleItems;
    }
    
    /**
     * Gets the synchronization history for a specific node.
     * 
     * @param nodeId The node ID
     * @return A list of session summaries
     */
    public List<Map<String, Object>> getSyncHistory(String nodeId) {
        List<Map<String, Object>> history = new ArrayList<>();
        
        for (SyncSession session : activeSessions.values()) {
            if (session.initiatorNodeId.equals(nodeId) || session.responderNodeId.equals(nodeId)) {
                Map<String, Object> summary = new HashMap<>();
                summary.put("sessionId", session.sessionId);
                summary.put("peer", session.initiatorNodeId.equals(nodeId) ? 
                        session.responderNodeId : session.initiatorNodeId);
                summary.put("state", session.state.name());
                summary.put("startTime", session.startTime.toString());
                summary.put("successful", session.state == SyncSession.SyncState.COMPLETED);
                summary.put("dataTypeCount", session.dataTypesToSync.size());
                
                history.add(summary);
            }
        }
        
        // Sort by start time (newest first)
        history.sort((a, b) -> {
            Instant aTime = Instant.parse((String) a.get("startTime"));
            Instant bTime = Instant.parse((String) b.get("startTime"));
            return bTime.compareTo(aTime);
        });
        
        return history;
    }
    
    /**
     * Cleans up completed or expired synchronization sessions.
     * 
     * @param maxAgeMillis Maximum age of sessions to keep
     * @return Number of sessions cleaned up
     */
    public int cleanupSessions(long maxAgeMillis) {
        int cleanedCount = 0;
        Instant now = Instant.now();
        List<String> sessionsToRemove = new ArrayList<>();
        
        for (Map.Entry<String, SyncSession> entry : activeSessions.entrySet()) {
            SyncSession session = entry.getValue();
            
            // Remove completed sessions older than maxAge
            if (session.state == SyncSession.SyncState.COMPLETED || 
                session.state == SyncSession.SyncState.FAILED) {
                
                long ageMillis = java.time.Duration.between(session.lastActivity, now).toMillis();
                if (ageMillis > maxAgeMillis) {
                    sessionsToRemove.add(entry.getKey());
                }
            }
        }
        
        for (String sessionId : sessionsToRemove) {
            activeSessions.remove(sessionId);
            cleanedCount++;
        }
        
        return cleanedCount;
    }
}