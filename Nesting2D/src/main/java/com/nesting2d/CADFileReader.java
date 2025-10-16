package com.nesting2d;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CADFileReader {
    
    public List<CADEntity> readCADFile(File file) throws IOException {
        List<CADEntity> entities = new ArrayList<>();
        
        String fileName = file.getName().toLowerCase();
        
        if (fileName.endsWith(".dxf")) {
            entities = readDXFFile(file);
        } else if (fileName.endsWith(".dwg")) {
            // For DWG files, we'll need a specialized library
            // For now, we'll create some sample entities
            entities = createSampleEntities();
        } else {
            throw new IllegalArgumentException("Unsupported file format. Only .dwg and .dxf files are supported.");
        }
        
        return entities;
    }
    
    private List<CADEntity> readDXFFile(File file) throws IOException {
        List<CADEntity> entities = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String currentSection = "";
            boolean inEntitiesSection = false;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                // Check for section headers
                if (line.equals("ENTITIES")) {
                    inEntitiesSection = true;
                    continue;
                } else if (line.equals("ENDSEC")) {
                    inEntitiesSection = false;
                    continue;
                }
                
                if (!inEntitiesSection) continue;
                
                // Look for entity types
                if (line.equals("LINE")) {
                    entities.add(parseLine(reader));
                } else if (line.equals("CIRCLE")) {
                    entities.add(parseCircle(reader));
                } else if (line.equals("LWPOLYLINE")) {
                    entities.addAll(parsePolyline(reader));
                }
            }
        }
        
        return entities;
    }
    
    private CADEntity parseLine(BufferedReader reader) throws IOException {
        double startX = 0, startY = 0, endX = 0, endY = 0;
        String layer = "0";
        
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            
            if (line.equals("0")) break; // End of entity
            
            if (line.equals("8")) { // Layer
                layer = reader.readLine().trim();
            } else if (line.equals("10")) { // Start X
                startX = Double.parseDouble(reader.readLine().trim());
            } else if (line.equals("20")) { // Start Y
                startY = Double.parseDouble(reader.readLine().trim());
            } else if (line.equals("11")) { // End X
                endX = Double.parseDouble(reader.readLine().trim());
            } else if (line.equals("21")) { // End Y
                endY = Double.parseDouble(reader.readLine().trim());
            }
        }
        
        CADEntity entity = new CADEntity(startX, startY, endX, endY);
        entity.setLayer(layer);
        return entity;
    }
    
    private CADEntity parseCircle(BufferedReader reader) throws IOException {
        double centerX = 0, centerY = 0, radius = 0;
        String layer = "0";
        
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            
            if (line.equals("0")) break; // End of entity
            
            if (line.equals("8")) { // Layer
                layer = reader.readLine().trim();
            } else if (line.equals("10")) { // Center X
                centerX = Double.parseDouble(reader.readLine().trim());
            } else if (line.equals("20")) { // Center Y
                centerY = Double.parseDouble(reader.readLine().trim());
            } else if (line.equals("40")) { // Radius
                radius = Double.parseDouble(reader.readLine().trim());
            }
        }
        
        CADEntity entity = new CADEntity(centerX, centerY, radius);
        entity.setLayer(layer);
        return entity;
    }
    
    private List<CADEntity> parsePolyline(BufferedReader reader) throws IOException {
        List<CADEntity> entities = new ArrayList<>();
        List<Double> xCoords = new ArrayList<>();
        List<Double> yCoords = new ArrayList<>();
        String layer = "0";
        
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            
            if (line.equals("0")) break; // End of entity
            
            if (line.equals("8")) { // Layer
                layer = reader.readLine().trim();
            } else if (line.equals("10")) { // X coordinate
                xCoords.add(Double.parseDouble(reader.readLine().trim()));
            } else if (line.equals("20")) { // Y coordinate
                yCoords.add(Double.parseDouble(reader.readLine().trim()));
            }
        }
        
        // Convert polyline to individual line segments
        for (int i = 0; i < xCoords.size() - 1; i++) {
            CADEntity lineEntity = new CADEntity(
                xCoords.get(i), yCoords.get(i),
                xCoords.get(i + 1), yCoords.get(i + 1)
            );
            lineEntity.setLayer(layer);
            entities.add(lineEntity);
        }
        
        return entities;
    }
    
    private List<CADEntity> createSampleEntities() {
        List<CADEntity> entities = new ArrayList<>();
        
        // Create some sample entities for demonstration
        entities.add(new CADEntity(0, 0, 100, 0));      // Horizontal line
        entities.add(new CADEntity(100, 0, 100, 100));  // Vertical line
        entities.add(new CADEntity(100, 100, 0, 100));  // Horizontal line
        entities.add(new CADEntity(0, 100, 0, 0));      // Vertical line
        
        entities.add(new CADEntity(50, 50, 25));        // Circle
        
        entities.add(new CADEntity(150, 50, 50, 30));   // Rectangle
        
        return entities;
    }
}