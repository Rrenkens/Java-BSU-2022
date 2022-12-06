package by.AlexAzyavchikov.paint.Components.Draw;

import javafx.scene.paint.Color;

public class Pen {
    private Color penColor = Color.BLACK;
    private Color fillColor = Color.WHITE;
    private int size = 1;

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    public Color getPenColor() {
        return penColor;
    }

    public void setPenColor(Color color) {
        this.penColor = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
