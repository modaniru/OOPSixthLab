package com.example.oop6.models.shapes;

import com.example.oop6.models.shapes.funcs.ShapeAction;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Position;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    public static final int MIN_HEIGHT = 10;
    public static final int MIN_WIDTH = 10;
    protected Position position;
    protected int width;
    protected int height;
    private Color fillColor;

    public Shape(int width, int height) {
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

    public final void setPosition(Position position) {
        this.position = position.clone();
    }

    public final int getCenterToX() {
        return width / 2;
    }

    public final int getCenterToY() {
        return height / 2;
    }

    public Position getPosition() {
        return position;
    }

    //Вопрос
    public void setSize(int width, int height) {
        if(width < MIN_WIDTH) width = MIN_WIDTH;
        if(height < MIN_HEIGHT) height = MIN_HEIGHT;
        this.width = width;
        this.height = height;
    }

    //todo NEW
    public void setSizeWithLimit(int width, int height, int fieldWidth, int fieldHeight) {
        if (!(position.getX() > width / 2 && position.getX() < fieldWidth - width / 2)) width = this.width;
        if (!(position.getY() > height / 2 && position.getY() < fieldHeight - height / 2)) height = this.height;
        setSize(width, height);
    }

    public void setFillColor(Color color) {
        fillColor = color;
    }

    //Проверяет, находится ли фигура в заданом пространстве
    public boolean entersByWidth(int width) {
        if (this.width < MIN_WIDTH) return false;
        return position.getX() > getCenterToX() && position.getX() < width - getCenterToX();
    }

    public boolean entersByHeight(int height) {
        if (this.height < MIN_HEIGHT) return false;
        return position.getY() > getCenterToY() && position.getY() < height - getCenterToY();
    }

    protected Color getFillColor() {
        return fillColor;
    }

    public void accept(ShapeAction action) {
        action.shapeAction(this);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
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
}
