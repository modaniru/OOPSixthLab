package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Position;

public class SelectCommand implements Command{
    private Position firstPosition;
    private Position secondPosition;
    private PaintField paintField;
    private Container<Shape> selected = new Container<>();
    private Container<Shape> oldSelected = new Container<>();

    public SelectCommand(Position firstPosition, Position secondPosition) {
        this.firstPosition = firstPosition.clone();
        this.secondPosition = secondPosition.clone();
    }

    @Override
    public void execute(PaintField paintField) {
        this.paintField = paintField;
        if (oldSelected.size()==0)
            oldSelected = paintField.getAllSelectedShapes();
        if(firstPosition.equals(secondPosition)){
            paintField.changeSelectIfInside(firstPosition);
        }
        else{
            paintField.selectInSection(firstPosition, secondPosition);
        }
        for (Shape shape : paintField.getAllSelectedShapes()) {
            selected.add(shape.getInstance());
        }
    }

    @Override
    public void unExecute() {
        for (Shape shape : selected) {
            paintField.removeInstanceShape(shape);
            paintField.addShapeToContainer(shape);
        }
        for (Shape shape : oldSelected) {
            paintField.removeInstanceShape(shape);
            paintField.addShapeToContainer(shape);
        }
    }

    @Override
    public Command clone() {
        return null;
    }

    @Override
    public String report() {
        return "SelectCommand";
    }

    public void setSecondPosition(Position secondPosition) {
        this.secondPosition = secondPosition.clone();
    }
}
