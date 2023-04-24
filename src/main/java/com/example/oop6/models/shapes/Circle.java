package com.example.oop6.models.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Circle extends Shape {
    public Circle(double width, double height) {
        super(width, height);
    }

    public Circle() {
        super(0, 0);
    }

    @Override
    public Shape getExample() {
        return new Circle(0, 0);
    }

    @Override
    public boolean inShapeArea(double x, double y) {
        double normalizedX = position.getX() - x;
        double normalizedY = position.getY() - y;
        return (Math.pow(normalizedX, 2) / Math.pow(width / 2, 2) + Math.pow(normalizedY, 2) / Math.pow(height / 2, 2)) <= 1;
    }

    @Override
    public void drawShape(GraphicsContext graphicsContext) {
        graphicsContext.setFill(getFillColor());
        graphicsContext.fillOval(position.getX() - (width / 2), position.getY() - (height / 2), width, height);
    }
}
