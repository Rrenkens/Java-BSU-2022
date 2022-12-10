package by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy.OnceDrawingStrategy;

import by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy.OnceDrawingStrategy.OnceDrawingDrawingStrategy;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Circle extends OnceDrawingDrawingStrategy {
    @Override
    protected void draw(GraphicsContext graphicContext, Point2D top, int w, int h) {
        graphicContext.fillOval(top.getX(), top.getY(), w, h);
        graphicContext.strokeOval(top.getX(), top.getY(), w, h);
    }
}
