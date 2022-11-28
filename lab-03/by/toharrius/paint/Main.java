package by.toharrius.paint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int WINDOW_HEIGHT = 900;
    private static final int WINDOW_WIDTH = 1400;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        var fxml_url = getClass().getResource("interface.fxml");
        assert fxml_url != null;
        var scene = new Scene(FXMLLoader.load(fxml_url));
        window.setScene(scene);
        Controller.getInstance().initElements();

        window.setTitle("PaintNotMS");
        window.setHeight(WINDOW_HEIGHT);
        window.setWidth(WINDOW_WIDTH);
        window.show();
    }
}
