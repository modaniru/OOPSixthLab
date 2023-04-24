package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Images;
import javafx.scene.image.Image;

public class UnGroupCommand implements Command {
    private Container<Shape> groups;
    private final Container<Container<Shape>> unGroupShapes = new Container<>();
    private PaintField paintField;
    @Override
    public void execute(PaintField paintField) {
        //todo может ли группироваться один элемент
        this.paintField = paintField;
        groups = paintField.getAllSelectedShapes();
        for (Shape group : groups) {
            unGroupShapes.add(paintField.unGroupShape(group));
        }
    }

    @Override
    public void unExecute() {
        for (Shape group : groups) {
            for (Shape shape : group.getInstance().getShapes()) {
                shape = shape.getInstance();
                paintField.removeInstanceShape(shape);
            }
            paintField.addShape(group);
        }
    }

    @Override
    public Command clone() {
        return null;
    }
    @Override
    public String report() {
        return this.getClass().getSimpleName();
    }
    @Override
    public Image getImage() {
        return Images.COLOR.getImage();
    }
}
