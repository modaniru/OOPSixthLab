package com.example.oop6.models.shapes;

import com.example.oop6.utils.Container;
import javafx.scene.canvas.GraphicsContext;

public class ShapeGroup extends Shape{
    private final Container<Shape> shapes;

    public ShapeGroup() {
        super(0, 0);
        this.shapes = new Container<Shape>();
    }

    @Override
    public boolean addShape(Shape shape) {
        shapes.add(shape);
        return true;
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
            if(shape.inShapeArea(x, y)) return true;
        }
        return false;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        for (Shape shape : shapes) {
            shape.draw(graphicsContext);
        }
    }
}
