package com.example.oop6.utils.instruments;

import com.example.oop6.models.field.commands.Command;
import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.field.commands.ResizeCommand;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.funcs.ResizeDeltaAction;

import java.util.Optional;

public class ResizeInstrument implements Instrument{
    private int startX;
    private int startY;
    private int oldX;
    private int oldY;
    private final PaintField paintField;
    private ResizeCommand resizeCommand;

    public ResizeInstrument(PaintField paintField) {
        this.paintField = paintField;
    }

    @Override
    public void mouseDown(Shape shape, int x, int y) {
        startX = x;
        startY = y;
        oldX = x;
        oldY = y;
        this.resizeCommand = new ResizeCommand(0,0);
        resizeCommand.execute(paintField);
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
    public Optional<Command> mouseUp(int x, int y) {
        resizeCommand.setDx(x - startX);
        resizeCommand.setDy(y - startY);
        return Optional.of(resizeCommand);
    }
}
