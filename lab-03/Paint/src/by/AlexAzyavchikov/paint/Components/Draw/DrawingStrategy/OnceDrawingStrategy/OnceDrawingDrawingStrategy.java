package by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy.OnceDrawingStrategy;

import by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy.AbstractDrawingStrategy;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

abstract public class OnceDrawingDrawingStrategy extends AbstractDrawingStrategy {
    @Override
    public void drawFinal(GraphicsContext graphicContext, Point2D pressedPoint, Point2D releasedPoint) {
        Point2D top = AbstractDrawingStrategy.leftRectsCorner(pressedPoint, releasedPoint);
        int w = AbstractDrawingStrategy.countXDifference(pressedPoint, releasedPoint);
        int h = AbstractDrawingStrategy.countYDifference(pressedPoint, releasedPoint);
        draw(graphicContext, top, w, h);
    }

    abstract protected void draw(GraphicsContext graphicContext, Point2D top, int w, int h);

    @Override
    public void drawIntermediately(GraphicsContext graphicContext, Point2D pressedPoint, Point2D releasedPoint) {
    }
}
