import abc
import uuid
from datetime import datetime
from enum import Enum
from typing import Any, Dict, Optional, Set

class InsightType(Enum):
    RAW_OBSERVATION = 1
    DERIVED_PATTERN = 2
    HYPOTHESIS = 3
    INTEGRATED_UNDERSTANDING = 4
    EXTERNAL_INPUT = 5

class Insight(abc.ABC):
    """
    Represents a unit of knowledge, observation, or pattern within the collective.
    Improvement: Makes Insight an explicit, structured object.
    """

    @abc.abstractmethod
    def get_id(self) -> uuid.UUID:
        pass

    @abc.abstractmethod
    def get_origin_node_id(self) -> uuid.UUID:
        pass

    @abc.abstractmethod
    def get_timestamp(self) -> datetime:
        pass

    @abc.abstractmethod
    def get_type(self) -> InsightType:
        pass

    @abc.abstractmethod
    def get_content(self) -> Any:
        # Could be refined with generics (TypeVar) or specific subtypes
        pass

    @abc.abstractmethod
    def get_confidence_score(self) -> Optional[float]:
        # Optional: 0.0 to 1.0
        pass

    @abc.abstractmethod
    def get_resonance_score(self) -> Optional[float]:
         # Optional: How much it resonates/impacts
        pass

    @abc.abstractmethod
    def get_metadata(self) -> Dict[str, str]:
        # For tags, context, etc.
        pass

    # Potentially links to related insights:
    # @abc.abstractmethod
    # def get_related_insight_ids(self) -> Set[uuid.UUID]:
    #     pass

# Example concrete implementation skeleton
class BasicInsight(Insight):
    def __init__(self, origin_node_id: uuid.UUID, type: InsightType, content: Any, **kwargs):
        self._id = uuid.uuid4()
        self._origin_node_id = origin_node_id
        self._timestamp = datetime.now()
        self._type = type
        self._content = content
        self._confidence = kwargs.get('confidence_score')
        self._resonance = kwargs.get('resonance_score')
        self._metadata = kwargs.get('metadata', {})

    def get_id(self) -> uuid.UUID: return self._id
    def get_origin_node_id(self) -> uuid.UUID: return self._origin_node_id
    def get_timestamp(self) -> datetime: return self._timestamp
    def get_type(self) -> InsightType: return self._type
    def get_content(self) -> Any: return self._content
    def get_confidence_score(self) -> Optional[float]: return self._confidence
    def get_resonance_score(self) -> Optional[float]: return self._resonance
    def get_metadata(self) -> Dict[str, str]: return self._metadata