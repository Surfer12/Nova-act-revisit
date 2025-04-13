# Comprehensive Maven Guide for Java 23

This guide covers everything you need to know about using Maven with Java 23 in a zsh environment on macOS.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Installing Java 23](#installing-java-23)
- [Installing Maven](#installing-maven)
- [Maven Basics](#maven-basics)
- [Project Setup](#project-setup)
- [Common Maven Commands](#common-maven-commands)
- [Dependency Management](#dependency-management)
- [Build Lifecycle](#build-lifecycle)
- [Profiles](#profiles)
- [Plugins](#plugins)
- [Best Practices](#best-practices)
- [Troubleshooting](#troubleshooting)

## Prerequisites

Before starting, ensure you have:
- macOS with zsh shell
- Homebrew (recommended for installations)
- Basic command line knowledge

## Installing Java 23

Java 23 is a recent release. Here's how to install it using Homebrew:

```zsh
# Update Homebrew
brew update

# Install Java 23
brew install openjdk@23

# Set JAVA_HOME in your .zshrc file
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 23)' >> ~/.zshrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.zshrc

# Reload your shell configuration
source ~/.zshrc
```

Verify the installation:

```zsh
java --version
```

## Installing Maven

Install Maven using Homebrew:

```zsh
# Install Maven
brew install maven

# Verify installation
mvn --version
```

## Maven Basics

Maven is a build automation and dependency management tool. It uses a Project Object Model (POM) defined in a `pom.xml` file.

### Key Concepts

- **POM**: Project Object Model - the fundamental unit of work in Maven
- **Artifact**: A file (usually a JAR) that is deployed to a Maven repository
- **GroupId**: Identifies your project uniquely across all projects
- **ArtifactId**: The name of the jar without version
- **Version**: A specific release of a project

## Project Setup

### Creating a New Maven Project

```zsh
# Create a basic Maven project
mvn archetype:generate \
  -DgroupId=com.example \
  -DartifactId=my-java23-app \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DarchetypeVersion=1.4 \
  -DinteractiveMode=false
```

### Configuring for Java 23

Navigate to your project directory and update the `pom.xml` file to use Java 23:

```zsh
cd my-java23-app
```

Edit your `pom.xml` to include:

```xml
<properties>
    <maven.compiler.source>23</maven.compiler.source>
    <maven.compiler.target>23</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

Add the Maven compiler plugin:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.11.0</version>
            <configuration>
                <release>23</release>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## Common Maven Commands

Here are essential Maven commands to use in zsh:

```zsh
# Compile the project
mvn compile

# Run tests
mvn test

# Package the application
mvn package

# Install the package to local repository
mvn install

# Clean the target directory
mvn clean

# Clean and package
mvn clean package

# Generate project documentation
mvn site

# Run the application (if it has a main class)
mvn exec:java -Dexec.mainClass="com.example.App"

# Check for dependency updates
mvn versions:display-dependency-updates

# Skip tests
mvn package -DskipTests
```

## Dependency Management

### Adding Dependencies

Edit your `pom.xml` to add dependencies:

```xml
<dependencies>
    <!-- JUnit 5 -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.0</version>
        <scope>test</scope>
    </dependency>
    
    <!-- Example: Add Log4j -->
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>2.20.0</version>
    </dependency>
</dependencies>
```

### Dependency Scopes

- **compile**: Default scope, available in all classpaths
- **provided**: Provided by JDK or container at runtime
- **runtime**: Required at runtime but not for compilation
- **test**: Only for testing
- **system**: Similar to provided, but you specify the JAR explicitly
- **import**: Special scope for dependency management

### Managing Dependencies

```zsh
# Show dependency tree
mvn dependency:tree

# Analyze dependencies
mvn dependency:analyze

# Copy dependencies to target/dependency folder
mvn dependency:copy-dependencies
```

## Build Lifecycle

Maven has three built-in build lifecycles:

1. **default**: handles project deployment
2. **clean**: handles project cleaning
3. **site**: handles site documentation creation

### Main Build Phases

- **validate**: Validate project structure
- **compile**: Compile source code
- **test**: Run tests
- **package**: Package compiled code
- **verify**: Run checks on integration tests
- **install**: Install package to local repository
- **deploy**: Copy package to remote repository

## Profiles

Profiles allow you to customize builds for different environments:

```xml
<profiles>
    <profile>
        <id>development</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <environment>dev</environment>
        </properties>
    </profile>
    <profile>
        <id>production</id>
        <properties>
            <environment>prod</environment>
        </properties>
    </profile>
</profiles>
```

Activate profiles in zsh:

```zsh
# Activate a specific profile
mvn package -P production

# List active profiles
mvn help:active-profiles
```

## Plugins

Maven functionality is extended through plugins. Common plugins include:

### Exec Plugin

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.1.0</version>
    <configuration>
        <mainClass>com.example.App</mainClass>
    </configuration>
</plugin>
```

### Shade Plugin (for creating uber/fat JARs)

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.5.0</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <transformers>
                    <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                        <mainClass>com.example.App</mainClass>
                    </transformer>
                </transformers>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## Best Practices

1. **Use properties for versions**: Define versions in properties section
   ```xml
   <properties>
       <junit.version>5.10.0</junit.version>
   </properties>
   
   <dependencies>
       <dependency>
           <groupId>org.junit.jupiter</groupId>
           <artifactId>junit-jupiter</artifactId>
           <version>${junit.version}</version>
       </dependency>
   </dependencies>
   ```

2. **Use dependency management**: For consistent versions across modules
   ```xml
   <dependencyManagement>
       <dependencies>
           <!-- Dependencies with versions but not actually included -->
       </dependencies>
   </dependencyManagement>
   ```

3. **Organize multi-module projects**: Use parent POMs and modules

4. **Use .mvn/maven.config**: Store common command-line options

5. **Create custom Maven wrapper**: For consistent builds

   ```zsh
   mvn wrapper:wrapper
   ```

## Troubleshooting

### Common Issues and Solutions

1. **Compilation errors with Java 23 features**:
   - Ensure Maven compiler plugin is configured correctly
   - Check that JAVA_HOME points to Java 23

2. **Dependency conflicts**:
   ```zsh
   # Find conflicts
   mvn dependency:tree -Dverbose
   
   # Exclude problematic dependencies
   ```

3. **Out of memory errors**:
   ```zsh
   # Set Maven options in .zshrc
   echo 'export MAVEN_OPTS="-Xmx2g -XX:MaxPermSize=512m"' >> ~/.zshrc
   source ~/.zshrc
   ```

4. **Maven can't find local artifacts**:
   ```zsh
   # Force update
   mvn clean install -U
   ```

5. **Clean Maven cache**:
   ```zsh
   rm -rf ~/.m2/repository/
   ```

### Maven Debug Mode

```zsh
# Run Maven in debug mode
mvn clean install -X
```

## Advanced zsh Aliases for Maven

Add these to your `~/.zshrc` file:

```zsh
# Maven aliases
alias mvnc='mvn clean'
alias mvnci='mvn clean install'
alias mvnct='mvn clean test'
alias mvncp='mvn clean package'
alias mvnd='mvn deploy'
alias mvnp='mvn package'
alias mvni='mvn install'
alias mvnt='mvn test'
alias mvndt='mvn dependency:tree'
alias mvnfmt='mvn fmt:format'
```

After adding these, run:

```zsh
source ~/.zshrc
```

---

This guide covers the essentials of using Maven with Java 23 on macOS with zsh. For more detailed information, refer to the [official Maven documentation](https://maven.apache.org/guides/index.html).
