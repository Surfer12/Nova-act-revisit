import abc
from typing import Dict, Optional

Tags = Optional[Dict[str, str]]

class MetricsRegistry(abc.ABC):
    """Interface for registering and reporting metrics. Improvement: Monitoring hooks."""
    
    @abc.abstractmethod
    def increment_counter(self, name: str, tags: Tags = None, value: int = 1) -> None:
        pass
    
    @abc.abstractmethod
    def record_timer(self, name: str, duration_seconds: float, tags: Tags = None) -> None:
        pass
    
    @abc.abstractmethod
    def record_gauge(self, name: str, value: float, tags: Tags = None) -> None:
        pass
    # Integration with Prometheus client or similar preferred