package com.example.oop6.models;

import com.example.oop6.funcInterfaces.ContainerMapFunc;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Container;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PaintField {
    private final Container<Shape> shapeContainer;
    private final Canvas fieldCanvas;
    private boolean multiplySelection = false;
    private int fieldWidth;
    private int fieldHeight;

    public PaintField(Canvas canvas) {
        fieldWidth = (int) canvas.getWidth();
        fieldHeight = (int) canvas.getHeight();
        this.shapeContainer = new Container<>();
        this.fieldCanvas = canvas;
    }

    public void addOrSelectShape(Shape shape, int x, int y) {
        if (!multiplySelection) unselectAllShapes();
        if (!isASelection(x, y)) {
            shape.setPosition(x, y);
            if (!isInAField(shape)) {
                drawAllShapesInContainer();
                return;
            }
            unselectAllShapes();
            shape.changeSelection();
            shapeContainer.add(shape);
        }
        drawAllShapesInContainer();
    }

    public void setMultiplySelection(boolean value) {
        multiplySelection = value;
    }

    public void deleteAllSelectedShapes() {
        List<Integer> candidates = new ArrayList<>();
        int i = 0;
        for (Shape shape : shapeContainer) {
            if (shape.isSelection())
                candidates.add(i);
            i++;
        }
        deleteAtIndexes(candidates);
        drawAllShapesInContainer();
    }

    public void resizeWidth(int newWidth) {
        fieldWidth = newWidth;
        fieldCanvas.setWidth(newWidth);
        drawAllShapesInContainer();
    }

    public void resizeHeight(int newHeight) {
        fieldHeight = newHeight;
        fieldCanvas.setHeight(newHeight);
        drawAllShapesInContainer();
    }

    public void clearField() {
        shapeContainer.clear();
        clearCanvas();
    }

    public void moveAllSelectedShapes(int dx, int dy) {
        map(shape -> {
            if (shape.isSelection()) {
                if (shape.getX() + shape.getCenterToX() + dx >= fieldWidth || shape.getX() - shape.getCenterToX() + dx <= 0)
                    return;
                if (shape.getY() + shape.getCenterToY() + dy >= fieldHeight || shape.getY() - shape.getCenterToY() + dy <= 0)
                    return;
                shape.setPosition(shape.getX() + dx, shape.getY() + dy);
            }
        });
        drawAllShapesInContainer();
    }

    public void changeColorSelectedShapes(Color color) {
        map(shape -> {
            if (shape.isSelection()) {
                shape.setFillColor(color);
            }
        });
        drawAllShapesInContainer();
    }

    public void resizeSelectedShapes(int newWidth, int newHeight) {
        map(shape -> {
            if (shape.isSelection()) {
                if (!(shape.getX() + (newWidth / 2) >= fieldWidth || shape.getX() - (newWidth / 2) <= 0)) {
                    shape.setWidth(newWidth);
                }
                if (!(shape.getY() + (newHeight / 2) >= fieldHeight || shape.getY() - (newHeight / 2) <= 0)) {
                    shape.setHeight(newHeight);
                }
            }
        });
        drawAllShapesInContainer();
    }

    private boolean isASelection(int x, int y) {
        boolean global = false;
        for (Shape shape : shapeContainer) {
            boolean inArea = shape.inShapeArea(x, y);
            if (inArea) {
                global = true;
                shape.changeSelection();
            }
            if (inArea && !multiplySelection) return global;
        }
        return global;
    }


    private void drawAllShapesInContainer() {
        clearCanvas();
        GraphicsContext gc = fieldCanvas.getGraphicsContext2D();
        List<Integer> candidates = new ArrayList<>();
        int i = 0;
        for (Shape shape : shapeContainer) {
            if (!isInAField(shape)) candidates.add(i);
            else shape.draw(gc);
            i++;
        }
        deleteAtIndexes(candidates);
    }

    private void deleteAtIndexes(List<Integer> list) {
        if (list.isEmpty()) return;
        for (int i = list.size() - 1; i >= 0; i--) {
            shapeContainer.deleteAt(list.get(i));
        }
    }

    private void unselectAllShapes() {
        map(Shape::disableSelection);
    }

    private void clearCanvas() {
        fieldCanvas.getGraphicsContext2D().clearRect(0, 0, fieldCanvas.getWidth(), fieldCanvas.getHeight());
    }

    private boolean isInAField(Shape shape) {
        return shape.getX() > shape.getCenterToX() && shape.getY() > shape.getCenterToY() && shape.getX() < fieldWidth - shape.getCenterToX() && shape.getY() < fieldHeight - shape.getCenterToY();
    }

    private void map(ContainerMapFunc func) {
        for (Shape shape : shapeContainer) {
            func.map(shape);
        }
    }
}
