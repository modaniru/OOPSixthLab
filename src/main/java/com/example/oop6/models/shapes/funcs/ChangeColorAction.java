package com.example.oop6.models.shapes.funcs;

import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Container;
import javafx.scene.paint.Color;

/* Класс посетитель по смене цвета */
public class ChangeColorAction implements ShapeAction{
    private Color color;

    public ChangeColorAction(Color color) {
        this.color = color;
    }

    @Override
    public boolean shapeAction(Shape shape) {
        shape.setFillColor(color);
        return true;
    }

    @Override
    public boolean groupAction(Shape shape) {
        Container<Shape> shapes = shape.getShapes();
        for (Shape s : shapes) {
            s.accept(this);
        }
        return true;
    }
}
