package com.example.oop6.utils.instruments;

import com.example.oop6.models.field.commands.Command;
import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.field.commands.MoveCommand;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.funcs.MoveAction;
import com.example.oop6.utils.Container;

import java.util.List;
import java.util.Optional;

public class MoveInstrument implements Instrument{
    private final PaintField paintField;
    private MoveCommand command;
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
        command = new MoveCommand(0, 0);
        command.execute(paintField);
    }

    @Override
    public void drag(int x, int y) {
        Command command = new MoveCommand(x - oldX, y - oldY);
        command.execute(paintField);
        oldY = y;
        oldX = x;
    }

    @Override
    public Optional<Command> mouseUp(int x, int y) {
        command.setDx(startX - x);
        command.setDy(startY - y);
        return Optional.of(command);
    }
}