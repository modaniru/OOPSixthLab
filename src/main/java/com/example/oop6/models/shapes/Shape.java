package com.example.oop6.models.shapes;

import com.example.oop6.models.shapes.funcs.ShapeAction;
import com.example.oop6.utils.Container;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    public static int MIN_HEIGHT = 10;
    public static int MIN_WIDTH = 10;
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
        fillColor = Color.rgb(0,0,0,0);
    }



    public abstract Shape clone();

    public abstract boolean inShapeArea(int x, int y);

    protected abstract void drawShape(GraphicsContext graphicsContext);
    //todo шаблонный метод рисования
    public void draw(Canvas canvas){
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();
        if(entersByWidth(width) && entersByHeight(height))
            drawShape(canvas.getGraphicsContext2D());
    }

    //return true if success
    //todo можно ли этого избежать
    public Container<Shape> getShapes() {
        return new Container<>();
    }


    public void changeSelection() {
        selection = !selection;
    }

    public void disableSelection() {
        selection = false;
    }

    public boolean isSelection() {
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
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    //todo NEW
    public void setSizeWithLimit(int width, int height, int fieldWidth, int fieldHeight) {
        if (!(x > width / 2 && x < fieldWidth - width / 2)) width = this.width;
        if (!(y > height / 2 && y < fieldHeight - height / 2)) height = this.height;
        setSize(width, height);
    }
    
    public void setFillColor(Color color) {
        fillColor = color;
    }

    //Проверяет, находится ли фигура в заданом пространстве
    public boolean entersByWidth(int width) {
        if(this.width < MIN_WIDTH) return false;
        return x > getCenterToX() && x < width - getCenterToX();
    }

    public boolean entersByHeight(int height) {
        if(this.height < MIN_HEIGHT) return false;
        return y > getCenterToY() && y < height - getCenterToY();
    }
    protected final Color getBorderColor() {
        if (selection) return selectionColor;
        return Color.rgb(0, 0, 0, 0);
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
}
