# Java SDK

The Nova ACT Java SDK provides native Java bindings for Nova ACT functionality, maintaining feature parity with the Python implementation while integrating seamlessly with Java applications.

## Overview

The Java SDK offers:
- Complete Nova ACT functionality in pure Java
- Enterprise-ready implementation
- Thread-safe operations
- Native Java ecosystem integration

## Installation

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.amazon</groupId>
    <artifactId>nova-act</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Basic Usage

```java
import com.amazon.novaact.NovaAct;

public class Example {
    public static void main(String[] args) {
        try (NovaAct nova = new NovaAct()) {
            nova.start();
            nova.act("navigate", Map.of("url", "https://example.com"));
        }
    }
}
```

See the [Project Structure](project-structure.md) documentation for details about how the Java SDK fits into the overall Nova ACT ecosystem.

## API Reference

[Documentation coming soon...]