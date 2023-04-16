package com.example.oop6.utils;

import javafx.geometry.Pos;

public class Position {
    private double x;
    private double y;
    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Position clone(){
        return new Position(x, y);
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

    public boolean inArea(double x1, double y1, double x2, double y2){
        double minX = Math.min(x1, x2);
        double maxX = Math.max(x1, x2);
        double minY = Math.min(y1, y2);
        double maxY = Math.max(y1, y2);
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    public Position changePosition(double dx, double dy){
        x += dx;
        y += dy;
        return this;
    }
}
