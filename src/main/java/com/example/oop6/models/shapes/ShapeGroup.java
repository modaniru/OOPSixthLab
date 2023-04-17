package com.example.oop6.models.shapes;

import com.example.oop6.models.shapes.funcs.ShapeAction;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.ShapeAbstractFactory;
import javafx.scene.canvas.GraphicsContext;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ShapeGroup extends Shape {
    private final Container<Shape> shapes;
    private double leftShape = 0;
    private double rightShape = 0;
    private double upShape = 0;
    private double downShape = 0;

    public ShapeGroup() {
        super(0, 0);
        this.shapes = new Container<Shape>();
    }

    public void addShape(Shape shape) {
        if (shapes.getSize() == 0) {
            width = shape.width;
            height = shape.height;
            leftShape = shape.position.getX() - shape.getMinWidth() / 2;
            rightShape = shape.position.getX() + shape.getMinWidth() / 2;
            upShape = shape.position.getY() - shape.getMinHeight() / 2;
            downShape = shape.position.getY() + shape.getMinHeight() / 2;
            position = shape.position.clone();
        }
        //Вычисление координат центра, при добавлении новой фигуры
        double maxX = Math.max(shape.position.getX() + shape.getCenterToX(), position.getX() + getCenterToX());
        double maxY = Math.max(shape.position.getY() + shape.getCenterToY(), position.getY() + getCenterToY());
        double minX = Math.min(shape.position.getX() - shape.getCenterToX(), position.getX() - getCenterToX());
        double minY = Math.min(shape.position.getY() - shape.getCenterToY(), position.getY() - getCenterToY());
        if (shape.position.getX() > rightShape) rightShape = shape.position.getX() + shape.getMinWidth() / 2;
        if (shape.position.getX() < leftShape) leftShape = shape.position.getX() - shape.getMinWidth() / 2;
        if (shape.position.getY() > downShape) downShape = shape.position.getY() + shape.getMinHeight() / 2;
        if (shape.position.getY() < upShape) upShape = shape.position.getY() - shape.getMinHeight() / 2;
        width = maxX - minX;
        height = maxY - minY;
        double oldX = position.getX();
        double oldY = position.getY();
        position.setX((int) (minX + width / 2));
        position.setY((int) (minY + height / 2));
        //нормализация координат относительно центра группы
        for (Shape s : shapes) {
            s.getPosition().changePosition(- (position.getX() - oldX), - (position.getY() - oldY));
        }
        //нормализация и добавление фигуры в контейнер
        shape.getPosition().changePosition(-position.getX(), -position.getY());
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
    public Shape getExample() {
        ShapeGroup shapeGroup = new ShapeGroup();
        for (Shape shape : shapes) {
            shapeGroup.addShape(shape.clone());
        }
        return shapeGroup;
    }

    @Override
    public boolean inShapeArea(double x, double y) {
        for (Shape shape : shapes) {
            shape.getPosition().changePosition(position.getX(), position.getY());
            boolean res = shape.inShapeArea(x, y);
            shape.getPosition().changePosition(-position.getX(), -position.getY());
            if (res) return true;
        }
        return false;
    }

    @Override
    protected void drawShape(GraphicsContext graphicsContext) {
        for (Shape shape : shapes) {
            //Установление
            shape.getPosition().changePosition(position.getX(), position.getY());
            shape.drawShape(graphicsContext);
            shape.getPosition().changePosition(-position.getX(), -position.getY());
        }
    }

    @Override
    public void accept(ShapeAction action) {
        action.groupAction(this);
    }

    //переименовать
    @Override
    public boolean entersByWidth(double width) {
        boolean res = super.entersByWidth(width);
        return res && this.width >= rightShape - leftShape;
    }

    @Override
    public boolean entersByHeight(double height) {
        boolean res = super.entersByHeight(height);
        return res && this.height >= downShape - upShape;
    }

    @Override
    public double getMinWidth() {
        return rightShape - leftShape;
    }

    @Override
    public double getMinHeight() {
        return downShape - upShape;
    }

    @Override
    public void load(BufferedReader bufferedReader, ShapeAbstractFactory factory) throws IOException {
        super.load(bufferedReader, factory);
        String line = check("\tShapes: ", bufferedReader.readLine());
        int count = Integer.parseInt(line.split(" ")[1]);
        for (int i = 0; i < count; i++) {
            Shape shape = factory.createShape(bufferedReader);
            shapes.add(shape);
        }
    }

    @Override
    public void save(BufferedWriter bufferedWriter) throws IOException {
        super.save(bufferedWriter);
        bufferedWriter.write("\tShapes: " + shapes.getSize() + "\n");
        for (Shape shape : shapes) {
            shape.getInstance().save(bufferedWriter);
        }
    }
}
