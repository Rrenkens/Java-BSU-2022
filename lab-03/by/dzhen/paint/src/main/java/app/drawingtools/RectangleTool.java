package app.drawingtools;

import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import app.Utils;

public class RectangleTool extends DrawingTool {
    private Point2D firstPoint;

    public RectangleTool(GraphicsContext mainG, GraphicsContext g) {
        super(mainG, g);
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        configureGraphicsContext();

        Point2D secondPoint = new Point2D(e.getX(), e.getY());

        Utils.Clear(g);

        Utils.drawRect(firstPoint, secondPoint, g);
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

        Utils.Clear(g);
    }
}
