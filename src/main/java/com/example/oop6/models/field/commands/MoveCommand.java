package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.ShapeDecorator;
import com.example.oop6.models.shapes.funcs.MoveAction;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Images;
import com.example.oop6.utils.Position;
import javafx.scene.image.Image;

import java.util.Iterator;

/* Команда перемещения фигур */
public class MoveCommand implements Command {
    private Position deltaPosition;
    private PaintField paintField;
    private Container<Position> posUndo;
    private Container<Shape> selected;

    public MoveCommand(Position position) {
        deltaPosition = position.clone();
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
        MoveAction moveAction = new MoveAction(paintField.getWidth(), paintField.getHeight());
        moveAction.setDx(deltaPosition.getX());
        moveAction.setDy(deltaPosition.getY());
        paintField.actionSelectedShapes(moveAction);
    }

    @Override
    public void unExecute() {
        Iterator<Position> iterator = posUndo.iterator();
        for (Shape shape : selected) {
            Position next = iterator.next();
            paintField.removeInstanceShape(shape);
            shape.setPosition(next);
            paintField.addShapeToContainer(new ShapeDecorator(shape.getInstance()));
        }
        paintField.drawAllShapesInContainer();
    }


    public void setDeltaPosition(Position position) {
        deltaPosition = position.clone();
    }

    @Override
    public Image getImage() {
        return Images.MOVE.getImage();
    }
}
