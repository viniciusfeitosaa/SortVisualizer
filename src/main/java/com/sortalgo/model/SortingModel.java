package com.sortalgo.model;

import com.sortalgo.algorithm.SortingAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Model class that manages the sorting algorithm and notifies observers of changes.
 */
public class SortingModel {
    
    private int[] array;
    private SortingAlgorithm algorithm;
    private List<AlgorithmObserver> observers;
    private Timer timer;
    private int animationDelay;
    private boolean sorting;
    
    /**
     * Initialize the model with default values.
     */
    public SortingModel() {
        this.array = new int[0];
        this.algorithm = null;
        this.observers = new ArrayList<>();
        this.timer = null;
        this.animationDelay = 300; // Default delay in milliseconds
        this.sorting = false;
    }
    
    /**
     * Register an observer to be notified of sorting events.
     * 
     * @param observer The observer to register
     */
    public void registerObserver(AlgorithmObserver observer) {
        observers.add(observer);
    }
    
    /**
     * Unregister an observer to stop receiving notifications.
     * 
     * @param observer The observer to unregister
     */
    public void unregisterObserver(AlgorithmObserver observer) {
        observers.remove(observer);
    }
    
    /**
     * Set the array to be sorted.
     * 
     * @param array The array to sort
     */
    public void setArray(int[] array) {
        this.array = array.clone();
    }
    
    /**
     * Set the sorting algorithm to use.
     * 
     * @param algorithm The algorithm implementation
     */
    public void setAlgorithm(SortingAlgorithm algorithm) {
        this.algorithm = algorithm;
        if (this.array != null && this.array.length > 0) {
            algorithm.init(this.array);
        }
    }
    
    /**
     * Set the delay between animation steps.
     * 
     * @param delay Delay in milliseconds
     */
    public void setAnimationDelay(int delay) {
        this.animationDelay = delay;
    }
    
    /**
     * Start automatic sorting with animation.
     */
    public void startSorting() {
        if (algorithm == null || array == null || array.length <= 1) return;
        
        // Initialize algorithm with current array
        algorithm.init(array.clone());
        
        // Stop any existing timer
        stopSorting();
        
        // Start a new timer for animation
        sorting = true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (algorithm.isSortingComplete()) {
                    stopSorting();
                    notifySortingComplete(algorithm.step());
                } else {
                    SortingEvent event = algorithm.step();
                    notifySortingStep(event);
                }
            }
        }, 0, animationDelay);
    }
    
    /**
     * Stop automatic sorting.
     */
    public void stopSorting() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        sorting = false;
    }
    
    /**
     * Perform a single step of the sorting algorithm.
     */
    public void stepSorting() {
        if (algorithm == null || array == null) return;
        
        if (algorithm.isSortingComplete()) {
            notifySortingComplete(algorithm.step());
        } else {
            SortingEvent event = algorithm.step();
            notifySortingStep(event);
            
            if (algorithm.isSortingComplete()) {
                notifySortingComplete(event);
            }
        }
    }
    
    /**
     * Check if sorting is currently active.
     * 
     * @return true if sorting is active, false otherwise
     */
    public boolean isSorting() {
        return sorting;
    }
    
    /**
     * Notify observers of a sorting step.
     * 
     * @param event The sorting event
     */
    private void notifySortingStep(SortingEvent event) {
        for (AlgorithmObserver observer : observers) {
            observer.onSortingStep(event);
        }
    }
    
    /**
     * Notify observers that sorting is complete.
     * 
     * @param event The final sorting event
     */
    private void notifySortingComplete(SortingEvent event) {
        sorting = false;
        for (AlgorithmObserver observer : observers) {
            observer.onSortingComplete(event);
        }
    }
}
