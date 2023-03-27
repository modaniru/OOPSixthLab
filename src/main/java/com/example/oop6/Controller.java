package com.example.oop6;

import com.example.oop6.models.PaintField;
import com.example.oop6.models.shapes.Circle;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.Rectangle;
import com.example.oop6.models.shapes.Triangle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private AnchorPane form;
    @FXML
    private Pane drawField;
    @FXML
    private Button btnCircle;
    @FXML
    private Button btnSquare;
    @FXML
    private Button btnTriangle;
    private PaintField paintField;

    private Shape shape;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Canvas canvas = new Canvas(drawField.getPrefWidth(), drawField.getPrefHeight());
        paintField = new PaintField(canvas);
        drawField.widthProperty().addListener((o, oldValue, newValue) -> {
            paintField.resizeWidth(newValue.intValue());
        });
        drawField.heightProperty().addListener((o, oldValue, newValue) -> {
            paintField.resizeHeight(newValue.intValue());
        });
        form.setMinWidth(700);
        form.setMinHeight(400);
        drawField.getChildren().add(canvas);
        Shape circle = new Circle(40, 60);
        btnCircle.setUserData(circle);
        btnSquare.setUserData(new Rectangle(40, 60));
        btnTriangle.setUserData(new Triangle(40, 80));
        shape = circle.clone();
        btnCircle.setOnAction(this::btnShapePress);
        btnSquare.setOnAction(this::btnShapePress);
        btnTriangle.setOnAction(this::btnShapePress);
        drawField.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void btnShapePress(ActionEvent actionEvent) {
        shape = ((Shape) ((Button) actionEvent.getSource()).getUserData());
    }

    public void clickPaintField(MouseEvent mouseEvent) {
        paintField.addOrSelectShape(shape.clone(), (int) mouseEvent.getX(), (int) mouseEvent.getY());
    }

    public void keyInFormDown(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.COMMAND) {
            paintField.setMultiplySelection(true);
        } else if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
            paintField.deleteAllSelectedShapes();
        }
    }

    public void keyInFormUp(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.COMMAND) {
            paintField.setMultiplySelection(false);
        }
    }
}
