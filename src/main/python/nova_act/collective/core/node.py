import abc
import uuid
from enum import Enum

class NodeStatus(Enum):
    ACTIVE = 1
    INACTIVE = 2
    SYNCHRONIZING = 3
    ERROR = 4

class Node(abc.ABC):
    """
    Interface representing any participant in the collective network.
    Improvement: Abstracts the node concept for flexibility.
    """
    @abc.abstractmethod
    def get_id(self) -> uuid.UUID:
        pass

    @abc.abstractmethod
    def get_status(self) -> NodeStatus:
        pass

    @abc.abstractmethod
    async def initialize(self): # Async example
        pass

    @abc.abstractmethod
    async def shutdown(self): # Async example
        pass

    # Core interaction methods might go here or be handled via communication protocols

# Existing CollectiveNode would implement this
# from .node import Node, NodeStatus
# class CollectiveNode(Node): ...