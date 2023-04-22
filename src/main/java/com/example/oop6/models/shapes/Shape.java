package com.example.oop6.models.shapes;

import com.example.oop6.models.shapes.funcs.ShapeAction;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Position;
import com.example.oop6.utils.ShapeAbstractFactory;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public abstract class Shape {
    public static final double MIN_HEIGHT = 10;
    public static final double MIN_WIDTH = 10;
    protected Position position;
    protected double width;
    protected double height;
    private Color fillColor;

    public Shape(double width, double height) {
        this.width = width;
        this.height = height;
        fillColor = Color.rgb(0, 0, 0, 0);
        position = new Position(0,0);
    }

    //temp
    public Shape clone(){
        Shape shape = getExample();
        shape.setFillColor(fillColor);
        shape.setPosition(position.clone());
        shape.setSize(width, height);
        return shape;
    }

    public boolean isCorrect(){
        return !(width < getMinWidth() || height < getMinHeight());
    }
    //do spec
    public abstract Shape getExample();


    public abstract boolean inShapeArea(double x, double y);

    protected abstract void drawShape(GraphicsContext graphicsContext);

    //todo шаблонный метод рисования
    public void draw(Canvas canvas) {
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();
        if (entersByWidth(width) && entersByHeight(height))
            drawShape(canvas.getGraphicsContext2D());
    }

    //todo можно ли этого избежать
    public Container<Shape> getShapes() {
        return new Container<>();
    }

    public void setPosition(Position position) {
        this.position = position.clone();
    }

    public final double getXDistanceToBorder() {
        return width / 2;
    }

    public final double getYDistanceToBorder() {
        return height / 2;
    }

    public Position getPosition() {
        return position;
    }

    //Вопрос
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void setSizeWithLimit(double width, double height, double fieldWidth, double fieldHeight) {
        if (!(position.getX() > width / 2 && position.getX() < fieldWidth - width / 2)) width = this.width;
        if (!(position.getY() > height / 2 && position.getY() < fieldHeight - height / 2)) height = this.height;
    }

    public void setFillColor(Color color) {
        fillColor = color;
    }

    //Проверяет, находится ли фигура в заданом пространстве
    public boolean entersByWidth(double width) {
        if (this.width < MIN_WIDTH) return false;
        return position.getX() > getXDistanceToBorder() && position.getX() < width - getXDistanceToBorder();
    }

    public boolean entersByHeight(double height) {
        if (this.height < MIN_HEIGHT) return false;
        return position.getY() > getYDistanceToBorder() && position.getY() < height - getYDistanceToBorder();
    }

    protected Color getFillColor() {
        return fillColor;
    }

    public void accept(ShapeAction action) {
        action.shapeAction(this);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getMinHeight() {
        return MIN_HEIGHT;
    }

    public double getMinWidth() {
        return MIN_WIDTH;
    }

    public Shape getInstance() {
        return this;
    }

    public void load(BufferedReader bufferedReader, ShapeAbstractFactory factory) throws IOException {
        //pos
        String line = check("\tx: ", bufferedReader.readLine());
        double x = Double.parseDouble(line.split(" ")[1]);
        line = check("\ty: ", bufferedReader.readLine());
        double y = Double.parseDouble(line.split(" ")[1]);
        position = new Position(x , y);
        //size
        line = check("\twidth: ", bufferedReader.readLine());
        width = Double.parseDouble(line.split(" ")[1]);
        line = check("\theight: ", bufferedReader.readLine());
        height = Double.parseDouble(line.split(" ")[1]);
        //color
        String[] s = check("\trgb: ", bufferedReader.readLine()).split(" ");
        int red = Integer.parseInt(s[1]);
        int green = Integer.parseInt(s[2]);
        int blue = Integer.parseInt(s[3]);
        fillColor = Color.rgb(red, green, blue);
    }

    public void save(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write(getClass().getSimpleName() + "\n");
        bufferedWriter.write("\tx: " + position.getX() + "\n");
        bufferedWriter.write("\ty: " + position.getY() + "\n");
        bufferedWriter.write("\twidth: " + width + "\n");
        bufferedWriter.write("\theight: " + height + "\n");
        bufferedWriter.write("\trgb: " + (int) (fillColor.getRed() * 255) + " " + (int) (fillColor.getGreen() * 255) + " " + (int) (fillColor.getBlue() * 255) + "\n");
    }
    protected String check(String prefix, String line){
        String[] split = line.split(" ");
        if(!(line.startsWith(prefix) && split.length >= 2))
            throw new IllegalArgumentException();
        else{
            try {
                for (int i = 1; i < split.length; i++) {
                    Double.parseDouble(split[i]);
                }
            }
            catch (NumberFormatException e){
                throw new IllegalArgumentException();
            }
        }
        return line;
    }
}
