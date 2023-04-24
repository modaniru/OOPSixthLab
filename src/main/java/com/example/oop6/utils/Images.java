package com.example.oop6.utils;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

//todo
/* Перечисление всех иконок (пока не использовал)*/
public enum Images {
    CREATE(new Object() {
        Image evaluate() {
            try {
                return new Image(new FileInputStream(new File("src/main/resources/com/example/oop6/icons/cursor.png")));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }.evaluate()),
    SELECT(new Object() {
        Image evaluate() {
            try {
                return new Image(new FileInputStream(new File("src/main/resources/com/example/oop6/icons/selection.png")));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }.evaluate()),
    MOVE(new Object() {
        Image evaluate() {
            try {
                return new Image(new FileInputStream(new File("src/main/resources/com/example/oop6/icons/expand-arrows.png")));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }.evaluate()),
    SIZE(new Object() {
        Image evaluate() {
            try {
                return new Image(new FileInputStream(new File("src/main/resources/com/example/oop6/icons/expand.png")));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }.evaluate()),
    COLOR(new Object() {
        Image evaluate() {
            try {
                return new Image(new FileInputStream(new File("src/main/resources/com/example/oop6/icons/cursor.png")));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }.evaluate());
    private final Image image;

    Images(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }
}
