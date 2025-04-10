import abc
import uuid
from typing import Any, Type, Callable, Coroutine

# Type hint for the async receiver function
ReceiverCallback = Callable[[uuid.UUID, Any], Coroutine[Any, Any, None]]

class CommunicationProtocol(abc.ABC):
    """
    Defines a standard way for nodes to communicate.
    Improvement: Abstracts communication mechanisms.
    """

    @abc.abstractmethod
    async def send(self, target_node_id: uuid.UUID, payload: Any) -> None:
        """Send a message payload to a specific node."""
        pass

    @abc.abstractmethod
    async def broadcast(self, payload: Any) -> None:
        """Broadcast a message payload to relevant nodes."""
        pass

    @abc.abstractmethod
    def register_receiver(self, payload_type: Type, receiver: ReceiverCallback) -> None:
        """Register an async listener to receive messages of a specific type."""
        pass

# InsightExchange, BifurcationBroadcast could implement or use this.