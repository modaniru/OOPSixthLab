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

    @Override
    public boolean inShapeArea(int x, int y) {
        for (Shape shape : shapes) {
            normalizedShape(shape);
            boolean res = shape.inShapeArea(x, y);
            undoNormalizedShape(shape);
            if(res) return true;
        }
        return false;
    }

    @Override
    protected void drawShape(GraphicsContext graphicsContext) {
        if (selection) {
            graphicsContext.setLineWidth(1);
            graphicsContext.setStroke(Color.LIGHTBLUE);
            graphicsContext.strokeRect(x - getCenterToX(), y - getCenterToY(), width, height);
        }
        for (Shape shape : shapes) {
            //Установление
            normalizedShape(shape);
            shape.drawShape(graphicsContext);
            undoNormalizedShape(shape);
        }
    }

    //Двигаем саму группу, при этом не меняя относительные координаты объектов в контейнере
    @Override
    public void increasePositionWithLimit(int dx, int dy, int fieldWidth, int fieldHeight) {
        super.increasePositionWithLimit(dx, dy, fieldWidth, fieldHeight);
    }

    //todo в тз не сказано увеличивать группу. Делать или нет?
    @Override
    public void setSizeWithLimit(int width, int height, int fieldWidth, int fieldHeight) {
        return;
    }

    //todo в тз не сказано увеличивать группу. Делать или нет?
    @Override
    public void increaseSizeWithLimit(int dx, int dy, int fieldWidth, int fieldHeight) {
        return;
    }

    @Override
    public void changeSelection() {
        selection = !selection;
        for (Shape shape : shapes) {
            shape.changeSelection();
        }
    }

    @Override
    public void disableSelection() {
        selection = false;
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
    //Устанавливает фигуре координаты относительно Поля
    private void normalizedShape(Shape shape){
        shape.setPosition(shape.x + x, shape.y + y);
    }
    //Устанавливает фигуре координаты относительно группы
    private void undoNormalizedShape(Shape shape){
        shape.setPosition(shape.x - x, shape.y - y);
    }
}
