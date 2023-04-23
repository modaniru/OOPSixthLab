package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;

public interface Command {
    void execute(PaintField paintField);
    void unExecute();
    Command clone();
    String report();
}
