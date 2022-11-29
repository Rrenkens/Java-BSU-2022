package app.drawingtools;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.StrokeLineCap;

public class PenTool extends DrawingTool {
    private Point2D prevPos;

    public PenTool(GraphicsContext mainG, GraphicsContext g) {
        super(mainG, g);
        prevPos = new Point2D(0, 0);
    }

    @Override
    protected void configureGraphicsContext() {
        super.configureGraphicsContext();
        mainG.setLineCap(StrokeLineCap.ROUND);
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        Point2D curPos = new Point2D(e.getX(), e.getY());

        configureGraphicsContext();

        mainG.strokeLine(prevPos.getX(), prevPos.getY(), curPos.getX(), curPos.getY());

        prevPos = curPos;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        prevPos = new Point2D(e.getX(), e.getY());
    }
}
