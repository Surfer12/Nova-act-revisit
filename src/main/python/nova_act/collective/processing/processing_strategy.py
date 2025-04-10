import abc
from typing import Collection, List
from nova_act.collective.core.insight import Insight # Assumes Insight class exists

class ProcessingStrategy(abc.ABC):
    """
    Defines a strategy for processing insights or collective state.
    Improvement: Allows swapping different processing algorithms.
    """

    @abc.abstractmethod
    async def process(self, inputs: Collection[Insight]) -> Collection[Insight]:
        """
        Processes a collection of insights or updates the collective state (async).
        Returns a collection of resulting insights or state updates.
        """
        pass

    @abc.abstractmethod
    def get_name(self) -> str:
        """Identifier for the strategy."""
        pass

# Concrete classes like ConsensusFormation, CrossNodeIntegration could implement this.