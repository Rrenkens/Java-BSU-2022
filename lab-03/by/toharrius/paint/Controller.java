package by.toharrius.paint;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class Controller {
    private static Controller instance = null;
    public Spinner<Integer> strokeWidthSpinner;
    public ComboBox<String> lineCapChoice;
    public Canvas secondaryCanvas;
    private ColorChooser colorChooser;
    private PaintingTool paintingTool = PaintingTool.RECTANGLE;
    private Point2D mousePressPos;

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
        colorChooser = new ColorChooser(colorBoxFlow);
        strokeWidthSpinner.setOnScroll(event -> {
            if (event.getDeltaY() < 0) {
                strokeWidthSpinner.decrement();
            } else if (event.getDeltaY() > 0) {
                strokeWidthSpinner.increment();
            }
        });
        strokeWidthSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            getMainDC().setLineWidth(newValue);
            getSecondaryDC().setLineWidth(newValue);
        });
        lineCapChoice.getItems().setAll("Round", "Butt", "Square");
        lineCapChoice.getSelectionModel()
                .selectedItemProperty()
                .addListener((selected, old_val, new_val) -> {
            getMainDC().setLineCap(switch (new_val) {
                case "Round" -> StrokeLineCap.ROUND;
                case "Butt" -> StrokeLineCap.BUTT;
                case "Square" -> StrokeLineCap.SQUARE;
                default -> null;
            });
        });
        lineCapChoice.getSelectionModel().select(0);
    }
    private void recordMousePress(MouseEvent e) {
        mousePressPos = new Point2D(e.getX(), e.getY());
    }
    private void clearFrontLayer() {
        secondaryCanvas.getGraphicsContext2D().clearRect(
                0, 0, secondaryCanvas.getWidth(), secondaryCanvas.getHeight()
        );
    }
    public void mousePressed(MouseEvent event) {
        var ctx = getMainDC();
        switch (paintingTool) {
            case PENCIL -> {
                ctx.beginPath();
                ctx.moveTo(event.getX(), event.getY());
            }
            case RECTANGLE -> {
                recordMousePress(event);
            }
        }
    }
    private Rectangle2D rectByCorners(double ax, double ay, double bx, double by) {
        if (ax > bx) { double t = ax; ax = bx; bx = t; }
        if (ay > by) { double t = ay; ay = by; by = t; }
        double border = getMainDC().getLineWidth();
        double dx = Math.max(0, Math.min(border, bx - ax - 1) / 2);
        double dy = Math.max(0, Math.min(border, by - ay - 1) / 2);
        return new Rectangle2D(ax + dx, ay + dy,
                (bx - ax) - dx * 2, (by - ay) - dy * 2);
    }
    public void mouseDragged(MouseEvent mouseEvent) {
        var ctx = getMainDC();
        switch (paintingTool) {
            case PENCIL -> {
                ctx.lineTo(mouseEvent.getX(), mouseEvent.getY());
                ctx.stroke();
            }
            case RECTANGLE -> {
                clearFrontLayer();
                var r = rectByCorners(mouseEvent.getX(), mouseEvent.getY(),
                        mousePressPos.getX(), mousePressPos.getY());
                secondaryCanvas.getGraphicsContext2D().strokeRect(
                        r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());
            }
        }
    }
    public void mouseReleased(MouseEvent mouseEvent) {
        var ctx = getMainDC();
        switch (paintingTool) {
            case PENCIL -> {
                ctx.closePath();
            }
            case RECTANGLE, ELLIPSE -> {
                var pars = new SnapshotParameters();
                pars.setFill(Color.TRANSPARENT);
                WritableImage image = secondaryCanvas.snapshot(pars, null);
                getMainDC().drawImage(image, 0, 0);
                clearFrontLayer();
            }
        }
    }

    public static Controller getInstance() {
        return instance;
    }

    public static GraphicsContext getMainDC() {
        return getInstance().mainCanvas.getGraphicsContext2D();
    }

    public static GraphicsContext getSecondaryDC() {
        return getInstance().secondaryCanvas.getGraphicsContext2D();
    }

    public void chooseStroke(MouseEvent event) {
        int value = Integer.parseInt(((Button)event.getSource()).getText());
        strokeWidthSpinner.increment(value - strokeWidthSpinner.getValue());
    }
}
