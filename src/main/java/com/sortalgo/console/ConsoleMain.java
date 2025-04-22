package com.sortalgo.console;

import com.sortalgo.algorithm.*;
import com.sortalgo.util.ArrayGenerator;

import java.util.Scanner;

/**
 * Classe principal para o aplicativo console com entrada de usuário mais detalhada.
 */
public class ConsoleMain {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        limparConsole();
        imprimirCabecalho();
        
        boolean sair = false;
        while (!sair) {
            System.out.println("Como você deseja criar o array?");
            System.out.println("1. Gerar automaticamente");
            System.out.println("2. Inserir manualmente");
            System.out.println("3. Sair");
            
            int opcao = lerInteiro("Digite sua escolha (1-3): ", 1, 3);
            
            if (opcao == 1) {
                processarArrayAutomatico();
            } else if (opcao == 2) {
                processarArrayManual();
            } else {
                sair = true;
            }
        }
        
        System.out.println("\nObrigado por usar o Visualizador de Algoritmos de Ordenação!");
        scanner.close();
    }
    
    /**
     * Processa a opção de gerar array automaticamente.
     */
    private static void processarArrayAutomatico() {
        limparConsole();
        System.out.println("===== GERAR ARRAY AUTOMATICAMENTE =====\n");
        
        // Escolher tamanho do array
        System.out.println("Escolha o tamanho do array:");
        System.out.println("1. Pequeno (10 elementos)");
        System.out.println("2. Médio (20 elementos)");
        System.out.println("3. Grande (50 elementos)");
        int opcaoTamanho = lerInteiro("Digite sua escolha (1-3): ", 1, 3);
        
        int tamanho;
        switch (opcaoTamanho) {
            case 1: tamanho = 10; break;
            case 2: tamanho = 20; break;
            default: tamanho = 50; break;
        }
        
        // Escolher tipo de array
        System.out.println("\nEscolha o tipo de array:");
        System.out.println("1. Aleatório");
        System.out.println("2. Quase ordenado");
        System.out.println("3. Invertido (ordem decrescente)");
        System.out.println("4. Poucos valores únicos");
        int opcaoTipo = lerInteiro("Digite sua escolha (1-4): ", 1, 4);
        
        int[] array;
        switch (opcaoTipo) {
            case 1:
                array = ArrayGenerator.generateRandomArray(tamanho, 1, 99);
                break;
            case 2:
                array = ArrayGenerator.generateNearlySortedArray(tamanho, (int)(tamanho * 0.1));
                break;
            case 3:
                array = ArrayGenerator.generateReversedArray(tamanho);
                break;
            case 4:
                array = ArrayGenerator.generateFewUniqueArray(tamanho, 5);
                break;
            default:
                array = ArrayGenerator.generateRandomArray(tamanho, 1, 99);
        }
        
        selecionarAlgoritmo(array);
    }
    
    /**
     * Processa a opção de inserir array manualmente.
     */
    private static void processarArrayManual() {
        limparConsole();
        System.out.println("===== INSERIR ARRAY MANUALMENTE =====\n");
        
        int tamanho = lerInteiro("Digite o tamanho do array (1-50): ", 1, 50);
        int[] array = new int[tamanho];
        
        System.out.println("\nDigite os elementos do array:");
        for (int i = 0; i < tamanho; i++) {
            array[i] = lerInteiro("Elemento " + (i + 1) + ": ", Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        
        selecionarAlgoritmo(array);
    }
    
    /**
     * Permite ao usuário selecionar um algoritmo para ordenar o array.
     */
    private static void selecionarAlgoritmo(int[] array) {
        limparConsole();
        System.out.println("===== SELECIONAR ALGORITMO =====\n");
        
        System.out.println("Escolha um algoritmo de ordenação:");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Selection Sort");
        System.out.println("3. Insertion Sort");
        System.out.println("4. Quick Sort");
        System.out.println("5. Merge Sort");
        System.out.println("6. Comparar Todos");
        
        int opcao = lerInteiro("Digite sua escolha (1-6): ", 1, 6);
        
        SortingAlgorithm algoritmo = null;
        
        switch (opcao) {
            case 1:
                algoritmo = new BubbleSort();
                break;
            case 2:
                algoritmo = new SelectionSort();
                break;
            case 3:
                algoritmo = new InsertionSort();
                break;
            case 4:
                algoritmo = new QuickSort();
                break;
            case 5:
                algoritmo = new MergeSort();
                break;
            case 6:
                compararAlgoritmos(array);
                return;
        }
        
        System.out.println("\nEscolha a velocidade da visualização:");
        System.out.println("1. Lenta");
        System.out.println("2. Média");
        System.out.println("3. Rápida");
        int opcaoVelocidade = lerInteiro("Opção: ", 1, 3);
        
        int delayMs;
        switch (opcaoVelocidade) {
            case 1: delayMs = 800; break; // Lenta
            case 2: delayMs = 300; break; // Média
            default: delayMs = 100; break; // Rápida
        }
        
        limparConsole();
        ConsoleVisualizer.visualize(algoritmo, array, delayMs);
        
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Compara o desempenho de todos os algoritmos usando o mesmo array.
     */
    private static void compararAlgoritmos(int[] arrayOriginal) {
        limparConsole();
        System.out.println("===== COMPARAÇÃO DE ALGORITMOS =====\n");
        
        // Criar cópias do array para cada algoritmo
        int[] arrayBubble = arrayOriginal.clone();
        int[] arraySelection = arrayOriginal.clone();
        int[] arrayInsertion = arrayOriginal.clone();
        int[] arrayQuick = arrayOriginal.clone();
        int[] arrayMerge = arrayOriginal.clone();
        
        // Bubble Sort
        long inicioBubble = System.currentTimeMillis();
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.sort(arrayBubble);
        long fimBubble = System.currentTimeMillis();
        
        // Selection Sort
        long inicioSelection = System.currentTimeMillis();
        SelectionSort selectionSort = new SelectionSort();
        selectionSort.sort(arraySelection);
        long fimSelection = System.currentTimeMillis();
        
        // Insertion Sort
        long inicioInsertion = System.currentTimeMillis();
        InsertionSort insertionSort = new InsertionSort();
        insertionSort.sort(arrayInsertion);
        long fimInsertion = System.currentTimeMillis();
        
        // Quick Sort
        long inicioQuick = System.currentTimeMillis();
        QuickSort quickSort = new QuickSort();
        quickSort.sort(arrayQuick);
        long fimQuick = System.currentTimeMillis();
        
        // Merge Sort
        long inicioMerge = System.currentTimeMillis();
        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(arrayMerge);
        long fimMerge = System.currentTimeMillis();
        
        // Exibir resultados
        System.out.println("Resultados da comparação para um array de " + arrayOriginal.length + " elementos:");
        System.out.println("-----------------------------------------------------");
        System.out.println("Algoritmo      | Tempo (ms) | Comparações | Trocas");
        System.out.println("-----------------------------------------------------");
        System.out.printf("%-15s| %-11d| %-12d| %-7d\n", "Bubble Sort", (fimBubble - inicioBubble), bubbleSort.getComparisons(), bubbleSort.getSwaps());
        System.out.printf("%-15s| %-11d| %-12d| %-7d\n", "Selection Sort", (fimSelection - inicioSelection), selectionSort.getComparisons(), selectionSort.getSwaps());
        System.out.printf("%-15s| %-11d| %-12d| %-7d\n", "Insertion Sort", (fimInsertion - inicioInsertion), insertionSort.getComparisons(), insertionSort.getSwaps());
        System.out.printf("%-15s| %-11d| %-12d| %-7d\n", "Quick Sort", (fimQuick - inicioQuick), quickSort.getComparisons(), quickSort.getSwaps());
        System.out.printf("%-15s| %-11d| %-12d| %-7d\n", "Merge Sort", (fimMerge - inicioMerge), mergeSort.getComparisons(), mergeSort.getSwaps());
        System.out.println("-----------------------------------------------------");
        
        System.out.println("\nDeseja visualizar algum desses algoritmos em detalhe?");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Selection Sort");
        System.out.println("3. Insertion Sort");
        System.out.println("4. Quick Sort");
        System.out.println("5. Merge Sort");
        System.out.println("6. Voltar ao menu principal");
        
        int opcao = lerInteiro("Digite sua escolha (1-6): ", 1, 6);
        
        if (opcao < 6) {
            SortingAlgorithm algoritmo = null;
            
            switch (opcao) {
                case 1:
                    algoritmo = new BubbleSort();
                    break;
                case 2:
                    algoritmo = new SelectionSort();
                    break;
                case 3:
                    algoritmo = new InsertionSort();
                    break;
                case 4:
                    algoritmo = new QuickSort();
                    break;
                case 5:
                    algoritmo = new MergeSort();
                    break;
            }
            
            System.out.println("\nEscolha a velocidade da visualização:");
            System.out.println("1. Lenta");
            System.out.println("2. Média");
            System.out.println("3. Rápida");
            int opcaoVelocidade = lerInteiro("Opção: ", 1, 3);
            
            int delayMs;
            switch (opcaoVelocidade) {
                case 1: delayMs = 800; break; // Lenta
                case 2: delayMs = 300; break; // Média
                default: delayMs = 100; break; // Rápida
            }
            
            limparConsole();
            // Clonar o array original para visualizar
            ConsoleVisualizer.visualize(algoritmo, arrayOriginal.clone(), delayMs);
            
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
        }
    }
    
    /**
     * Imprime o cabeçalho do programa.
     */
    private static void imprimirCabecalho() {
        System.out.println("======================================");
        System.out.println("| VISUALIZADOR DE ALGORITMOS DE ORDENAÇÃO |");
        System.out.println("======================================");
    }
    
    /**
     * Limpa o console.
     */
    private static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    /**
     * Lê um número inteiro do usuário com validação.
     */
    private static int lerInteiro(String mensagem, int min, int max) {
        int valor = 0;
        boolean valorValido = false;
        
        while (!valorValido) {
            System.out.print(mensagem);
            try {
                valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor >= min && valor <= max) {
                    valorValido = true;
                } else {
                    System.out.println("Por favor, digite um número entre " + min + " e " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
        
        return valor;
    }
}