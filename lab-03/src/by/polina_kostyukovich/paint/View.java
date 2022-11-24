package by.polina_kostyukovich.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.geom.Point2D;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class View {
    private Canvas canvas;
    private Canvas secondLayer;
    private Color color1;
    private Color color2;

    public void setCanvas(Canvas canvas, Canvas secondLayer) {
        this.canvas = canvas;
        this.secondLayer = secondLayer;
        this.canvas.toFront();
    }

    public void clearCanvas() {
        GraphicsContext gcCanvas = canvas.getGraphicsContext2D();
        gcCanvas.setFill(Color.WHITE);
        gcCanvas.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        GraphicsContext gcSecondLayer = secondLayer.getGraphicsContext2D();
        gcSecondLayer.setFill(Color.TRANSPARENT);
        gcSecondLayer.fillRect(0, 0, secondLayer.getWidth(), secondLayer.getHeight());
    }

    public void drawLine(Point2D.Double point1, Point2D.Double point2, double width) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(color1);
        gc.setLineWidth(width);
        gc.strokeLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
    }

    public void drawingShape(Point2D.Double point1, Point2D.Double point2,
                             Shape shape, Controller.FillType fillType, double width) {
        GraphicsContext gc = secondLayer.getGraphicsContext2D();
        gc.clearRect(0, 0, secondLayer.getWidth(), secondLayer.getHeight());
        drawShape(gc, point1, point2, shape, fillType, width);
        secondLayer.toFront();
    }

    public void endDrawingShape(Point2D.Double point1, Point2D.Double point2,
                                Shape shape, Controller.FillType fillType, double width) {
        GraphicsContext gcSecondLayer = secondLayer.getGraphicsContext2D();
        gcSecondLayer.clearRect(0, 0, secondLayer.getWidth(), secondLayer.getHeight());

        GraphicsContext gcCanvas = canvas.getGraphicsContext2D();
        drawShape(gcCanvas, point1, point2, shape, fillType, width);
        canvas.toFront();
    }

    private void drawShape(GraphicsContext gc, Point2D.Double point1, Point2D.Double point2,
                           Shape shape, Controller.FillType fillType, double width) {
        gc.setStroke(color1);
        switch (fillType) {
            case NO_FILL -> gc.setFill(Color.TRANSPARENT);
            case SOLID_COLOR -> gc.setFill(color2);
        }

        gc.setLineWidth(width);
        switch (shape) {
            case ELLIPSE -> {
                gc.fillOval(min(point1.getX(), point2.getX()), min(point1.getY(), point2.getY()),
                        abs(point2.getX() - point1.getX()), abs(point2.getY() - point1.getY()));
                gc.strokeOval(min(point1.getX(), point2.getX()), min(point1.getY(), point2.getY()),
                        abs(point2.getX() - point1.getX()), abs(point2.getY() - point1.getY()));
            }
            case RECTANGLE -> {
                gc.fillRect(min(point1.getX(), point2.getX()), min(point1.getY(), point2.getY()),
                        abs(point2.getX() - point1.getX()), abs(point2.getY() - point1.getY()));
                gc.strokeRect(min(point1.getX(), point2.getX()), min(point1.getY(), point2.getY()),
                        abs(point2.getX() - point1.getX()), abs(point2.getY() - point1.getY()));
            }
        }
    }

    public void setColor1(Color color) {
        color1 = color;
    }

    public void setColor2(Color color) {
        color2 = color;
    }

    enum Shape {
        ELLIPSE,
        RECTANGLE
    }
}
