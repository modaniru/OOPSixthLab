package com.example.oop6.utils;

/* Класс представляющий собой позицию */
public class Position {
    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Клонирование позиции
    public Position clone() {
        return new Position(x, y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Находится ли текущая позиция в заданном промежутке
    public boolean inArea(Position f, Position s) {
        double minX = Math.min(f.x, s.x);
        double maxX = Math.max(f.x, s.x);
        double minY = Math.min(f.y, s.y);
        double maxY = Math.max(f.y, s.y);
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    // Изменение позиции на dx и dy
    public void changePosition(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public void minusDeltaPosition(Position position) {
        x -= position.x;
        y -= position.y;
    }

    public void plusDeltaPosition(Position position) {
        x += position.x;
        y += position.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (Double.compare(position.x, x) != 0) return false;
        return Double.compare(position.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
