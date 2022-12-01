package by.toharrius.paint;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ColorChooser {
    private final FlowPane flow;
    private static final String[] defaultColorNames = {
            "black", "white", "grey",
            "darkred", "red", "hotpink",
            "#808000", "yellow", "gold",
            "brown", "orange", "salmon",
            "purple", "#8000ff", "#ff00ff",
            "green", "#00ff00", "lightgreen",
            "darkblue", "blue", "skyblue",
    };
    private final Color[] defaultColors;
    private int activeColorIndex = 0;

    private Color colorByIndex(int index) {
        return defaultColors[index];
    }
    private void chooseActiveColor(int new_color_index) {
        getNthCell(activeColorIndex).getStyleClass().remove("chosen");
        activeColorIndex = new_color_index;
        var color = colorByIndex(new_color_index);
        getNthCell(activeColorIndex).getStyleClass().add("chosen");

        var ctx = Controller.getMainDC();
        ctx.setFill(color);
        ctx.setStroke(color);
        ctx = Controller.getSecondaryDC();
        ctx.setFill(color);
        ctx.setStroke(color);
    }
    private void addColorBox(String color) {
        var box = new Label();
        box.getStyleClass().add("color-box");
        box.setStyle("-fx-background-color: " + color);
        final int index = flow.getChildren().size();
        box.setOnMousePressed(event -> chooseActiveColor(index));
        flow.getChildren().add(box);
    }
    private Label getNthCell(int n) {
        return (Label) flow.getChildren().get(n);
    }
    public ColorChooser(FlowPane flow) {
        this.flow = flow;
        var arr = new ArrayList<Color>();
        for (var color : defaultColorNames) {
            addColorBox(color);
            arr.add(Color.valueOf(color));
        }
        this.defaultColors = arr.toArray(new Color[0]);
        chooseActiveColor(0);
    }
}
