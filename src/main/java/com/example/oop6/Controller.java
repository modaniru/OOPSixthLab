package com.example.oop6;

import com.example.oop6.models.PaintField;
import com.example.oop6.models.ShapeSizeModel;
import com.example.oop6.models.shapes.Circle;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.Rectangle;
import com.example.oop6.models.shapes.Triangle;
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
import javafx.scene.text.Text;

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
    @FXML
    private Text tWidth;
    @FXML
    private Text tHeight;
    @FXML
    private Text tCursorPosition;
    private PaintField paintField;
    private ShapeSizeModel shapeSizeModel;

    private Shape shape;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Работа с Canvas
        Canvas canvas = new Canvas(drawField.getPrefWidth(), drawField.getPrefHeight());
        drawField.getChildren().add(canvas);
        paintField = new PaintField(canvas);
        //Обработчики событий при изменении размера окна

        drawField.widthProperty().addListener(this::formChangeWidthEvent);
        drawField.heightProperty().addListener(this::formChangeHeightEvent);
        //Моделька
        shapeSizeModel = new ShapeSizeModel(40, 40);
        shapeSizeModel.setModelChangeEvent(this::changeModel);
        widthSlider.setValue(shapeSizeModel.getWidth());
        heightSlider.setValue(shapeSizeModel.getHeight());
        tWidth.setText("width: " + shapeSizeModel.getWidth());
        tHeight.setText("height: " + shapeSizeModel.getHeight());
        //Слайдеры, для выбора размера фигуры
        heightSlider.valueProperty().addListener(this::heightSliderChangeEvent);
        widthSlider.valueProperty().addListener(this::widthSliderChangeEvent);
        //Колор пикер
        colorPicker.setOnAction(this::colorPickerAction);
        colorPicker.setValue(Color.LIGHTGRAY);
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

    //Обработчик колор пикера
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
    public void formChangeWidthEvent(ObservableValue<? extends Number> o, Number oldValue, Number newValue) {
        paintField.resizeWidth(newValue.intValue());
    }

    public void formChangeHeightEvent(ObservableValue<? extends Number> o, Number oldValue, Number newValue) {
        paintField.resizeHeight(newValue.intValue());
    }

    //todo NEW
    public void mouseMoveInPaintFieldEvent(MouseEvent mouseEvent){
        tCursorPosition.setText((int)mouseEvent.getX() + " " + (int)mouseEvent.getY());
    }

    //Обработчики нажатия на кнопку
    private void btnShapePress(ActionEvent actionEvent) {
        shape = ((Shape) ((Button) actionEvent.getSource()).getUserData());
    }

    private void btnClearCanvasAction(ActionEvent actionEvent) {
        paintField.clearField();
    }

    //private boolean dragEvent = false;
    //Нажатие на форму рисования
    public void mouseDownEventInPaintField(MouseEvent mouseEvent) {
        shape.setPosition((int) mouseEvent.getX(), (int) mouseEvent.getY());
        shape.setSize(shapeSizeModel.getWidth(), shapeSizeModel.getHeight());
    }

    public void mouseDragEventInPaintField(MouseEvent mouseEvent){
        shape.setSize((int) (Math.abs(mouseEvent.getX() - shape.getX()) * 2), (int) (Math.abs(mouseEvent.getY() - shape.getY() ) * 2));
        shape.changeSelection();
        paintField.drawTempShape(shape);
        shape.changeSelection();
        //промежуточная на отрисовку будующей фигуры (только с контуром)
    }

    public void mouseUpEventInPaintField(MouseEvent mouseEvent){
        Shape clone = shape.clone();
        clone.setFillColor(colorPicker.getValue());
        clone.setPosition(shape.getX(), shape.getY());
        if(!(clone.getX() == mouseEvent.getX() && clone.getY() == mouseEvent.getY())){
            clone.setSize((int) (Math.abs(mouseEvent.getX() - clone.getX()) * 2), (int) (Math.abs(mouseEvent.getY() - clone.getY() ) * 2));
        }
        else{
            clone.setSize(shapeSizeModel.getWidth(), shapeSizeModel.getHeight());
        }
        paintField.addOrSelectShape(clone);
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
        } else if (keyEvent.getCode() == KeyCode.K) {
            paintField.resizeDeltaSelectedShapes(-2, 0);
        } else if (keyEvent.getCode() == KeyCode.O) {
            paintField.resizeDeltaSelectedShapes(0, 2);
        } else if (keyEvent.getCode() == KeyCode.I) {
            paintField.resizeDeltaSelectedShapes(0, -2);
        } else if (keyEvent.getCode() == KeyCode.G) {
            paintField.groupSelectedShapes();
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
        tWidth.setText("width: " + x);
        tHeight.setText("height: " + y);
        paintField.resizeSelectedShapes(x, y);
    }
}
