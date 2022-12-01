package by.AlexAzyavchikov.paint;

import by.AlexAzyavchikov.paint.Components.Component;

import javax.swing.*;

public interface Mediator {
    void registerComponent(Component component);

    void createGUI();
}
