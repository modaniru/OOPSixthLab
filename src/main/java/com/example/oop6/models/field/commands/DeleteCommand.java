package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.ShapeDecorator;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Images;
import javafx.scene.image.Image;

/* Команда удаления фигуры */
public class DeleteCommand implements Command {
    /* Все удаленные фигуры */
    private Container<Shape> deletedShapes;
    private PaintField paintField;

    @Override
    public void execute(PaintField paintField) {
        this.deletedShapes = paintField.getAllSelectedShapes();
        this.paintField = paintField;
        paintField.deleteAllSelectedShapes();
    }

    @Override
    public void unExecute() {
        for (Shape shape : deletedShapes) {
            paintField.addShapeToContainer(new ShapeDecorator(shape.getInstance()));
        }
    }

    @Override
    public Image getImage() {
        return Images.DELETE.getImage();
    }
}
