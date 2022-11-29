package app;

import app.drawingtools.CircleTool;
import app.drawingtools.DrawingTool;
import app.drawingtools.PenTool;
import app.drawingtools.RectangleTool;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Controller {
    @FXML
    public BorderPane rootPane;
    @FXML
    private Slider strokeWidthSlider;
    @FXML
    private ColorPicker strokeColorPicker;
    @FXML
    private ColorPicker fillColorPicker;

    @FXML
    private Canvas mainCanvas;
    @FXML
    private Canvas bufferCanvas;

    private DrawingTool currentTool;

    private Color strokeColor;
    private Color fillColor;
    private double strokeWidth;

    private DrawingTool penTool;
    private DrawingTool rectangleTool;
    private DrawingTool circleTool;

    private Stage stage;

    public void initialize() {
        initializeDrawingParameters();
        initializeDrawingTools();
        pick(penTool);

        strokeWidthSlider.valueChangingProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                onStrokeWidthChanged();
            }
        });
        rootPane.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                stage = ((Stage) newScene.getWindow());
            }
        });
    }

    private void initializeDrawingParameters() {
        strokeColor = strokeColorPicker.getValue();
        fillColor = fillColorPicker.getValue();
        strokeWidth = strokeWidthSlider.getValue();
    }

    private void pick(DrawingTool tool) {
        currentTool = tool;
        configureCurrentTool();
    }

    private void configureCurrentTool() {
        currentTool.setStrokePaint(strokeColor);
        currentTool.setFillPaint(fillColor);
        currentTool.setStrokeWidth(strokeWidth);
    }

    private void initializeDrawingTools() {
        GraphicsContext bufferG = bufferCanvas.getGraphicsContext2D();
        GraphicsContext mainG = mainCanvas.getGraphicsContext2D();

        penTool = new PenTool(mainG, bufferG);
        rectangleTool = new RectangleTool(mainG, bufferG);
        circleTool = new CircleTool(mainG, bufferG);
    }

    @FXML
    void onMouseDragged(MouseEvent e) {
        currentTool.onMouseDragged(e);
    }

    @FXML
    void onMousePressed(MouseEvent e) {
        currentTool.onMousePressed(e);
    }

    @FXML
    void onMouseReleased(MouseEvent e) {
        currentTool.onMouseReleased(e);
    }

    @FXML
    void onStrokeColorPicked() {
        strokeColor = strokeColorPicker.getValue();
        currentTool.setStrokePaint(strokeColor);
    }

    @FXML
    void onFillColorPicked() {
        fillColor = fillColorPicker.getValue();
        currentTool.setFillPaint(fillColor);
    }

    void onStrokeWidthChanged() {
        strokeWidth = strokeWidthSlider.getValue();
        currentTool.setStrokeWidth(strokeWidth);
    }

    @FXML
    void onPenToolSelected() {
        pick(penTool);
    }

    @FXML
    void onRectangleToolSelected() {
        pick(rectangleTool);
    }

    @FXML
    void onCircleToolSelected() {
        pick(circleTool);
    }

    @FXML
    void onOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image file");
        File file = fileChooser.showOpenDialog(stage);

        if (file == null) {
            Utils.showErrorAlert("File hasn't been specified");
            return;
        }

        Image image;
        try {
            image = new Image(file.toURI().toString());
        } catch (Exception e) {
            Utils.showErrorAlert(e.getMessage());
            e.printStackTrace();
            return;
        }

        mainCanvas.getGraphicsContext2D().drawImage(image, 0, 0);
    }

    @FXML
    void onSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select file name");
        File file = fileChooser.showSaveDialog(stage);

        if (file == null) {
            Utils.showErrorAlert("File hasn't been specified");
            return;
        }

        BufferedImage image = Utils.snapshot(mainCanvas);
        try {
            ImageIO.write(image, "png", file);
        } catch (Exception e) {
            Utils.showErrorAlert(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void onClose() {
        Platform.exit();
    }
}