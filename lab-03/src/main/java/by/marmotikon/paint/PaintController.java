package by.marmotikon.paint;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

import java.net.URL;
import java.util.List;
import java.util.Map;
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
    private double startX;
    private double startY;
    private final Color canvasBackgroundColor = Color.WHITE;

    private enum Tools {
        ROUND_BRUSH,
        BUTT_BRUSH,
        ERASER,
        RECTANGLE,
        OVAL
    }
    private final List<String> toolNames = List.of(
            "Round brush",
            "Butt brush",
            "Rectangle",
            "Oval",
            "Eraser");
    private final Map<String, Tools> toolByName = Map.of(
            toolNames.get(0), Tools.ROUND_BRUSH,
            toolNames.get(1), Tools.BUTT_BRUSH,
            toolNames.get(2), Tools.RECTANGLE,
            toolNames.get(3), Tools.OVAL,
            toolNames.get(4), Tools.ERASER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graphicsContext = canvas.getGraphicsContext2D();
        tempDrawGraphicsContext = tempDrawCanvas.getGraphicsContext2D();
        tool.setValue(toolNames.get(0));
        tool.getItems().addAll(toolNames);
        onClear();
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
        graphicsContext.fillOval(min(startX, mouseEvent.getX()),
                                 min(startY, mouseEvent.getY()),
                                 width,
                                 height);
    }

    private void ClearTempCanvas() {
        tempDrawGraphicsContext.clearRect(0,0, tempDrawCanvas.getWidth(), tempDrawCanvas.getHeight());
    }

    @FXML
    protected void onSave() {
        System.err.println("Save");
//        try {
//            Image snapshot = canvas.snapshot(null, null);
//            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", )
//        }
    }

    @FXML
    protected void onClear() {
        canvas.getGraphicsContext2D().setFill(canvasBackgroundColor);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    protected void onExit() {
        System.err.println("Exit");
    }

    @FXML
    public void onMousePressedListener(MouseEvent mouseEvent) {
        startX = mouseEvent.getX();
        startY = mouseEvent.getY();
        graphicsContext.setLineWidth(Double.parseDouble(brushSize.getValue().toString()));
        switch (toolByName.get(tool.getValue())) {
            case ROUND_BRUSH -> {
                graphicsContext.setLineCap(StrokeLineCap.ROUND);
                graphicsContext.setStroke(brushColor.getValue());
            }
            case BUTT_BRUSH -> {
                graphicsContext.setLineCap(StrokeLineCap.BUTT);
                graphicsContext.setStroke(brushColor.getValue());
            }
            case ERASER -> {
                graphicsContext.setFill(canvasBackgroundColor);
                double size = Double.parseDouble(brushSize.getValue().toString());
                graphicsContext.fillRect(mouseEvent.getX() - size / 2,
                        mouseEvent.getY() - size / 2, size, size);
            }
            case RECTANGLE, OVAL -> {
                graphicsContext.setFill(brushColor.getValue());
                tempDrawGraphicsContext.setFill(brushColor.getValue());
            }
        }
    }

    @FXML
    private void onMouseDraggedListener(MouseEvent mouseEvent){
        switch (toolByName.get(tool.getValue())) {
            case ROUND_BRUSH, BUTT_BRUSH -> {
                graphicsContext.strokeLine(startX, startY, mouseEvent.getX(), mouseEvent.getY());
                startX = mouseEvent.getX();
                startY = mouseEvent.getY();
            }
            case ERASER -> {
                double size = Double.parseDouble(brushSize.getValue().toString());
                graphicsContext.fillRect(mouseEvent.getX() - size / 2,
                        mouseEvent.getY() - size / 2, size, size);
            }
            case RECTANGLE -> {
                ClearTempCanvas();
                DrawRect(mouseEvent, tempDrawGraphicsContext);
            }
            case OVAL -> {
                ClearTempCanvas();
                DrawOval(mouseEvent, tempDrawGraphicsContext);
            }
        }

    }

    @FXML
    public void onMouseReleaseListener(MouseEvent mouseEvent) {
        switch (toolByName.get(tool.getValue())) {
            case ROUND_BRUSH, BUTT_BRUSH -> {
                graphicsContext.strokeLine(startX, startY, mouseEvent.getX(), mouseEvent.getY());
            }
            case ERASER -> {}
            case RECTANGLE -> {
                ClearTempCanvas();
                DrawRect(mouseEvent, graphicsContext);
            }
            case OVAL -> {
                ClearTempCanvas();
                DrawOval(mouseEvent, graphicsContext);
            }
        }
    }
}