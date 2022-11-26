package by.marmotikon.paint;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PaintController {
    @FXML
    private TextField brushSize;
    @FXML
    private ColorPicker brushColor;
    @FXML
    private CheckBox eraser;
    @FXML
    private Canvas canvas;

    @FXML
    protected void onSave() {
        System.err.println("Save");
    }

    @FXML
    protected void onExit() {
        System.err.println("Exit");
    }
}