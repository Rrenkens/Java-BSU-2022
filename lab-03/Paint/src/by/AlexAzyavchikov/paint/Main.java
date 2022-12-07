package by.AlexAzyavchikov.paint;

import by.AlexAzyavchikov.paint.Components.Draw.ClearComponent;
import by.AlexAzyavchikov.paint.Components.Draw.DrawComponent;
import by.AlexAzyavchikov.paint.Components.FileUtils.LoadComponent;
import by.AlexAzyavchikov.paint.Components.FileUtils.SaveComponent;
import by.AlexAzyavchikov.paint.Components.Settings.FillColorComponent;
import by.AlexAzyavchikov.paint.Components.Settings.PenColorComponent;
import by.AlexAzyavchikov.paint.Components.Settings.ShapeComponent;
import by.AlexAzyavchikov.paint.Components.Settings.SizeComponent;
import by.AlexAzyavchikov.paint.Mediator.Mediator;
import by.AlexAzyavchikov.paint.Mediator.Paint;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Mediator mediator = new Paint();

        mediator.registerComponent(new DrawComponent());
        mediator.registerComponent(new ShapeComponent());
        mediator.registerComponent(new FillColorComponent());
        mediator.registerComponent(new PenColorComponent());
        mediator.registerComponent(new SizeComponent());
        mediator.registerComponent(new SaveComponent());
        mediator.registerComponent(new LoadComponent());
        mediator.registerComponent(new ClearComponent());

        mediator.createGUI(stage);
    }
}