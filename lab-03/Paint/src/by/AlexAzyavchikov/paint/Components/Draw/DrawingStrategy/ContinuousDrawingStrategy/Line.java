package by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy.ContinuousDrawingStrategy;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Line extends ContinuousDrawingStrategy {
    @Override
    public void drawIntermediately(GraphicsContext graphicContext, Point2D pressedPoint, Point2D releasedPoint) {
        graphicContext.beginPath();
        graphicContext.moveTo(pressedPoint.getX(), pressedPoint.getY());
        graphicContext.lineTo(releasedPoint.getX(), releasedPoint.getY());
        graphicContext.stroke();
    }
}
