package com.example.oop6.models.shapes.funcs;

import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Container;

public class ResizeDeltaAction implements ShapeAction {
    private int dx;
    private int dy;
    private int width;
    private int height;

    public ResizeDeltaAction(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean shapeAction(Shape shape) {
        int oldWidth = shape.getWidth();
        int oldHeight = shape.getHeight();
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
        Container<Shape> shapes = shape.getShapes();
        for (Shape s : shapes) {
            s.setSize(s.getWidth() + dx, s.getHeight() + dy);
            s.setPosition(s.getX() + shape.getX(), s.getY() + shape.getY());
            if (!(s.entersByWidth(width) && s.entersByHeight(height))) {
                s.setSize(s.getWidth() - dx, s.getHeight() - dy);
                s.setPosition(s.getX() - shape.getX(), s.getY() - shape.getY());
                return false;
            }
            s.setPosition(s.getX() - shape.getX(), s.getY() - shape.getY());
            s.setSize(s.getWidth() - dx, s.getHeight() - dy);
        }
        if (!shapeAction(shape)) return false;
        for (Shape s : shapes) {
            s.setPosition(shape.getX() + s.getX(), shape.getY() + s.getY());
            s.accept(this);
            s.setPosition(s.getX() - shape.getX(), s.getY() - shape.getY());
        }
        return true;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
}
