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
        //Вычисление координат центра, при добавлении новой фигуры
        int maxX = Math.max(shape.x + shape.getCenterToX(), x + getCenterToX());
        int maxY = Math.max(shape.y + shape.getCenterToY(), y + getCenterToY());
        int minX = Math.min(shape.x - shape.getCenterToX(), x - getCenterToX());
        int minY = Math.min(shape.y - shape.getCenterToY(), y - getCenterToY());
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

    //todo dry
    @Override
    public boolean inShapeArea(int x, int y) {
        for (Shape shape : shapes) {
            int oldX = shape.getX();
            int oldY = shape.getY();
            shape.setPosition(this.x + oldX, this.y + oldY);
            if (shape.inShapeArea(x, y)) {
                shape.setPosition(oldX, oldY);
                return true;
            }
            shape.setPosition(oldX, oldY);
        }
        return false;
    }

    //todo dry
    @Override
    protected void drawShape(GraphicsContext graphicsContext) {
        graphicsContext.setLineWidth(2);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeRect(x - getCenterToX(), y - getCenterToY(), width, height);
        for (Shape shape : shapes) {
            //Установление
            int oldX = shape.getX();
            int oldY = shape.getY();
            shape.setPosition(x + oldX, y + oldY);
            shape.drawShape(graphicsContext);
            shape.setPosition(oldX, oldY);
        }
    }

    //Двигаем саму группу, при этом не меняя относительные координаты объектов в контейнере
    @Override
    public void increasePositionWithLimit(int dx, int dy, int fieldWidth, int fieldHeight) {
        super.increasePositionWithLimit(dx, dy, fieldWidth, fieldHeight);
    }
    //todo Группа может расширять свой размер, но при этом не увеличивает размер фигур
    @Override
    //todo to double???
    public void setSizeWithLimit(int width, int height, int fieldWidth, int fieldHeight) {

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

    @Override
    public void setFillColor(Color color) {
        for (Shape shape : shapes) {
            shape.setFillColor(color);
        }
    }
}
