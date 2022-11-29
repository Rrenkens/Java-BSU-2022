module com.example.paint {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens app to javafx.fxml;
    exports app;
    exports app.drawingtools;
    opens app.drawingtools to javafx.fxml;
}