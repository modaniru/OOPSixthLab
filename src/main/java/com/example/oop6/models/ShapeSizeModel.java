package com.example.oop6.models;

import com.example.oop6.funcInterfaces.ModelChangeEvent;

@Deprecated
public class ShapeSizeModel {
    private int width;
    private int height;
    private ModelChangeEvent modelChangeEvent;

    public ShapeSizeModel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (this.width == width) return;
        this.width = width;
        modelChangeEvent.modelChange(this.width, this.height);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (this.height == height) return;
        this.height = height;
        modelChangeEvent.modelChange(this.width, this.height);
    }

    public void setModelChangeEvent(ModelChangeEvent event) {
        modelChangeEvent = event;
    }
}
