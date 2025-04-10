# core/nova_act_client.py
from abc import ABC, abstractmethod
import logging

class NovaActClient(ABC):
    @abstractmethod
    def initialize(self):
        pass
        
    @abstractmethod
    def execute_act(self, act):
        pass
        
    @abstractmethod
    def shutdown(self):
        pass

# core/act.py
class Act(ABC):
    @abstractmethod
    def execute(self):
        pass
        
    @abstractmethod
    def get_result(self):
        pass

# exceptions/fractal_browser_exception.py
class FractalBrowserException(Exception):
    pass

# impl/default_nova_act_client.py
import logging

class DefaultNovaActClient(NovaActClient):
    def __init__(self):
        self.logger = logging.getLogger(__name__)
        
    def initialize(self):
        self.logger.info("Initializing NovaAct client")
        
    def execute_act(self, act):
        self.logger.debug(f"Executing act: {act}")
        
    def shutdown(self):
        self.logger.info("Shutting down NovaAct client")

# collections/fractal_hash_map.py
from typing import TypeVar, Generic, Dict

K = TypeVar('K')
V = TypeVar('V')

class FractalHashMap(Generic[K, V]):
    def __init__(self, max_depth: int):
        self.max_depth = max_depth
        self._data: Dict[K, V] = {}
        
    def put_recursive(self, key: K, value: V, depth: int):
        if depth > self.max_depth:
            raise FractalBrowserException("Maximum recursion depth exceeded")

# analysis/analyzer.py
from abc import ABC, abstractmethod
from typing import Generic, TypeVar

T = TypeVar('T')
R = TypeVar('R')

class Analyzer(Generic[T, R], ABC):
    @abstractmethod
    def analyze(self, input: T) -> R:
        pass
        
    @abstractmethod
    def set_depth(self, depth: int):
        pass

class JournalAnalyzer(Analyzer):
    def analyze(self, input):
        # Implementation
        pass
        
    def set_depth(self, depth):
        # Implementation
        pass

# cli/fractal_browser_cli.py
class FractalBrowserCLI:
    def __init__(self, client: NovaActClient):
        self.client = client
        
    def start(self):
        # Implementation
        pass
