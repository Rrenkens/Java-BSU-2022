package by.toharrius.paint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int WINDOW_HEIGHT = 900;
    private static final int WINDOW_WIDTH = 1400;
    private static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    public static Scene getScene() {
        return scene;
    }

    @Override
    public void start(Stage window) throws Exception {
        var fxml_url = getClass().getResource("interface.fxml");
        assert fxml_url != null;
        scene = new Scene(
                FXMLLoader.load(fxml_url),
                WINDOW_WIDTH,
                WINDOW_HEIGHT,
                false,
                SceneAntialiasing.BALANCED
        );
        window.setScene(scene);
        Controller.getInstance().initElements();

        window.setTitle("PaintNotMS");
        window.show();
    }
}
