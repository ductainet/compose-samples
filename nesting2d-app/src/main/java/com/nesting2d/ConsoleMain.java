package com.nesting2d;

import com.nesting2d.model.CadFile;
import com.nesting2d.service.CadFileService;

import java.io.File;
import java.util.Scanner;

public class ConsoleMain {
    public static void main(String[] args) {
        System.out.println("🚀 Nesting 2D - CAD File Importer (Console Version)");
        System.out.println("==================================================");
        System.out.println();
        
        CadFileService cadFileService = new CadFileService();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("📋 Menu:");
            System.out.println("1. Import CAD file");
            System.out.println("2. Show file information");
            System.out.println("3. Exit");
            System.out.print("Choose an option (1-3): ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    importCadFile(cadFileService, scanner);
                    break;
                case "2":
                    showFileInfo(cadFileService);
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
    
    private static void importCadFile(CadFileService cadFileService, Scanner scanner) {
        System.out.print("📁 Enter path to CAD file (.dwg or .dxf): ");
        String filePath = scanner.nextLine().trim();
        
        if (filePath.isEmpty()) {
            System.out.println("❌ No file path provided.");
            return;
        }
        
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("❌ File not found: " + filePath);
            return;
        }
        
        if (!cadFileService.isSupportedFile(file)) {
            System.out.println("❌ Unsupported file type. Please use .dwg or .dxf files.");
            return;
        }
        
        try {
            System.out.println("⏳ Importing file...");
            CadFile cadFile = cadFileService.importCadFile(file);
            
            System.out.println("✅ File imported successfully!");
            System.out.println("📊 File Information:");
            System.out.println("   Name: " + cadFile.getFileName());
            System.out.println("   Type: " + cadFile.getFileType());
            System.out.println("   Size: " + formatFileSize(cadFile.getFileSize()));
            System.out.println("   Entities: " + cadFile.getEntityCount());
            
            if (cadFile.getDrawingInfo() != null) {
                System.out.println("   Version: " + cadFile.getDrawingInfo().getVersion());
                System.out.println("   Units: " + cadFile.getDrawingInfo().getUnits());
                System.out.println("   Dimensions: " + 
                    cadFile.getDrawingInfo().getWidth() + " x " + 
                    cadFile.getDrawingInfo().getHeight());
            }
            
            // Store for later viewing
            CadFileManager.setCurrentFile(cadFile);
            
        } catch (Exception e) {
            System.out.println("❌ Error importing file: " + e.getMessage());
        }
    }
    
    private static void showFileInfo(CadFileService cadFileService) {
        CadFile currentFile = CadFileManager.getCurrentFile();
        
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
        java.util.Map<String, Integer> entityCounts = new java.util.HashMap<>();
        for (com.nesting2d.model.CadEntity entity : currentFile.getEntities()) {
            entityCounts.put(entity.getType(), entityCounts.getOrDefault(entity.getType(), 0) + 1);
        }
        
        for (java.util.Map.Entry<String, Integer> entry : entityCounts.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        
        System.out.println();
        System.out.println("Sample Entities:");
        System.out.println("===============");
        
        int count = 0;
        for (com.nesting2d.model.CadEntity entity : currentFile.getEntities()) {
            if (count >= 5) break;
            
            System.out.println("  " + (count + 1) + ". " + entity.getType() + 
                " [Layer: " + entity.getLayer() + ", Color: " + entity.getColor() + "]");
            
            if (entity.getPoints() != null && !entity.getPoints().isEmpty()) {
                System.out.print("     Points: ");
                for (int i = 0; i < Math.min(entity.getPoints().size(), 3); i++) {
                    com.nesting2d.model.Point2D point = entity.getPoints().get(i);
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
    
    // Simple file manager for console version
    private static class CadFileManager {
        private static CadFile currentFile;
        
        public static void setCurrentFile(CadFile file) {
            currentFile = file;
        }
        
        public static CadFile getCurrentFile() {
            return currentFile;
        }
    }
}