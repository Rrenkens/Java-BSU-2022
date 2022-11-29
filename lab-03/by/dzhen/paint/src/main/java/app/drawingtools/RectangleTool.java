package app.drawingtools;

import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class RectangleTool extends DrawingTool {
    private Point2D firstPoint;

    public RectangleTool(GraphicsContext mainG, GraphicsContext g) {
        super(mainG, g);
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        configureGraphicsContext();

        Clear(g);

        Point2D secondPoint = new Point2D(e.getX(), e.getY());

        double topLeftX = Math.min(secondPoint.getX(), firstPoint.getX());
        double topLeftY = Math.min(secondPoint.getY(), firstPoint.getY());
        double width = Math.abs(firstPoint.getX() - secondPoint.getX());
        double height = Math.abs(firstPoint.getY() - secondPoint.getY());

        g.fillRect(topLeftX, topLeftY, width, height);
        g.strokeRect(topLeftX, topLeftY, width, height);
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        firstPoint = new Point2D(e.getX(), e.getY());
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        WritableImage image = g.getCanvas().snapshot(params, null);
        mainG.drawImage(image, 0, 0);

        Clear(g);
    }

    private void Clear(GraphicsContext g) {
        g.clearRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());
    }
}
