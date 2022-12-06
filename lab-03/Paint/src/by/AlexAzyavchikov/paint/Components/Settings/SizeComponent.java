package by.AlexAzyavchikov.paint.Components.Settings;

import by.AlexAzyavchikov.paint.Components.Component;
import by.AlexAzyavchikov.paint.Mediator.Mediator;
import javafx.event.Event;
import javafx.scene.control.ComboBox;

public class SizeComponent extends ComboBox implements Component {
    private Mediator mediator;

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "Size";
    }

    public void actionPerformed(Event event) {
//        TODO Magic constants
        switch (this.getValue().toString()) {
            case "Thin pen" -> mediator.setPenSize(1);
            case "Standard pen" -> mediator.setPenSize(3);
            case "Thick pen" -> mediator.setPenSize(7);
        }
    }
}
