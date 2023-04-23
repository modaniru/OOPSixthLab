package com.example.oop6.utils.instruments;

import com.example.oop6.models.field.commands.Command;
import com.example.oop6.models.field.commands.CreateCommand;
import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.ShapeDecorator;
import com.example.oop6.utils.Position;

import java.util.Optional;

public class CreateInstrument implements Instrument{

    private final PaintField paintField;
    private Shape shape;
    private boolean inShape = false;
    private Command command;

    public CreateInstrument(PaintField paintField) {
        this.paintField = paintField;
    }

    @Override
    public void mouseDown(Shape shape, int x, int y) {
        command = null;
        this.shape = shape;
        shape.setPosition(new Position(x, y));
        inShape = paintField.insideTheFigure(shape.getPosition().getX(), shape.getPosition().getY());
    }

    @Override
    public void drag(int x, int y) {
        if(!inShape){
            shape.setSize(Math.abs(x - shape.getPosition().getX()) * 2, Math.abs(y - shape.getPosition().getY()) * 2);
            paintField.drawTempShape(new ShapeDecorator(shape));
        }
    }

    //todo возврщать команду
    @Override
    public Optional<Command> mouseUp(int x, int y) {
        if(!inShape && shape.isCorrect() && shape.entersByWidth(paintField.getFieldWidth()) && shape.entersByHeight(paintField.getFieldHeight())){
            command = new CreateCommand(shape);
            command.execute(paintField);
            return Optional.of(command);
        }
        return Optional.empty();
    }
}
