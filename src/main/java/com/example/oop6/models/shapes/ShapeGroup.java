package com.example.oop6.models.shapes;

import com.example.oop6.models.shapes.funcs.ShapeAction;
import com.example.oop6.utils.Container;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShapeGroup extends Shape {
    private final Container<Shape> shapes;
    private int leftShape = 0;
    private int rightShape = 0;
    private int upShape = 0;
    private int downShape = 0;

    public ShapeGroup() {
        super(0, 0);
        this.shapes = new Container<Shape>();
    }

    public void addShape(Shape shape) {
        if (shapes.getSize() == 0) {
            width = shape.width;
            height = shape.height;
            leftShape = shape.x - shape.getMinWidth() / 2;
            rightShape = shape.x + shape.getMinWidth() / 2;
            upShape = shape.y - shape.getMinHeight() / 2;
            downShape = shape.y + shape.getMinHeight() / 2;
            x = shape.x;
            y = shape.y;
        }
        //Вычисление координат центра, при добавлении новой фигуры
        int maxX = Math.max(shape.x + shape.getCenterToX(), x + getCenterToX());
        int maxY = Math.max(shape.y + shape.getCenterToY(), y + getCenterToY());
        int minX = Math.min(shape.x - shape.getCenterToX(), x - getCenterToX());
        int minY = Math.min(shape.y - shape.getCenterToY(), y - getCenterToY());
        if (shape.x > rightShape) rightShape = shape.x + shape.getMinWidth() / 2;
        if (shape.x < leftShape) leftShape = shape.x - shape.getMinWidth() / 2;
        if (shape.y > downShape) downShape = shape.y + shape.getMinHeight() / 2;
        if (shape.y < upShape) upShape = shape.y - shape.getMinHeight() / 2;
        width = maxX - minX;
        height = maxY - minY;
        int oldX = x;
        int oldY = y;
        x = minX + width / 2;
        y = minY + height / 2;
        //нормализация координат относительно центра группы
        for (Shape s : shapes) {
            s.setPosition(s.getX() - (x - oldX), s.getY() - (y - oldY));
        }
        //нормализация и добавление фигуры в контейнер
        shape.setPosition(shape.getX() - x, shape.getY() - y);
        shapes.add(shape);
    }


    @Override
    public Container<Shape> getShapes() {
        return shapes;
    }

    @Override
    public Shape clone() {
        ShapeGroup shapeGroup = new ShapeGroup();
        for (Shape shape : shapes) {
            shapeGroup.addShape(shape.clone());
        }
        return shapeGroup;
    }

    @Override
    public boolean inShapeArea(int x, int y) {
        for (Shape shape : shapes) {
            shape.setPosition(this.x + shape.getX(), this.y + shape.getY());
            boolean res = shape.inShapeArea(x, y);
            shape.setPosition(shape.getX() - this.x, shape.getY() - this.y);
            if (res) return true;
        }
        return false;
    }

    @Override
    protected void drawShape(GraphicsContext graphicsContext) {
        for (Shape shape : shapes) {
            //Установление
            shape.setPosition(x + shape.getX(), y + shape.getY());
            shape.drawShape(graphicsContext);
            shape.setPosition(shape.getX() - x, shape.getY() - y);
        }
    }

    @Override
    public void accept(ShapeAction action) {
        action.groupAction(this);
    }

    //переименовать
    @Override
    public boolean entersByWidth(int width) {
        boolean res = super.entersByWidth(width);
        return res && this.width >= rightShape - leftShape;
    }

    @Override
    public boolean entersByHeight(int height) {
        boolean res = super.entersByHeight(height);
        return res && this.height >= downShape - upShape;
    }

    @Override
    public int getMinWidth() {
        return rightShape - leftShape;
    }

    @Override
    public int getMinHeight() {
        return downShape - upShape;
    }
}
