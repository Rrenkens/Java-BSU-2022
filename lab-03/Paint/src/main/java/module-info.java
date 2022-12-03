module com.example.paint {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.paint to javafx.fxml;
    exports by.DaniilDomnin.paint;
    opens by.DaniilDomnin.paint to javafx.fxml;
}