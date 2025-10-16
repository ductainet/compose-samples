package com.nesting2d.model;

import java.util.List;

public class CadEntity {
    private String type;
    private String layer;
    private String color;
    private double lineWidth;
    private List<Point2D> points;
    private String text;
    private double radius;
    private double startAngle;
    private double endAngle;
    private boolean closed;
    
    public CadEntity() {
    }
    
    public CadEntity(String type) {
        this.type = type;
    }
    
    // Getters and Setters
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getLayer() {
        return layer;
    }
    
    public void setLayer(String layer) {
        this.layer = layer;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public double getLineWidth() {
        return lineWidth;
    }
    
    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }
    
    public List<Point2D> getPoints() {
        return points;
    }
    
    public void setPoints(List<Point2D> points) {
        this.points = points;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public double getRadius() {
        return radius;
    }
    
    public void setRadius(double radius) {
        this.radius = radius;
    }
    
    public double getStartAngle() {
        return startAngle;
    }
    
    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }
    
    public double getEndAngle() {
        return endAngle;
    }
    
    public void setEndAngle(double endAngle) {
        this.endAngle = endAngle;
    }
    
    public boolean isClosed() {
        return closed;
    }
    
    public void setClosed(boolean closed) {
        this.closed = closed;
    }
    
    @Override
    public String toString() {
        return String.format("%s [Layer: %s, Color: %s]", type, layer, color);
    }
}