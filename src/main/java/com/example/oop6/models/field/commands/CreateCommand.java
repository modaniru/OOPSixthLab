package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.ShapeDecorator;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Images;
import javafx.scene.image.Image;

public class CreateCommand implements Command {
    private Shape shape;
    private PaintField paintField;
    private Container<Shape> selectedBeforeShapes = new Container<>();

    public CreateCommand(Shape shape) {
        this.shape = shape;
    }

    @Override
    public void execute(PaintField paintField) {
        selectedBeforeShapes = paintField.getAllSelectedShapes();
        paintField.addShape(shape);
        this.paintField = paintField;
    }

    @Override
    public void unExecute() {
        paintField.removeInstanceShape(shape);
        for (Shape shape : selectedBeforeShapes) {
            paintField.removeInstanceShape(shape);
            paintField.addShapeToContainer(new ShapeDecorator(shape));
        }
    }

    @Override
    public Command clone() {
        return null;
    }

    @Override
    public String report() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Image getImage() {
        return Images.CREATE.getImage();
    }
}
