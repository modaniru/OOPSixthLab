package com.example.oop6.utils.instruments;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Rectangle;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.ShapeDecorator;
import com.example.oop6.utils.Container;

public class SelectionInstrument implements Instrument {
    //todo mb singlethon
    private PaintField paintField;
    private int xStart;
    private int yStart;
    private ShapeDecorator shapeDecorator;

    public SelectionInstrument(PaintField paintField) {
        this.paintField = paintField;
    }

    @Override
    public void mouseDown(Shape shape, int x, int y) {
        shapeDecorator = new ShapeDecorator(new Rectangle(0, 0));
        xStart = x;
        yStart = y;
    }

    @Override
    public void drag(int x, int y) {
        shapeDecorator.setPosition(xStart + (x - xStart) / 2, yStart + (y - yStart) / 2);
        shapeDecorator.setSize(Math.abs(x - shapeDecorator.getX()) * 2, Math.abs(y - shapeDecorator.getY()) * 2);
        paintField.selectInSection(xStart, yStart, x, y);
        paintField.drawTempShape(shapeDecorator);

    }

    @Override
    public void mouseUp(int x, int y) {
        if (xStart == x && yStart == y) {
            paintField.changeSelectIfInside(x, y);
        } else {
            shapeDecorator.setSize(0, 0);
            shapeDecorator.setPosition(-10, -10);
            paintField.drawTempShape(shapeDecorator);
        }

    }

}
