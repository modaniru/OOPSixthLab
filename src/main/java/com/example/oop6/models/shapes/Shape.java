package com.example.oop6.models.shapes;

import com.example.oop6.models.shapes.funcs.ShapeAction;
import com.example.oop6.utils.Container;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    public static final int MIN_HEIGHT = 10;
    public static final int MIN_WIDTH = 10;
    protected int x;
    protected int y;
    protected double width;
    protected double height;
    private Color fillColor;

    public Shape(double width, double height) {
        this.width = width;
        this.height = height;
        fillColor = Color.rgb(0, 0, 0, 0);
    }


    public Shape clone(){
        Shape shape = getExample();
        shape.setFillColor(fillColor);
        shape.setPosition(x, y);
        shape.setSize(width, height);
        return shape;
    }

    public abstract Shape getExample();


    public abstract boolean inShapeArea(int x, int y);

    protected abstract void drawShape(GraphicsContext graphicsContext);

    //todo шаблонный метод рисования
    public void draw(Canvas canvas) {
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();
        if (entersByWidth(width) && entersByHeight(height))
            drawShape(canvas.getGraphicsContext2D());
    }

    //return true if success
    //todo можно ли этого избежать
    public Container<Shape> getShapes() {
        return new Container<>();
    }

    public final void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public final double getCenterToX() {
        return width / 2;
    }

    public final double getCenterToY() {
        return height / 2;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    //Вопрос
    public void setSize(double width, double height) {
        if(width < MIN_WIDTH) width = MIN_WIDTH;
        if(height < MIN_HEIGHT) height = MIN_HEIGHT;
        this.width = width;
        this.height = height;
    }

    //todo NEW
    public void setSizeWithLimit(double width, double height, double fieldWidth, double fieldHeight) {
        if (!(x > width / 2 && x < fieldWidth - width / 2)) width = this.width;
        if (!(y > height / 2 && y < fieldHeight - height / 2)) height = this.height;
        setSize(width, height);
    }

    public void setFillColor(Color color) {
        fillColor = color;
    }

    //Проверяет, находится ли фигура в заданом пространстве
    public boolean entersByWidth(int width) {
        if (this.width < MIN_WIDTH) return false;
        return x > getCenterToX() && x < width - getCenterToX();
    }

    public boolean entersByHeight(int height) {
        if (this.height < MIN_HEIGHT) return false;
        return y > getCenterToY() && y < height - getCenterToY();
    }

    protected Color getFillColor() {
        return fillColor;
    }

    public void accept(ShapeAction action) {
        action.shapeAction(this);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getMinHeight() {
        return MIN_HEIGHT;
    }

    public int getMinWidth() {
        return MIN_WIDTH;
    }

    public Shape getInstance() {
        return this;
    }
    public boolean includeCentre(int x1, int y1, int x2, int y2){
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }
}
