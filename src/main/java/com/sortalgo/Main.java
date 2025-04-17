package com.sortalgo;

import com.sortalgo.ui.WelcomeScreen;

import javax.swing.*;
import java.awt.*;

/**
 * Main entry point for the Sorting Algorithm Visualization application.
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Set the look and feel to the system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Launch the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            WelcomeScreen welcomeScreen = new WelcomeScreen();
            welcomeScreen.setVisible(true);
        });
    }
}
