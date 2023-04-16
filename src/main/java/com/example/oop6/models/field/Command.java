package com.example.oop6.models.field;

import com.example.oop6.models.shapes.Shape;

public interface Command {
    void execute(PaintField paintField);
    void unExecute();

    Command clone();
    String report();
}
