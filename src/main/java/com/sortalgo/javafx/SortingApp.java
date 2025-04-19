package com.sortalgo.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import com.sortalgo.algorithm.*;
import com.sortalgo.model.AlgorithmObserver;
import com.sortalgo.model.SortingEvent;
import com.sortalgo.model.SortingModel;
import com.sortalgo.util.ArrayGenerator;

/**
 * Aplicação JavaFX para visualização de algoritmos de ordenação.
 */
public class SortingApp extends Application {
    
    private SortingModel model;
    private SortingVisualizer visualizer;
    private SortingInfoPane infoPane;
    
    private Button generateButton;
    private Button startButton;
    private Button stepButton;
    private Button resetButton;
    private ComboBox<String> algorithmComboBox;
    private ComboBox<String> arrayTypeComboBox;
    private Slider speedSlider;
    private Slider sizeSlider;
    
    @Override
    public void start(Stage primaryStage) {
        model = new SortingModel();
        
        // Criação dos painéis de visualização e informações
        visualizer = new SortingVisualizer();
        infoPane = new SortingInfoPane();
        
        // Configuração do modelo e observadores
        model.addObserver(visualizer);
        model.addObserver(infoPane);
        
        // Layout principal
        BorderPane root = new BorderPane();
        root.setCenter(visualizer);
        root.setBottom(createControlPanel());
        root.setRight(infoPane);
        
        // Configuração da cena e estágio
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("Visualizador de Algoritmos de Ordenação");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Gera um array aleatório inicial
        generateNewArray();
    }
    
    /**
     * Cria o painel de controle com botões e opções.
     */
    private VBox createControlPanel() {
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
        
        // Configuração do ComboBox para algoritmos
        algorithmComboBox = new ComboBox<>();
        algorithmComboBox.getItems().addAll(
            "Bubble Sort",
            "Selection Sort",
            "Insertion Sort",
            "Quick Sort",
            "Merge Sort"
        );
        algorithmComboBox.setValue("Bubble Sort");
        
        // Configuração do ComboBox para tipos de array
        arrayTypeComboBox = new ComboBox<>();
        arrayTypeComboBox.getItems().addAll(
            "Aleatório",
            "Quase Ordenado",
            "Invertido",
            "Poucos Valores Únicos"
        );
        arrayTypeComboBox.setValue("Aleatório");
        
        // Configuração dos sliders
        sizeSlider = new Slider(5, 100, 30);
        sizeSlider.setShowTickMarks(true);
        sizeSlider.setShowTickLabels(true);
        sizeSlider.setMajorTickUnit(20);
        
        speedSlider = new Slider(1, 20, 5);
        speedSlider.setShowTickMarks(true);
        speedSlider.setShowTickLabels(true);
        speedSlider.setMajorTickUnit(5);
        
        // Configuração dos botões
        generateButton = new Button("Gerar Novo Array");
        generateButton.setOnAction(e -> generateNewArray());
        
        startButton = new Button("Iniciar");
        startButton.setOnAction(e -> startSorting());
        
        stepButton = new Button("Passo");
        stepButton.setOnAction(e -> stepSorting());
        
        resetButton = new Button("Resetar");
        resetButton.setOnAction(e -> resetSorting());
        
        // Criação dos painéis para sliders
        HBox sizeBox = new HBox(10, new Label("Tamanho:"), sizeSlider);
        HBox speedBox = new HBox(10, new Label("Velocidade:"), speedSlider);
        
        // Botões em linha
        HBox buttonBox = new HBox(10, generateButton, startButton, stepButton, resetButton);
        
        // Adicionar tudo ao painel de controle
        controlPanel.getChildren().addAll(
            new HBox(10, new Label("Algoritmo:"), algorithmComboBox),
            new HBox(10, new Label("Tipo de Array:"), arrayTypeComboBox),
            sizeBox,
            speedBox,
            buttonBox
        );
        
        return controlPanel;
    }
    
    /**
     * Gera um novo array com base nas configurações selecionadas.
     */
    private void generateNewArray() {
        int size = (int) sizeSlider.getValue();
        String arrayType = arrayTypeComboBox.getValue();
        
        int[] array;
        switch (arrayType) {
            case "Aleatório":
                array = ArrayGenerator.generateRandomArray(size, 1, 100);
                break;
            case "Quase Ordenado":
                array = ArrayGenerator.generateNearlySortedArray(size, (int)(size * 0.1));
                break;
            case "Invertido":
                array = ArrayGenerator.generateReversedArray(size);
                break;
            case "Poucos Valores Únicos":
                array = ArrayGenerator.generateFewUniqueArray(size, 5);
                break;
            default:
                array = ArrayGenerator.generateRandomArray(size, 1, 100);
        }
        
        model.setArray(array);
        visualizer.resetCanvas();
        visualizer.drawArray(array, new int[0]);
        
        // Habilitar botões apropriados
        startButton.setDisable(false);
        stepButton.setDisable(false);
        resetButton.setDisable(true);
    }
    
    /**
     * Inicia a ordenação do array atual.
     */
    private void startSorting() {
        String algorithm = algorithmComboBox.getValue();
        SortingAlgorithm sortingAlgorithm = null;
        
        switch (algorithm) {
            case "Bubble Sort":
                sortingAlgorithm = new BubbleSort();
                break;
            case "Selection Sort":
                sortingAlgorithm = new SelectionSort();
                break;
            case "Insertion Sort":
                sortingAlgorithm = new InsertionSort();
                break;
            case "Quick Sort":
                sortingAlgorithm = new QuickSort();
                break;
            case "Merge Sort":
                sortingAlgorithm = new MergeSort();
                break;
        }
        
        if (sortingAlgorithm != null) {
            model.setAlgorithm(sortingAlgorithm);
            
            // Desabilitar controles durante a ordenação
            generateButton.setDisable(true);
            startButton.setDisable(true);
            stepButton.setDisable(true);
            algorithmComboBox.setDisable(true);
            arrayTypeComboBox.setDisable(true);
            sizeSlider.setDisable(true);
            resetButton.setDisable(false);
            
            // Iniciar thread de ordenação
            new Thread(() -> {
                double speed = speedSlider.getValue();
                long delay = (long) (1000 / speed);
                
                while (!model.isSortingComplete()) {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    
                    Platform.runLater(() -> {
                        if (!model.isSortingComplete()) {
                            model.step();
                        } else {
                            // Reabilitar botões quando terminar
                            generateButton.setDisable(false);
                            algorithmComboBox.setDisable(false);
                            arrayTypeComboBox.setDisable(false);
                            sizeSlider.setDisable(false);
                        }
                    });
                }
            }).start();
        }
    }
    
    /**
     * Executa um único passo da ordenação.
     */
    private void stepSorting() {
        if (model.getAlgorithm() == null) {
            String algorithm = algorithmComboBox.getValue();
            SortingAlgorithm sortingAlgorithm = null;
            
            switch (algorithm) {
                case "Bubble Sort":
                    sortingAlgorithm = new BubbleSort();
                    break;
                case "Selection Sort":
                    sortingAlgorithm = new SelectionSort();
                    break;
                case "Insertion Sort":
                    sortingAlgorithm = new InsertionSort();
                    break;
                case "Quick Sort":
                    sortingAlgorithm = new QuickSort();
                    break;
                case "Merge Sort":
                    sortingAlgorithm = new MergeSort();
                    break;
            }
            
            if (sortingAlgorithm != null) {
                model.setAlgorithm(sortingAlgorithm);
                resetButton.setDisable(false);
            }
        }
        
        model.step();
        
        if (model.isSortingComplete()) {
            // Reabilitar botões quando terminar
            generateButton.setDisable(false);
            startButton.setDisable(true);
            stepButton.setDisable(true);
        }
    }
    
    /**
     * Reseta a ordenação para o estado inicial.
     */
    private void resetSorting() {
        // Reabilitar controles
        generateButton.setDisable(false);
        startButton.setDisable(false);
        stepButton.setDisable(false);
        algorithmComboBox.setDisable(false);
        arrayTypeComboBox.setDisable(false);
        sizeSlider.setDisable(false);
        resetButton.setDisable(true);
        
        // Gerar novo array
        generateNewArray();
    }
    
    /**
     * Método principal para iniciar a aplicação.
     */
    public static void main(String[] args) {
        launch(args);
    }
}