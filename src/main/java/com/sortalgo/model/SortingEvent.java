package com.sortalgo.model;

/**
 * Class representing an event in the sorting process.
 * Contains information about the current state of the array and performance metrics.
 */
public class SortingEvent {
    
    private final int[] currentArray;
    private final int[] highlightIndices;
    private final int comparisons;
    private final int swaps;
    private final long executionTime;
    
    /**
     * Create a new sorting event.
     * 
     * @param currentArray The current state of the array being sorted
     * @param highlightIndices Indices to highlight in the visualization
     * @param comparisons Number of comparisons performed so far
     * @param swaps Number of swaps performed so far
     * @param executionTime Execution time in milliseconds so far
     */
    public SortingEvent(int[] currentArray, int[] highlightIndices, int comparisons, int swaps, long executionTime) {
        this.currentArray = currentArray;
        this.highlightIndices = highlightIndices;
        this.comparisons = comparisons;
        this.swaps = swaps;
        this.executionTime = executionTime;
    }
    
    /**
     * Get the current state of the array.
     * 
     * @return A copy of the current array
     */
    public int[] getCurrentArray() {
        return currentArray;
    }
    
    /**
     * Get the indices to highlight in the visualization.
     * 
     * @return Array of indices to highlight
     */
    public int[] getHighlightIndices() {
        return highlightIndices;
    }
    
    /**
     * Get the number of comparisons performed.
     * 
     * @return Number of comparisons
     */
    public int getComparisons() {
        return comparisons;
    }
    
    /**
     * Get the number of swaps performed.
     * 
     * @return Number of swaps
     */
    public int getSwaps() {
        return swaps;
    }
    
    /**
     * Get the execution time so far.
     * 
     * @return Execution time in milliseconds
     */
    public long getExecutionTime() {
        return executionTime;
    }
}
