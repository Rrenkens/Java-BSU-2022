package app;

import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class Utils {
    public static void Clear(GraphicsContext g) {
        g.clearRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());
    }

    public static void drawRect(Point2D p1, Point2D p2, GraphicsContext g) {
        double topLeftX = Math.min(p2.getX(), p1.getX());
        double topLeftY = Math.min(p2.getY(), p1.getY());
        double width = Math.abs(p1.getX() - p2.getX());
        double height = Math.abs(p1.getY() - p2.getY());

        g.fillRect(topLeftX, topLeftY, width, height);
        g.strokeRect(topLeftX, topLeftY, width, height);
    }

    public static void drawCircle(Point2D center, double radius, GraphicsContext g) {
        double topLeftX = center.getX() - radius;
        double topLeftY = center.getY() - radius;

        g.fillOval(topLeftX, topLeftY, radius * 2, radius * 2);
        g.strokeOval(topLeftX, topLeftY, radius * 2, radius * 2);
    }

    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    public static BufferedImage snapshot(Canvas canvas) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        WritableImage writableImage = canvas.getGraphicsContext2D().getCanvas().snapshot(params, null);

        return SwingFXUtils.fromFXImage(writableImage, null);
    }
}
