package by.AlexAzyavchikov.paint.Components.Settings;


import by.AlexAzyavchikov.paint.Components.Component;
import by.AlexAzyavchikov.paint.Mediator.Mediator;
import javafx.event.ActionEvent;
import javafx.scene.control.ColorPicker;

public class PenColorComponent extends ColorPicker implements Component {
    private Mediator mediator;

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "PenColor";
    }

    public void actionPerformed(ActionEvent event) {
        mediator.setPenColor(getValue());
    }
}
