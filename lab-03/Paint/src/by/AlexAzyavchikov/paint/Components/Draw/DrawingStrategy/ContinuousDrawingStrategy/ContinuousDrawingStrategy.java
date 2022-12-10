package by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy.ContinuousDrawingStrategy;

import by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy.AbstractDrawingStrategy;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class ContinuousDrawingStrategy extends AbstractDrawingStrategy {
    @Override
    public void drawFinal(GraphicsContext graphicContext, Point2D pressedPoint, Point2D releasedPoint) {
    }
}
