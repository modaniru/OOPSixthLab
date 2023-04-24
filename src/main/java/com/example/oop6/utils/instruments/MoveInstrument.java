package com.example.oop6.utils.instruments;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.field.commands.Command;
import com.example.oop6.models.field.commands.MoveCommand;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Position;

import java.util.Optional;

/* Инструмент, который отвечает за передвижение фигур */
public class MoveInstrument extends PositionInstrument {
    private MoveCommand command;

    public MoveInstrument(PaintField paintField) {
        super(paintField);
    }

    @Override
    public void mouseDown(Shape shape, Position position) {
        startPosition = position.clone();
        oldPosition = startPosition.clone();
        command = new MoveCommand(new Position(0, 0));
        command.execute(paintField);
    }

    @Override
    public void drag(Position position) {
        position.minusDeltaPosition(oldPosition);
        Command command = new MoveCommand(position);
        command.execute(paintField);
        oldPosition.plusDeltaPosition(position);
    }

    @Override
    public Optional<Command> mouseUp(Position position) {
        startPosition.minusDeltaPosition(position);
        command.setDeltaPosition(startPosition);
        return Optional.of(command);
    }
}