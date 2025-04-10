```java
package com.fractaljournal;
import com.fractaljournal.analysis.AnalysisEngine;
import com.fractaljournal.analysis.ComponentAnalyzer;
import com.fractaljournal.analysis.StructureAnalyzer;
import com.fractaljournal.browser.BrowserAutomationService;
import com.fractaljournal.browser.NovaActBridge;
import com.fractaljournal.browser.SemanticInstruction;
import com.fractaljournal.cli.CommandLineInterface;
import com.fractaljournal.config.Configuration;
import com.fractaljournal.exceptions.FractalJournalException;
import com.fractaljournal.journal.JournalEntry;
import com.fractaljournal.journal.JournalManager;
import com.fractaljournal.logging.FractalLogger;
import com.fractaljournal.observation.ObservationCollector;
import com.fractaljournal.utils.FractalHashMap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Main application class that demonstrates fractal browser automation principles.
 * This class orchestrates the interaction between different components of the system.
 */
public class FractalBrowserAutomation {
    private static final FractalLogger logger = FractalLogger.getLogger(FractalBrowserAutomation.class);

    public static void main(String[] args) {
        try {
            // Parse command line arguments through the CLI interface
            CommandLineInterface cli = new CommandLineInterface(args);

            // Initialize the configuration with command line parameters
            Configuration config = cli.parseConfiguration();

            // Create the main application and run it
            FractalBrowserAutomation app = new FractalBrowserAutomation(config);
            app.run();

        } catch (FractalJournalException e) {
            logger.error("Application failed", e);
            System.err.println("Error: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Caused by: " + e.getCause().getMessage());
            }
            System.exit(1);
        }
    }

    private final Configuration config;
    private final BrowserAutomationService browserService;
    private final JournalManager journalManager;
    private final AnalysisEngine analysisEngine;
    private final ObservationCollector observationCollector;

    /**
     * Constructor using dependency injection to initialize all components.
     *
     * @param config The configuration for the application
     */
    public FractalBrowserAutomation(Configuration config) {
        this.config = config;

        // Initialize the browser automation service with the NovaAct bridge
        this.browserService = new BrowserAutomationService(
            new NovaActBridge(config.getBrowserConfig())
        );

        // Initialize the journal manager with its dependencies
        this.journalManager = new JournalManager(config.getJournalConfig());

        // Initialize the analysis engine with its component and structure analyzers
        this.analysisEngine = new AnalysisEngine(
            new ComponentAnalyzer(),
            new StructureAnalyzer()
        );

        // Initialize the observation collector
        this.observationCollector = new ObservationCollector(config.getObservationConfig());

        logger.info("FractalBrowserAutomation initialized successfully");
    }

    /**
     * Runs the application, demonstrating the fractal pattern of interaction
     * between semantic instructions and browser automation.
     */
    public void run() throws FractalJournalException {
        logger.info("Starting FractalBrowserAutomation");

        // Create a semantic instruction for the browser automation
        SemanticInstruction instruction = new SemanticInstruction.Builder()
            .withIntent("Analyze website structure and components")
            .withContext("Looking for creative design patterns")
            .withAction("Navigate and observe hierarchical elements")
            .build();

        // Execute the instruction and collect observations asynchronously
        CompletableFuture<Map<String, Object>> observationsFuture =
            CompletableFuture.supplyAsync(() -> browserService.execute(instruction))
                .thenApply(observationCollector::collectObservations);

        // Create a journal entry to record the results
        JournalEntry journalEntry = new JournalEntry.Builder()
            .withTitle("Website Structural Analysis")
            .withInstruction(instruction)
            .build();

        // Store the initial journal entry
        journalManager.storeEntry(journalEntry);

        // Process the observations when they're ready and update the journal entry
        observationsFuture.thenAccept(observations -> {
            try {
                // Analyze the observations using the analysis engine
                Map<String, Object> analysisResults = analysisEngine.analyze(observations);

                // Update the journal entry with the analysis results
                journalEntry.updateWithAnalysis(analysisResults);

                // Store the updated journal entry
                journalManager.updateEntry(journalEntry);

                // Generate recursive analysis based on the initial results
                generateRecursiveAnalysis(journalEntry, 3); // Depth of 3 for demonstration

                logger.info("Analysis completed successfully");

            } catch (Exception e) {
                logger.error("Error processing analysis", e);
            }
        }).join(); // Wait for completion in this example

        logger.info("FractalBrowserAutomation completed execution");
    }

    /**
     * Demonstrates the fractal pattern by recursively analyzing components
     * found in the initial analysis, creating a nested structure of observations
     * and analyses.
     *
     * @param parentEntry The parent journal entry
     * @param depth The maximum depth of recursion
     */
    private void generateRecursiveAnalysis(JournalEntry parentEntry, int depth) throws FractalJournalException {
        if (depth <= 0) {
            return;
        }

        // Get components identified in the parent analysis
        Optional<Map<String, Object>> components = parentEntry.getComponentsFromAnalysis();

        if (components.isPresent()) {
            // Use LinkedHashMap to maintain insertion order
            LinkedHashMap<String, JournalEntry> childEntries = new LinkedHashMap<>();

            // For each component, create a new analysis
            components.get().forEach((componentId, component) -> {
                try {
                    // Create a more specific instruction for this component
                    SemanticInstruction refinedInstruction = new SemanticInstruction.Builder()
                        .withIntent("Analyze specific component structure")
                        .withContext("Component: " + componentId)
                        .withAction("Navigate to component and analyze internal elements")
                        .build();

                    // Execute the instruction for this component
                    Map<String, Object> componentObservations = browserService.execute(refinedInstruction);
                    Map<String, Object> collectedObservations = observationCollector.collectObservations(componentObservations);

                    // Create a child journal entry for this component
                    JournalEntry childEntry = new JournalEntry.Builder()
                        .withTitle("Component Analysis: " + componentId)
                        .withInstruction(refinedInstruction)
                        .withParentEntry(parentEntry)
                        .build();

                    // Analyze the component observations
                    Map<String, Object> componentAnalysis = analysisEngine.analyzeComponent(collectedObservations);

                    // Update and store the child entry
                    childEntry.updateWithAnalysis(componentAnalysis);
                    journalManager.storeEntry(childEntry);

                    // Add to our map of child entries
                    childEntries.put(componentId, childEntry);

                    // Recurse for this component
                    generateRecursiveAnalysis(childEntry, depth - 1);

                } catch (Exception e) {
                    logger.error("Error in recursive analysis for component " + componentId, e);
                }
            });

            // Update the parent entry with links to all child analyses
            parentEntry.updateWithChildEntries(new FractalHashMap<>(childEntries));
            journalManager.updateEntry(parentEntry);
        }
    }
}
```
```java
/**
 * Core domain package definitions follow
 */

// Package: com.fractaljournal.analysis
package com.fractaljournal.analysis;

import java.util.HashMap;
import java.util.Map;

/**
 * Interface for analysis components that process observations
 * and generate insights.
 *
 * @param <T> The type of input data
 * @param <R> The type of analysis result
 */
public interface Analyzer<T, R> {
    /**
     * Analyze the input data and produce results.
     *
     * @param input The input data to analyze
     * @return The analysis results
     */
    R analyze(T input);
}

/**
 * Implementation of the Analyzer interface for web components.
 */
public class ComponentAnalyzer implements Analyzer<Map<String, Object>, Map<String, Object>> {

    @Override
    public Map<String, Object> analyze(Map<String, Object> input) {
        // Implementation of component analysis logic
        Map<String, Object> results = new HashMap<>();

        // Extract component information from the input
        input.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith("component."))
            .forEach(entry -> {
                String componentKey = entry.getKey().substring("component.".length());
                results.put(componentKey, processComponent(entry.getValue()));
            });

        return results;
    }

    @SuppressWarnings("unchecked")
    private Object processComponent(Object component) {
        if (component instanceof Map) {
            Map<String, Object> componentMap = (Map<String, Object>) component;
            Map<String, Object> processedComponent = new HashMap<>();

            // Extract attributes like type, structure, interactions
            componentMap.forEach((key, value) -> {
                if (key.equals("children")) {
                    processedComponent.put(key, processChildren(value));
                } else {
                    processedComponent.put(key, value);
                }
            });

            return processedComponent;
        }

        return component;
    }

    @SuppressWarnings("unchecked")
    private Object processChildren(Object children) {
        if (children instanceof Map) {
            Map<String, Object> childrenMap = (Map<String, Object>) children;
            Map<String, Object> processedChildren = new HashMap<>();

            childrenMap.forEach((key, value) -> {
                processedChildren.put(key, processComponent(value));
            });

            return processedChildren;
        }

        return children;
    }
}
```
```java
/**
 * Implementation of the Analyzer interface for website structures.
 */
public class StructureAnalyzer implements Analyzer<Map<String, Object>, Map<String, Object>> {

    @Override
    public Map<String, Object> analyze(Map<String, Object> input) {
        // Implementation of structure analysis logic
        Map<String, Object> results = new HashMap<>();

        // Extract structural information from the input
        results.put("hierarchy", analyzeHierarchy(input));
        results.put("layout", analyzeLayout(input));
        results.put("patterns", identifyPatterns(input));

        return results;
    }

    private Map<String, Object> analyzeHierarchy(Map<String, Object> input) {
        // Implementation of hierarchy analysis
        Map<String, Object> hierarchy = new HashMap<>();

        // Extract DOM structure and build hierarchy representation
        if (input.containsKey("dom.structure")) {
            hierarchy.put("depth", calculateDepth(input.get("dom.structure")));
            hierarchy.put("breadth", calculateBreadth(input.get("dom.structure")));
        }

        return hierarchy;
    }

    private Map<String, Object> analyzeLayout(Map<String, Object> input) {
        // Implementation of layout analysis
        Map<String, Object> layout = new HashMap<>();

        // Analyze layout properties
        if (input.containsKey("layout.grid")) {
            layout.put("grid", true);
        } else if (input.containsKey("layout.flex")) {
            layout.put("flex", true);
        }

        return layout;
    }

    private Map<String, Object> identifyPatterns(Map<String, Object> input) {
        // Implementation of pattern identification
        Map<String, Object> patterns = new HashMap<>();

        // Identify common design patterns
        if (input.containsKey("components.repeating")) {
            patterns.put("repeating", true);
        }

        return patterns;
    }

    private int calculateDepth(Object structure) {
        // Implementation of DOM tree depth calculation
        return 3; // Placeholder value
    }

    private int calculateBreadth(Object structure) {
        // Implementation of DOM tree breadth calculation
        return 5; // Placeholder value
    }
}
```
```java
/**
 * Class that combines multiple analyzers to provide comprehensive analysis.
 */
public class AnalysisEngine {
    private final ComponentAnalyzer componentAnalyzer;
    private final StructureAnalyzer structureAnalyzer;

    public AnalysisEngine(ComponentAnalyzer componentAnalyzer, StructureAnalyzer structureAnalyzer) {
        this.componentAnalyzer = componentAnalyzer;
        this.structureAnalyzer = structureAnalyzer;
    }

    public Map<String, Object> analyze(Map<String, Object> observations) {
        Map<String, Object> results = new HashMap<>();

        // Analyze components
        results.put("components", componentAnalyzer.analyze(observations));

        // Analyze structure
        results.put("structure", structureAnalyzer.analyze(observations));

        return results;
    }

    public Map<String, Object> analyzeComponent(Map<String, Object> componentObservations) {
        return componentAnalyzer.analyze(componentObservations);
    }
}
```
```java
Package: com.fractaljournal.browser
package com.fractaljournal.browser;

import com.fractaljournal.exceptions.BrowserAutomationException;
import com.fractaljournal.logging.FractalLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class that handles browser automation tasks.
 */
public class BrowserAutomationService {
    private static final FractalLogger logger = FractalLogger.getLogger(BrowserAutomationService.class);

    private final NovaActBridge novaActBridge;

    public BrowserAutomationService(NovaActBridge novaActBridge) {
        this.novaActBridge = novaActBridge;
    }

    /**
     * Executes a semantic instruction using the NovaAct bridge.
     *
     * @param instruction The semantic instruction to execute
     * @return Map of observations collected during execution
     */
    public Map<String, Object> execute(SemanticInstruction instruction) {
        try {
            logger.info("Executing instruction: {}", instruction);

            // Convert semantic instruction to NovaAct commands
            NovaActCommand command = semanticToSyntactic(instruction);

            // Execute the command and get the result
            NovaActResult result = novaActBridge.executeCommand(command);

            // Extract observations from the result
            return extractObservations(result);

        } catch (Exception e) {
            logger.error("Error executing browser automation", e);
            throw new BrowserAutomationException("Failed to execute browser automation", e);
        }
    }

    /**
     * Converts a semantic instruction to a syntactic NovaAct command.
     * This demonstrates the semantic-to-syntactic transformation.
     *
     * @param instruction The semantic instruction
     * @return The NovaAct command
     */
    private NovaActCommand semanticToSyntactic(SemanticInstruction instruction) {
        // Map of transformations for different instruction intents
        Map<String, Function<SemanticInstruction, String>> intentTransformers = new HashMap<>();

        // Define transformations for different intents
        intentTransformers.put("Analyze website structure",
            inst -> "navigate to " + inst.getContext() + " and identify structural elements");

        intentTransformers.put("Analyze specific component",
            inst -> "focus on element " + inst.getContext() + " and examine its properties");

        intentTransformers.put("Navigate and observe",
            inst -> "navigate to " + inst.getContext() + " and observe page elements");

        // Default transformation
        String commandText = intentTransformers
            .getOrDefault(instruction.getIntent(),
                inst -> "navigate to website and observe structure")
            .apply(instruction);

        return new NovaActCommand(commandText);
    }

    /**
     * Extracts observations from the NovaAct result.
     *
     * @param result The NovaAct execution result
     * @return Map of extracted observations
     */
    private Map<String, Object> extractObservations(NovaActResult result) {
        Map<String, Object> observations = new HashMap<>();

        // Extract DOM structure
        if (result.getResponse().contains("DOM structure")) {
            observations.put("dom.structure", extractDomStructure(result.getResponse()));
        }

        // Extract components
        if (result.getResponse().contains("components")) {
            observations.put("components", extractComponents(result.getResponse()));
        }

        // Extract layout information
        if (result.getResponse().contains("layout")) {
            observations.put("layout", extractLayout(result.getResponse()));
        }

        return observations;
    }

    private Object extractDomStructure(String response) {
        // Implementation to parse DOM structure from response
        return new HashMap<String, Object>() {{
            put("rootElement", "html");
            put("depth", 5);
            put("childElements", new HashMap<String, Object>() {{
                put("head", new HashMap<String, Object>());
                put("body", new HashMap<String, Object>() {{
                    put("childElements", new HashMap<String, Object>() {{
                        put("header", new HashMap<String, Object>());
                        put("main", new HashMap<String, Object>());
                        put("footer", new HashMap<String, Object>());
                    }});
                }});
            }});
        }};
    }

    private Object extractComponents(String response) {
        // Implementation to parse components from response
        return new HashMap<String, Object>() {{
            put("navigation", new HashMap<String, Object>() {{
                put("type", "menu");
                put("items", 5);
            }});
            put("content", new HashMap<String, Object>() {{
                put("type", "article");
                put("sections", 3);
            }});
            put("sidebar", new HashMap<String, Object>() {{
                put("type", "aside");
                put("widgets", 2);
            }});
        }};
    }

    private Object extractLayout(String response) {
        // Implementation to parse layout information from response
        return new HashMap<String, Object>() {{
            put("type", "flex");
            put("direction", "column");
            put("sections", 3);
        }};
    }
}
```
```java
/**
 * Class representing a semantic instruction for browser automation.
 * This uses the builder pattern for fluent interface construction.
 */
public class SemanticInstruction {
    private final String intent;
    private final String context;
    private final String action;

    private SemanticInstruction(Builder builder) {
        this.intent = builder.intent;
        this.context = builder.context;
        this.action = builder.action;
    }

    public String getIntent() {
        return intent;
    }

    public String getContext() {
        return context;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "SemanticInstruction{" +
               "intent='" + intent + '\'' +
               ", context='" + context + '\'' +
               ", action='" + action + '\'' +
               '}';
    }

    /**
     * Builder class for constructing SemanticInstruction instances.
     */
    public static class Builder {
        private String intent;
        private String context;
        private String action;

        public Builder withIntent(String intent) {
            this.intent = intent;
            return this;
        }

        public Builder withContext(String context) {
            this.context = context;
            return this;
        }

        public Builder withAction(String action) {
            this.action = action;
            return this;
        }

        public SemanticInstruction build() {
            if (intent == null || intent.isEmpty()) {
                throw new IllegalArgumentException("Intent cannot be null or empty");
            }
            return new SemanticInstruction(this);
        }
    }
}
```
```java
/**
 * Class representing a command to be executed by NovaAct.
 */
public class NovaActCommand {
    private final String command;

    public NovaActCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
```
```java
/**
 * Class representing the result of executing a NovaActCommand.
 */
public class NovaActResult {
    private final String response;
    private final boolean success;

    public NovaActResult(String response, boolean success) {
        this.response = response;
        this.success = success;
    }

    public String getResponse() {
        return response;
    }

    public boolean isSuccess() {
        return success;
    }
}
```
```java
/**
 * Bridge class that interfaces with the NovaAct Python SDK.
 * In a real implementation, this would use JNI, Process, or other
 * inter-language communication mechanism.
 */
public class NovaActBridge {
    private static final FractalLogger logger = FractalLogger.getLogger(NovaActBridge.class);

    private final Map<String, Object> browserConfig;

    public NovaActBridge(Map<String, Object> browserConfig) {
        this.browserConfig = browserConfig;
        logger.info("Initialized NovaActBridge with configuration: {}", browserConfig);
    }

    /**
     * Executes a NovaAct command.
     * This is a simplified simulation of the actual bridge functionality.
     *
     * @param command The command to execute
     * @return The result of the execution
     */
    public NovaActResult executeCommand(NovaActCommand command) {
        logger.info("Executing NovaAct command: {}", command.getCommand());

        // In a real implementation, this would call the NovaAct Python SDK
        // For this example, we simulate the response

        String simulatedResponse = simulateNovaActExecution(command.getCommand());
        return new NovaActResult(simulatedResponse, true);
    }

    private String simulateNovaActExecution(String command) {
        // Simulate different responses based on the command
        if (command.contains("navigate") && command.contains("structure")) {
            return "DOM structure identified with 3 main sections. " +
                   "Components detected: header, navigation, main content, sidebar, footer. " +
                   "Layout appears to be using flex containers with responsive design.";
        } else if (command.contains("focus on element")) {
            String element = command.substring(command.indexOf("element") + 8, command.indexOf("and"));
            return "Analyzed " + element.trim() + " component. " +
                   "Component has 5 child elements with interactive features. " +
                   "Styles include flex layout, custom fonts, and hover effects.";
        } else {
            return "Executed command: " + command + ". " +
                   "Observed page structure with multiple components and layout sections.";
        }
    }
}
```
```java
/**
 * Package: com.fractaljournal.journal
 */
package com.fractaljournal.journal;

import com.fractaljournal.browser.SemanticInstruction;
import com.fractaljournal.exceptions.JournalException;
import com.fractaljournal.utils.FractalHashMap;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Class representing a journal entry that records automation and analysis.
 * Uses builder pattern for construction and maintains immutable state.
 */
public class JournalEntry {
    private final UUID id;
    private final String title;
    private final LocalDateTime creationTime;
    private final SemanticInstruction instruction;
    private final JournalEntry parentEntry;

    private Map<String, Object> analysisResults;
    private FractalHashMap<String, JournalEntry> childEntries;

    private JournalEntry(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.creationTime = builder.creationTime;
        this.instruction = builder.instruction;
        this.parentEntry = builder.parentEntry;
        this.analysisResults = null;
        this.childEntries = null;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public SemanticInstruction getInstruction() {
        return instruction;
    }

    public Optional<JournalEntry> getParentEntry() {
        return Optional.ofNullable(parentEntry);
    }

    public Optional<Map<String, Object>> getAnalysisResults() {
        return Optional.ofNullable(analysisResults);
    }

    public Optional<FractalHashMap<String, JournalEntry>> getChildEntries() {
        return Optional.ofNullable(childEntries);
    }

    @SuppressWarnings("unchecked")
    public Optional<Map<String, Object>> getComponentsFromAnalysis() {
        if (analysisResults != null && analysisResults.containsKey("components")) {
            return Optional.of((Map<String, Object>) analysisResults.get("components"));
        }
        return Optional.empty();
    }

    public void updateWithAnalysis(Map<String, Object> analysisResults) {
        this.analysisResults = analysisResults;
    }

    public void updateWithChildEntries(FractalHashMap<String, JournalEntry> childEntries) {
        this.childEntries = childEntries;
    }

    /**
     * Builder class for JournalEntry.
     */
    public static class Builder {
        private final UUID id;
        private String title;
        private final LocalDateTime creationTime;
        private SemanticInstruction instruction;
        private JournalEntry parentEntry;

        public Builder() {
            this.id = UUID.randomUUID();
            this.creationTime = LocalDateTime.now();
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withInstruction(SemanticInstruction instruction) {
            this.instruction = instruction;
            return this;
        }

        public Builder withParentEntry(JournalEntry parentEntry) {
            this.parentEntry = parentEntry;
            return this;
        }

        public JournalEntry build() {
            if (title == null || title.isEmpty()) {
                throw new IllegalArgumentException("Title cannot be null or empty");
            }
            if (instruction == null) {
                throw new IllegalArgumentException("Instruction cannot be null");
            }
            return new JournalEntry(this);
        }
    }
}
```
```java
/**
 * Manager class for journal entries.
 */
public class JournalManager {
    private final Map<String, Object> journalConfig;
    private final Map<UUID, JournalEntry> entries = new HashMap<>();

    public JournalManager(Map<String, Object> journalConfig) {
        this.journalConfig = journalConfig;
    }

    public void storeEntry(JournalEntry entry) throws JournalException {
        try {
            entries.put(entry.getId(), entry);
        } catch (Exception e) {
            throw new JournalException("Failed to store journal entry", e);
        }
    }

    public void updateEntry(JournalEntry entry) throws JournalException {
        try {
            entries.put(entry.getId(), entry);
        } catch (Exception e) {
            throw new JournalException("Failed to update journal entry", e);
        }
    }

    public Optional<JournalEntry> getEntry(UUID id) {
        return Optional.ofNullable(entries.get(id));
    }

    public Map<UUID, JournalEntry> getAllEntries() {
        return new HashMap<>(entries);
    }
}
```
```java
Package: com.fractaljournal.utils
package com.fractaljournal.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A specialized implementation of LinkedHashMap that adds fractal capabilities.
 * This map preserves insertion order and provides additional methods for
 * recursive operations.
 *
 * @param <K> The type of keys in the map
 * @param <V> The type of values in the map
 */
public class FractalHashMap<K, V> implements Map<K, V> {

    private final LinkedHashMap<K, V> internalMap;

    public FractalHashMap() {
        this.internalMap = new LinkedHashMap<>();
    }

    public FractalHashMap(Map<K, V> map) {
        this.internalMap = new LinkedHashMap<>(map);
    }

    /**
     * Applies a function recursively to all values in the map that are themselves maps.
     *
     * @param function The function to apply
     * @param <R> The return type of the function
     * @return A new map with the function applied
     */
    @SuppressWarnings("unchecked")
    public <R> Map<K, R> recursiveMap(Function<V, R> function) {
        Map<K, R> result = new HashMap<>();

        for (Entry<K, V> entry : internalMap.entrySet()) {
            if (entry.getValue() instanceof Map) {
                FractalHashMap<Object, Object> subMap = new FractalHashMap<>((Map<Object, Object>) entry.getValue());
                result.put(entry.getKey(), (R) subMap.recursiveMap(v -> function.apply((V) v)));
            } else {
                result.put(entry.getKey(), function.apply(entry.getValue()));
            }
        }

        return result;
    }

    /**
     * Finds all values at any depth that match the predicate.
     *
     * @param predicate The predicate to match
     * @return A map of paths to matching values
     */
    @SuppressWarnings("unchecked")
    public Map<String, V> findRecursive(Function<V, Boolean> predicate) {
        Map<String, V> result = new HashMap<>();

        for (Entry<K, V> entry : internalMap.entrySet()) {
            String path = entry.getKey().toString();

            if (predicate.apply(entry.getValue())) {
                result.put(path, entry.getValue());
            }

            if (entry.getValue() instanceof Map) {
                FractalHashMap<Object, Object> subMap = new FractalHashMap<>((Map<Object, Object>) entry.getValue());
                Map<String, Object> subResults = subMap.findRecursive(v -> predicate.apply((V) v));

                for (Entry<String, Object> subEntry : subResults.entrySet()) {
                    result.put(path + "." + subEntry.getKey(), (V) subEntry.getValue());
                }
            }
        }

        return result;
    }

    // Delegate methods to internal LinkedHashMap

    @Override
    public int size() {
        return internalMap.size();
    }

    @Override
    public boolean isEmpty() {
        return internalMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return internalMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return internalMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return internalMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        return internalMap.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return internalMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        internalMap.putAll(m);
    }

    @Override
    public void clear() {
        internalMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return internalMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return internalMap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return internalMap.entrySet();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return internalMap.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        internalMap.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        internalMap.replaceAll(function);
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return internalMap.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return internalMap.remove(key, value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return internalMap.replace(key, oldValue, newValue);
    }

    @Override
    public V replace(K key, V value) {
        return internalMap.replace(key, value);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return internalMap.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return internalMap.computeIfPresent(key, remappingFunction);
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return internalMap.compute(key, remappingFunction);
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return internalMap.merge(key, value, remappingFunction);
    }
}
```
```java
/**
 * Package: com.fractaljournal.exceptions
 */
package com.fractaljournal.exceptions;

/**
 * Base exception class for the application.
 */
public class FractalJournalException extends RuntimeException {

    public FractalJournalException(String message) {
        super(message);
    }

    public FractalJournalException(String message, Throwable cause) {
        super(message, cause);
    }
}
```
```java
/**
 * Exception thrown when browser automation fails.
 */
public class BrowserAutomationException extends FractalJournalException {

    public BrowserAutomationException(String message) {
        super(message);
    }

    public BrowserAutomationException(String message, Throwable cause) {
        super(message, cause);
    }
}
```
```java
/**
 * Exception thrown when journal operations fail.
 */
public class JournalException extends FractalJournalException {

    public JournalException(String message) {
        super(message);
    }

    public JournalException(String message, Throwable cause) {
        super(message, cause);
    }
}
```
```java
/**
 * Exception thrown when configuration is invalid.
 */
public class ConfigurationException extends FractalJournalException {

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
```
```java
/**
 * Package: com.fractaljournal.logging
 */
package com.fractaljournal.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrapper class for SLF4J logging.
 */
public class FractalLogger {

    private final Logger logger;

    private FractalLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public static FractalLogger getLogger(Class<?> clazz) {
        return new FractalLogger(clazz);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void info(String message, Object... args) {
        logger.info(message, args);
    }

    public void debug(String message) {
        logger.debug(message);
    }

    public void debug(String message, Object... args) {
        logger.debug(message, args);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void warn(String message, Object... args) {
        logger.warn(message, args);
    }

    public void error(String message) {
        logger.error(message);
    }

    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public void error(String message, Object... args) {
        logger.error(message, args);
    }
}
```
```java
/**
 * Package: com.fractaljournal.config
package com.fractaljournal.config;

import com.fractaljournal.exceptions.ConfigurationException;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for the application.
 */
public class Configurat ion {
    private final Map<String, Object> properties = new HashMap<>();

    public Configuration() {
        // Set default values
        setDefaults();
    }

    private void setDefaults() {
        // Browser configuration defaults
        Map<String, Object> browserConfig = new HashMap<>();
        browserConfig.put("screenWidth", 1920);
        browserConfig.put("screenHeight", 1080);
        browserConfig.put("headless", false);
        browserConfig.put("startingPage", "https://example.com");
        properties.put("browser", browserConfig);

        // Journal configuration defaults
        Map<String, Object> journalConfig = new HashMap<>();
        journalConfig.put("storageLocation", "journal");
        journalConfig.put("maxEntries", 1000);
        properties.put("journal", journalConfig);

        // Observation configuration defaults
        Map<String, Object> observationConfig = new HashMap<>();
        observationConfig.put("screenshotEnabled", true);
        observationConfig.put("domCaptureEnabled", true);
        properties.put("observation", observationConfig);
    }

    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getBrowserConfig() {
        return (Map<String, Object>) properties.getOrDefault("browser", new HashMap<>());
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getJournalConfig() {
        return (Map<String, Object>) properties.getOrDefault("journal", new HashMap<>());
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getObservationConfig() {
        return (Map<String, Object>) properties.getOrDefault("observation", new HashMap<>());
    }

    public void overrideWith(Map<String, Object> overrides) {
        if (overrides == null) {
            return;
        }

        overrides.forEach((key, value) -> {
            if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> existingMap = (Map<String, Object>) properties.getOrDefault(key, new HashMap<>());

                @SuppressWarnings("unchecked")
                Map<String, Object> overrideMap = (Map<String, Object>) value;

                existingMap.putAll(overrideMap);
                properties.put(key, existingMap);
            } else {
                properties.put(key, value);
            }
        });
    }

    public void validate() throws ConfigurationException {
        // Validate browser configuration
        Map<String, Object> browserConfig = getBrowserConfig();
        if (!browserConfig.containsKey("startingPage")) {
            throw new ConfigurationException("Browser configuration must include starting page");
        }

        // Validate journal configuration
        Map<String, Object> journalConfig = getJournalConfig();
        if (!journalConfig.containsKey("storageLocation")) {
            throw new ConfigurationException("Journal configuration must include storage location");
        }

        // Validate observation configuration
        Map<String, Object> observationConfig = getObservationConfig();
        if (!observationConfig.containsKey("screenshotEnabled")) {
            throw new ConfigurationException("Observation configuration must specify if screenshots are enabled");
        }
    }
}
```
```java
/**
 * Package: com.fractaljournal.observation
package com.fractaljournal.observation;

import java.util.HashMap;
import java.util.Map;

/**
 * Collects and processes observations from browser automation.
 */
public class ObservationCollector {
    private final Map<String, Object> observationConfig;

    public ObservationCollector(Map<String, Object> observationConfig) {
        this.observationConfig = observationConfig;
    }

    /**
     * Collects observations from browser automation results.
     *
     * @param rawObservations The raw observations from the browser
     * @return Processed observations
     */
    public Map<String, Object> collectObservations(Map<String, Object> rawObservations) {
        Map<String, Object> processedObservations = new HashMap<>();

        // Process DOM structure observations
        if (rawObservations.containsKey("dom.structure")) {
            processedObservations.put("dom.structure", rawObservations.get("dom.structure"));
        }

        // Process component observations
        if (rawObservations.containsKey("components")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> components = (Map<String, Object>) rawObservations.get("components");

            // Process each component and add "component." prefix
            components.forEach((key, value) -> {
                processedObservations.put("component." + key, value);
            });
        }

        // Process layout observations
        if (rawObservations.containsKey("layout")) {
            processedObservations.put("layout", rawObservations.get("layout"));
        }

        return processedObservations;
    }
}
```
```java
Package: com.fractaljournal.cli
package com.fractaljournal.cli;

import com.fractaljournal.config.Configuration;
import com.fractaljournal.exceptions.ConfigurationException;
import com.fractaljournal.logging.FractalLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * Command-line interface for the application.
 */
public class CommandLineInterface {
    private static final FractalLogger logger = FractalLogger.getLogger(CommandLineInterface.class);

    private final String[] args;

    public CommandLineInterface(String[] args) {
        this.args = args;
    }

    /**
     * Parses command-line arguments and creates a configuration.
     *
     * @return The configuration
     * @throws ConfigurationException If configuration is invalid
     */
    public Configuration parseConfiguration() throws ConfigurationException {
        Configuration config = new Configuration();

        Map<String, Object> overrides = parseArgs();
        config.overrideWith(overrides);

        try {
            config.validate();
        } catch (ConfigurationException e) {
            logger.error("Invalid configuration", e);
            throw e;
        }

        return config;
    }

    private Map<String, Object> parseArgs() {
        Map<String, Object> overrides = new HashMap<>();

        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].startsWith("--")) {
                    String key = args[i].substring(2);

                    if (i + 1 < args.length && !args[i + 1].startsWith("--")) {
                        String value = args[i + 1];
                        processArgument(overrides, key, value);
                        i++;
                    } else {
                        processArgument(overrides, key, "true");
                    }
                }
            }
        }

        return overrides;
    }

    @SuppressWarnings("unchecked")
    private void processArgument(Map<String, Object> overrides, String key, String value) {
        if (key.contains(".")) {
            String[] parts = key.split("\\.", 2);
            String section = parts[0];
            String subKey = parts[1];

            Map<String, Object> sectionMap = (Map<String, Object>) overrides.computeIfAbsent(
                section, k -> new HashMap<>());

            setValueWithType(sectionMap, subKey, value);
        } else {
            setValueWithType(overrides, key, value);
        }
    }

    private void setValueWithType(Map<String, Object> map, String key, String value) {
        // Try to parse as integer
        try {
            int intValue = Integer.parseInt(value);
            map.put(key, intValue);
            return;
        } catch (NumberFormatException e) {
            // Not an integer, continue
        }

        // Try to parse as boolean
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            map.put(key, Boolean.parseBoolean(value));
            return;
        }

        // Default to string
        map.put(key, value);
    }
}
```
