package by.AlexAzyavchikov.paint.Components.Settings;

import by.AlexAzyavchikov.paint.Components.Component;
import by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy.Circle;
import by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy.Rectangle;
import by.AlexAzyavchikov.paint.Mediator.Mediator;
import javafx.event.Event;
import javafx.scene.control.ComboBox;

public class ShapeComponent extends ComboBox implements Component {
    private Mediator mediator;

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "Shape";
    }


    public void actionPerformed(Event event) {
        switch (this.getValue().toString()) {
            case "Rectangle" -> mediator.setFigure(new Rectangle());
            case "Circle" -> mediator.setFigure(new Circle());
        }
    }
}
