package com.fractal.browser.collective.memory;

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
import java.util.Collections;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.fractal.browser.collective.boundaries.InformationBoundary;

/**
 * SharedContextRegistry implements a registry of shared contexts within the collective.
 * It enables nodes to create, join, and interact within various shared cognitive contexts,
 * maintaining relationships between contexts at different scales.
 * 
 * This class applies fractal patterns to context management, allowing for nested contexts
 * and multi-scale context relationships.
 */
public class SharedContextRegistry {
    
    // Maps context IDs to their data
    private final Map<String, Context> contextRegistry;
    
    // Maps node IDs to the contexts they participate in
    private final Map<String, Set<String>> nodeContexts;
    
    // Maps parent context IDs to child context IDs
    private final Map<String, Set<String>> contextHierarchy;
    
    // Maps context names to context IDs
    private final Map<String, String> contextNameIndex;
    
    // Information boundary for access control
    private final InformationBoundary boundary;
    
    // Persistence strategy for durable storage
    private final PersistenceStrategy persistenceStrategy;
    
    // Lock for context operations
    private final ReadWriteLock contextLock;
    
    /**
     * Represents a shared context within the collective.
     */
    public static class Context {
        private final String contextId;
        private final String name;
        private final String description;
        private final String creatorNodeId;
        private final Instant creationTime;
        private final Instant lastActivity;
        private final Map<String, Object> metadata;
        private final Set<String> participantNodeIds;
        private final String parentContextId; // null for root contexts
        private boolean active;
        
        /**
         * Creates a new context.
         */
        public Context(String name, String description, String creatorNodeId, 
                Map<String, Object> metadata, String parentContextId) {
            this.contextId = UUID.randomUUID().toString();
            this.name = name;
            this.description = description;
            this.creatorNodeId = creatorNodeId;
            this.creationTime = Instant.now();
            this.lastActivity = this.creationTime;
            this.metadata = new HashMap<>(metadata);
            this.participantNodeIds = Collections.synchronizedSet(new HashSet<>());
            this.participantNodeIds.add(creatorNodeId); // Creator is first participant
            this.parentContextId = parentContextId;
            this.active = true;
        }
        
        /**
         * Creates a context with an existing ID (for loading from persistence).
         */
        public Context(String contextId, String name, String description, String creatorNodeId,
                Instant creationTime, Instant lastActivity, Map<String, Object> metadata,
                Set<String> participantNodeIds, String parentContextId, boolean active) {
            this.contextId = contextId;
            this.name = name;
            this.description = description;
            this.creatorNodeId = creatorNodeId;
            this.creationTime = creationTime;
            this.lastActivity = lastActivity;
            this.metadata = new HashMap<>(metadata);
            this.participantNodeIds = Collections.synchronizedSet(new HashSet<>(participantNodeIds));
            this.parentContextId = parentContextId;
            this.active = active;
        }
        
        // Getters
        public String getContextId() { return contextId; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public String getCreatorNodeId() { return creatorNodeId; }
        public Instant getCreationTime() { return creationTime; }
        public Instant getLastActivity() { return lastActivity; }
        public Map<String, Object> getMetadata() { return Collections.unmodifiableMap(metadata); }
        public Set<String> getParticipantNodeIds() { return Collections.unmodifiableSet(participantNodeIds); }
        public String getParentContextId() { return parentContextId; }
        public boolean isActive() { return active; }
        
        /**
         * Adds a participant to this context.
         * 
         * @param nodeId The node ID to add
         * @return true if added, false if already a participant
         */
        public boolean addParticipant(String nodeId) {
            return participantNodeIds.add(nodeId);
        }
        
        /**
         * Removes a participant from this context.
         * 
         * @param nodeId The node ID to remove
         * @return true if removed, false if not a participant
         */
        public boolean removeParticipant(String nodeId) {
            // Can't remove creator
            if (nodeId.equals(creatorNodeId)) {
                return false;
            }
            
            return participantNodeIds.remove(nodeId);
        }
        
        /**
         * Deactivates this context.
         */
        public void deactivate() {
            this.active = false;
        }
        
        /**
         * Checks if a node is a participant in this context.
         * 
         * @param nodeId The node ID to check
         * @return true if participant, false otherwise
         */
        public boolean isParticipant(String nodeId) {
            return participantNodeIds.contains(nodeId);
        }
    }
    
    /**
     * Creates a new SharedContextRegistry with the specified components.
     */
    public SharedContextRegistry(InformationBoundary boundary, PersistenceStrategy persistenceStrategy) {
        this.contextRegistry = new ConcurrentHashMap<>();
        this.nodeContexts = new ConcurrentHashMap<>();
        this.contextHierarchy = new ConcurrentHashMap<>();
        this.contextNameIndex = new ConcurrentHashMap<>();
        this.boundary = boundary;
        this.persistenceStrategy = persistenceStrategy;
        this.contextLock = new ReentrantReadWriteLock();
        
        // Load existing contexts from persistence if available
        loadFromPersistence();
    }
    
    /**
     * Loads existing contexts from the persistence layer.
     */
    private void loadFromPersistence() {
        if (persistenceStrategy != null) {
            try {
                List<Map<String, Object>> persistedContexts = persistenceStrategy.loadAll("contexts");
                
                // Process each persisted context
                for (Map<String, Object> data : persistedContexts) {
                    // In a real implementation, this would convert the data to a Context
                    // and load it into the registry, updating all indexes
                    
                    // For now, just log that we would load contexts
                    System.out.println("Would load context: " + data.get("contextId"));
                }
            } catch (Exception e) {
                System.err.println("Error loading contexts from persistence: " + e.getMessage());
            }
        }
    }
    
    /**
     * Creates a new shared context.
     * 
     * @param name The context name
     * @param description The context description
     * @param creatorNodeId The creator node ID
     * @param metadata Additional metadata
     * @param parentContextId Parent context ID, or null for root contexts
     * @return The created context if successful, empty if error
     */
    public Optional<Context> createContext(String name, String description, String creatorNodeId,
            Map<String, Object> metadata, String parentContextId) {
        
        contextLock.writeLock().lock();
        try {
            // Check if name is already used
            if (contextNameIndex.containsKey(name)) {
                return Optional.empty();
            }
            
            // Check if parent context exists (if specified)
            if (parentContextId != null && !contextRegistry.containsKey(parentContextId)) {
                return Optional.empty();
            }
            
            // Create the context
            Context context = new Context(name, description, creatorNodeId, metadata, parentContextId);
            
            // Store in registry
            contextRegistry.put(context.getContextId(), context);
            
            // Index by name
            contextNameIndex.put(name, context.getContextId());
            
            // Update node contexts
            nodeContexts.putIfAbsent(creatorNodeId, Collections.synchronizedSet(new HashSet<>()));
            nodeContexts.get(creatorNodeId).add(context.getContextId());
            
            // Update hierarchy if parent specified
            if (parentContextId != null) {
                contextHierarchy.putIfAbsent(parentContextId, Collections.synchronizedSet(new HashSet<>()));
                contextHierarchy.get(parentContextId).add(context.getContextId());
            }
            
            // Persist the context
            if (persistenceStrategy != null) {
                try {
                    Map<String, Object> persistData = new HashMap<>();
                    persistData.put("contextId", context.getContextId());
                    persistData.put("name", context.getName());
                    persistData.put("description", context.getDescription());
                    persistData.put("creatorNodeId", context.getCreatorNodeId());
                    persistData.put("creationTime", context.getCreationTime().toString());
                    persistData.put("lastActivity", context.getLastActivity().toString());
                    persistData.put("metadata", context.getMetadata());
                    persistData.put("participantNodeIds", new ArrayList<>(context.getParticipantNodeIds()));
                    persistData.put("parentContextId", context.getParentContextId());
                    persistData.put("active", context.isActive());
                    
                    persistenceStrategy.store("contexts", context.getContextId(), persistData);
                } catch (Exception e) {
                    System.err.println("Error persisting context: " + e.getMessage());
                }
            }
            
            return Optional.of(context);
        } finally {
            contextLock.writeLock().unlock();
        }
    }
    
    /**
     * Gets a context by ID.
     * 
     * @param contextId The context ID
     * @return An Optional containing the context if found
     */
    public Optional<Context> getContext(String contextId) {
        contextLock.readLock().lock();
        try {
            return Optional.ofNullable(contextRegistry.get(contextId));
        } finally {
            contextLock.readLock().unlock();
        }
    }
    
    /**
     * Gets a context by name.
     * 
     * @param name The context name
     * @return An Optional containing the context if found
     */
    public Optional<Context> getContextByName(String name) {
        contextLock.readLock().lock();
        try {
            String contextId = contextNameIndex.get(name);
            if (contextId == null) {
                return Optional.empty();
            }
            
            return Optional.ofNullable(contextRegistry.get(contextId));
        } finally {
            contextLock.readLock().unlock();
        }
    }
    
    /**
     * Joins a node to a context.
     * 
     * @param nodeId The node ID
     * @param contextId The context ID
     * @return true if joined, false otherwise
     */
    public boolean joinContext(String nodeId, String contextId) {
        contextLock.writeLock().lock();
        try {
            // Check if context exists and is active
            Context context = contextRegistry.get(contextId);
            if (context == null || !context.isActive()) {
                return false;
            }
            
            // Add node to context
            boolean added = context.addParticipant(nodeId);
            if (!added) {
                return false; // Already a participant
            }
            
            // Update node contexts
            nodeContexts.putIfAbsent(nodeId, Collections.synchronizedSet(new HashSet<>()));
            nodeContexts.get(nodeId).add(contextId);
            
            // Update persistence
            if (persistenceStrategy != null) {
                try {
                    Map<String, Object> persistData = new HashMap<>();
                    persistData.put("participantNodeIds", new ArrayList<>(context.getParticipantNodeIds()));
                    
                    persistenceStrategy.store("contexts", contextId, persistData);
                } catch (Exception e) {
                    System.err.println("Error updating context participants: " + e.getMessage());
                }
            }
            
            return true;
        } finally {
            contextLock.writeLock().unlock();
        }
    }
    
    /**
     * Leaves a context.
     * 
     * @param nodeId The node ID
     * @param contextId The context ID
     * @return true if left, false otherwise
     */
    public boolean leaveContext(String nodeId, String contextId) {
        contextLock.writeLock().lock();
        try {
            // Check if context exists
            Context context = contextRegistry.get(contextId);
            if (context == null) {
                return false;
            }
            
            // Remove node from context
            boolean removed = context.removeParticipant(nodeId);
            if (!removed) {
                return false; // Not a participant or is creator
            }
            
            // Update node contexts
            if (nodeContexts.containsKey(nodeId)) {
                nodeContexts.get(nodeId).remove(contextId);
            }
            
            // Update persistence
            if (persistenceStrategy != null) {
                try {
                    Map<String, Object> persistData = new HashMap<>();
                    persistData.put("participantNodeIds", new ArrayList<>(context.getParticipantNodeIds()));
                    
                    persistenceStrategy.store("contexts", contextId, persistData);
                } catch (Exception e) {
                    System.err.println("Error updating context participants: " + e.getMessage());
                }
            }
            
            return true;
        } finally {
            contextLock.writeLock().unlock();
        }
    }
    
    /**
     * Gets all contexts a node participates in.
     * 
     * @param nodeId The node ID
     * @return A set of context IDs
     */
    public Set<String> getNodeContexts(String nodeId) {
        contextLock.readLock().lock();
        try {
            if (!nodeContexts.containsKey(nodeId)) {
                return Collections.emptySet();
            }
            
            return new HashSet<>(nodeContexts.get(nodeId));
        } finally {
            contextLock.readLock().unlock();
        }
    }
    
    /**
     * Gets all child contexts of a parent context.
     * 
     * @param parentContextId The parent context ID
     * @return A set of child context IDs
     */
    public Set<String> getChildContexts(String parentContextId) {
        contextLock.readLock().lock();
        try {
            if (!contextHierarchy.containsKey(parentContextId)) {
                return Collections.emptySet();
            }
            
            return new HashSet<>(contextHierarchy.get(parentContextId));
        } finally {
            contextLock.readLock().unlock();
        }
    }
    
    /**
     * Gets the full context hierarchy below a parent context.
     * 
     * @param parentContextId The parent context ID
     * @return A map of context IDs to their levels in the hierarchy
     */
    public Map<String, Integer> getContextHierarchy(String parentContextId) {
        contextLock.readLock().lock();
        try {
            Map<String, Integer> hierarchy = new HashMap<>();
            
            // Add parent context at level 0
            hierarchy.put(parentContextId, 0);
            
            // Recursively add child contexts
            addChildContextsToHierarchy(parentContextId, 1, hierarchy);
            
            return hierarchy;
        } finally {
            contextLock.readLock().unlock();
        }
    }
    
    /**
     * Recursively adds child contexts to the hierarchy map.
     */
    private void addChildContextsToHierarchy(String parentContextId, int level, Map<String, Integer> hierarchy) {
        Set<String> childContexts = contextHierarchy.getOrDefault(parentContextId, Collections.emptySet());
        
        for (String childId : childContexts) {
            hierarchy.put(childId, level);
            addChildContextsToHierarchy(childId, level + 1, hierarchy);
        }
    }
    
    /**
     * Deactivates a context.
     * 
     * @param contextId The context ID
     * @param nodeId The node ID making the request (must be creator)
     * @return true if deactivated, false otherwise
     */
    public boolean deactivateContext(String contextId, String nodeId) {
        contextLock.writeLock().lock();
        try {
            // Check if context exists
            Context context = contextRegistry.get(contextId);
            if (context == null) {
                return false;
            }
            
            // Check if node is context creator
            if (!context.getCreatorNodeId().equals(nodeId)) {
                return false; // Only creator can deactivate
            }
            
            // Deactivate context
            context.deactivate();
            
            // Update persistence
            if (persistenceStrategy != null) {
                try {
                    Map<String, Object> persistData = new HashMap<>();
                    persistData.put("active", false);
                    
                    persistenceStrategy.store("contexts", contextId, persistData);
                } catch (Exception e) {
                    System.err.println("Error updating context status: " + e.getMessage());
                }
            }
            
            return true;
        } finally {
            contextLock.writeLock().unlock();
        }
    }
    
    /**
     * Checks if a node can access a context.
     * 
     * @param nodeId The node ID
     * @param contextId The context ID
     * @return true if node can access the context, false otherwise
     */
    public boolean canAccessContext(String nodeId, String contextId) {
        contextLock.readLock().lock();
        try {
            // Check if context exists and is active
            Context context = contextRegistry.get(contextId);
            if (context == null || !context.isActive()) {
                return false;
            }
            
            // Check if node is a participant
            if (context.isParticipant(nodeId)) {
                return true;
            }
            
            // Check information boundary
            return boundary.canInformationPass(contextId, nodeId);
        } finally {
            contextLock.readLock().unlock();
        }
    }
    
    /**
     * Gets all active root contexts.
     * 
     * @return A list of root contexts
     */
    public List<Context> getRootContexts() {
        contextLock.readLock().lock();
        try {
            List<Context> rootContexts = new ArrayList<>();
            
            for (Context context : contextRegistry.values()) {
                if (context.isActive() && context.getParentContextId() == null) {
                    rootContexts.add(context);
                }
            }
            
            return rootContexts;
        } finally {
            contextLock.readLock().unlock();
        }
    }
}