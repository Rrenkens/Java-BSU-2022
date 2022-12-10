package by.AlexAzyavchikov.paint.Mediator;

import by.AlexAzyavchikov.paint.Components.Component;
import by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy.AbstractDrawingStrategy;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public interface Mediator {
    void registerComponent(Component component);

    void setFillColor(Color color);

    void setPenColor(Color color);

    void setPenSize(int size);

    void setFigure(AbstractDrawingStrategy strategy);

    void clear();

    void createGUI(Stage stage);
}
