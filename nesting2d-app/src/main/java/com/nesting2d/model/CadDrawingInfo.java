package com.nesting2d.model;

public class CadDrawingInfo {
    private String version;
    private String units;
    private Point2D minPoint;
    private Point2D maxPoint;
    private int layerCount;
    private int entityCount;
    
    public CadDrawingInfo() {
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getUnits() {
        return units;
    }
    
    public void setUnits(String units) {
        this.units = units;
    }
    
    public Point2D getMinPoint() {
        return minPoint;
    }
    
    public void setMinPoint(Point2D minPoint) {
        this.minPoint = minPoint;
    }
    
    public Point2D getMaxPoint() {
        return maxPoint;
    }
    
    public void setMaxPoint(Point2D maxPoint) {
        this.maxPoint = maxPoint;
    }
    
    public int getLayerCount() {
        return layerCount;
    }
    
    public void setLayerCount(int layerCount) {
        this.layerCount = layerCount;
    }
    
    public int getEntityCount() {
        return entityCount;
    }
    
    public void setEntityCount(int entityCount) {
        this.entityCount = entityCount;
    }
    
    public double getWidth() {
        if (minPoint != null && maxPoint != null) {
            return maxPoint.getX() - minPoint.getX();
        }
        return 0.0;
    }
    
    public double getHeight() {
        if (minPoint != null && maxPoint != null) {
            return maxPoint.getY() - minPoint.getY();
        }
        return 0.0;
    }
}