package com.sortalgo.algorithm;

import com.sortalgo.model.SortingEvent;

/**
 * Interface for sorting algorithms.
 */
public interface SortingAlgorithm {
    
    /**
     * Initialize the sorting algorithm with the array to be sorted.
     * 
     * @param array The array to be sorted
     */
    void init(int[] array);
    
    /**
     * Execute the entire sorting algorithm at once.
     * 
     * @return The final SortingEvent with the sorted array and performance metrics
     */
    SortingEvent sort();
    
    /**
     * Execute a single step of the sorting algorithm.
     * 
     * @return A SortingEvent containing the current state after the step,
     *         or null if the sorting is complete
     */
    SortingEvent step();
    
    /**
     * Check if the sorting is complete.
     * 
     * @return true if sorting is complete, false otherwise
     */
    boolean isSortingComplete();
    
    /**
     * Get a description of the sorting algorithm.
     * 
     * @return A text description of how the algorithm works
     */
    String getDescription();
    
    /**
     * Get the worst-case time complexity of the algorithm.
     * 
     * @return The worst-case time complexity (e.g., "O(n²)")
     */
    String getWorstCaseComplexity();
    
    /**
     * Get the average-case time complexity of the algorithm.
     * 
     * @return The average-case time complexity (e.g., "O(n log n)")
     */
    String getAverageCaseComplexity();
    
    /**
     * Get the best-case time complexity of the algorithm.
     * 
     * @return The best-case time complexity (e.g., "O(n)")
     */
    String getBestCaseComplexity();
    
    /**
     * Get the space complexity of the algorithm.
     * 
     * @return The space complexity (e.g., "O(1)" or "O(n)")
     */
    String getSpaceComplexity();
    
    /**
     * Sort an array directly without stepping through.
     * 
     * @param array The array to be sorted
     * @return The final SortingEvent with the sorted array
     */
    default SortingEvent sort(int[] array) {
        init(array);
        return sort();
    }
    
    /**
     * Get the number of comparisons performed.
     * 
     * @return Number of comparisons
     */
    default int getComparisons() {
        return 0;
    }
    
    /**
     * Get the number of swaps performed.
     * 
     * @return Number of swaps
     */
    default int getSwaps() {
        return 0;
    }
}
