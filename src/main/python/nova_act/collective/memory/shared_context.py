import abc
from typing import Optional, Any, TypeVar, Generic

T = TypeVar('T')

# Placeholder for parameter object
# class NetworkParameters: ...

class SharedContextRegistry(abc.ABC):
    """
    Manages shared context essential for collective understanding.
    Improvement: More specific methods for context items.
    """

    # Example context items: Replace with actual needed context
    @abc.abstractmethod
    async def get_current_collective_goal(self) -> Optional[str]:
        pass
    
    @abc.abstractmethod
    async def set_current_collective_goal(self, goal: str) -> None:
        pass

    # @abc.abstractmethod
    # async def get_active_network_parameters(self) -> Optional[NetworkParameters]:
    #     pass
    # @abc.abstractmethod
    # async def set_active_network_parameters(self, params: NetworkParameters) -> None:
    #     pass

    @abc.abstractmethod
    async def get_context_item(self, key: str, item_type: type[T]) -> Optional[T]:
        pass

    @abc.abstractmethod
    async def set_context_item(self, key: str, value: Any) -> None:
        pass

    @abc.abstractmethod
    async def remove_context_item(self, key: str) -> None:
        pass

    # Potentially: def subscribe_to_context_changes(self, key: str, listener: Callable): ...