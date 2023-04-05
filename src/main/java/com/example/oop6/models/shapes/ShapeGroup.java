package com.example.oop6.models.shapes;

import com.example.oop6.utils.Container;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShapeGroup extends Shape {
    private final Container<Shape> shapes;

    public ShapeGroup() {
        super(0, 0);
        this.shapes = new Container<Shape>();
    }

    public void addShape(Shape shape) {
        if (shapes.getSize() == 0) {
            width = shape.width;
            height = shape.height;
            x = shape.x;
            y = shape.y;
        }
        int maxX = Math.max(shape.x + shape.getCenterToX(), x + getCenterToX());
        int maxY = Math.max(shape.y + shape.getCenterToY(), y + getCenterToY());
        int minX = Math.min(shape.x - shape.getCenterToX(), x - getCenterToX());
        int minY = Math.min(shape.y - shape.getCenterToY(), y - getCenterToY());
        width = maxX - minX;
        height = maxY - minY;
        x = minX + width / 2;
        y = minY + height / 2;
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
            if (shape.inShapeArea(x, y)) return true;
        }
        return false;
    }

    @Override
    protected void drawShape(GraphicsContext graphicsContext) {
        graphicsContext.setLineWidth(2);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeRect(x - getCenterToX(), y - getCenterToY(), width, height);
        for (Shape shape : shapes) {
            shape.drawShape(graphicsContext);
        }
    }

    @Override
    public void increasePositionWithLimit(int dx, int dy, int fieldWidth, int fieldHeight) {
        int oldX = x;
        int oldY = y;
        super.increasePositionWithLimit(dx, dy, fieldWidth, fieldHeight);
        if(x == oldX && y == oldY) return;
        for (Shape shape : shapes) {
            shape.increasePositionWithLimit(dx, dy, fieldWidth, fieldHeight);
        }
    }

    @Override
    public void setSizeWithLimit(int width, int height, int fieldWidth, int fieldHeight) {
        //todo
    }

    //todo проверять не вышли ли фигуры за пределлы группы при уменьшении
    //todo мб Делать координаты каждого шейпа относительными группы, при отрисовке делаль их координаты относительно поля
    @Override
    public void increaseSizeWithLimit(int dx, int dy, int fieldWidth, int fieldHeight) {

    }

    @Override
    public void changeSelection() {
        for (Shape shape : shapes) {
            shape.changeSelection();
        }
    }

    @Override
    public void disableSelection() {
        for (Shape shape : shapes) {
            shape.disableSelection();
        }
    }

    @Override
    public boolean isSelection() {
        boolean res = true;
        for (Shape shape : shapes) {
            res = res & shape.isSelection();
        }
        return res;
    }
}
