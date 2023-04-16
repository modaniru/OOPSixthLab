package com.example.oop6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static int FORM_MIN_WIDTH = 700;
    public static int FORM_MIN_HEIGHT = 400;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ООП 6 лаба");
        stage.setMinHeight(FORM_MIN_HEIGHT);
        stage.setMinWidth(FORM_MIN_WIDTH);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}