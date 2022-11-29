package app.drawingtools;

import app.Utils;
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

        Utils.Clear(g);

        Point2D secondPoint = new Point2D(e.getX(), e.getY());
        double radius = secondPoint.distance(center);

        Utils.drawCircle(center, radius, g);
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

        Utils.Clear(g);
    }
}
