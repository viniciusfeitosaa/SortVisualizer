package com.sortalgo.algorithm;

import com.sortalgo.model.SortingEvent;

/**
 * Implementation of Selection Sort algorithm.
 */
public class SelectionSort implements SortingAlgorithm {
    
    private int[] array;
    private int n;
    private int i;
    private int j;
    private int minIndex;
    private int comparisons;
    private int swaps;
    private long startTime;
    private boolean isComplete;
    
    @Override
    public void init(int[] array) {
        this.array = array.clone();
        this.n = array.length;
        this.i = 0;
        this.j = 1;
        this.minIndex = 0;
        this.comparisons = 0;
        this.swaps = 0;
        this.startTime = System.currentTimeMillis();
        this.isComplete = false;
    }
    
    @Override
    public SortingEvent sort() {
        // Full sort implementation
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            
            // Swap the found minimum element with the element at index i
            if (minIndex != i) {
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
                swaps++;
            }
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
        
        // Search for minimum element in unsorted part
        if (j < n) {
            int[] highlightIndices = {i, j, minIndex};
            comparisons++;
            
            if (array[j] < array[minIndex]) {
                minIndex = j;
            }
            
            j++;
            return new SortingEvent(array, highlightIndices, comparisons, swaps, 
                    System.currentTimeMillis() - startTime);
        } 
        // Found minimum, perform swap and move to next position
        else {
            // Swap if needed
            if (minIndex != i) {
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
                swaps++;
            }
            
            // Move to next position
            i++;
            
            // Check if sorting is complete
            if (i >= n - 1) {
                isComplete = true;
                return new SortingEvent(array, new int[0], comparisons, swaps, 
                        System.currentTimeMillis() - startTime);
            }
            
            // Reset for next pass
            minIndex = i;
            j = i + 1;
            
            return new SortingEvent(array, new int[]{i}, comparisons, swaps, 
                    System.currentTimeMillis() - startTime);
        }
    }
    
    @Override
    public boolean isSortingComplete() {
        return isComplete;
    }
    
    @Override
    public String getDescription() {
        return "Selection Sort works by dividing the array into a sorted and an unsorted region. " +
               "It repeatedly finds the minimum element from the unsorted region and puts it at the " +
               "end of the sorted region. This algorithm performs poorly on large lists but performs " +
               "well on small lists.";
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
        return "O(n²)";
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
