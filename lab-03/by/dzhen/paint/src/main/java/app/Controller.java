package app;

import app.drawingtools.DrawingTool;
import app.drawingtools.PenTool;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    public Slider strokeWidthSlider;
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker strokeColorPicker;
    @FXML
    private ColorPicker fillColorPicker;

    DrawingTool currentTool;

    Color strokeColor;
    Color fillColor;
    double strokeWidth;

    DrawingTool penTool;

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
        penTool = new PenTool(canvas.getGraphicsContext2D());
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
    void onMouseClicked(MouseEvent e) {
        currentTool.onMouseClicked(e);
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
        System.out.println(1);
    }

    @FXML
    void onPenSelected() {
        pick(penTool);
    }
}