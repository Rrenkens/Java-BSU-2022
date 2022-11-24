package by.polina_kostyukovich.paint.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.*;

public class ImageSerializer implements Serializable {
    private transient WritableImage image ;

    public ImageSerializer(WritableImage image) {
        this.image = image;
    }

    public void setImage(WritableImage image) {
        this.image = image;
    }

    public WritableImage getImage() {
        return image;
    }

    @Serial
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        image = SwingFXUtils.toFXImage(ImageIO.read(inputStream), null);
    }

    @Serial
    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputStream);
    }
}
