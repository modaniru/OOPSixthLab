package com.example.oop6.models;

import com.example.oop6.funcInterfaces.ContainerMapFunc;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.ShapeGroup;
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
    public void addOrSelectShape(Shape shape) {
        if (!multiplySelection) unselectAllShapes();
        if (!isASelection(shape.getX(), shape.getY())) {
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

    public void drawTempShape(Shape shape){
        drawAllShapesInContainer();
        shape.draw(fieldCanvas);
    }

    //Устанавливает флаг множественного выделения
    public void setMultiplySelection(boolean value) {
        multiplySelection = value;
    }

    //Удаляет все выделенные фигуры
    public void deleteAllSelectedShapes() {
        shapeContainer.deleteAtIndexes(getSelectedShapesIndexes());
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

    //todo NEW
    //как бы говорим фигуре: "перемести себя, если можешь на dx и dy в пределах fieldWidth, fieldHeight"
    //идентично для других обновленный функций
    //метод, который двигает фигуру на dx и dy
    public void moveAllSelectedShapes(int dx, int dy) {
        map(shape -> {
            if (shape.isSelection()) {
                shape.increasePositionWithLimit(dx, dy, fieldWidth, fieldHeight);
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
    //todo NEW
    //Отвечает за изменения размера фигуры
    public void resizeSelectedShapes(int newWidth, int newHeight) {
        map(shape -> {
            if (shape.isSelection()) shape.setSizeWithLimit(newWidth, newHeight, fieldWidth, fieldHeight);
        });
        drawAllShapesInContainer();
    }
    //todo NEW
    public void resizeDeltaSelectedShapes(int dx, int dy) {
        map(shape -> {
            if (shape.isSelection()) shape.increaseSizeWithLimit(dx, dy, fieldWidth, fieldHeight);
        });
        drawAllShapesInContainer();
    }

    //todo NEW
    //Группирует выделенные объекты
    public void groupSelectedShapes() {
        ShapeGroup shapeGroup = new ShapeGroup();
        shapeGroup.changeSelection();
        map(shape -> {
            if (shape.isSelection()) shapeGroup.addShape(shape);
        });
        deleteAllSelectedShapes();
        shapeContainer.add(shapeGroup);
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
        map(shape -> shape.draw(fieldCanvas));
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

    //Возвращает список индексов у фигур, которые выделены
    private List<Integer> getSelectedShapesIndexes() {
        List<Integer> list = new ArrayList<>();
        int index = 0;
        for (Shape shape : shapeContainer) {
            if (shape.isSelection()) list.add(index);
            index++;
        }
        return list;
    }
}
