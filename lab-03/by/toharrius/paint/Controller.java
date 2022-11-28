package by.toharrius.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Controller {
    private static Controller instance = null;
    public FlowPane colorBoxFlow;
    private PaintingTool paintingTool = PaintingTool.PENCIL;
    public HBox root;
    public Canvas mainCanvas;
    public Controller() {
        instance = this;
    }

    public void initElements() {
        var ctx = mainCanvas.getGraphicsContext2D();
        ctx.setFill(Color.WHITE);
        ctx.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

        for (var color : new String[]{
                "white", "black", "grey",
                "red", "green", "blue",
                "yellow", "hotpink", "lightblue"
        }) {
            var box = new Label();
            box.getStyleClass().add("color-box");
            box.setStyle("-fx-background-color: " + color);
            colorBoxFlow.getChildren().add(box);
        }
    }
    public void mousePressed(MouseEvent mouseEvent) {
        switch (paintingTool) {
            case PENCIL -> {
                getDrawingContext().moveTo(mouseEvent.getX(), mouseEvent.getY());
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
}
