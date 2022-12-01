package by.AlexAzyavchikov.paint.Components;

import by.AlexAzyavchikov.paint.Mediator;

import javax.swing.*;

public class SizeSpinner extends JSpinner implements Component {
    private Mediator mediator;

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "SizeSpinner";
    }
}
