# Assume BifurcationBroadcast uses CommunicationProtocol or similar
import uuid
from typing import Any, Dict, Optional
from .protocol import CommunicationProtocol # Assuming protocol exists

class BifurcationEvent:
    def __init__(self, type: str, data: Any, metadata: Dict[str, str], source: Optional[uuid.UUID]):
        self.type = type
        self.data = data
        self.metadata = metadata
        self.source = source

class BifurcationBroadcast: # Or could implement CommunicationProtocol directly
    def __init__(self, comms: CommunicationProtocol):
        self._comms = comms # Inject dependency

    async def broadcast_potential_bifurcation(
        self,
        bifurcation_type: str, # e.g., "STATE_SHIFT", "PATTERN_EMERGENCE"
        event_data: Any,       # Details about the trigger
        metadata: Dict[str, str], # Severity, confidence, etc.
        originating_node: Optional[uuid.UUID] = None # Optional: source
        # Potentially add targeting info if not a full broadcast
    ):
        """Improvement: Add parameters for context and targeting."""
        payload = BifurcationEvent(bifurcation_type, event_data, metadata, originating_node)
        await self._comms.broadcast(payload) # Use underlying protocol