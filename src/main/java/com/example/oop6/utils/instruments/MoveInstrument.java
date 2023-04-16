package com.example.oop6.utils.instruments;

import com.example.oop6.models.field.Command;
import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.funcs.MoveAction;
import com.example.oop6.models.shapes.funcs.ShapeAction;

public class MoveInstrument implements Instrument{
    private final PaintField paintField;
    private int startX;
    private int startY;
    private int oldX;
    private int oldY;

    public MoveInstrument(PaintField paintField) {
        this.paintField = paintField;
    }

    @Override
    public void mouseDown(Shape shape, int x, int y) {
        startX = x;
        startY = y;
        oldX = x;
        oldY = y;
    }

    @Override
    public void drag(int x, int y) {
        MoveAction shapeAction = new MoveAction(paintField.getFieldWidth(), paintField.getFieldHeight());
        shapeAction.setDx(x - oldX);
        shapeAction.setDy(y - oldY);
        paintField.actionSelectedShapes(shapeAction);
        oldY = y;
        oldX = x;
    }

    @Override
    public Command mouseUp(int x, int y) {
        //todo return paintFieldCommand
        return null;
    }
}
