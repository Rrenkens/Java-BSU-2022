package by.marmotikon.paint;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class PaintController {
    @FXML
    private Spinner brushSize;
    @FXML
    private ColorPicker brushColor;
    @FXML
    private CheckBox eraser;
    @FXML
    private Canvas canvas;

    public void initialize() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(mouseEvent -> {
            double size = Double.parseDouble(brushSize.getValue().toString());
            double x = mouseEvent.getX() - size / 2;
            double y = mouseEvent.getY() - size / 2;
            if (eraser.isSelected()) {
                graphicsContext.clearRect(x, y, size, size);
            } else {
                graphicsContext.setFill(brushColor.getValue());
                graphicsContext.fillOval(x, y, size, size);
            }
        });
    }
    @FXML
    protected void onSave() {
        System.err.println("Save");
    }

    @FXML
    protected void onExit() {
        System.err.println("Exit");
    }
}