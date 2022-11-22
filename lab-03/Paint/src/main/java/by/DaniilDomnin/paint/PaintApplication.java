package by.DaniilDomnin.paint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PaintApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        PaintController paintController = new PaintController();
        Scene scene  = new Scene(paintController.GetBorderPane(), Constants.SceneWidth,  Constants.SceneHeight);
        stage.setScene(scene);
        paintController.setScene(stage.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}