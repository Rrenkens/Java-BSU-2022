package by.AlexAzyavchikov.paint.Components.FileUtils;

import by.AlexAzyavchikov.paint.Components.Component;
import by.AlexAzyavchikov.paint.Mediator.Mediator;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LoadComponent extends Button implements Component {
    private Mediator mediator;

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "Load";
    }

    public void actionPerformed(Event event, Canvas canvas) {
        FileChooser loadFileChooser = new FileChooser();
        loadFileChooser.setTitle("Select file to load");
        File loadFile = loadFileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (loadFile == null) {
            return;
        }
        try {
            InputStream io = new FileInputStream(loadFile);
            Image img = new Image(io);
            canvas.getGraphicsContext2D().drawImage(img, 0, 0, img.getWidth(), img.getHeight());
        } catch (IOException ex) {
            System.out.println("Load error");
        }
    }
}
