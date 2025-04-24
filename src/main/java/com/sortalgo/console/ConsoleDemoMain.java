package com.sortalgo.console;

import com.sortalgo.algorithm.*;
import com.sortalgo.util.ArrayGenerator;

import java.util.Scanner;

/**
 * Classe para demonstrar algoritmos de ordenação no console com valores predefinidos.
 * (Sem necessidade de entrada do usuário)
 */
public class ConsoleDemoMain {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        limparConsole();
        imprimirCabecalho();
        
        boolean sair = false;
        while (!sair) {
            exibirMenuPrincipal();
            int opcao = lerInteiro("Escolha uma opção: ", 1, 8);
            
            switch (opcao) {
                case 1: // Bubble Sort
                    executarDemonstracao(new BubbleSort());
                    break;
                case 2: // Selection Sort
                    executarDemonstracao(new SelectionSort());
                    break;
                case 3: // Insertion Sort
                    executarDemonstracao(new InsertionSort());
                    break;
                case 4: // Quick Sort
                    executarDemonstracao(new QuickSort());
                    break;
                case 5: // Merge Sort
                    executarDemonstracao(new MergeSort());
                    break;
                case 6: // Counting Sort
                    executarDemonstracao(new CountingSort());
                    break;
                case 7: // Demonstrar todos os algoritmos
                    demonstrarTodosAlgoritmos();
                    break;
                case 8: // Sair
                    sair = true;
                    break;
            }
        }
        
        System.out.println("\nObrigado por usar o Visualizador de Algoritmos de Ordenação!");
        scanner.close();
    }
    
    /**
     * Executa a demonstração de um algoritmo específico.
     */
    private static void executarDemonstracao(SortingAlgorithm algoritmo) {
        limparConsole();
        System.out.println("===== DEMONSTRAÇÃO: " + getNomeAlgoritmo(algoritmo) + " =====\n");
        
        // Exibir informações do algoritmo
        System.out.println("Descrição: " + algoritmo.getDescription());
        System.out.println("Complexidade (Pior caso): " + algoritmo.getWorstCaseComplexity());
        System.out.println("Complexidade (Caso médio): " + algoritmo.getAverageCaseComplexity());
        System.out.println("Complexidade (Melhor caso): " + algoritmo.getBestCaseComplexity());
        System.out.println("Complexidade de Espaço: " + algoritmo.getSpaceComplexity() + "\n");
        
        // Opções para tamanho e tipo de array
        System.out.println("Escolha o tamanho do array:");
        System.out.println("1. Pequeno (10 elementos)");
        System.out.println("2. Médio (20 elementos)");
        System.out.println("3. Grande (50 elementos)");
        int opcaoTamanho = lerInteiro("Opção: ", 1, 3);
        
        int tamanho;
        switch (opcaoTamanho) {
            case 1: tamanho = 10; break;
            case 2: tamanho = 20; break;
            default: tamanho = 50; break;
        }
        
        System.out.println("\nEscolha o tipo de array:");
        System.out.println("1. Aleatório");
        System.out.println("2. Quase ordenado");
        System.out.println("3. Invertido (ordem decrescente)");
        System.out.println("4. Poucos valores únicos");
        int opcaoTipo = lerInteiro("Opção: ", 1, 4);
        
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
     * Demonstra todos os algoritmos de ordenação em sequência.
     */
    private static void demonstrarTodosAlgoritmos() {
        limparConsole();
        System.out.println("===== DEMONSTRAÇÃO DE TODOS OS ALGORITMOS =====\n");
        
        System.out.println("Esta opção demonstrará todos os algoritmos de ordenação");
        System.out.println("usando o mesmo array, para facilitar a comparação.\n");
        
        // Opções para tamanho e tipo de array
        System.out.println("Escolha o tamanho do array:");
        System.out.println("1. Pequeno (10 elementos)");
        System.out.println("2. Médio (20 elementos)");
        int opcaoTamanho = lerInteiro("Opção: ", 1, 2);
        
        int tamanho = (opcaoTamanho == 1) ? 10 : 20;
        
        System.out.println("\nEscolha o tipo de array:");
        System.out.println("1. Aleatório");
        System.out.println("2. Quase ordenado");
        System.out.println("3. Invertido (ordem decrescente)");
        int opcaoTipo = lerInteiro("Opção: ", 1, 3);
        
        int[] arrayOriginal;
        switch (opcaoTipo) {
            case 1:
                arrayOriginal = ArrayGenerator.generateRandomArray(tamanho, 1, 99);
                break;
            case 2:
                arrayOriginal = ArrayGenerator.generateNearlySortedArray(tamanho, (int)(tamanho * 0.1));
                break;
            case 3:
                arrayOriginal = ArrayGenerator.generateReversedArray(tamanho);
                break;
            default:
                arrayOriginal = ArrayGenerator.generateRandomArray(tamanho, 1, 99);
        }
        
        // Criar cópias do array para cada algoritmo
        int[] arrayBubble = arrayOriginal.clone();
        int[] arraySelection = arrayOriginal.clone();
        int[] arrayInsertion = arrayOriginal.clone();
        int[] arrayQuick = arrayOriginal.clone();
        int[] arrayMerge = arrayOriginal.clone();
        int[] arrayCounting = arrayOriginal.clone();
        
        // Definir velocidade
        int delayMs = 200; // Velocidade média
        
        // Demonstrar cada algoritmo
        limparConsole();
        System.out.println("===== BUBBLE SORT =====");
        ConsoleVisualizer.visualize(new BubbleSort(), arrayBubble, delayMs);
        
        System.out.println("\nPressione ENTER para o próximo algoritmo...");
        scanner.nextLine();
        
        limparConsole();
        System.out.println("===== SELECTION SORT =====");
        ConsoleVisualizer.visualize(new SelectionSort(), arraySelection, delayMs);
        
        System.out.println("\nPressione ENTER para o próximo algoritmo...");
        scanner.nextLine();
        
        limparConsole();
        System.out.println("===== INSERTION SORT =====");
        ConsoleVisualizer.visualize(new InsertionSort(), arrayInsertion, delayMs);
        
        System.out.println("\nPressione ENTER para o próximo algoritmo...");
        scanner.nextLine();
        
        limparConsole();
        System.out.println("===== QUICK SORT =====");
        ConsoleVisualizer.visualize(new QuickSort(), arrayQuick, delayMs);
        
        System.out.println("\nPressione ENTER para o próximo algoritmo...");
        scanner.nextLine();
        
        limparConsole();
        System.out.println("===== MERGE SORT =====");
        ConsoleVisualizer.visualize(new MergeSort(), arrayMerge, delayMs);
        
        System.out.println("\nPressione ENTER para o próximo algoritmo...");
        scanner.nextLine();
        
        limparConsole();
        System.out.println("===== COUNTING SORT =====");
        ConsoleVisualizer.visualize(new CountingSort(), arrayCounting, delayMs);
        
        System.out.println("\nPressione ENTER para voltar ao menu principal...");
        scanner.nextLine();
    }
    
    /**
     * Exibe o menu principal.
     */
    private static void exibirMenuPrincipal() {
        System.out.println("\nEscolha um algoritmo para demonstrar:");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Selection Sort");
        System.out.println("3. Insertion Sort");
        System.out.println("4. Quick Sort");
        System.out.println("5. Merge Sort");
        System.out.println("6. Counting Sort");
        System.out.println("7. Demonstrar todos os algoritmos");
        System.out.println("8. Sair");
    }
    
    /**
     * Imprime o cabeçalho do programa.
     */
    private static void imprimirCabecalho() {
        System.out.println("+--------------------------------------------------+");
        System.out.println("|    VISUALIZADOR DE ALGORITMOS DE ORDENAÇÃO       |");
        System.out.println("|    Desenvolvido como projeto educacional         |");
        System.out.println("+--------------------------------------------------+");
    }
    
    /**
     * Limpa o console.
     */
    private static void limparConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    /**
     * Lê um número inteiro do usuário dentro de um intervalo.
     */
    private static int lerInteiro(String mensagem, int min, int max) {
        int valor;
        do {
            System.out.print(mensagem);
            try {
                valor = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                valor = min - 1;
            }
        } while (valor < min || valor > max);
        return valor;
    }
    
    /**
     * Obtém o nome do algoritmo com base na classe.
     */
    private static String getNomeAlgoritmo(SortingAlgorithm algorithm) {
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