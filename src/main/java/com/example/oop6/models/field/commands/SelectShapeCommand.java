package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Images;
import javafx.scene.image.Image;

public class SelectShapeCommand implements Command {
    private Container<Shape> shapes;
    private Container<Shape> oldSelected;
    private PaintField paintField;

    public SelectShapeCommand(Container<Shape> shapes) {
        this.shapes = shapes;
    }

    @Override
    public void execute(PaintField paintField) {
        this.paintField = paintField;
        oldSelected = paintField.getAllSelectedShapes();
        paintField.unselectAllShapes();
        for (Shape shape : shapes) {
            while (shape.getRoot() != null) shape = shape.getRoot();
            paintField.selectShape(shape);
        }
    }

    @Override
    public void unExecute() {
        paintField.unselectAllShapes();
        for (Shape shape : oldSelected) {
            paintField.selectShape(shape.getInstance());
        }
    }

    @Override
    public Image getImage() {
        return Images.SELECT.getImage();
    }
}
