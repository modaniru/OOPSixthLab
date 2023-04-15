package com.example.oop6.models.shapes;

import com.example.oop6.models.shapes.funcs.ShapeAction;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShapeDecorator extends Shape {
    private Shape instance;

    public ShapeDecorator(Shape instance) {
        super(instance.getWidth(), instance.getHeight());
        position = instance.position;
        this.instance = instance;
    }

    @Override
    public Shape getExample() {
        return new ShapeDecorator(instance.clone());
    }

    @Override
    public boolean inShapeArea(int x, int y) {
        return instance.getInstance().inShapeArea(x, y);
    }

    @Override
    protected void drawShape(GraphicsContext graphicsContext) {
        instance.drawShape(graphicsContext);
        graphicsContext.setLineWidth(2);
        graphicsContext.setStroke(Color.LIGHTBLUE);
        graphicsContext.strokeRect(position.getX() - getCenterToX(), position.getY() - getCenterToY(), width, height);
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
        width = instance.getWidth();
        height = instance.getHeight();
    }
}
