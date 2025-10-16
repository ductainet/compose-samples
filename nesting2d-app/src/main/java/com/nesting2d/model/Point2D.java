package com.nesting2d.model;

public class Point2D {
    private double x;
    private double y;
    
    public Point2D() {
        this(0.0, 0.0);
    }
    
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return x;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public double distanceTo(Point2D other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point2D point2D = (Point2D) obj;
        return Double.compare(point2D.x, x) == 0 && Double.compare(point2D.y, y) == 0;
    }
    
    @Override
    public int hashCode() {
        return Double.hashCode(x) + Double.hashCode(y);
    }
}