import abc
from typing import TypeVar, Callable, Coroutine, Any
from types import TracebackType
from typing import Optional, Type

T = TypeVar('T')

class LockHandle(abc.ABC):
    """Context manager for distributed locks."""
    @abc.abstractmethod
    async def __aenter__(self) -> 'LockHandle':
        pass
    
    @abc.abstractmethod
    async def __aexit__(self,
                       exc_type: Optional[Type[BaseException]],
                       exc_value: Optional[BaseException],
                       traceback: Optional[TracebackType]) -> Optional[bool]:
         """Releases the lock."""
         pass

class ConsistencyManager(abc.ABC):
    """
    Manages consistency for distributed state operations.
    Improvement: Explicit interface for consistency mechanisms (locks, transactions).
    """

    @abc.abstractmethod
    def acquire_lock(self, lock_key: str, timeout_seconds: float) -> LockHandle:
        """Returns an async context manager for the lock."""
        pass

    @abc.abstractmethod
    async def execute_transactional(self, operation: Callable[[], Coroutine[Any, Any, T]]) -> T:
        """
        Execute an async operation within a distributed transaction (simplified concept).
        Implementations might use two-phase commit or other protocols.
        """
        pass