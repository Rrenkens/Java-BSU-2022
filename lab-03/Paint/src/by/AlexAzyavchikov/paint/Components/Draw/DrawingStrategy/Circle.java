package by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Circle extends AbstractStrategy {

    @Override
    public void draw(GraphicsContext graphicContext, Point2D top, int w, int h) {
        graphicContext.fillOval(top.getX(), top.getY(), w, h);
        graphicContext.strokeOval(top.getX(), top.getY(), w, h);
    }

    @Override
    public String toString() {
        return "Circle";
    }
}
