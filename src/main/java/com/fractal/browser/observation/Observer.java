package com.fractal.browser.observation;

import com.fractal.browser.model.Pattern;

public interface Observer {
    void onPatternDetected(Pattern pattern);
    void onMetricUpdated(String metric, double value);
}