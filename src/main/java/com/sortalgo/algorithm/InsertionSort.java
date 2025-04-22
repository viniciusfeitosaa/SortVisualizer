package com.sortalgo.algorithm;

import com.sortalgo.model.SortingEvent;

/**
 * Implementation of Insertion Sort algorithm.
 */
public class InsertionSort implements SortingAlgorithm {
    
    private int[] array;
    private int n;
    private int i;
    private int j;
    private int key;
    private boolean movingKey;
    private boolean keyInserted;
    private int comparisons;
    private int swaps;
    private long startTime;
    private boolean isComplete;
    
    @Override
    public void init(int[] array) {
        this.array = array.clone();
        this.n = array.length;
        this.i = 1; // Start from the second element
        this.j = 0;
        this.key = this.i < n ? array[i] : 0;
        this.movingKey = false;
        this.keyInserted = true;
        this.comparisons = 0;
        this.swaps = 0;
        this.startTime = System.currentTimeMillis();
        this.isComplete = n <= 1; // Already sorted if array size <= 1
    }
    
    @Override
    public SortingEvent sort() {
        // Full sort implementation
        for (int i = 1; i < n; i++) {
            int key = array[i];
            int j = i - 1;
            
            // Move elements that are greater than key to one position ahead
            while (j >= 0 && array[j] > key) {
                comparisons++;
                array[j + 1] = array[j];
                swaps++;
                j--;
            }
            
            // If j >= 0, we need one more comparison
            if (j >= 0) {
                comparisons++;
            }
            
            array[j + 1] = key;
        }
        
        isComplete = true;
        long executionTime = System.currentTimeMillis() - startTime;
        
        return new SortingEvent(array, new int[0], comparisons, swaps, executionTime);
    }
    
    @Override
    public SortingEvent step() {
        if (isComplete) {
            return new SortingEvent(array, new int[0], comparisons, swaps, 
                    System.currentTimeMillis() - startTime);
        }
        
        // If we need to start with a new key
        if (keyInserted) {
            if (i >= n) {
                isComplete = true;
                return new SortingEvent(array, new int[0], comparisons, swaps, 
                        System.currentTimeMillis() - startTime);
            }
            
            key = array[i];
            j = i - 1;
            keyInserted = false;
            movingKey = true;
            
            return new SortingEvent(array, new int[]{i}, comparisons, swaps, 
                    System.currentTimeMillis() - startTime);
        }
        
        // Moving key to its proper position
        if (movingKey) {
            // If we can move the key further left
            if (j >= 0) {
                comparisons++;
                
                if (array[j] > key) {
                    array[j + 1] = array[j];
                    swaps++;
                    j--;
                    
                    return new SortingEvent(array, new int[]{j, j + 1}, comparisons, swaps, 
                            System.currentTimeMillis() - startTime);
                } else {
                    // Found the right position for key
                    movingKey = false;
                    return new SortingEvent(array, new int[]{j, j + 1}, comparisons, swaps, 
                            System.currentTimeMillis() - startTime);
                }
            } else {
                // Reached the beginning of the array
                movingKey = false;
                return new SortingEvent(array, new int[]{0}, comparisons, swaps, 
                        System.currentTimeMillis() - startTime);
            }
        } 
        // Insert key and move to next element
        else {
            array[j + 1] = key;
            keyInserted = true;
            i++;
            
            return new SortingEvent(array, new int[]{j + 1}, comparisons, swaps, 
                    System.currentTimeMillis() - startTime);
        }
    }
    
    @Override
    public boolean isSortingComplete() {
        return isComplete;
    }
    
    @Override
    public String getDescription() {
        return "Insertion Sort works by building a sorted array one item at a time. " +
               "It takes each element from the unsorted part and inserts it into its correct position " +
               "in the sorted part. This algorithm is efficient for small data sets and mostly-sorted arrays.";
    }
    
    @Override
    public String getWorstCaseComplexity() {
        return "O(n²)";
    }
    
    @Override
    public String getAverageCaseComplexity() {
        return "O(n²)";
    }
    
    @Override
    public String getBestCaseComplexity() {
        return "O(n)";
    }
    
    @Override
    public String getSpaceComplexity() {
        return "O(1)";
    }
    
    @Override
    public int getComparisons() {
        return comparisons;
    }
    
    @Override
    public int getSwaps() {
        return swaps;
    }
}
