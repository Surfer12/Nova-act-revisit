import abc
from typing import Type, Callable, Coroutine, Any, List, Dict, TypeVar
from .event import Event
from .event_listener import EventListener

T = TypeVar('T', bound=Event)
# Could also support simple async functions as listeners
Handler = Callable[[Any], Coroutine[Any, Any, None]]

class EventBus(abc.ABC):
     """Simple event bus interface."""
     @abc.abstractmethod
     def subscribe(self, event_type: Type[T], listener: EventListener[T] | Handler) -> None:
         pass
     @abc.abstractmethod
     def unsubscribe(self, event_type: Type[T], listener: EventListener[T] | Handler) -> None:
         pass
     @abc.abstractmethod
     async def publish(self, event: Event) -> None: # Async publish
         pass
# Need a concrete implementation (e.g., using asyncio queues, listener registry)