package by.polina_kostyukovich.paint.util;

import javafx.beans.NamedArg;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorButton extends ToggleButton {
    private Color color;
    private final int size;
    private final static int OFFSET = 12;

    public ColorButton(
            @NamedArg("color") Color color,
            @NamedArg("size") int size) {
        this.color = color;
        this.size = size;
        setMaxHeight(size);
        setMaxWidth(size);
        setMinHeight(size);
        setMinWidth(size);
        Rectangle rect = new Rectangle(size - OFFSET, size - OFFSET, this.color);
        setGraphic(rect);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color newColor) {
        color = newColor;
        Rectangle rect = new Rectangle(size - OFFSET, size - OFFSET, this.color);
        setGraphic(rect);
    }
}
