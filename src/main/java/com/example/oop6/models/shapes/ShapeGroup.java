package com.example.oop6.models.shapes;

import com.example.oop6.models.shapes.funcs.ShapeAction;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Position;
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
        if (shapes.size() == 0) {
            width = shape.width;
            height = shape.height;
            leftShape = shape.position.getX() - shape.getMinWidth() / 2;
            rightShape = shape.position.getX() + shape.getMinWidth() / 2;
            upShape = shape.position.getY() - shape.getMinHeight() / 2;
            downShape = shape.position.getY() + shape.getMinHeight() / 2;
            position = shape.position.clone();
        }
        //Вычисление координат центра, при добавлении новой фигуры
        double maxX = Math.max(shape.position.getX() + shape.getWidth() / 2,
                position.getX() + getWidth() / 2);
        double maxY = Math.max(shape.position.getY() + shape.getHeight() / 2,
                position.getY() + getHeight() / 2);
        double minX = Math.min(shape.position.getX() - shape.getWidth() / 2,
                position.getX() - getWidth() / 2);
        double minY = Math.min(shape.position.getY() - shape.getHeight() / 2,
                position.getY() - getHeight() / 2);
        if (shape.position.getX() > rightShape)
            rightShape = shape.position.getX() + shape.getMinWidth() / 2;
        if (shape.position.getX() < leftShape)
            leftShape = shape.position.getX() - shape.getMinWidth() / 2;
        if (shape.position.getY() > downShape)
            downShape = shape.position.getY() + shape.getMinHeight() / 2;
        if (shape.position.getY() < upShape)
            upShape = shape.position.getY() - shape.getMinHeight() / 2;
        width = maxX - minX;
        height = maxY - minY;
        position = new Position(minX + width / 2, minY + height / 2);
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
        return new ShapeGroup();
    }
    //Если обернуть в visitor, то будут выполняться ненужные итерации
    @Override
    public boolean inShapeArea(double x, double y) {
        for (Shape shape : shapes) {
            boolean res = shape.inShapeArea(x, y);
            if (res) return true;
        }
        return false;
    }
    //Реализован через template method
    //todo
    @Override
    protected void drawShape(GraphicsContext graphicsContext) {
        for (Shape shape : shapes) {
            shape.drawShape(graphicsContext);
        }
    }

    @Override
    public void accept(ShapeAction action) {
        action.groupAction(this);
    }

    //переименовать
    @Override
    public boolean entersByWidth(double width) {
        if (!super.entersByWidth(width)) {
            return false;
        }
        for (Shape shape : shapes) {
            if(!shape.entersByWidth(width)) return false;
        }
        return true;
    }

    @Override
    public boolean entersByHeight(double height) {
        if (!super.entersByHeight(height)) {
            return false;
        }
        for (Shape shape : shapes) {
            if(!shape.entersByHeight(height)) return false;
        }
        return true;
    }

    @Override
    public boolean entersByHeight(double height, Position position) {
        if (!super.entersByHeight(height, position)) {
            return false;
        }
        for (Shape shape : shapes) {
            if(!shape.entersByHeight(height, this.position)) return false;
        }
        return true;
    }

    @Override
    public boolean entersByWidth(double width, Position position) {
        if (!super.entersByWidth(width, position)) {
            return false;
        }
        for (Shape shape : shapes) {
            if(!shape.entersByWidth(width, this.position)) return false;
        }
        return true;
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
        bufferedWriter.write("\tShapes: " + shapes.size() + "\n");
        for (Shape shape : shapes) {
            shape.getInstance().save(bufferedWriter);
        }
    }

    @Override
    public void setPosition(Position position) {
        double dx = position.getX() - this.position.getX();
        double dy = position.getY() - this.position.getY();
        super.setPosition(position);
        for (Shape shape : shapes) {
            Position p = shape.getPosition().clone();
            p.changePosition(dx, dy);
            shape.setPosition(p);
        }
    }
}
