package com.sortalgo.util;

import java.util.Random;

/**
 * Utility class for generating arrays for sorting.
 */
public class ArrayGenerator {
    
    private static final Random random = new Random();
    
    /**
     * Generate an array of random integers.
     * 
     * @param size Size of the array
     * @param min Minimum value (inclusive)
     * @param max Maximum value (inclusive)
     * @return Array of random integers
     */
    public static int[] generateRandomArray(int size, int min, int max) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(max - min + 1) + min;
        }
        return array;
    }
    
    /**
     * Generate a nearly sorted array (few elements out of place).
     * 
     * @param size Size of the array
     * @param swaps Number of random swaps to perform
     * @return Nearly sorted array
     */
    public static int[] generateNearlySortedArray(int size, int swaps) {
        int[] array = new int[size];
        
        // Start with a sorted array
        for (int i = 0; i < size; i++) {
            array[i] = i + 1;
        }
        
        // Perform random swaps
        for (int i = 0; i < swaps; i++) {
            int idx1 = random.nextInt(size);
            int idx2 = random.nextInt(size);
            
            int temp = array[idx1];
            array[idx1] = array[idx2];
            array[idx2] = temp;
        }
        
        return array;
    }
    
    /**
     * Generate a reversed array (worst case for many algorithms).
     * 
     * @param size Size of the array
     * @return Reversed array
     */
    public static int[] generateReversedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i;
        }
        return array;
    }
    
    /**
     * Generate an array with few unique values (many duplicates).
     * 
     * @param size Size of the array
     * @param uniqueValues Number of unique values
     * @return Array with few unique values
     */
    public static int[] generateFewUniqueArray(int size, int uniqueValues) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(uniqueValues) + 1;
        }
        return array;
    }
}
