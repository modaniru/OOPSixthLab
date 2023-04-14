package com.example.oop6.models.shapes;

import com.example.oop6.models.shapes.funcs.ShapeAction;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShapeDecorator extends Shape {
    private Shape instance;

    public ShapeDecorator(Shape instance) {
        super(instance.getWidth(), instance.getHeight());
        this.x = instance.getX();
        this.y = instance.getY();
        this.instance = instance;
    }

    @Override
    public Shape clone() {
        return new ShapeDecorator(instance.clone());
    }

    @Override
    public boolean inShapeArea(int x, int y) {
        return instance.getInstance().inShapeArea(x, y);
    }

    @Override
    protected void drawShape(GraphicsContext graphicsContext) {
        graphicsContext.setLineWidth(2);
        graphicsContext.setStroke(Color.LIGHTBLUE);
        graphicsContext.strokeRect(x - getCenterToX(), y - getCenterToY(), width, height);
        instance.drawShape(graphicsContext);
    }

    @Override
    public Shape getInstance() {
        return instance.getInstance();
    }

    @Override
    public int getMinHeight() {
        return instance.getMinHeight();
    }

    @Override
    public int getMinWidth() {
        return instance.getMinWidth();
    }

    @Override
    public void accept(ShapeAction action) {
        instance.accept(action);
        x = instance.getX();
        y = instance.getY();
        width = instance.getWidth();
        height = instance.getHeight();
    }
}
