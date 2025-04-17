package com.sortalgo.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel containing controls for the sorting visualization.
 */
public class ControlPanel extends JPanel {
    
    private final MainScreen mainScreen;
    private final JButton startButton;
    private final JButton stopButton;
    private final JButton stepButton;
    private final JButton generateButton;
    private final JComboBox<String> algorithmComboBox;
    private final JSlider speedSlider;
    private final JSlider arraySizeSlider;
    private final JTextField customArrayField;
    private final JButton applyCustomArrayButton;
    
    public ControlPanel(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Algorithm selection panel
        JPanel algorithmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        algorithmPanel.add(new JLabel("Algorithm:"));
        
        String[] algorithms = {
            "Bubble Sort", 
            "Selection Sort", 
            "Insertion Sort", 
            "Quick Sort", 
            "Merge Sort"
        };
        
        algorithmComboBox = new JComboBox<>(algorithms);
        algorithmPanel.add(algorithmComboBox);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSorting();
            }
        });
        
        stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopSorting();
            }
        });
        stopButton.setEnabled(false);
        
        stepButton = new JButton("Step");
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stepSorting();
            }
        });
        
        generateButton = new JButton("Generate New Array");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateNewArray();
            }
        });
        
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(generateButton);
        
        // Sliders panel
        JPanel slidersPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        
        // Speed slider
        slidersPanel.add(new JLabel("Animation Speed:"));
        speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 500, 200);
        speedSlider.addChangeListener(e -> {
            mainScreen.setAnimationSpeed(speedSlider.getValue());
        });
        speedSlider.setMajorTickSpacing(100);
        speedSlider.setMinorTickSpacing(25);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        slidersPanel.add(speedSlider);
        
        // Array size slider
        slidersPanel.add(new JLabel("Array Size:"));
        arraySizeSlider = new JSlider(JSlider.HORIZONTAL, 5, 200, 50);
        arraySizeSlider.addChangeListener(e -> {
            if (!arraySizeSlider.getValueIsAdjusting()) {
                mainScreen.generateNewArray(arraySizeSlider.getValue());
            }
        });
        arraySizeSlider.setMajorTickSpacing(50);
        arraySizeSlider.setMinorTickSpacing(10);
        arraySizeSlider.setPaintTicks(true);
        arraySizeSlider.setPaintLabels(true);
        slidersPanel.add(arraySizeSlider);
        
        // Custom array input panel
        JPanel customArrayPanel = new JPanel(new BorderLayout(5, 0));
        customArrayPanel.add(new JLabel("Custom Array (comma separated):"), BorderLayout.WEST);
        
        customArrayField = new JTextField("5, 2, 9, 1, 3, 8, 4, 7, 6");
        customArrayPanel.add(customArrayField, BorderLayout.CENTER);
        
        applyCustomArrayButton = new JButton("Apply");
        applyCustomArrayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyCustomArray();
            }
        });
        customArrayPanel.add(applyCustomArrayButton, BorderLayout.EAST);
        
        // Add all panels to the control panel
        JPanel topControls = new JPanel(new BorderLayout());
        topControls.add(algorithmPanel, BorderLayout.NORTH);
        topControls.add(buttonPanel, BorderLayout.CENTER);
        
        add(topControls, BorderLayout.NORTH);
        add(slidersPanel, BorderLayout.CENTER);
        add(customArrayPanel, BorderLayout.SOUTH);
    }
    
    private void startSorting() {
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
        mainScreen.startSorting(selectedAlgorithm);
        setSortingActive(true);
    }
    
    private void stopSorting() {
        mainScreen.stopSorting();
        setSortingActive(false);
    }
    
    private void stepSorting() {
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
        
        // If sorting is not active, initialize the algorithm first
        if (startButton.isEnabled()) {
            mainScreen.startSorting(selectedAlgorithm);
            setSortingActive(true);
        }
        
        mainScreen.stepSorting();
    }
    
    private void generateNewArray() {
        mainScreen.generateNewArray(arraySizeSlider.getValue());
    }
    
    private void applyCustomArray() {
        mainScreen.setCustomArray(customArrayField.getText());
    }
    
    public void setSortingActive(boolean active) {
        startButton.setEnabled(!active);
        algorithmComboBox.setEnabled(!active);
        generateButton.setEnabled(!active);
        arraySizeSlider.setEnabled(!active);
        applyCustomArrayButton.setEnabled(!active);
        customArrayField.setEnabled(!active);
        stopButton.setEnabled(active);
    }
}
