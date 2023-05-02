package com.example.oop6.models.field;

import com.example.oop6.funcInterfaces.ContainerMapFunc;
import com.example.oop6.models.Observer;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.ShapeDecorator;
import com.example.oop6.models.shapes.ShapeGroup;
import com.example.oop6.models.shapes.funcs.ShapeAction;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Position;
import com.example.oop6.utils.ShapeAbstractFactory;
import javafx.scene.canvas.Canvas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* Обертка над контейнером шейпов, отвечающая за хранение нарисованных объектов*/
public class PaintField {
    private final Container<Shape> shapeContainer;
    private final Canvas fieldCanvas;
    private boolean multiplySelection = false;
    private int width;
    private int height;
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.notifyObserver(shapeContainer);
        }
    }

    public PaintField(Canvas canvas) {
        width = (int) canvas.getWidth();
        height = (int) canvas.getHeight();
        this.shapeContainer = new Container<>();
        this.fieldCanvas = canvas;
    }

    /* Добавляет фигуру, выделяя ее*/
    public void addShape(Shape shape) {
        if (!(shape.entersByWidth(width) && shape.entersByHeight(height))) {
            return;
        }
        if (!multiplySelection) unselectAllShapes();
        addShapeToContainer(new ShapeDecorator(shape));
        notifyAllObservers();
    }

    /* Добавляет фигуру без выделения */
    public void addShapeToContainer(Shape shape) {
        shapeContainer.add(shape);
        drawAllShapesInContainer();
        notifyAllObservers();
    }

    /* Удаляет фигуру по instance */
    public void removeInstanceShape(Shape shape) {
        for (Shape s : shapeContainer) {
            if (s.getInstance() == shape.getInstance()) {
                shape = s;
            }
        }
        shapeContainer.delete(shape);
        drawAllShapesInContainer();
        notifyAllObservers();
    }


    /* Возвращает булево значение, находится ли точка в какой-нибудь фигуре */
    public boolean insideTheFigure(double x, double y) {
        for (Shape shape : shapeContainer) {
            if (shape.inShapeArea(x, y))
                return true;
        }
        return false;
    }

    /* Выделяет все фигуры, которые находятся в заданном поле */
    public void selectInSection(Position f, Position s) {
        unselectAllShapes();
        Container<Shape> news = new Container<>();
        for (Shape shape : shapeContainer) {
            if (shape.getPosition().inArea(f, s)) {
                shapeContainer.replace(shape.getInstance(), new ShapeDecorator(shape.getInstance()));
//                if (shape != shape.getInstance()) continue;
//                news.add(new ShapeDecorator(shape));
//                //shapeContainer.delete(shape);
            }
        }
//        for (Shape shape : news) {
//            shapeContainer.replace(shape.getInstance(), shape);
//        }
        notifyAllObservers();
        drawAllShapesInContainer();
    }

    /* Выделение всех фигур, включающие себя position */
    public void changeSelectIfInside(Position position) {
        if (!multiplySelection) unselectAllShapes();
        Container<Shape> addShapes = new Container<>();
        for (Shape shape : shapeContainer) {
            if (shape.inShapeArea(position.getX(), position.getY())) {
                Shape newShape;
                if (shape == shape.getInstance()) {
                    newShape = new ShapeDecorator(shape);
                } else {
                    newShape = shape.getInstance();
                }
                shapeContainer.replace(shape, newShape);
                //shapeContainer.delete(shape);
                addShapes.add(newShape);
            }
        }
//        for (Shape addShape : addShapes) {
//            shapeContainer.replace(addShape.getInstance(), addShape);
////            addShapeToContainer(addShape);
//        }
        notifyAllObservers();
        drawAllShapesInContainer();
    }

    /* Отрисовать фигуру без занесения ее в контейнер */
    public void drawTempShape(Shape shape) {
        drawAllShapesInContainer();
        shape.draw(fieldCanvas);
    }

    /* Флаг множественного выделения */
    public void setMultiplySelection(boolean value) {
        multiplySelection = value;
    }

    /* Удаление всех выделенных фигур */
    public void deleteAllSelectedShapes() {
        for (Shape shape : getAllSelectedShapes()) {
            shapeContainer.delete(shape);
        }
        notifyAllObservers();
        drawAllShapesInContainer();
    }

    /* Возвращает все выделенные фигуры */
    public Container<Shape> getAllSelectedShapes() {
        Container<Shape> container = new Container<>();
        for (Shape shape : shapeContainer) {
            if (shape != shape.getInstance())
                container.add(shape);
        }
        return container;
    }

    /* Отрисовка формы при изменении ее размера */
    public void resizeWidth(int newWidth) {
        width = newWidth;
        fieldCanvas.setWidth(newWidth);
        drawAllShapesInContainer();
    }

    public void resizeHeight(int newHeight) {
        height = newHeight;
        fieldCanvas.setHeight(newHeight);
        drawAllShapesInContainer();
    }

    /* Очистка всего поля */
    public void clearField() {
        shapeContainer.clear();
        notifyAllObservers();
        clearCanvas();
    }

    /* Группирует выделенные объекты */
    public ShapeGroup groupSelectedShapes() {
        ShapeGroup shapeGroup = new ShapeGroup();
        Container<Shape> selectedShapes = getAllSelectedShapes();
        for (Shape selectedShape : selectedShapes) {
            shapeGroup.addShape(selectedShape.getInstance());
            selectedShape.setRoot(shapeGroup);
            shapeContainer.delete(selectedShape);
        }
        addShape(new ShapeDecorator(shapeGroup));
        drawAllShapesInContainer();
        return shapeGroup;
    }

    /* Отрисовка всех объектов */
    public void drawAllShapesInContainer() {
        clearCanvas();
        map(shape -> shape.draw(fieldCanvas));
    }

    /* Выключает выделение у всех фигур */
    public void unselectAllShapes() {
        List<Shape> decorators = new ArrayList<>();
        for (Shape shape : shapeContainer) {
            if (shape != shape.getInstance()) {
                decorators.add(shape);
            }
        }
        for (Shape decorator : decorators) {
            shapeContainer.replace(decorator, decorator.getInstance());
//            shapeContainer.delete(decorator);
//            shapeContainer.add(decorator.getInstance());
        }
    }

    /* Очистка канваса */
    private void clearCanvas() {
        fieldCanvas.getGraphicsContext2D().clearRect(0, 0, fieldCanvas.getWidth(), fieldCanvas.getHeight());
    }

    /* Обход по всем шейпам */
    private void map(ContainerMapFunc func) {
        for (Shape shape : shapeContainer) {
            func.map(shape);
        }
    }

    /* Выполнение действия для всех выделенных фигур */
    public void actionSelectedShapes(ShapeAction action) {
        for (Shape shape : shapeContainer) {
            if (shape != shape.getInstance()) {
                shape.accept(action);
            }
        }
        drawAllShapesInContainer();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /* Разгруппировка группы */
    public Container<Shape> unGroupShape(Shape shape) {
        removeInstanceShape(shape);
        shape = shape.getInstance();
        for (Shape s : shape.getShapes()) {
            s.setRoot(shape.getRoot());
            addShapeToContainer(new ShapeDecorator(s));
        }
        drawAllShapesInContainer();
        return shape.getShapes();
    }

    /* Загрузка и сохранение */
    public void load(BufferedReader bufferedReader, ShapeAbstractFactory factory) throws IOException {
        shapeContainer.clear();
        int count = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < count; i++) {
            shapeContainer.add(factory.createShape(bufferedReader));
        }
        drawAllShapesInContainer();
        notifyAllObservers();
    }

    public void save(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write(shapeContainer.size() + "\n");
        for (Shape shape : shapeContainer) {
            shape.getInstance().save(bufferedWriter);
        }
    }

    public void selectShape(Shape value) {
        for (Shape shape : shapeContainer) {
            if(shape.getInstance() == value.getInstance()){
                shapeContainer.replace(shape, new ShapeDecorator(shape.getInstance()));
                break;
            }
        }
        drawAllShapesInContainer();
    }

    public void unSelectShape(Shape value){
        for (Shape shape : shapeContainer) {
            if(shape.getInstance() == value.getInstance()){
                shapeContainer.replace(shape, shape.getInstance());
                break;
            }
        }
        drawAllShapesInContainer();
    }
}
