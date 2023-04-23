package com.example.oop6.utils.instruments;

import com.example.oop6.models.field.commands.Command;
import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.field.commands.SelectCommand;
import com.example.oop6.models.shapes.Rectangle;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.ShapeDecorator;
import com.example.oop6.utils.Position;

import java.util.Optional;

public class SelectionInstrument implements Instrument {
    //todo mb singlethon
    private PaintField paintField;
    private Position startPosition;
    private ShapeDecorator shapeDecorator;
    private SelectCommand command;

    public SelectionInstrument(PaintField paintField) {
        this.paintField = paintField;
    }

    @Override
    public void mouseDown(Shape shape, int x, int y) {
        command = new SelectCommand(x, y, x, y);
        shapeDecorator = new ShapeDecorator(new Rectangle(0, 0));
        startPosition = new Position(x, y);
    }

    @Override
    public void drag(int x, int y) {
        shapeDecorator.setPosition(new Position(startPosition.getX() + (x - startPosition.getX()) / 2, startPosition.getY() + (y - startPosition.getY()) / 2));
        shapeDecorator.setSize(Math.abs(x - shapeDecorator.getPosition().getX()) * 2, Math.abs(y - shapeDecorator.getPosition().getY()) * 2);
        command.setX2(x);
        command.setY2(y);
        command.execute(paintField);
        paintField.drawTempShape(shapeDecorator);
    }

    @Override
    public Optional<Command> mouseUp(int x, int y) {
        command.setX2(x);
        command.setY2(y);
        command.execute(paintField);
        return Optional.of(command);
    }

}
