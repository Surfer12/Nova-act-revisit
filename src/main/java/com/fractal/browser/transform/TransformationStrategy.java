package com.fractal.browser.transform;

import com.fractal.browser.model.SemanticInstruction;

public interface TransformationStrategy {
    SemanticInstruction transform(String input);
}