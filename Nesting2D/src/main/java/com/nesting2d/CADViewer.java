package com.nesting2d;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CADViewer extends BorderPane {
    
    private Pane drawingPane;
    private TextArea detailsArea;
    private CADFileReader fileReader;
    private List<CADEntity> entities;
    
    public CADViewer() {
        initializeComponents();
        fileReader = new CADFileReader();
        entities = new ArrayList<>();
    }
    
    private void initializeComponents() {
        // Drawing area
        drawingPane = new Pane();
        drawingPane.setPrefSize(800, 600);
        drawingPane.setStyle("-fx-background-color: white; -fx-border-color: #ccc;");
        
        ScrollPane scrollPane = new ScrollPane(drawingPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        // Details area
        detailsArea = new TextArea();
        detailsArea.setPrefRowCount(10);
        detailsArea.setEditable(false);
        detailsArea.setText("No CAD file loaded. Click 'Import CAD File' to load a .dwg or .dxf file.");
        
        // Layout
        setCenter(scrollPane);
        setBottom(detailsArea);
        
        // Add title
        Label titleLabel = new Label("CAD Drawing View");
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        setTop(titleLabel);
    }
    
    public void loadCADFile(File file) {
        try {
            // Clear previous content
            drawingPane.getChildren().clear();
            entities.clear();
            
            // Read CAD file
            entities = fileReader.readCADFile(file);
            
            // Display details
            displayFileDetails(file, entities);
            
            // Draw entities
            drawEntities();
            
        } catch (Exception e) {
            detailsArea.setText("Error loading CAD file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void displayFileDetails(File file, List<CADEntity> entities) {
        StringBuilder details = new StringBuilder();
        details.append("File: ").append(file.getName()).append("\n");
        details.append("Path: ").append(file.getAbsolutePath()).append("\n");
        details.append("Size: ").append(file.length()).append(" bytes\n");
        details.append("Entities found: ").append(entities.size()).append("\n\n");
        
        // Group entities by type
        int lines = 0, circles = 0, rectangles = 0, other = 0;
        for (CADEntity entity : entities) {
            switch (entity.getType()) {
                case LINE: lines++; break;
                case CIRCLE: circles++; break;
                case RECTANGLE: rectangles++; break;
                default: other++; break;
            }
        }
        
        details.append("Entity breakdown:\n");
        details.append("- Lines: ").append(lines).append("\n");
        details.append("- Circles: ").append(circles).append("\n");
        details.append("- Rectangles: ").append(rectangles).append("\n");
        details.append("- Other: ").append(other).append("\n");
        
        detailsArea.setText(details.toString());
    }
    
    private void drawEntities() {
        Group drawingGroup = new Group();
        
        for (CADEntity entity : entities) {
            switch (entity.getType()) {
                case LINE:
                    Line line = new Line(
                        entity.getStartX(), entity.getStartY(),
                        entity.getEndX(), entity.getEndY()
                    );
                    line.setStroke(Color.BLACK);
                    line.setStrokeWidth(1);
                    drawingGroup.getChildren().add(line);
                    break;
                    
                case CIRCLE:
                    Circle circle = new Circle(
                        entity.getCenterX(), entity.getCenterY(), entity.getRadius()
                    );
                    circle.setFill(Color.TRANSPARENT);
                    circle.setStroke(Color.BLACK);
                    circle.setStrokeWidth(1);
                    drawingGroup.getChildren().add(circle);
                    break;
                    
                case RECTANGLE:
                    Rectangle rect = new Rectangle(
                        entity.getX(), entity.getY(),
                        entity.getWidth(), entity.getHeight()
                    );
                    rect.setFill(Color.TRANSPARENT);
                    rect.setStroke(Color.BLACK);
                    rect.setStrokeWidth(1);
                    drawingGroup.getChildren().add(rect);
                    break;
            }
        }
        
        drawingPane.getChildren().add(drawingGroup);
    }
}