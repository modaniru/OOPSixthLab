package com.example.oop6.models.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Triangle extends Shape {
    public Triangle(int width, int height) {
        super(width, height);
    }

    public Triangle() {
        super(0, 0);
    }

    @Override
    public Shape getExample() {
        return new Triangle(0, 0);
    }

    @Override
    public boolean inShapeArea(double x, double y) {
        double first = -(width) * (position.getY() + height / 2 - y);
        double second = (position.getX() + width / 2 - x) * (-height) - (-width / 2) * (position.getY() + height / 2 - y);
        double third = (position.getX() - x) * (height) - (-width / 2) * (position.getY() - height / 2 - y);
        return (first <= 0 && second <= 0 && third <= 0) || (first >= 0 && second >= 0 && third >= 0);
    }

    @Override
    public void drawShape(GraphicsContext graphicsContext) {
        graphicsContext.setFill(getFillColor());
        double[] xCoords = new double[]{position.getX() - width / 2, position.getX() + width / 2, position.getX()};
        double[] yCoords = new double[]{position.getY() + height / 2, position.getY() + height / 2, position.getY() - height / 2};
        graphicsContext.fillPolygon(xCoords, yCoords, 3);
    }
}
