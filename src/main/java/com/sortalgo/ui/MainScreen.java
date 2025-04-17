package com.sortalgo.ui;

import com.sortalgo.algorithm.*;
import com.sortalgo.model.AlgorithmObserver;
import com.sortalgo.model.SortingEvent;
import com.sortalgo.model.SortingModel;
import com.sortalgo.util.ArrayGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The main screen of the application that contains the visualization panel and controls.
 */
public class MainScreen extends JFrame implements AlgorithmObserver {
    
    private SortingPanel sortingPanel;
    private ControlPanel controlPanel;
    private InfoPanel infoPanel;
    private SortingModel model;
    
    public MainScreen() {
        setTitle("Sort Algorithm Visualizer - Demonstration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Initialize model
        model = new SortingModel();
        model.registerObserver(this);
        
        // Initialize UI components
        initComponents();
        
        // Register window close handler
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                model.stopSorting();
            }
        });
    }
    
    private void initComponents() {
        // Main layout
        setLayout(new BorderLayout());
        
        // Sorting visualization panel
        sortingPanel = new SortingPanel();
        add(sortingPanel, BorderLayout.CENTER);
        
        // Information panel (showing algorithm details and metrics)
        infoPanel = new InfoPanel();
        add(infoPanel, BorderLayout.NORTH);
        
        // Control panel (buttons, sliders, etc.)
        controlPanel = new ControlPanel(this);
        add(controlPanel, BorderLayout.SOUTH);
        
        // Initial array generation
        generateNewArray(50);
    }
    
    // Method called by the control panel
    public void startSorting(String algorithmName) {
        SortingAlgorithm algorithm = createAlgorithm(algorithmName);
        if (algorithm == null) return;
        
        // Set algorithm in the model and start sorting
        infoPanel.setAlgorithmName(algorithmName);
        infoPanel.resetMetrics();
        model.setAlgorithm(algorithm);
        model.startSorting();
    }
    
    public void stopSorting() {
        model.stopSorting();
    }
    
    public void stepSorting() {
        model.stepSorting();
    }
    
    public void generateNewArray(int size) {
        // Stop any ongoing sorting
        model.stopSorting();
        
        // Generate new array
        int[] array = ArrayGenerator.generateRandomArray(size, 1, 100);
        model.setArray(array);
        
        // Update UI
        sortingPanel.setArray(array.clone());
        sortingPanel.clearHighlights();
        sortingPanel.repaint();
        infoPanel.resetMetrics();
    }
    
    public void setCustomArray(String arrayInput) {
        try {
            // Parse custom array input
            String[] elements = arrayInput.split(",");
            int[] array = new int[elements.length];
            
            for (int i = 0; i < elements.length; i++) {
                array[i] = Integer.parseInt(elements[i].trim());
            }
            
            if (array.length == 0) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter at least one number", 
                    "Invalid Input", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Stop any ongoing sorting
            model.stopSorting();
            
            // Set new array
            model.setArray(array);
            
            // Update UI
            sortingPanel.setArray(array.clone());
            sortingPanel.clearHighlights();
            sortingPanel.repaint();
            infoPanel.resetMetrics();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter valid numbers separated by commas", 
                "Invalid Input", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setAnimationSpeed(int speed) {
        model.setAnimationDelay(510 - speed); // Invert scale for more intuitive control
    }
    
    private SortingAlgorithm createAlgorithm(String name) {
        switch (name) {
            case "Bubble Sort":
                return new BubbleSort();
            case "Selection Sort":
                return new SelectionSort();
            case "Insertion Sort":
                return new InsertionSort();
            case "Quick Sort":
                return new QuickSort();
            case "Merge Sort":
                return new MergeSort();
            default:
                return null;
        }
    }
    
    @Override
    public void onSortingStep(SortingEvent event) {
        // Update visualization with new array state and highlighted indices
        sortingPanel.setArray(event.getCurrentArray().clone());
        sortingPanel.setHighlightIndices(event.getHighlightIndices());
        sortingPanel.repaint();
        
        // Update metrics
        infoPanel.updateComparisons(event.getComparisons());
        infoPanel.updateSwaps(event.getSwaps());
        infoPanel.updateExecutionTime(event.getExecutionTime());
    }
    
    @Override
    public void onSortingComplete(SortingEvent event) {
        sortingPanel.setArray(event.getCurrentArray().clone());
        sortingPanel.clearHighlights();
        sortingPanel.repaint();
        
        // Update final metrics
        infoPanel.updateComparisons(event.getComparisons());
        infoPanel.updateSwaps(event.getSwaps());
        infoPanel.updateExecutionTime(event.getExecutionTime());
        
        // Enable controls after sorting is complete
        controlPanel.setSortingActive(false);
        
        // Show completion message
        JOptionPane.showMessageDialog(this, 
                "Sorting completed in " + event.getExecutionTime() + " ms\n" +
                "Comparisons: " + event.getComparisons() + "\n" +
                "Swaps: " + event.getSwaps(),
                "Sorting Complete", 
                JOptionPane.INFORMATION_MESSAGE);
    }
}
