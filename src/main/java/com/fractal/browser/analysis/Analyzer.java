package com.fractal.browser.analysis;

public interface Analyzer<T, R> {
    R analyze(T input);
    void setDepth(int depth);
}