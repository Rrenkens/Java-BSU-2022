package by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Line extends AbstractStrategy {
    public enum Type {
        STANDARD,
        REVERSED
    }

    private Type type = Type.STANDARD;

    @Override
    public void draw(GraphicsContext graphicContext, Point2D top, int w, int h) {
        graphicContext.beginPath();
        if (type.equals(Type.STANDARD)) {
            graphicContext.lineTo(top.getX(), top.getY());
            graphicContext.lineTo(top.getX() + w, top.getY() + h);
        } else if (type.equals(Type.REVERSED)) {
            graphicContext.lineTo(top.getX() + w, top.getY());
            graphicContext.lineTo(top.getX(), top.getY() + h);
        }
        graphicContext.stroke();
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Line";
    }
}
