package by.marmotikon.paint;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Double.min;
import static java.lang.Math.abs;

public class PaintController implements Initializable {
    @FXML
    private ComboBox<String> tool;
    @FXML
    private Spinner<Integer> brushSize;
    @FXML
    private ColorPicker brushColor;
    @FXML
    private Canvas canvas;
    @FXML
    private Canvas tempDrawCanvas;
    private GraphicsContext graphicsContext;
    private GraphicsContext tempDrawGraphicsContext;
    private final List<String> tools = List.of(
            "Round brush",
            "Butt brush",
            "Eraser",
            "Square",
            "Ellipse");
    private double startX;
    private double startY;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graphicsContext = canvas.getGraphicsContext2D();
        onClear();
        tempDrawGraphicsContext = tempDrawCanvas.getGraphicsContext2D();
        tool.setValue(tools.get(0));
        tool.getItems().addAll(tools);
    }

    private void DrawRect(MouseEvent mouseEvent, GraphicsContext graphicsContext) {
        double width = abs(mouseEvent.getX() - startX);
        double height = abs(mouseEvent.getY() - startY);
        graphicsContext.fillRect(min(startX, mouseEvent.getX()),
                                 min(startY, mouseEvent.getY()),
                                 width,
                                 height);
    }

    private void DrawOval(MouseEvent mouseEvent, GraphicsContext graphicsContext) {
        double width = abs(mouseEvent.getX() - startX);
        double height = abs(mouseEvent.getY() - startY);
        graphicsContext.fillRect(min(startX, mouseEvent.getX()),
                                 min(startY, mouseEvent.getY()),
                                 width,
                                 height);
    }

    @FXML
    protected void onSave() {
        System.err.println("Save");
    }

    @FXML
    protected void onClear() {
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void Clear(Canvas canvas) {
        canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    protected void onExit() {
        System.err.println("Exit");
    }

    public void onMousePressedListener(MouseEvent mouseEvent) {
        startX = mouseEvent.getX();
        startY = mouseEvent.getY();
        graphicsContext.setLineWidth(Double.parseDouble(brushSize.getValue().toString()));
        switch (tool.getValue()) {
            case "Round brush" -> {
                graphicsContext.setLineCap(StrokeLineCap.ROUND);
                graphicsContext.setStroke(brushColor.getValue());
            }
            case "Butt brush" -> {
                graphicsContext.setLineCap(StrokeLineCap.BUTT);
                graphicsContext.setStroke(brushColor.getValue());
            }
            case "Eraser" -> {}
            case "Square", "Ellipse" -> {
                graphicsContext.setFill(brushColor.getValue());
                tempDrawGraphicsContext.setFill(brushColor.getValue());
            }
        }
    }

    @FXML
    private void onMouseDraggedListener(MouseEvent mouseEvent){
        switch (tool.getValue()) {
            case "Round brush", "Butt brush" -> {
                graphicsContext.strokeLine(startX, startY, mouseEvent.getX(), mouseEvent.getY());
                startX = mouseEvent.getX();
                startY = mouseEvent.getY();
            }
            case "Eraser" -> {
                double size = Double.parseDouble(brushSize.getValue().toString());
                graphicsContext.clearRect(mouseEvent.getX() - size / 2,
                        mouseEvent.getY() - size / 2, size, size);
            }
            case "Square" -> {
                Clear(tempDrawCanvas);
                DrawRect(mouseEvent, tempDrawGraphicsContext);
            }
            case "Ellipse" -> {
                Clear(tempDrawCanvas);
                DrawOval(mouseEvent, tempDrawGraphicsContext);
            }
        }

    }

    public void onMouseReleaseListener(MouseEvent mouseEvent) {
        switch (tool.getValue()) {
            case "Round brush", "Butt brush" -> {
                graphicsContext.strokeLine(startX, startY, mouseEvent.getX(), mouseEvent.getY());
            }
            case "Eraser" -> {}
            case "Square" -> {
                Clear(tempDrawCanvas);
                DrawRect(mouseEvent, graphicsContext);
            }
            case "Ellipse" -> {
                Clear(tempDrawCanvas);
                DrawOval(mouseEvent, graphicsContext);
            }
        }
    }
}