package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.ShapeDecorator;
import com.example.oop6.models.shapes.funcs.ResizeDeltaAction;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Images;
import javafx.scene.image.Image;

import java.util.Iterator;
/* Команда изменения размера фигуры */
public class ResizeCommand implements Command{
    private PaintField paintField;
    private Container<Shape> resizedShapes = new Container<>();
    private class Size{
        private double width;
        private double height;

        public Size(double width, double height) {
            this.width = width;
            this.height = height;
        }
    }
    private Container<Size> oldSize;
    private Container<Shape> delete;
    private double dx;
    private double dy;

    public ResizeCommand(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute(PaintField paintField) {
        this.paintField = paintField;
        delete = paintField.getAllSelectedShapes();
        for (Shape shape : delete) {
            getAllShapes(shape.getInstance());
        }
        oldSize = new Container<>();
        for (Shape s : resizedShapes) {
            oldSize.add(new Size(s.getWidth(), s.getHeight()));
        }
        ResizeDeltaAction action = new ResizeDeltaAction(paintField.getWidth(), paintField.getHeight());
        action.setDx(dx);
        action.setDy(dy);
        paintField.actionSelectedShapes(action);
    }

    private void getAllShapes(Shape shape){
        resizedShapes.add(shape);
        for (Shape s : shape.getInstance().getShapes()) {
            getAllShapes(s);
        }
    }

    @Override
    public void unExecute() {
        Iterator<Size> iterator = oldSize.iterator();
        for (Shape s : resizedShapes) {
            Size next = iterator.next();
            s.setSize(next.width, next.height);
        }
        for (Shape shape : delete) {
            paintField.removeInstanceShape(shape);
            paintField.addShapeToContainer(new ShapeDecorator(shape.getInstance()));
        }
        paintField.drawAllShapesInContainer();
    }


    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
    @Override
    public Image getImage() {
        return Images.SIZE.getImage();
    }
}
