package com.sortalgo.console;

import com.sortalgo.algorithm.*;
import com.sortalgo.util.ArrayGenerator;

/**
 * Classe para demonstrar algoritmos de ordenação no console com valores predefinidos.
 * (Sem necessidade de entrada do usuário)
 */
public class ConsoleDemoMain {
    
    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("| VISUALIZADOR DE ALGORITMOS DE ORDENAÇÃO |");
        System.out.println("======================================");
        System.out.println();
        
        // Criar um array aleatório de tamanho 10 com valores entre 1 e 50
        int[] array = ArrayGenerator.generateRandomArray(10, 1, 50);
        
        // Demonstrar cada algoritmo de ordenação
        demonstrarAlgoritmo("Bubble Sort", new BubbleSort(), array.clone());
        pause(2000);
        
        demonstrarAlgoritmo("Selection Sort", new SelectionSort(), array.clone());
        pause(2000);
        
        demonstrarAlgoritmo("Insertion Sort", new InsertionSort(), array.clone());
        pause(2000);
        
        demonstrarAlgoritmo("Quick Sort", new QuickSort(), array.clone());
        pause(2000);
        
        demonstrarAlgoritmo("Merge Sort", new MergeSort(), array.clone());
        
        System.out.println("\nA demonstração de todos os algoritmos foi concluída!");
    }
    
    /**
     * Demonstra um algoritmo de ordenação específico.
     * 
     * @param nome Nome do algoritmo
     * @param algorithm O algoritmo a ser demonstrado
     * @param array O array a ser ordenado
     */
    private static void demonstrarAlgoritmo(String nome, SortingAlgorithm algorithm, int[] array) {
        System.out.println("\n\n===== DEMONSTRAÇÃO: " + nome + " =====");
        ConsoleVisualizer.visualize(algorithm, array);
    }
    
    /**
     * Pausa a execução por um determinado tempo.
     * 
     * @param millis Tempo em milissegundos
     */
    private static void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}