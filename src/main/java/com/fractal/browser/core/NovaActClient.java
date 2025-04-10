package com.fractal.browser.core;

public interface NovaActClient {
    void initialize();
    void executeAct(Act act);
    void shutdown();
}