package com.example.oop6.models.field;

import com.example.oop6.models.shapes.Shape;

import java.util.List;

public class CreateCommand implements Command{
    private List<Shape> shapes;
    private Shape shape;

    public CreateCommand(List<Shape> shapes) {
        this.shapes = shapes;
    }

    @Override
    public void execute(Shape shape) {
        this.shape = shape;
        shapes.add(shape);
    }

    @Override
    public void unExecute() {
        shapes.remove(shape);
    }

    @Override
    public Command clone() {
        return new CreateCommand(shapes);
    }
}
