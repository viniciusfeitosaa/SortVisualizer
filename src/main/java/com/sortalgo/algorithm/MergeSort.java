package com.sortalgo.algorithm;

import com.sortalgo.model.SortingEvent;
import java.util.Arrays;
import java.util.Stack;

/**
 * Implementation of Merge Sort algorithm using an iterative approach for step-by-step visualization.
 */
public class MergeSort implements SortingAlgorithm {
    
    private int[] array;
    private int n;
    private int comparisons;
    private int swaps;
    private long startTime;
    private boolean isComplete;
    
    // For step-by-step execution
    private Stack<MergePair> mergeStack;
    private MergePair currentMerge;
    private int[] tempArray;
    private int mergeIndex;
    private boolean inMergePhase;
    
    // Class to represent a merge operation
    private static class MergePair {
        int left;
        int mid;
        int right;
        
        MergePair(int left, int mid, int right) {
            this.left = left;
            this.mid = mid;
            this.right = right;
        }
    }
    
    @Override
    public void init(int[] array) {
        this.array = array.clone();
        this.n = array.length;
        this.comparisons = 0;
        this.swaps = 0;
        this.startTime = System.currentTimeMillis();
        this.isComplete = n <= 1; // Already sorted if array size <= 1
        
        // Initialize step-by-step state
        this.mergeStack = new Stack<>();
        this.tempArray = new int[n];
        this.inMergePhase = false;
        
        // Build the merge operations stack
        if (n > 1) {
            buildMergeOperationsStack(0, n - 1);
        }
    }
    
    private void buildMergeOperationsStack(int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            // Recursively build operations for the left and right subarrays
            buildMergeOperationsStack(left, mid);
            buildMergeOperationsStack(mid + 1, right);
            
            // Add this merge operation to the stack
            mergeStack.push(new MergePair(left, mid, right));
        }
    }
    
    @Override
    public SortingEvent sort() {
        mergeSort(array, 0, n - 1);
        
        isComplete = true;
        long executionTime = System.currentTimeMillis() - startTime;
        
        return new SortingEvent(array, new int[0], comparisons, swaps, executionTime);
    }
    
    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            // Find the middle point
            int mid = left + (right - left) / 2;
            
            // Sort first and second halves
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            
            // Merge the sorted halves
            merge(arr, left, mid, right);
        }
    }
    
    private void merge(int[] arr, int left, int mid, int right) {
        // Create temporary arrays
        int[] L = Arrays.copyOfRange(arr, left, mid + 1);
        int[] R = Arrays.copyOfRange(arr, mid + 1, right + 1);
        
        // Merge the temporary arrays back into arr[left..right]
        int i = 0, j = 0, k = left;
        
        while (i < L.length && j < R.length) {
            comparisons++;
            
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            swaps++;
            k++;
        }
        
        // Copy remaining elements of L[] if any
        while (i < L.length) {
            arr[k] = L[i];
            i++;
            k++;
            swaps++;
        }
        
        // Copy remaining elements of R[] if any
        while (j < R.length) {
            arr[k] = R[j];
            j++;
            k++;
            swaps++;
        }
    }
    
    @Override
    public SortingEvent step() {
        if (isComplete) {
            return new SortingEvent(array, new int[0], comparisons, swaps, 
                    System.currentTimeMillis() - startTime);
        }
        
        // If no current merge operation, get one from the stack
        if (!inMergePhase) {
            if (mergeStack.isEmpty()) {
                isComplete = true;
                return new SortingEvent(array, new int[0], comparisons, swaps, 
                        System.currentTimeMillis() - startTime);
            }
            
            // Start a new merge operation
            currentMerge = mergeStack.pop();
            inMergePhase = true;
            
            // Initialize for merge
            int left = currentMerge.left;
            int mid = currentMerge.mid;
            int right = currentMerge.right;
            
            // Copy data to temp array
            System.arraycopy(array, left, tempArray, left, right - left + 1);
            
            // Initialize merge indices
            int i = left;      // Initial index of first subarray
            int j = mid + 1;   // Initial index of second subarray
            mergeIndex = left; // Initial index of merged subarray
            
            return new SortingEvent(array, new int[]{left, mid, right}, comparisons, swaps, 
                    System.currentTimeMillis() - startTime);
        }
        
        // Continue with current merge operation
        int left = currentMerge.left;
        int mid = currentMerge.mid;
        int right = currentMerge.right;
        
        // Initial indices of first and second subarrays
        int i = left;
        int j = mid + 1;
        
        // If we have elements to compare from both subarrays
        if (i <= mid && j <= right) {
            comparisons++;
            
            if (tempArray[i] <= tempArray[j]) {
                array[mergeIndex] = tempArray[i];
                i++;
            } else {
                array[mergeIndex] = tempArray[j];
                j++;
            }
            swaps++;
            mergeIndex++;
            
            return new SortingEvent(array, new int[]{mergeIndex - 1, i, j}, comparisons, swaps, 
                    System.currentTimeMillis() - startTime);
        }
        
        // Copy remaining elements from first subarray
        while (i <= mid) {
            array[mergeIndex] = tempArray[i];
            i++;
            mergeIndex++;
            swaps++;
            
            return new SortingEvent(array, new int[]{mergeIndex - 1, i}, comparisons, swaps, 
                    System.currentTimeMillis() - startTime);
        }
        
        // Copy remaining elements from second subarray
        while (j <= right) {
            array[mergeIndex] = tempArray[j];
            j++;
            mergeIndex++;
            swaps++;
            
            return new SortingEvent(array, new int[]{mergeIndex - 1, j}, comparisons, swaps, 
                    System.currentTimeMillis() - startTime);
        }
        
        // Merge operation completed
        inMergePhase = false;
        
        return new SortingEvent(array, new int[]{left, right}, comparisons, swaps, 
                System.currentTimeMillis() - startTime);
    }
    
    @Override
    public boolean isSortingComplete() {
        return isComplete;
    }
    
    @Override
    public String getDescription() {
        return "Merge Sort is a divide-and-conquer algorithm that divides the array into equal halves, " +
               "recursively sorts them, and then merges the sorted halves. It has a consistent O(n log n) " +
               "time complexity regardless of the input but requires additional space proportional to the " +
               "size of the input array.";
    }
    
    @Override
    public String getWorstCaseComplexity() {
        return "O(n log n)";
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
        return "O(n)";
    }
}
