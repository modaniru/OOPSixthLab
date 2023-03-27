package com.example.oop6.models.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Square implements Shape {
    private int x;
    private int y;
    private Color color;
    private int side;
    private boolean selection;

    public Square(int side) {
        this.side = side;
        color = Color.GRAY;
    }

    @Override
    public Shape clone() {
        return new Square(side);
    }

    @Override
    public boolean inShapeArea(int x, int y) {
        return (x >= (this.x - side / 2) && x <= (this.x + side / 2)) && (y >= (this.y - side / 2) && y <= (this.y + side / 2));
    }

    @Override
    public void changeSelection() {
        selection = !selection;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setLineWidth(4);
        graphicsContext.setStroke(getSelection());
        graphicsContext.setFill(color);
        graphicsContext.strokeRect(x - (side / 2), y - (side / 2), side, side);
        graphicsContext.fillRect(x - (side / 2), y - (side / 2), side, side);
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

    private Color getSelection() {
        if (selection) return Color.LIME;
        return Color.rgb(0, 0, 0, 0);
    }

    @Override
    public int getRadius() {
        return side / 2;
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
