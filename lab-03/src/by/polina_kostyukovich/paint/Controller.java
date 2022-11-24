package by.polina_kostyukovich.paint;

import by.polina_kostyukovich.paint.util.ColorButton;
import by.polina_kostyukovich.paint.util.ImageSerializer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.geom.Point2D;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Controller() {
        view = new View();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createColorButtons();
        groupButtons();
        createPencilWidthMenu();
        createFillMenu();
        createMainMenu();

        pencilButton.setSelected(true);
        activeTool = ActiveTool.PENCIL;

        view.setCanvas(canvas, secondLayer);
        view.clearCanvas();
        view.setColor1(color1Button.getColor());
        view.setColor2(color2Button.getColor());
    }

    @FXML
    private void handlePencilButtonPressed() {
        activeTool = ActiveTool.PENCIL;
        fillMenuButton.setDisable(true);
    }

    @FXML
    private void handleEllipseButtonPressed() {
        activeTool = ActiveTool.ELLIPSE;
        fillMenuButton.setDisable(false);
    }

    @FXML
    private void handleRectangleButtonPressed() {
        activeTool = ActiveTool.RECTANGLE;
        fillMenuButton.setDisable(false);
    }

    @FXML
    private void handleMousePressedEvent(MouseEvent event) {
        lastMouseCoords.setLocation(event.getX(), event.getY());
        firstShapePoint.setLocation(event.getX(), event.getY());
    }

    @FXML
    private void handleMouseDraggedEvent(MouseEvent event) {
        Point2D.Double newPoint = new Point2D.Double(event.getX(), event.getY());
        switch (activeTool) {
            case PENCIL -> view.drawLine(lastMouseCoords, newPoint, width);
            case ELLIPSE -> view.drawingShape(firstShapePoint, newPoint, View.Shape.ELLIPSE, fillType, width);
            case RECTANGLE -> view.drawingShape(firstShapePoint, newPoint, View.Shape.RECTANGLE, fillType, width);
        }
        lastMouseCoords.setLocation(newPoint.getX(), newPoint.getY());
    }

    @FXML
    private void handleMouseReleasedEvent(MouseEvent event) {
        Point2D.Double newPoint = new Point2D.Double(event.getX(), event.getY());
        switch (activeTool) {
            case PENCIL -> view.drawLine(lastMouseCoords, newPoint, width);
            case ELLIPSE -> view.endDrawingShape(firstShapePoint, newPoint, View.Shape.ELLIPSE, fillType, width);
            case RECTANGLE -> view.endDrawingShape(firstShapePoint, newPoint, View.Shape.RECTANGLE, fillType, width);
        }
    }

    @FXML
    private void handleMouseClickedEvent(MouseEvent event) {
        Point2D.Double currentPoint = new Point2D.Double(event.getX(), event.getY());
        view.drawLine(currentPoint, currentPoint, width);
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
    }

    private void createColorButtons() {
        String[] colorsForButtons = {
                "#000000", "#e6194b", "#f58231", "#ffe119", "#3cb44b",
                "#008080", "#4363d8", "#911eb4", "#f032e6", "#9a6324",
                "#ffffff", "#f1948a", "#ffd8b1", "#fffac8", "#bcf60c",
                "#aaffc3", "#46f0f0", "#e6beff", "#fabebe","#99a3a4"
        };
        ArrayList<ColorButton> colorButtons = new ArrayList<>(colorsForButtons.length);
        for (String colorForButton : colorsForButtons) {
            colorButtons.add(new ColorButton(Color.web(colorForButton), COLOR_BUTTON_SIZE));
        }

        for (int i = 0; i < colorButtons.size(); ++i) {
            ColorButton currentColorButton = colorButtons.get(i);
            colors.add(currentColorButton, i % (colorButtons.size() / 2), i / (colorButtons.size() / 2));
            currentColorButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (color1Button.isSelected()) {
                    color1Button.setColor(currentColorButton.getColor());
                    view.setColor1(color1Button.getColor());
                } else if (color2Button.isSelected()) {
                    color2Button.setColor(currentColorButton.getColor());
                    view.setColor2(color2Button.getColor());
                }
            });
        }
    }

    private void groupButtons() {
        ToggleGroup group1 = new ToggleGroup();
        color1Button.setToggleGroup(group1);
        color2Button.setToggleGroup(group1);

        ToggleGroup group2 = new ToggleGroup();
        pencilButton.setToggleGroup(group2);
        ellipseButton.setToggleGroup(group2);
        rectangleButton.setToggleGroup(group2);
    }

    private void createPencilWidthMenu() {
        Rectangle rect1 = new Rectangle(LINE_LENGTH_IN_MENU, 1, Color.BLACK);
        RadioMenuItem width1 = new RadioMenuItem("", rect1);
        width1.setOnAction(actionEvent -> width = 1);
        Rectangle rect3 = new Rectangle(LINE_LENGTH_IN_MENU, 3, Color.BLACK);
        RadioMenuItem width3 = new RadioMenuItem("", rect3);
        width3.setOnAction(actionEvent -> width = 3);
        Rectangle rect5 = new Rectangle(LINE_LENGTH_IN_MENU, 5, Color.BLACK);
        RadioMenuItem width5 = new RadioMenuItem("", rect5);
        width5.setOnAction(actionEvent -> width = 5);
        Rectangle rect7 = new Rectangle(LINE_LENGTH_IN_MENU, 7, Color.BLACK);
        RadioMenuItem width7 = new RadioMenuItem("", rect7);
        width7.setOnAction(actionEvent -> width = 7);

        ToggleGroup group = new ToggleGroup();
        width1.setToggleGroup(group);
        width3.setToggleGroup(group);
        width5.setToggleGroup(group);
        width7.setToggleGroup(group);
        width1.setSelected(true);
        pencilWidthMenuButton.getItems().addAll(width1, width3, width5, width7);
    }

    private void createFillMenu() {
        ToggleGroup group = new ToggleGroup();
        RadioMenuItem noFill = new RadioMenuItem("No fill");
        noFill.setOnAction(actionEvent -> fillType = FillType.NO_FILL);
        RadioMenuItem solidColor = new RadioMenuItem("Solid color");
        solidColor.setOnAction(actionEvent -> fillType = FillType.SOLID_COLOR);
        noFill.setSelected(true);
        noFill.setToggleGroup(group);
        solidColor.setToggleGroup(group);
        fillMenuButton.getItems().addAll(noFill, solidColor);
    }

    private void createMainMenu() {
        MenuItem save = new MenuItem("Save");
        save.setOnAction(actionEvent -> {
            WritableImage img = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvas.snapshot(e -> writeImage(img), null, img);
        });
        MenuItem open = new MenuItem("Open");
        open.setOnAction(actionEvent -> openImage());
        menuMenuButton.getItems().addAll(save, open);
    }

    private Void writeImage(WritableImage img) {
        try {
            File file = chooseFileToSave();
            if (file == null) {
                return null;
            }
            ImageSerializer serializer = new ImageSerializer(img);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(serializer);
            objectOutputStream.close();
        } catch (IOException | RuntimeException e) {
            showAlert("Something went wrong with saving the image :(");
        }
        return null;
    }

    private void openImage() {
        try {
            File file = chooseFileToOpen();
            if (file == null) return;
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            ImageSerializer serializer = (ImageSerializer) objectInputStream.readObject();
            objectInputStream.close();

            WritableImage img = serializer.getImage();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            view.clearCanvas();
            gc.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());
        } catch (IOException | ClassNotFoundException | RuntimeException e) {
            showAlert("Something went wrong with opening the image :(");
        }
    }

    private File chooseFileToSave() {
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showSaveDialog(primaryStage);
    }

    private File chooseFileToOpen() {
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showOpenDialog(primaryStage);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Problem...");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.setOnHidden(evt -> alert.close());
        alert.show();
    }

    private enum ActiveTool {
        PENCIL,
        ELLIPSE,
        RECTANGLE
    }

    enum FillType {
        NO_FILL,
        SOLID_COLOR
    }

    private final View view;
    private Stage primaryStage;
    private @FXML MenuButton menuMenuButton;
    private @FXML ToggleButton pencilButton;
    private @FXML ToggleButton ellipseButton;
    private @FXML ToggleButton rectangleButton;
    private @FXML MenuButton pencilWidthMenuButton;
    private @FXML MenuButton fillMenuButton;
    private @FXML ColorButton color1Button;
    private @FXML ColorButton color2Button;
    private @FXML GridPane colors;
    private @FXML Canvas canvas;
    private @FXML Canvas secondLayer;
    private ActiveTool activeTool;
    private final Point2D.Double lastMouseCoords = new Point2D.Double();
    private final Point2D.Double firstShapePoint = new Point2D.Double();
    private FillType fillType = FillType.NO_FILL;
    private double width = 1;
    private static final int COLOR_BUTTON_SIZE = 40;
    private static final double LINE_LENGTH_IN_MENU = 70;
}
