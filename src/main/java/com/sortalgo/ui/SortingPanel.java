package com.sortalgo.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Panel for visualizing the sorting process.
 */
public class SortingPanel extends JPanel {
    
    private int[] array;
    private Set<Integer> highlightIndices;
    
    private static final Color BAR_COLOR = new Color(65, 105, 225);
    private static final Color HIGHLIGHT_COLOR = new Color(220, 20, 60);
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 240);
    private static final int PADDING = 20;
    
    public SortingPanel() {
        this.array = new int[0];
        this.highlightIndices = new HashSet<>();
        setBackground(BACKGROUND_COLOR);
    }
    
    public void setArray(int[] array) {
        this.array = array;
    }
    
    public void setHighlightIndices(int[] indices) {
        this.highlightIndices.clear();
        if (indices != null) {
            for (int index : indices) {
                this.highlightIndices.add(index);
            }
        }
    }
    
    public void clearHighlights() {
        this.highlightIndices.clear();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Enable anti-aliasing for smoother graphics
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (array == null || array.length == 0) return;
        
        int width = getWidth() - 2 * PADDING;
        int height = getHeight() - 2 * PADDING;
        
        // Find max value to scale bars
        int maxValue = Arrays.stream(array).max().orElse(1);
        
        // Calculate bar width based on array size
        int barWidth = Math.max(1, width / array.length);
        
        // Draw each element as a bar
        for (int i = 0; i < array.length; i++) {
            // Calculate bar height proportional to value
            int barHeight = (int) (((double) array[i] / maxValue) * height);
            
            // Calculate bar position
            int x = PADDING + i * barWidth;
            int y = getHeight() - PADDING - barHeight;
            
            // Draw bar with appropriate color
            g2d.setColor(highlightIndices.contains(i) ? HIGHLIGHT_COLOR : BAR_COLOR);
            g2d.fillRect(x, y, barWidth - 1, barHeight);
            
            // Draw bar outline
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, barWidth - 1, barHeight);
            
            // Draw value on top of the bar if there's enough space
            if (barWidth > 20) {
                g2d.setColor(Color.BLACK);
                g2d.drawString(String.valueOf(array[i]), x + 2, y - 5);
            }
        }
    }
}
