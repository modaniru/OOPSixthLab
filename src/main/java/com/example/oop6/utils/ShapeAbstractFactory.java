package com.example.oop6.utils;

import com.example.oop6.models.shapes.Shape;

import java.io.BufferedReader;
import java.io.IOException;

public interface ShapeAbstractFactory {
    public Shape createShape(BufferedReader bufferedReader) throws IOException;
}
