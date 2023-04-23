package com.example.oop6.utils.instruments;

import com.example.oop6.models.field.commands.Command;
import com.example.oop6.models.shapes.Shape;

import java.util.Optional;

public interface Instrument {
    void mouseDown(Shape shape, int x, int y);

    void drag(int x, int y);

    Optional<Command> mouseUp(int x, int y);
}
