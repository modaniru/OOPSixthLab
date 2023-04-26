package com.example.oop6.utils.instruments;

import com.example.oop6.models.field.PaintField;
import com.example.oop6.utils.Position;

public abstract class PositionInstrument implements Instrument {
    public PositionInstrument(PaintField paintField) {
        this.paintField = paintField;
    }

    protected PaintField paintField;
    protected Position startPosition;
    protected Position oldPosition;
}
