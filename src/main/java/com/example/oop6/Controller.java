package com.example.oop6;

import com.example.oop6.models.field.*;
import com.example.oop6.models.ShapeSizeModel;
import com.example.oop6.models.field.commands.Command;
import com.example.oop6.models.field.commands.DeleteCommand;
import com.example.oop6.models.field.commands.GroupCommand;
import com.example.oop6.models.field.commands.UnGroupCommand;
import com.example.oop6.models.shapes.Circle;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.Rectangle;
import com.example.oop6.models.shapes.Triangle;
import com.example.oop6.models.shapes.funcs.ChangeColorAction;
import com.example.oop6.models.shapes.funcs.MoveAction;
import com.example.oop6.models.shapes.funcs.ResizeDeltaAction;
import com.example.oop6.utils.Position;
import com.example.oop6.utils.ShapeFactory;
import com.example.oop6.utils.ShortCuts;
import com.example.oop6.utils.instruments.*;
import com.example.oop6.utils.mvc.StackOperation;
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
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.Optional;
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
    //todo сделать команды для кнопок
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnSelect;
    @FXML
    private Button btnPosition;
    @FXML
    private Button btnSize;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem miSaveAs;
    @FXML
    private MenuItem miOpen;
    @FXML
    private MenuItem miSave;
    @FXML
    private MenuItem miCreate;
    @FXML
    private TextField tfProjectName;
    private String fileName;
    private PaintField paintField;
    private ShapeSizeModel shapeSizeModel;
    private MoveAction moveAction;
    private ResizeDeltaAction resizeDeltaAction;
    private Shape shape;
    private Instrument instrument;
    private FileChooser fileChooser = new FileChooser();
    @FXML
    private TextArea report;
    private StackOperation stackOperation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac"))
            menuBar.useSystemMenuBarProperty().set(true);


        //Работа с Canvas
        Canvas canvas = new Canvas(drawField.getPrefWidth(), drawField.getPrefHeight());
        moveAction = new MoveAction((int) drawField.getPrefWidth(), (int) drawField.getPrefHeight());
        resizeDeltaAction = new ResizeDeltaAction((int) drawField.getPrefWidth(), (int) drawField.getPrefHeight());
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
        circle.setFillColor(colorPicker.getValue());
        btnCircle.setUserData(circle);
        //Всплывающие подсказки к кнопками и их шорткатам
        btnCircle.setTooltip(ShortCuts.CIRCLE.getToolTip());
        btnSquare.setTooltip(ShortCuts.RECTANGLE.getToolTip());
        btnTriangle.setTooltip(ShortCuts.TRIANGLE.getToolTip());
        btnCreate.setTooltip(ShortCuts.CREATE.getToolTip());
        btnPosition.setTooltip(ShortCuts.MOVE.getToolTip());
        btnSize.setTooltip(ShortCuts.SIZE.getToolTip());
        btnSelect.setTooltip(ShortCuts.SELECT.getToolTip());
        //Вставка информации в кнопки
        btnSquare.setUserData(new Rectangle(shapeSizeModel.getWidth(), shapeSizeModel.getHeight()));
        btnTriangle.setUserData(new Triangle(shapeSizeModel.getWidth(), shapeSizeModel.getHeight()));
        instrument = new CreateInstrument(paintField);
        btnCreate.setUserData(instrument);
        btnSelect.setUserData(new SelectionInstrument(paintField));
        btnPosition.setUserData(new MoveInstrument(paintField));
        btnSize.setUserData(new ResizeInstrument(paintField));
        btnCreate.setDisable(true);
        btnCircle.setDisable(true);
        //По дефолту круг
        shape = circle.clone();
        //Обработчики событий при нажатии на кнопку
        btnCircle.setOnAction(this::btnShapePress);
        btnSquare.setOnAction(this::btnShapePress);
        btnTriangle.setOnAction(this::btnShapePress);
        btnCanvasClear.setOnAction(this::btnClearCanvasAction);
        btnCreate.setOnAction(this::btnInstrumentPress);
        btnSelect.setOnAction(this::btnInstrumentPress);
        btnPosition.setOnAction(this::btnInstrumentPress);
        btnSize.setOnAction(this::btnInstrumentPress);
        //mvc
        stackOperation = new StackOperation();
        stackOperation.setModelChangeEvent((stackOperation) -> {
            StringBuilder sb = new StringBuilder();
            for (Command command : stackOperation) {
                sb.append(command.report()).append("\n");
            }
            report.setText(sb.toString());
        });
        //todo Если проект принадлежит файлу уже
        miSaveAs.setOnAction(actionEvent -> {
            File file = fileChooser.showOpenDialog(HelloApplication.getStage());
            //todo оповещать пользователя
            if(!file.getName().endsWith(".mdp")) return;
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                paintField.save(bufferedWriter);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        miOpen.setOnAction(actionEvent -> {
            File file = fileChooser.showOpenDialog(HelloApplication.getStage());
            System.out.println(file.getName());
            if(!file.getName().endsWith(".mdp")) return;
            StringBuilder stringBuilder = new StringBuilder(file.getName());
            stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());
            tfProjectName.setText(stringBuilder.toString());
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                paintField.load(bufferedReader, new ShapeFactory());
                stackOperation.clear();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        miSave.setOnAction(actionEvent -> {
            File file = new File(fileName + ".mdp");
            //todo сообщать путь сохранения
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                paintField.save(bufferedWriter);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        //todo мб сделать уведомление
        miCreate.setOnAction(actionEvent -> {
            paintField.clearField();
            stackOperation.clear();
            tfProjectName.setText("");
        });
        tfProjectName.textProperty().addListener((o, oldV, newV) -> {
            fileName = newV.trim();
        });
        tfProjectName.setOnAction(actionEvent -> {drawField.requestFocus();});
        //Белый цвет paintField
        drawField.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void btnInstrumentPress(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        setInstrument(button);
    }

    private void setInstrument(Button button) {
        btnCreate.setDisable(false);
        btnSize.setDisable(false);
        btnPosition.setDisable(false);
        btnSelect.setDisable(false);
        button.setDisable(true);
        instrument = (Instrument) button.getUserData();
    }

    //Обработчик колор пикера
    private void colorPickerAction(ActionEvent actionEvent) {
        paintField.actionSelectedShapes(new ChangeColorAction(colorPicker.getValue()));
        shape.setFillColor(colorPicker.getValue());
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
        moveAction.setWidth(newValue.intValue());
        paintField.resizeWidth(newValue.intValue());
    }

    public void formChangeHeightEvent(ObservableValue<? extends Number> o, Number oldValue, Number newValue) {
        moveAction.setHeight(newValue.intValue());
        paintField.resizeHeight(newValue.intValue());
    }

    //todo NEW
    public void mouseMoveInPaintFieldEvent(MouseEvent mouseEvent) {
        tCursorPosition.setText((int) mouseEvent.getX() + " " + (int) mouseEvent.getY());
    }

    //Обработчики нажатия на кнопку
    private void btnShapePress(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        btnTriangle.setDisable(false);
        btnSquare.setDisable(false);
        btnCircle.setDisable(false);
        button.setDisable(true);
        shape = ((Shape) button.getUserData());
    }

    private void btnClearCanvasAction(ActionEvent actionEvent) {
        paintField.clearField();
        stackOperation.clear();
    }

    //private boolean dragEvent = false;
    //Нажатие на форму рисования
    public void mouseDownEventInPaintField(MouseEvent mouseEvent) {
        shape.setFillColor(colorPicker.getValue());
        instrument.mouseDown(shape.clone(), (int) mouseEvent.getX(), (int) mouseEvent.getY());
    }

    public void mouseDragEventInPaintField(MouseEvent mouseEvent) {
        instrument.drag((int) mouseEvent.getX(), (int) mouseEvent.getY());
        //промежуточная на отрисовку будующей фигуры (только с контуром)
    }


    public void mouseUpEventInPaintField(MouseEvent e) {
        Optional<Command> optionalCommand = instrument.mouseUp((int) e.getX(), (int) e.getY());
        optionalCommand.ifPresent(command -> stackOperation.push(command));
    }

    //Обработчик различных нажатий клавиш
    public void keyInFormDown(KeyEvent keyEvent) {
        Command command;
        switch (keyEvent.getCode()){
            case V:
                setInstrument(btnCreate);
                break;
            case M:
                setInstrument(btnPosition);
                break;
                //todo with ctrl
            case Z:
                stackOperation.pop();
                break;
            case COMMAND:
                paintField.setMultiplySelection(true);
                break;
            case BACK_SPACE:
                command = new DeleteCommand();
                command.execute(paintField);
                stackOperation.push(command);
                break;
            case RIGHT:
                moveAction.setDx(2);
                moveAction.setDy(0);
                paintField.actionSelectedShapes(moveAction);
                break;
            case LEFT:
                moveAction.setDx(-2);
                moveAction.setDy(0);
                paintField.actionSelectedShapes(moveAction);
                break;
            case UP:
                moveAction.setDx(0);
                moveAction.setDy(-2);
                paintField.actionSelectedShapes(moveAction);
                break;
            case DOWN:
                moveAction.setDx(0);
                moveAction.setDy(2);
                paintField.actionSelectedShapes(moveAction);
                break;
            case L:
                resizeDeltaAction.setDx(2);
                resizeDeltaAction.setDy(0);
                paintField.actionSelectedShapes(resizeDeltaAction);
                break;
            case K:
                resizeDeltaAction.setDx(-2);
                resizeDeltaAction.setDy(0);
                paintField.actionSelectedShapes(resizeDeltaAction);
                break;
            case O:
                resizeDeltaAction.setDx(0);
                resizeDeltaAction.setDy(2);
                paintField.actionSelectedShapes(resizeDeltaAction);
                break;
            case I:
                resizeDeltaAction.setDx(0);
                resizeDeltaAction.setDy(-2);
                paintField.actionSelectedShapes(resizeDeltaAction);
                break;
            case G:
                command = new GroupCommand();
                command.execute(paintField);
                stackOperation.push(command);
                break;
            case F:
                command = new UnGroupCommand();
                command.execute(paintField);
                stackOperation.push(command);
                break;
            case S:
                File file = new File("save.txt");
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                    paintField.save(bufferedWriter);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
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
