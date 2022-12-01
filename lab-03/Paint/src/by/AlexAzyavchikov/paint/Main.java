package by.AlexAzyavchikov.paint;

import by.AlexAzyavchikov.paint.Components.*;

public class Main {
    public static void main(String[] args) {
        Mediator mediator = new Editor();

        mediator.registerComponent(new DrawField());
        mediator.registerComponent(new ShapeButton());
        mediator.registerComponent(new ColorButton());
        mediator.registerComponent(new SizeSpinner());
        mediator.registerComponent(new SaveButton());

        mediator.createGUI();
    }
}