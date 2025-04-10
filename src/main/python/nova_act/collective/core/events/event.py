import abc
from typing import Type, Callable, Coroutine, Any, List, Dict, TypeVar
from datetime import datetime

class Event(abc.ABC):
    """Base class for all events in the system."""
    @abc.abstractmethod
    def get_timestamp(self) -> datetime:
        pass

    @abc.abstractmethod
    def get_source(self) -> Any:
        pass