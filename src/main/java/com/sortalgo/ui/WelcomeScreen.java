package com.sortalgo.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Welcome screen for the application that displays introduction and allows users to proceed.
 */
public class WelcomeScreen extends JFrame {
    
    private static final String TITLE = "Sort Algorithm Visualizer";
    private static final String WELCOME_MESSAGE = 
            "<html><h1>Welcome to Sort Algorithm Visualizer</h1>" +
            "<p>This application demonstrates various sorting algorithms through visualization.</p>" +
            "<p>You can:</p>" +
            "<ul>" +
            "<li>Select different sorting algorithms</li>" +
            "<li>Visualize the sorting process step-by-step</li>" +
            "<li>Input custom arrays for sorting</li>" +
            "<li>Compare performance between algorithms</li>" +
            "</ul>" +
            "<p>Supported algorithms:</p>" +
            "<ul>" +
            "<li>Bubble Sort</li>" +
            "<li>Selection Sort</li>" +
            "<li>Insertion Sort</li>" +
            "<li>Quick Sort</li>" +
            "<li>Merge Sort</li>" +
            "</ul></html>";
            
    public WelcomeScreen() {
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Welcome message
        JLabel welcomeLabel = new JLabel(WELCOME_MESSAGE);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Start button
        JButton startButton = new JButton("Start Visualization");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMainScreen();
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private void openMainScreen() {
        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen();
            mainScreen.setVisible(true);
            this.dispose();
        });
    }
}
