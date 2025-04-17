package com.sortalgo.javafx;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import com.sortalgo.algorithm.SortingAlgorithm;
import com.sortalgo.model.AlgorithmObserver;
import com.sortalgo.model.SortingEvent;

/**
 * Painel para exibir informações sobre o algoritmo de ordenação.
 */
public class SortingInfoPane extends VBox implements AlgorithmObserver {
    
    private Label titleLabel;
    private Label descriptionLabel;
    private Label complexityLabel;
    private Label statisticsLabel;
    
    /**
     * Construtor padrão.
     */
    public SortingInfoPane() {
        setPadding(new Insets(10));
        setSpacing(10);
        setPrefWidth(280);
        
        // Criar componentes
        titleLabel = new Label("Algoritmo de Ordenação");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        
        descriptionLabel = new Label("Selecione um algoritmo e inicie a ordenação para ver os detalhes.");
        descriptionLabel.setWrapText(true);
        
        complexityLabel = new Label("Complexidade: ");
        complexityLabel.setWrapText(true);
        
        statisticsLabel = new Label("Estatísticas:");
        statisticsLabel.setWrapText(true);
        
        getChildren().addAll(
            titleLabel,
            new Label("Descrição:"),
            descriptionLabel,
            complexityLabel,
            statisticsLabel
        );
    }
    
    /**
     * Atualiza as informações do algoritmo.
     * 
     * @param algorithm O algoritmo de ordenação
     */
    public void updateAlgorithmInfo(SortingAlgorithm algorithm) {
        if (algorithm == null) {
            return;
        }
        
        // Atualizar título baseado na classe do algoritmo
        String algorithmName = algorithm.getClass().getSimpleName();
        titleLabel.setText(algorithmName);
        
        // Atualizar descrição
        descriptionLabel.setText(algorithm.getDescription());
        
        // Atualizar complexidade
        StringBuilder complexityText = new StringBuilder();
        complexityText.append("Pior caso: ").append(algorithm.getWorstCaseComplexity()).append("\n");
        complexityText.append("Caso médio: ").append(algorithm.getAverageCaseComplexity()).append("\n");
        complexityText.append("Melhor caso: ").append(algorithm.getBestCaseComplexity()).append("\n");
        complexityText.append("Espaço: ").append(algorithm.getSpaceComplexity());
        
        complexityLabel.setText(complexityText.toString());
    }
    
    /**
     * Atualiza as estatísticas de execução.
     * 
     * @param event O evento de ordenação
     */
    public void updateStatistics(SortingEvent event) {
        if (event == null) {
            return;
        }
        
        StringBuilder stats = new StringBuilder("Estatísticas:\n");
        stats.append("Comparações: ").append(event.getComparisons()).append("\n");
        stats.append("Trocas: ").append(event.getSwaps()).append("\n");
        
        if (event.getExecutionTime() > 0) {
            stats.append("Tempo de execução: ").append(event.getExecutionTime()).append(" ms");
        }
        
        statisticsLabel.setText(stats.toString());
    }
    
    @Override
    public void onAlgorithmStep(SortingEvent event) {
        if (event.isFirstStep()) {
            updateAlgorithmInfo(event.getAlgorithm());
        }
        
        updateStatistics(event);
    }
}