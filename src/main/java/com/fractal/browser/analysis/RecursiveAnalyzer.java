package com.fractal.browser.analysis;

import com.fractal.browser.model.JournalEntry;
import com.fractal.browser.model.AnalysisResult;
import com.fractal.browser.analysis.PatternRecognizer;
import java.util.Set;

public class RecursiveAnalyzer implements Analyzer<JournalEntry, AnalysisResult> {
    private final PatternRecognizer recognizer;
    private int currentDepth;
    
    public RecursiveAnalyzer(PatternRecognizer recognizer) {
        this.recognizer = recognizer;
        this.currentDepth = 0;
    }
    
    @Override
    public AnalysisResult analyze(JournalEntry input) {
        // Use pattern recognizer to find patterns
        Set<String> patterns = recognizer.recognizePatterns(input);
        
        // Build analysis result
        AnalysisResult.Builder builder = new AnalysisResult.Builder();
        
        // Add patterns as insights
        for (String pattern : patterns) {
            builder.addInsight(pattern);
        }
        
        // Add metrics
        builder.addMetric("depth", (double) currentDepth);
        builder.addMetric("patternCount", (double) patterns.size());
        
        return builder.build();
    }
    
    @Override
    public void setDepth(int depth) {
        this.currentDepth = depth;
    }
}