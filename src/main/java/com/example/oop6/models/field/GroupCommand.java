package com.example.oop6.models.field;

import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.ShapeGroup;

public class GroupCommand implements Command{

    private PaintField paintField;
    private Shape group;
    @Override
    public void execute(PaintField paintField) {
        this.group = paintField.groupSelectedShapes();
        this.paintField = paintField;
    }

    @Override
    public void unExecute() {
        paintField.unGroupShape(group);
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
