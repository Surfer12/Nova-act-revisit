# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build/Lint/Test Commands
- **Java (Maven)**: `mvn compile`, `mvn test`, `mvn test -Dtest=TestClassName#testMethodName`
- **Java (Gradle)**: `./gradlew build`, `./gradlew test`, `./gradlew test --tests com.fractal.browser.TestClassName.testMethodName`
- **Python**: `pip install -r requirements.txt`, `python -m pytest src/test/python/`, `pytest src/test/python/nova_act/collective/test_collective_node.py::TestClassName::test_method_name -v`
- **Python Checks**: `ruff check .`, `ruff format .`, `mypy .`
- **Rust**: `cargo build`, `cargo test`, `cargo test test_name`

## Code Style Guidelines
- **Java**: Java 11 (Maven) or Java 21 (Gradle) compatibility, follow JUnit 5 testing patterns
- **Python**: Python >=3.9, 100 character line length, snake_case for variables/functions, PascalCase for classes
- **Imports**: Group by external libraries first, then internal modules; use absolute imports
- **Types**: All Python functions must have complete type annotations, use Generic/TypeVar for generics
- **Error Handling**: Use specific exception types (FractalBrowserException, etc.)
- **Documentation**: Include docstrings/Javadoc (purpose, parameters, returns)
- **Logging**: Use appropriate logging framework (SLF4J for Java, logging module for Python)
- **Testing**: Write unit tests for all new functionality and modifications