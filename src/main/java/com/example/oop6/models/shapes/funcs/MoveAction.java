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
        System.out.println(dx + " " + dy);
        shape.getInstance().getPosition().changePosition(dx, dy);
        if(!(shape.getInstance().entersByWidth(width) && shape.getInstance().entersByHeight(height))){
            shape.getInstance().getPosition().changePosition(-dx, -dy);
            return false;
        }
        return true;
    }

    @Override
    public boolean groupAction(Shape shape) {
        shapeAction(shape);
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
