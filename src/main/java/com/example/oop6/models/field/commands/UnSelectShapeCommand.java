package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Images;
import javafx.scene.image.Image;

public class UnSelectShapeCommand implements Command{
    private Shape shape;
    private PaintField paintField;

    public UnSelectShapeCommand(Shape shape) {
        shape = shape.getInstance();
        while (shape.getRoot() != null) shape = shape.getRoot();
        this.shape = shape;
    }

    @Override
    public void execute(PaintField paintField) {
        this.paintField = paintField;
        paintField.unSelectShape(shape);
    }

    @Override
    public void unExecute() {
        paintField.selectShape(shape);
    }

    @Override
    public Image getImage() {
        return Images.SELECT.getImage();
    }
}
