import subprocess
import time
import json
from pathlib import Path
from typing import Dict, Any
import statistics

def run_java_tests() -> Dict[str, float]:
    print("Running Java performance tests...")
    java_results = {}
    
    # Run Java tests using Maven
    maven_cmd = ["mvn", "test", "-Dtest=ShadowWorkspacePerformanceTest"]
    result = subprocess.run(maven_cmd, capture_output=True, text=True)
    
    # Parse Java test output
    for line in result.stdout.split('\n'):
        if "took:" in line:
            test_name = line.split()[0]
            duration = float(line.split()[-2])
            java_results[test_name] = duration
    
    return java_results

def run_python_tests() -> Dict[str, float]:
    print("Running Python performance tests...")
    python_results = {}
    
    # Run Python tests
    python_script = Path(__file__).parent / "shadow_workspace_performance.py"
    result = subprocess.run(["python", str(python_script)], capture_output=True, text=True)
    
    # Parse Python test output
    for line in result.stdout.split('\n'):
        if "took:" in line:
            test_name = line.split()[0]
            duration = float(line.split()[-2])
            python_results[test_name] = duration
    
    return python_results

def compare_performance(java_results: Dict[str, float], python_results: Dict[str, float]) -> None:
    print("\nPerformance Comparison:")
    print("=" * 50)
    
    # Compare individual tests
    for test_name in set(java_results.keys()) | set(python_results.keys()):
        java_time = java_results.get(test_name, float('inf'))
        python_time = python_results.get(test_name, float('inf'))
        
        if java_time == float('inf') or python_time == float('inf'):
            continue
            
        ratio = java_time / python_time
        faster = "Java" if ratio < 1 else "Python"
        print(f"\n{test_name}:")
        print(f"  Java: {java_time:.2f} ms")
        print(f"  Python: {python_time:.2f} ms")
        print(f"  {faster} is {abs(1 - ratio):.2f}x faster")
    
    # Calculate overall statistics
    java_avg = statistics.mean(java_results.values())
    python_avg = statistics.mean(python_results.values())
    overall_ratio = java_avg / python_avg
    overall_faster = "Java" if overall_ratio < 1 else "Python"
    
    print("\nOverall Performance:")
    print(f"  Java Average: {java_avg:.2f} ms")
    print(f"  Python Average: {python_avg:.2f} ms")
    print(f"  {overall_faster} is {abs(1 - overall_ratio):.2f}x faster overall")

def main():
    # Run tests multiple times for more accurate comparison
    java_results = []
    python_results = []
    
    for i in range(3):  # Run 3 times
        print(f"\nRun {i + 1}:")
        java_results.append(run_java_tests())
        python_results.append(run_python_tests())
    
    # Average the results
    avg_java = {}
    avg_python = {}
    
    for test_name in set().union(*[r.keys() for r in java_results]):
        avg_java[test_name] = statistics.mean([r.get(test_name, float('inf')) for r in java_results])
    
    for test_name in set().union(*[r.keys() for r in python_results]):
        avg_python[test_name] = statistics.mean([r.get(test_name, float('inf')) for r in python_results])
    
    # Compare averaged results
    compare_performance(avg_java, avg_python)

if __name__ == "__main__":
    main() 