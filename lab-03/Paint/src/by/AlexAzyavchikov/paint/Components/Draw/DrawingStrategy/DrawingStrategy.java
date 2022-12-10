package by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public interface DrawingStrategy {
    void drawFinal(GraphicsContext graphicContext, Point2D pressedPoint, Point2D releasedPoint);
    void drawIntermediately(GraphicsContext graphicContext, Point2D pressedPoint, Point2D releasedPoint);
}
