package by.toharrius.paint;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class Controller {
    private static Controller instance = null;
    @FXML
    public FlowPane root;
    @FXML
    public Canvas mainCanvas;
    @FXML
    public Button fuf;

    public Controller() {
        instance = this;
    }

    public static Controller getInstance() {
        return instance;
    }

    public void clicc(ActionEvent event) {
        System.out.println(5);
    }
}
