package com.nesting2d;

public class CADEntity {
    
    public enum EntityType {
        LINE, CIRCLE, RECTANGLE, ARC, POLYLINE, TEXT, OTHER
    }
    
    private EntityType type;
    private double x, y;
    private double startX, startY, endX, endY;
    private double centerX, centerY, radius;
    private double width, height;
    private String layer;
    private String color;
    
    // Constructor for Line
    public CADEntity(double startX, double startY, double endX, double endY) {
        this.type = EntityType.LINE;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.layer = "0";
        this.color = "BYLAYER";
    }
    
    // Constructor for Circle
    public CADEntity(double centerX, double centerY, double radius) {
        this.type = EntityType.CIRCLE;
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.layer = "0";
        this.color = "BYLAYER";
    }
    
    // Constructor for Rectangle
    public CADEntity(double x, double y, double width, double height) {
        this.type = EntityType.RECTANGLE;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.layer = "0";
        this.color = "BYLAYER";
    }
    
    // Getters
    public EntityType getType() { return type; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getStartX() { return startX; }
    public double getStartY() { return startY; }
    public double getEndX() { return endX; }
    public double getEndY() { return endY; }
    public double getCenterX() { return centerX; }
    public double getCenterY() { return centerY; }
    public double getRadius() { return radius; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public String getLayer() { return layer; }
    public String getColor() { return color; }
    
    // Setters
    public void setLayer(String layer) { this.layer = layer; }
    public void setColor(String color) { this.color = color; }
    
    @Override
    public String toString() {
        switch (type) {
            case LINE:
                return String.format("Line: (%.2f,%.2f) to (%.2f,%.2f)", startX, startY, endX, endY);
            case CIRCLE:
                return String.format("Circle: center(%.2f,%.2f) radius=%.2f", centerX, centerY, radius);
            case RECTANGLE:
                return String.format("Rectangle: (%.2f,%.2f) w=%.2f h=%.2f", x, y, width, height);
            default:
                return type.toString();
        }
    }
}