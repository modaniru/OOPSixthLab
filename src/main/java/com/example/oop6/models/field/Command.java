package com.example.oop6.models.field;

import com.example.oop6.models.shapes.Shape;

public interface Command {
    void execute(Shape shape);
    void unExecute();

    Command clone();
}
