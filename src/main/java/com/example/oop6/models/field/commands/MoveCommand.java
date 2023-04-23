package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.funcs.MoveAction;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Position;

import java.util.Iterator;

public class MoveCommand implements Command{
    private int dx;
    private int dy;
    private PaintField paintField;
    private Container<Position> posUndo;
    private Container<Shape> selected;

    public MoveCommand(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute(PaintField paintField) {
        this.paintField = paintField;
        selected = new Container<>();
        posUndo = new Container<>();
        for (Shape shape : paintField.getAllSelectedShapes()) {
            posUndo.add(shape.getInstance().getPosition().clone());
            selected.add(shape);
        }
        paintField.moveSelectedShapes(dx, dy);
    }

    @Override
    public void unExecute() {
        Iterator<Position> iterator = posUndo.iterator();
        for (Shape shape : selected) {
            Position next = iterator.next();
            shape.getPosition()
                            .changePosition(
                                    next.getX() - shape.getPosition().getX(),
                                    next.getY() - shape.getPosition().getY());
        }
        paintField.drawAllShapesInContainer();
    }

    @Override
    public Command clone() {
        return null;
    }

    @Override
    public String report() {
        return "MoveCommand";
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
}
