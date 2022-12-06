package by.AlexAzyavchikov.paint.Components.Draw;

import by.AlexAzyavchikov.paint.Components.Component;
import by.AlexAzyavchikov.paint.Mediator.Mediator;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class DrawComponent extends ResizableCanvas implements Component {
    private Mediator mediator;
    private final Figure figure = new Figure();
    private final Pen pen = new Pen();

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "Draw";
    }

    public void setFillColor(Color color) {
        pen.setFillColor(color);
    }

    public void setPenColor(Color color) {
        pen.setPenColor(color);
    }

    public void setPenSize(int size) {
        pen.setSize(size);
    }

    public void setShape(Figure.Shape shape) {
        figure.setShape(shape);
    }

    public void mousePressed(MouseEvent event) {
        figure.setStartPoint(new Point2D(event.getX(), event.getY()));
    }

    public void mouseReleased(MouseEvent event) {
        figure.setFinishPoint(new Point2D(event.getX(), event.getY()));
        figure.drawFigure(this.getGraphicsContext2D(), pen);
    }
}
