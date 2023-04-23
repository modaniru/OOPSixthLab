package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Container;

public class SelectCommand implements Command{
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private PaintField paintField;
    private Container<Shape> selected = new Container<>();
    private Container<Shape> oldSelected = new Container<>();

    public SelectCommand(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    @Override
    public void execute(PaintField paintField) {
        this.paintField = paintField;
        if (oldSelected.getSize()==0)
            oldSelected = paintField.getAllSelectedShapes();
        if(x1 == x2 && y1 == y2){
            paintField.changeSelectIfInside(x1, y1);
        }
        else{
            paintField.selectInSection(x1, y1, x2, y2);
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

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }
}
