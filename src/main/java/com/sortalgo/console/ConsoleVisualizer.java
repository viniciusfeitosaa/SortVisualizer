package com.sortalgo.console;

import com.sortalgo.algorithm.*;
import com.sortalgo.model.SortingEvent;

/**
 * Uma classe que fornece visualização dos algoritmos de ordenação no console.
 */
public class ConsoleVisualizer {
    
    private static final int DEFAULT_ARRAY_SIZE = 15;
    private static final int DEFAULT_MAX_VALUE = 20;
    
    /**
     * Roda a visualização do algoritmo de ordenação no console.
     * 
     * @param algorithm O algoritmo de ordenação a ser visualizado
     * @param array O array a ser ordenado
     * @param delayMs Tempo de espera entre passos (em milissegundos)
     */
    public static void visualize(SortingAlgorithm algorithm, int[] array, int delayMs) {
        System.out.println("\n===== Visualizador de Algoritmo de Ordenação =====");
        System.out.println("Algoritmo: " + getAlgorithmName(algorithm));
        System.out.println("Descrição: " + algorithm.getDescription());
        System.out.println("Complexidade (Pior caso): " + algorithm.getWorstCaseComplexity());
        System.out.println("Complexidade (Caso médio): " + algorithm.getAverageCaseComplexity());
        System.out.println("Complexidade (Melhor caso): " + algorithm.getBestCaseComplexity());
        System.out.println("Complexidade de Espaço: " + algorithm.getSpaceComplexity());
        
        System.out.println("\nArray Original:");
        printArray(array);
        
        // Inicializa o algoritmo
        algorithm.init(array.clone());
        
        int step = 1;
        SortingEvent event = null;
        
        // Loop principal para mostrar cada passo do algoritmo
        while (!algorithm.isSortingComplete()) {
            event = algorithm.step();
            
            System.out.println("\nPasso " + step + ":");
            printArrayWithHighlights(event.getCurrentArray(), event.getHighlightIndices());
            System.out.println("Comparações: " + event.getComparisons() + ", Trocas: " + event.getSwaps());
            
            // Pausa entre os passos
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            
            step++;
        }
        
        if (event != null) {
            System.out.println("\n===== Ordenação Completa =====");
            printArray(event.getCurrentArray());
            System.out.println("\nEstatísticas Finais:");
            System.out.println("Passos: " + (step - 1));
            System.out.println("Comparações: " + event.getComparisons());
            System.out.println("Trocas: " + event.getSwaps());
            System.out.println("Tempo de Execução: " + event.getExecutionTime() + " ms");
        }
    }
    
    /**
     * Imprime o array no console.
     */
    private static void printArray(int[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        
        // Representação visual
        printArrayVisualization(array);
    }
    
    /**
     * Imprime o array com destaques para os índices especificados.
     */
    private static void printArrayWithHighlights(int[] array, int[] highlightIndices) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            boolean isHighlighted = false;
            
            // Verifica se o índice atual está destacado
            for (int index : highlightIndices) {
                if (i == index) {
                    isHighlighted = true;
                    break;
                }
            }
            
            if (isHighlighted) {
                System.out.print("\u001B[31m" + array[i] + "\u001B[0m"); // Vermelho para destaques
            } else {
                System.out.print(array[i]);
            }
            
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        
        // Representação visual com destaques
        printArrayVisualizationWithHighlights(array, highlightIndices);
    }
    
    /**
     * Imprime uma representação visual do array usando barras.
     */
    private static void printArrayVisualization(int[] array) {
        int maxValue = findMaxValue(array);
        
        for (int value : array) {
            int barLength = (value * 20) / maxValue;
            System.out.print("|");
            for (int i = 0; i < barLength; i++) {
                System.out.print("█");
            }
            System.out.println(" " + value);
        }
    }
    
    /**
     * Imprime uma representação visual do array com destaques.
     */
    private static void printArrayVisualizationWithHighlights(int[] array, int[] highlightIndices) {
        int maxValue = findMaxValue(array);
        
        for (int i = 0; i < array.length; i++) {
            boolean isHighlighted = false;
            
            // Verifica se o índice atual está destacado
            for (int index : highlightIndices) {
                if (i == index) {
                    isHighlighted = true;
                    break;
                }
            }
            
            int value = array[i];
            int barLength = (value * 20) / maxValue;
            
            System.out.print("|");
            for (int j = 0; j < barLength; j++) {
                if (isHighlighted) {
                    System.out.print("\u001B[31m█\u001B[0m"); // Vermelho para destaques
                } else {
                    System.out.print("█");
                }
            }
            
            if (isHighlighted) {
                System.out.println(" \u001B[31m" + value + "\u001B[0m");
            } else {
                System.out.println(" " + value);
            }
        }
    }
    
    /**
     * Encontra o valor máximo no array.
     */
    private static int findMaxValue(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
    
    /**
     * Método de sobrecarga que usa um delay padrão de 500ms.
     * 
     * @param algorithm O algoritmo de ordenação a ser visualizado
     * @param array O array a ser ordenado
     */
    public static void visualize(SortingAlgorithm algorithm, int[] array) {
        visualize(algorithm, array, 500);
    }
    
    /**
     * Obtém o nome do algoritmo com base na classe.
     */
    private static String getAlgorithmName(SortingAlgorithm algorithm) {
        if (algorithm instanceof BubbleSort) {
            return "Bubble Sort";
        } else if (algorithm instanceof SelectionSort) {
            return "Selection Sort";
        } else if (algorithm instanceof InsertionSort) {
            return "Insertion Sort";
        } else if (algorithm instanceof QuickSort) {
            return "Quick Sort";
        } else if (algorithm instanceof MergeSort) {
            return "Merge Sort";
        } else if (algorithm instanceof CountingSort) {
            return "Counting Sort";
        } else {
            return "Desconhecido";
        }
    }
}