package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Images;
import javafx.scene.image.Image;

/* Команда группировки фигур */
public class GroupCommand implements Command {
    private PaintField paintField;
    private Shape group;

    @Override
    public void execute(PaintField paintField) {
        this.group = paintField.groupSelectedShapes();
        this.paintField = paintField;
    }

    @Override
    public void unExecute() {
        paintField.unGroupShape(group);
    }

    @Override
    public Image getImage() {
        return Images.GROUPING.getImage();
    }
}
