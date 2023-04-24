package com.example.oop6.utils.instruments;

import com.example.oop6.models.field.commands.Command;
import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.field.commands.ResizeCommand;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.funcs.ResizeDeltaAction;
import com.example.oop6.utils.Position;

import java.util.Optional;

/* Инструмент, который отвечает за изменение размера фигур */
public class ResizeInstrument extends PositionInstrument {
    private ResizeCommand resizeCommand;

    public ResizeInstrument(PaintField paintField) {
        super(paintField);
    }

    @Override
    public void mouseDown(Shape shape, Position position) {
        startPosition = position.clone();
        oldPosition = startPosition.clone();
        this.resizeCommand = new ResizeCommand(0, 0);
        resizeCommand.execute(paintField);
    }

    @Override
    public void drag(Position position) {
        ResizeDeltaAction resizeDeltaAction = new ResizeDeltaAction(paintField.getFieldWidth(), paintField.getFieldHeight());
        resizeDeltaAction.setDx(position.getX() - oldPosition.getX());
        resizeDeltaAction.setDy(position.getY() - oldPosition.getY());
        paintField.actionSelectedShapes(resizeDeltaAction);
        oldPosition = position.clone();
    }

    @Override
    public Optional<Command> mouseUp(Position position) {
        resizeCommand.setDx(position.getX() - startPosition.getX());
        resizeCommand.setDy(position.getY() - startPosition.getY());
        return Optional.of(resizeCommand);
    }
}
