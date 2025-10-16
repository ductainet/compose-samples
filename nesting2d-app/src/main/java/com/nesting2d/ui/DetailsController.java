package com.nesting2d.ui;

import com.nesting2d.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class DetailsController {
    
    @FXML private Label fileNameLabel;
    @FXML private Label fileTypeLabel;
    @FXML private Label fileSizeLabel;
    @FXML private Label filePathLabel;
    @FXML private Label versionLabel;
    @FXML private Label unitsLabel;
    @FXML private Label layerCountLabel;
    @FXML private Label entityCountLabel;
    @FXML private Label dimensionsLabel;
    
    @FXML private ComboBox<String> typeFilterCombo;
    @FXML private ComboBox<String> layerFilterCombo;
    @FXML private Button clearFilterButton;
    @FXML private TableView<CadEntity> entitiesTable;
    @FXML private TableColumn<CadEntity, String> typeColumn;
    @FXML private TableColumn<CadEntity, String> layerColumn;
    @FXML private TableColumn<CadEntity, String> colorColumn;
    @FXML private TableColumn<CadEntity, Double> lineWidthColumn;
    @FXML private TableColumn<CadEntity, String> pointsColumn;
    
    @FXML private TableView<LayerInfo> layersTable;
    @FXML private TableColumn<LayerInfo, String> layerNameColumn;
    @FXML private TableColumn<LayerInfo, Integer> entityCountColumn;
    @FXML private TableColumn<LayerInfo, String> layerColorColumn;
    
    @FXML private Button closeButton;
    
    private CadFile cadFile;
    private ObservableList<CadEntity> allEntities;
    private ObservableList<CadEntity> filteredEntities;
    private ObservableList<LayerInfo> layerInfos;
    
    @FXML
    public void initialize() {
        setupTableColumns();
        setupFilters();
    }
    
    public void setCadFile(CadFile cadFile) {
        this.cadFile = cadFile;
        updateGeneralInfo();
        updateEntitiesTable();
        updateLayersTable();
    }
    
    private void setupTableColumns() {
        // Entity table columns
        typeColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getType()));
        
        layerColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLayer()));
        
        colorColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getColor()));
        
        lineWidthColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getLineWidth()).asObject());
        
        pointsColumn.setCellValueFactory(cellData -> {
            CadEntity entity = cellData.getValue();
            String pointsText = formatEntityProperties(entity);
            return new javafx.beans.property.SimpleStringProperty(pointsText);
        });
        
        // Layer table columns
        layerNameColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        
        entityCountColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getEntityCount()).asObject());
        
        layerColorColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getColor()));
    }
    
    private void setupFilters() {
        typeFilterCombo.setPromptText("All Types");
        layerFilterCombo.setPromptText("All Layers");
        
        typeFilterCombo.setOnAction(e -> applyFilters());
        layerFilterCombo.setOnAction(e -> applyFilters());
    }
    
    private void updateGeneralInfo() {
        if (cadFile == null) return;
        
        fileNameLabel.setText(cadFile.getFileName());
        fileTypeLabel.setText(cadFile.getFileType());
        fileSizeLabel.setText(formatFileSize(cadFile.getFileSize()));
        filePathLabel.setText(cadFile.getFilePath());
        
        CadDrawingInfo drawingInfo = cadFile.getDrawingInfo();
        if (drawingInfo != null) {
            versionLabel.setText(drawingInfo.getVersion());
            unitsLabel.setText(drawingInfo.getUnits());
            layerCountLabel.setText(String.valueOf(drawingInfo.getLayerCount()));
            entityCountLabel.setText(String.valueOf(drawingInfo.getEntityCount()));
            
            if (drawingInfo.getMinPoint() != null && drawingInfo.getMaxPoint() != null) {
                double width = drawingInfo.getWidth();
                double height = drawingInfo.getHeight();
                dimensionsLabel.setText(String.format("%.2f x %.2f", width, height));
            } else {
                dimensionsLabel.setText("Unknown");
            }
        }
    }
    
    private void updateEntitiesTable() {
        if (cadFile == null) return;
        
        allEntities = FXCollections.observableArrayList(cadFile.getEntities());
        filteredEntities = FXCollections.observableArrayList(allEntities);
        entitiesTable.setItems(filteredEntities);
        
        // Update filter combos
        Set<String> types = allEntities.stream()
                .map(CadEntity::getType)
                .collect(Collectors.toSet());
        typeFilterCombo.setItems(FXCollections.observableArrayList(types));
        
        Set<String> layers = allEntities.stream()
                .map(CadEntity::getLayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        layerFilterCombo.setItems(FXCollections.observableArrayList(layers));
    }
    
    private void updateLayersTable() {
        if (cadFile == null) return;
        
        Map<String, LayerInfo> layerMap = new HashMap<>();
        
        for (CadEntity entity : cadFile.getEntities()) {
            String layerName = entity.getLayer() != null ? entity.getLayer() : "Default";
            layerMap.computeIfAbsent(layerName, LayerInfo::new).incrementEntityCount();
        }
        
        layerInfos = FXCollections.observableArrayList(layerMap.values());
        layersTable.setItems(layerInfos);
    }
    
    private String formatEntityProperties(CadEntity entity) {
        StringBuilder sb = new StringBuilder();
        
        if (entity.getPoints() != null && !entity.getPoints().isEmpty()) {
            if (entity.getType().equals("CIRCLE")) {
                Point2D center = entity.getPoints().get(0);
                sb.append(String.format("Center: (%.2f, %.2f), R: %.2f", 
                    center.getX(), center.getY(), entity.getRadius()));
            } else if (entity.getType().equals("ARC")) {
                Point2D center = entity.getPoints().get(0);
                sb.append(String.format("Center: (%.2f, %.2f), R: %.2f, A: %.1f°-%.1f°", 
                    center.getX(), center.getY(), entity.getRadius(), 
                    entity.getStartAngle(), entity.getEndAngle()));
            } else if (entity.getType().equals("TEXT")) {
                Point2D position = entity.getPoints().get(0);
                sb.append(String.format("Pos: (%.2f, %.2f), Text: %s", 
                    position.getX(), position.getY(), entity.getText()));
            } else {
                sb.append("Points: ");
                for (int i = 0; i < Math.min(entity.getPoints().size(), 3); i++) {
                    Point2D point = entity.getPoints().get(i);
                    sb.append(String.format("(%.1f,%.1f)", point.getX(), point.getY()));
                    if (i < Math.min(entity.getPoints().size(), 3) - 1) {
                        sb.append(", ");
                    }
                }
                if (entity.getPoints().size() > 3) {
                    sb.append("...");
                }
            }
        }
        
        return sb.toString();
    }
    
    private void applyFilters() {
        String selectedType = typeFilterCombo.getValue();
        String selectedLayer = layerFilterCombo.getValue();
        
        filteredEntities.clear();
        
        for (CadEntity entity : allEntities) {
            boolean typeMatch = selectedType == null || entity.getType().equals(selectedType);
            boolean layerMatch = selectedLayer == null || 
                (entity.getLayer() != null && entity.getLayer().equals(selectedLayer));
            
            if (typeMatch && layerMatch) {
                filteredEntities.add(entity);
            }
        }
    }
    
    @FXML
    private void handleTypeFilter() {
        applyFilters();
    }
    
    @FXML
    private void handleLayerFilter() {
        applyFilters();
    }
    
    @FXML
    private void handleClearFilter() {
        typeFilterCombo.setValue(null);
        layerFilterCombo.setValue(null);
        filteredEntities.clear();
        filteredEntities.addAll(allEntities);
    }
    
    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
    private String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        if (bytes < 1024 * 1024 * 1024) return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
        return String.format("%.1f GB", bytes / (1024.0 * 1024.0 * 1024.0));
    }
    
    // Inner class for layer information
    public static class LayerInfo {
        private String name;
        private int entityCount;
        private String color;
        
        public LayerInfo(String name) {
            this.name = name;
            this.entityCount = 0;
            this.color = "7"; // Default color
        }
        
        public void incrementEntityCount() {
            this.entityCount++;
        }
        
        public String getName() { return name; }
        public int getEntityCount() { return entityCount; }
        public String getColor() { return color; }
    }
}