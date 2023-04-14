package com.example.oop6.utils.instruments;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;

public interface Instrument {
    void mouseDown(Shape shape, int x, int y);

    void drag(int x, int y);

    void mouseUp(int x, int y);
}
