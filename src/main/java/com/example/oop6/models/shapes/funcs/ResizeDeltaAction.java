package com.example.oop6.models.shapes.funcs;

import com.example.oop6.models.shapes.Shape;

public class ResizeDeltaAction implements ShapeAction {
    private double dx;
    private double dy;
    private double width;
    private double height;

    public ResizeDeltaAction(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean shapeAction(Shape shape) {
        double oldWidth = shape.getWidth();
        double oldHeight = shape.getHeight();
        shape.setSize(oldWidth + dx, oldHeight + dy);
        if (!(shape.entersByWidth(width) && shape.entersByHeight(height))) {
            shape.setSize(oldWidth, oldHeight);
            return false;
        }
        return true;
    }

    //сначала проверять смогут ли элементы группы уменьшиться, а потом уменьшать саму группу
    @Override
    public boolean groupAction(Shape shape) {
        for (Shape s : shape.getShapes()) {
            s = s.clone();
            s.setSize(s.getWidth() + dx, s.getHeight() + dy);
            boolean res = s.entersByHeight(height) && s.entersByWidth(width);
            if (!res) return false;
        }
        shapeAction(shape);
        for (Shape s : shape.getShapes()) {
            s.accept(this);
        }
        return true;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}
