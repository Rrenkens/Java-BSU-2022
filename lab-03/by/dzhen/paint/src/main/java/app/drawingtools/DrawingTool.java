package app.drawingtools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

public abstract class DrawingTool {
    protected GraphicsContext mainG;
    protected GraphicsContext g;

    protected Paint strokePaint;
    protected Paint fillPaint;
    protected double strokeWidth;

    public DrawingTool(GraphicsContext mainG, GraphicsContext g) {
        this.g = g;
        this.mainG = mainG;
    }

    public void setStrokePaint(Paint paint) {
        this.strokePaint = paint;
        g.setStroke(paint);
        mainG.setStroke(paint);
    }

    public void setFillPaint(Paint paint) {
        this.fillPaint = paint;
        g.setFill(paint);
        mainG.setFill(paint);
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
        g.setLineWidth(strokeWidth);
        mainG.setLineWidth(strokeWidth);
    }

    protected void configureGraphicsContext() {
        g.setStroke(strokePaint);
        g.setLineWidth(strokeWidth);
        g.setLineWidth(strokeWidth);

        mainG.setStroke(strokePaint);
        mainG.setLineWidth(strokeWidth);
        mainG.setLineWidth(strokeWidth);
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

    public void onMouseReleased(MouseEvent e) {

    }
}
