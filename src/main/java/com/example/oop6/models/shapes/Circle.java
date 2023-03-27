package com.example.oop6.models.shapes;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle implements Shape {
    private Color color;
    private int radius;
    private boolean selection;
    private int x;
    private int y;

    public Circle(int radius) {
        this.radius = radius;
        color = Color.BLACK;
    }

    @Override
    public Shape clone() {
        return new Circle(radius);
    }

    @Override
    public boolean inShapeArea(int x, int y) {
        double distance = Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
        return distance < radius;
    }

    @Override
    public void changeSelection() {
        selection = !selection;
    }

    private Color getBorderColor() {
        if (selection) return Color.YELLOW;
        return Color.rgb(0, 0, 0, 0);
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setLineWidth(4);
        graphicsContext.setStroke(getBorderColor());
        graphicsContext.setFill(color);
        graphicsContext.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
        graphicsContext.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    @Override
    public void disableSelection() {
        selection = false;
    }

    @Override
    public boolean isSelection() {
        return selection;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                '}';
    }

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
