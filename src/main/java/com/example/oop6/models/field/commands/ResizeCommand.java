package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.funcs.ResizeDeltaAction;
import com.example.oop6.utils.Container;

import java.util.Iterator;

public class ResizeCommand implements Command{
    private PaintField paintField;
    private Container<Shape> resizedShapes;
    private class Size{
        private double width;
        private double height;

        public Size(double width, double height) {
            this.width = width;
            this.height = height;
        }
    }
    private Container<Size> oldSize;
    private double dx;
    private double dy;

    public ResizeCommand(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute(PaintField paintField) {
        this.paintField = paintField;
        resizedShapes = paintField.getAllSelectedShapes();
        oldSize = new Container<>();
        for (Shape s : resizedShapes) {
            s = s.getInstance();
            oldSize.add(new Size(s.getWidth(), s.getHeight()));
        }
        ResizeDeltaAction action = new ResizeDeltaAction(paintField.getFieldWidth(), paintField.getFieldHeight());
        action.setDx(dx);
        action.setDy(dy);
        paintField.actionSelectedShapes(action);
    }

    @Override
    public void unExecute() {
        Iterator<Size> iterator = oldSize.iterator();
        for (Shape s : resizedShapes) {
            Size next = iterator.next();
            s.setSize(next.width, next.height);
        }
        paintField.drawAllShapesInContainer();
    }

    @Override
    public Command clone() {
        return null;
    }

    @Override
    public String report() {
        return "ResizeCommand";
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}
