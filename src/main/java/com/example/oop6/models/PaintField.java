package com.example.oop6.models;

import com.example.oop6.models.shapes.Shape;
import com.example.oop6.utils.Container;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class PaintField {
    private final Container<Shape> shapeContainer;
    private final Canvas fieldCanvas;
    private boolean multiplySelection = false;
    private int fieldWidth;
    private int fieldHeight;

    public PaintField(Canvas canvas) {
        fieldWidth = (int)canvas.getWidth();
        fieldHeight = (int)canvas.getHeight();
        this.shapeContainer = new Container<>();
        this.fieldCanvas = canvas;
    }

    public void addOrSelectShape(Shape shape, int x, int y){
        if(!multiplySelection) unselectAllShapes();
        if(!isASelection(x,y)){
            shape.setPosition(x, y);
            if(!isInAField(shape)) {
                drawAllShapesInContainer();
                return;
            }
            unselectAllShapes();
            shape.changeSelection();
            shapeContainer.add(shape);
        }
        drawAllShapesInContainer();
    }

    public void setMultiplySelection(boolean value){
        multiplySelection = value;
    }

    public void deleteAllSelectedShapes(){
        List<Integer> candidates = new ArrayList<>();
        int i = 0;
        for (Shape shape : shapeContainer) {
            if(shape.isSelection())
                candidates.add(i);
            i++;
        }
        deleteAtIndexes(candidates);
        drawAllShapesInContainer();
    }

    public void resizeWidth(int newWidth)
    {
        fieldWidth = newWidth;
        fieldCanvas.setWidth(newWidth);
        drawAllShapesInContainer();
    }
    public void resizeHeight(int newHeight)
    {
        fieldHeight = newHeight;
        fieldCanvas.setHeight(newHeight);
        drawAllShapesInContainer();
    }
    public void clearField(){
        shapeContainer.clear();
        clearCanvas();
    }
    private boolean isASelection(int x, int y){
        boolean global = false;
        for (Shape shape : shapeContainer) {
            boolean inArea = shape.inShapeArea(x,y);
            if(inArea){
                global = true;
                shape.changeSelection();
            }
            if(inArea && !multiplySelection) return global;
        }
        return global;
    }



    private void drawAllShapesInContainer(){
        clearCanvas();
        GraphicsContext gc = fieldCanvas.getGraphicsContext2D();
        List<Integer> candidates = new ArrayList<>();
        int i = 0;
        for (Shape shape : shapeContainer) {
            if(!isInAField(shape)) candidates.add(i);
            else shape.draw(gc);
            i++;
        }
        deleteAtIndexes(candidates);
    }

    private void deleteAtIndexes(List<Integer> list){
        if(list.isEmpty()) return;
        for (int i = list.size() - 1; i >= 0; i--) {
            shapeContainer.deleteAt(list.get(i));
        }
    }
    private void unselectAllShapes(){
        for (Shape shape : shapeContainer) {
            shape.disableSelection();
        }
    }
    private void clearCanvas(){
        fieldCanvas.getGraphicsContext2D().clearRect(0,0,fieldCanvas.getWidth(),fieldCanvas.getHeight());
    }
    private boolean isInAField(Shape shape){
        return shape.getX() > shape.getCenterToX() && shape.getY() > shape.getCenterToY() && shape.getX() < fieldWidth - shape.getCenterToX() && shape.getY() < fieldHeight - shape.getCenterToY();
    }
}
