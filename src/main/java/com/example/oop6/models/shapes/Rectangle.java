package com.example.oop6.models.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Shape {

    public Rectangle(double width, double height) {
        super(width, height);
    }

    public Rectangle() {
        super(0, 0);
    }

    @Override
    public Shape getExample() {
        return new Rectangle(0, 0);
    }

    @Override
    public boolean inShapeArea(double x, double y) {
        return (x >= (position.getX() - width / 2) && x <= (position.getX() + width / 2)) && (y >= (position.getY() - height / 2) && y <= (position.getY() + height / 2));
    }

    @Override
    public void drawShape(GraphicsContext graphicsContext) {
        graphicsContext.setFill(getFillColor());
        graphicsContext.fillRect(position.getX() - (width / 2), position.getY() - (height / 2), width, height);
    }
}
