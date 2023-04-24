package com.example.oop6.utils.instruments;

import com.example.oop6.models.field.commands.Command;
import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.field.commands.SelectCommand;
import com.example.oop6.models.shapes.Rectangle;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.ShapeDecorator;
import com.example.oop6.utils.Position;

import java.util.Optional;
/* Инструмент, который отвечает за выделение фигур */
public class SelectionInstrument extends PositionInstrument {
    private ShapeDecorator shapeDecorator;
    private SelectCommand command;

    public SelectionInstrument(PaintField paintField) {
        super(paintField);
    }

    @Override
    public void mouseDown(Shape shape, Position position) {
        command = new SelectCommand(position, position);
        shapeDecorator = new ShapeDecorator(new Rectangle(0, 0));
        startPosition = position.clone();
    }

    @Override
    public void drag(Position position) {
        shapeDecorator.setPosition(new Position(startPosition.getX() + (position.getX() - startPosition.getX()) / 2, startPosition.getY() + (position.getY() - startPosition.getY()) / 2));
        shapeDecorator.setSize(Math.abs(position.getX() - shapeDecorator.getPosition().getX()) * 2, Math.abs(position.getY() - shapeDecorator.getPosition().getY()) * 2);
        command.setSecondPosition(position);
        command.execute(paintField);
        paintField.drawTempShape(shapeDecorator);
    }

    @Override
    public Optional<Command> mouseUp(Position position) {
        command.setSecondPosition(position);
        command.execute(paintField);
        paintField.drawAllShapesInContainer();
        return Optional.of(command);
    }

}
