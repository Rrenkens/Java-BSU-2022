package by.marmotikon.paint;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import java.lang.ref.Cleaner;

public class PaintController {
    @FXML
    private Spinner brushSize;
    @FXML
    private ColorPicker brushColor;
    @FXML
    private CheckBox eraser;
    @FXML
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        ClearCanvas();
//        graphicsContext.setFill(Color.WHITE);
//        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.setOnMousePressed(mouseEvent -> {
            if (eraser.isSelected()) {
                graphicsContext.setLineCap(StrokeLineCap.SQUARE);
                graphicsContext.setStroke(Color.WHITE);
            } else {
                graphicsContext.setLineCap(StrokeLineCap.ROUND);
                graphicsContext.setStroke(brushColor.getValue());

            }
            graphicsContext.setLineWidth(Double.parseDouble(brushSize.getValue().toString()));

            graphicsContext.beginPath();
            graphicsContext.moveTo(mouseEvent.getX(), mouseEvent.getY());
            graphicsContext.stroke();
        });
        canvas.setOnMouseDragged(mouseEvent -> {
//            double size = Double.parseDouble(brushSize.getValue().toString());
//            double x = mouseEvent.getX() - size / 2;
//            double y = mouseEvent.getY() - size / 2;
//            if (eraser.isSelected()) {
//                graphicsContext.clearRect(x, y, size, size);
//            } else {
//                graphicsContext.setFill(brushColor.getValue());
//                graphicsContext.fillOval(x, y, size, size);
//            }
            graphicsContext.lineTo(mouseEvent.getX(), mouseEvent.getY());
            graphicsContext.stroke();
            graphicsContext.closePath();
            graphicsContext.beginPath();
            graphicsContext.moveTo(mouseEvent.getX(), mouseEvent.getY());
        });
        canvas.setOnMouseReleased(mouseEvent -> {
            graphicsContext.lineTo(mouseEvent.getX(), mouseEvent.getY());
            graphicsContext.stroke();
            graphicsContext.closePath();
        });
    }
    @FXML
    protected void onSave() {
        System.err.println("Save");
    }

    @FXML
    protected void onClear() {
        ClearCanvas();
    }

    private void ClearCanvas() {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    protected void onExit() {
        System.err.println("Exit");
    }
}