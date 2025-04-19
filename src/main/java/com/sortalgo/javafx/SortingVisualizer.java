package com.sortalgo.javafx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.sortalgo.model.AlgorithmObserver;
import com.sortalgo.model.SortingEvent;

/**
 * Componente visual que exibe a representação gráfica do array sendo ordenado.
 */
public class SortingVisualizer extends Pane implements AlgorithmObserver {
    
    private static final double BAR_SPACING = 2;
    private static final Color BAR_COLOR = Color.CORNFLOWERBLUE;
    private static final Color HIGHLIGHT_COLOR = Color.RED;
    
    private Canvas canvas;
    private GraphicsContext gc;
    
    /**
     * Construtor padrão.
     */
    public SortingVisualizer() {
        this.canvas = new Canvas(800, 400);
        this.gc = canvas.getGraphicsContext2D();
        
        getChildren().add(canvas);
        
        // Ajustar tamanho do canvas quando o painel for redimensionado
        widthProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setWidth(newVal.doubleValue());
            redrawCanvas();
        });
        
        heightProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setHeight(newVal.doubleValue());
            redrawCanvas();
        });
    }
    
    /**
     * Limpa o canvas.
     */
    public void resetCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
    /**
     * Redesenha o conteúdo do canvas.
     */
    private void redrawCanvas() {
        // Implementação para redesenhar o canvas quando necessário
    }
    
    /**
     * Desenha o array no canvas.
     * 
     * @param array O array a ser desenhado
     * @param highlightIndices Índices a serem destacados
     */
    public void drawArray(int[] array, int[] highlightIndices) {
        if (array == null || array.length == 0) {
            return;
        }
        
        resetCanvas();
        
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();
        
        // Encontrar o valor máximo no array para normalização
        int maxValue = findMaxValue(array);
        
        // Calcular largura da barra com base no número de elementos
        double barWidth = (canvasWidth - (array.length * BAR_SPACING)) / array.length;
        
        // Desenhar cada elemento como uma barra
        for (int i = 0; i < array.length; i++) {
            // Normalizar altura da barra
            double barHeight = ((double) array[i] / maxValue) * (canvasHeight - 20);
            
            // Calcular posição da barra
            double x = i * (barWidth + BAR_SPACING);
            double y = canvasHeight - barHeight;
            
            // Verificar se o índice atual deve ser destacado
            boolean isHighlighted = false;
            for (int index : highlightIndices) {
                if (i == index) {
                    isHighlighted = true;
                    break;
                }
            }
            
            // Definir cor com base no destaque
            if (isHighlighted) {
                gc.setFill(HIGHLIGHT_COLOR);
            } else {
                gc.setFill(BAR_COLOR);
            }
            
            // Desenhar a barra
            gc.fillRect(x, y, barWidth, barHeight);
            
            // Desenhar o valor acima da barra
            gc.setFill(Color.BLACK);
            gc.fillText(Integer.toString(array[i]), x + (barWidth / 2) - 5, y - 5);
        }
    }
    
    /**
     * Encontra o valor máximo no array.
     */
    private int findMaxValue(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
    
    @Override
    public void onSortingStep(SortingEvent event) {
        drawArray(event.getCurrentArray(), event.getHighlightIndices());
    }
    
    @Override
    public void onSortingComplete(SortingEvent event) {
        drawArray(event.getCurrentArray(), new int[0]);
    }
}