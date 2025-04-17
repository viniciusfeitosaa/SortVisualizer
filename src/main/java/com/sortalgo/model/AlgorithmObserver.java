package com.sortalgo.model;

/**
 * Observer interface for sorting algorithm events.
 */
public interface AlgorithmObserver {
    
    /**
     * Called when a step in the sorting algorithm is completed.
     * 
     * @param event Contains information about the current state of the sorting process
     */
    void onSortingStep(SortingEvent event);
    
    /**
     * Called when the sorting algorithm has completed.
     * 
     * @param event Contains information about the final state and performance metrics
     */
    void onSortingComplete(SortingEvent event);
}
