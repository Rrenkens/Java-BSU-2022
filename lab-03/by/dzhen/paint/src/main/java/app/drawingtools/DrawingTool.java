package app.drawingtools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

public abstract class DrawingTool {
    protected GraphicsContext g;

    protected Paint strokePaint;
    protected Paint fillPaint;
    protected double strokeWidth;

    public DrawingTool(GraphicsContext g) {
        this.g = g;
    }

    public void setStrokePaint(Paint paint) {
        this.strokePaint = paint;
        g.setStroke(paint);
    }

    public void setFillPaint(Paint paint) {
        this.fillPaint = paint;
        g.setFill(paint);
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
        g.setLineWidth(strokeWidth);
    }

    protected void configureGraphicsContext() {
        g.setStroke(strokePaint);
        g.setLineWidth(strokeWidth);
        g.setLineWidth(strokeWidth);
    }

    public void onToolPicked() {

    }

    public void onToolPutAside() {

    }

    public void onMouseDragged(MouseEvent e) {

    }

    public void onMouseClicked(MouseEvent e) {

    }

    public void onMousePressed(MouseEvent e) {

    }
}
