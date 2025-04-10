import abc
from enum import Enum
from typing import NamedTuple, Optional

class HealthStatus(Enum):
    UP = 1
    DOWN = 2
    DEGRADED = 3

class HealthReport(NamedTuple):
    status: HealthStatus
    message: Optional[str] = None

class HealthIndicator(abc.ABC):
    """Interface for components to report their health status."""
    @abc.abstractmethod
    async def check_health(self) -> HealthReport:
        pass