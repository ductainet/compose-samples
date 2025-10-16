package com.nesting2d;

import com.nesting2d.model.CadFile;
import com.nesting2d.model.CadEntity;
import com.nesting2d.model.Point2D;
import com.nesting2d.model.CadDrawingInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SimpleConsoleMain {
    public static void main(String[] args) {
        System.out.println("🚀 Nesting 2D - CAD File Importer (Simple Console Version)");
        System.out.println("=========================================================");
        System.out.println();
        
        Scanner scanner = new Scanner(System.in);
        CadFile currentFile = null;
        
        while (true) {
            System.out.println("📋 Menu:");
            System.out.println("1. Import CAD file (Demo)");
            System.out.println("2. Show file information");
            System.out.println("3. Exit");
            System.out.print("Choose an option (1-3): ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    currentFile = importCadFile(scanner);
                    break;
                case "2":
                    showFileInfo(currentFile);
                    break;
                case "3":
                    System.out.println("👋 Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("❌ Invalid option. Please choose 1-3.");
            }
            System.out.println();
        }
    }
    
    private static CadFile importCadFile(Scanner scanner) {
        System.out.print("📁 Enter file name (or press Enter for demo): ");
        String fileName = scanner.nextLine().trim();
        
        if (fileName.isEmpty()) {
            fileName = "demo.dxf";
        }
        
        System.out.println("⏳ Creating demo CAD file...");
        
        // Create a demo CAD file
        CadFile cadFile = new CadFile(fileName, "/path/to/" + fileName, "DXF", 1024);
        
        // Create drawing info
        CadDrawingInfo drawingInfo = new CadDrawingInfo();
        drawingInfo.setVersion("R12");
        drawingInfo.setUnits("Millimeters");
        drawingInfo.setLayerCount(2);
        drawingInfo.setEntityCount(6);
        drawingInfo.setMinPoint(new Point2D(0, 0));
        drawingInfo.setMaxPoint(new Point2D(100, 100));
        cadFile.setDrawingInfo(drawingInfo);
        
        // Create sample entities
        List<CadEntity> entities = new ArrayList<>();
        
        // Line
        CadEntity line = new CadEntity("LINE");
        line.setLayer("0");
        line.setColor("7");
        line.setLineWidth(0.0);
        List<Point2D> linePoints = new ArrayList<>();
        linePoints.add(new Point2D(0, 0));
        linePoints.add(new Point2D(100, 100));
        line.setPoints(linePoints);
        entities.add(line);
        
        // Circle
        CadEntity circle = new CadEntity("CIRCLE");
        circle.setLayer("0");
        circle.setColor("7");
        circle.setLineWidth(0.0);
        circle.setRadius(25.0);
        List<Point2D> circlePoints = new ArrayList<>();
        circlePoints.add(new Point2D(50, 50));
        circle.setPoints(circlePoints);
        entities.add(circle);
        
        // Rectangle (polyline)
        CadEntity rectangle = new CadEntity("LWPOLYLINE");
        rectangle.setLayer("0");
        rectangle.setColor("7");
        rectangle.setLineWidth(0.0);
        rectangle.setClosed(true);
        List<Point2D> rectPoints = new ArrayList<>();
        rectPoints.add(new Point2D(10, 10));
        rectPoints.add(new Point2D(90, 10));
        rectPoints.add(new Point2D(90, 40));
        rectPoints.add(new Point2D(10, 40));
        rectangle.setPoints(rectPoints);
        entities.add(rectangle);
        
        // Arc
        CadEntity arc = new CadEntity("ARC");
        arc.setLayer("0");
        arc.setColor("7");
        arc.setLineWidth(0.0);
        arc.setRadius(15.0);
        arc.setStartAngle(0.0);
        arc.setEndAngle(90.0);
        List<Point2D> arcPoints = new ArrayList<>();
        arcPoints.add(new Point2D(75, 75));
        arc.setPoints(arcPoints);
        entities.add(arc);
        
        // Text
        CadEntity text = new CadEntity("TEXT");
        text.setLayer("0");
        text.setColor("7");
        text.setLineWidth(0.0);
        text.setText("Sample CAD Text");
        List<Point2D> textPoints = new ArrayList<>();
        textPoints.add(new Point2D(20, 80));
        text.setPoints(textPoints);
        entities.add(text);
        
        // Another line
        CadEntity line2 = new CadEntity("LINE");
        line2.setLayer("1");
        line2.setColor("1");
        line2.setLineWidth(2.0);
        List<Point2D> line2Points = new ArrayList<>();
        line2Points.add(new Point2D(30, 30));
        line2Points.add(new Point2D(70, 70));
        line2.setPoints(line2Points);
        entities.add(line2);
        
        cadFile.setEntities(entities);
        
        System.out.println("✅ Demo CAD file created successfully!");
        System.out.println("📊 File Information:");
        System.out.println("   Name: " + cadFile.getFileName());
        System.out.println("   Type: " + cadFile.getFileType());
        System.out.println("   Size: " + formatFileSize(cadFile.getFileSize()));
        System.out.println("   Entities: " + cadFile.getEntityCount());
        System.out.println("   Version: " + cadFile.getDrawingInfo().getVersion());
        System.out.println("   Units: " + cadFile.getDrawingInfo().getUnits());
        System.out.println("   Dimensions: " + 
            cadFile.getDrawingInfo().getWidth() + " x " + 
            cadFile.getDrawingInfo().getHeight());
        
        return cadFile;
    }
    
    private static void showFileInfo(CadFile currentFile) {
        if (currentFile == null) {
            System.out.println("❌ No file imported yet. Please import a file first.");
            return;
        }
        
        System.out.println("📊 Current File Information:");
        System.out.println("=============================");
        System.out.println("File Name: " + currentFile.getFileName());
        System.out.println("File Type: " + currentFile.getFileType());
        System.out.println("File Size: " + formatFileSize(currentFile.getFileSize()));
        System.out.println("File Path: " + currentFile.getFilePath());
        System.out.println();
        
        if (currentFile.getDrawingInfo() != null) {
            System.out.println("Drawing Information:");
            System.out.println("  Version: " + currentFile.getDrawingInfo().getVersion());
            System.out.println("  Units: " + currentFile.getDrawingInfo().getUnits());
            System.out.println("  Layers: " + currentFile.getDrawingInfo().getLayerCount());
            System.out.println("  Entities: " + currentFile.getDrawingInfo().getEntityCount());
            System.out.println("  Dimensions: " + 
                currentFile.getDrawingInfo().getWidth() + " x " + 
                currentFile.getDrawingInfo().getHeight());
            System.out.println();
        }
        
        System.out.println("Entities (" + currentFile.getEntityCount() + " total):");
        System.out.println("==========");
        
        // Group entities by type
        Map<String, Integer> entityCounts = new HashMap<>();
        for (CadEntity entity : currentFile.getEntities()) {
            entityCounts.put(entity.getType(), entityCounts.getOrDefault(entity.getType(), 0) + 1);
        }
        
        for (Map.Entry<String, Integer> entry : entityCounts.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        
        System.out.println();
        System.out.println("Sample Entities:");
        System.out.println("===============");
        
        int count = 0;
        for (CadEntity entity : currentFile.getEntities()) {
            if (count >= 5) break;
            
            System.out.println("  " + (count + 1) + ". " + entity.getType() + 
                " [Layer: " + entity.getLayer() + ", Color: " + entity.getColor() + "]");
            
            if (entity.getPoints() != null && !entity.getPoints().isEmpty()) {
                System.out.print("     Points: ");
                for (int i = 0; i < Math.min(entity.getPoints().size(), 3); i++) {
                    Point2D point = entity.getPoints().get(i);
                    System.out.print("(" + String.format("%.1f", point.getX()) + 
                        ", " + String.format("%.1f", point.getY()) + ")");
                    if (i < Math.min(entity.getPoints().size(), 3) - 1) {
                        System.out.print(", ");
                    }
                }
                if (entity.getPoints().size() > 3) {
                    System.out.print("...");
                }
                System.out.println();
            }
            
            if (entity.getType().equals("CIRCLE") && entity.getRadius() > 0) {
                System.out.println("     Radius: " + String.format("%.2f", entity.getRadius()));
            }
            
            if (entity.getType().equals("ARC") && entity.getRadius() > 0) {
                System.out.println("     Radius: " + String.format("%.2f", entity.getRadius()) + 
                    ", Angle: " + String.format("%.1f", entity.getStartAngle()) + "° - " + 
                    String.format("%.1f", entity.getEndAngle()) + "°");
            }
            
            if (entity.getType().equals("TEXT") && entity.getText() != null) {
                System.out.println("     Text: " + entity.getText());
            }
            
            count++;
        }
        
        if (currentFile.getEntityCount() > 5) {
            System.out.println("  ... and " + (currentFile.getEntityCount() - 5) + " more entities");
        }
    }
    
    private static String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        if (bytes < 1024 * 1024 * 1024) return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
        return String.format("%.1f GB", bytes / (1024.0 * 1024.0 * 1024.0));
    }
}