import time
import threading
import psutil
import statistics
from typing import List, Dict, Any
from dataclasses import dataclass
from concurrent.futures import ThreadPoolExecutor

@dataclass
class SemanticInstruction:
    initial_value: float
    constant_value: float
    metadata: Dict[str, Any] = None

    def __post_init__(self):
        if self.metadata is None:
            self.metadata = {}

    def add_metadata(self, key: str, value: Any) -> None:
        self.metadata[key] = value

@dataclass
class ProcessingResult:
    results: Dict[str, Any]
    iterations: int
    convergence_value: float
    context_id: str

class NodeFractalProcessor:
    def __init__(self, max_iterations: int, convergence_threshold: float):
        self.max_iterations = max_iterations
        self.convergence_threshold = convergence_threshold

    def process(self, instruction: SemanticInstruction, context_id: str) -> ProcessingResult:
        z = instruction.initial_value
        c = instruction.constant_value
        iterations = 0
        convergence_value = 0.0

        while iterations < self.max_iterations:
            z = z * z + c
            convergence_value = abs(z)
            iterations += 1
            if convergence_value < self.convergence_threshold:
                break

        results = {
            "final_value": z,
            "iterations": iterations,
            "converged": convergence_value < self.convergence_threshold
        }

        return ProcessingResult(
            results=results,
            iterations=iterations,
            convergence_value=convergence_value,
            context_id=context_id
        )

def test_single_shadow_operation():
    processor = NodeFractalProcessor(100, 0.001)
    instruction = SemanticInstruction(0.5, 0.3)
    instruction.add_metadata("depth", 0)
    instruction.add_metadata("shadow_id", "base")

    start_time = time.time()
    result = processor.process(instruction, "test-context")
    duration = (time.time() - start_time) * 1000  # Convert to milliseconds

    print(f"Single shadow operation took: {duration:.2f} ms")
    assert result is not None
    assert duration < 100  # Should complete within 100ms

def create_nested_shadows(processor: NodeFractalProcessor, depth: int) -> None:
    if depth <= 0:
        return

    instruction = SemanticInstruction(0.5, 0.3)
    instruction.add_metadata("depth", depth)
    instruction.add_metadata("shadow_id", f"shadow_{depth}")

    result = processor.process(instruction, f"shadow-context-{depth}")
    assert result is not None

    create_nested_shadows(processor, depth - 1)

def test_nested_shadow_operations():
    processor = NodeFractalProcessor(100, 0.001)
    shadow_depth = 5
    warmup_iterations = 10
    measurement_iterations = 100
    durations = []

    # Warmup phase
    for _ in range(warmup_iterations):
        create_nested_shadows(processor, shadow_depth)

    # Measurement phase
    for _ in range(measurement_iterations):
        start_time = time.time()
        create_nested_shadows(processor, shadow_depth)
        duration = (time.time() - start_time) * 1000
        durations.append(duration)

    avg_duration = statistics.mean(durations)
    max_duration = max(durations)

    print(f"Nested shadow operations (depth {shadow_depth}):")
    print(f"Average duration: {avg_duration:.2f} ms")
    print(f"Maximum duration: {max_duration:.2f} ms")

    assert avg_duration < 500  # Average should be under 500ms
    assert max_duration < 1000  # Max should be under 1000ms

def run_parallel_shadows(processor: NodeFractalProcessor, count: int) -> None:
    def process_shadow(shadow_id: int) -> None:
        instruction = SemanticInstruction(0.5, 0.3)
        instruction.add_metadata("shadow_id", f"parallel_{shadow_id}")
        result = processor.process(instruction, f"parallel-context-{shadow_id}")
        assert result is not None

    with ThreadPoolExecutor(max_workers=count) as executor:
        futures = [executor.submit(process_shadow, i) for i in range(count)]
        for future in futures:
            future.result()

def test_parallel_shadow_operations():
    processor = NodeFractalProcessor(100, 0.001)
    warmup_iterations = 10
    measurement_iterations = 100
    parallel_count = 5
    durations = []

    # Warmup phase
    for _ in range(warmup_iterations):
        run_parallel_shadows(processor, parallel_count)

    # Measurement phase
    for _ in range(measurement_iterations):
        start_time = time.time()
        run_parallel_shadows(processor, parallel_count)
        duration = (time.time() - start_time) * 1000
        durations.append(duration)

    avg_duration = statistics.mean(durations)
    print(f"Parallel shadow operations ({parallel_count} parallel):")
    print(f"Average duration: {avg_duration:.2f} ms")

    assert avg_duration < 300  # Should complete within 300ms

def test_shadow_memory_usage():
    processor = NodeFractalProcessor(100, 0.001)
    results = []
    process = psutil.Process()

    # Initial memory usage
    initial_memory = process.memory_info().rss

    # Create multiple shadow operations
    for i in range(1000):
        instruction = SemanticInstruction(0.5, 0.3)
        instruction.add_metadata("shadow_id", f"memory_test_{i}")
        result = processor.process(instruction, "memory-test-context")
        results.append(result)

    # Final memory usage
    final_memory = process.memory_info().rss
    memory_used = final_memory - initial_memory

    print(f"Memory used by 1000 shadow operations: {memory_used / 1024 / 1024:.2f} MB")
    assert memory_used < 100 * 1024 * 1024  # Should use less than 100MB

if __name__ == "__main__":
    print("Running performance tests...")
    test_single_shadow_operation()
    test_nested_shadow_operations()
    test_parallel_shadow_operations()
    test_shadow_memory_usage() 