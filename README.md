# Fractal Browser: A Distributed Collective Intelligence System

Fractal Browser is a sophisticated distributed system that enables collective intelligence through fractal processing patterns, recursive analysis, and emergent pattern detection. It provides a framework for nodes to collaboratively process information, share insights, and develop collective understanding across multiple scales.

The system implements a novel approach to distributed computing by applying fractal mathematics and collective intelligence principles. It features self-similar processing at different scales, bifurcation detection for emergent patterns, and robust safety/privacy boundaries for secure information exchange between nodes.

## Repository Structure
```
.
├── src/                           # Source code root directory
│   ├── main/java                  # Java implementation 
│   │   └── com/fractal/browser/
│   │       ├── analysis/         # Analysis components for pattern recognition
│   │       ├── collective/       # Core collective intelligence implementation
│   │       │   ├── boundaries/   # Safety and privacy controls
│   │       │   ├── communication/# Node communication protocols
│   │       │   ├── core/        # Core collective node functionality
│   │       │   ├── memory/      # Distributed memory management
│   │       │   ├── processing/  # Collective processing algorithms
│   │       │   └── visualization/# Network and pattern visualization
│   │       └── util/            # Utility classes and helpers
│   └── main/python              # Python implementation
│       └── nova_act/
│           └── collective/      # Python collective intelligence modules
├── gradle/                      # Gradle build configuration
└── docs/                       # Documentation and design files
```

## Usage Instructions

### Prerequisites
- Java 21 or higher
- Python 3.9 or higher
- Gradle 8.13 or higher
- NumPy >= 1.22.2
- Pandas >= 1.3.0
- pytest >= 7.0.0

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd fractal-browser
```

2. Install Python dependencies:
```bash
pip install -r requirements.txt
```

3. Build the Java components:
```bash
./gradlew build
```

### Quick Start

1. Initialize a collective node:
```java
CollectiveNode node = new CollectiveNode();
node.initialize();
```

2. Create a fractal processor:
```java
FractalProcessor processor = new NodeFractalProcessor();
processor.process(new SemanticInstruction("initial-value", "constant"));
```

3. Set up safety boundaries:
```java
SafetyContainer container = new SafetyContainer();
container.setSafetyLevel(3);
container.setStrictMode(true);
```

### More Detailed Examples

Pattern Recognition:
```java
PatternRecognizer recognizer = new PatternRecognizer();
Pattern pattern = recognizer.recognize(journalEntry);
```

Collective Processing:
```java
CollectiveFractalProcessor collective = new CollectiveFractalProcessor();
ProcessingResult result = collective.processCollectively(instruction, "context-id");
```

### Troubleshooting

Common Issues:
1. Node Discovery Failures
   - Error: "Unable to discover nodes"
   - Solution: Check network connectivity and ensure NodeDiscovery service is running
   ```java
   NodeDiscovery discovery = new NodeDiscovery();
   boolean available = discovery.isNodeAvailable(nodeId);
   ```

2. Safety Container Violations
   - Error: "Safety boundary violation detected"
   - Solution: Review safety level settings and content validation
   ```java
   SafetyContainer container = new SafetyContainer();
   container.setSafetyLevel(4);
   ```

## Data Flow

The system processes information through recursive fractal patterns, with data flowing from individual nodes through collective processing to emerge as shared insights.

```ascii
Input -> [Node Processing] -> [Collective Analysis] -> [Pattern Detection]
           |                        |                         |
           v                        v                         v
    Local Insights  ->    Shared Understanding  ->   Emergent Patterns
```

Key interactions:
1. Nodes process local information using fractal algorithms
2. Results are shared through the collective communication layer
3. Pattern detection identifies emergent behaviors
4. Safety boundaries enforce secure information exchange
5. Distributed memory maintains collective state
6. Meta-awareness network tracks pattern relationships
7. Visualization components render network state and patterns

## Infrastructure

![Infrastructure diagram](./docs/infra.svg)

The system uses the following key infrastructure components:

Lambda Functions:
- `NodeDiscovery` - Handles node discovery and availability tracking
- `CollectiveFractalProcessor` - Manages distributed processing tasks
- `SafetyContainer` - Enforces security boundaries

Storage:
- Distributed insight repository for collective memory
- Temporal indexing for time-based pattern analysis
- Shared context registry for coordination

Network:
- Bifurcation broadcast system for pattern propagation
- Synchronization protocol for state consistency
- Meta-awareness network for pattern relationships