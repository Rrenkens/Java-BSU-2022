package by.AlexAzyavchikov.paint.Components.Draw;

import by.AlexAzyavchikov.paint.Components.Component;
import by.AlexAzyavchikov.paint.Mediator.Mediator;
import javafx.event.Event;
import javafx.scene.control.Button;

public class ClearComponent extends Button implements Component {
    private Mediator mediator;

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "Clear";
    }

    public void actionPerformed(Event event) {
        mediator.clear();
    }
}