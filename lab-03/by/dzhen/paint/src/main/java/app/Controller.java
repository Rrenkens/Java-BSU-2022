package app;

import app.drawingtools.CircleTool;
import app.drawingtools.DrawingTool;
import app.drawingtools.PenTool;
import app.drawingtools.RectangleTool;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    public Slider strokeWidthSlider;
    @FXML
    private ColorPicker strokeColorPicker;
    @FXML
    private ColorPicker fillColorPicker;

    @FXML
    private Canvas mainCanvas;
    @FXML
    private Canvas bufferCanvas;

    DrawingTool currentTool;

    Color strokeColor;
    Color fillColor;
    double strokeWidth;

    DrawingTool penTool;
    DrawingTool rectangleTool;
    DrawingTool circleTool;

    public void initialize() {
        initializeDrawingParameters();
        initializeDrawingTools();
        pick(penTool);

        strokeWidthSlider.valueChangingProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                onStrokeWidthChanged();
            }
        });
    }

    private void initializeDrawingParameters() {
        strokeColor = strokeColorPicker.getValue();
        fillColor = fillColorPicker.getValue();
        strokeWidth = strokeWidthSlider.getValue();
    }

    private void pick(DrawingTool tool) {
        if (currentTool != null) {
            currentTool.onToolPutAside();
        }
        currentTool = tool;
        tool.onToolPicked();

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
        System.out.println("Dragged");
    }

    int pressed = 0;
    @FXML
    void onMousePressed(MouseEvent e) {
        currentTool.onMousePressed(e);
        System.out.println("Pressed" + pressed++);
    }

    @FXML
    void onMouseClicked(MouseEvent e) {
        currentTool.onMouseClicked(e);
    }

    int released = 0;
    @FXML
    void onMouseReleased(MouseEvent e) {
        currentTool.onMouseReleased(e);
        System.out.println("Released" + released++);
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
}