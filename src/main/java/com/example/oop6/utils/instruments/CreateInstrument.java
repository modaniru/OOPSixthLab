package com.example.oop6.utils.instruments;

import com.example.oop6.models.field.commands.Command;
import com.example.oop6.models.field.commands.CreateCommand;
import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.ShapeDecorator;
import com.example.oop6.utils.Position;

import java.util.Optional;

/* Инструмент, который отвечает за создание фигур */
public class CreateInstrument implements Instrument{

    private final PaintField paintField;
    private Shape shape;
    private boolean inShape = false;

    public CreateInstrument(PaintField paintField) {
        this.paintField = paintField;
    }

    @Override
    public void mouseDown(Shape shape, Position position) {
        this.shape = shape;
        shape.setPosition(position.clone());
        inShape = paintField.insideTheFigure(shape.getPosition().getX(), shape.getPosition().getY());
    }

    @Override
    public void drag(Position position) {
        if(!inShape){
            shape.setSize(Math.abs(position.getX() - shape.getPosition().getX()) * 2, Math.abs(position.getY() - shape.getPosition().getY()) * 2);
            paintField.drawTempShape(new ShapeDecorator(shape));
        }
    }

    @Override
    public Optional<Command> mouseUp(Position position) {
        if(!inShape && shape.isCorrect() && shape.entersByWidth(paintField.getFieldWidth()) && shape.entersByHeight(paintField.getFieldHeight())){
            Command command = new CreateCommand(shape);
            command.execute(paintField);
            return Optional.of(command);
        }
        return Optional.empty();
    }
}
