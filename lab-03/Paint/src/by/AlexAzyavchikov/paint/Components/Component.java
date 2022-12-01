package by.AlexAzyavchikov.paint.Components;

import by.AlexAzyavchikov.paint.Mediator;

import javax.swing.*;

public interface Component {
    void setMediator(Mediator mediator);
    String getName();
}
