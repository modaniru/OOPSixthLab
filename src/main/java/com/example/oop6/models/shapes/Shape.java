package com.example.oop6.models.shapes;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean selection;
    private Color fillColor = Color.AQUA;
    private Color selectionColor = Color.ORANGERED;

    public Shape(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract Shape clone();

    public abstract boolean inShapeArea(int x, int y);

    public abstract void draw(GraphicsContext graphicsContext);


    public final void changeSelection() {
        selection = !selection;
    }

    public final void disableSelection() {
        selection = false;
    }

    public final boolean isSelection() {
        return selection;
    }

    public final void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public final int getCenterToX() {
        return width / 2;
    }

    public final int getCenterToY() {
        return height / 2;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public final void setWidth(int newWidth){
        width = newWidth;
    }
    public final void setHeight(int newHeight){
        height = newHeight;
    }

    public final void setFillColor(Color color){
        fillColor = color;
    }

    protected final Color getBorderColor() {
        if (selection) return selectionColor;
        return Color.rgb(0, 0, 0, 0);
    }
    protected Color getFillColor(){
        return fillColor;
    }
}
