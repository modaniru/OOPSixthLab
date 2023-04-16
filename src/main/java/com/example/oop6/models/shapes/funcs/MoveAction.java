package com.example.oop6.models.shapes.funcs;

import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Position;

public class MoveAction implements ShapeAction {
    private double width;
    private double height;
    private double dx;
    private double dy;

    public MoveAction(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean shapeAction(Shape shape) {
        Position position = shape.getPosition().clone();
        shape.getPosition().changePosition(dx, dy);
        if(!(shape.entersByWidth(width) && shape.entersByHeight(height))){
            shape.setPosition(position);
            return false;
        }
        return true;
    }

    @Override
    public boolean groupAction(Shape shape) {
        shapeAction(shape);
        return true;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}
