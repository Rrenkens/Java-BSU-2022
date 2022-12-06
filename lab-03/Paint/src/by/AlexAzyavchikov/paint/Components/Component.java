package by.AlexAzyavchikov.paint.Components;

import by.AlexAzyavchikov.paint.Mediator.Mediator;

public interface Component {
    void setMediator(Mediator mediator);
    String getName();
}
