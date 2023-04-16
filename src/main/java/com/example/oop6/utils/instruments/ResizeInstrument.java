package com.example.oop6.utils.instruments;

import com.example.oop6.models.field.Command;
import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.funcs.ResizeDeltaAction;

public class ResizeInstrument implements Instrument{
    private int startX;
    private int startY;
    private int oldX;
    private int oldY;
    private PaintField paintField;

    public ResizeInstrument(PaintField paintField) {
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
        ResizeDeltaAction resizeDeltaAction = new ResizeDeltaAction(paintField.getFieldWidth(), paintField.getFieldHeight());
        resizeDeltaAction.setDx(x - oldX);
        resizeDeltaAction.setDy(y - oldY);
        paintField.actionSelectedShapes(resizeDeltaAction);
        oldX = x;
        oldY = y;
    }

    @Override
    public Command mouseUp(int x, int y) {
        //todo return paint field resize command
        return null;
    }
}
