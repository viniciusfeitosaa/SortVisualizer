package com.sortalgo.algorithm;

import com.sortalgo.model.SortingEvent;
import java.util.Stack;

/**
 * Implementation of Quick Sort algorithm using an iterative approach for step-by-step visualization.
 */
public class QuickSort implements SortingAlgorithm {
    
    private int[] array;
    private int n;
    private int comparisons;
    private int swaps;
    private long startTime;
    private boolean isComplete;
    
    // For step-by-step execution
    private Stack<Integer> stack;
    private int low;
    private int high;
    private int partitionIndex;
    private boolean findingPivot;
    private boolean partitioning;
    
    @Override
    public void init(int[] array) {
        this.array = array.clone();
        this.n = array.length;
        this.comparisons = 0;
        this.swaps = 0;
        this.startTime = System.currentTimeMillis();
        this.isComplete = n <= 1; // Already sorted if array size <= 1
        
        // Initialize step-by-step state
        this.stack = new Stack<>();
        if (n > 1) {
            stack.push(0);
            stack.push(n - 1);
        }
        this.findingPivot = false;
        this.partitioning = false;
    }
    
    @Override
    public SortingEvent sort() {
        quickSort(array, 0, n - 1);
        
        isComplete = true;
        long executionTime = System.currentTimeMillis() - startTime;
        
        return new SortingEvent(array, new int[0], comparisons, swaps, executionTime);
    }
    
    private void quickSort(int[] arr, int low, int high) {
        // Create an auxiliary stack
        Stack<Integer> stack = new Stack<>();
        
        // Push initial values to stack
        stack.push(low);
        stack.push(high);
        
        // Keep popping elements until stack is empty
        while (!stack.isEmpty()) {
            // Pop high and low
            high = stack.pop();
            low = stack.pop();
            
            // Partition the array and get pivot index
            int pivotIndex = partition(arr, low, high);
            
            // If there are elements on the left side of pivot, push them to stack
            if (pivotIndex - 1 > low) {
                stack.push(low);
                stack.push(pivotIndex - 1);
            }
            
            // If there are elements on the right side of pivot, push them to stack
            if (pivotIndex + 1 < high) {
                stack.push(pivotIndex + 1);
                stack.push(high);
            }
        }
    }
    
    private int partition(int[] arr, int low, int high) {
        // Select the rightmost element as pivot
        int pivot = arr[high];
        
        // Index of smaller element
        int i = low - 1;
        
        // Compare each element with pivot
        for (int j = low; j < high; j++) {
            comparisons++;
            
            // If current element is smaller than or equal to pivot
            if (arr[j] <= pivot) {
                i++;
                
                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                swaps++;
            }
        }
        
        // Swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        swaps++;
        
        return i + 1;
    }
    
    @Override
    public SortingEvent step() {
        if (isComplete) {
            return new SortingEvent(array, new int[0], comparisons, swaps, 
                    System.currentTimeMillis() - startTime);
        }
        
        // If we need to start a new partition operation
        if (!findingPivot && !partitioning) {
            if (stack.isEmpty()) {
                isComplete = true;
                return new SortingEvent(array, new int[0], comparisons, swaps, 
                        System.currentTimeMillis() - startTime);
            }
            
            // Pop high and low for the next partition
            high = stack.pop();
            low = stack.pop();
            
            // Initialize partition operation
            findingPivot = true;
            partitioning = false;
            partitionIndex = low - 1;
            
            return new SortingEvent(array, new int[]{low, high}, comparisons, swaps, 
                    System.currentTimeMillis() - startTime);
        }
        
        // Processing partition operation
        if (findingPivot) {
            int pivot = array[high];
            int j = partitionIndex + 1;
            
            // If we still have elements to compare with pivot
            if (j < high) {
                comparisons++;
                
                // If current element is smaller than or equal to pivot
                if (array[j] <= pivot) {
                    partitionIndex++;
                    
                    // Swap array[partitionIndex] and array[j]
                    int temp = array[partitionIndex];
                    array[partitionIndex] = array[j];
                    array[j] = temp;
                    swaps++;
                }
                
                return new SortingEvent(array, new int[]{partitionIndex, j, high}, comparisons, swaps, 
                        System.currentTimeMillis() - startTime);
            } 
            // Finished comparing all elements with pivot
            else {
                findingPivot = false;
                partitioning = true;
                
                return new SortingEvent(array, new int[]{partitionIndex + 1, high}, comparisons, swaps, 
                        System.currentTimeMillis() - startTime);
            }
        }
        
        // Completing the partition by placing the pivot in its correct position
        if (partitioning) {
            // Swap array[partitionIndex + 1] and array[high] (or pivot)
            int temp = array[partitionIndex + 1];
            array[partitionIndex + 1] = array[high];
            array[high] = temp;
            swaps++;
            
            int pivotIndex = partitionIndex + 1;
            
            // Push left subarray to stack if there are elements
            if (pivotIndex - 1 > low) {
                stack.push(low);
                stack.push(pivotIndex - 1);
            }
            
            // Push right subarray to stack if there are elements
            if (pivotIndex + 1 < high) {
                stack.push(pivotIndex + 1);
                stack.push(high);
            }
            
            // Reset for next partition operation
            findingPivot = false;
            partitioning = false;
            
            return new SortingEvent(array, new int[]{pivotIndex}, comparisons, swaps, 
                    System.currentTimeMillis() - startTime);
        }
        
        // This should not happen
        return new SortingEvent(array, new int[0], comparisons, swaps, 
                System.currentTimeMillis() - startTime);
    }
    
    @Override
    public boolean isSortingComplete() {
        return isComplete;
    }
    
    @Override
    public String getDescription() {
        return "Quick Sort is a divide-and-conquer algorithm that works by selecting a 'pivot' element " +
               "and partitioning the array around the pivot so that elements smaller than the pivot are on " +
               "the left and elements greater are on the right. The process is then repeated for the sub-arrays. " +
               "Quick Sort is one of the most efficient sorting algorithms but can perform poorly with certain " +
               "pivot selection strategies or already sorted arrays.";
    }
    
    @Override
    public String getWorstCaseComplexity() {
        return "O(n²)";
    }
    
    @Override
    public String getAverageCaseComplexity() {
        return "O(n log n)";
    }
    
    @Override
    public String getBestCaseComplexity() {
        return "O(n log n)";
    }
    
    @Override
    public String getSpaceComplexity() {
        return "O(log n)";
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
