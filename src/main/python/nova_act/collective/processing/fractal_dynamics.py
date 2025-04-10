import abc
from typing import TypeVar, Generic, Dict, Any

S = TypeVar('S') # State type
C = TypeVar('C') # Constant/Context type

class FractalDynamicsEngine(abc.ABC, Generic[S, C]):
    """
    Encapsulates the core fractal calculation logic (e.g., z = z^2 + c).
    Improvement: Separates core algorithm from its application context.
    """

    @abc.abstractmethod
    def iterate(self, current_state: S, context: C, parameters: Dict[str, Any]) -> S:
        """Performs one iteration."""
        pass

    @abc.abstractmethod
    def has_converged(self, state: S, parameters: Dict[str, Any]) -> bool:
        """Checks for convergence."""
        pass

    @abc.abstractmethod
    def has_escaped(self, state: S, parameters: Dict[str, Any]) -> bool:
        """Checks for escape condition."""
        pass

# CollectiveProcessor would *use* an instance of this engine.
# from .fractal_dynamics_engine import FractalDynamicsEngine
# class CollectiveProcessor:
#     def __init__(self, engine: FractalDynamicsEngine[CollectiveState, CollectiveContext]):
#         self.engine = engine
#     # ... methods using the engine ...