package by.AlexAzyavchikov.paint.Components.Draw;

import by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy.AbstractStrategy;
import by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy.Rectangle;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Figure {
    private Point2D startPoint = new Point2D(0, 0);
    private Point2D finishPoint = new Point2D(0, 0);
    private AbstractStrategy strategy = new Rectangle();

    public void setStrategy(AbstractStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStartPoint(Point2D point) {
        this.startPoint = point;
    }

    public void setFinishPoint(Point2D point) {
        this.finishPoint = point;
    }

    public void draw(GraphicsContext graphicContext, Pen pen) {
        setupGraphicContextPen(graphicContext, pen);
        Point2D top = lessPoint(startPoint, finishPoint);
        int width = (int) Math.abs(finishPoint.getX() - startPoint.getX());
        int height = (int) Math.abs(finishPoint.getY() - startPoint.getY());
        strategy.draw(graphicContext, top, width, height);
    }

    static private Point2D lessPoint(Point2D point1, Point2D point2) {
        return new Point2D(Math.min(point1.getX(), point2.getX()),
                Math.min(point1.getY(), point2.getY()));
    }

    private void setupGraphicContextPen(GraphicsContext graphicContext, Pen pen) {
        graphicContext.setFill(pen.getFillColor());
        graphicContext.setLineWidth(pen.getSize());
        graphicContext.setStroke(pen.getPenColor());
    }
}
