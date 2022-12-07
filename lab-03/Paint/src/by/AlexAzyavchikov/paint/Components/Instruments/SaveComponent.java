package by.AlexAzyavchikov.paint.Components.Instruments;

import by.AlexAzyavchikov.paint.Components.Component;
import by.AlexAzyavchikov.paint.Mediator.Mediator;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class SaveComponent extends Button implements Component {
    private Mediator mediator;

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "Save";
    }

    public void actionPerformed(Event event, Node node) {
        FileChooser saveFileChooser = new FileChooser();
        saveFileChooser.setTitle("Select file to save");

        File saveFile = saveFileChooser.showSaveDialog(node.getScene().getWindow());
        if (saveFile == null) {
            return;
        }
        try {
            WritableImage image = node.snapshot(new SnapshotParameters(), null);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(renderedImage, "png", saveFile);
        } catch (IOException ex) {
            System.out.println("Save error");
        }
    }
}
