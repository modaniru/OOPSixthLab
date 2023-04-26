package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Images;
import javafx.scene.image.Image;

/* Команда разгруппировки фигур */
public class UnGroupCommand implements Command {
    private Container<Shape> groups = new Container<>();
    private final Container<Container<Shape>> unGroupShapes = new Container<>();
    private PaintField paintField;
    @Override
    public void execute(PaintField paintField) {
        this.paintField = paintField;
        for (Shape shape : paintField.getAllSelectedShapes()) {
            if(shape.getInstance().getShapes().size() != 0) groups.add(shape);
        }
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
            paintField.addShape(group.getInstance());
        }
    }

    @Override
    public Image getImage() {
        return Images.UNGROUPING.getImage();
    }
}
