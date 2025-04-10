import abc
from typing import Generic, TypeVar
from .event import Event

T = TypeVar('T', bound=Event)

class EventListener(abc.ABC, Generic[T]):
    """Interface for event listeners."""
    @abc.abstractmethod
    async def on_event(self, event: T) -> None:
        pass