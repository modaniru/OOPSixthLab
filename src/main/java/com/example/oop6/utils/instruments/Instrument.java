package com.example.oop6.utils.instruments;

import com.example.oop6.models.field.commands.Command;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Position;

import java.util.Optional;

public interface Instrument {
    void mouseDown(Shape shape, Position position);

    void drag(Position position);

    Optional<Command> mouseUp(Position position);
}
