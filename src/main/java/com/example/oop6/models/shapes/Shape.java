package com.example.oop6.models.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    private static int MIN_HEIGHT = 10;
    private static int MIN_WIDTH = 10;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean selection;
    private Color fillColor;
    private final Color selectionColor = Color.ORANGERED;

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

    //Вопрос
    public final void setSize(int width, int height) {
        if (width >= MIN_WIDTH)
            this.width = width;
        if (height >= MIN_HEIGHT) {
            this.height = height;
        }
    }

    public void increaseSize(int dx, int dy) {
        this.width += dx * 2;
        this.height += dy * 2;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public final void setFillColor(Color color) {
        fillColor = color;
    }

    //Проверяет, находится ли фигура в заданом пространстве
    public boolean isItIncluded(int width, int height) {
        return x > getCenterToX() && y > getCenterToY() && x < width - getCenterToX() && y < height - getCenterToY();
    }
    public boolean isItIncludedWidth(int width){
        return x > getCenterToX() && x < width - getCenterToX();
    }
    public boolean isItIncludedHeight(int height){
        return y > getCenterToY() && y < height - getCenterToY();
    }

    protected final Color getBorderColor() {
        if (selection) return selectionColor;
        return Color.rgb(0, 0, 0, 0);
    }

    protected Color getFillColor() {
        return fillColor;
    }
}
