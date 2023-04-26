package com.example.oop6.models.field.commands;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.funcs.ChangeColorAction;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Images;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Iterator;

/* Команда отвечающая за изменение цвета выделенных фигур */
public class ColorCommand implements Command {
    private PaintField paintField;
    /* Старые цвета фигур */
    private final Container<Color> oldColors = new Container<>();
    /* Все выделенные фигуры */
    private final Container<Shape> allSelectedShapes = new Container<>();
    private final Color newColor;

    public ColorCommand(Color newColor) {
        this.newColor = newColor;
    }

    @Override
    public void execute(PaintField paintField) {
        this.paintField = paintField;
        for (Shape shape : paintField.getAllSelectedShapes()) {
            getAllShapes(shape.getInstance());
        }
        for (Shape shape : allSelectedShapes) {
            oldColors.add(shape.getFillColor());
        }
        paintField.actionSelectedShapes(new ChangeColorAction(newColor));
    }

    @Override
    public void unExecute() {
        Iterator<Color> iterator = oldColors.iterator();
        for (Shape shape : allSelectedShapes) {
            Color next = iterator.next();
            shape.setFillColor(next);
        }
        paintField.drawAllShapesInContainer();
    }

    @Override
    public Image getImage() {
        return Images.COLOR.getImage();
    }

    /* Получаем все ФИГУРЫ (без групп), т.к. у группы могут быть фигуры разных цветов */
    private void getAllShapes(Shape shape) {
        // Крайний случай рекурсии
        if (shape.getShapes().size() == 0)
            allSelectedShapes.add(shape);
        else {
            for (Shape s : shape.getShapes()) {
                getAllShapes(s);
            }
        }
    }
}
