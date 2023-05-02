package com.example.oop6;

import com.example.oop6.models.TreeViewObserver;
import com.example.oop6.models.field.PaintField;
import com.example.oop6.models.field.commands.*;
import com.example.oop6.models.shapes.Circle;
import com.example.oop6.models.shapes.Rectangle;
import com.example.oop6.models.shapes.Shape;
import com.example.oop6.models.shapes.Triangle;
import com.example.oop6.utils.Container;
import com.example.oop6.utils.Position;
import com.example.oop6.utils.ShapeFactory;
import com.example.oop6.utils.ShortCuts;
import com.example.oop6.utils.information.Information;
import com.example.oop6.utils.instruments.*;
import com.example.oop6.utils.mvc.StackOperation;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    /* --- Кнопки ---*/
    @FXML
    private Button btnCircle;
    @FXML
    private Button btnSquare;
    @FXML
    private Button btnTriangle;
    // Список всех кнопок с шейпами
    List<Button> shapeButtons = new ArrayList<>();
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnSelect;
    @FXML
    private Button btnPosition;
    @FXML
    private Button btnSize;
    // Список всех кнопок с инструментами
    List<Button> instrumentsButtons = new ArrayList<>();
    /* --- text views --- */
    @FXML
    TreeView<Shape> treeViewShapes;
    /* --- text fields --- */
    @FXML
    private TextField tfProjectName;
    /* --- menu items ---*/
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
    /* --- color picker --- */
    @FXML
    private ColorPicker colorPicker;
    /* --- pane --- */
    @FXML
    private Pane drawField;
    /* --- other classes --- */
    // Имя текущего файла
    private String fileName;
    // Объект поля для рисования
    private PaintField paintField;
    // Текущая фигура
    private Shape shape;
    // Текущий инструмент
    private Instrument instrument;
    // File dialog
    private final FileChooser fileChooser = new FileChooser();
    // Моделька стека операций
    private StackOperation stackOperation;
    @FXML
    private ListView<Command> lvReport;
    private TreeViewObserver treeViewObserver;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* Создание класса обертки стека операций */
        stackOperation = new StackOperation();
        stackOperation.setModelChangeEvent((stackOperation) -> {
            lvReport.setCellFactory(e -> new ListCell<>(){
                @Override
                protected void updateItem(Command command, boolean b) {
                    setStyle("-fx-background-color: " + " #363e52");
                    if(command == null) return;
                    super.updateItem(command, b);
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(1);
                    ImageView imageView = new ImageView(command.getImage());
                    imageView.setFitWidth(10);
                    imageView.setFitHeight(10);
                    imageView.setEffect(colorAdjust);
                    setGraphic(imageView);
                }
            });
            lvReport.setItems(stackOperation);
        });
        /* Заполнение массива кнопок с шейпами */
        shapeButtons.add(btnCircle);
        shapeButtons.add(btnTriangle);
        shapeButtons.add(btnSquare);
        /* Заполнение массива кнопок с инструментами */
        instrumentsButtons.add(btnSize);
        instrumentsButtons.add(btnSelect);
        instrumentsButtons.add(btnPosition);
        instrumentsButtons.add(btnCreate);
        /* Установка Menu Bar */
        final String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac")) {
            menuBar.useSystemMenuBarProperty().set(true);
        }
        /* Создание paintField с вставкой Canvas */
        Canvas canvas = new Canvas(drawField.getPrefWidth(), drawField.getPrefHeight());
        drawField.getChildren().add(canvas);
        paintField = new PaintField(canvas);
        /*TreeViewObserver*/
        treeViewObserver = new TreeViewObserver(treeViewShapes);
        treeViewShapes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(!treeViewObserver.isNotified()){
                        if(newValue != null){
                            Container<Shape> shapes = new Container<>();
                            for (TreeItem<Shape> selectedItem : treeViewShapes.getSelectionModel().getSelectedItems()) {
                                shapes.add(selectedItem.getValue());
                            }
                            Command command = new SelectShapeCommand(shapes);
                            command.execute(paintField);
                            stackOperation.push(command);
                        }
                        else{
                            Command command = new UnSelectShapeCommand(oldValue.getValue());
                            command.execute(paintField);
                            stackOperation.push(command);
                        }
                    }
                }
        );
        paintField.addObserver(treeViewObserver);
        treeViewShapes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        /* Установка слушателей для формы */
        drawField.widthProperty().addListener(this::formChangeWidthEvent);
        drawField.heightProperty().addListener(this::formChangeHeightEvent);
        /* Установка слушателя для colorPicker */
        colorPicker.setOnAction(this::colorPickerAction);
        colorPicker.setValue(Color.LIGHTGRAY);
        /* Вставка в кнопки пользовательскую информацию (Фигуры) */
        Shape circle = new Circle(Information.DEFAULT_WIDTH, Information.DEFAULT_HEIGHT);
        //Установка текущего цвета colorPicker'a
        circle.setFillColor(colorPicker.getValue());
        //По умолчанию выбран эллипс
        shape = circle.clone();
        btnCircle.setDisable(true);
        btnCircle.setUserData(circle);
        btnSquare.setUserData(new Rectangle(Information.DEFAULT_WIDTH, Information.DEFAULT_HEIGHT));
        btnTriangle.setUserData(new Triangle(Information.DEFAULT_WIDTH, Information.DEFAULT_HEIGHT));
        /* Установка подсказок всем кнопкам */
        /* --- Сделал некоторые шорткаты через enum, чтобы изменяя информацию о них,
        не перекомпилировать этот класс --- */
        btnCircle.setTooltip(ShortCuts.CIRCLE.getToolTip());
        btnSquare.setTooltip(ShortCuts.RECTANGLE.getToolTip());
        btnTriangle.setTooltip(ShortCuts.TRIANGLE.getToolTip());
        btnCreate.setTooltip(ShortCuts.CREATE.getToolTip());
        btnPosition.setTooltip(ShortCuts.MOVE.getToolTip());
        btnSize.setTooltip(ShortCuts.SIZE.getToolTip());
        btnSelect.setTooltip(ShortCuts.SELECT.getToolTip());
        /* Вставка в кнопки пользовательскую информацию (Инструменты) */
        // Инструмент по умолчанию
        instrument = new CreateInstrument(paintField);
        btnCreate.setUserData(instrument);
        btnCreate.setDisable(true);
        btnSelect.setUserData(new SelectionInstrument(paintField));
        btnPosition.setUserData(new MoveInstrument(paintField));
        btnSize.setUserData(new ResizeInstrument(paintField));
        /* Обработчик нажатия на кнопку 'Сохранить как' */
        miSaveAs.setOnAction(actionEvent -> {
            File file = getFileFromDialog();
            if (file == null) return;
            if (!file.getName().endsWith(Information.EXTENSION)) return;
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                paintField.save(bufferedWriter);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        /* Обработчик нажатия на кнопку 'Открыть' */
        miOpen.setOnAction(actionEvent -> {
            File file = getFileFromDialog();
            if (file == null) return;
            if (!file.getName().endsWith(Information.EXTENSION)) return;
            StringBuilder stringBuilder = new StringBuilder(file.getName());
            stringBuilder.delete(stringBuilder.length() - Information.EXTENSION.length(), stringBuilder.length());
            tfProjectName.setText(stringBuilder.toString());
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                paintField.load(bufferedReader, new ShapeFactory());
                stackOperation.clear();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (IllegalArgumentException e) {
                paintField.clearField();
                new Alert(Alert.AlertType.ERROR, "Ошибка загрузки.").show();
            }
        });
        /* Обработчик нажатия на кнопку 'Сохранить' */
        miSave.setOnAction(actionEvent -> {
            File file = new File(fileName + Information.EXTENSION);
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                paintField.save(bufferedWriter);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        /* Обработчик нажатия на кнопку 'Создать' */
        miCreate.setOnAction(actionEvent -> {
            paintField.clearField();
            stackOperation.clear();
            tfProjectName.setText("");
        });
        /* Обработчик изменения значения TextField */
        tfProjectName.textProperty().addListener((o, oldV, newV) -> {
            fileName = newV.trim();
        });
        /* Обработчик нажатия Enter в TextField */
        tfProjectName.setOnAction(actionEvent -> {
            drawField.requestFocus();
        });
    }

    /* Возвращает файл из диалога */
    private File getFileFromDialog() {
        return fileChooser.showOpenDialog(HelloApplication.getStage());
    }

    /* Обработчик выбора цвета */
    private void colorPickerAction(ActionEvent actionEvent) {
        ColorCommand command = new ColorCommand(colorPicker.getValue());
        command.execute(paintField);
        stackOperation.push(command);
        shape.setFillColor(colorPicker.getValue());
    }

    /* Обработчик изменения ширины формы */
    public void formChangeWidthEvent(ObservableValue<? extends Number> o, Number oldValue, Number newValue) {
        paintField.resizeWidth(newValue.intValue());
    }

    /* Обработчик изменения высоты формы */

    public void formChangeHeightEvent(ObservableValue<? extends Number> o, Number oldValue, Number newValue) {
        paintField.resizeHeight(newValue.intValue());
    }

    /* Обработка нажатия на кнопку с инструментом */
    public void btnInstrumentPress(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        setInstrument(button);
    }

    /* Установка инструмента в активное состояние */
    private void setInstrument(Button button) {
        for (Button instrumentButton : instrumentsButtons) {
            instrumentButton.setDisable(false);
        }
        button.setDisable(true);
        instrument = (Instrument) button.getUserData();
    }

    /* Обработка нажатия кнопки с шейпом */
    public void btnShapePress(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        setShape(button);
    }

    private void setShape(Button button) {
        for (Button shapeButton : shapeButtons) {
            shapeButton.setDisable(false);
        }
        button.setDisable(true);
        shape = ((Shape) button.getUserData());
        shape.setFillColor(colorPicker.getValue());
    }

    /* Очистка формы */
    public void btnClearCanvasAction(ActionEvent actionEvent) {
        paintField.clearField();
        stackOperation.clear();
    }

    /* Нажатие клавиши мыши */
    public void mouseDownEventInPaintField(MouseEvent e) {
        instrument.mouseDown(shape.clone(), new Position(e.getX(), e.getY()));
    }

    /* Удержание клавиши мыши*/
    public void mouseDragEventInPaintField(MouseEvent e) {
        instrument.drag(new Position(e.getX(), e.getY()));
    }

    /* Отжатие клавиши мыши */
    public void mouseUpEventInPaintField(MouseEvent e) {
        Optional<Command> optionalCommand = instrument.mouseUp(new Position(e.getX(), e.getY()));
        optionalCommand.ifPresent(command -> stackOperation.push(command));
    }

    /* Обработчик нажатий клавиш */
    public void keyInFormDown(KeyEvent keyEvent) {
        Command command = null;
        KeyCode keyCode = keyEvent.getCode();
        if (keyCode.equals(ShortCuts.CREATE.getKeyCode()))
            setInstrument(btnCreate);
        else if (keyCode.equals(ShortCuts.MOVE.getKeyCode()))
            setInstrument(btnPosition);
        else if (keyCode.equals(ShortCuts.SIZE.getKeyCode()))
            setInstrument(btnSize);
        else if (keyCode.equals(ShortCuts.SELECT.getKeyCode()))
            setInstrument(btnSelect);
        else if (keyCode.equals(ShortCuts.STEP_BACK.getKeyCode())) {
            Optional<Command> op = stackOperation.popUnExecute();
        } else if (keyCode.equals(ShortCuts.STEP_FORWARD.getKeyCode())) {
            //todo
        } else if (keyCode.equals(KeyCode.COMMAND))
            paintField.setMultiplySelection(true);
        else if (keyCode.equals(ShortCuts.DELETE.getKeyCode())) {
            command = new DeleteCommand();
            command.execute(paintField);
            stackOperation.push(command);
        } else if (keyCode.equals(ShortCuts.GROUP.getKeyCode())) {
            if(paintField.getAllSelectedShapes().size() <= 1) return;
            command = new GroupCommand();
            command.execute(paintField);
            stackOperation.push(command);
        } else if (keyCode.equals(ShortCuts.UNGROUP.getKeyCode())) {
            command = new UnGroupCommand();
            command.execute(paintField);
            stackOperation.push(command);
        } else if (keyCode.equals(ShortCuts.SAVE.getKeyCode())) {
            File file = new File("save.mdp");
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                paintField.save(bufferedWriter);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (keyCode.equals(ShortCuts.CIRCLE.getKeyCode()))
            setShape(btnCircle);
        else if (keyCode.equals(ShortCuts.RECTANGLE.getKeyCode()))
            setShape(btnSquare);
        else if (keyCode.equals(ShortCuts.TRIANGLE.getKeyCode()))
            setShape(btnTriangle);
        else {
            switch (keyEvent.getCode()) {
                case RIGHT -> command = new MoveCommand(new Position(Information.DELTA, 0));
                case LEFT -> command = new MoveCommand(new Position(-Information.DELTA, 0));
                case UP -> command = new MoveCommand(new Position(0, -Information.DELTA));
                case DOWN -> command = new MoveCommand(new Position(0, Information.DELTA));
                case L -> command = new ResizeCommand(Information.DELTA, 0);
                case K -> command = new ResizeCommand(-Information.DELTA, 0);
                case O -> command = new ResizeCommand(0, Information.DELTA);
                case I -> command = new ResizeCommand(0, -Information.DELTA);
            }
            if (command != null) {
                command.execute(paintField);
                stackOperation.push(command);
            }
        }
    }

    /* Обработчик отжатия клавиш */
    public void keyInFormUp(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.COMMAND) {
            paintField.setMultiplySelection(false);
        }
    }
}
