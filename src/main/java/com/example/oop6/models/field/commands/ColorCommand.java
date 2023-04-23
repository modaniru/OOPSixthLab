package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.funcs.ChangeColorAction;
import com.example.oop6.utils.Container;
import javafx.scene.paint.Color;

import java.util.Iterator;

public class ColorCommand implements Command{
    private PaintField paintField;
    private Container<Color> oldColors = new Container<>();
    private Container<Shape> selected = new Container<>();
    private Color newColor;

    public ColorCommand(Color newColor) {
        this.newColor = newColor;
    }

    @Override
    public void execute(PaintField paintField) {
        this.paintField = paintField;
        for (Shape shape : paintField.getAllSelectedShapes()) {
            selected.add(shape.getInstance());
        }
        for (Shape shape : selected) {
            oldColors.add(shape.getFillColor());
        }
        paintField.actionSelectedShapes(new ChangeColorAction(newColor));
    }

    @Override
    public void unExecute() {
        Iterator<Color> iterator = oldColors.iterator();
        for (Shape shape : selected) {
            Color next = iterator.next();
            shape.setFillColor(next);
        }
        paintField.drawAllShapesInContainer();
    }

    @Override
    public Command clone() {
        return null;
    }

    @Override
    public String report() {
        return "ColorCommand";
    }
}
