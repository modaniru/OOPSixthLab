package com.example.oop6.utils;

import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;

public enum ShortCuts {
    CREATE(KeyCode.V, "Создание"),
    SELECT(KeyCode.B, "Выделение"),
    MOVE(KeyCode.N, "Перемещение"),
    SIZE(KeyCode.M, "Размер"),
    COLOR(KeyCode.C, "Цвет"),
    GROUP(KeyCode.G, "Группировка"),
    UNGROUP(KeyCode.F, "Расформировать группу"),
    DELETE(KeyCode.BACK_SPACE, "Удалить"),
    STEP_BACK(KeyCode.Z, "Шаг назад"),
    STEP_FORWARD(KeyCode.X, "Шаг вперед"),
    SAVE(KeyCode.S, "Сохранить быстро"),
    CIRCLE(KeyCode.DIGIT1, "Эллипс"),
    RECTANGLE(KeyCode.DIGIT2, "Прямоугольник"),
    TRIANGLE(KeyCode.DIGIT3, "Треугольник");



    private final KeyCode keyCode;
    private final String toolName;
    ShortCuts(KeyCode keyCode, String toolName){
        this.keyCode = keyCode;
        this.toolName = toolName;
    }

    public Tooltip getToolTip(){
        return new Tooltip(toolName + " '" + keyCode.getName() + "'");
    }

    public KeyCode getKeyCode() {
        return keyCode;
    }
}
