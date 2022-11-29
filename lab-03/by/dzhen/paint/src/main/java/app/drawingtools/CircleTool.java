package app.drawingtools;

import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CircleTool extends DrawingTool {

    private Point2D center;

    public CircleTool(GraphicsContext mainG, GraphicsContext g) {
        super(mainG, g);
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        configureGraphicsContext();

        Clear(g);

        Point2D secondPoint = new Point2D(e.getX(), e.getY());

        double radius = secondPoint.distance(center);
        double topLeftX = center.getX() - radius;
        double topLeftY = center.getY() - radius;

        g.fillOval(topLeftX, topLeftY, radius * 2, radius * 2);
        g.strokeOval(topLeftX, topLeftY, radius * 2, radius * 2);
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        center = new Point2D(e.getX(), e.getY());
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
