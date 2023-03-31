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

    //Добавляет или выделяет фигуру
    public void addOrSelectShape(Shape shape, int x, int y) {
        if (!multiplySelection) unselectAllShapes();
        if (!isASelection(x, y)) {
            shape.setPosition(x, y);
            if (!(shape.isItIncludedWidth(fieldWidth) && shape.isItIncludedHeight(fieldHeight))) {
                drawAllShapesInContainer();
                return;
            }
            unselectAllShapes();
            shape.changeSelection();
            shapeContainer.add(shape);
        }
        drawAllShapesInContainer();
    }

    //Устанавливает флаг множественного выделения
    public void setMultiplySelection(boolean value) {
        multiplySelection = value;
    }

    //Удаляет все выделенные фигуры
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

    //Отвечает за отрисовку, после изменения размера формы
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

    //Очищает поле
    public void clearField() {
        shapeContainer.clear();
        clearCanvas();
    }

    //метод, который двигает фигуру на dx и dy
    public void moveAllSelectedShapes(int dx, int dy) {
        map(shape -> {
            if (shape.isSelection()) {
                int oldX = shape.getX();
                int oldY = shape.getY();
                shape.setPosition(oldX + dx, oldY + dy);
                if (!(shape.isItIncludedWidth(fieldWidth) && shape.isItIncludedHeight(fieldHeight))) {
                    shape.setPosition(oldX, oldY);
                }
            }
        });
        drawAllShapesInContainer();
    }

    //Меняет цвет всех выделенных фигур
    public void changeColorSelectedShapes(Color color) {
        map(shape -> {
            if (shape.isSelection()) {
                shape.setFillColor(color);
            }
        });
        drawAllShapesInContainer();
    }

    //Отвечает за изменения размера фигуры
    public void resizeSelectedShapes(int newWidth, int newHeight) {
        resizeShapes(shape -> {
            shape.setSize(newWidth, newHeight);
        });
    }

    public void resizeDeltaSelectedShapes(int dx, int dy) {
        resizeShapes(shape -> {
            shape.increaseSize(dx, dy);
        });
    }

    private void resizeShapes(ContainerMapFunc mapFunc) {
        map(shape -> {
            if (shape.isSelection()) {
                int oldWidth = shape.getWidth();
                int oldHeight = shape.getHeight();
                mapFunc.map(shape);
                if(!shape.isItIncludedWidth(fieldWidth)){
                    shape.setSize(oldWidth, shape.getHeight());
                }
                if (!shape.isItIncludedHeight(fieldHeight)){
                    shape.setSize(shape.getWidth(), oldHeight);
                }
            }
        });
        drawAllShapesInContainer();
    }

    //Проверяет, если ли в списке, которые могут быть выделенными по заданным координатам
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

    //Отрисовывает все фигуры, находящиеся в списке
    private void drawAllShapesInContainer() {
        clearCanvas();
        GraphicsContext gc = fieldCanvas.getGraphicsContext2D();
        map(shape -> {
            if ((shape.isItIncludedWidth(fieldWidth) && shape.isItIncludedHeight(fieldHeight))) shape.draw(gc);
            else shape.disableSelection();
        });
    }

    //Удаляет все фигуры, по заданному массиву индексов
    private void deleteAtIndexes(List<Integer> list) {
        if (list.isEmpty()) return;
        for (int i = list.size() - 1; i >= 0; i--) {
            shapeContainer.deleteAt(list.get(i));
        }
    }

    //Выключает выделение у всех фигур
    private void unselectAllShapes() {
        map(Shape::disableSelection);
    }

    //Очищает canvas
    private void clearCanvas() {
        fieldCanvas.getGraphicsContext2D().clearRect(0, 0, fieldCanvas.getWidth(), fieldCanvas.getHeight());
    }

    //Функция для прохода по всем элементам, принимающая в себя функц. интерфейс
    private void map(ContainerMapFunc func) {
        for (Shape shape : shapeContainer) {
            func.map(shape);
        }
    }
}
