from enum import Enum

class ProcessingScale(Enum):
    MICRO = 1 # Fine-grained detail
    MESO = 2  # Local patterns
    MACRO = 3 # System-wide patterns
    META = 4  # Process of processing