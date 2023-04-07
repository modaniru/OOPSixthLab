package com.example.oop6.models.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Shape {

    public Rectangle(int width, int height) {
        super(width, height);
    }

    @Override
    public Shape clone() {
        return new Rectangle(width, height);
    }

    @Override
    public boolean inShapeArea(int x, int y) {
        return (x >= (this.x - width / 2) && x <= (this.x + width / 2)) && (y >= (this.y - height / 2) && y <= (this.y + height / 2));
    }

    @Override
    public void drawShape(GraphicsContext graphicsContext) {
        graphicsContext.setLineWidth(2);
        graphicsContext.setStroke(getBorderColor());
        graphicsContext.setFill(getFillColor());
        graphicsContext.strokeRect(x - (width / 2), y - (height / 2), width, height);
        graphicsContext.fillRect(x - (width / 2), y - (height / 2), width, height);
    }
}
