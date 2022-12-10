package by.AlexAzyavchikov.paint.Components.Draw.Utils;

import by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy.AbstractDrawingStrategy;
import by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy.OnceDrawingStrategy.Rectangle;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Figure {
    private Point2D startPoint;
    private Point2D startIntermediatePoint;
    private Point2D finishIntermediatePoint;
    private Point2D finishPoint;
    private AbstractDrawingStrategy strategy = new Rectangle();

    public void setStrategy(AbstractDrawingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStartPoint(Point2D point) {
        startIntermediatePoint = point;
        finishIntermediatePoint = point;
        this.startPoint = point;
    }

    public void setIntermediatePoint(Point2D point) {
        startIntermediatePoint = finishIntermediatePoint;
        this.finishIntermediatePoint = point;
    }

    public void setFinishPoint(Point2D point) {
        startIntermediatePoint = finishIntermediatePoint;
        finishIntermediatePoint = point;
        this.finishPoint = point;
    }

    public void drawIntermediately(GraphicsContext graphicContext, Pen pen) {
        setupGraphicContextPen(graphicContext, pen);
        strategy.drawIntermediately(graphicContext, startIntermediatePoint, finishIntermediatePoint);
    }

    public void drawFinally(GraphicsContext graphicContext, Pen pen) {
        setupGraphicContextPen(graphicContext, pen);
        strategy.drawFinal(graphicContext, startPoint, finishPoint);
    }

    private void setupGraphicContextPen(GraphicsContext graphicContext, Pen pen) {
        graphicContext.setFill(pen.getFillColor());
        graphicContext.setLineWidth(pen.getSize());
        graphicContext.setStroke(pen.getPenColor());
    }

}
