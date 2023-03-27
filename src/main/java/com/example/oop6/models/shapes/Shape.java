package com.example.oop6.models.shapes;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public interface Shape {
    Shape clone();
    boolean inShapeArea(int x, int y);
    void changeSelection();
    void draw(GraphicsContext graphicsContext);
    void disableSelection();
    boolean isSelection();
    void setPosition(int x, int y);
    int getRadius();
    int getX();
    int getY();
}
