package com.example.oop6;

import com.example.oop6.models.PaintField;
import com.example.oop6.models.ShapeSizeModel;
import com.example.oop6.models.shapes.Circle;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.Rectangle;
import com.example.oop6.models.shapes.Triangle;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
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
    @FXML
    private Button btnCanvasClear;
    @FXML
    private Slider widthSlider;
    @FXML
    private Slider heightSlider;
    @FXML
    private ColorPicker colorPicker;
    private PaintField paintField;
    private ShapeSizeModel shapeSizeModel;

    private Shape shape;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Работа с Canvas
        Canvas canvas = new Canvas(drawField.getPrefWidth(), drawField.getPrefHeight());
        paintField = new PaintField(canvas);
        drawField.getChildren().add(canvas);
        //Обработчики событий при изменении размера окна
        drawField.widthProperty().addListener(this::formChangeWidthEvent);
        drawField.heightProperty().addListener(this::formChangeHeightEvent);
        //Моделька
        shapeSizeModel = new ShapeSizeModel(40, 40);
        shapeSizeModel.setModelChangeEvent(this::changeModel);
        widthSlider.setValue(shapeSizeModel.getWidth());
        heightSlider.setValue(shapeSizeModel.getHeight());
        //Слайдеры, для выбора размера фигуры
        heightSlider.valueProperty().addListener(this::heightSliderChangeEvent);
        widthSlider.valueProperty().addListener(this::widthSliderChangeEvent);
        //Колор пикер
        colorPicker.setOnAction(this::colorPickerAction);
        //Вставка фигур в кнопки
        Shape circle = new Circle(shapeSizeModel.getWidth(), shapeSizeModel.getHeight());
        btnCircle.setUserData(circle);
        btnSquare.setUserData(new Rectangle(shapeSizeModel.getWidth(), shapeSizeModel.getHeight()));
        btnTriangle.setUserData(new Triangle(shapeSizeModel.getWidth(), shapeSizeModel.getHeight()));
        //По дефолту круг
        shape = circle.clone();
        //Обработчики событий при нажатии на кнопку
        btnCircle.setOnAction(this::btnShapePress);
        btnSquare.setOnAction(this::btnShapePress);
        btnTriangle.setOnAction(this::btnShapePress);
        btnCanvasClear.setOnAction(this::btnClearCanvasAction);
        //Белый цвет paintField
        drawField.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    //Обработчик колор пиркера
    private void colorPickerAction(ActionEvent actionEvent) {
        paintField.changeColorSelectedShapes(colorPicker.getValue());
    }

    //Обработчики слайдеров
    private void heightSliderChangeEvent(ObservableValue<? extends Number> o, Number oldValue, Number newValue) {
        shapeSizeModel.setHeight(newValue.intValue());
    }

    private void widthSliderChangeEvent(ObservableValue<? extends Number> o, Number oldValue, Number newValue) {
        shapeSizeModel.setWidth(newValue.intValue());
    }

    //Обработчики изменения размера окна
    private void formChangeWidthEvent(ObservableValue<? extends Number> o, Number oldValue, Number newValue) {
        paintField.resizeWidth(newValue.intValue());
    }

    private void formChangeHeightEvent(ObservableValue<? extends Number> o, Number oldValue, Number newValue) {
        paintField.resizeHeight(newValue.intValue());
    }

    //Обработчики нажатия на кнопку
    private void btnShapePress(ActionEvent actionEvent) {
        shape = ((Shape) ((Button) actionEvent.getSource()).getUserData());
    }

    private void btnClearCanvasAction(ActionEvent actionEvent) {
        paintField.clearField();
    }

    //Нажатие на форму рисования
    public void clickPaintField(MouseEvent mouseEvent) {
        Shape shapeClone = shape.clone();
        shapeClone.setHeight(shapeSizeModel.getHeight());
        shapeClone.setWidth(shapeSizeModel.getWidth());
        shapeClone.setFillColor(colorPicker.getValue());
        paintField.addOrSelectShape(shapeClone, (int) mouseEvent.getX(), (int) mouseEvent.getY());
    }

    //Обработчик различных нажатий клавиш
    public void keyInFormDown(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.COMMAND) {
            paintField.setMultiplySelection(true);
        } else if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
            paintField.deleteAllSelectedShapes();
        } else if (keyEvent.getCode() == KeyCode.RIGHT) {
            paintField.moveAllSelectedShapes(2, 0);
        } else if (keyEvent.getCode() == KeyCode.LEFT) {
            paintField.moveAllSelectedShapes(-2, 0);
        } else if (keyEvent.getCode() == KeyCode.UP) {
            paintField.moveAllSelectedShapes(0, -2);
        } else if (keyEvent.getCode() == KeyCode.DOWN) {
            paintField.moveAllSelectedShapes(0, 2);
        } else if (keyEvent.getCode() == KeyCode.L) {
            paintField.resizeDeltaSelectedShapes(2, 0);
        }else if (keyEvent.getCode() == KeyCode.K) {
            paintField.resizeDeltaSelectedShapes(-2, 0);
        }else if (keyEvent.getCode() == KeyCode.O) {
            paintField.resizeDeltaSelectedShapes(0, 2);
        }else if (keyEvent.getCode() == KeyCode.I) {
            paintField.resizeDeltaSelectedShapes(0, -2);
        }
    }

    //Множественное выделение фигур
    public void keyInFormUp(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.COMMAND) {
            paintField.setMultiplySelection(false);
        }
    }

    //Model change event
    private void changeModel(int x, int y) {
        paintField.resizeSelectedShapes(x, y);
    }
}
