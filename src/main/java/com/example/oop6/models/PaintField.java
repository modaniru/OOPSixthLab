package com.example.oop6.models;

import com.example.oop6.funcInterfaces.ContainerMapFunc;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.ShapeGroup;
import com.example.oop6.models.shapes.funcs.ShapeAction;
import com.example.oop6.utils.Container;
import javafx.scene.canvas.Canvas;
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

    //todo NEW вынес логику проверки курсора в фигуре
    //Добавляет или выделяет фигуру
    public void addOrSelectShape(Shape shape) {
        if (!(shape.entersByWidth(fieldWidth) && shape.entersByHeight(fieldHeight))) {
            return;
        }
        if (!multiplySelection) unselectAllShapes();
        shape.changeSelection();
        shapeContainer.add(shape);
        drawAllShapesInContainer();
    }

    //todo NEW
    //Вынес логику выделения фигур в отдельный метод
    public List<Shape> getListInsideTheFigure(int x, int y) {
        List<Shape> shapes = new ArrayList<>();
        for (Shape shape : shapeContainer) {
            if (shape.inShapeArea(x, y)) {
                shapes.add(shape);
            }
        }
        return shapes;
    }

    public boolean insideTheFigure(int x, int y) {
        return !getListInsideTheFigure(x, y).isEmpty();
    }

    public void changeSelectIfInside(int x, int y) {
        if (!multiplySelection) unselectAllShapes();
        List<Shape> shapes = getListInsideTheFigure(x, y);
        for (Shape shape : shapes) {
            shape.changeSelection();
        }
        drawAllShapesInContainer();
    }

    public void drawTempShape(Shape shape) {
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
    //Отвечает за изменения размера фигуры
    public void resizeSelectedShapes(int newWidth, int newHeight) {
        map(shape -> {
            if (shape.isSelection()) shape.setSizeWithLimit(newWidth, newHeight, fieldWidth, fieldHeight);
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

    public void actionSelectedShapes(ShapeAction action) {
        for (Shape shape : shapeContainer) {
            if(shape.isSelection()){
                shape.accept(action);
            }
        }
        drawAllShapesInContainer();
    }
}
