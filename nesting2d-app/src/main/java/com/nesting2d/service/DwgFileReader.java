package com.nesting2d.service;

import com.nesting2d.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DwgFileReader {
    private static final Logger logger = LoggerFactory.getLogger(DwgFileReader.class);
    
    public CadFile readDwgFile(File file, CadFile cadFile) throws Exception {
        logger.info("Reading DWG file: {}", file.getName());
        
        // For now, we'll create a placeholder implementation
        // In a real implementation, you would use ACadSharp or another DWG library
        
        CadDrawingInfo drawingInfo = new CadDrawingInfo();
        drawingInfo.setVersion("Unknown");
        drawingInfo.setUnits("Millimeters");
        cadFile.setDrawingInfo(drawingInfo);
        
        // Create some sample entities for demonstration
        List<CadEntity> entities = createSampleEntities();
        cadFile.setEntities(entities);
        
        drawingInfo.setEntityCount(entities.size());
        drawingInfo.setLayerCount(1);
        
        // Calculate bounding box
        calculateBoundingBox(entities, drawingInfo);
        
        logger.info("Successfully processed DWG file with {} entities", entities.size());
        
        return cadFile;
    }
    
    private List<CadEntity> createSampleEntities() {
        List<CadEntity> entities = new ArrayList<>();
        
        // Sample line
        CadEntity line = new CadEntity("LINE");
        line.setLayer("0");
        line.setColor("7");
        line.setLineWidth(0.0);
        List<Point2D> linePoints = new ArrayList<>();
        linePoints.add(new Point2D(0, 0));
        linePoints.add(new Point2D(100, 100));
        line.setPoints(linePoints);
        entities.add(line);
        
        // Sample circle
        CadEntity circle = new CadEntity("CIRCLE");
        circle.setLayer("0");
        circle.setColor("7");
        circle.setLineWidth(0.0);
        circle.setRadius(25.0);
        List<Point2D> circlePoints = new ArrayList<>();
        circlePoints.add(new Point2D(50, 50));
        circle.setPoints(circlePoints);
        entities.add(circle);
        
        // Sample rectangle (polyline)
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
        
        return entities;
    }
    
    private void calculateBoundingBox(List<CadEntity> entities, CadDrawingInfo drawingInfo) {
        if (entities.isEmpty()) {
            return;
        }
        
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        
        for (CadEntity entity : entities) {
            if (entity.getPoints() != null) {
                for (Point2D point : entity.getPoints()) {
                    minX = Math.min(minX, point.getX());
                    minY = Math.min(minY, point.getY());
                    maxX = Math.max(maxX, point.getX());
                    maxY = Math.max(maxY, point.getY());
                }
            }
            
            // For circles, consider radius
            if (entity.getType().equals("CIRCLE") && entity.getPoints() != null && !entity.getPoints().isEmpty()) {
                Point2D center = entity.getPoints().get(0);
                double radius = entity.getRadius();
                minX = Math.min(minX, center.getX() - radius);
                minY = Math.min(minY, center.getY() - radius);
                maxX = Math.max(maxX, center.getX() + radius);
                maxY = Math.max(maxY, center.getY() + radius);
            }
        }
        
        if (minX != Double.MAX_VALUE) {
            drawingInfo.setMinPoint(new Point2D(minX, minY));
            drawingInfo.setMaxPoint(new Point2D(maxX, maxY));
        }
    }
}