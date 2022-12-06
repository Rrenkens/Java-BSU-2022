package by.AlexAzyavchikov.paint.Components.Settings;

import by.AlexAzyavchikov.paint.Components.Component;
import by.AlexAzyavchikov.paint.Components.Draw.Figure;
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
            case "Rectangle" -> mediator.setShape(Figure.Shape.RECTANGLE);
            case "Circle" -> mediator.setShape(Figure.Shape.CIRCLE);
        }
    }
}
