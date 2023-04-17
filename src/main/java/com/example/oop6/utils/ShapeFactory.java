package com.example.oop6.utils;

import com.example.oop6.models.shapes.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;

public class ShapeFactory implements ShapeAbstractFactory{
    @Override
    public Shape createShape(BufferedReader bufferedReader) throws IOException {
        String shapeName = bufferedReader.readLine();
        Shape shape;
        //todo check exception
        switch (shapeName){
            case "Circle":
                shape = new Circle();
                break;
            case "Rectangle":
                shape = new Rectangle();
                break;
            case "Triangle":
                shape = new Triangle();
                break;
            case "ShapeGroup":
                shape = new ShapeGroup();
                break;
            default:
                throw new IllegalArgumentException();
        }
        shape.load(bufferedReader,  this);
        return shape;
    }
}
