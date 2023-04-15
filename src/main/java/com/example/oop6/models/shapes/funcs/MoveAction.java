package com.example.oop6.models.shapes.funcs;

import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Position;

public class MoveAction implements ShapeAction {
    private int width;
    private int height;
    private int dx;
    private int dy;

    public MoveAction(int width, int height) {
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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
}
