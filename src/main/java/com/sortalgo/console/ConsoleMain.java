package com.sortalgo.console;

import com.sortalgo.algorithm.*;
import com.sortalgo.util.ArrayGenerator;

import java.util.Scanner;

/**
 * Classe principal para o visualizador de algoritmos de ordenação em console.
 */
public class ConsoleMain {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("| VISUALIZADOR DE ALGORITMOS DE ORDENAÇÃO |");
        System.out.println("======================================");
        System.out.println();
        
        while (true) {
            int[] array = criarArray();
            SortingAlgorithm algorithm = escolherAlgoritmo();
            
            ConsoleVisualizer.visualize(algorithm, array);
            
            System.out.println("\nDeseja executar outro algoritmo? (s/n)");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (resposta.equals("n")) {
                break;
            }
        }
        
        System.out.println("Obrigado por usar o Visualizador de Algoritmos de Ordenação!");
        scanner.close();
    }
    
    /**
     * Permite ao usuário escolher um algoritmo de ordenação.
     * 
     * @return O algoritmo de ordenação escolhido
     */
    private static SortingAlgorithm escolherAlgoritmo() {
        System.out.println("Escolha um algoritmo de ordenação:");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Selection Sort");
        System.out.println("3. Insertion Sort");
        System.out.println("4. Quick Sort");
        System.out.println("5. Merge Sort");
        
        int escolha = 0;
        boolean escolhaValida = false;
        
        while (!escolhaValida) {
            System.out.print("Digite o número do algoritmo (1-5): ");
            try {
                escolha = Integer.parseInt(scanner.nextLine().trim());
                if (escolha >= 1 && escolha <= 5) {
                    escolhaValida = true;
                } else {
                    System.out.println("Por favor, digite um número entre 1 e 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
        
        switch (escolha) {
            case 1:
                return new BubbleSort();
            case 2:
                return new SelectionSort();
            case 3:
                return new InsertionSort();
            case 4:
                return new QuickSort();
            case 5:
                return new MergeSort();
            default:
                return new BubbleSort(); // Padrão caso algo inesperado aconteça
        }
    }
    
    /**
     * Permite ao usuário criar um array para ordenação.
     * 
     * @return O array criado
     */
    private static int[] criarArray() {
        System.out.println("Como você deseja criar o array?");
        System.out.println("1. Gerar automaticamente");
        System.out.println("2. Inserir manualmente");
        
        int escolha = 0;
        boolean escolhaValida = false;
        
        while (!escolhaValida) {
            System.out.print("Digite sua escolha (1-2): ");
            try {
                escolha = Integer.parseInt(scanner.nextLine().trim());
                if (escolha >= 1 && escolha <= 2) {
                    escolhaValida = true;
                } else {
                    System.out.println("Por favor, digite 1 ou 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
        
        if (escolha == 1) {
            return gerarArrayAutomatico();
        } else {
            return inserirArrayManual();
        }
    }
    
    /**
     * Gera um array automático com tamanho e valores definidos pelo usuário.
     * 
     * @return O array gerado
     */
    private static int[] gerarArrayAutomatico() {
        System.out.print("Digite o tamanho do array (recomendado: 10-20): ");
        int tamanho = 0;
        
        while (tamanho <= 0) {
            try {
                tamanho = Integer.parseInt(scanner.nextLine().trim());
                if (tamanho <= 0) {
                    System.out.print("O tamanho deve ser positivo. Digite novamente: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Por favor, digite um número válido: ");
            }
        }
        
        System.out.print("Digite o valor máximo para os números (recomendado: 100): ");
        int valorMaximo = 0;
        
        while (valorMaximo <= 0) {
            try {
                valorMaximo = Integer.parseInt(scanner.nextLine().trim());
                if (valorMaximo <= 0) {
                    System.out.print("O valor máximo deve ser positivo. Digite novamente: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Por favor, digite um número válido: ");
            }
        }
        
        System.out.println("Escolha o tipo de array:");
        System.out.println("1. Aleatório");
        System.out.println("2. Quase ordenado");
        System.out.println("3. Inversamente ordenado");
        
        int tipoArray = 0;
        while (tipoArray < 1 || tipoArray > 3) {
            try {
                System.out.print("Digite sua escolha (1-3): ");
                tipoArray = Integer.parseInt(scanner.nextLine().trim());
                if (tipoArray < 1 || tipoArray > 3) {
                    System.out.println("Por favor, digite um número entre 1 e 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
        
        switch (tipoArray) {
            case 1:
                return ArrayGenerator.generateRandomArray(tamanho, 1, valorMaximo);
            case 2:
                return ArrayGenerator.generateNearlySortedArray(tamanho, (int)(tamanho * 0.1)); // 10% de swaps
            case 3:
                return ArrayGenerator.generateReversedArray(tamanho);
            default:
                return ArrayGenerator.generateRandomArray(tamanho, 1, valorMaximo);
        }
    }
    
    /**
     * Permite ao usuário inserir um array manualmente.
     * 
     * @return O array inserido
     */
    private static int[] inserirArrayManual() {
        System.out.print("Digite o tamanho do array: ");
        int tamanho = 0;
        
        while (tamanho <= 0) {
            try {
                tamanho = Integer.parseInt(scanner.nextLine().trim());
                if (tamanho <= 0) {
                    System.out.print("O tamanho deve ser positivo. Digite novamente: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Por favor, digite um número válido: ");
            }
        }
        
        int[] array = new int[tamanho];
        
        System.out.println("Digite os " + tamanho + " elementos do array:");
        for (int i = 0; i < tamanho; i++) {
            boolean valorValido = false;
            while (!valorValido) {
                try {
                    System.out.print("Elemento " + (i + 1) + ": ");
                    array[i] = Integer.parseInt(scanner.nextLine().trim());
                    valorValido = true;
                } catch (NumberFormatException e) {
                    System.out.println("Por favor, digite um número válido.");
                }
            }
        }
        
        return array;
    }
}