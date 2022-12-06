package by.AlexAzyavchikov.paint.Components.Draw;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Figure {
    private Point2D startPoint = new Point2D(0, 0);
    private Point2D finishPoint = new Point2D(0, 0);

    public enum Shape {
        RECTANGLE,
        CIRCLE
    }

    private Shape shape = Shape.RECTANGLE;

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setStartPoint(Point2D point) {
        this.startPoint = point;
    }

    public void setFinishPoint(Point2D point) {
        this.finishPoint = point;
    }

    public void drawFigure(GraphicsContext graphicContext, Pen pen) {
        setupGraphicContextPen(graphicContext, pen);
        Point2D top = lessPoint(startPoint, finishPoint);
        int width = (int) Math.abs(finishPoint.getX() - startPoint.getX());
        int height = (int) Math.abs(finishPoint.getY() - startPoint.getY());
        switch (shape) {
            case RECTANGLE -> {
                graphicContext.fillRect(top.getX(), top.getY(), width, height);
                graphicContext.strokeRect(top.getX(), top.getY(), width, height);
            }
            case CIRCLE -> {
                graphicContext.fillOval(top.getX(), top.getY(), width, height);
                graphicContext.strokeOval(top.getX(), top.getY(), width, height);
            }
        }
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
