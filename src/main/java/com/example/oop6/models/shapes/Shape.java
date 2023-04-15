package com.example.oop6.models.shapes;

import com.example.oop6.models.shapes.funcs.ShapeAction;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Position;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    public static final double MIN_HEIGHT = 10;
    public static final double MIN_WIDTH = 10;
    protected Position position;
    protected double width;
    protected double height;
    private Color fillColor;

    public Shape(double width, double height) {
        this.width = width;
        this.height = height;
        fillColor = Color.rgb(0, 0, 0, 0);
        position = new Position(0,0);
    }


    public Shape clone(){
        Shape shape = getExample();
        shape.setFillColor(fillColor);
        shape.setPosition(position.clone());
        shape.setSize(width, height);
        return shape;
    }

    public abstract Shape getExample();


    public abstract boolean inShapeArea(double x, double y);

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

    public final void setPosition(Position position) {
        this.position = position.clone();
    }

    public final double getCenterToX() {
        return width / 2;
    }

    public final double getCenterToY() {
        return height / 2;
    }

    public Position getPosition() {
        return position;
    }

    //Вопрос
    public void setSize(double width, double height) {
        if(width < MIN_WIDTH) width = MIN_WIDTH;
        if(height < MIN_HEIGHT) height = MIN_HEIGHT;
        this.width = width;
        this.height = height;
    }

    public void setSizeWithLimit(double width, double height, double fieldWidth, double fieldHeight) {
        if (!(position.getX() > width / 2 && position.getX() < fieldWidth - width / 2)) width = this.width;
        if (!(position.getY() > height / 2 && position.getY() < fieldHeight - height / 2)) height = this.height;
    }

    public void setFillColor(Color color) {
        fillColor = color;
    }

    //Проверяет, находится ли фигура в заданом пространстве
    public boolean entersByWidth(double width) {
        if (this.width < MIN_WIDTH) return false;
        return position.getX() > getCenterToX() && position.getX() < width - getCenterToX();
    }

    public boolean entersByHeight(double height) {
        if (this.height < MIN_HEIGHT) return false;
        return position.getY() > getCenterToY() && position.getY() < height - getCenterToY();
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

    public double getMinHeight() {
        return MIN_HEIGHT;
    }

    public double getMinWidth() {
        return MIN_WIDTH;
    }

    public Shape getInstance() {
        return this;
    }
}
