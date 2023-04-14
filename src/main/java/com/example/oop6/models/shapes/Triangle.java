package com.example.oop6.models.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Triangle extends Shape {
    public Triangle(int width, int height) {
        super(width, height);
    }

    @Override
    public Shape clone() {
        return new Triangle(width, height);
    }

    @Override
    public boolean inShapeArea(int x, int y) {
        double first = -(width) * (this.y + height / 2 - y);
        double second = (this.x + width / 2 - x) * (-height) - (-width / 2) * (this.y + height / 2 - y);
        double third = (this.x - x) * (height) - (-width / 2) * (this.y - height / 2 - y);
        return (first <= 0 && second <= 0 && third <= 0) || (first >= 0 && second >= 0 && third >= 0);
    }

    @Override
    public void drawShape(GraphicsContext graphicsContext) {
        graphicsContext.setFill(getFillColor());
        double[] xCoords = new double[]{x - width / 2, x + width / 2, x};
        double[] yCoords = new double[]{y + height / 2, y + height / 2, y - height / 2};
        graphicsContext.fillPolygon(xCoords, yCoords, 3);
    }
}
