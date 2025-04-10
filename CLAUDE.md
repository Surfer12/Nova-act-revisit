# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build/Lint/Test Commands
- Install dependencies: `pip install -e ".[dev]"` or `pip install -r requirements.txt`
- Build package: `python -m build --wheel --no-isolation --outdir dist/ .`
- Run tests: `pytest src/test/python/`
- Run a single test: `pytest src/test/python/nova_act/collective/test_collective_node.py::TestClassName::test_method_name -v`
- Lint code: `ruff check .` 
- Format code: `ruff format .`
- Type check: `mypy .`

## Code Style Guidelines
- Python >=3.9 compatibility required (per setup.py)
- Line length: 100 characters
- Formatting: Use ruff for formatting and linting
- Imports: Sort imports with ruff and use absolute imports
- Types: All functions must have complete type annotations (use Generic/TypeVar for generics)
- Error handling: Use specific exception types (like FractalBrowserException)
- Naming: Use snake_case for variables/functions, PascalCase for classes
- Class design: Follow ABC pattern for interfaces
- Documentation: Include docstrings (function purpose, parameters, returns)
- Logging: Use Python's logging module instead of print statements