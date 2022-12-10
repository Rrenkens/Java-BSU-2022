package by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy;

import javafx.geometry.Point2D;

abstract public class AbstractDrawingStrategy implements DrawingStrategy {
    static protected int countXDifference(Point2D first, Point2D second) {
        return (int) Math.abs(first.getX() - second.getX());
    }

    static protected int countYDifference(Point2D first, Point2D second) {
        return (int) Math.abs(first.getY() - second.getY());
    }

    static protected Point2D leftRectsCorner(Point2D first, Point2D second) {
        return new Point2D(Math.min(first.getX(), second.getX()),
                Math.min(first.getY(), second.getY()));
    }
}
