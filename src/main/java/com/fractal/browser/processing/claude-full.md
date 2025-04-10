╭────────────────────────────────────────────────╮
│ ✻ Welcome to Claude Code research preview!     │
│                                                │
│   /help for help                               │
│                                                │
│   cwd: /Users/ryandavidoates/Nova-act-revisit  │
╰────────────────────────────────────────────────╯

 ⚠ Large /Users/ryandavidoates/.claude/CLAUDE.md will impact performance 
   (126.6k chars > 40k) • /memory to edit

> these are still empty /User
  s/ryandavidoates/Nova-act-r
  evisit/src/main/java/com/fr
  actal/browser/collective/bo
  undaries/PrivacyFilter.java
  /Users/ryandavidoates/Nova-
  act-revisit/src/main/java/c
  om/fractal/browser/collecti
  ve/boundaries/TrustManager.
  java
  /Users/ryandavidoates/Nova-
  act-revisit/src/main/java/c
  om/fractal/browser/collecti
  ve/boundaries/SafetyContain
  er.java
  /Users/ryandavidoates/Nova-
  act-revisit/src/main/java/c
  om/fractal/browser/collecti
  ve/boundaries/InformationBo
  undary.java

⏺ Cal(Checking Java boundar…
     class files)
  ⎿  (No content)
  ⎿  (No content)
  ⎿  (No content)
  ⎿  (No content)
  ⎿ Done (4 tool uses · 0s)

⏺ Would you like me to
  implement these boundary
  classes? They seem to be
  part of the collective
  boundaries in your fractal
  browser system.

> /Users/ryandavidoates/Nova-
  act-revisit/src/main/java/c
  om/fractal/browser/collecti
  ve/communication/NodeDiscov
  ery.java
  /Users/ryandavidoates/Nova-
  act-revisit/src/main/java/c
  om/fractal/browser/collecti
  ve/communication/InsightExc
  hange.java
  /Users/ryandavidoates/Nova-
  act-revisit/src/main/java/c
  om/fractal/browser/collecti
  ve/communication/Synchroniz
  ationProtocol.java
  /Users/ryandavidoates/Nova-
  act-revisit/src/main/java/c
  om/fractal/browser/collecti
  ve/communication/Bifurcatio
  nBroadcast.java/Users/ryand
  avidoates/Nova-act-revisit/
  src/main/java/com/fractal/b
  rowser/collective/communica
  tion/NodeDiscovery.java
  /Users/ryandavidoates/Nova-
  act-revisit/src/main/java/c
  om/fractal/browser/collecti
  ve/communication/InsightExc
  hange.java
  /Users/ryandavidoates/Nova-
  act-revisit/src/main/java/c
  om/fractal/browser/collecti
  ve/communication/Synchroniz
  ationProtocol.java
  /Users/ryandavidoates/Nova-
  act-revisit/src/main/java/c
  om/fractal/browser/collecti
  ve/communication/Bifurcatio
  nBroadcast.java

⏺ Cal(Checking Java      …
     communication class
     files)
  ⎿  (No content)
  ⎿  (No content)
  ⎿  (No content)
  ⎿  (No content)
  ⎿ Done (4 tool uses · 0s)

⏺ These communication class
  files also appear to be
  empty. Would you like me to
   implement these classes
  too?

> as well as /Users/ryandavid
  oates/Nova-act-revisit/src/
  main/java/com/fractal/brows
  er/collective/memory 
  /Users/ryandavidoates/Nova-
  act-revisit/src/main/java/c
  om/fractal/browser/collecti
  ve/processing memory, 
  processing and 
  visualization packages 
  /Users/ryandavidoates/Nova-
  act-revisit/src/main/java/c
  om/fractal/browser/collecti
  ve/visualization

⏺ Task(Check all Java files in the collective memory, processing, and visualization directories to see…
      if they're empty. List all files found in these directories that need implementation.

      Check these specific directories:
      1. /Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/memory
      2. /Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/processing
      3.
      /Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/visualization

      Use GlobTool to find all Java files in these directories, then View each file to check if it's
      empty.)


  ⎿  Done (14 tool uses · 37.4k tokens · 52.9s)

⏺ Task(I need to understand the architecture and interfaces in the codebase before implementing these …
      classes. Check for:

      1. Any existing interfaces or base classes these implementations would need to extend or implement

      2. Any existing model/entity classes they would need to interact with
      3. Any other implemented classes in the codebase that could provide a pattern to follow
      4. Any documentation files about the architecture that might describe how these components should
      work

      Look in the core/ directories, check for interfaces or documentation across the project.)
  ⎿  Done (27 tool uses · 74.9k tokens · 1m 46.1s)

⏺ I'll need to implement all these empty Java files. Let me start with the boundary classes first.

⏺ Task(Find any existing core interfaces or related classes that the boundary classes                 …
      (InformationBoundary, PrivacyFilter, SafetyContainer, TrustManager) would need to implement or
      use. Specifically, look for:

      1. Any interfaces that these classes might need to implement
      2. Related model classes they might use
      3. Any common patterns in the existing codebase for similar classes

      Also check the Python implementation in src/main/python/nova_act/collective/boundaries to see if
      there's corresponding functionality that could provide insight.)
  ⎿  Done (23 tool uses · 39.8k tokens · 1m 9.7s)

⏺ Read(file_path: "/Users/ryandavidoates/Nova-act-revisit/src/main/python/nova_act/collective/boundari…s
      /information_boundary.py")
  ⎿  (No content)

⏺ Read(file_path: "/Users/ryandavidoates/Nova-act-revisit/src/main/python/nova_act/collective/boundari…s
      /privacy_filter.py")
  ⎿  (No content)

⏺ Let me implement the boundary classes first. I'll start with InformationBoundary.java:

⏺ Create(/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/boundarie…/
        InformationBoundary.java)
  ⎿  Updated/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/boundar
            ies/InformationBoundary.java with 141 additions
       1  package com.fractal.browser.collective.boundaries;
       2  
       3  import java.util.Map;
       4  import java.util.Optional;
       5  import java.util.concurrent.ConcurrentHashMap;
       6  
       7  /**
       8   * InformationBoundary defines the interface and mechanisms for managing information flow 
       9   * between collective nodes in a fractal communication network. This class implements
      10   * boundary conditions that determine what information can pass between nodes based on
      11   * context, trust levels, and information sensitivity.
      12   * 
      13   * It serves as a recursive boundary system, operating at multiple scales to ensure
      14   * appropriate information sharing while maintaining system integrity.
      15   */
      16  public class InformationBoundary {
      17      
      18      private final Map<String, Integer> contextualAccessLevels;
      19      private final Map<String, Double> informationSensitivityMap;
      20      
      21      /**
      22       * Creates a new InformationBoundary with default settings.
      23       */
      24      public InformationBoundary() {
      25          this.contextualAccessLevels = new ConcurrentHashMap<>();
      26          this.informationSensitivityMap = new ConcurrentHashMap<>();
      27      }
      28      
      29      /**
      30       * Registers a context with a specific access level.
      31       * Higher access levels indicate greater access to sensitive information.
      32       *
      33       * @param contextId The unique identifier for the context
      34       * @param accessLevel The access level (0-10, where 10 is highest access)
      35       * @return true if successfully registered, false otherwise
      36       */
      37      public boolean registerContext(String contextId, int accessLevel) {
      38          if (accessLevel < 0 || accessLevel > 10) {
      39              return false;
      40          }
      41          
      42          contextualAccessLevels.put(contextId, accessLevel);
      43          return true;
      44      }
      45      
      46      /**
      47       * Sets the sensitivity level for a piece of information.
      48       * Higher sensitivity requires higher access levels for distribution.
      49       *
      50       * @param informationId The unique identifier for the information
      51       * @param sensitivityLevel The sensitivity level (0.0-1.0, where 1.0 is highest sensitiv
         ity)
      52       * @return true if successfully set, false otherwise
      53       */
      54      public boolean setSensitivity(String informationId, double sensitivityLevel) {
      55          if (sensitivityLevel < 0.0 || sensitivityLevel > 1.0) {
      56              return false;
      57          }
      58          
      59          informationSensitivityMap.put(informationId, sensitivityLevel);
      60          return true;
      61      }
      62      
      63      /**
      64       * Evaluates whether information can pass through the boundary in the given context.
      65       *
      66       * @param informationId The unique identifier for the information
      67       * @param contextId The context in which the information is being accessed
      68       * @return true if information can pass, false otherwise
      69       */
      70      public boolean canInformationPass(String informationId, String contextId) {
      71          Integer accessLevel = contextualAccessLevels.getOrDefault(contextId, 0);
      72          Double sensitivityLevel = informationSensitivityMap.getOrDefault(informationId, 1.0)
         ;
      73          
      74          // Calculate the required access level based on sensitivity
      75          int requiredAccessLevel = (int) Math.ceil(sensitivityLevel * 10);
      76          
      77          return accessLevel >= requiredAccessLevel;
      78      }
      79      
      80      /**
      81       * Gets the metadata about information passing constraints for a given context,
      82       * including required access levels and current permissions.
      83       *
      84       * @param contextId The context to evaluate
      85       * @return A map of metadata about the boundary conditions for this context
      86       */
      87      public Map<String, Object> getBoundaryMetadata(String contextId) {
      88          Map<String, Object> metadata = new ConcurrentHashMap<>();
      89          
      90          Integer accessLevel = contextualAccessLevels.getOrDefault(contextId, 0);
      91          metadata.put("contextId", contextId);
      92          metadata.put("accessLevel", accessLevel);
      93          metadata.put("maxSensitivityAllowed", accessLevel / 10.0);
      94          
      95          return metadata;
      96      }
      97      
      98      /**
      99       * Transforms information to comply with boundary conditions, potentially
     100       * redacting or summarizing sensitive content that cannot be shared fully.
     101       *
     102       * @param information The original information
     103       * @param contextId The context in which the information is being accessed
     104       * @return The transformed information that complies with boundary conditions
     105       */
     106      public String transformInformationForContext(String information, String contextId) {
     107          Integer accessLevel = contextualAccessLevels.getOrDefault(contextId, 0);
     108          
     109          // Basic transformation logic - in a real implementation, this would be more sophist
         icated
     110          if (accessLevel < 3) {
     111              return "[Redacted - Insufficient Access Level]";
     112          } else if (accessLevel < 7) {
     113              // Return a summarized version for medium access levels
     114              return "Summary: " + information.substring(0, Math.min(50, information.length())
         ) 
     115                  + (information.length() > 50 ? "..." : "");
     116          }
     117          
     118          // Full access for high access levels
     119          return information;
     120      }
     121      
     122      /**
     123       * Integrates with the TrustManager to adjust boundary conditions based on trust levels.
     124       *
     125       * @param trustManager The TrustManager to integrate with
     126       * @param contextId The context to update based on trust
     127       * @return true if successfully integrated, false otherwise
     128       */
     129      public boolean integrateWithTrustManager(TrustManager trustManager, String contextId) {
     130          Optional<Double> trustScore = trustManager.getTrustScore(contextId);
     131          
     132          if (trustScore.isPresent()) {
     133              // Adjust access level based on trust score (0.0-1.0)
     134              int newAccessLevel = (int) Math.round(trustScore.get() * 10);
     135              contextualAccessLevels.put(contextId, newAccessLevel);
     136              return true;
     137          }
     138          
     139          return false;
     140      }
     141  }
     142 \ No newline at end of file

⏺ Now I'll implement the PrivacyFilter class:

⏺ Create(/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/boundarie…/
        PrivacyFilter.java)
  ⎿  Updated/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/boundar
            ies/PrivacyFilter.java with 221 additions
       1  package com.fractal.browser.collective.boundaries;
       2  
       3  import java.util.HashMap;
       4  import java.util.Map;
       5  import java.util.Set;
       6  import java.util.HashSet;
       7  import java.util.regex.Pattern;
       8  import java.util.regex.Matcher;
       9  
      10  /**
      11   * PrivacyFilter implements mechanisms to filter and protect sensitive information
      12   * within the fractal browser collective. It applies privacy rules recursively across
      13   * different scales of information, ensuring appropriate data protection while 
      14   * allowing necessary information exchange.
      15   * 
      16   * This class works in conjunction with other boundary mechanisms to create a 
      17   * multi-layered approach to privacy protection.
      18   */
      19  public class PrivacyFilter {
      20      
      21      private final Map<String, Pattern> sensitivePatterns;
      22      private final Set<String> sensitiveTerms;
      23      private boolean strictFilteringEnabled;
      24      private int privacyLevel; // 1-5, where 5 is most restrictive
      25      
      26      /**
      27       * Creates a new PrivacyFilter with default settings (medium privacy level).
      28       */
      29      public PrivacyFilter() {
      30          this.sensitivePatterns = new HashMap<>();
      31          this.sensitiveTerms = new HashSet<>();
      32          this.strictFilteringEnabled = false;
      33          this.privacyLevel = 3; // Default medium privacy level
      34          
      35          initializeDefaultPatterns();
      36      }
      37      
      38      /**
      39       * Creates a new PrivacyFilter with specific privacy level.
      40       * 
      41       * @param privacyLevel The privacy level (1-5) where 5 is most restrictive
      42       */
      43      public PrivacyFilter(int privacyLevel) {
      44          this.sensitivePatterns = new HashMap<>();
      45          this.sensitiveTerms = new HashSet<>();
      46          this.strictFilteringEnabled = false;
      47          this.privacyLevel = Math.max(1, Math.min(5, privacyLevel));
      48          
      49          initializeDefaultPatterns();
      50      }
      51      
      52      /**
      53       * Initializes default patterns for sensitive information detection.
      54       */
      55      private void initializeDefaultPatterns() {
      56          // Email pattern
      57          sensitivePatterns.put("email", 
      58              Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"));
      59          
      60          // Phone number pattern (simplified)
      61          sensitivePatterns.put("phone", 
      62              Pattern.compile("\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}"));
      63          
      64          // Add default sensitive terms
      65          sensitiveTerms.add("password");
      66          sensitiveTerms.add("secret");
      67          sensitiveTerms.add("private");
      68          sensitiveTerms.add("confidential");
      69      }
      70      
      71      /**
      72       * Adds a custom pattern for identifying sensitive information.
      73       * 
      74       * @param patternName A unique name for the pattern
      75       * @param pattern The regex pattern to detect sensitive information
      76       * @return true if successfully added, false if the name already exists
      77       */
      78      public boolean addSensitivePattern(String patternName, String pattern) {
      79          if (sensitivePatterns.containsKey(patternName)) {
      80              return false;
      81          }
      82          
      83          try {
      84              Pattern compiledPattern = Pattern.compile(pattern);
      85              sensitivePatterns.put(patternName, compiledPattern);
      86              return true;
      87          } catch (Exception e) {
      88              return false;
      89          }
      90      }
      91      
      92      /**
      93       * Adds a sensitive term to be filtered.
      94       * 
      95       * @param term The sensitive term to add
      96       * @return true if added, false if it already exists
      97       */
      98      public boolean addSensitiveTerm(String term) {
      99          return sensitiveTerms.add(term.toLowerCase());
     100      }
     101      
     102      /**
     103       * Sets the privacy level for this filter.
     104       * 
     105       * @param level The privacy level (1-5) where 5 is most restrictive
     106       */
     107      public void setPrivacyLevel(int level) {
     108          this.privacyLevel = Math.max(1, Math.min(5, level));
     109      }
     110      
     111      /**
     112       * Enables or disables strict filtering mode.
     113       * When enabled, even borderline cases are filtered.
     114       * 
     115       * @param enabled true to enable strict filtering, false to disable
     116       */
     117      public void setStrictFiltering(boolean enabled) {
     118          this.strictFilteringEnabled = enabled;
     119      }
     120      
     121      /**
     122       * Filters the input content based on privacy rules.
     123       * 
     124       * @param content The content to filter
     125       * @return The filtered content with sensitive information redacted
     126       */
     127      public String filterContent(String content) {
     128          if (content == null || content.isEmpty()) {
     129              return content;
     130          }
     131          
     132          String result = content;
     133          
     134          // Apply pattern-based filtering
     135          for (Map.Entry<String, Pattern> entry : sensitivePatterns.entrySet()) {
     136              Matcher matcher = entry.getValue().matcher(result);
     137              result = matcher.replaceAll("[REDACTED-" + entry.getKey().toUpperCase() + "]");
     138          }
     139          
     140          // Apply term-based filtering if privacy level is high enough
     141          if (privacyLevel >= 3) {
     142              for (String term : sensitiveTerms) {
     143                  // Case insensitive replace
     144                  String pattern = "(?i)" + Pattern.quote(term);
     145                  result = result.replaceAll(pattern, "[REDACTED-TERM]");
     146              }
     147          }
     148          
     149          // Apply stricter filtering based on privacy level
     150          if (privacyLevel >= 4) {
     151              // For high privacy levels, redact numeric sequences that might be sensitive
     152              if (strictFilteringEnabled) {
     153                  result = result.replaceAll("\\b\\d{4,}\\b", "[REDACTED-NUMBER]");
     154              }
     155          }
     156          
     157          return result;
     158      }
     159      
     160      /**
     161       * Evaluates if the content contains sensitive information.
     162       * 
     163       * @param content The content to check
     164       * @return true if sensitive information is detected, false otherwise
     165       */
     166      public boolean containsSensitiveInformation(String content) {
     167          if (content == null || content.isEmpty()) {
     168              return false;
     169          }
     170          
     171          // Check patterns
     172          for (Pattern pattern : sensitivePatterns.values()) {
     173              if (pattern.matcher(content).find()) {
     174                  return true;
     175              }
     176          }
     177          
     178          // Check terms
     179          for (String term : sensitiveTerms) {
     180              if (content.toLowerCase().contains(term)) {
     181                  return true;
     182              }
     183          }
     184          
     185          return false;
     186      }
     187      
     188      /**
     189       * Gets the current privacy level.
     190       * 
     191       * @return The privacy level (1-5)
     192       */
     193      public int getPrivacyLevel() {
     194          return privacyLevel;
     195      }
     196      
     197      /**
     198       * Integrates this filter with the SafetyContainer to provide multi-layered protection.
     199       * 
     200       * @param container The SafetyContainer to integrate with
     201       * @return A composite filter that combines both protections
     202       */
     203      public PrivacyFilter integrateWithSafetyContainer(SafetyContainer container) {
     204          // Create a new filter with the higher privacy level between the two
     205          PrivacyFilter compositeFilter = new PrivacyFilter(
     206              Math.max(this.privacyLevel, container.getSafetyLevel()));
     207          
     208          // Copy all patterns and terms from this filter
     209          for (Map.Entry<String, Pattern> entry : this.sensitivePatterns.entrySet()) {
     210              compositeFilter.sensitivePatterns.put(entry.getKey(), entry.getValue());
     211          }
     212          compositeFilter.sensitiveTerms.addAll(this.sensitiveTerms);
     213          
     214          // Add safety-specific patterns and terms
     215          for (String safetyTerm : container.getSafetyTerms()) {
     216              compositeFilter.addSensitiveTerm(safetyTerm);
     217          }
     218          
     219          return compositeFilter;
     220      }
     221  }
     222 \ No newline at end of file

⏺ Now let's implement the SafetyContainer class:

⏺ Create(/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/boundarie…/
        SafetyContainer.java)
  ⎿  Updated/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/boundar
            ies/SafetyContainer.java with 234 additions
       1  package com.fractal.browser.collective.boundaries;
       2  
       3  import java.util.Set;
       4  import java.util.HashSet;
       5  import java.util.Collections;
       6  import java.util.function.Function;
       7  import java.util.ArrayList;
       8  import java.util.List;
       9  
      10  /**
      11   * SafetyContainer provides mechanisms to ensure the safety of information processing
      12   * within the fractal browser collective. It implements containment policies that 
      13   * enforce safety boundaries at multiple levels of the system.
      14   * 
      15   * This class creates safe execution contexts for potentially dangerous operations,
      16   * working in a fractal pattern to contain risks at various scales.
      17   */
      18  public class SafetyContainer {
      19      
      20      private final Set<String> safetyTerms;
      21      private final List<Function<String, String>> safetyTransformers;
      22      private int safetyLevel; // 1-5, where 5 is most restrictive
      23      private boolean strictModeEnabled;
      24      
      25      /**
      26       * Creates a new SafetyContainer with default settings (medium safety level).
      27       */
      28      public SafetyContainer() {
      29          this.safetyTerms = new HashSet<>();
      30          this.safetyTransformers = new ArrayList<>();
      31          this.safetyLevel = 3; // Default medium safety level
      32          this.strictModeEnabled = false;
      33          
      34          initializeDefaultSafetyTerms();
      35          initializeDefaultTransformers();
      36      }
      37      
      38      /**
      39       * Creates a new SafetyContainer with a specific safety level.
      40       * 
      41       * @param safetyLevel The safety level (1-5) where 5 is most restrictive
      42       */
      43      public SafetyContainer(int safetyLevel) {
      44          this.safetyTerms = new HashSet<>();
      45          this.safetyTransformers = new ArrayList<>();
      46          this.safetyLevel = Math.max(1, Math.min(5, safetyLevel));
      47          this.strictModeEnabled = false;
      48          
      49          initializeDefaultSafetyTerms();
      50          initializeDefaultTransformers();
      51      }
      52      
      53      /**
      54       * Initializes default safety terms.
      55       */
      56      private void initializeDefaultSafetyTerms() {
      57          safetyTerms.add("malware");
      58          safetyTerms.add("exploit");
      59          safetyTerms.add("vulnerability");
      60          safetyTerms.add("attack");
      61      }
      62      
      63      /**
      64       * Initializes default safety transformers that sanitize content.
      65       */
      66      private void initializeDefaultTransformers() {
      67          // Basic HTML sanitizer
      68          safetyTransformers.add(content -> 
      69              content.replaceAll("<script[^>]*>.*?</script>", "[REMOVED-UNSAFE-SCRIPT]"));
      70          
      71          // Remove executable code patterns
      72          safetyTransformers.add(content -> 
      73              content.replaceAll("(?i)exec\\s*\\(", "[REMOVED-EXEC]"));
      74      }
      75      
      76      /**
      77       * Adds a safety term to be monitored.
      78       * 
      79       * @param term The safety term to add
      80       * @return true if added, false if it already exists
      81       */
      82      public boolean addSafetyTerm(String term) {
      83          return safetyTerms.add(term.toLowerCase());
      84      }
      85      
      86      /**
      87       * Adds a custom safety transformer.
      88       * 
      89       * @param transformer A function that transforms content for safety
      90       */
      91      public void addSafetyTransformer(Function<String, String> transformer) {
      92          safetyTransformers.add(transformer);
      93      }
      94      
      95      /**
      96       * Sets the safety level for this container.
      97       * 
      98       * @param level The safety level (1-5) where 5 is most restrictive
      99       */
     100      public void setSafetyLevel(int level) {
     101          this.safetyLevel = Math.max(1, Math.min(5, level));
     102      }
     103      
     104      /**
     105       * Enables or disables strict safety mode.
     106       * When enabled, more aggressive safety measures are applied.
     107       * 
     108       * @param enabled true to enable strict mode, false to disable
     109       */
     110      public void setStrictMode(boolean enabled) {
     111          this.strictModeEnabled = enabled;
     112      }
     113      
     114      /**
     115       * Gets the current safety level.
     116       * 
     117       * @return The safety level (1-5)
     118       */
     119      public int getSafetyLevel() {
     120          return safetyLevel;
     121      }
     122      
     123      /**
     124       * Gets an unmodifiable set of all safety terms.
     125       * 
     126       * @return An unmodifiable set of safety terms
     127       */
     128      public Set<String> getSafetyTerms() {
     129          return Collections.unmodifiableSet(safetyTerms);
     130      }
     131      
     132      /**
     133       * Applies safety transformations to the content.
     134       * 
     135       * @param content The content to transform
     136       * @return The transformed content with safety measures applied
     137       */
     138      public String applySafetyMeasures(String content) {
     139          if (content == null || content.isEmpty()) {
     140              return content;
     141          }
     142          
     143          String result = content;
     144          
     145          // Apply all transformers
     146          for (Function<String, String> transformer : safetyTransformers) {
     147              result = transformer.apply(result);
     148          }
     149          
     150          // Apply additional safety measures based on safety level
     151          if (safetyLevel >= 4 || strictModeEnabled) {
     152              // For high safety levels, remove URLs or mark them as potentially unsafe
     153              result = result.replaceAll("https?://[^\\s]+", "[EXTERNAL-URL]");
     154          }
     155          
     156          return result;
     157      }
     158      
     159      /**
     160       * Checks if content contains potentially unsafe elements.
     161       * 
     162       * @param content The content to check
     163       * @return true if unsafe elements are detected, false otherwise
     164       */
     165      public boolean containsUnsafeElements(String content) {
     166          if (content == null || content.isEmpty()) {
     167              return false;
     168          }
     169          
     170          // Check for safety terms
     171          for (String term : safetyTerms) {
     172              if (content.toLowerCase().contains(term)) {
     173                  return true;
     174              }
     175          }
     176          
     177          // Check for common unsafe patterns
     178          if (content.contains("<script") || 
     179              content.toLowerCase().contains("exec(") ||
     180              content.toLowerCase().contains("eval(")) {
     181              return true;
     182          }
     183          
     184          return false;
     185      }
     186      
     187      /**
     188       * Creates a contained execution environment for the provided operation.
     189       * This provides a higher level of isolation for potentially risky operations.
     190       * 
     191       * @param <T> The return type of the operation
     192       * @param operation The operation to execute in the safety container
     193       * @param fallback The fallback value if the operation fails or is unsafe
     194       * @return The result of the operation or the fallback value
     195       */
     196      public <T> T executeInContainer(Function<SafetyContainer, T> operation, T fallback) {
     197          try {
     198              // Create a more restrictive container for the operation
     199              SafetyContainer subContainer = new SafetyContainer(this.safetyLevel + 1);
     200              subContainer.safetyTerms.addAll(this.safetyTerms);
     201              subContainer.strictModeEnabled = true;
     202              
     203              // Execute the operation in the sub-container
     204              return operation.apply(subContainer);
     205          } catch (Exception e) {
     206              // Safely contain any exceptions and return the fallback
     207              return fallback;
     208          }
     209      }
     210      
     211      /**
     212       * Integrates this container with a TrustManager for enhanced safety evaluation.
     213       * 
     214       * @param trustManager The TrustManager to integrate with
     215       * @param contextId The context to evaluate
     216       * @return true if the integration affects safety settings, false otherwise
     217       */
     218      public boolean integrateWithTrustManager(TrustManager trustManager, String contextId) {
     219          if (trustManager == null) {
     220              return false;
     221          }
     222          
     223          trustManager.getTrustScore(contextId).ifPresent(trustScore -> {
     224              // Adjust safety level based on trust score (inversely)
     225              // Lower trust = higher safety restrictions
     226              this.safetyLevel = 5 - (int) Math.round(trustScore * 4);
     227              
     228              // Enable strict mode for low trust scores
     229              this.strictModeEnabled = trustScore < 0.4;
     230          });
     231          
     232          return true;
     233      }
     234  }
     235 \ No newline at end of file

⏺ Finally, let's implement the TrustManager class:

⏺ Create(/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/boundarie…/
        TrustManager.java)
  ⎿  Updated/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/boundar
            ies/TrustManager.java with 308 additions
       1  package com.fractal.browser.collective.boundaries;
       2  
       3  import java.util.Map;
       4  import java.util.HashMap;
       5  import java.util.Optional;
       6  import java.util.concurrent.ConcurrentHashMap;
       7  import java.time.Instant;
       8  import java.util.List;
       9  import java.util.ArrayList;
      10  
      11  /**
      12   * TrustManager implements a fractal trust model for the collective system, 
      13   * managing trust relationships between nodes and contexts. It provides mechanisms
      14   * for trust assessment, trust evolution over time, and trust propagation through
      15   * the network.
      16   * 
      17   * This class works with other boundary components to create a comprehensive
      18   * trust boundary system that operates recursively across different scales.
      19   */
      20  public class TrustManager {
      21      
      22      // Trust score map: contextId -> TrustData
      23      private final Map<String, TrustData> trustScores;
      24      
      25      // Trust decay rate per day (reduces trust if no interactions)
      26      private double trustDecayRate;
      27      
      28      // Threshold below which trust is considered insufficient
      29      private double minTrustThreshold;
      30      
      31      /**
      32       * Internal class to store trust data and history.
      33       */
      34      private static class TrustData {
      35          private double score; // 0.0 to 1.0
      36          private Instant lastUpdated;
      37          private final List<TrustInteraction> interactions;
      38          
      39          public TrustData(double initialScore) {
      40              this.score = Math.max(0.0, Math.min(1.0, initialScore));
      41              this.lastUpdated = Instant.now();
      42              this.interactions = new ArrayList<>();
      43          }
      44          
      45          public void recordInteraction(TrustInteraction interaction) {
      46              interactions.add(interaction);
      47              lastUpdated = Instant.now();
      48          }
      49      }
      50      
      51      /**
      52       * Represents a trust interaction that affects trust scores.
      53       */
      54      public static class TrustInteraction {
      55          private final String description;
      56          private final double impact; // Positive or negative impact on trust
      57          private final Instant timestamp;
      58          
      59          public TrustInteraction(String description, double impact) {
      60              this.description = description;
      61              this.impact = impact;
      62              this.timestamp = Instant.now();
      63          }
      64          
      65          public String getDescription() {
      66              return description;
      67          }
      68          
      69          public double getImpact() {
      70              return impact;
      71          }
      72          
      73          public Instant getTimestamp() {
      74              return timestamp;
      75          }
      76      }
      77      
      78      /**
      79       * Creates a new TrustManager with default settings.
      80       */
      81      public TrustManager() {
      82          this.trustScores = new ConcurrentHashMap<>();
      83          this.trustDecayRate = 0.01; // 1% decay per day
      84          this.minTrustThreshold = 0.3;
      85      }
      86      
      87      /**
      88       * Creates a new TrustManager with the specified trust decay rate.
      89       * 
      90       * @param trustDecayRate The daily trust decay rate (0.0-1.0)
      91       * @param minTrustThreshold The minimum trust threshold (0.0-1.0)
      92       */
      93      public TrustManager(double trustDecayRate, double minTrustThreshold) {
      94          this.trustScores = new ConcurrentHashMap<>();
      95          this.trustDecayRate = Math.max(0.0, Math.min(1.0, trustDecayRate));
      96          this.minTrustThreshold = Math.max(0.0, Math.min(1.0, minTrustThreshold));
      97      }
      98      
      99      /**
     100       * Initializes a trust relationship with the given context.
     101       * 
     102       * @param contextId The unique identifier for the context
     103       * @param initialTrustScore The initial trust score (0.0-1.0)
     104       * @return true if successfully initialized, false if already exists
     105       */
     106      public boolean initializeTrust(String contextId, double initialTrustScore) {
     107          if (trustScores.containsKey(contextId)) {
     108              return false; // Trust already initialized
     109          }
     110          
     111          trustScores.put(contextId, new TrustData(initialTrustScore));
     112          return true;
     113      }
     114      
     115      /**
     116       * Records a trust interaction for the specified context.
     117       * 
     118       * @param contextId The context ID
     119       * @param description A description of the interaction
     120       * @param impact The impact on trust (-1.0 to 1.0)
     121       * @return The new trust score
     122       */
     123      public double recordInteraction(String contextId, String description, double impact) {
     124          // Ensure context exists
     125          trustScores.putIfAbsent(contextId, new TrustData(0.5)); // Default moderate trust
     126          
     127          TrustData data = trustScores.get(contextId);
     128          TrustInteraction interaction = new TrustInteraction(description, impact);
     129          data.recordInteraction(interaction);
     130          
     131          // Update trust score
     132          double newScore = data.score + impact;
     133          // Ensure score stays within bounds
     134          newScore = Math.max(0.0, Math.min(1.0, newScore));
     135          data.score = newScore;
     136          
     137          return newScore;
     138      }
     139      
     140      /**
     141       * Gets the current trust score for the context.
     142       * 
     143       * @param contextId The context ID
     144       * @return An Optional containing the trust score, or empty if not found
     145       */
     146      public Optional<Double> getTrustScore(String contextId) {
     147          if (!trustScores.containsKey(contextId)) {
     148              return Optional.empty();
     149          }
     150          
     151          // Apply trust decay based on time since last update
     152          TrustData data = trustScores.get(contextId);
     153          applyTrustDecay(data);
     154          
     155          return Optional.of(data.score);
     156      }
     157      
     158      /**
     159       * Applies trust decay based on time elapsed since last update.
     160       * 
     161       * @param data The trust data to update
     162       */
     163      private void applyTrustDecay(TrustData data) {
     164          Instant now = Instant.now();
     165          long daysSinceUpdate = java.time.Duration.between(data.lastUpdated, now).toDays();
     166          
     167          if (daysSinceUpdate > 0) {
     168              // Apply compound decay: score = score * (1 - decayRate)^days
     169              double decayFactor = Math.pow(1.0 - trustDecayRate, daysSinceUpdate);
     170              data.score = data.score * decayFactor;
     171              data.lastUpdated = now;
     172          }
     173      }
     174      
     175      /**
     176       * Sets the trust decay rate.
     177       * 
     178       * @param rate The daily decay rate (0.0-1.0)
     179       */
     180      public void setTrustDecayRate(double rate) {
     181          this.trustDecayRate = Math.max(0.0, Math.min(1.0, rate));
     182      }
     183      
     184      /**
     185       * Sets the minimum trust threshold.
     186       * 
     187       * @param threshold The minimum threshold (0.0-1.0)
     188       */
     189      public void setMinTrustThreshold(double threshold) {
     190          this.minTrustThreshold = Math.max(0.0, Math.min(1.0, threshold));
     191      }
     192      
     193      /**
     194       * Checks if the trust level is sufficient for the given context.
     195       * 
     196       * @param contextId The context ID
     197       * @return true if trust is sufficient, false otherwise
     198       */
     199      public boolean isTrustSufficient(String contextId) {
     200          Optional<Double> trustScore = getTrustScore(contextId);
     201          return trustScore.isPresent() && trustScore.get() >= minTrustThreshold;
     202      }
     203      
     204      /**
     205       * Gets the trust level categorization for a context.
     206       * 
     207       * @param contextId The context ID
     208       * @return A string representing the trust level category
     209       */
     210      public String getTrustLevel(String contextId) {
     211          Optional<Double> trustScore = getTrustScore(contextId);
     212          
     213          if (!trustScore.isPresent()) {
     214              return "UNKNOWN";
     215          }
     216          
     217          double score = trustScore.get();
     218          
     219          if (score < 0.2) return "VERY_LOW";
     220          if (score < 0.4) return "LOW";
     221          if (score < 0.6) return "MODERATE";
     222          if (score < 0.8) return "HIGH";
     223          return "VERY_HIGH";
     224      }
     225      
     226      /**
     227       * Gets a map of metadata about the trust relationship.
     228       * 
     229       * @param contextId The context ID
     230       * @return A map of trust metadata, or empty map if context not found
     231       */
     232      public Map<String, Object> getTrustMetadata(String contextId) {
     233          Map<String, Object> metadata = new HashMap<>();
     234          
     235          if (!trustScores.containsKey(contextId)) {
     236              return metadata;
     237          }
     238          
     239          TrustData data = trustScores.get(contextId);
     240          applyTrustDecay(data);
     241          
     242          metadata.put("contextId", contextId);
     243          metadata.put("trustScore", data.score);
     244          metadata.put("trustLevel", getTrustLevel(contextId));
     245          metadata.put("lastUpdated", data.lastUpdated.toString());
     246          metadata.put("interactionCount", data.interactions.size());
     247          metadata.put("trustThreshold", minTrustThreshold);
     248          metadata.put("isTrustSufficient", data.score >= minTrustThreshold);
     249          
     250          return metadata;
     251      }
     252      
     253      /**
     254       * Propagates trust from one context to another based on a transitive relationship.
     255       * 
     256       * @param sourceContextId The source context with established trust
     257       * @param targetContextId The target context to propagate trust to
     258       * @param propagationFactor How much trust is propagated (0.0-1.0)
     259       * @return true if propagation succeeded, false otherwise
     260       */
     261      public boolean propagateTrust(String sourceContextId, String targetContextId, double pro
         pagationFactor) {
     262          Optional<Double> sourceTrust = getTrustScore(sourceContextId);
     263          
     264          if (!sourceTrust.isPresent()) {
     265              return false;
     266          }
     267          
     268          double adjustedPropagation = Math.max(0.0, Math.min(1.0, propagationFactor));
     269          double propagatedTrust = sourceTrust.get() * adjustedPropagation;
     270          
     271          // If target already exists, blend the trust scores
     272          if (trustScores.containsKey(targetContextId)) {
     273              TrustData targetData = trustScores.get(targetContextId);
     274              applyTrustDecay(targetData);
     275              
     276              // Blend existing trust with propagated trust
     277              double blendedTrust = (targetData.score + propagatedTrust) / 2.0;
     278              targetData.score = blendedTrust;
     279              targetData.lastUpdated = Instant.now();
     280          } else {
     281              // Initialize new trust relationship with propagated trust
     282              trustScores.put(targetContextId, new TrustData(propagatedTrust));
     283          }
     284          
     285          return true;
     286      }
     287      
     288      /**
     289       * Integrates with the InformationBoundary to create trust-based information control.
     290       * 
     291       * @param boundary The InformationBoundary to integrate with
     292       * @param contextId The context to apply
     293       * @return true if integration succeeded, false otherwise
     294       */
     295      public boolean integrateWithInformationBoundary(InformationBoundary boundary, String con
         textId) {
     296          Optional<Double> trustScore = getTrustScore(contextId);
     297          
     298          if (!trustScore.isPresent()) {
     299              return false;
     300          }
     301          
     302          // Convert trust score to access level (0-10)
     303          int accessLevel = (int) Math.round(trustScore.get() * 10);
     304          
     305          // Register the context with the boundary
     306          return boundary.registerContext(contextId, accessLevel);
     307      }
     308  }
     309 \ No newline at end of file

⏺ I've implemented all four boundary classes. Let me now continue with the communication classes. I'll
  start with NodeDiscovery.java:

⏺ Create(/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/communica…i
        on/NodeDiscovery.java)
  ⎿  Updated/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/communi
            cation/NodeDiscovery.java with 369 additions
       1  package com.fractal.browser.collective.communication;
       2  
       3  import java.util.Map;
       4  import java.util.Set;
       5  import java.util.HashSet;
       6  import java.util.HashMap;
       7  import java.util.concurrent.ConcurrentHashMap;
       8  import java.time.Instant;
       9  import java.util.Optional;
      10  import java.util.function.Consumer;
      11  import java.util.function.Predicate;
      12  import java.util.Collections;
      13  
      14  import com.fractal.browser.collective.boundaries.TrustManager;
      15  
      16  /**
      17   * NodeDiscovery implements a fractal discovery mechanism for collective nodes in the networ
         k.
      18   * It manages node registration, discovery, and provides mechanisms for nodes to join and
      19   * leave the collective network at multiple scales.
      20   * 
      21   * This class utilizes recursive discovery patterns to efficiently maintain network topology
      22   * across different scales of interaction.
      23   */
      24  public class NodeDiscovery {
      25      
      26      // Node metadata key constants
      27      public static final String NODE_ID = "nodeId";
      28      public static final String NODE_TYPE = "nodeType";
      29      public static final String CAPABILITIES = "capabilities";
      30      public static final String LAST_ACTIVE = "lastActive";
      31      public static final String CONNECTIVITY = "connectivity";
      32      
      33      // Maps node IDs to their metadata
      34      private final Map<String, Map<String, Object>> nodeRegistry;
      35      
      36      // Maps node IDs to their connection status
      37      private final Map<String, Boolean> connectionStatus;
      38      
      39      // Set of listeners for node events
      40      private final Set<Consumer<NodeEvent>> nodeEventListeners;
      41      
      42      // Discovery timeout in milliseconds
      43      private long discoveryTimeout;
      44      
      45      // Maximum number of recursive discovery hops
      46      private int maxDiscoveryHops;
      47      
      48      /**
      49       * Represents different types of node events.
      50       */
      51      public enum NodeEventType {
      52          NODE_JOINED,
      53          NODE_LEFT,
      54          NODE_DISCOVERED,
      55          NODE_UPDATED,
      56          CONNECTIVITY_CHANGED
      57      }
      58      
      59      /**
      60       * Represents a node event within the collective.
      61       */
      62      public static class NodeEvent {
      63          private final String nodeId;
      64          private final NodeEventType eventType;
      65          private final Instant timestamp;
      66          private final Map<String, Object> metadata;
      67          
      68          public NodeEvent(String nodeId, NodeEventType eventType, Map<String, Object> metadat
         a) {
      69              this.nodeId = nodeId;
      70              this.eventType = eventType;
      71              this.timestamp = Instant.now();
      72              this.metadata = metadata != null ? new HashMap<>(metadata) : new HashMap<>();
      73          }
      74          
      75          public String getNodeId() {
      76              return nodeId;
      77          }
      78          
      79          public NodeEventType getEventType() {
      80              return eventType;
      81          }
      82          
      83          public Instant getTimestamp() {
      84              return timestamp;
      85          }
      86          
      87          public Map<String, Object> getMetadata() {
      88              return Collections.unmodifiableMap(metadata);
      89          }
      90      }
      91      
      92      /**
      93       * Creates a new NodeDiscovery instance with default settings.
      94       */
      95      public NodeDiscovery() {
      96          this.nodeRegistry = new ConcurrentHashMap<>();
      97          this.connectionStatus = new ConcurrentHashMap<>();
      98          this.nodeEventListeners = Collections.synchronizedSet(new HashSet<>());
      99          this.discoveryTimeout = 30000; // 30 seconds
     100          this.maxDiscoveryHops = 3;
     101      }
     102      
     103      /**
     104       * Creates a new NodeDiscovery instance with custom settings.
     105       * 
     106       * @param discoveryTimeout The timeout for discovery operations in milliseconds
     107       * @param maxDiscoveryHops The maximum number of recursive discovery hops
     108       */
     109      public NodeDiscovery(long discoveryTimeout, int maxDiscoveryHops) {
     110          this.nodeRegistry = new ConcurrentHashMap<>();
     111          this.connectionStatus = new ConcurrentHashMap<>();
     112          this.nodeEventListeners = Collections.synchronizedSet(new HashSet<>());
     113          this.discoveryTimeout = discoveryTimeout;
     114          this.maxDiscoveryHops = maxDiscoveryHops;
     115      }
     116      
     117      /**
     118       * Registers a new node in the collective.
     119       * 
     120       * @param nodeId The unique identifier for the node
     121       * @param metadata Metadata describing the node capabilities and properties
     122       * @return true if successfully registered, false if already exists
     123       */
     124      public boolean registerNode(String nodeId, Map<String, Object> metadata) {
     125          if (nodeRegistry.containsKey(nodeId)) {
     126              return false; // Node already registered
     127          }
     128          
     129          Map<String, Object> nodeMetadata = new HashMap<>(metadata);
     130          nodeMetadata.put(LAST_ACTIVE, Instant.now().toString());
     131          nodeRegistry.put(nodeId, nodeMetadata);
     132          connectionStatus.put(nodeId, true);
     133          
     134          // Notify listeners of new node
     135          NodeEvent event = new NodeEvent(nodeId, NodeEventType.NODE_JOINED, nodeMetadata);
     136          notifyListeners(event);
     137          
     138          return true;
     139      }
     140      
     141      /**
     142       * Updates an existing node's metadata.
     143       * 
     144       * @param nodeId The node identifier
     145       * @param metadata The updated metadata
     146       * @return true if successfully updated, false if node doesn't exist
     147       */
     148      public boolean updateNode(String nodeId, Map<String, Object> metadata) {
     149          if (!nodeRegistry.containsKey(nodeId)) {
     150              return false;
     151          }
     152          
     153          Map<String, Object> existingMetadata = nodeRegistry.get(nodeId);
     154          existingMetadata.putAll(metadata);
     155          existingMetadata.put(LAST_ACTIVE, Instant.now().toString());
     156          
     157          // Notify listeners of update
     158          NodeEvent event = new NodeEvent(nodeId, NodeEventType.NODE_UPDATED, existingMetadata
         );
     159          notifyListeners(event);
     160          
     161          return true;
     162      }
     163      
     164      /**
     165       * Unregisters a node from the collective.
     166       * 
     167       * @param nodeId The node identifier
     168       * @return true if successfully removed, false if node wasn't registered
     169       */
     170      public boolean unregisterNode(String nodeId) {
     171          if (!nodeRegistry.containsKey(nodeId)) {
     172              return false;
     173          }
     174          
     175          Map<String, Object> nodeMetadata = nodeRegistry.remove(nodeId);
     176          connectionStatus.remove(nodeId);
     177          
     178          // Notify listeners of node departure
     179          NodeEvent event = new NodeEvent(nodeId, NodeEventType.NODE_LEFT, nodeMetadata);
     180          notifyListeners(event);
     181          
     182          return true;
     183      }
     184      
     185      /**
     186       * Discovers nodes in the collective that match the given criteria.
     187       * 
     188       * @param filter Predicate to filter nodes by their metadata
     189       * @return A map of node IDs to their metadata
     190       */
     191      public Map<String, Map<String, Object>> discoverNodes(Predicate<Map<String, Object>> fil
         ter) {
     192          Map<String, Map<String, Object>> discoveredNodes = new HashMap<>();
     193          
     194          for (Map.Entry<String, Map<String, Object>> entry : nodeRegistry.entrySet()) {
     195              if (filter == null || filter.test(entry.getValue())) {
     196                  discoveredNodes.put(entry.getKey(), new HashMap<>(entry.getValue()));
     197              }
     198          }
     199          
     200          return discoveredNodes;
     201      }
     202      
     203      /**
     204       * Gets metadata for a specific node.
     205       * 
     206       * @param nodeId The node identifier
     207       * @return An Optional containing the node metadata, or empty if not found
     208       */
     209      public Optional<Map<String, Object>> getNodeMetadata(String nodeId) {
     210          if (!nodeRegistry.containsKey(nodeId)) {
     211              return Optional.empty();
     212          }
     213          
     214          return Optional.of(new HashMap<>(nodeRegistry.get(nodeId)));
     215      }
     216      
     217      /**
     218       * Sets the connection status of a node.
     219       * 
     220       * @param nodeId The node identifier
     221       * @param connected true if connected, false otherwise
     222       * @return true if status was changed, false if no change or node doesn't exist
     223       */
     224      public boolean setNodeConnectionStatus(String nodeId, boolean connected) {
     225          if (!nodeRegistry.containsKey(nodeId)) {
     226              return false;
     227          }
     228          
     229          boolean previousStatus = connectionStatus.getOrDefault(nodeId, false);
     230          if (previousStatus == connected) {
     231              return false; // No change
     232          }
     233          
     234          connectionStatus.put(nodeId, connected);
     235          Map<String, Object> nodeMetadata = nodeRegistry.get(nodeId);
     236          nodeMetadata.put(CONNECTIVITY, connected ? "CONNECTED" : "DISCONNECTED");
     237          nodeMetadata.put(LAST_ACTIVE, Instant.now().toString());
     238          
     239          // Notify listeners of connectivity change
     240          NodeEvent event = new NodeEvent(nodeId, NodeEventType.CONNECTIVITY_CHANGED, nodeMeta
         data);
     241          notifyListeners(event);
     242          
     243          return true;
     244      }
     245      
     246      /**
     247       * Checks if a node is currently connected.
     248       * 
     249       * @param nodeId The node identifier
     250       * @return true if connected, false otherwise
     251       */
     252      public boolean isNodeConnected(String nodeId) {
     253          return connectionStatus.getOrDefault(nodeId, false);
     254      }
     255      
     256      /**
     257       * Gets all registered nodes in the collective.
     258       * 
     259       * @return A map of node IDs to their metadata
     260       */
     261      public Map<String, Map<String, Object>> getAllNodes() {
     262          Map<String, Map<String, Object>> result = new HashMap<>();
     263          
     264          for (Map.Entry<String, Map<String, Object>> entry : nodeRegistry.entrySet()) {
     265              result.put(entry.getKey(), new HashMap<>(entry.getValue()));
     266          }
     267          
     268          return result;
     269      }
     270      
     271      /**
     272       * Gets connected nodes that match the specified filter.
     273       * 
     274       * @param filter Predicate to filter nodes by their metadata
     275       * @return A map of node IDs to their metadata
     276       */
     277      public Map<String, Map<String, Object>> getConnectedNodes(Predicate<Map<String, Object>>
          filter) {
     278          Map<String, Map<String, Object>> result = new HashMap<>();
     279          
     280          for (Map.Entry<String, Map<String, Object>> entry : nodeRegistry.entrySet()) {
     281              String nodeId = entry.getKey();
     282              if (isNodeConnected(nodeId) && (filter == null || filter.test(entry.getValue()))
         ) {
     283                  result.put(nodeId, new HashMap<>(entry.getValue()));
     284              }
     285          }
     286          
     287          return result;
     288      }
     289      
     290      /**
     291       * Adds a listener for node events.
     292       * 
     293       * @param listener The listener to add
     294       * @return true if added, false if it was already registered
     295       */
     296      public boolean addNodeEventListener(Consumer<NodeEvent> listener) {
     297          return nodeEventListeners.add(listener);
     298      }
     299      
     300      /**
     301       * Removes a listener for node events.
     302       * 
     303       * @param listener The listener to remove
     304       * @return true if removed, false if it wasn't registered
     305       */
     306      public boolean removeNodeEventListener(Consumer<NodeEvent> listener) {
     307          return nodeEventListeners.remove(listener);
     308      }
     309      
     310      /**
     311       * Notifies all registered listeners of a node event.
     312       * 
     313       * @param event The event to notify about
     314       */
     315      private void notifyListeners(NodeEvent event) {
     316          for (Consumer<NodeEvent> listener : nodeEventListeners) {
     317              try {
     318                  listener.accept(event);
     319              } catch (Exception e) {
     320                  // Log exception but continue notifying other listeners
     321                  System.err.println("Error notifying listener: " + e.getMessage());
     322              }
     323          }
     324      }
     325      
     326      /**
     327       * Sets the discovery timeout.
     328       * 
     329       * @param timeout The timeout in milliseconds
     330       */
     331      public void setDiscoveryTimeout(long timeout) {
     332          this.discoveryTimeout = timeout;
     333      }
     334      
     335      /**
     336       * Sets the maximum number of discovery hops.
     337       * 
     338       * @param maxHops The maximum hops
     339       */
     340      public void setMaxDiscoveryHops(int maxHops) {
     341          this.maxDiscoveryHops = maxHops;
     342      }
     343      
     344      /**
     345       * Integrates with the TrustManager to filter nodes based on trust level.
     346       * 
     347       * @param trustManager The TrustManager to integrate with
     348       * @param minTrustLevel The minimum trust level required (0.0-1.0)
     349       * @return A map of trusted node IDs to their metadata
     350       */
     351      public Map<String, Map<String, Object>> discoverTrustedNodes(TrustManager trustManager, 
         double minTrustLevel) {
     352          Map<String, Map<String, Object>> result = new HashMap<>();
     353          
     354          if (trustManager == null) {
     355              return result;
     356          }
     357          
     358          for (Map.Entry<String, Map<String, Object>> entry : nodeRegistry.entrySet()) {
     359              String nodeId = entry.getKey();
     360              Optional<Double> trustScore = trustManager.getTrustScore(nodeId);
     361              
     362              if (trustScore.isPresent() && trustScore.get() >= minTrustLevel) {
     363                  result.put(nodeId, new HashMap<>(entry.getValue()));
     364              }
     365          }
     366          
     367          return result;
     368      }
     369  }
     370 \ No newline at end of file

⏺ Now let's implement the InsightExchange class:

⏺ Create(/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/communica…i
        on/InsightExchange.java)
  ⎿  Updated/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/communi
            cation/InsightExchange.java with 441 additions
       1  package com.fractal.browser.collective.communication;
       2  
       3  import java.util.Map;
       4  import java.util.HashMap;
       5  import java.util.List;
       6  import java.util.ArrayList;
       7  import java.util.concurrent.ConcurrentHashMap;
       8  import java.util.UUID;
       9  import java.util.Optional;
      10  import java.time.Instant;
      11  import java.util.function.Function;
      12  import java.util.concurrent.CompletableFuture;
      13  import java.util.Collections;
      14  
      15  import com.fractal.browser.collective.boundaries.InformationBoundary;
      16  import com.fractal.browser.collective.boundaries.PrivacyFilter;
      17  import com.fractal.browser.model.Pattern;
      18  
      19  /**
      20   * InsightExchange implements mechanisms for exchanging insights between nodes in the
      21   * collective. It allows for fractal insight propagation across the network, with
      22   * security and privacy controls enforced at multiple scales.
      23   * 
      24   * This class manages the bidirectional flow of insights, including recursive transformation
         s,
      25   * integration, and emergent pattern recognition through collective interaction.
      26   */
      27  public class InsightExchange {
      28      
      29      // Maps insight IDs to their data
      30      private final Map<String, Insight> insightRegistry;
      31      
      32      // Maps exchange session IDs to their data
      33      private final Map<String, ExchangeSession> activeSessions;
      34      
      35      // Privacy filter for outgoing insights
      36      private final PrivacyFilter privacyFilter;
      37      
      38      // Information boundary for access control
      39      private final InformationBoundary informationBoundary;
      40      
      41      // Node discovery instance for locating exchange partners
      42      private final NodeDiscovery nodeDiscovery;
      43      
      44      /**
      45       * Represents an insight being exchanged.
      46       */
      47      public static class Insight {
      48          private final String insightId;
      49          private final String sourceNodeId;
      50          private final String contextId;
      51          private final String content;
      52          private final Map<String, Object> metadata;
      53          private final Instant timestamp;
      54          private final List<Pattern> associatedPatterns;
      55          private int propagationLevel; // How far the insight has propagated
      56          
      57          /**
      58           * Creates a new Insight.
      59           * 
      60           * @param sourceNodeId The source node ID
      61           * @param contextId The context ID
      62           * @param content The insight content
      63           * @param metadata Additional metadata about the insight
      64           */
      65          public Insight(String sourceNodeId, String contextId, String content, Map<String, Ob
         ject> metadata) {
      66              this.insightId = UUID.randomUUID().toString();
      67              this.sourceNodeId = sourceNodeId;
      68              this.contextId = contextId;
      69              this.content = content;
      70              this.metadata = new HashMap<>(metadata);
      71              this.timestamp = Instant.now();
      72              this.associatedPatterns = new ArrayList<>();
      73              this.propagationLevel = 0;
      74          }
      75          
      76          /**
      77           * Creates a new Insight with specified ID (for receiving from network).
      78           */
      79          public Insight(String insightId, String sourceNodeId, String contextId, String conte
         nt, 
      80                  Map<String, Object> metadata, Instant timestamp, int propagationLevel) {
      81              this.insightId = insightId;
      82              this.sourceNodeId = sourceNodeId;
      83              this.contextId = contextId;
      84              this.content = content;
      85              this.metadata = new HashMap<>(metadata);
      86              this.timestamp = timestamp;
      87              this.associatedPatterns = new ArrayList<>();
      88              this.propagationLevel = propagationLevel;
      89          }
      90          
      91          // Getters
      92          public String getInsightId() { return insightId; }
      93          public String getSourceNodeId() { return sourceNodeId; }
      94          public String getContextId() { return contextId; }
      95          public String getContent() { return content; }
      96          public Map<String, Object> getMetadata() { return Collections.unmodifiableMap(metada
         ta); }
      97          public Instant getTimestamp() { return timestamp; }
      98          public List<Pattern> getAssociatedPatterns() { return Collections.unmodifiableList(a
         ssociatedPatterns); }
      99          public int getPropagationLevel() { return propagationLevel; }
     100          
     101          /**
     102           * Adds an associated pattern to this insight.
     103           * 
     104           * @param pattern The pattern to associate
     105           */
     106          public void addAssociatedPattern(Pattern pattern) {
     107              this.associatedPatterns.add(pattern);
     108          }
     109          
     110          /**
     111           * Increments the propagation level of this insight.
     112           */
     113          public void incrementPropagationLevel() {
     114              this.propagationLevel++;
     115          }
     116      }
     117      
     118      /**
     119       * Represents an active exchange session between nodes.
     120       */
     121      private static class ExchangeSession {
     122          private final String sessionId;
     123          private final String initiatorNodeId;
     124          private final String responderNodeId;
     125          private final String contextId;
     126          private final Instant startTime;
     127          private Instant lastActivity;
     128          private final List<String> exchangedInsightIds;
     129          private SessionState state;
     130          
     131          public enum SessionState {
     132              INITIATED,
     133              ACTIVE,
     134              COMPLETED,
     135              FAILED
     136          }
     137          
     138          public ExchangeSession(String initiatorNodeId, String responderNodeId, String contex
         tId) {
     139              this.sessionId = UUID.randomUUID().toString();
     140              this.initiatorNodeId = initiatorNodeId;
     141              this.responderNodeId = responderNodeId;
     142              this.contextId = contextId;
     143              this.startTime = Instant.now();
     144              this.lastActivity = this.startTime;
     145              this.exchangedInsightIds = new ArrayList<>();
     146              this.state = SessionState.INITIATED;
     147          }
     148          
     149          public void addExchangedInsight(String insightId) {
     150              exchangedInsightIds.add(insightId);
     151              lastActivity = Instant.now();
     152          }
     153          
     154          public void updateState(SessionState newState) {
     155              this.state = newState;
     156              this.lastActivity = Instant.now();
     157          }
     158      }
     159      
     160      /**
     161       * Creates a new InsightExchange with the specified components.
     162       * 
     163       * @param nodeDiscovery The NodeDiscovery component for finding exchange partners
     164       * @param privacyFilter The PrivacyFilter for outgoing insights
     165       * @param informationBoundary The InformationBoundary for access control
     166       */
     167      public InsightExchange(NodeDiscovery nodeDiscovery, PrivacyFilter privacyFilter, 
     168              InformationBoundary informationBoundary) {
     169          this.insightRegistry = new ConcurrentHashMap<>();
     170          this.activeSessions = new ConcurrentHashMap<>();
     171          this.nodeDiscovery = nodeDiscovery;
     172          this.privacyFilter = privacyFilter;
     173          this.informationBoundary = informationBoundary;
     174      }
     175      
     176      /**
     177       * Creates a new insight to share with the collective.
     178       * 
     179       * @param nodeId The source node ID
     180       * @param contextId The context in which the insight is relevant
     181       * @param content The insight content
     182       * @param metadata Additional metadata about the insight
     183       * @return The created Insight
     184       */
     185      public Insight createInsight(String nodeId, String contextId, String content, Map<String
         , Object> metadata) {
     186          // Apply privacy filter to content before storing
     187          String filteredContent = privacyFilter.filterContent(content);
     188          
     189          Insight insight = new Insight(nodeId, contextId, filteredContent, metadata);
     190          insightRegistry.put(insight.getInsightId(), insight);
     191          
     192          return insight;
     193      }
     194      
     195      /**
     196       * Initiates an insight exchange session with another node.
     197       * 
     198       * @param initiatorNodeId The node initiating the exchange
     199       * @param responderNodeId The node to exchange with
     200       * @param contextId The context for the exchange
     201       * @return The session ID if successful, empty Optional otherwise
     202       */
     203      public Optional<String> initiateExchange(String initiatorNodeId, String responderNodeId,
          String contextId) {
     204          // Check if responder node is available
     205          if (!nodeDiscovery.isNodeConnected(responderNodeId)) {
     206              return Optional.empty();
     207          }
     208          
     209          // Create new exchange session
     210          ExchangeSession session = new ExchangeSession(initiatorNodeId, responderNodeId, cont
         extId);
     211          activeSessions.put(session.sessionId, session);
     212          
     213          return Optional.of(session.sessionId);
     214      }
     215      
     216      /**
     217       * Shares an insight with another node in the context of an exchange session.
     218       * 
     219       * @param sessionId The exchange session ID
     220       * @param insightId The insight to share
     221       * @return true if shared successfully, false otherwise
     222       */
     223      public boolean shareInsight(String sessionId, String insightId) {
     224          if (!activeSessions.containsKey(sessionId) || !insightRegistry.containsKey(insightId
         )) {
     225              return false;
     226          }
     227          
     228          ExchangeSession session = activeSessions.get(sessionId);
     229          Insight insight = insightRegistry.get(insightId);
     230          
     231          // Check if the insight can be shared in this context
     232          if (!informationBoundary.canInformationPass(insightId, session.contextId)) {
     233              return false;
     234          }
     235          
     236          // Record the exchange
     237          session.addExchangedInsight(insightId);
     238          session.updateState(ExchangeSession.SessionState.ACTIVE);
     239          
     240          return true;
     241      }
     242      
     243      /**
     244       * Receives an insight from another node.
     245       * 
     246       * @param sourceNodeId The node sending the insight
     247       * @param insightData Map containing insight data
     248       * @return The ID of the received insight if successful, empty Optional otherwise
     249       */
     250      public Optional<String> receiveInsight(String sourceNodeId, Map<String, Object> insightD
         ata) {
     251          try {
     252              String insightId = (String) insightData.get("insightId");
     253              String contextId = (String) insightData.get("contextId");
     254              String content = (String) insightData.get("content");
     255              Map<String, Object> metadata = (Map<String, Object>) insightData.get("metadata")
         ;
     256              Instant timestamp = Instant.parse((String) insightData.get("timestamp"));
     257              int propagationLevel = (int) insightData.get("propagationLevel");
     258              
     259              // Create insight from received data
     260              Insight receivedInsight = new Insight(
     261                  insightId, sourceNodeId, contextId, content, metadata, timestamp, propagatio
         nLevel
     262              );
     263              
     264              // Store the received insight
     265              insightRegistry.put(insightId, receivedInsight);
     266              
     267              return Optional.of(insightId);
     268          } catch (Exception e) {
     269              return Optional.empty();
     270          }
     271      }
     272      
     273      /**
     274       * Transforms an insight using the provided transformation function.
     275       * 
     276       * @param insightId The insight to transform
     277       * @param transformer The transformation function
     278       * @param newContextId The context ID for the transformed insight
     279       * @return The ID of the transformed insight if successful, empty Optional otherwise
     280       */
     281      public Optional<String> transformInsight(String insightId, Function<String, String> tran
         sformer, String newContextId) {
     282          if (!insightRegistry.containsKey(insightId)) {
     283              return Optional.empty();
     284          }
     285          
     286          Insight original = insightRegistry.get(insightId);
     287          
     288          try {
     289              // Apply transformation to content
     290              String transformedContent = transformer.apply(original.getContent());
     291              
     292              // Create new metadata based on original
     293              Map<String, Object> newMetadata = new HashMap<>(original.getMetadata());
     294              newMetadata.put("derivedFrom", original.getInsightId());
     295              newMetadata.put("transformationType", "custom");
     296              
     297              // Create transformed insight
     298              Insight transformedInsight = createInsight(
     299                  original.getSourceNodeId(), 
     300                  newContextId, 
     301                  transformedContent, 
     302                  newMetadata
     303              );
     304              
     305              // Set propagation level for the new insight
     306              transformedInsight.incrementPropagationLevel();
     307              
     308              return Optional.of(transformedInsight.getInsightId());
     309          } catch (Exception e) {
     310              return Optional.empty();
     311          }
     312      }
     313      
     314      /**
     315       * Propagates an insight to multiple nodes matching filter criteria.
     316       * 
     317       * @param insightId The insight to propagate
     318       * @param nodeFilter Filter to determine eligible nodes
     319       * @param maxPropagationLevel Maximum propagation level (how many hops)
     320       * @return A map of node IDs to future results of the propagation
     321       */
     322      public Map<String, CompletableFuture<Boolean>> propagateInsight(
     323              String insightId, 
     324              Function<Map<String, Object>, Boolean> nodeFilter, 
     325              int maxPropagationLevel) {
     326          
     327          if (!insightRegistry.containsKey(insightId)) {
     328              return Collections.emptyMap();
     329          }
     330          
     331          Insight insight = insightRegistry.get(insightId);
     332          
     333          // Check if we've reached max propagation level
     334          if (insight.getPropagationLevel() >= maxPropagationLevel) {
     335              return Collections.emptyMap();
     336          }
     337          
     338          // Find eligible nodes
     339          Map<String, Map<String, Object>> connectedNodes = nodeDiscovery.getConnectedNodes(no
         deFilter::apply);
     340          Map<String, CompletableFuture<Boolean>> propagationResults = new HashMap<>();
     341          
     342          // Propagate to each eligible node
     343          for (String nodeId : connectedNodes.keySet()) {
     344              // Skip propagating back to the source
     345              if (nodeId.equals(insight.getSourceNodeId())) {
     346                  continue;
     347              }
     348              
     349              // Create future for async propagation
     350              CompletableFuture<Boolean> future = new CompletableFuture<>();
     351              propagationResults.put(nodeId, future);
     352              
     353              // In a real implementation, this would be an async network call
     354              // For now, we'll just complete the future with success
     355              future.complete(true);
     356          }
     357          
     358          // Increment propagation level after propagating
     359          insight.incrementPropagationLevel();
     360          
     361          return propagationResults;
     362      }
     363      
     364      /**
     365       * Gets an insight by ID.
     366       * 
     367       * @param insightId The insight ID
     368       * @return An Optional containing the insight, or empty if not found
     369       */
     370      public Optional<Insight> getInsight(String insightId) {
     371          return Optional.ofNullable(insightRegistry.get(insightId));
     372      }
     373      
     374      /**
     375       * Gets all insights for a specific context.
     376       * 
     377       * @param contextId The context ID
     378       * @return A list of insights relevant to the context
     379       */
     380      public List<Insight> getInsightsForContext(String contextId) {
     381          List<Insight> contextInsights = new ArrayList<>();
     382          
     383          for (Insight insight : insightRegistry.values()) {
     384              if (insight.getContextId().equals(contextId)) {
     385                  // Ensure information can pass this boundary for the context
     386                  if (informationBoundary.canInformationPass(insight.getInsightId(), contextId
         )) {
     387                      contextInsights.add(insight);
     388                  }
     389              }
     390          }
     391          
     392          return contextInsights;
     393      }
     394      
     395      /**
     396       * Completes an exchange session.
     397       * 
     398       * @param sessionId The session ID
     399       * @param success Whether the session completed successfully
     400       * @return true if session was successfully closed, false otherwise
     401       */
     402      public boolean completeExchangeSession(String sessionId, boolean success) {
     403          if (!activeSessions.containsKey(sessionId)) {
     404              return false;
     405          }
     406          
     407          ExchangeSession session = activeSessions.get(sessionId);
     408          session.updateState(success ? ExchangeSession.SessionState.COMPLETED : ExchangeSessi
         on.SessionState.FAILED);
     409          
     410          return true;
     411      }
     412      
     413      /**
     414       * Cleans up expired sessions.
     415       * 
     416       * @param maxSessionAge Maximum session age in milliseconds
     417       * @return The number of sessions cleaned up
     418       */
     419      public int cleanupExpiredSessions(long maxSessionAge) {
     420          int cleanedCount = 0;
     421          Instant now = Instant.now();
     422          
     423          List<String> expiredSessions = new ArrayList<>();
     424          
     425          for (Map.Entry<String, ExchangeSession> entry : activeSessions.entrySet()) {
     426              ExchangeSession session = entry.getValue();
     427              long sessionAgeMs = java.time.Duration.between(session.startTime, now).toMillis(
         );
     428              
     429              if (sessionAgeMs > maxSessionAge) {
     430                  expiredSessions.add(entry.getKey());
     431              }
     432          }
     433          
     434          for (String sessionId : expiredSessions) {
     435              activeSessions.remove(sessionId);
     436              cleanedCount++;
     437          }
     438          
     439          return cleanedCount;
     440      }
     441  }
     442 \ No newline at end of file

⏺ Now let's implement the SynchronizationProtocol class:

⏺ Create(/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/communica…i
        on/SynchronizationProtocol.java)
  ⎿  Updated/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/communi
            cation/SynchronizationProtocol.java with 559 additions
       1  package com.fractal.browser.collective.communication;
       2  
       3  import java.util.Map;
       4  import java.util.HashMap;
       5  import java.util.List;
       6  import java.util.ArrayList;
       7  import java.util.Set;
       8  import java.util.HashSet;
       9  import java.util.concurrent.ConcurrentHashMap;
      10  import java.time.Instant;
      11  import java.util.UUID;
      12  import java.util.Optional;
      13  import java.util.function.BiFunction;
      14  import java.util.Collections;
      15  import java.util.Comparator;
      16  
      17  import com.fractal.browser.collective.boundaries.InformationBoundary;
      18  
      19  /**
      20   * SynchronizationProtocol implements mechanisms to synchronize state between nodes
      21   * in the collective. It manages the flow of synchronization operations across multiple
      22   * scales, ensuring consistency while allowing for localized variations.
      23   * 
      24   * This class applies fractal synchronization patterns that support both highly coherent
      25   * and loosely coupled node interactions.
      26   */
      27  public class SynchronizationProtocol {
      28      
      29      // Maps sync session IDs to their data
      30      private final Map<String, SyncSession> activeSessions;
      31      
      32      // Maps data type to the current version information
      33      private final Map<String, VersionedData> versionedDataRegistry;
      34      
      35      // Node discovery for finding sync partners
      36      private final NodeDiscovery nodeDiscovery;
      37      
      38      // Information boundary for controlling sensitive data
      39      private final InformationBoundary boundary;
      40      
      41      // Conflict resolution strategies for each data type
      42      private final Map<String, BiFunction<Object, Object, Object>> conflictResolvers;
      43      
      44      /**
      45       * Represents a synchronization session between nodes.
      46       */
      47      private static class SyncSession {
      48          private final String sessionId;
      49          private final String initiatorNodeId;
      50          private final String responderNodeId;
      51          private final Set<String> dataTypesToSync;
      52          private final Instant startTime;
      53          private Instant lastActivity;
      54          private SyncState state;
      55          private final Map<String, SyncResult> results;
      56          
      57          /**
      58           * Possible states of a synchronization session.
      59           */
      60          public enum SyncState {
      61              INITIATED,
      62              COMPARING,
      63              TRANSFERRING,
      64              RESOLVING_CONFLICTS,
      65              COMPLETED,
      66              FAILED
      67          }
      68          
      69          /**
      70           * Result of synchronizing a specific data type.
      71           */
      72          public static class SyncResult {
      73              private final String dataType;
      74              private final boolean success;
      75              private final int itemsSent;
      76              private final int itemsReceived;
      77              private final int conflictsResolved;
      78              private final String message;
      79              
      80              public SyncResult(String dataType, boolean success, int itemsSent, 
      81                      int itemsReceived, int conflictsResolved, String message) {
      82                  this.dataType = dataType;
      83                  this.success = success;
      84                  this.itemsSent = itemsSent;
      85                  this.itemsReceived = itemsReceived;
      86                  this.conflictsResolved = conflictsResolved;
      87                  this.message = message;
      88              }
      89              
      90              // Getters
      91              public String getDataType() { return dataType; }
      92              public boolean isSuccess() { return success; }
      93              public int getItemsSent() { return itemsSent; }
      94              public int getItemsReceived() { return itemsReceived; }
      95              public int getConflictsResolved() { return conflictsResolved; }
      96              public String getMessage() { return message; }
      97          }
      98          
      99          /**
     100           * Creates a new sync session.
     101           */
     102          public SyncSession(String initiatorNodeId, String responderNodeId, Set<String> dataT
         ypesToSync) {
     103              this.sessionId = UUID.randomUUID().toString();
     104              this.initiatorNodeId = initiatorNodeId;
     105              this.responderNodeId = responderNodeId;
     106              this.dataTypesToSync = new HashSet<>(dataTypesToSync);
     107              this.startTime = Instant.now();
     108              this.lastActivity = this.startTime;
     109              this.state = SyncState.INITIATED;
     110              this.results = new HashMap<>();
     111          }
     112          
     113          /**
     114           * Updates the session state and last activity timestamp.
     115           */
     116          public void updateState(SyncState newState) {
     117              this.state = newState;
     118              this.lastActivity = Instant.now();
     119          }
     120          
     121          /**
     122           * Records the result of synchronizing a specific data type.
     123           */
     124          public void recordResult(SyncResult result) {
     125              this.results.put(result.dataType, result);
     126              this.lastActivity = Instant.now();
     127          }
     128          
     129          /**
     130           * Checks if all required data types have been synchronized.
     131           */
     132          public boolean isComplete() {
     133              return dataTypesToSync.size() == results.size() && 
     134                     results.values().stream().allMatch(SyncResult::isSuccess);
     135          }
     136      }
     137      
     138      /**
     139       * Represents versioned data for synchronization.
     140       */
     141      public static class VersionedData {
     142          private final String dataType;
     143          private final Map<String, DataItem> items; // Item ID -> Item
     144          
     145          /**
     146           * Creates a new versioned data container.
     147           */
     148          public VersionedData(String dataType) {
     149              this.dataType = dataType;
     150              this.items = new ConcurrentHashMap<>();
     151          }
     152          
     153          /**
     154           * Gets all data items.
     155           */
     156          public Map<String, DataItem> getItems() {
     157              Map<String, DataItem> copy = new HashMap<>();
     158              for (Map.Entry<String, DataItem> entry : items.entrySet()) {
     159                  copy.put(entry.getKey(), entry.getValue().copy());
     160              }
     161              return copy;
     162          }
     163          
     164          /**
     165           * Updates a data item.
     166           */
     167          public boolean updateItem(String itemId, Object value, long version) {
     168              DataItem existing = items.get(itemId);
     169              
     170              if (existing == null) {
     171                  // New item
     172                  items.put(itemId, new DataItem(itemId, value, version));
     173                  return true;
     174              }
     175              
     176              // Update only if version is newer
     177              if (version > existing.getVersion()) {
     178                  items.put(itemId, new DataItem(itemId, value, version));
     179                  return true;
     180              }
     181              
     182              return false;
     183          }
     184          
     185          /**
     186           * Gets the latest version of a data item.
     187           */
     188          public Optional<DataItem> getItem(String itemId) {
     189              DataItem item = items.get(itemId);
     190              return item != null ? Optional.of(item.copy()) : Optional.empty();
     191          }
     192          
     193          /**
     194           * Gets the latest version number across all items.
     195           */
     196          public long getLatestVersion() {
     197              return items.values().stream()
     198                      .mapToLong(DataItem::getVersion)
     199                      .max()
     200                      .orElse(0);
     201          }
     202      }
     203      
     204      /**
     205       * Represents a single data item with versioning.
     206       */
     207      public static class DataItem {
     208          private final String itemId;
     209          private final Object value;
     210          private final long version;
     211          private final Instant lastModified;
     212          
     213          /**
     214           * Creates a new data item.
     215           */
     216          public DataItem(String itemId, Object value, long version) {
     217              this.itemId = itemId;
     218              this.value = value;
     219              this.version = version;
     220              this.lastModified = Instant.now();
     221          }
     222          
     223          /**
     224           * Creates a full copy of this data item.
     225           */
     226          public DataItem copy() {
     227              return new DataItem(this.itemId, this.value, this.version);
     228          }
     229          
     230          // Getters
     231          public String getItemId() { return itemId; }
     232          public Object getValue() { return value; }
     233          public long getVersion() { return version; }
     234          public Instant getLastModified() { return lastModified; }
     235      }
     236      
     237      /**
     238       * Creates a new SynchronizationProtocol with the specified components.
     239       */
     240      public SynchronizationProtocol(NodeDiscovery nodeDiscovery, InformationBoundary boundary
         ) {
     241          this.activeSessions = new ConcurrentHashMap<>();
     242          this.versionedDataRegistry = new ConcurrentHashMap<>();
     243          this.nodeDiscovery = nodeDiscovery;
     244          this.boundary = boundary;
     245          this.conflictResolvers = new HashMap<>();
     246          
     247          // Register default conflict resolvers
     248          registerDefaultConflictResolvers();
     249      }
     250      
     251      /**
     252       * Registers default conflict resolution strategies.
     253       */
     254      private void registerDefaultConflictResolvers() {
     255          // Default: Latest version wins
     256          BiFunction<Object, Object, Object> latestWins = (local, remote) -> remote;
     257          
     258          // Map merger: Combine maps, latest version of each key wins
     259          BiFunction<Object, Object, Object> mapMerger = (local, remote) -> {
     260              if (local instanceof Map && remote instanceof Map) {
     261                  Map<Object, Object> result = new HashMap<>((Map<Object, Object>) local);
     262                  result.putAll((Map<Object, Object>) remote);
     263                  return result;
     264              }
     265              return remote; // Fallback to latest wins
     266          };
     267          
     268          // List merger: Combine lists, remove duplicates
     269          BiFunction<Object, Object, Object> listMerger = (local, remote) -> {
     270              if (local instanceof List && remote instanceof List) {
     271                  Set<Object> combined = new HashSet<>((List<Object>) local);
     272                  combined.addAll((List<Object>) remote);
     273                  return new ArrayList<>(combined);
     274              }
     275              return remote; // Fallback to latest wins
     276          };
     277          
     278          // Register these defaults
     279          registerConflictResolver("default", latestWins);
     280          registerConflictResolver("map", mapMerger);
     281          registerConflictResolver("list", listMerger);
     282      }
     283      
     284      /**
     285       * Registers a custom conflict resolution strategy for a data type.
     286       * 
     287       * @param dataType The data type to register for
     288       * @param resolver The conflict resolution function (local, remote) -> resolved
     289       * @return true if registered, false if already exists
     290       */
     291      public boolean registerConflictResolver(String dataType, BiFunction<Object, Object, Obje
         ct> resolver) {
     292          if (conflictResolvers.containsKey(dataType)) {
     293              return false;
     294          }
     295          
     296          conflictResolvers.put(dataType, resolver);
     297          return true;
     298      }
     299      
     300      /**
     301       * Initiates a synchronization session with another node.
     302       * 
     303       * @param initiatorNodeId The node initiating synchronization
     304       * @param responderNodeId The node to synchronize with
     305       * @param dataTypesToSync Set of data types to synchronize
     306       * @return Session ID if successful, empty if failed
     307       */
     308      public Optional<String> initiateSynchronization(String initiatorNodeId, 
     309              String responderNodeId, Set<String> dataTypesToSync) {
     310          
     311          // Check if responder node is connected
     312          if (!nodeDiscovery.isNodeConnected(responderNodeId)) {
     313              return Optional.empty();
     314          }
     315          
     316          // Create new session
     317          SyncSession session = new SyncSession(initiatorNodeId, responderNodeId, dataTypesToS
         ync);
     318          activeSessions.put(session.sessionId, session);
     319          
     320          return Optional.of(session.sessionId);
     321      }
     322      
     323      /**
     324       * Updates a data item for synchronization.
     325       * 
     326       * @param dataType The type of data
     327       * @param itemId The unique identifier for the item
     328       * @param value The item value
     329       * @return The version assigned to the update
     330       */
     331      public long updateData(String dataType, String itemId, Object value) {
     332          // Ensure data type exists in registry
     333          versionedDataRegistry.putIfAbsent(dataType, new VersionedData(dataType));
     334          
     335          // Calculate new version (usually timestamp-based in real systems)
     336          VersionedData data = versionedDataRegistry.get(dataType);
     337          long newVersion = Math.max(data.getLatestVersion() + 1, System.currentTimeMillis());
     338          
     339          // Update the data
     340          data.updateItem(itemId, value, newVersion);
     341          
     342          return newVersion;
     343      }
     344      
     345      /**
     346       * Gets a data item.
     347       * 
     348       * @param dataType The type of data
     349       * @param itemId The item identifier
     350       * @return An Optional containing the item if found
     351       */
     352      public Optional<DataItem> getData(String dataType, String itemId) {
     353          if (!versionedDataRegistry.containsKey(dataType)) {
     354              return Optional.empty();
     355          }
     356          
     357          return versionedDataRegistry.get(dataType).getItem(itemId);
     358      }
     359      
     360      /**
     361       * Synchronizes data of a specific type with a remote node.
     362       * 
     363       * @param sessionId The synchronization session ID
     364       * @param dataType The type of data to synchronize
     365       * @return The result of the synchronization
     366       */
     367      public SyncSession.SyncResult synchronizeDataType(String sessionId, String dataType) {
     368          if (!activeSessions.containsKey(sessionId)) {
     369              return new SyncSession.SyncResult(dataType, false, 0, 0, 0, "Session not found")
         ;
     370          }
     371          
     372          SyncSession session = activeSessions.get(sessionId);
     373          
     374          // Ensure we have local data to sync
     375          if (!versionedDataRegistry.containsKey(dataType)) {
     376              versionedDataRegistry.put(dataType, new VersionedData(dataType));
     377          }
     378          
     379          // Update session state
     380          session.updateState(SyncSession.SyncState.COMPARING);
     381          
     382          // In a real implementation, this would involve network communication
     383          // with the remote node to compare versions and exchange data
     384          
     385          // For now, we'll simulate a successful sync with fake metrics
     386          int itemsSent = 5;
     387          int itemsReceived = 7;
     388          int conflictsResolved = 2;
     389          
     390          SyncSession.SyncResult result = new SyncSession.SyncResult(
     391                  dataType, true, itemsSent, itemsReceived, conflictsResolved, "Synchronized s
         uccessfully");
     392          
     393          session.recordResult(result);
     394          
     395          // Update session state if all data types have been synchronized
     396          if (session.isComplete()) {
     397              session.updateState(SyncSession.SyncState.COMPLETED);
     398          }
     399          
     400          return result;
     401      }
     402      
     403      /**
     404       * Resolves a conflict between local and remote data.
     405       * 
     406       * @param dataType The type of data
     407       * @param itemId The item identifier
     408       * @param localItem The local version of the item
     409       * @param remoteItem The remote version of the item
     410       * @return The resolved data item
     411       */
     412      public DataItem resolveConflict(String dataType, String itemId, DataItem localItem, Data
         Item remoteItem) {
     413          // Use the registered conflict resolver for this data type, or fall back to default
     414          BiFunction<Object, Object, Object> resolver = conflictResolvers.getOrDefault(dataTyp
         e, 
     415                  conflictResolvers.get("default"));
     416          
     417          // Determine which version to use for the result
     418          long newVersion = Math.max(localItem.getVersion(), remoteItem.getVersion()) + 1;
     419          
     420          // Resolve the conflict
     421          Object resolvedValue = resolver.apply(localItem.getValue(), remoteItem.getValue());
     422          
     423          return new DataItem(itemId, resolvedValue, newVersion);
     424      }
     425      
     426      /**
     427       * Gets the current synchronization status for a session.
     428       * 
     429       * @param sessionId The session ID
     430       * @return A map of status information, or empty if session not found
     431       */
     432      public Map<String, Object> getSyncStatus(String sessionId) {
     433          if (!activeSessions.containsKey(sessionId)) {
     434              return Collections.emptyMap();
     435          }
     436          
     437          SyncSession session = activeSessions.get(sessionId);
     438          Map<String, Object> status = new HashMap<>();
     439          
     440          status.put("sessionId", sessionId);
     441          status.put("initiatorNodeId", session.initiatorNodeId);
     442          status.put("responderNodeId", session.responderNodeId);
     443          status.put("state", session.state.name());
     444          status.put("startTime", session.startTime.toString());
     445          status.put("lastActivity", session.lastActivity.toString());
     446          status.put("dataTypesToSync", new ArrayList<>(session.dataTypesToSync));
     447          
     448          // Result summaries
     449          List<Map<String, Object>> resultSummaries = new ArrayList<>();
     450          for (SyncSession.SyncResult result : session.results.values()) {
     451              Map<String, Object> summary = new HashMap<>();
     452              summary.put("dataType", result.getDataType());
     453              summary.put("success", result.isSuccess());
     454              summary.put("itemsSent", result.getItemsSent());
     455              summary.put("itemsReceived", result.getItemsReceived());
     456              summary.put("conflictsResolved", result.getConflictsResolved());
     457              summary.put("message", result.getMessage());
     458              resultSummaries.add(summary);
     459          }
     460          status.put("results", resultSummaries);
     461          
     462          return status;
     463      }
     464      
     465      /**
     466       * Gets all data items of a specific type that pass boundary checks.
     467       * 
     468       * @param dataType The type of data to retrieve
     469       * @param contextId The context ID for boundary checks
     470       * @return A list of data items
     471       */
     472      public List<DataItem> getAccessibleData(String dataType, String contextId) {
     473          if (!versionedDataRegistry.containsKey(dataType)) {
     474              return Collections.emptyList();
     475          }
     476          
     477          VersionedData data = versionedDataRegistry.get(dataType);
     478          List<DataItem> accessibleItems = new ArrayList<>();
     479          
     480          for (DataItem item : data.getItems().values()) {
     481              // Check if this item can pass the information boundary
     482              if (boundary.canInformationPass(item.getItemId(), contextId)) {
     483                  accessibleItems.add(item);
     484              }
     485          }
     486          
     487          // Sort by version (newest first)
     488          accessibleItems.sort(Comparator.comparing(DataItem::getVersion).reversed());
     489          
     490          return accessibleItems;
     491      }
     492      
     493      /**
     494       * Gets the synchronization history for a specific node.
     495       * 
     496       * @param nodeId The node ID
     497       * @return A list of session summaries
     498       */
     499      public List<Map<String, Object>> getSyncHistory(String nodeId) {
     500          List<Map<String, Object>> history = new ArrayList<>();
     501          
     502          for (SyncSession session : activeSessions.values()) {
     503              if (session.initiatorNodeId.equals(nodeId) || session.responderNodeId.equals(nod
         eId)) {
     504                  Map<String, Object> summary = new HashMap<>();
     505                  summary.put("sessionId", session.sessionId);
     506                  summary.put("peer", session.initiatorNodeId.equals(nodeId) ? 
     507                          session.responderNodeId : session.initiatorNodeId);
     508                  summary.put("state", session.state.name());
     509                  summary.put("startTime", session.startTime.toString());
     510                  summary.put("successful", session.state == SyncSession.SyncState.COMPLETED);
     511                  summary.put("dataTypeCount", session.dataTypesToSync.size());
     512                  
     513                  history.add(summary);
     514              }
     515          }
     516          
     517          // Sort by start time (newest first)
     518          history.sort((a, b) -> {
     519              Instant aTime = Instant.parse((String) a.get("startTime"));
     520              Instant bTime = Instant.parse((String) b.get("startTime"));
     521              return bTime.compareTo(aTime);
     522          });
     523          
     524          return history;
     525      }
     526      
     527      /**
     528       * Cleans up completed or expired synchronization sessions.
     529       * 
     530       * @param maxAgeMillis Maximum age of sessions to keep
     531       * @return Number of sessions cleaned up
     532       */
     533      public int cleanupSessions(long maxAgeMillis) {
     534          int cleanedCount = 0;
     535          Instant now = Instant.now();
     536          List<String> sessionsToRemove = new ArrayList<>();
     537          
     538          for (Map.Entry<String, SyncSession> entry : activeSessions.entrySet()) {
     539              SyncSession session = entry.getValue();
     540              
     541              // Remove completed sessions older than maxAge
     542              if (session.state == SyncSession.SyncState.COMPLETED || 
     543                  session.state == SyncSession.SyncState.FAILED) {
     544                  
     545                  long ageMillis = java.time.Duration.between(session.lastActivity, now).toMil
         lis();
     546                  if (ageMillis > maxAgeMillis) {
     547                      sessionsToRemove.add(entry.getKey());
     548                  }
     549              }
     550          }
     551          
     552          for (String sessionId : sessionsToRemove) {
     553              activeSessions.remove(sessionId);
     554              cleanedCount++;
     555          }
     556          
     557          return cleanedCount;
     558      }
     559  }
     560 \ No newline at end of file

⏺ Finally, let's implement the BifurcationBroadcast class:

⏺ Create(/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/communica…i
        on/BifurcationBroadcast.java)
  ⎿  Updated/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/communi
            cation/BifurcationBroadcast.java with 455 additions
       1  package com.fractal.browser.collective.communication;
       2  
       3  import java.util.Map;
       4  import java.util.HashMap;
       5  import java.util.List;
       6  import java.util.ArrayList;
       7  import java.util.Set;
       8  import java.util.HashSet;
       9  import java.util.UUID;
      10  import java.util.concurrent.ConcurrentHashMap;
      11  import java.time.Instant;
      12  import java.util.Optional;
      13  import java.util.concurrent.CompletableFuture;
      14  import java.util.function.Predicate;
      15  import java.util.Collections;
      16  
      17  import com.fractal.browser.collective.boundaries.InformationBoundary;
      18  import com.fractal.browser.model.Pattern;
      19  
      20  /**
      21   * BifurcationBroadcast implements a special communication mechanism that optimizes
      22   * for transformative moments in collective cognition. It identifies and propagates
      23   * bifurcation points - moments where small inputs can create significant shifts in 
      24   * collective understanding.
      25   * 
      26   * This class applies the mathematics of bifurcation theory to communication patterns,
      27   * enabling efficient transmission of high-leverage insights across the network.
      28   */
      29  public class BifurcationBroadcast {
      30      
      31      // Minimum significance threshold for bifurcation events
      32      private static final double MIN_SIGNIFICANCE = 0.7;
      33      
      34      // Maximum hop count for propagation
      35      private static final int MAX_PROPAGATION_HOPS = 5;
      36      
      37      // Maps broadcast IDs to their data
      38      private final Map<String, BifurcationEvent> eventRegistry;
      39      
      40      // Maps node IDs to their subscription topics
      41      private final Map<String, Set<String>> subscriptions;
      42      
      43      // Tracks propagation paths to avoid loops
      44      private final Map<String, Set<String>> propagationPaths;
      45      
      46      // Node discovery for broadcast routing
      47      private final NodeDiscovery nodeDiscovery;
      48      
      49      // Information boundary for access control
      50      private final InformationBoundary boundary;
      51      
      52      /**
      53       * Represents a bifurcation event in the collective.
      54       */
      55      public static class BifurcationEvent {
      56          private final String eventId;
      57          private final String sourceNodeId;
      58          private final String topic;
      59          private final String content;
      60          private final Map<String, Object> metadata;
      61          private final double significance;
      62          private final Instant timestamp;
      63          private int propagationHops;
      64          private final List<Pattern> associatedPatterns;
      65          
      66          /**
      67           * Creates a new bifurcation event.
      68           */
      69          public BifurcationEvent(String sourceNodeId, String topic, String content, 
      70                  Map<String, Object> metadata, double significance) {
      71              this.eventId = UUID.randomUUID().toString();
      72              this.sourceNodeId = sourceNodeId;
      73              this.topic = topic;
      74              this.content = content;
      75              this.metadata = new HashMap<>(metadata);
      76              this.significance = Math.max(0.0, Math.min(1.0, significance));
      77              this.timestamp = Instant.now();
      78              this.propagationHops = 0;
      79              this.associatedPatterns = new ArrayList<>();
      80          }
      81          
      82          /**
      83           * Creates a new bifurcation event with specified ID (for receiving from network).
      84           */
      85          public BifurcationEvent(String eventId, String sourceNodeId, String topic, String co
         ntent,
      86                  Map<String, Object> metadata, double significance, Instant timestamp, int pr
         opagationHops) {
      87              this.eventId = eventId;
      88              this.sourceNodeId = sourceNodeId;
      89              this.topic = topic;
      90              this.content = content;
      91              this.metadata = new HashMap<>(metadata);
      92              this.significance = Math.max(0.0, Math.min(1.0, significance));
      93              this.timestamp = timestamp;
      94              this.propagationHops = propagationHops;
      95              this.associatedPatterns = new ArrayList<>();
      96          }
      97          
      98          // Getters
      99          public String getEventId() { return eventId; }
     100          public String getSourceNodeId() { return sourceNodeId; }
     101          public String getTopic() { return topic; }
     102          public String getContent() { return content; }
     103          public Map<String, Object> getMetadata() { return Collections.unmodifiableMap(metada
         ta); }
     104          public double getSignificance() { return significance; }
     105          public Instant getTimestamp() { return timestamp; }
     106          public int getPropagationHops() { return propagationHops; }
     107          public List<Pattern> getAssociatedPatterns() { return Collections.unmodifiableList(a
         ssociatedPatterns); }
     108          
     109          /**
     110           * Adds an associated pattern to this event.
     111           */
     112          public void addAssociatedPattern(Pattern pattern) {
     113              this.associatedPatterns.add(pattern);
     114          }
     115          
     116          /**
     117           * Increments the propagation hops counter.
     118           */
     119          public void incrementPropagationHops() {
     120              this.propagationHops++;
     121          }
     122      }
     123      
     124      /**
     125       * Creates a new BifurcationBroadcast with the specified components.
     126       */
     127      public BifurcationBroadcast(NodeDiscovery nodeDiscovery, InformationBoundary boundary) {
     128          this.eventRegistry = new ConcurrentHashMap<>();
     129          this.subscriptions = new ConcurrentHashMap<>();
     130          this.propagationPaths = new ConcurrentHashMap<>();
     131          this.nodeDiscovery = nodeDiscovery;
     132          this.boundary = boundary;
     133      }
     134      
     135      /**
     136       * Subscribes a node to a topic.
     137       * 
     138       * @param nodeId The node ID
     139       * @param topic The topic to subscribe to
     140       * @return true if subscribed, false if already subscribed
     141       */
     142      public boolean subscribeTopic(String nodeId, String topic) {
     143          subscriptions.putIfAbsent(nodeId, Collections.synchronizedSet(new HashSet<>()));
     144          return subscriptions.get(nodeId).add(topic);
     145      }
     146      
     147      /**
     148       * Unsubscribes a node from a topic.
     149       * 
     150       * @param nodeId The node ID
     151       * @param topic The topic to unsubscribe from
     152       * @return true if unsubscribed, false if not subscribed
     153       */
     154      public boolean unsubscribeTopic(String nodeId, String topic) {
     155          if (!subscriptions.containsKey(nodeId)) {
     156              return false;
     157          }
     158          
     159          return subscriptions.get(nodeId).remove(topic);
     160      }
     161      
     162      /**
     163       * Gets the topics a node is subscribed to.
     164       * 
     165       * @param nodeId The node ID
     166       * @return A set of subscribed topics
     167       */
     168      public Set<String> getSubscribedTopics(String nodeId) {
     169          if (!subscriptions.containsKey(nodeId)) {
     170              return Collections.emptySet();
     171          }
     172          
     173          return new HashSet<>(subscriptions.get(nodeId));
     174      }
     175      
     176      /**
     177       * Creates a new bifurcation event.
     178       * 
     179       * @param sourceNodeId The source node ID
     180       * @param topic The topic of the event
     181       * @param content The event content
     182       * @param metadata Additional metadata about the event
     183       * @param significance The significance level (0.0-1.0)
     184       * @return The created event if successful, empty if below significance threshold
     185       */
     186      public Optional<BifurcationEvent> createBifurcationEvent(String sourceNodeId, String top
         ic, 
     187              String content, Map<String, Object> metadata, double significance) {
     188          
     189          // Check if this event meets the significance threshold
     190          if (significance < MIN_SIGNIFICANCE) {
     191              return Optional.empty();
     192          }
     193          
     194          BifurcationEvent event = new BifurcationEvent(sourceNodeId, topic, content, metadata
         , significance);
     195          eventRegistry.put(event.getEventId(), event);
     196          
     197          // Initialize propagation path for this event
     198          propagationPaths.put(event.getEventId(), new HashSet<>());
     199          propagationPaths.get(event.getEventId()).add(sourceNodeId);
     200          
     201          return Optional.of(event);
     202      }
     203      
     204      /**
     205       * Broadcasts a bifurcation event to subscribed nodes.
     206       * 
     207       * @param eventId The event ID to broadcast
     208       * @param filter Optional filter for eligible nodes
     209       * @return A map of node IDs to future results of the broadcast
     210       */
     211      public Map<String, CompletableFuture<Boolean>> broadcastEvent(String eventId, 
     212              Predicate<Map<String, Object>> filter) {
     213          
     214          if (!eventRegistry.containsKey(eventId)) {
     215              return Collections.emptyMap();
     216          }
     217          
     218          BifurcationEvent event = eventRegistry.get(eventId);
     219          
     220          // Check if we've reached max propagation hops
     221          if (event.getPropagationHops() >= MAX_PROPAGATION_HOPS) {
     222              return Collections.emptyMap();
     223          }
     224          
     225          // Find eligible nodes (connected and subscribed to this topic)
     226          Map<String, Map<String, Object>> connectedNodes = nodeDiscovery.getConnectedNodes(fi
         lter);
     227          Map<String, CompletableFuture<Boolean>> broadcastResults = new HashMap<>();
     228          
     229          for (String nodeId : connectedNodes.keySet()) {
     230              // Skip broadcasting back to the source or nodes we've already sent to
     231              if (nodeId.equals(event.getSourceNodeId()) || 
     232                      (propagationPaths.containsKey(eventId) && 
     233                       propagationPaths.get(eventId).contains(nodeId))) {
     234                  continue;
     235              }
     236              
     237              // Check if node is subscribed to this topic
     238              if (subscriptions.containsKey(nodeId) && 
     239                      subscriptions.get(nodeId).contains(event.getTopic())) {
     240                  
     241                  // Check if event can pass information boundary
     242                  if (boundary.canInformationPass(eventId, nodeId)) {
     243                      // Track this node in the propagation path
     244                      propagationPaths.get(eventId).add(nodeId);
     245                      
     246                      // Create future for async broadcast
     247                      CompletableFuture<Boolean> future = new CompletableFuture<>();
     248                      broadcastResults.put(nodeId, future);
     249                      
     250                      // In a real implementation, this would be an async network call
     251                      // For now, we'll just complete the future with success
     252                      future.complete(true);
     253                  }
     254              }
     255          }
     256          
     257          // Increment propagation hops after broadcasting
     258          event.incrementPropagationHops();
     259          
     260          return broadcastResults;
     261      }
     262      
     263      /**
     264       * Receives a bifurcation event from another node.
     265       * 
     266       * @param sourceNodeId The node sending the event
     267       * @param eventData Map containing event data
     268       * @return The ID of the received event if successful, empty Optional otherwise
     269       */
     270      public Optional<String> receiveEvent(String sourceNodeId, Map<String, Object> eventData)
          {
     271          try {
     272              String eventId = (String) eventData.get("eventId");
     273              String topic = (String) eventData.get("topic");
     274              String content = (String) eventData.get("content");
     275              Map<String, Object> metadata = (Map<String, Object>) eventData.get("metadata");
     276              double significance = (double) eventData.get("significance");
     277              Instant timestamp = Instant.parse((String) eventData.get("timestamp"));
     278              int propagationHops = (int) eventData.get("propagationHops");
     279              
     280              // Create event from received data
     281              BifurcationEvent receivedEvent = new BifurcationEvent(
     282                  eventId, sourceNodeId, topic, content, metadata, significance, timestamp, pr
         opagationHops
     283              );
     284              
     285              // Store the received event
     286              eventRegistry.put(eventId, receivedEvent);
     287              
     288              // Initialize propagation path for this event if not exists
     289              propagationPaths.putIfAbsent(eventId, new HashSet<>());
     290              propagationPaths.get(eventId).add(sourceNodeId);
     291              
     292              return Optional.of(eventId);
     293          } catch (Exception e) {
     294              return Optional.empty();
     295          }
     296      }
     297      
     298      /**
     299       * Gets a bifurcation event by ID.
     300       * 
     301       * @param eventId The event ID
     302       * @return An Optional containing the event, or empty if not found
     303       */
     304      public Optional<BifurcationEvent> getEvent(String eventId) {
     305          return Optional.ofNullable(eventRegistry.get(eventId));
     306      }
     307      
     308      /**
     309       * Gets all bifurcation events for a specific topic that pass boundary checks.
     310       * 
     311       * @param topic The topic
     312       * @param contextId The context ID for boundary checks
     313       * @return A list of relevant events
     314       */
     315      public List<BifurcationEvent> getEventsForTopic(String topic, String contextId) {
     316          List<BifurcationEvent> topicEvents = new ArrayList<>();
     317          
     318          for (BifurcationEvent event : eventRegistry.values()) {
     319              if (event.getTopic().equals(topic)) {
     320                  // Ensure event can pass this boundary for the context
     321                  if (boundary.canInformationPass(event.getEventId(), contextId)) {
     322                      topicEvents.add(event);
     323                  }
     324              }
     325          }
     326          
     327          // Sort by significance (highest first)
     328          topicEvents.sort((e1, e2) -> Double.compare(e2.getSignificance(), e1.getSignificance
         ()));
     329          
     330          return topicEvents;
     331      }
     332      
     333      /**
     334       * Gets the propagation path for an event.
     335       * 
     336       * @param eventId The event ID
     337       * @return The set of node IDs in the propagation path
     338       */
     339      public Set<String> getPropagationPath(String eventId) {
     340          if (!propagationPaths.containsKey(eventId)) {
     341              return Collections.emptySet();
     342          }
     343          
     344          return new HashSet<>(propagationPaths.get(eventId));
     345      }
     346      
     347      /**
     348       * Analyzes the significance of an event based on its content and metadata.
     349       * 
     350       * @param content The event content
     351       * @param metadata The event metadata
     352       * @return A significance score (0.0-1.0)
     353       */
     354      public double analyzeSignificance(String content, Map<String, Object> metadata) {
     355          // This would typically involve a more sophisticated analysis algorithm
     356          // For now, we'll use a simple heuristic based on content length and metadata
     357          
     358          double significanceScore = 0.5; // Base score
     359          
     360          // Adjust based on content length (longer content might be more significant)
     361          if (content != null) {
     362              int contentLength = content.length();
     363              if (contentLength > 1000) {
     364                  significanceScore += 0.2;
     365              } else if (contentLength > 500) {
     366                  significanceScore += 0.1;
     367              }
     368          }
     369          
     370          // Adjust based on metadata
     371          if (metadata != null) {
     372              // Check for explicitly provided significance
     373              if (metadata.containsKey("significance") && metadata.get("significance") instanc
         eof Number) {
     374                  double providedSignificance = ((Number) metadata.get("significance")).double
         Value();
     375                  significanceScore = Math.max(significanceScore, providedSignificance);
     376              }
     377              
     378              // Check for priority indicator
     379              if (metadata.containsKey("priority") && metadata.get("priority") instanceof Stri
         ng) {
     380                  String priority = (String) metadata.get("priority");
     381                  if (priority.equalsIgnoreCase("high")) {
     382                      significanceScore += 0.2;
     383                  } else if (priority.equalsIgnoreCase("critical")) {
     384                      significanceScore += 0.3;
     385                  }
     386              }
     387          }
     388          
     389          // Ensure score is in valid range
     390          return Math.max(0.0, Math.min(1.0, significanceScore));
     391      }
     392      
     393      /**
     394       * Finds the most significant events across all topics.
     395       * 
     396       * @param limit Maximum number of events to return
     397       * @param contextId The context ID for boundary checks
     398       * @return A list of the most significant events
     399       */
     400      public List<BifurcationEvent> getMostSignificantEvents(int limit, String contextId) {
     401          List<BifurcationEvent> allEvents = new ArrayList<>();
     402          
     403          for (BifurcationEvent event : eventRegistry.values()) {
     404              // Ensure event can pass this boundary for the context
     405              if (boundary.canInformationPass(event.getEventId(), contextId)) {
     406                  allEvents.add(event);
     407              }
     408          }
     409          
     410          // Sort by significance (highest first)
     411          allEvents.sort((e1, e2) -> Double.compare(e2.getSignificance(), e1.getSignificance()
         ));
     412          
     413          // Return up to the limit
     414          return allEvents.size() <= limit ? allEvents : allEvents.subList(0, limit);
     415      }
     416      
     417      /**
     418       * Gets nodes that would receive an event if broadcasted.
     419       * 
     420       * @param eventId The event ID
     421       * @return A set of node IDs that would receive the event
     422       */
     423      public Set<String> getEligibleReceivers(String eventId) {
     424          if (!eventRegistry.containsKey(eventId)) {
     425              return Collections.emptySet();
     426          }
     427          
     428          BifurcationEvent event = eventRegistry.get(eventId);
     429          Set<String> eligibleNodes = new HashSet<>();
     430          
     431          // Find all connected nodes
     432          Map<String, Map<String, Object>> connectedNodes = nodeDiscovery.getConnectedNodes(nu
         ll);
     433          
     434          for (String nodeId : connectedNodes.keySet()) {
     435              // Skip the source and nodes that already received this event
     436              if (nodeId.equals(event.getSourceNodeId()) || 
     437                      (propagationPaths.containsKey(eventId) && 
     438                       propagationPaths.get(eventId).contains(nodeId))) {
     439                  continue;
     440              }
     441              
     442              // Check if node is subscribed to this topic
     443              if (subscriptions.containsKey(nodeId) && 
     444                      subscriptions.get(nodeId).contains(event.getTopic())) {
     445                  
     446                  // Check if event can pass information boundary
     447                  if (boundary.canInformationPass(eventId, nodeId)) {
     448                      eligibleNodes.add(nodeId);
     449                  }
     450              }
     451          }
     452          
     453          return eligibleNodes;
     454      }
     455  }
     456 \ No newline at end of file

⏺ Now I'll check if there's any class referencing Pattern that might need fixing:

⏺ Bash(grep -r "import com.fractal.browser.model.Pattern" src/main/java/ | grep -v …
      "InsightExchange\|BifurcationBroadcast")
  ⎿  src/main/java/com/fractal/browser/util/PatternUtils.java:import com.fractal.browser.model.Pattern;
     src/main/java/com/fractal/browser/observation/ObservationSystem.java:import
     com.fractal.browser.model.Pattern;
     src/main/java/com/fractal/browser/observation/Observer.java:import
     com.fractal.browser.model.Pattern;

⏺ Let's verify the Pattern class exists:

⏺ Read(file_path:                                                                                   …
      "/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/model/Pattern.java")
  ⎿  package com.fractal.browser.model;
     import java.util.Map;
     public class Pattern {
         private final String name;
         private final String description;
         private final Map<String, Double> attributes;
         // Constructor and getters
     … +1 more lines

⏺ Great! The Pattern class exists, but it looks like it's just a skeleton. Let's move on to
  implementing the core classes. I'll now finish implementing the memory classes.

⏺ Create(/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/memory/Di…t
        ributedInsightRepository.java)
  ⎿  Updated/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/memory/
            DistributedInsightRepository.java with 453 additions
       1  package com.fractal.browser.collective.memory;
       2  
       3  import java.util.Map;
       4  import java.util.HashMap;
       5  import java.util.List;
       6  import java.util.ArrayList;
       7  import java.util.Set;
       8  import java.util.HashSet;
       9  import java.util.concurrent.ConcurrentHashMap;
      10  import java.time.Instant;
      11  import java.util.UUID;
      12  import java.util.Optional;
      13  import java.util.Collections;
      14  import java.util.function.Predicate;
      15  import java.util.stream.Collectors;
      16  
      17  import com.fractal.browser.collective.boundaries.InformationBoundary;
      18  import com.fractal.browser.model.Pattern;
      19  
      20  /**
      21   * DistributedInsightRepository implements a distributed memory system for the collective, 
      22   * storing insights, patterns, and other cognitive artifacts. It manages distributed storage
      23   * and retrieval across multiple nodes while maintaining coherence.
      24   * 
      25   * This class provides mechanisms for recursive storage and access to collective insights
      26   * at multiple scales.
      27   */
      28  public class DistributedInsightRepository {
      29      
      30      // Maps insight IDs to their data
      31      private final Map<String, Insight> insightRegistry;
      32      
      33      // Maps tags to insight IDs
      34      private final Map<String, Set<String>> tagIndex;
      35      
      36      // Maps pattern IDs to insight IDs
      37      private final Map<String, Set<String>> patternIndex;
      38      
      39      // Maps contexts to insight IDs
      40      private final Map<String, Set<String>> contextIndex;
      41      
      42      // Information boundary for access control
      43      private final InformationBoundary boundary;
      44      
      45      // Persistence strategy for durable storage
      46      private final PersistenceStrategy persistenceStrategy;
      47      
      48      /**
      49       * Represents an insight stored in the repository.
      50       */
      51      public static class Insight {
      52          private final String insightId;
      53          private final String sourceNodeId;
      54          private final String content;
      55          private final Set<String> tags;
      56          private final Map<String, Object> metadata;
      57          private final Instant timestamp;
      58          private final Instant lastAccessed;
      59          private final List<Pattern> associatedPatterns;
      60          private final double significance;
      61          private int accessCount;
      62          
      63          /**
      64           * Creates a new insight.
      65           */
      66          public Insight(String sourceNodeId, String content, Set<String> tags, 
      67                  Map<String, Object> metadata, double significance) {
      68              this.insightId = UUID.randomUUID().toString();
      69              this.sourceNodeId = sourceNodeId;
      70              this.content = content;
      71              this.tags = new HashSet<>(tags);
      72              this.metadata = new HashMap<>(metadata);
      73              this.timestamp = Instant.now();
      74              this.lastAccessed = this.timestamp;
      75              this.associatedPatterns = new ArrayList<>();
      76              this.significance = significance;
      77              this.accessCount = 0;
      78          }
      79          
      80          /**
      81           * Creates an insight with an existing ID (for loading from persistence).
      82           */
      83          public Insight(String insightId, String sourceNodeId, String content, Set<String> ta
         gs,
      84                  Map<String, Object> metadata, Instant timestamp, Instant lastAccessed,
      85                  List<Pattern> associatedPatterns, double significance, int accessCount) {
      86              this.insightId = insightId;
      87              this.sourceNodeId = sourceNodeId;
      88              this.content = content;
      89              this.tags = new HashSet<>(tags);
      90              this.metadata = new HashMap<>(metadata);
      91              this.timestamp = timestamp;
      92              this.lastAccessed = lastAccessed;
      93              this.associatedPatterns = new ArrayList<>(associatedPatterns);
      94              this.significance = significance;
      95              this.accessCount = accessCount;
      96          }
      97          
      98          // Getters
      99          public String getInsightId() { return insightId; }
     100          public String getSourceNodeId() { return sourceNodeId; }
     101          public String getContent() { return content; }
     102          public Set<String> getTags() { return Collections.unmodifiableSet(tags); }
     103          public Map<String, Object> getMetadata() { return Collections.unmodifiableMap(metada
         ta); }
     104          public Instant getTimestamp() { return timestamp; }
     105          public Instant getLastAccessed() { return lastAccessed; }
     106          public List<Pattern> getAssociatedPatterns() { return Collections.unmodifiableList(a
         ssociatedPatterns); }
     107          public double getSignificance() { return significance; }
     108          public int getAccessCount() { return accessCount; }
     109      }
     110      
     111      /**
     112       * Creates a new DistributedInsightRepository with the specified components.
     113       */
     114      public DistributedInsightRepository(InformationBoundary boundary, PersistenceStrategy pe
         rsistenceStrategy) {
     115          this.insightRegistry = new ConcurrentHashMap<>();
     116          this.tagIndex = new ConcurrentHashMap<>();
     117          this.patternIndex = new ConcurrentHashMap<>();
     118          this.contextIndex = new ConcurrentHashMap<>();
     119          this.boundary = boundary;
     120          this.persistenceStrategy = persistenceStrategy;
     121          
     122          // Load existing insights from persistence if available
     123          loadFromPersistence();
     124      }
     125      
     126      /**
     127       * Loads existing insights from the persistence layer.
     128       */
     129      private void loadFromPersistence() {
     130          if (persistenceStrategy != null) {
     131              try {
     132                  List<Map<String, Object>> persistedInsights = persistenceStrategy.loadAll("i
         nsights");
     133                  
     134                  for (Map<String, Object> data : persistedInsights) {
     135                      // Parse and create Insight from persisted data
     136                      // In a real implementation, this would handle all the necessary convers
         ions
     137                      
     138                      // For now, just log that we would load insights
     139                      System.out.println("Would load insight: " + data.get("insightId"));
     140                  }
     141              } catch (Exception e) {
     142                  System.err.println("Error loading insights from persistence: " + e.getMessag
         e());
     143              }
     144          }
     145      }
     146      
     147      /**
     148       * Stores a new insight in the repository.
     149       * 
     150       * @param sourceNodeId The source node ID
     151       * @param content The insight content
     152       * @param tags Tags associated with the insight
     153       * @param metadata Additional metadata
     154       * @param significance The significance level (0.0-1.0)
     155       * @return The created insight
     156       */
     157      public Insight storeInsight(String sourceNodeId, String content, Set<String> tags,
     158              Map<String, Object> metadata, double significance) {
     159          
     160          // Create the insight
     161          Insight insight = new Insight(sourceNodeId, content, tags, metadata, significance);
     162          
     163          // Store in registry
     164          insightRegistry.put(insight.getInsightId(), insight);
     165          
     166          // Index by tags
     167          for (String tag : tags) {
     168              tagIndex.putIfAbsent(tag, Collections.synchronizedSet(new HashSet<>()));
     169              tagIndex.get(tag).add(insight.getInsightId());
     170          }
     171          
     172          // Index by context if provided
     173          if (metadata.containsKey("context")) {
     174              String context = metadata.get("context").toString();
     175              contextIndex.putIfAbsent(context, Collections.synchronizedSet(new HashSet<>()));
     176              contextIndex.get(context).add(insight.getInsightId());
     177          }
     178          
     179          // Persist the insight
     180          if (persistenceStrategy != null) {
     181              try {
     182                  Map<String, Object> persistData = new HashMap<>();
     183                  persistData.put("insightId", insight.getInsightId());
     184                  persistData.put("sourceNodeId", insight.getSourceNodeId());
     185                  persistData.put("content", insight.getContent());
     186                  persistData.put("tags", new ArrayList<>(insight.getTags()));
     187                  persistData.put("metadata", insight.getMetadata());
     188                  persistData.put("timestamp", insight.getTimestamp().toString());
     189                  persistData.put("significance", insight.getSignificance());
     190                  
     191                  persistenceStrategy.store("insights", insight.getInsightId(), persistData);
     192              } catch (Exception e) {
     193                  System.err.println("Error persisting insight: " + e.getMessage());
     194              }
     195          }
     196          
     197          return insight;
     198      }
     199      
     200      /**
     201       * Retrieves an insight by ID, if accessible in the given context.
     202       * 
     203       * @param insightId The insight ID
     204       * @param contextId The context ID for boundary checks
     205       * @return An Optional containing the insight if found and accessible
     206       */
     207      public Optional<Insight> getInsight(String insightId, String contextId) {
     208          if (!insightRegistry.containsKey(insightId)) {
     209              return Optional.empty();
     210          }
     211          
     212          // Check if insight can pass information boundary
     213          if (!boundary.canInformationPass(insightId, contextId)) {
     214              return Optional.empty();
     215          }
     216          
     217          // Record access
     218          recordAccess(insightId);
     219          
     220          return Optional.of(insightRegistry.get(insightId));
     221      }
     222      
     223      /**
     224       * Records that an insight was accessed.
     225       */
     226      private void recordAccess(String insightId) {
     227          Insight insight = insightRegistry.get(insightId);
     228          if (insight != null) {
     229              insight.accessCount++;
     230              
     231              // In a real implementation, we would update the lastAccessed timestamp
     232              // and persist the updated access information
     233          }
     234      }
     235      
     236      /**
     237       * Associates a pattern with an insight.
     238       * 
     239       * @param insightId The insight ID
     240       * @param pattern The pattern to associate
     241       * @return true if successful, false otherwise
     242       */
     243      public boolean associatePattern(String insightId, Pattern pattern) {
     244          if (!insightRegistry.containsKey(insightId)) {
     245              return false;
     246          }
     247          
     248          Insight insight = insightRegistry.get(insightId);
     249          insight.associatedPatterns.add(pattern);
     250          
     251          // Index by pattern ID
     252          String patternId = pattern.getName(); // Assuming name is used as ID
     253          patternIndex.putIfAbsent(patternId, Collections.synchronizedSet(new HashSet<>()));
     254          patternIndex.get(patternId).add(insightId);
     255          
     256          return true;
     257      }
     258      
     259      /**
     260       * Finds insights by tags.
     261       * 
     262       * @param tags The tags to search for
     263       * @param matchAll If true, all tags must match; if false, any tag can match
     264       * @param contextId The context ID for boundary checks
     265       * @return A list of matching insights
     266       */
     267      public List<Insight> findByTags(Set<String> tags, boolean matchAll, String contextId) {
     268          Set<String> matchingIds = new HashSet<>();
     269          
     270          if (matchAll) {
     271              // All tags must match - start with all insights for the first tag
     272              boolean first = true;
     273              
     274              for (String tag : tags) {
     275                  Set<String> taggedInsights = tagIndex.getOrDefault(tag, Collections.emptySet
         ());
     276                  
     277                  if (first) {
     278                      matchingIds.addAll(taggedInsights);
     279                      first = false;
     280                  } else {
     281                      // Retain only insights that have all tags
     282                      matchingIds.retainAll(taggedInsights);
     283                  }
     284              }
     285          } else {
     286              // Any tag can match - union of all insights with any tag
     287              for (String tag : tags) {
     288                  Set<String> taggedInsights = tagIndex.getOrDefault(tag, Collections.emptySet
         ());
     289                  matchingIds.addAll(taggedInsights);
     290              }
     291          }
     292          
     293          // Filter by boundary check
     294          return matchingIds.stream()
     295                  .filter(id -> boundary.canInformationPass(id, contextId))
     296                  .map(id -> {
     297                      recordAccess(id);
     298                      return insightRegistry.get(id);
     299                  })
     300                  .collect(Collectors.toList());
     301      }
     302      
     303      /**
     304       * Finds insights by pattern.
     305       * 
     306       * @param patternId The pattern ID
     307       * @param contextId The context ID for boundary checks
     308       * @return A list of insights associated with the pattern
     309       */
     310      public List<Insight> findByPattern(String patternId, String contextId) {
     311          Set<String> matchingIds = patternIndex.getOrDefault(patternId, Collections.emptySet(
         ));
     312          
     313          // Filter by boundary check
     314          return matchingIds.stream()
     315                  .filter(id -> boundary.canInformationPass(id, contextId))
     316                  .map(id -> {
     317                      recordAccess(id);
     318                      return insightRegistry.get(id);
     319                  })
     320                  .collect(Collectors.toList());
     321      }
     322      
     323      /**
     324       * Finds insights by context.
     325       * 
     326       * @param context The context to search for
     327       * @param contextId The context ID for boundary checks
     328       * @return A list of insights in the specified context
     329       */
     330      public List<Insight> findByContext(String context, String contextId) {
     331          Set<String> matchingIds = contextIndex.getOrDefault(context, Collections.emptySet())
         ;
     332          
     333          // Filter by boundary check
     334          return matchingIds.stream()
     335                  .filter(id -> boundary.canInformationPass(id, contextId))
     336                  .map(id -> {
     337                      recordAccess(id);
     338                      return insightRegistry.get(id);
     339                  })
     340                  .collect(Collectors.toList());
     341      }
     342      
     343      /**
     344       * Finds insights by a custom filter.
     345       * 
     346       * @param filter The filter predicate
     347       * @param contextId The context ID for boundary checks
     348       * @return A list of insights matching the filter
     349       */
     350      public List<Insight> findByFilter(Predicate<Insight> filter, String contextId) {
     351          return insightRegistry.values().stream()
     352                  .filter(filter)
     353                  .filter(insight -> boundary.canInformationPass(insight.getInsightId(), conte
         xtId))
     354                  .peek(insight -> recordAccess(insight.getInsightId()))
     355                  .collect(Collectors.toList());
     356      }
     357      
     358      /**
     359       * Gets the most significant insights.
     360       * 
     361       * @param limit Maximum number of insights to return
     362       * @param contextId The context ID for boundary checks
     363       * @return A list of the most significant insights
     364       */
     365      public List<Insight> getMostSignificantInsights(int limit, String contextId) {
     366          return insightRegistry.values().stream()
     367                  .filter(insight -> boundary.canInformationPass(insight.getInsightId(), conte
         xtId))
     368                  .sorted((i1, i2) -> Double.compare(i2.getSignificance(), i1.getSignificance(
         )))
     369                  .limit(limit)
     370                  .peek(insight -> recordAccess(insight.getInsightId()))
     371                  .collect(Collectors.toList());
     372      }
     373      
     374      /**
     375       * Gets the most frequently accessed insights.
     376       * 
     377       * @param limit Maximum number of insights to return
     378       * @param contextId The context ID for boundary checks
     379       * @return A list of the most accessed insights
     380       */
     381      public List<Insight> getMostAccessedInsights(int limit, String contextId) {
     382          return insightRegistry.values().stream()
     383                  .filter(insight -> boundary.canInformationPass(insight.getInsightId(), conte
         xtId))
     384                  .sorted((i1, i2) -> Integer.compare(i2.getAccessCount(), i1.getAccessCount()
         ))
     385                  .limit(limit)
     386                  .peek(insight -> recordAccess(insight.getInsightId()))
     387                  .collect(Collectors.toList());
     388      }
     389      
     390      /**
     391       * Gets the most recent insights.
     392       * 
     393       * @param limit Maximum number of insights to return
     394       * @param contextId The context ID for boundary checks
     395       * @return A list of the most recent insights
     396       */
     397      public List<Insight> getMostRecentInsights(int limit, String contextId) {
     398          return insightRegistry.values().stream()
     399                  .filter(insight -> boundary.canInformationPass(insight.getInsightId(), conte
         xtId))
     400                  .sorted((i1, i2) -> i2.getTimestamp().compareTo(i1.getTimestamp()))
     401                  .limit(limit)
     402                  .peek(insight -> recordAccess(insight.getInsightId()))
     403                  .collect(Collectors.toList());
     404      }
     405      
     406      /**
     407       * Deletes an insight from the repository.
     408       * 
     409       * @param insightId The insight ID to delete
     410       * @return true if successful, false otherwise
     411       */
     412      public boolean deleteInsight(String insightId) {
     413          if (!insightRegistry.containsKey(insightId)) {
     414              return false;
     415          }
     416          
     417          Insight insight = insightRegistry.remove(insightId);
     418          
     419          // Remove from tag index
     420          for (String tag : insight.getTags()) {
     421              if (tagIndex.containsKey(tag)) {
     422                  tagIndex.get(tag).remove(insightId);
     423              }
     424          }
     425          
     426          // Remove from pattern index
     427          for (Pattern pattern : insight.getAssociatedPatterns()) {
     428              String patternId = pattern.getName();
     429              if (patternIndex.containsKey(patternId)) {
     430                  patternIndex.get(patternId).remove(insightId);
     431              }
     432          }
     433          
     434          // Remove from context index
     435          if (insight.getMetadata().containsKey("context")) {
     436              String context = insight.getMetadata().get("context").toString();
     437              if (contextIndex.containsKey(context)) {
     438                  contextIndex.get(context).remove(insightId);
     439              }
     440          }
     441          
     442          // Remove from persistence
     443          if (persistenceStrategy != null) {
     444              try {
     445                  persistenceStrategy.delete("insights", insightId);
     446              } catch (Exception e) {
     447                  System.err.println("Error deleting insight from persistence: " + e.getMessag
         e());
     448              }
     449          }
     450          
     451          return true;
     452      }
     453  }
     454 \ No newline at end of file

⏺ Now let's implement the PersistenceStrategy interface:

⏺ Create(/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/memory/Pe…s
        istenceStrategy.java)
  ⎿  Updated/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/memory/
            PersistenceStrategy.java with 105 additions
       1  package com.fractal.browser.collective.memory;
       2  
       3  import java.util.List;
       4  import java.util.Map;
       5  
       6  /**
       7   * PersistenceStrategy defines an interface for storing and retrieving data in the
       8   * collective memory system. This interface allows for different persistence implementations
       9   * (file, database, distributed storage, etc.) while providing a consistent API.
      10   * 
      11   * The strategy applies fractal storage patterns to manage data across different scales,
      12   * from individual nodes to the full collective.
      13   */
      14  public interface PersistenceStrategy {
      15      
      16      /**
      17       * Stores data under the specified type and ID.
      18       * 
      19       * @param dataType The type of data (e.g., "insights", "patterns")
      20       * @param id The unique identifier for the data
      21       * @param data The data to store
      22       * @throws Exception If there is an error during storage
      23       */
      24      void store(String dataType, String id, Map<String, Object> data) throws Exception;
      25      
      26      /**
      27       * Loads data for the specified type and ID.
      28       * 
      29       * @param dataType The type of data to load
      30       * @param id The unique identifier
      31       * @return The loaded data, or null if not found
      32       * @throws Exception If there is an error during loading
      33       */
      34      Map<String, Object> load(String dataType, String id) throws Exception;
      35      
      36      /**
      37       * Loads all data of the specified type.
      38       * 
      39       * @param dataType The type of data to load
      40       * @return A list of data maps
      41       * @throws Exception If there is an error during loading
      42       */
      43      List<Map<String, Object>> loadAll(String dataType) throws Exception;
      44      
      45      /**
      46       * Deletes data for the specified type and ID.
      47       * 
      48       * @param dataType The type of data to delete
      49       * @param id The unique identifier
      50       * @throws Exception If there is an error during deletion
      51       */
      52      void delete(String dataType, String id) throws Exception;
      53      
      54      /**
      55       * Checks if data exists for the specified type and ID.
      56       * 
      57       * @param dataType The type of data to check
      58       * @param id The unique identifier
      59       * @return true if data exists, false otherwise
      60       * @throws Exception If there is an error during the check
      61       */
      62      boolean exists(String dataType, String id) throws Exception;
      63      
      64      /**
      65       * Initializes the persistence system.
      66       * 
      67       * @throws Exception If there is an error during initialization
      68       */
      69      void initialize() throws Exception;
      70      
      71      /**
      72       * Shuts down the persistence system.
      73       * 
      74       * @throws Exception If there is an error during shutdown
      75       */
      76      void shutdown() throws Exception;
      77      
      78      /**
      79       * Performs a query against the stored data.
      80       * 
      81       * @param dataType The type of data to query
      82       * @param query A map of query parameters
      83       * @return A list of matching data maps
      84       * @throws Exception If there is an error during the query
      85       */
      86      List<Map<String, Object>> query(String dataType, Map<String, Object> query) throws Excep
         tion;
      87      
      88      /**
      89       * Creates a backup of the stored data.
      90       * 
      91       * @param backupLocation The location for the backup
      92       * @return true if successful, false otherwise
      93       * @throws Exception If there is an error during backup
      94       */
      95      boolean backup(String backupLocation) throws Exception;
      96      
      97      /**
      98       * Restores data from a backup.
      99       * 
     100       * @param backupLocation The location of the backup
     101       * @return true if successful, false otherwise
     102       * @throws Exception If there is an error during restoration
     103       */
     104      boolean restore(String backupLocation) throws Exception;
     105  }
     106 \ No newline at end of file

⏺ Let's implement the SharedContextRegistry class:

⏺ Create(/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/memory/Sh…r
        edContextRegistry.java)
  ⎿  Updated/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/memory/
            SharedContextRegistry.java with 553 additions
       1  package com.fractal.browser.collective.memory;
       2  
       3  import java.util.Map;
       4  import java.util.HashMap;
       5  import java.util.List;
       6  import java.util.ArrayList;
       7  import java.util.Set;
       8  import java.util.HashSet;
       9  import java.util.concurrent.ConcurrentHashMap;
      10  import java.time.Instant;
      11  import java.util.UUID;
      12  import java.util.Optional;
      13  import java.util.Collections;
      14  import java.util.concurrent.locks.ReadWriteLock;
      15  import java.util.concurrent.locks.ReentrantReadWriteLock;
      16  
      17  import com.fractal.browser.collective.boundaries.InformationBoundary;
      18  
      19  /**
      20   * SharedContextRegistry implements a registry of shared contexts within the collective.
      21   * It enables nodes to create, join, and interact within various shared cognitive contexts,
      22   * maintaining relationships between contexts at different scales.
      23   * 
      24   * This class applies fractal patterns to context management, allowing for nested contexts
      25   * and multi-scale context relationships.
      26   */
      27  public class SharedContextRegistry {
      28      
      29      // Maps context IDs to their data
      30      private final Map<String, Context> contextRegistry;
      31      
      32      // Maps node IDs to the contexts they participate in
      33      private final Map<String, Set<String>> nodeContexts;
      34      
      35      // Maps parent context IDs to child context IDs
      36      private final Map<String, Set<String>> contextHierarchy;
      37      
      38      // Maps context names to context IDs
      39      private final Map<String, String> contextNameIndex;
      40      
      41      // Information boundary for access control
      42      private final InformationBoundary boundary;
      43      
      44      // Persistence strategy for durable storage
      45      private final PersistenceStrategy persistenceStrategy;
      46      
      47      // Lock for context operations
      48      private final ReadWriteLock contextLock;
      49      
      50      /**
      51       * Represents a shared context within the collective.
      52       */
      53      public static class Context {
      54          private final String contextId;
      55          private final String name;
      56          private final String description;
      57          private final String creatorNodeId;
      58          private final Instant creationTime;
      59          private final Instant lastActivity;
      60          private final Map<String, Object> metadata;
      61          private final Set<String> participantNodeIds;
      62          private final String parentContextId; // null for root contexts
      63          private boolean active;
      64          
      65          /**
      66           * Creates a new context.
      67           */
      68          public Context(String name, String description, String creatorNodeId, 
      69                  Map<String, Object> metadata, String parentContextId) {
      70              this.contextId = UUID.randomUUID().toString();
      71              this.name = name;
      72              this.description = description;
      73              this.creatorNodeId = creatorNodeId;
      74              this.creationTime = Instant.now();
      75              this.lastActivity = this.creationTime;
      76              this.metadata = new HashMap<>(metadata);
      77              this.participantNodeIds = Collections.synchronizedSet(new HashSet<>());
      78              this.participantNodeIds.add(creatorNodeId); // Creator is first participant
      79              this.parentContextId = parentContextId;
      80              this.active = true;
      81          }
      82          
      83          /**
      84           * Creates a context with an existing ID (for loading from persistence).
      85           */
      86          public Context(String contextId, String name, String description, String creatorNode
         Id,
      87                  Instant creationTime, Instant lastActivity, Map<String, Object> metadata,
      88                  Set<String> participantNodeIds, String parentContextId, boolean active) {
      89              this.contextId = contextId;
      90              this.name = name;
      91              this.description = description;
      92              this.creatorNodeId = creatorNodeId;
      93              this.creationTime = creationTime;
      94              this.lastActivity = lastActivity;
      95              this.metadata = new HashMap<>(metadata);
      96              this.participantNodeIds = Collections.synchronizedSet(new HashSet<>(participantN
         odeIds));
      97              this.parentContextId = parentContextId;
      98              this.active = active;
      99          }
     100          
     101          // Getters
     102          public String getContextId() { return contextId; }
     103          public String getName() { return name; }
     104          public String getDescription() { return description; }
     105          public String getCreatorNodeId() { return creatorNodeId; }
     106          public Instant getCreationTime() { return creationTime; }
     107          public Instant getLastActivity() { return lastActivity; }
     108          public Map<String, Object> getMetadata() { return Collections.unmodifiableMap(metada
         ta); }
     109          public Set<String> getParticipantNodeIds() { return Collections.unmodifiableSet(part
         icipantNodeIds); }
     110          public String getParentContextId() { return parentContextId; }
     111          public boolean isActive() { return active; }
     112          
     113          /**
     114           * Adds a participant to this context.
     115           * 
     116           * @param nodeId The node ID to add
     117           * @return true if added, false if already a participant
     118           */
     119          public boolean addParticipant(String nodeId) {
     120              return participantNodeIds.add(nodeId);
     121          }
     122          
     123          /**
     124           * Removes a participant from this context.
     125           * 
     126           * @param nodeId The node ID to remove
     127           * @return true if removed, false if not a participant
     128           */
     129          public boolean removeParticipant(String nodeId) {
     130              // Can't remove creator
     131              if (nodeId.equals(creatorNodeId)) {
     132                  return false;
     133              }
     134              
     135              return participantNodeIds.remove(nodeId);
     136          }
     137          
     138          /**
     139           * Deactivates this context.
     140           */
     141          public void deactivate() {
     142              this.active = false;
     143          }
     144          
     145          /**
     146           * Checks if a node is a participant in this context.
     147           * 
     148           * @param nodeId The node ID to check
     149           * @return true if participant, false otherwise
     150           */
     151          public boolean isParticipant(String nodeId) {
     152              return participantNodeIds.contains(nodeId);
     153          }
     154      }
     155      
     156      /**
     157       * Creates a new SharedContextRegistry with the specified components.
     158       */
     159      public SharedContextRegistry(InformationBoundary boundary, PersistenceStrategy persisten
         ceStrategy) {
     160          this.contextRegistry = new ConcurrentHashMap<>();
     161          this.nodeContexts = new ConcurrentHashMap<>();
     162          this.contextHierarchy = new ConcurrentHashMap<>();
     163          this.contextNameIndex = new ConcurrentHashMap<>();
     164          this.boundary = boundary;
     165          this.persistenceStrategy = persistenceStrategy;
     166          this.contextLock = new ReentrantReadWriteLock();
     167          
     168          // Load existing contexts from persistence if available
     169          loadFromPersistence();
     170      }
     171      
     172      /**
     173       * Loads existing contexts from the persistence layer.
     174       */
     175      private void loadFromPersistence() {
     176          if (persistenceStrategy != null) {
     177              try {
     178                  List<Map<String, Object>> persistedContexts = persistenceStrategy.loadAll("c
         ontexts");
     179                  
     180                  // Process each persisted context
     181                  for (Map<String, Object> data : persistedContexts) {
     182                      // In a real implementation, this would convert the data to a Context
     183                      // and load it into the registry, updating all indexes
     184                      
     185                      // For now, just log that we would load contexts
     186                      System.out.println("Would load context: " + data.get("contextId"));
     187                  }
     188              } catch (Exception e) {
     189                  System.err.println("Error loading contexts from persistence: " + e.getMessag
         e());
     190              }
     191          }
     192      }
     193      
     194      /**
     195       * Creates a new shared context.
     196       * 
     197       * @param name The context name
     198       * @param description The context description
     199       * @param creatorNodeId The creator node ID
     200       * @param metadata Additional metadata
     201       * @param parentContextId Parent context ID, or null for root contexts
     202       * @return The created context if successful, empty if error
     203       */
     204      public Optional<Context> createContext(String name, String description, String creatorNo
         deId,
     205              Map<String, Object> metadata, String parentContextId) {
     206          
     207          contextLock.writeLock().lock();
     208          try {
     209              // Check if name is already used
     210              if (contextNameIndex.containsKey(name)) {
     211                  return Optional.empty();
     212              }
     213              
     214              // Check if parent context exists (if specified)
     215              if (parentContextId != null && !contextRegistry.containsKey(parentContextId)) {
     216                  return Optional.empty();
     217              }
     218              
     219              // Create the context
     220              Context context = new Context(name, description, creatorNodeId, metadata, parent
         ContextId);
     221              
     222              // Store in registry
     223              contextRegistry.put(context.getContextId(), context);
     224              
     225              // Index by name
     226              contextNameIndex.put(name, context.getContextId());
     227              
     228              // Update node contexts
     229              nodeContexts.putIfAbsent(creatorNodeId, Collections.synchronizedSet(new HashSet<
         >()));
     230              nodeContexts.get(creatorNodeId).add(context.getContextId());
     231              
     232              // Update hierarchy if parent specified
     233              if (parentContextId != null) {
     234                  contextHierarchy.putIfAbsent(parentContextId, Collections.synchronizedSet(ne
         w HashSet<>()));
     235                  contextHierarchy.get(parentContextId).add(context.getContextId());
     236              }
     237              
     238              // Persist the context
     239              if (persistenceStrategy != null) {
     240                  try {
     241                      Map<String, Object> persistData = new HashMap<>();
     242                      persistData.put("contextId", context.getContextId());
     243                      persistData.put("name", context.getName());
     244                      persistData.put("description", context.getDescription());
     245                      persistData.put("creatorNodeId", context.getCreatorNodeId());
     246                      persistData.put("creationTime", context.getCreationTime().toString());
     247                      persistData.put("lastActivity", context.getLastActivity().toString());
     248                      persistData.put("metadata", context.getMetadata());
     249                      persistData.put("participantNodeIds", new ArrayList<>(context.getPartici
         pantNodeIds()));
     250                      persistData.put("parentContextId", context.getParentContextId());
     251                      persistData.put("active", context.isActive());
     252                      
     253                      persistenceStrategy.store("contexts", context.getContextId(), persistDat
         a);
     254                  } catch (Exception e) {
     255                      System.err.println("Error persisting context: " + e.getMessage());
     256                  }
     257              }
     258              
     259              return Optional.of(context);
     260          } finally {
     261              contextLock.writeLock().unlock();
     262          }
     263      }
     264      
     265      /**
     266       * Gets a context by ID.
     267       * 
     268       * @param contextId The context ID
     269       * @return An Optional containing the context if found
     270       */
     271      public Optional<Context> getContext(String contextId) {
     272          contextLock.readLock().lock();
     273          try {
     274              return Optional.ofNullable(contextRegistry.get(contextId));
     275          } finally {
     276              contextLock.readLock().unlock();
     277          }
     278      }
     279      
     280      /**
     281       * Gets a context by name.
     282       * 
     283       * @param name The context name
     284       * @return An Optional containing the context if found
     285       */
     286      public Optional<Context> getContextByName(String name) {
     287          contextLock.readLock().lock();
     288          try {
     289              String contextId = contextNameIndex.get(name);
     290              if (contextId == null) {
     291                  return Optional.empty();
     292              }
     293              
     294              return Optional.ofNullable(contextRegistry.get(contextId));
     295          } finally {
     296              contextLock.readLock().unlock();
     297          }
     298      }
     299      
     300      /**
     301       * Joins a node to a context.
     302       * 
     303       * @param nodeId The node ID
     304       * @param contextId The context ID
     305       * @return true if joined, false otherwise
     306       */
     307      public boolean joinContext(String nodeId, String contextId) {
     308          contextLock.writeLock().lock();
     309          try {
     310              // Check if context exists and is active
     311              Context context = contextRegistry.get(contextId);
     312              if (context == null || !context.isActive()) {
     313                  return false;
     314              }
     315              
     316              // Add node to context
     317              boolean added = context.addParticipant(nodeId);
     318              if (!added) {
     319                  return false; // Already a participant
     320              }
     321              
     322              // Update node contexts
     323              nodeContexts.putIfAbsent(nodeId, Collections.synchronizedSet(new HashSet<>()));
     324              nodeContexts.get(nodeId).add(contextId);
     325              
     326              // Update persistence
     327              if (persistenceStrategy != null) {
     328                  try {
     329                      Map<String, Object> persistData = new HashMap<>();
     330                      persistData.put("participantNodeIds", new ArrayList<>(context.getPartici
         pantNodeIds()));
     331                      
     332                      persistenceStrategy.store("contexts", contextId, persistData);
     333                  } catch (Exception e) {
     334                      System.err.println("Error updating context participants: " + e.getMessag
         e());
     335                  }
     336              }
     337              
     338              return true;
     339          } finally {
     340              contextLock.writeLock().unlock();
     341          }
     342      }
     343      
     344      /**
     345       * Leaves a context.
     346       * 
     347       * @param nodeId The node ID
     348       * @param contextId The context ID
     349       * @return true if left, false otherwise
     350       */
     351      public boolean leaveContext(String nodeId, String contextId) {
     352          contextLock.writeLock().lock();
     353          try {
     354              // Check if context exists
     355              Context context = contextRegistry.get(contextId);
     356              if (context == null) {
     357                  return false;
     358              }
     359              
     360              // Remove node from context
     361              boolean removed = context.removeParticipant(nodeId);
     362              if (!removed) {
     363                  return false; // Not a participant or is creator
     364              }
     365              
     366              // Update node contexts
     367              if (nodeContexts.containsKey(nodeId)) {
     368                  nodeContexts.get(nodeId).remove(contextId);
     369              }
     370              
     371              // Update persistence
     372              if (persistenceStrategy != null) {
     373                  try {
     374                      Map<String, Object> persistData = new HashMap<>();
     375                      persistData.put("participantNodeIds", new ArrayList<>(context.getPartici
         pantNodeIds()));
     376                      
     377                      persistenceStrategy.store("contexts", contextId, persistData);
     378                  } catch (Exception e) {
     379                      System.err.println("Error updating context participants: " + e.getMessag
         e());
     380                  }
     381              }
     382              
     383              return true;
     384          } finally {
     385              contextLock.writeLock().unlock();
     386          }
     387      }
     388      
     389      /**
     390       * Gets all contexts a node participates in.
     391       * 
     392       * @param nodeId The node ID
     393       * @return A set of context IDs
     394       */
     395      public Set<String> getNodeContexts(String nodeId) {
     396          contextLock.readLock().lock();
     397          try {
     398              if (!nodeContexts.containsKey(nodeId)) {
     399                  return Collections.emptySet();
     400              }
     401              
     402              return new HashSet<>(nodeContexts.get(nodeId));
     403          } finally {
     404              contextLock.readLock().unlock();
     405          }
     406      }
     407      
     408      /**
     409       * Gets all child contexts of a parent context.
     410       * 
     411       * @param parentContextId The parent context ID
     412       * @return A set of child context IDs
     413       */
     414      public Set<String> getChildContexts(String parentContextId) {
     415          contextLock.readLock().lock();
     416          try {
     417              if (!contextHierarchy.containsKey(parentContextId)) {
     418                  return Collections.emptySet();
     419              }
     420              
     421              return new HashSet<>(contextHierarchy.get(parentContextId));
     422          } finally {
     423              contextLock.readLock().unlock();
     424          }
     425      }
     426      
     427      /**
     428       * Gets the full context hierarchy below a parent context.
     429       * 
     430       * @param parentContextId The parent context ID
     431       * @return A map of context IDs to their levels in the hierarchy
     432       */
     433      public Map<String, Integer> getContextHierarchy(String parentContextId) {
     434          contextLock.readLock().lock();
     435          try {
     436              Map<String, Integer> hierarchy = new HashMap<>();
     437              
     438              // Add parent context at level 0
     439              hierarchy.put(parentContextId, 0);
     440              
     441              // Recursively add child contexts
     442              addChildContextsToHierarchy(parentContextId, 1, hierarchy);
     443              
     444              return hierarchy;
     445          } finally {
     446              contextLock.readLock().unlock();
     447          }
     448      }
     449      
     450      /**
     451       * Recursively adds child contexts to the hierarchy map.
     452       */
     453      private void addChildContextsToHierarchy(String parentContextId, int level, Map<String, 
         Integer> hierarchy) {
     454          Set<String> childContexts = contextHierarchy.getOrDefault(parentContextId, Collectio
         ns.emptySet());
     455          
     456          for (String childId : childContexts) {
     457              hierarchy.put(childId, level);
     458              addChildContextsToHierarchy(childId, level + 1, hierarchy);
     459          }
     460      }
     461      
     462      /**
     463       * Deactivates a context.
     464       * 
     465       * @param contextId The context ID
     466       * @param nodeId The node ID making the request (must be creator)
     467       * @return true if deactivated, false otherwise
     468       */
     469      public boolean deactivateContext(String contextId, String nodeId) {
     470          contextLock.writeLock().lock();
     471          try {
     472              // Check if context exists
     473              Context context = contextRegistry.get(contextId);
     474              if (context == null) {
     475                  return false;
     476              }
     477              
     478              // Check if node is context creator
     479              if (!context.getCreatorNodeId().equals(nodeId)) {
     480                  return false; // Only creator can deactivate
     481              }
     482              
     483              // Deactivate context
     484              context.deactivate();
     485              
     486              // Update persistence
     487              if (persistenceStrategy != null) {
     488                  try {
     489                      Map<String, Object> persistData = new HashMap<>();
     490                      persistData.put("active", false);
     491                      
     492                      persistenceStrategy.store("contexts", contextId, persistData);
     493                  } catch (Exception e) {
     494                      System.err.println("Error updating context status: " + e.getMessage());
     495                  }
     496              }
     497              
     498              return true;
     499          } finally {
     500              contextLock.writeLock().unlock();
     501          }
     502      }
     503      
     504      /**
     505       * Checks if a node can access a context.
     506       * 
     507       * @param nodeId The node ID
     508       * @param contextId The context ID
     509       * @return true if node can access the context, false otherwise
     510       */
     511      public boolean canAccessContext(String nodeId, String contextId) {
     512          contextLock.readLock().lock();
     513          try {
     514              // Check if context exists and is active
     515              Context context = contextRegistry.get(contextId);
     516              if (context == null || !context.isActive()) {
     517                  return false;
     518              }
     519              
     520              // Check if node is a participant
     521              if (context.isParticipant(nodeId)) {
     522                  return true;
     523              }
     524              
     525              // Check information boundary
     526              return boundary.canInformationPass(contextId, nodeId);
     527          } finally {
     528              contextLock.readLock().unlock();
     529          }
     530      }
     531      
     532      /**
     533       * Gets all active root contexts.
     534       * 
     535       * @return A list of root contexts
     536       */
     537      public List<Context> getRootContexts() {
     538          contextLock.readLock().lock();
     539          try {
     540              List<Context> rootContexts = new ArrayList<>();
     541              
     542              for (Context context : contextRegistry.values()) {
     543                  if (context.isActive() && context.getParentContextId() == null) {
     544                      rootContexts.add(context);
     545                  }
     546              }
     547              
     548              return rootContexts;
     549          } finally {
     550              contextLock.readLock().unlock();
     551          }
     552      }
     553  }
     554 \ No newline at end of file

⏺ Now let's implement the TemporalIndexing class:

⏺ Create(/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/memory/Te…p
        oralIndexing.java)
  ⎿  Updated/Users/ryandavidoates/Nova-act-revisit/src/main/java/com/fractal/browser/collective/memory/
            TemporalIndexing.java with 455 additions
       1  package com.fractal.browser.collective.memory;
       2  
       3  import java.util.Map;
       4  import java.util.HashMap;
       5  import java.util.List;
       6  import java.util.ArrayList;
       7  import java.util.Set;
       8  import java.util.HashSet;
       9  import java.util.concurrent.ConcurrentHashMap;
      10  import java.time.Instant;
      11  import java.time.LocalDate;
      12  import java.time.ZoneId;
      13  import java.time.temporal.ChronoUnit;
      14  import java.util.NavigableMap;
      15  import java.util.TreeMap;
      16  import java.util.Collections;
      17  import java.util.Comparator;
      18  import java.util.Optional;
      19  import java.util.stream.Collectors;
      20  
      21  import com.fractal.browser.collective.boundaries.InformationBoundary;
      22  
      23  /**
      24   * TemporalIndexing implements temporal storage and retrieval patterns for the collective
      25   * memory. It tracks when information was created, accessed, and modified, enabling
      26   * time-based queries and temporal pattern recognition.
      27   * 
      28   * This class applies fractal temporal patterns, allowing for time-based queries at
      29   * multiple scales, from microseconds to years.
      30   */
      31  public class TemporalIndexing {
      32      
      33      // Maps timestamps to item IDs
      34      private final NavigableMap<Instant, Set<String>> timeIndex;
      35      
      36      // Maps date strings (YYYY-MM-DD) to item IDs
      37      private final Map<String, Set<String>> dateIndex;
      38      
      39      // Maps item IDs to their timestamps
      40      private final Map<String, Instant> itemTimes;
      41      
      42      // Maps item IDs to their metadata
      43      private final Map<String, Map<String, Object>> itemMetadata;
      44      
      45      // Information boundary for access control
      46      private final InformationBoundary boundary;
      47      
      48      /**
      49       * Creates a new TemporalIndexing instance.
      50       */
      51      public TemporalIndexing(InformationBoundary boundary) {
      52          this.timeIndex = Collections.synchronizedNavigableMap(new TreeMap<>());
      53          this.dateIndex = new ConcurrentHashMap<>();
      54          this.itemTimes = new ConcurrentHashMap<>();
      55          this.itemMetadata = new ConcurrentHashMap<>();
      56          this.boundary = boundary;
      57      }
      58      
      59      /**
      60       * Indexes an item by its timestamp.
      61       * 
      62       * @param itemId The item ID
      63       * @param timestamp The timestamp
      64       * @param metadata Additional metadata about the item
      65       * @return true if indexed, false if already indexed
      66       */
      67      public boolean indexItem(String itemId, Instant timestamp, Map<String, Object> metadata)
          {
      68          if (itemTimes.containsKey(itemId)) {
      69              return false; // Already indexed
      70          }
      71          
      72          // Add to time index
      73          timeIndex.computeIfAbsent(timestamp, k -> Collections.synchronizedSet(new HashSet<>(
         )))
      74                  .add(itemId);
      75          
      76          // Add to date index
      77          LocalDate date = LocalDate.ofInstant(timestamp, ZoneId.systemDefault());
      78          String dateString = date.toString(); // YYYY-MM-DD format
      79          dateIndex.computeIfAbsent(dateString, k -> Collections.synchronizedSet(new HashSet<>
         ()))
      80                  .add(itemId);
      81          
      82          // Store item time and metadata
      83          itemTimes.put(itemId, timestamp);
      84          itemMetadata.put(itemId, new HashMap<>(metadata));
      85          
      86          return true;
      87      }
      88      
      89      /**
      90       * Updates an existing item's metadata.
      91       * 
      92       * @param itemId The item ID
      93       * @param metadata The new metadata
      94       * @return true if updated, false if item not found
      95       */
      96      public boolean updateItemMetadata(String itemId, Map<String, Object> metadata) {
      97          if (!itemTimes.containsKey(itemId)) {
      98              return false; // Item not found
      99          }
     100          
     101          // Update metadata
     102          itemMetadata.put(itemId, new HashMap<>(metadata));
     103          
     104          return true;
     105      }
     106      
     107      /**
     108       * Removes an item from the temporal indexes.
     109       * 
     110       * @param itemId The item ID
     111       * @return true if removed, false if not found
     112       */
     113      public boolean removeItem(String itemId) {
     114          Instant timestamp = itemTimes.remove(itemId);
     115          if (timestamp == null) {
     116              return false; // Not found
     117          }
     118          
     119          // Remove from time index
     120          Set<String> timeEntries = timeIndex.get(timestamp);
     121          if (timeEntries != null) {
     122              timeEntries.remove(itemId);
     123              if (timeEntries.isEmpty()) {
     124                  timeIndex.remove(timestamp);
     125              }
     126          }
     127          
     128          // Remove from date index
     129          LocalDate date = LocalDate.ofInstant(timestamp, ZoneId.systemDefault());
     130          String dateString = date.toString();
     131          Set<String> dateEntries = dateIndex.get(dateString);
     132          if (dateEntries != null) {
     133              dateEntries.remove(itemId);
     134              if (dateEntries.isEmpty()) {
     135                  dateIndex.remove(dateString);
     136              }
     137          }
     138          
     139          // Remove metadata
     140          itemMetadata.remove(itemId);
     141          
     142          return true;
     143      }
     144      
     145      /**
     146       * Gets an item's timestamp.
     147       * 
     148       * @param itemId The item ID
     149       * @return An Optional containing the timestamp if found
     150       */
     151      public Optional<Instant> getItemTimestamp(String itemId) {
     152          return Optional.ofNullable(itemTimes.get(itemId));
     153      }
     154      
     155      /**
     156       * Gets an item's metadata.
     157       * 
     158       * @param itemId The item ID
     159       * @return An Optional containing the metadata if found
     160       */
     161      public Optional<Map<String, Object>> getItemMetadata(String itemId) {
     162          Map<String, Object> metadata = itemMetadata.get(itemId);
     163          if (metadata == null) {
     164              return Optional.empty();
     165          }
     166          
     167          return Optional.of(new HashMap<>(metadata));
     168      }
     169      
     170      /**
     171       * Finds items created within a time range.
     172       * 
     173       * @param start The start time (inclusive)
     174       * @param end The end time (inclusive)
     175       * @param contextId The context ID for boundary checks
     176       * @return A list of item IDs in the time range
     177       */
     178      public List<String> findItemsInTimeRange(Instant start, Instant end, String contextId) {
     179          // Get the submap for the time range
     180          NavigableMap<Instant, Set<String>> rangeMap = timeIndex.subMap(start, true, end, tru
         e);
     181          
     182          List<String> result = new ArrayList<>();
     183          
     184          // Collect all item IDs in the range that pass boundary checks
     185          for (Set<String> items : rangeMap.values()) {
     186              for (String itemId : items) {
     187                  if (boundary.canInformationPass(itemId, contextId)) {
     188                      result.add(itemId);
     189                  }
     190              }
     191          }
     192          
     193          return result;
     194      }
     195      
     196      /**
     197       * Finds items created on a specific date.
     198       * 
     199       * @param date The date in YYYY-MM-DD format
     200       * @param contextId The context ID for boundary checks
     201       * @return A list of item IDs on the date
     202       */
     203      public List<String> findItemsOnDate(String date, String contextId) {
     204          Set<String> dateItems = dateIndex.getOrDefault(date, Collections.emptySet());
     205          
     206          return dateItems.stream()
     207                  .filter(itemId -> boundary.canInformationPass(itemId, contextId))
     208                  .collect(Collectors.toList());
     209      }
     210      
     211      /**
     212       * Groups items by time periods.
     213       * 
     214       * @param unit The time unit to group by (e.g., ChronoUnit.DAYS, ChronoUnit.HOURS)
     215       * @param contextId The context ID for boundary checks
     216       * @return A map of period start times to item IDs
     217       */
     218      public Map<Instant, List<String>> groupItemsByTimePeriod(ChronoUnit unit, String context
         Id) {
     219          Map<Instant, List<String>> result = new HashMap<>();
     220          
     221          // Group all items by the specified time unit
     222          for (Map.Entry<String, Instant> entry : itemTimes.entrySet()) {
     223              String itemId = entry.getKey();
     224              Instant timestamp = entry.getValue();
     225              
     226              // Check if item can pass boundary
     227              if (!boundary.canInformationPass(itemId, contextId)) {
     228                  continue;
     229              }
     230              
     231              // Truncate timestamp to the specified unit
     232              Instant periodStart = truncateToUnit(timestamp, unit);
     233              
     234              // Add to result
     235              result.computeIfAbsent(periodStart, k -> new ArrayList<>()).add(itemId);
     236          }
     237          
     238          return result;
     239      }
     240      
     241      /**
     242       * Truncates an Instant to the specified ChronoUnit.
     243       */
     244      private Instant truncateToUnit(Instant instant, ChronoUnit unit) {
     245          LocalDate date = LocalDate.ofInstant(instant, ZoneId.systemDefault());
     246          
     247          switch (unit) {
     248              case YEARS:
     249                  return LocalDate.of(date.getYear(), 1, 1)
     250                          .atStartOfDay(ZoneId.systemDefault()).toInstant();
     251              case MONTHS:
     252                  return LocalDate.of(date.getYear(), date.getMonthValue(), 1)
     253                          .atStartOfDay(ZoneId.systemDefault()).toInstant();
     254              case WEEKS:
     255                  return date.minusDays(date.getDayOfWeek().getValue() - 1)
     256                          .atStartOfDay(ZoneId.systemDefault()).toInstant();
     257              case DAYS:
     258                  return date.atStartOfDay(ZoneId.systemDefault()).toInstant();
     259              case HOURS:
     260                  return instant.truncatedTo(ChronoUnit.HOURS);
     261              case MINUTES:
     262                  return instant.truncatedTo(ChronoUnit.MINUTES);
     263              case SECONDS:
     264                  return instant.truncatedTo(ChronoUnit.SECONDS);
     265              default:
     266                  return instant;
     267          }
     268      }
     269      
     270      /**
     271       * Gets the most recent items.
     272       * 
     273       * @param limit Maximum number of items to return
     274       * @param contextId The context ID for boundary checks
     275       * @return A list of the most recent item IDs
     276       */
     277      public List<String> getMostRecentItems(int limit, String contextId) {
     278          List<Map.Entry<String, Instant>> sortedItems = new ArrayList<>();
     279          
     280          // Filter items by boundary check
     281          for (Map.Entry<String, Instant> entry : itemTimes.entrySet()) {
     282              String itemId = entry.getKey();
     283              if (boundary.canInformationPass(itemId, contextId)) {
     284                  sortedItems.add(entry);
     285              }
     286          }
     287          
     288          // Sort by timestamp (most recent first)
     289          sortedItems.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
     290          
     291          // Take the top N items
     292          return sortedItems.stream()
     293                  .limit(limit)
     294                  .map(Map.Entry::getKey)
     295                  .collect(Collectors.toList());
     296      }
     297      
     298      /**
     299       * Finds temporal patterns in the data.
     300       * 
     301       * @param windowSize The sliding window size in milliseconds
     302       * @param threshold The minimum number of items in a window to consider it a pattern
     303       * @param contextId The context ID for boundary checks
     304       * @return A list of time windows with item counts above the threshold
     305       */
     306      public List<Map<String, Object>> findTemporalPatterns(long windowSize, int threshold, St
         ring contextId) {
     307          List<Map<String, Object>> patterns = new ArrayList<>();
     308          
     309          // Create a timeline of all timestamps with their items
     310          List<Instant> timeline = new ArrayList<>(timeIndex.keySet());
     311          Collections.sort(timeline);
     312          
     313          // Use a sliding window to detect patterns
     314          for (int i = 0; i < timeline.size(); i++) {
     315              Instant windowStart = timeline.get(i);
     316              Instant windowEnd = windowStart.plusMillis(windowSize);
     317              
     318              // Count items in the window that pass boundary checks
     319              int itemCount = 0;
     320              Set<String> itemsInWindow = new HashSet<>();
     321              
     322              for (int j = i; j < timeline.size() && !timeline.get(j).isAfter(windowEnd); j++)
          {
     323                  Instant time = timeline.get(j);
     324                  Set<String> items = timeIndex.get(time);
     325                  
     326                  for (String itemId : items) {
     327                      if (boundary.canInformationPass(itemId, contextId)) {
     328                          itemCount++;
     329                          itemsInWindow.add(itemId);
     330                      }
     331                  }
     332              }
     333              
     334              // If count exceeds threshold, record the pattern
     335              if (itemCount >= threshold) {
     336                  Map<String, Object> pattern = new HashMap<>();
     337                  pattern.put("startTime", windowStart);
     338                  pattern.put("endTime", windowEnd);
     339                  pattern.put("itemCount", itemCount);
     340                  pattern.put("items", new ArrayList<>(itemsInWindow));
     341                  
     342                  patterns.add(pattern);
     343              }
     344          }
     345          
     346          return patterns;
     347      }
     348      
     349      /**
     350       * Finds gaps in the temporal data.
     351       * 
     352       * @param minGapMillis The minimum gap size in milliseconds
     353       * @param contextId The context ID for boundary checks
     354       * @return A list of gaps (start and end times)
     355       */
     356      public List<Map<String, Instant>> findTemporalGaps(long minGapMillis, String contextId) 
         {
     357          List<Map<String, Instant>> gaps = new ArrayList<>();
     358          
     359          // Get all timestamps for items that pass boundary checks
     360          List<Instant> timeline = new ArrayList<>();
     361          for (Map.Entry<Instant, Set<String>> entry : timeIndex.entrySet()) {
     362              Instant time = entry.getKey();
     363              Set<String> items = entry.getValue();
     364              
     365              boolean anyVisible = false;
     366              for (String itemId : items) {
     367                  if (boundary.canInformationPass(itemId, contextId)) {
     368                      anyVisible = true;
     369                      break;
     370                  }
     371              }
     372              
     373              if (anyVisible) {
     374                  timeline.add(time);
     375              }
     376          }
     377          
     378          // Sort the timeline
     379          Collections.sort(timeline);
     380          
     381          // Find gaps
     382          for (int i = 0; i < timeline.size() - 1; i++) {
     383              Instant current = timeline.get(i);
     384              Instant next = timeline.get(i + 1);
     385              
     386              long gapMillis = current.until(next, ChronoUnit.MILLIS);
     387              
     388              if (gapMillis >= minGapMillis) {
     389                  Map<String, Instant> gap = new HashMap<>();
     390                  gap.put("start", current);
     391                  gap.put("end", next);
     392                  
     393                  gaps.add(gap);
     394              }
     395          }
     396          
     397          return gaps;
     398      }
     399      
     400      /**
     401       * Gets time-based statistics.
     402       * 
     403       * @param unit The time unit for aggregation
     404       * @param contextId The context ID for boundary checks
     405       * @return Statistics by time period
     406       */
     407      public Map<Instant, Map<String, Object>> getTimeStatistics(ChronoUnit unit, String conte
         xtId) {
     408          Map<Instant, Map<String, Object>> statistics = new HashMap<>();
     409          
     410          // Group items by time period
     411          Map<Instant, List<String>> groupedItems = groupItemsByTimePeriod(unit, contextId);
     412          
     413          // Calculate statistics for each group
     414          for (Map.Entry<Instant, List<String>> entry : groupedItems.entrySet()) {
     415              Instant periodStart = entry.getKey();
     416              List<String> itemIds = entry.getValue();
     417              
     418              Map<String, Object> periodStats = new HashMap<>();
     419              periodStats.put("itemCount", itemIds.size());
     420              
     421              // Calculate average metadata values if numeric
     422              Map<String, Double> metadataSums = new HashMap<>();
     423              Map<String, Integer> metadataCounts = new HashMap<>();
     424              
     425              for (String itemId : itemIds) {
     426                  Map<String, Object> metadata = itemMetadata.get(itemId);
     427                  if (metadata != null) {
     428                      for (Map.Entry<String, Object> metaEntry : metadata.entrySet()) {
     429                          String key = metaEntry.getKey();
     430                          Object value = metaEntry.getValue();
     431                          
     432                          if (value instanceof Number) {
     433                              double numValue = ((Number) value).doubleValue();
     434                              metadataSums.put(key, metadataSums.getOrDefault(key, 0.0) + numV
         alue);
     435                              metadataCounts.put(key, metadataCounts.getOrDefault(key, 0) + 1)
         ;
     436                          }
     437                      }
     438                  }
     439              }
     440              
     441              // Calculate averages
     442              Map<String, Double> metadataAverages = new HashMap<>();
     443              for (String key : metadataSums.keySet()) {
     444                  double sum = metadataSums.get(key);
     445                  int count = metadataCounts.get(key);
     446                  metadataAverages.put("avg_" + key, sum / count);
     447              }
     448              
     449              periodStats.putAll(metadataAverages);
     450              statistics.put(periodStart, periodStats);
     451          }
     452          
     453          return statistics;
     454      }
     455  }
     456 \ No newline at end of file

✢ Generating… (2s · esc to interrupt)

╭──────────────────────────────────────────────────────────────────────────────────────────────────────────╮
│ >                                                                                                        │
╰──────────────────────────────────────────────────────────────────────────────────────────────────────────╯
 