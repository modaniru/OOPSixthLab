package com.example.oop6.models.shapes.funcs;

import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Position;

/* Класс посетитель по изменению размера */
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
        //todo вынести
        double width = this.width;
        double height = this.height;
        Position position = new Position(width / 2, height / 2);
        if(shape.getRoot() != null){
            width = shape.getRoot().getWidth();
            height = shape.getRoot().getHeight();
            position = shape.getRoot().getPosition();
        }
        double oldWidth = shape.getWidth();
        double oldHeight = shape.getHeight();
        shape.setSize(oldWidth + dx, oldHeight + dy);
        if (!(shape.entersByWidth(width, position) && shape.entersByHeight(height, position))) {
            shape.setSize(oldWidth, oldHeight);
            return false;
        }
        return true;
    }
    Container<Shape> shapes = new Container<>();
    Container<Shape> sss = new Container<>();
    //сначала проверять смогут ли элементы группы уменьшиться, а потом уменьшать саму группу
    @Override
    public boolean groupAction(Shape shape) {
        double width = this.width;
        double height = this.height;
        Position position = new Position(width / 2, height / 2);
        if(shape.getRoot() != null){
            width = shape.getRoot().getWidth();
            height = shape.getRoot().getHeight();
            position = shape.getRoot().getPosition();
        }
        getAllShapesInContainer(shape);
        shapes.add(shape);
        boolean res = false;
        for (Shape s : shapes) {
            s = s.clone();
            s.setSize(s.getWidth() + dx, s.getHeight() + dy);
            if(!res){
                res = !(s.entersByHeight(height, position) && s.entersByWidth(width, position));
            }
            else{
                break;
            }
        }
        if(res) return false;
        getAllShapesWithGroupInContainer(shape);
        for (Shape s : sss) {
            s.setSize(s.getWidth() + dx, s.getHeight() + dy);
        }
        return true;
    }
    private void getAllShapesInContainer(Shape shape){
        if(shape.getShapes().size() == 0) {
            shapes.add(shape);
        }
        for (Shape s : shape.getShapes()) {
            getAllShapesInContainer(s);
        }
    }
    private void getAllShapesWithGroupInContainer(Shape shape){
        sss.add(shape);
        if(shape.getShapes().size() == 0) {
            return;
        }
        for (Shape s : shape.getShapes()) {
            getAllShapesWithGroupInContainer(s);
        }
    }
    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}
