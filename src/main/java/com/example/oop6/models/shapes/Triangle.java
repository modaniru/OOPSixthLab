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
        //todo адаптировать для треугольника
        return (x >= (this.x - width / 2) && x <= (this.x + width / 2)) && (y >= (this.y - height / 2) && y <= (this.y + height / 2));
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setLineWidth(4);
        graphicsContext.setFill(getFillColor());
        graphicsContext.setStroke(getBorderColor());
        double[] xCoords = new double[]{x - width / 2, x + width / 2, x};
        double[] yCoords = new double[]{y + height / 2, y + height / 2, y - height / 2};
        graphicsContext.fillPolygon(xCoords, yCoords, 3);
        graphicsContext.strokePolygon(xCoords, yCoords, 3);
    }
}
