package com.sortalgo.ui;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

/**
 * Panel that displays information about the current sorting algorithm and metrics.
 */
public class InfoPanel extends JPanel {
    
    private JLabel algorithmLabel;
    private JLabel comparisonsLabel;
    private JLabel swapsLabel;
    private JLabel timeLabel;
    
    private int comparisons;
    private int swaps;
    private long executionTime;
    
    public InfoPanel() {
        setLayout(new GridLayout(1, 4, 10, 0));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Algorithm name
        algorithmLabel = new JLabel("Algorithm: None");
        algorithmLabel.setHorizontalAlignment(JLabel.CENTER);
        algorithmLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Comparisons counter
        comparisonsLabel = new JLabel("Comparisons: 0");
        comparisonsLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Swaps counter
        swapsLabel = new JLabel("Swaps: 0");
        swapsLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Execution time
        timeLabel = new JLabel("Time: 0 ms");
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        
        add(algorithmLabel);
        add(comparisonsLabel);
        add(swapsLabel);
        add(timeLabel);
        
        resetMetrics();
    }
    
    public void setAlgorithmName(String name) {
        algorithmLabel.setText("Algorithm: " + name);
    }
    
    public void updateComparisons(int comparisons) {
        this.comparisons = comparisons;
        NumberFormat formatter = NumberFormat.getIntegerInstance();
        comparisonsLabel.setText("Comparisons: " + formatter.format(comparisons));
    }
    
    public void updateSwaps(int swaps) {
        this.swaps = swaps;
        NumberFormat formatter = NumberFormat.getIntegerInstance();
        swapsLabel.setText("Swaps: " + formatter.format(swaps));
    }
    
    public void updateExecutionTime(long timeMs) {
        this.executionTime = timeMs;
        timeLabel.setText("Time: " + timeMs + " ms");
    }
    
    public void resetMetrics() {
        updateComparisons(0);
        updateSwaps(0);
        updateExecutionTime(0);
    }
}
