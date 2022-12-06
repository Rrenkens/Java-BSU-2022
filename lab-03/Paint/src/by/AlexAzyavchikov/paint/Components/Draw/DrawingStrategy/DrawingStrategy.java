package by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public interface DrawingStrategy {
    void draw(GraphicsContext graphicContext, Point2D top, int w, int h);
}
