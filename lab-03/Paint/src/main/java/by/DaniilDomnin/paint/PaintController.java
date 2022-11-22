package by.DaniilDomnin.paint;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;


import javax.imageio.ImageIO;
import java.io.File;

import java.awt.image.RenderedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PaintController {

    PaintController() {
        circle.setMinWidth(Constants.CircleWidth);
        circle.setToggleGroup(tools);
        rectangle.setMinWidth(Constants.RectangleWidth);
        rectangle.setToggleGroup(tools);

        menu.getItems().add(save);
        menu.getItems().add(open);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);

        buttons_layout.setAlignment(Pos.CENTER);
        buttons_layout.setSpacing(Constants.ButtonLayoutSpacing);
        buttons_layout.getChildren().addAll(brushSize, colorPicker);
        buttons_layout.getChildren().add(rectangle);
        buttons_layout.getChildren().add(circle);
        toolBar.getItems().add(buttons_layout);

        main_layout.getChildren().addAll(menuBar, toolBar);
        main_layout.setAlignment(Pos.CENTER);

        pane.prefHeight(Constants.PaneHeight);
        pane.prefWidth(Constants.PaneWidth);
        pane.setTop(main_layout);

        pane.setCenter(canvas);

        Initialize();
        setSave();
        setOpen();
    }

    public void Initialize () {
        GraphicsContext g = canvas.getGraphicsContext2D();
        Rectangle rect = new Rectangle();
        Circle cir = new Circle();

        canvas.setOnMousePressed(e -> {
            g.setLineWidth( Double.parseDouble(brushSize.getText()));
            g.setStroke(colorPicker.getValue());
            if (circle.isSelected()) {
                cir.setCenterX(e.getX());
                cir.setCenterY(e.getY());
            }
            if (rectangle.isSelected()) {
                rect.setX(e.getX());
                rect.setY(e.getY());
            }
            if (!circle.isSelected() && !rectangle.isSelected()) {
                g.beginPath();
                g.lineTo(e.getX(), e.getY());
            }
        });
        canvas.setOnMouseDragged(e -> {
            if (!circle.isSelected() && !rectangle.isSelected()) {
                g.lineTo(e.getX(), e.getY());
                g.stroke();
            }
        });

        canvas.setOnMouseReleased(e -> {
            if (circle.isSelected()) {
                cir.setRadius((Math.abs(e.getX() - cir.getCenterX()) + Math.abs(e.getY() - cir.getCenterY())) / 2);
                if(cir.getCenterX() > e.getX()) {
                    cir.setCenterX(e.getX());
                }
                if(cir.getCenterY() > e.getY()) {
                    cir.setCenterY(e.getY());
                }

                g.strokeOval(cir.getCenterX(), cir.getCenterY(), cir.getRadius(), cir.getRadius());
            }
            if (rectangle.isSelected()) {
                rect.setWidth(Math.abs((e.getX() - rect.getX())));
                rect.setHeight(Math.abs((e.getY() - rect.getY())));
                if(rect.getX() > e.getX()) {
                    rect.setX(e.getX());
                }
                if(rect.getY() > e.getY()) {
                    rect.setY(e.getY());
                }

                g.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            }
            if (!circle.isSelected() && !rectangle.isSelected()) {
                g.lineTo(e.getX(), e.getY());
                g.stroke();
                g.closePath();
            }
        });
    }

    public BorderPane GetBorderPane() {
        return pane;
    }

    private void setSave() {
        save.setOnAction(x-> {
            FileChooser saveFile = new FileChooser();
            saveFile.setTitle("Save File");

            File file = saveFile.showSaveDialog(scene.getWindow());
            if (file != null) {
                try {
                    WritableImage writableImage = new WritableImage(Constants.CanvasWidth, Constants.CanvasHeight);
                    canvas.snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
        });
    }

    private void setOpen() {
        open.setOnAction((e)->{
            FileChooser openFile = new FileChooser();
            openFile.setTitle("Open File");
            File file = openFile.showOpenDialog(scene.getWindow());
            if (file != null) {
                try {
                    InputStream io = new FileInputStream(file);
                    Image img = new Image(io);
                    canvas.getGraphicsContext2D().drawImage(img, 0, 0);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
        });
    }

    public void setScene (Scene scene) {
        this.scene = scene;
        setOpen();
        setSave();
    }



    private Scene scene;

    private final Canvas canvas = new Canvas(Constants.CanvasWidth, Constants.CanvasHeight);
    private final ColorPicker colorPicker = new ColorPicker();
    private final TextField brushSize = new TextField("10");
    private final MenuItem save = new MenuItem("Save");

    private final MenuItem open =  new MenuItem("open");
    private final Menu menu = new Menu("File");
    private final BorderPane pane = new BorderPane();
    private final ToolBar toolBar = new ToolBar();
    private final VBox main_layout = new VBox();
    private final HBox buttons_layout = new HBox();

    private final ToggleButton rectangle = new ToggleButton("Rectangle");

    private final ToggleButton circle  = new ToggleButton("Circle");

    private final ToggleGroup tools = new ToggleGroup();

}
