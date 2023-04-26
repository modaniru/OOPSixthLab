package com.example.oop6.utils;

import com.example.oop6.models.shapes.*;

import java.io.BufferedReader;
import java.io.IOException;

/* реализация абстрактной фабрики */
public class ShapeFactory implements ShapeAbstractFactory {
    @Override
    public Shape createShape(BufferedReader bufferedReader) throws IOException {
        String shapeName = bufferedReader.readLine();
        Shape shape = switch (shapeName) {
            case "Circle" -> new Circle();
            case "Rectangle" -> new Rectangle();
            case "Triangle" -> new Triangle();
            case "ShapeGroup" -> new ShapeGroup();
            default -> throw new IllegalArgumentException();
        };
        shape.load(bufferedReader, this);
        return shape;
    }
}
