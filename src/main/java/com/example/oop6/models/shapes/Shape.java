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
/* Абстрактный класс фигуры */
public abstract class Shape {
    //Минимально возможный размер
    public static final double MIN_HEIGHT = 10;
    public static final double MIN_WIDTH = 10;
    protected Position position;
    protected double width;
    protected double height;
    private Color fillColor;
    private Shape root;

    public Shape(double width, double height) {
        this.width = width;
        this.height = height;
        fillColor = Color.rgb(0, 0, 0, 0);
        position = new Position(0, 0);
    }

    /* Метод клонирования объекта объявлен как шаблонный, экземпляр класса получается
    * благодаря абстрактному методу getExample()*/
    public Shape clone() {
        Shape shape = getExample();
        shape.setFillColor(fillColor);
        shape.setPosition(position.clone());
        shape.setSize(width, height);
        return shape;
    }

    /* Возвращает экземпляр наследника класса Shape */
    public abstract Shape getExample();
    /* Возвращает булево значение если точка находится в фигуре */
    public abstract boolean inShapeArea(double x, double y);
    /* Уникальный для всех метод рисования */
    protected abstract void drawShape(GraphicsContext graphicsContext);
    /* Шаблонный метод */
    public void draw(Canvas canvas) {
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();
        if (entersByWidth(width) && entersByHeight(height))
            drawShape(canvas.getGraphicsContext2D());
    }
    /* Возвращает есть ли фигуры в этом шейпе (для группы (можно ли этого избежать?)) */
    public Container<Shape> getShapes() {
        return new Container<>();
    }
    /* Устанавливает позицию */
    public void setPosition(Position position) {
        this.position = position.clone();
    }
    public Position getPosition() {
        return position;
    }
    /* Устанавливает размер */
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }
    /* Устанавливает цвет */
    public void setFillColor(Color color) {
        fillColor = color;
    }
    /* Находится ли фигура целиком в заданном отрезке */
    public boolean entersByWidth(double width) {
        if (this.width < getMinWidth()) return false;
        return position.getX() > getWidth() / 2 && position.getX() < width - getWidth() / 2;
    }

    public boolean entersByHeight(double height) {
        if (this.height < getMinHeight()) return false;
        return position.getY() > getHeight() / 2 && position.getY() < height - getHeight() / 2 ;
    }

    public boolean entersByHeight(double height, Position position){
        if (this.height < getMinHeight()) return false;
        return position.getY() - height / 2 < this.position.getY() - getHeight() / 2 &&
                position.getY() + height / 2 > this.position.getY() + getHeight() / 2;
    }

    public boolean entersByWidth(double width, Position position){
        if (this.width < getMinWidth()) return false;
        return position.getX() - width / 2 < this.position.getX() - getWidth() / 2 &&
                position.getX() + width / 2 > this.position.getX() + getWidth() / 2;
    }

    public Color getFillColor() {
        return fillColor;
    }
    /* Для методов-посетителей */
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
    /* Возвращает текущий объект */
    public Shape getInstance() {
        return this;
    }

    /* Методы загрузки и сохранения */
    public void load(BufferedReader bufferedReader, ShapeAbstractFactory factory) throws IOException {
        //pos
        String line = check("\tx: ", bufferedReader.readLine());
        double x = Double.parseDouble(line.split(" ")[1]);
        line = check("\ty: ", bufferedReader.readLine());
        double y = Double.parseDouble(line.split(" ")[1]);
        position = new Position(x, y);
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

    public Shape getRoot(){
        return root;
    }

    public void setRoot(Shape shape){
        root = shape;
    }

    public void save(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write(getClass().getSimpleName() + "\n");
        bufferedWriter.write("\tx: " + position.getX() + "\n");
        bufferedWriter.write("\ty: " + position.getY() + "\n");
        bufferedWriter.write("\twidth: " + width + "\n");
        bufferedWriter.write("\theight: " + height + "\n");
        bufferedWriter.write("\trgb: " + (int) (fillColor.getRed() * 255) + " " + (int) (fillColor.getGreen() * 255) + " " + (int) (fillColor.getBlue() * 255) + "\n");
    }

    protected String check(String prefix, String line) {
        String[] split = line.split(" ");
        if (!(line.startsWith(prefix) && split.length >= 2))
            throw new IllegalArgumentException();
        else {
            try {
                for (int i = 1; i < split.length; i++) {
                    Double.parseDouble(split[i]);
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException();
            }
        }
        return line;
    }

    @Override
    public String toString() {
        return this.getInstance().getClass().getSimpleName();
    }
}
