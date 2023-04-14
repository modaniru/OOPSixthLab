package com.example.oop6.models.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Shape {

    public Rectangle(int width, int height) {
        super(width, height);
    }

    @Override
    public Shape getExample() {
        return new Rectangle(0, 0);
    }

    @Override
    public boolean inShapeArea(int x, int y) {
        return (x >= (this.x - width / 2) && x <= (this.x + width / 2)) && (y >= (this.y - height / 2) && y <= (this.y + height / 2));
    }

    @Override
    public void drawShape(GraphicsContext graphicsContext) {
        graphicsContext.setFill(getFillColor());
        graphicsContext.fillRect(x - (width / 2), y - (height / 2), width, height);
    }
}
