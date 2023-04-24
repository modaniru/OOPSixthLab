package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import javafx.scene.image.Image;

public interface Command {
    void execute(PaintField paintField);
    void unExecute();
    Command clone();
    String report();
    Image getImage();
}
