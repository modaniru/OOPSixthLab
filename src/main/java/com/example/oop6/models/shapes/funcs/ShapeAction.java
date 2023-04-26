package com.example.oop6.models.shapes.funcs;

import com.example.oop6.models.shapes.Shape;

/* Интерфейс посетителя */
public interface ShapeAction {
    boolean shapeAction(Shape shape);
    boolean groupAction(Shape shape);
}
