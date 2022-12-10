package by.toharrius.paint;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Spinner;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Controller {
    private static Controller instance = null;
    public Spinner<Integer> strokeWidthSpinner;
    public ComboBox<String> lineCapChoice;
    public Canvas secondaryCanvas;
    public StackPane stackWrapCanvas;
    public MenuBar menuBar;
    public VBox sidePane;
    private ColorChooser colorChooser;
    private PaintingTool paintingTool = PaintingTool.PENCIL;
    private Point2D mousePressPos;
    private File currentFile = null;

    public FlowPane colorBoxFlow;
    public VBox root;
    public Canvas mainCanvas;
    public Controller() {
        instance = this;
    }

    public void initElements() {
        var ctx = mainCanvas.getGraphicsContext2D();
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
        Main.getScene().widthProperty().addListener((obs, old_v, new_v) -> { resizeCanvasWindowSize(); });
        Main.getScene().heightProperty().addListener((obs, old_v, new_v) -> { resizeCanvasWindowSize(); });
        sidePane.widthProperty().addListener((obs, old_v, new_v) -> { resizeCanvasWindowSize(); });
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
            case RECTANGLE, ELLIPSE -> {
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
            case ELLIPSE -> {
                clearFrontLayer();
                var r = rectByCorners(mouseEvent.getX(), mouseEvent.getY(),
                        mousePressPos.getX(), mousePressPos.getY());
                secondaryCanvas.getGraphicsContext2D().strokeArc(
                        r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight(),
                        0, 360, ArcType.CHORD);
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

    public void chooseTool(MouseEvent mouseEvent) {
        var btn = (Button)mouseEvent.getSource();
        var current = Main.getScene().lookup(".choose-tool.chosen");
        if (current != null) current.getStyleClass().remove("chosen");
        btn.getStyleClass().add("chosen");
        paintingTool = switch (btn.getText()) {
            case "Pencil" -> PaintingTool.PENCIL;
            case "Rectangle" -> PaintingTool.RECTANGLE;
            case "Ellipse" -> PaintingTool.ELLIPSE;
            default -> null;
        };
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    private String extensionOf(String filename) {
        var index = filename.lastIndexOf('.');
        return index < 0 ? "" : filename.substring(index + 1);
    }

    private void resizeCanvas(double w, double h) {
        mainCanvas.setHeight(h);
        mainCanvas.setWidth(w);
        secondaryCanvas.setHeight(h);
        secondaryCanvas.setWidth(w);
    }

    public static void resizeCanvasWindowSize() {
        var c = getInstance();
        var w = Math.max(c.root.getWidth() - c.sidePane.getWidth() - 2, c.mainCanvas.getWidth());
        var h = c.root.getHeight() - c.menuBar.getHeight() - 2;
        c.resizeCanvas(w, h);
    }

    public void saveToFile(File file) throws IOException {
        currentFile = file;
        var img = mainCanvas.snapshot(null, null);
        ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", file);
    }
    public void openFile(File file) throws IOException {
        currentFile = file;
        var img = ImageIO.read(file);
        resizeCanvas(img.getWidth(), img.getHeight());
        getMainDC().drawImage(SwingFXUtils.toFXImage(img, null), 0, 0);
    }

    public void save(ActionEvent actionEvent) throws IOException {
        if (currentFile == null) {
            saveAs(actionEvent);
        } else {
            saveToFile(currentFile);
        }
    }

    public void saveAs(ActionEvent actionEvent) throws IOException {
        var saveDialog = new FileChooser();
        saveDialog.setTitle("Save Picture");
        saveDialog.setInitialFileName("Untitled.png");
        saveToFile(saveDialog.showSaveDialog(Main.getScene().getWindow()));
    }

    public void open(ActionEvent actionEvent) throws IOException {
        var openDialog = new FileChooser();
        openDialog.setTitle("Open image");
        openDialog.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images",
                        "*.png", "*.GIF", "*.JPG", "*.JPEG"));
        openFile(openDialog.showOpenDialog(Main.getScene().getWindow()));
    }
}
