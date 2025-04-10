from datetime import datetime
from typing import Any, Collection
from .event import Event
from nova_act.collective.core.insight import Insight # Assumes Insight exists

class PatternDetectedEvent(Event):
    def __init__(self, source: Any, pattern_description: str, related_insights: Collection[Insight]):
        self._timestamp = datetime.now()
        self._source = source
        self._pattern_description = pattern_description
        self._related_insights = related_insights

    def get_timestamp(self) -> datetime: return self._timestamp
    def get_source(self) -> Any: return self._source
    def get_pattern_description(self) -> str: return self._pattern_description
    def get_related_insights(self) -> Collection[Insight]: return self._related_insights