package com.sortalgo;

import com.sortalgo.console.ConsoleDemoMain;
import com.sortalgo.console.ConsoleMain;

import java.util.Scanner;

/**
 * Classe principal que serve como um ponto de entrada para o aplicativo de visualização
 * de algoritmos de ordenação.
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("+--------------------------------------------------+");
        System.out.println("|    VISUALIZADOR DE ALGORITMOS DE ORDENAÇÃO       |");
        System.out.println("|    Desenvolvido como projeto educacional         |");
        System.out.println("+--------------------------------------------------+");
        System.out.println("\nBem-vindo ao Visualizador de Algoritmos de Ordenação!");
        System.out.println("Este aplicativo demonstra o funcionamento dos seguintes algoritmos:");
        System.out.println("- Bubble Sort");
        System.out.println("- Selection Sort");
        System.out.println("- Insertion Sort");
        System.out.println("- Quick Sort");
        System.out.println("- Merge Sort");
        
        System.out.println("\nEscolha uma opção:");
        System.out.println("1. Iniciar modo interativo (definir array manualmente)");
        System.out.println("2. Iniciar modo de demonstração (opções predefinidas)");
        System.out.println("3. Exibir código-fonte dos algoritmos");
        System.out.println("4. Sair");
        
        // Leitura da opção do usuário usando Scanner
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nOpção: ");
        int opcao = 0;
        try {
            opcao = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida! Iniciando modo interativo por padrão...");
            opcao = 1;
        }
        
        switch (opcao) {
            case 1:
                ConsoleMain.main(args);
                break;
            case 2:
                ConsoleDemoMain.main(args);
                break;
            case 3:
                exibirCodigoFonte();
                break;
            case 4:
                System.out.println("Obrigado por usar o Visualizador de Algoritmos de Ordenação!");
                break;
            default:
                System.out.println("Opção inválida! Iniciando modo interativo por padrão...");
                ConsoleMain.main(args);
        }
    }
    
    /**
     * Exibe uma prévia do código-fonte dos algoritmos implementados.
     */
    private static void exibirCodigoFonte() {
        System.out.println("\n=== PRÉVIA DO CÓDIGO-FONTE DOS ALGORITMOS ===\n");
        
        // Bubble Sort
        System.out.println("=== BUBBLE SORT ===");
        System.out.println("```java");
        System.out.println("public class BubbleSort implements SortingAlgorithm {");
        System.out.println("    @Override");
        System.out.println("    public SortingEvent sort() {");
        System.out.println("        // Full sort implementation");
        System.out.println("        for (int i = 0; i < n - 1; i++) {");
        System.out.println("            swapped = false;");
        System.out.println("            for (int j = 0; j < n - i - 1; j++) {");
        System.out.println("                comparisons++;");
        System.out.println("                if (array[j] > array[j + 1]) {");
        System.out.println("                    // Swap arr[j] and arr[j+1]");
        System.out.println("                    int temp = array[j];");
        System.out.println("                    array[j] = array[j + 1];");
        System.out.println("                    array[j + 1] = temp;");
        System.out.println("                    swaps++;");
        System.out.println("                    swapped = true;");
        System.out.println("                }");
        System.out.println("            }");
        System.out.println("            // If no swapping occurred in this pass, array is sorted");
        System.out.println("            if (!swapped) {");
        System.out.println("                break;");
        System.out.println("            }");
        System.out.println("        }");
        System.out.println("        // ... restante do código ...");
        System.out.println("    }");
        System.out.println("}");
        System.out.println("```\n");
        
        // Selection Sort
        System.out.println("=== SELECTION SORT ===");
        System.out.println("```java");
        System.out.println("public class SelectionSort implements SortingAlgorithm {");
        System.out.println("    @Override");
        System.out.println("    public SortingEvent sort() {");
        System.out.println("        // Full sort implementation");
        System.out.println("        for (int i = 0; i < n - 1; i++) {");
        System.out.println("            int minIndex = i;");
        System.out.println("            ");
        System.out.println("            for (int j = i + 1; j < n; j++) {");
        System.out.println("                comparisons++;");
        System.out.println("                if (array[j] < array[minIndex]) {");
        System.out.println("                    minIndex = j;");
        System.out.println("                }");
        System.out.println("            }");
        System.out.println("            ");
        System.out.println("            // Swap the found minimum element with the element at index i");
        System.out.println("            if (minIndex != i) {");
        System.out.println("                int temp = array[i];");
        System.out.println("                array[i] = array[minIndex];");
        System.out.println("                array[minIndex] = temp;");
        System.out.println("                swaps++;");
        System.out.println("            }");
        System.out.println("        }");
        System.out.println("        // ... restante do código ...");
        System.out.println("    }");
        System.out.println("}");
        System.out.println("```\n");
        
        // Insertion Sort
        System.out.println("=== INSERTION SORT ===");
        System.out.println("```java");
        System.out.println("public class InsertionSort implements SortingAlgorithm {");
        System.out.println("    @Override");
        System.out.println("    public SortingEvent sort() {");
        System.out.println("        // Full sort implementation");
        System.out.println("        for (int i = 1; i < n; i++) {");
        System.out.println("            int key = array[i];");
        System.out.println("            int j = i - 1;");
        System.out.println("            ");
        System.out.println("            // Move elements that are greater than key to one position ahead");
        System.out.println("            while (j >= 0 && array[j] > key) {");
        System.out.println("                comparisons++;");
        System.out.println("                array[j + 1] = array[j];");
        System.out.println("                swaps++;");
        System.out.println("                j--;");
        System.out.println("            }");
        System.out.println("            ");
        System.out.println("            // If j >= 0, we need one more comparison");
        System.out.println("            if (j >= 0) {");
        System.out.println("                comparisons++;");
        System.out.println("            }");
        System.out.println("            ");
        System.out.println("            array[j + 1] = key;");
        System.out.println("        }");
        System.out.println("        // ... restante do código ...");
        System.out.println("    }");
        System.out.println("}");
        System.out.println("```\n");
        
        // Quick Sort (versão simplificada)
        System.out.println("=== QUICK SORT ===");
        System.out.println("```java");
        System.out.println("public class QuickSort implements SortingAlgorithm {");
        System.out.println("    private int partition(int[] arr, int low, int high) {");
        System.out.println("        // Select the rightmost element as pivot");
        System.out.println("        int pivot = arr[high];");
        System.out.println("        ");
        System.out.println("        // Index of smaller element");
        System.out.println("        int i = low - 1;");
        System.out.println("        ");
        System.out.println("        // Compare each element with pivot");
        System.out.println("        for (int j = low; j < high; j++) {");
        System.out.println("            comparisons++;");
        System.out.println("            ");
        System.out.println("            // If current element is smaller than or equal to pivot");
        System.out.println("            if (arr[j] <= pivot) {");
        System.out.println("                i++;");
        System.out.println("                ");
        System.out.println("                // Swap arr[i] and arr[j]");
        System.out.println("                int temp = arr[i];");
        System.out.println("                arr[i] = arr[j];");
        System.out.println("                arr[j] = temp;");
        System.out.println("                swaps++;");
        System.out.println("            }");
        System.out.println("        }");
        System.out.println("        ");
        System.out.println("        // Swap arr[i+1] and arr[high] (or pivot)");
        System.out.println("        int temp = arr[i + 1];");
        System.out.println("        arr[i + 1] = arr[high];");
        System.out.println("        arr[high] = temp;");
        System.out.println("        swaps++;");
        System.out.println("        ");
        System.out.println("        return i + 1;");
        System.out.println("    }");
        System.out.println("    // ... restante do código ...");
        System.out.println("}");
        System.out.println("```\n");
        
        // Merge Sort (versão simplificada)
        System.out.println("=== MERGE SORT ===");
        System.out.println("```java");
        System.out.println("public class MergeSort implements SortingAlgorithm {");
        System.out.println("    private void merge(int[] arr, int left, int mid, int right) {");
        System.out.println("        // Create temporary arrays");
        System.out.println("        int[] L = Arrays.copyOfRange(arr, left, mid + 1);");
        System.out.println("        int[] R = Arrays.copyOfRange(arr, mid + 1, right + 1);");
        System.out.println("        ");
        System.out.println("        // Merge the temporary arrays back into arr[left..right]");
        System.out.println("        int i = 0, j = 0, k = left;");
        System.out.println("        ");
        System.out.println("        while (i < L.length && j < R.length) {");
        System.out.println("            comparisons++;");
        System.out.println("            ");
        System.out.println("            if (L[i] <= R[j]) {");
        System.out.println("                arr[k] = L[i];");
        System.out.println("                i++;");
        System.out.println("            } else {");
        System.out.println("                arr[k] = R[j];");
        System.out.println("                j++;");
        System.out.println("            }");
        System.out.println("            swaps++;");
        System.out.println("            k++;");
        System.out.println("        }");
        System.out.println("        ");
        System.out.println("        // Copy remaining elements");
        System.out.println("        while (i < L.length) {");
        System.out.println("            arr[k] = L[i];");
        System.out.println("            i++; k++; swaps++;");
        System.out.println("        }");
        System.out.println("        ");
        System.out.println("        while (j < R.length) {");
        System.out.println("            arr[k] = R[j];");
        System.out.println("            j++; k++; swaps++;");
        System.out.println("        }");
        System.out.println("    }");
        System.out.println("    // ... restante do código ...");
        System.out.println("}");
        System.out.println("```\n");
        
        System.out.println("\nPressione ENTER para voltar ao menu principal...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        main(new String[0]); // Voltar ao menu principal
    }
}