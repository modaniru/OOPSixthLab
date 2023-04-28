package com.example.oop6.models;

import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Container;

public interface Observer {
    public void notifyObserver(Container<Shape> shapes);
}
