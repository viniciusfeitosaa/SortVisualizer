package com.sortalgo.swing;

import com.sortalgo.algorithm.*;
import com.sortalgo.model.SortingEvent;
import com.sortalgo.util.ArrayGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Implementação de uma interface gráfica para o visualizador de algoritmos de ordenação
 * usando Swing em vez de JavaFX.
 */
public class SwingVisualizer extends JFrame {
    
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;
    private static final int ARRAY_DISPLAY_HEIGHT = 400;
    private static final int BAR_GAP = 2;
    private static final Color BAR_COLOR = new Color(100, 149, 237); // Cornflower blue
    private static final Color HIGHLIGHT_COLOR = Color.RED;
    
    private JComboBox<String> algorithmSelector;
    private JButton generateButton;
    private JButton manualInputButton;
    private JSlider speedSlider;
    private JButton startButton;
    private JButton stepButton;
    private JButton stopButton;
    private JTextArea infoArea;
    private ArrayPanel arrayPanel;
    
    private int[] array;
    private SortingAlgorithm algorithm;
    private boolean isSorting = false;
    private Timer timer;
    
    public SwingVisualizer() {
        super("Visualizador de Algoritmos de Ordenação");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        initComponents();
        layoutComponents();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initComponents() {
        // Inicializar componentes da interface
        algorithmSelector = new JComboBox<>(new String[] {
            "Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort", "Merge Sort"
        });
        
        generateButton = new JButton("Gerar Array");
        generateButton.addActionListener(e -> generateRandomArray());
        
        manualInputButton = new JButton("Inserir Manualmente");
        manualInputButton.addActionListener(e -> openManualInputDialog());
        
        speedSlider = new JSlider(1, 1000, 500);
        speedSlider.setInverted(true); // Invertido: valores menores = mais rápido
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setMajorTickSpacing(200);
        
        startButton = new JButton("Iniciar");
        startButton.addActionListener(e -> startSorting());
        
        stepButton = new JButton("Passo a Passo");
        stepButton.addActionListener(e -> stepSorting());
        
        stopButton = new JButton("Parar");
        stopButton.addActionListener(e -> stopSorting());
        stopButton.setEnabled(false);
        
        infoArea = new JTextArea(5, 40);
        infoArea.setEditable(false);
        
        arrayPanel = new ArrayPanel();
        
        // Criar array inicial
        generateRandomArray();
    }
    
    private void layoutComponents() {
        // Painel de controle superior
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        controlPanel.add(new JLabel("Algoritmo:"));
        controlPanel.add(algorithmSelector);
        controlPanel.add(generateButton);
        controlPanel.add(manualInputButton);
        
        // Painel de controle inferior
        JPanel speedPanel = new JPanel(new BorderLayout(5, 0));
        speedPanel.add(new JLabel("Velocidade:"), BorderLayout.WEST);
        speedPanel.add(speedSlider, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(startButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(stopButton);
        
        JPanel lowerControlPanel = new JPanel(new BorderLayout());
        lowerControlPanel.add(speedPanel, BorderLayout.NORTH);
        lowerControlPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Área de informações
        JScrollPane infoScrollPane = new JScrollPane(infoArea);
        infoScrollPane.setPreferredSize(new Dimension(DEFAULT_WIDTH, 80));
        
        // Layout principal
        setLayout(new BorderLayout(10, 10));
        add(controlPanel, BorderLayout.NORTH);
        add(arrayPanel, BorderLayout.CENTER);
        add(lowerControlPanel, BorderLayout.SOUTH);
        add(infoScrollPane, BorderLayout.SOUTH);
        
        // Ajustar layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(arrayPanel, BorderLayout.CENTER);
        
        JPanel southPanel = new JPanel(new BorderLayout(0, 5));
        southPanel.add(lowerControlPanel, BorderLayout.NORTH);
        southPanel.add(infoScrollPane, BorderLayout.SOUTH);
        
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private void generateRandomArray() {
        int size = 20;
        array = ArrayGenerator.generateRandomArray(size, 1, 100);
        arrayPanel.setArray(array);
        arrayPanel.setHighlightIndices(new int[0]);
        updateInfoArea("Array gerado aleatoriamente com " + size + " elementos");
    }
    
    private void openManualInputDialog() {
        String input = JOptionPane.showInputDialog(this, 
                "Digite os elementos do array separados por vírgula:",
                "Entrada Manual", JOptionPane.PLAIN_MESSAGE);
        
        if (input != null && !input.trim().isEmpty()) {
            try {
                String[] elements = input.split(",");
                int[] newArray = new int[elements.length];
                
                for (int i = 0; i < elements.length; i++) {
                    newArray[i] = Integer.parseInt(elements[i].trim());
                }
                
                array = newArray;
                arrayPanel.setArray(array);
                arrayPanel.setHighlightIndices(new int[0]);
                updateInfoArea("Array inserido manualmente com " + array.length + " elementos");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                        "Entrada inválida. Por favor, digite apenas números separados por vírgula.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void createAlgorithm() {
        String selectedAlgorithm = (String) algorithmSelector.getSelectedItem();
        
        if ("Bubble Sort".equals(selectedAlgorithm)) {
            algorithm = new BubbleSort();
        } else if ("Selection Sort".equals(selectedAlgorithm)) {
            algorithm = new SelectionSort();
        } else if ("Insertion Sort".equals(selectedAlgorithm)) {
            algorithm = new InsertionSort();
        } else if ("Quick Sort".equals(selectedAlgorithm)) {
            algorithm = new QuickSort();
        } else if ("Merge Sort".equals(selectedAlgorithm)) {
            algorithm = new MergeSort();
        } else {
            algorithm = new BubbleSort(); // Default
        }
        
        algorithm.init(array.clone());
    }
    
    private void startSorting() {
        if (isSorting) {
            return;
        }
        
        createAlgorithm();
        isSorting = true;
        
        // Atualizar estado dos botões
        startButton.setEnabled(false);
        stepButton.setEnabled(false);
        generateButton.setEnabled(false);
        manualInputButton.setEnabled(false);
        algorithmSelector.setEnabled(false);
        stopButton.setEnabled(true);
        
        // Iniciar timer para animação
        int delay = speedSlider.getValue();
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performStep();
            }
        });
        timer.start();
    }
    
    private void stepSorting() {
        if (isSorting) {
            return;
        }
        
        if (algorithm == null || algorithm.isSortingComplete()) {
            createAlgorithm();
        }
        
        performStep();
    }
    
    private void performStep() {
        if (algorithm.isSortingComplete()) {
            stopSorting();
            updateInfoArea("Ordenação completa!");
            return;
        }
        
        SortingEvent event = algorithm.step();
        array = event.getCurrentArray();
        arrayPanel.setArray(array);
        arrayPanel.setHighlightIndices(event.getHighlightIndices());
        
        updateInfoArea(
            "Algoritmo: " + algorithmSelector.getSelectedItem() + "\n" +
            "Comparações: " + event.getComparisons() + "\n" +
            "Trocas: " + event.getSwaps() + "\n" +
            "Tempo: " + event.getExecutionTime() + " ms"
        );
    }
    
    private void stopSorting() {
        if (!isSorting) {
            return;
        }
        
        if (timer != null) {
            timer.stop();
        }
        
        isSorting = false;
        
        // Restaurar estado dos botões
        startButton.setEnabled(true);
        stepButton.setEnabled(true);
        generateButton.setEnabled(true);
        manualInputButton.setEnabled(true);
        algorithmSelector.setEnabled(true);
        stopButton.setEnabled(false);
    }
    
    private void updateInfoArea(String text) {
        infoArea.setText(text);
    }
    
    /**
     * Painel para exibir o array como barras.
     */
    private class ArrayPanel extends JPanel {
        private int[] array;
        private int[] highlightIndices;
        
        public ArrayPanel() {
            setPreferredSize(new Dimension(DEFAULT_WIDTH, ARRAY_DISPLAY_HEIGHT));
            setBackground(Color.WHITE);
            this.array = new int[0];
            this.highlightIndices = new int[0];
        }
        
        public void setArray(int[] array) {
            this.array = array.clone();
            repaint();
        }
        
        public void setHighlightIndices(int[] indices) {
            this.highlightIndices = indices.clone();
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            if (array == null || array.length == 0) {
                return;
            }
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = getWidth();
            int height = getHeight();
            
            // Encontrar o valor máximo no array
            int maxValue = Integer.MIN_VALUE;
            for (int value : array) {
                if (value > maxValue) {
                    maxValue = value;
                }
            }
            
            // Calcular largura da barra
            int barWidth = (width / array.length) - BAR_GAP;
            if (barWidth < 1) barWidth = 1;
            
            // Desenhar barras
            for (int i = 0; i < array.length; i++) {
                // Verificar se o índice está destacado
                boolean isHighlighted = false;
                for (int index : highlightIndices) {
                    if (i == index) {
                        isHighlighted = true;
                        break;
                    }
                }
                
                // Calcular altura da barra
                int barHeight = (int) (((double) array[i] / maxValue) * (height - 50));
                
                // Calcular posição X
                int x = i * (barWidth + BAR_GAP);
                
                // Definir cor
                if (isHighlighted) {
                    g2d.setColor(HIGHLIGHT_COLOR);
                } else {
                    g2d.setColor(BAR_COLOR);
                }
                
                // Desenhar barra
                g2d.fillRect(x, height - barHeight, barWidth, barHeight);
                
                // Desenhar valor
                g2d.setColor(Color.BLACK);
                g2d.drawString(String.valueOf(array[i]), x, height - barHeight - 5);
            }
        }
    }
    
    public static void main(String[] args) {
        // Executar a interface gráfica
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SwingVisualizer();
        });
    }
}