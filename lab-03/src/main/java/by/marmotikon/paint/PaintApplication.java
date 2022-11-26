package by.marmotikon.paint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PaintApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PaintApplication.class.getResource("paint-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Paint");
        stage.setScene(scene);
        stage.setMinHeight(450);
        stage.setMinWidth(450);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}