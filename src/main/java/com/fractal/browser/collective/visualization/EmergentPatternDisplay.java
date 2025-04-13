package com.fractal.browser.collective.visualization;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class EmergentPatternDisplay {
    private List<PatternNode> nodes;
    
    public EmergentPatternDisplay() {
        this.nodes = new ArrayList<>();
    }
    
    public void displayPattern(double[] coordinates) {
        if (coordinates == null || coordinates.length < 2) {
            return;
        }
        
        PatternNode node = new PatternNode("pattern-" + nodes.size());
        node.setX(coordinates[0]);
        node.setY(coordinates[1]);
        nodes.add(node);
    }
    
    public void updateDisplay(double[] newCoordinates) {
        if (newCoordinates == null || newCoordinates.length < 2 || nodes.isEmpty()) {
            return;
        }
        
        // Update the last added node
        PatternNode lastNode = nodes.get(nodes.size() - 1);
        lastNode.setX(newCoordinates[0]);
        lastNode.setY(newCoordinates[1]);
    }
    
    public List<PatternNode> getNodes() {
        return new ArrayList<>(nodes);
    }
}