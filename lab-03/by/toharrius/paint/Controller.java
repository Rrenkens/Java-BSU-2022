package by.toharrius.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class Controller {
    private static Controller instance = null;
    public Spinner<Integer> strokeWidthSpinner;
    private ColorChooser colorChooser;
    private PaintingTool paintingTool = PaintingTool.PENCIL;

    public FlowPane colorBoxFlow;
    public HBox root;
    public Canvas mainCanvas;
    public Controller() {
        instance = this;
    }

    public void initElements() {
        var ctx = mainCanvas.getGraphicsContext2D();
        ctx.setFill(Color.WHITE);
        ctx.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        ctx.setLineCap(StrokeLineCap.ROUND);
        colorChooser = new ColorChooser(colorBoxFlow);
        strokeWidthSpinner.setOnScroll(event -> {
            if (event.getDeltaY() < 0) {
                strokeWidthSpinner.decrement();
            } else if (event.getDeltaY() > 0) {
                strokeWidthSpinner.increment();
            }
        });
        strokeWidthSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                getDrawingContext().setLineWidth(newValue));
    }
    public void mousePressed(MouseEvent mouseEvent) {
        var ctx = getDrawingContext();
        switch (paintingTool) {
            case PENCIL -> {
                ctx.beginPath();
                ctx.moveTo(mouseEvent.getX(), mouseEvent.getY());
            }
        }
    }
    public void mouseDragged(MouseEvent mouseEvent) {
        var ctx = getDrawingContext();
        switch (paintingTool) {
            case PENCIL -> {
                ctx.lineTo(mouseEvent.getX(), mouseEvent.getY());
                ctx.stroke();
            }
        }
    }
    public void mouseReleased(MouseEvent mouseEvent) {
        var ctx = getDrawingContext();
        switch (paintingTool) {
            case PENCIL -> {
                ctx.closePath();
            }
        }
    }

    public static Controller getInstance() {
        return instance;
    }

    public static Canvas getCanvas() {
        return getInstance().mainCanvas;
    }

    public static GraphicsContext getDrawingContext() {
        return getCanvas().getGraphicsContext2D();
    }

    public void chooseStroke(MouseEvent event) {
        int value = Integer.parseInt(((Button)event.getSource()).getText());
        strokeWidthSpinner.increment(value - strokeWidthSpinner.getValue());
    }
}
