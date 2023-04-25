package com.example.oop6.models.shapes.funcs;

import com.example.oop6.models.shapes.Shape;

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
        System.out.println(dx + " " + dy);
        shape.getPosition().changePosition(dx, dy);
        if(!(shape.entersByWidth(width) && shape.entersByHeight(height))){
            shape.getPosition().changePosition(-dx, -dy);
            return false;
        }
        return true;
    }

    @Override
    public boolean groupAction(Shape shape) {
        if(!shapeAction(shape)) return false;
        for (Shape s : shape.getShapes()) {
            s.accept(this);
        }
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
