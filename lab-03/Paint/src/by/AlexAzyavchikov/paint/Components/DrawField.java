package by.AlexAzyavchikov.paint.Components;

import by.AlexAzyavchikov.paint.Mediator;

public class DrawField implements Component {
    private Mediator mediator;

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "DrawField";
    }
}
