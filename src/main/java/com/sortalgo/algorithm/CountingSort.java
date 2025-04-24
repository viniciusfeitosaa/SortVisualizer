package com.sortalgo.algorithm;

import com.sortalgo.model.SortingEvent;

public class CountingSort implements SortingAlgorithm {
    private int[] array;
    private int[] output;
    private int comparisons;
    private int swaps;
    private boolean sortingComplete;
    private int currentStep;
    private int maxValue;
    private int[] count;
    private long startTime;

    @Override
    public void init(int[] array) {
        this.array = array.clone();
        this.output = new int[array.length];
        this.comparisons = 0;
        this.swaps = 0;
        this.sortingComplete = false;
        this.currentStep = 0;
        this.maxValue = findMaxValue();
        this.count = new int[maxValue + 1];
        this.startTime = System.currentTimeMillis();
    }

    private int findMaxValue() {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            comparisons++;
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    @Override
    public SortingEvent sort() {
        while (!isSortingComplete()) {
            step();
        }
        return new SortingEvent(output, new int[0], comparisons, swaps, System.currentTimeMillis() - startTime);
    }

    @Override
    public SortingEvent step() {
        if (sortingComplete) {
            return null;
        }

        switch (currentStep) {
            case 0:
                // Inicializa o array de contagem
                for (int i = 0; i < array.length; i++) {
                    count[array[i]]++;
                }
                currentStep++;
                break;
            case 1:
                // Acumula as contagens
                for (int i = 1; i <= maxValue; i++) {
                    count[i] += count[i - 1];
                }
                currentStep++;
                break;
            case 2:
                // Constrói o array de saída
                for (int i = array.length - 1; i >= 0; i--) {
                    output[count[array[i]] - 1] = array[i];
                    count[array[i]]--;
                    swaps++;
                }
                currentStep++;
                break;
            case 3:
                // Copia o array ordenado de volta para o array original
                System.arraycopy(output, 0, array, 0, array.length);
                sortingComplete = true;
                break;
        }

        return new SortingEvent(array, new int[0], comparisons, swaps, System.currentTimeMillis() - startTime);
    }

    @Override
    public boolean isSortingComplete() {
        return sortingComplete;
    }

    @Override
    public String getDescription() {
        return "Counting Sort é um algoritmo de ordenação que funciona contando o número de ocorrências de cada elemento no array. " +
               "É eficiente quando o intervalo de valores possíveis é pequeno em comparação com o tamanho do array.";
    }

    @Override
    public String getWorstCaseComplexity() {
        return "O(n + k)";
    }

    @Override
    public String getAverageCaseComplexity() {
        return "O(n + k)";
    }

    @Override
    public String getBestCaseComplexity() {
        return "O(n + k)";
    }

    @Override
    public String getSpaceComplexity() {
        return "O(n + k)";
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