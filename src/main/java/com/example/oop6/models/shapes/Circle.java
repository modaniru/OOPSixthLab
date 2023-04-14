package com.example.oop6.models.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Circle extends Shape {
    public Circle(int width, int height) {
        super(width, height);
    }

    @Override
    public Shape getExample() {
        return new Circle(0, 0);
    }

    @Override
    public boolean inShapeArea(int x, int y) {
        int normalizedX = this.x - x;
        int normalizedY = this.y - y;
        return (Math.pow(normalizedX, 2) / Math.pow(width / 2, 2) + Math.pow(normalizedY, 2) / Math.pow(height / 2, 2)) <= 1;
    }

    @Override
    public void drawShape(GraphicsContext graphicsContext) {
        graphicsContext.setFill(getFillColor());
        graphicsContext.fillOval(x - (width / 2), y - (height / 2), width, height);
    }
}
