package by.AlexAzyavchikov.paint.Components.Draw;

import by.AlexAzyavchikov.paint.Components.Component;
import by.AlexAzyavchikov.paint.Components.Draw.Utils.Figure;
import by.AlexAzyavchikov.paint.Components.Draw.Utils.Pen;
import by.AlexAzyavchikov.paint.Mediator.Mediator;
import javafx.geometry.Point2D;
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

    public Pen getPen() {
        return pen;
    }

    public Figure getFigure() {
        return figure;
    }

    public void mousePressed(MouseEvent event) {
        figure.setStartPoint(new Point2D(event.getX(), event.getY()));
    }

    public void mouseDragged(MouseEvent event) {
        figure.setIntermediatePoint(new Point2D(event.getX(), event.getY()));
        figure.drawIntermediately(getGraphicsContext2D(),pen);
    }

    public void mouseReleased(MouseEvent event) {
        figure.setFinishPoint(new Point2D(event.getX(), event.getY()));
        figure.drawFinally(getGraphicsContext2D(), pen);
    }

    public void clear() {
        this.getGraphicsContext2D().setFill(Color.WHITE);
        this.getGraphicsContext2D().fillRect(0, 0, getWidth() - 1, getHeight() - 1);
    }
}
