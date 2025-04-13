package com.fractal.browser.collective.memory;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fractal.browser.collective.boundaries.InformationBoundary;

/**
 * TemporalIndexing implements temporal storage and retrieval patterns for the collective
 * memory. It tracks when information was created, accessed, and modified, enabling
 * time-based queries and temporal pattern recognition.
 * 
 * This class applies fractal temporal patterns, allowing for time-based queries at
 * multiple scales, from microseconds to years.
 */
public class TemporalIndexing {
    
    // Maps timestamps to item IDs
    private final NavigableMap<Instant, Set<String>> timeIndex;
    
    // Maps date strings (YYYY-MM-DD) to item IDs
    private final Map<String, Set<String>> dateIndex;
    
    // Maps item IDs to their timestamps
    private final Map<String, Instant> itemTimes;
    
    // Maps item IDs to their metadata
    private final Map<String, Map<String, Object>> itemMetadata;
    
    // Information boundary for access control
    private final InformationBoundary boundary;
    
    private final ZoneId timezone;  // Add timezone field
    
    /**
     * Creates a new TemporalIndexing instance.
     */
    public TemporalIndexing() {
        this.timeIndex = Collections.synchronizedNavigableMap(new TreeMap<>());
        this.dateIndex = new ConcurrentHashMap<>();
        this.itemTimes = new ConcurrentHashMap<>();
        this.itemMetadata = new ConcurrentHashMap<>();
        this.timezone = ZoneId.of("UTC");  // Default to UTC, can be configured
        this.boundary = null;
    }
    
    public TemporalIndexing(ZoneId timezone) {
        this.timeIndex = Collections.synchronizedNavigableMap(new TreeMap<>());
        this.dateIndex = new ConcurrentHashMap<>();
        this.itemTimes = new ConcurrentHashMap<>();
        this.itemMetadata = new ConcurrentHashMap<>();
        this.timezone = timezone;
        this.boundary = null;
    }
    
    /**
     * Indexes an item by its timestamp.
     * 
     * @param itemId The item ID
     * @param timestamp The timestamp
     * @param metadata Additional metadata about the item
     * @return true if indexed, false if already indexed
     */
    public boolean indexItem(String itemId, Instant timestamp, Map<String, Object> metadata) {
        // Use putIfAbsent to ensure atomic check-and-set operation
        if (itemTimes.putIfAbsent(itemId, timestamp) != null) {
            return false; // Already indexed
        }
        
        // Add to time index
        timeIndex.computeIfAbsent(timestamp, k -> Collections.synchronizedSet(new HashSet<>()))
                .add(itemId);
        
        // Add to date index
        LocalDate date = LocalDate.ofInstant(timestamp, timezone);
        String dateString = date.toString(); // YYYY-MM-DD format
        dateIndex.computeIfAbsent(dateString, k -> Collections.synchronizedSet(new HashSet<>()))
                .add(itemId);
        
        // Store metadata
        itemMetadata.put(itemId, new HashMap<>(metadata));
        
        return true;
    }
    
    /**
     * Updates an existing item's metadata.
     * 
     * @param itemId The item ID
     * @param metadata The new metadata
     * @return true if updated, false if item not found
     */
    public boolean updateItemMetadata(String itemId, Map<String, Object> metadata) {
        if (!itemTimes.containsKey(itemId)) {
            return false; // Item not found
        }
        
        // Update metadata
        itemMetadata.put(itemId, new HashMap<>(metadata));
        
        return true;
    }
    
    /**
     * Removes an item from the temporal indexes.
     * 
     * @param itemId The item ID
     * @return true if removed, false if not found
     */
    public boolean removeItem(String itemId) {
        Instant timestamp = itemTimes.remove(itemId);
        if (timestamp == null) {
            return false; // Not found
        }
        
        // Remove from time index
        Set<String> timeEntries = timeIndex.get(timestamp);
        if (timeEntries != null) {
            synchronized (timeEntries) {
                timeEntries.remove(itemId);
                if (timeEntries.isEmpty()) {
                    timeIndex.remove(timestamp);
                }
            }
        }
        
        // Remove from date index
        LocalDate date = LocalDate.ofInstant(timestamp, timezone);
        String dateString = date.toString();
        Set<String> dateEntries = dateIndex.get(dateString);
        if (dateEntries != null) {
            synchronized (dateEntries) {
                dateEntries.remove(itemId);
                if (dateEntries.isEmpty()) {
                    dateIndex.remove(dateString);
                }
            }
        }
        
        // Remove metadata
        itemMetadata.remove(itemId);
        
        return true;
    }
    
    /**
     * Gets an item's timestamp.
     * 
     * @param itemId The item ID
     * @return An Optional containing the timestamp if found
     */
    public Optional<Instant> getItemTimestamp(String itemId) {
        return Optional.ofNullable(itemTimes.get(itemId));
    }
    
    /**
     * Gets an item's metadata.
     * 
     * @param itemId The item ID
     * @return An Optional containing the metadata if found
     */
    public Optional<Map<String, Object>> getItemMetadata(String itemId) {
        Map<String, Object> metadata = itemMetadata.get(itemId);
        if (metadata == null) {
            return Optional.empty();
        }
        
        return Optional.of(new HashMap<>(metadata));
    }
    
    /**
     * Finds items created within a time range.
     * 
     * @param start The start time (inclusive)
     * @param end The end time (inclusive)
     * @param contextId The context ID for boundary checks
     * @return A list of item IDs in the time range
     */
    public List<String> findItemsInTimeRange(Instant start, Instant end, String contextId) {
        // Get the submap for the time range
        NavigableMap<Instant, Set<String>> rangeMap = timeIndex.subMap(start, true, end, true);
        
        List<String> result = new ArrayList<>();
        
        // Collect all item IDs in the range that pass boundary checks
        for (Set<String> items : rangeMap.values()) {
            synchronized (items) {
                for (String itemId : items) {
                    if (boundary.canInformationPass(itemId, contextId)) {
                        result.add(itemId);
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * Finds items created on a specific date.
     * 
     * @param date The date in YYYY-MM-DD format
     * @param contextId The context ID for boundary checks
     * @return A list of item IDs on the date
     */
    public List<String> findItemsOnDate(String date, String contextId) {
        Set<String> dateItems = dateIndex.getOrDefault(date, Collections.emptySet());
        
        synchronized (dateItems) {
            return dateItems.stream()
                    .filter(itemId -> boundary.canInformationPass(itemId, contextId))
                    .collect(Collectors.toList());
        }
    }
    
    /**
     * Groups items by time periods.
     * 
     * @param unit The time unit to group by (e.g., ChronoUnit.DAYS, ChronoUnit.HOURS)
     * @param contextId The context ID for boundary checks
     * @return A map of period start times to item IDs
     */
    public Map<Instant, List<String>> groupItemsByTimePeriod(ChronoUnit unit, String contextId) {
        Map<Instant, List<String>> result = new HashMap<>();
        
        // Group all items by the specified time unit
        for (Map.Entry<String, Instant> entry : itemTimes.entrySet()) {
            String itemId = entry.getKey();
            Instant timestamp = entry.getValue();
            
            // Check if item can pass boundary
            if (!boundary.canInformationPass(itemId, contextId)) {
                continue;
            }
            
            // Truncate timestamp to the specified unit
            Instant periodStart = truncateToUnit(timestamp, unit);
            
            // Add to result with synchronization on the list
            result.computeIfAbsent(periodStart, k -> Collections.synchronizedList(new ArrayList<>())).add(itemId);
        }
        
        return result;
    }
    
    /**
     * Truncates an Instant to the specified ChronoUnit.
     */
    private Instant truncateToUnit(Instant instant, ChronoUnit unit) {
        LocalDate date = LocalDate.ofInstant(instant, timezone);
        
        switch (unit) {
            case YEARS:
                return LocalDate.of(date.getYear(), 1, 1)
                        .atStartOfDay(timezone).toInstant();
            case MONTHS:
                return LocalDate.of(date.getYear(), date.getMonthValue(), 1)
                        .atStartOfDay(timezone).toInstant();
            case WEEKS:
                return date.minusDays(date.getDayOfWeek().getValue() - 1)
                        .atStartOfDay(timezone).toInstant();
            case DAYS:
                return date.atStartOfDay(timezone).toInstant();
            case HOURS:
                return instant.truncatedTo(ChronoUnit.HOURS);
            case MINUTES:
                return instant.truncatedTo(ChronoUnit.MINUTES);
            case SECONDS:
                return instant.truncatedTo(ChronoUnit.SECONDS);
            default:
                return instant;
        }
    }
    
    /**
     * Gets the most recent items.
     * 
     * @param limit Maximum number of items to return
     * @param contextId The context ID for boundary checks
     * @return A list of the most recent item IDs
     */
    public List<String> getMostRecentItems(int limit, String contextId) {
        List<Map.Entry<String, Instant>> sortedItems = new ArrayList<>();
        
        // Filter items by boundary check
        for (Map.Entry<String, Instant> entry : itemTimes.entrySet()) {
            String itemId = entry.getKey();
            if (boundary.canInformationPass(itemId, contextId)) {
                sortedItems.add(entry);
            }
        }
        
        // Sort by timestamp (most recent first)
        sortedItems.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        
        // Take the top N items
        return sortedItems.stream()
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
    
    /**
     * Finds temporal patterns in the data.
     * 
     * @param windowSize The sliding window size in milliseconds
     * @param threshold The minimum number of items in a window to consider it a pattern
     * @param contextId The context ID for boundary checks
     * @return A list of time windows with item counts above the threshold
     */
    public List<Map<String, Object>> findTemporalPatterns(long windowSize, int threshold, String contextId) {
        List<Map<String, Object>> patterns = new ArrayList<>();
        
        // Create a timeline of all timestamps with their items
        List<Instant> timeline = new ArrayList<>(timeIndex.keySet());
        Collections.sort(timeline);
        
        // Use a sliding window to detect patterns
        for (int i = 0; i < timeline.size(); i++) {
            Instant windowStart = timeline.get(i);
            Instant windowEnd = windowStart.plusMillis(windowSize);
            
            // Count items in the window that pass boundary checks
            int itemCount = 0;
            Set<String> itemsInWindow = new HashSet<>();
            
            for (int j = i; j < timeline.size() && !timeline.get(j).isAfter(windowEnd); j++) {
                Instant time = timeline.get(j);
                Set<String> items = timeIndex.get(time);
                
                synchronized (items) {
                    for (String itemId : items) {
                        if (boundary.canInformationPass(itemId, contextId)) {
                            itemCount++;
                            itemsInWindow.add(itemId);
                        }
                    }
                }
            }
            
            // If count exceeds threshold, record the pattern
            if (itemCount >= threshold) {
                Map<String, Object> pattern = new HashMap<>();
                pattern.put("startTime", windowStart);
                pattern.put("endTime", windowEnd);
                pattern.put("itemCount", itemCount);
                pattern.put("items", new ArrayList<>(itemsInWindow));
                
                patterns.add(pattern);
            }
        }
        
        return patterns;
    }
    
    /**
     * Finds gaps in the temporal data.
     * 
     * @param minGapMillis The minimum gap size in milliseconds
     * @param contextId The context ID for boundary checks
     * @return A list of gaps (start and end times)
     */
    public List<Map<String, Instant>> findTemporalGaps(long minGapMillis, String contextId) {
        List<Map<String, Instant>> gaps = new ArrayList<>();
        
        // Get all timestamps for items that pass boundary checks
        List<Instant> timeline = new ArrayList<>();
        for (Map.Entry<Instant, Set<String>> entry : timeIndex.entrySet()) {
            Instant time = entry.getKey();
            Set<String> items = entry.getValue();
            
            boolean anyVisible = false;
            synchronized (items) {
                for (String itemId : items) {
                    if (boundary.canInformationPass(itemId, contextId)) {
                        anyVisible = true;
                        break;
                    }
                }
            }
            
            if (anyVisible) {
                timeline.add(time);
            }
        }
        
        // Sort the timeline
        Collections.sort(timeline);
        
        // Find gaps
        for (int i = 0; i < timeline.size() - 1; i++) {
            Instant current = timeline.get(i);
            Instant next = timeline.get(i + 1);
            
            long gapMillis = current.until(next, ChronoUnit.MILLIS);
            
            if (gapMillis >= minGapMillis) {
                Map<String, Instant> gap = new HashMap<>();
                gap.put("start", current);
                gap.put("end", next);
                
                gaps.add(gap);
            }
        }
        
        return gaps;
    }
    
    /**
     * Gets time-based statistics.
     * 
     * @param unit The time unit for aggregation
     * @param contextId The context ID for boundary checks
     * @return Statistics by time period
     */
    public Map<Instant, Map<String, Object>> getTimeStatistics(ChronoUnit unit, String contextId) {
        Map<Instant, Map<String, Object>> statistics = new HashMap<>();
        
        // Group items by time period
        Map<Instant, List<String>> groupedItems = groupItemsByTimePeriod(unit, contextId);
        
        // Calculate statistics for each group
        for (Map.Entry<Instant, List<String>> entry : groupedItems.entrySet()) {
            Instant periodStart = entry.getKey();
            List<String> itemIds = entry.getValue();
            
            Map<String, Object> periodStats = new HashMap<>();
            periodStats.put("itemCount", itemIds.size());
            
            // Calculate average metadata values if numeric
            Map<String, Double> metadataSums = new HashMap<>();
            Map<String, Integer> metadataCounts = new HashMap<>();
            
            synchronized (itemIds) {
                for (String itemId : itemIds) {
                    Map<String, Object> metadata = itemMetadata.get(itemId);
                    if (metadata != null) {
                        for (Map.Entry<String, Object> metaEntry : metadata.entrySet()) {
                            String key = metaEntry.getKey();
                            Object value = metaEntry.getValue();
                            
                            if (value instanceof Number) {
                                double numValue = ((Number) value).doubleValue();
                                metadataSums.put(key, metadataSums.getOrDefault(key, 0.0) + numValue);
                                metadataCounts.put(key, metadataCounts.getOrDefault(key, 0) + 1);
                            }
                        }
                    }
                }
            }
            
            // Calculate averages
            Map<String, Double> metadataAverages = new HashMap<>();
            for (String key : metadataSums.keySet()) {
                double sum = metadataSums.get(key);
                int count = metadataCounts.get(key);
                metadataAverages.put("avg_" + key, sum / count);
            }
            
            periodStats.putAll(metadataAverages);
            statistics.put(periodStart, periodStats);
        }
        
        return statistics;
    }
}