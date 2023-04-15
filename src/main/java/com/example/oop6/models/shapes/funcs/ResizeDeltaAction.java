package com.example.oop6.models.shapes.funcs;

import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Position;

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
        Position position = shape.getPosition();
        Container<Shape> shapes = shape.getShapes();
        for (Shape s : shapes) {
            s.setSize(s.getWidth() + dx, s.getHeight() + dy);
            s.setPosition(s.getPosition().changePosition(position.getX(), position.getY()));
            if (!(s.entersByWidth(width) && s.entersByHeight(height))) {
                s.setSize(s.getWidth() - dx, s.getHeight() - dy);
                s.setPosition(s.getPosition().changePosition(-position.getX(), -position.getY()));
                return false;
            }
            s.setPosition(s.getPosition().changePosition(-position.getX(), -position.getY()));
            s.setSize(s.getWidth() - dx, s.getHeight() - dy);
        }
        if (!shapeAction(shape)) return false;
        for (Shape s : shapes) {
            s.setPosition(s.getPosition().changePosition(position.getX(), position.getY()));
            s.accept(this);
            s.setPosition(s.getPosition().changePosition(-position.getX(), -position.getY()));
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
