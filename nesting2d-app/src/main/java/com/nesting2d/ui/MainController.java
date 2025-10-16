package com.nesting2d.ui;

import com.nesting2d.model.CadFile;
import com.nesting2d.service.CadFileService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class MainController {
    
    @FXML private Label fileNameLabel;
    @FXML private Label fileTypeLabel;
    @FXML private Label fileSizeLabel;
    @FXML private Label importStatusLabel;
    @FXML private Label statusLabel;
    @FXML private Button importButton;
    @FXML private Button showDetailsButton;
    @FXML private MenuItem importMenuItem;
    @FXML private MenuItem showDetailsMenuItem;
    @FXML private Canvas previewCanvas;
    
    private CadFileService cadFileService;
    private CadFile currentCadFile;
    
    @FXML
    public void initialize() {
        cadFileService = new CadFileService();
        updateUI();
    }
    
    @FXML
    private void handleImportFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import CAD File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("CAD Files", "*.dwg", "*.dxf"),
            new FileChooser.ExtensionFilter("DWG Files", "*.dwg"),
            new FileChooser.ExtensionFilter("DXF Files", "*.dxf"),
            new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        
        File selectedFile = fileChooser.showOpenDialog(importButton.getScene().getWindow());
        if (selectedFile != null) {
            importCadFile(selectedFile);
        }
    }
    
    @FXML
    private void handleShowDetails() {
        if (currentCadFile != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/details.fxml"));
                Stage detailsStage = new Stage();
                detailsStage.setScene(new javafx.scene.Scene(loader.load()));
                detailsStage.setTitle("CAD File Details - " + currentCadFile.getFileName());
                
                DetailsController detailsController = loader.getController();
                detailsController.setCadFile(currentCadFile);
                
                detailsStage.show();
            } catch (Exception e) {
                showError("Error opening details window", e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleExit() {
        System.exit(0);
    }
    
    private void importCadFile(File file) {
        try {
            statusLabel.setText("Importing file...");
            importStatusLabel.setText("Importing...");
            
            currentCadFile = cadFileService.importCadFile(file);
            
            if (currentCadFile != null) {
                updateFileInfo();
                drawPreview();
                showDetailsButton.setDisable(false);
                showDetailsMenuItem.setDisable(false);
                statusLabel.setText("File imported successfully");
                importStatusLabel.setText("Success");
            } else {
                showError("Import Failed", "Could not import the selected file");
                importStatusLabel.setText("Failed");
            }
        } catch (Exception e) {
            showError("Import Error", e.getMessage());
            importStatusLabel.setText("Error");
        }
    }
    
    private void updateFileInfo() {
        if (currentCadFile != null) {
            fileNameLabel.setText(currentCadFile.getFileName());
            fileTypeLabel.setText(currentCadFile.getFileType());
            fileSizeLabel.setText(formatFileSize(currentCadFile.getFileSize()));
        }
    }
    
    private void updateUI() {
        boolean hasFile = currentCadFile != null;
        showDetailsButton.setDisable(!hasFile);
        showDetailsMenuItem.setDisable(!hasFile);
    }
    
    private void drawPreview() {
        if (currentCadFile == null) return;
        
        GraphicsContext gc = previewCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, previewCanvas.getWidth(), previewCanvas.getHeight());
        
        // Set up coordinate system
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.0);
        
        // Draw a simple preview (this will be enhanced with actual CAD data)
        gc.strokeRect(50, 50, 200, 100);
        gc.strokeLine(50, 50, 250, 150);
        gc.strokeOval(100, 100, 50, 50);
        
        // Add text
        gc.setFill(Color.BLUE);
        gc.fillText("CAD Preview - " + currentCadFile.getFileName(), 10, 20);
    }
    
    private String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        if (bytes < 1024 * 1024 * 1024) return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
        return String.format("%.1f GB", bytes / (1024.0 * 1024.0 * 1024.0));
    }
    
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}