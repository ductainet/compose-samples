package com.nesting2d;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class Main extends Application {
    
    private CADViewer cadViewer;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Nesting 2D - CAD File Viewer");
        
        // Create main layout
        VBox root = new VBox(10);
        
        // Import button
        Button importButton = new Button("Import CAD File (.dwg/.dxf)");
        importButton.setOnAction(e -> importCADFile(primaryStage));
        
        // CAD viewer
        cadViewer = new CADViewer();
        
        root.getChildren().addAll(importButton, cadViewer);
        
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void importCADFile(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CAD File");
        
        // Set file filters
        FileChooser.ExtensionFilter dwgFilter = new FileChooser.ExtensionFilter("DWG Files", "*.dwg");
        FileChooser.ExtensionFilter dxfFilter = new FileChooser.ExtensionFilter("DXF Files", "*.dxf");
        FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("All CAD Files", "*.dwg", "*.dxf");
        
        fileChooser.getExtensionFilters().addAll(dwgFilter, dxfFilter, allFilter);
        
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            cadViewer.loadCADFile(selectedFile);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}