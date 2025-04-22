package com.sortalgo.algorithm;

import com.sortalgo.model.SortingEvent;

/**
 * Implementation of Bubble Sort algorithm.
 */
public class BubbleSort implements SortingAlgorithm {
    
    private int[] array;
    private int n;
    private int i;
    private int j;
    private boolean swapped;
    private int comparisons;
    private int swaps;
    private long startTime;
    private boolean isComplete;
    
    @Override
    public void init(int[] array) {
        this.array = array.clone(); // Create a copy to avoid modifying the original
        this.n = array.length;
        this.i = 0;
        this.j = 0;
        this.swapped = false;
        this.comparisons = 0;
        this.swaps = 0;
        this.startTime = System.currentTimeMillis();
        this.isComplete = false;
    }
    
    @Override
    public SortingEvent sort() {
        // Full sort implementation
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;
                if (array[j] > array[j + 1]) {
                    // Swap arr[j] and arr[j+1]
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swaps++;
                    swapped = true;
                }
            }
            // If no swapping occurred in this pass, array is sorted
            if (!swapped) {
                break;
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
        
        // Perform one comparison step
        if (j < n - i - 1) {
            int[] highlightIndices = {j, j + 1};
            comparisons++;
            
            if (array[j] > array[j + 1]) {
                // Swap arr[j] and arr[j+1]
                int temp = array[j];
                array[j] = array[j + 1];
                array[j + 1] = temp;
                swaps++;
                swapped = true;
            }
            
            j++;
            return new SortingEvent(array, highlightIndices, comparisons, swaps, 
                    System.currentTimeMillis() - startTime);
        } 
        // Move to next pass
        else if (i < n - 1) {
            j = 0;
            i++;
            
            // Optimization: if no swaps occurred in the last pass, we're done
            if (!swapped) {
                isComplete = true;
                return new SortingEvent(array, new int[0], comparisons, swaps, 
                        System.currentTimeMillis() - startTime);
            }
            
            swapped = false;
            return new SortingEvent(array, new int[]{i}, comparisons, swaps, 
                    System.currentTimeMillis() - startTime);
        } 
        // Sorting complete
        else {
            isComplete = true;
            return new SortingEvent(array, new int[0], comparisons, swaps, 
                    System.currentTimeMillis() - startTime);
        }
    }
    
    @Override
    public boolean isSortingComplete() {
        return isComplete;
    }
    
    @Override
    public String getDescription() {
        return "Bubble Sort works by repeatedly stepping through the list, comparing each pair " +
               "of adjacent items and swapping them if they are in the wrong order. The pass through " +
               "the list is repeated until no swaps are needed, indicating that the list is sorted.";
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
