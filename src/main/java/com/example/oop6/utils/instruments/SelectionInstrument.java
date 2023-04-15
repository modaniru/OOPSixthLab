package com.example.oop6.utils.instruments;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Rectangle;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.ShapeDecorator;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Position;

public class SelectionInstrument implements Instrument {
    //todo mb singlethon
    private PaintField paintField;
    private Position startPosition;
    private ShapeDecorator shapeDecorator;

    public SelectionInstrument(PaintField paintField) {
        this.paintField = paintField;
    }

    @Override
    public void mouseDown(Shape shape, int x, int y) {
        shapeDecorator = new ShapeDecorator(new Rectangle(0, 0));
        startPosition = new Position(x, y);
    }

    @Override
    public void drag(int x, int y) {
        shapeDecorator.setPosition(new Position(startPosition.getX() + (x - startPosition.getX()) / 2, startPosition.getY() + (y - startPosition.getY()) / 2));
        shapeDecorator.setSize(Math.abs(x - shapeDecorator.getPosition().getX()) * 2, Math.abs(y - shapeDecorator.getPosition().getY()) * 2);
        paintField.selectInSection(startPosition.getX(), startPosition.getY(), x, y);
        paintField.drawTempShape(shapeDecorator);

    }

    @Override
    public void mouseUp(int x, int y) {
        if (startPosition.getX() == x && startPosition.getY() == y) {
            paintField.changeSelectIfInside(x, y);
        } else {
            shapeDecorator.setSize(0, 0);
            shapeDecorator.setPosition(new Position(-10, -10));
            paintField.drawTempShape(shapeDecorator);
        }

    }

}
