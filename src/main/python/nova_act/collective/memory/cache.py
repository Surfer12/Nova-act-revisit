import abc
from typing import TypeVar, Generic, Optional

K = TypeVar('K')  # Key type
V = TypeVar('V')  # Value type

class CacheService(abc.ABC, Generic[K, V]):
    """
    Provides a caching layer for frequently accessed data.
    Improvement: Explicit caching abstraction.
    """
    @abc.abstractmethod
    async def get(self, key: K) -> Optional[V]:
        pass

    @abc.abstractmethod
    async def put(self, key: K, value: V, ttl_seconds: Optional[int] = None) -> None:
        pass

    @abc.abstractmethod
    async def invalidate(self, key: K) -> None:
        pass

    @abc.abstractmethod
    async def clear_all(self) -> None:
        pass