package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;

public class CreateCommand implements Command{
    private Shape shape;
    private PaintField paintField;

    public CreateCommand(Shape shape) {
        this.shape = shape;
    }

    @Override
    public void execute(PaintField paintField) {
        paintField.addShape(shape);
        this.paintField = paintField;
    }

    @Override
    public void unExecute() {
        paintField.removeInstanceShape(shape);
    }

    @Override
    public Command clone() {
        return null;
    }

    @Override
    public String report() {
        return this.getClass().getSimpleName();
    }
}
