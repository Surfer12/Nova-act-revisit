import abc
from typing import Optional, TypeVar, Type, Any

T = TypeVar('T')

class ConfigurationService(abc.ABC):
    """Provides access to system configuration. Improvement: Centralized config."""
    
    @abc.abstractmethod
    def get_string(self, key: str, default: Optional[str] = None) -> Optional[str]:
        pass
    
    @abc.abstractmethod
    def get_int(self, key: str, default: Optional[int] = None) -> Optional[int]:
        pass
    
    @abc.abstractmethod
    def get_bool(self, key: str, default: Optional[bool] = None) -> Optional[bool]:
        pass
    
    @abc.abstractmethod
    def get_object(self, key: str, obj_type: Type[T], default: Optional[T] = None) -> Optional[T]:
        pass