package by.AlexAzyavchikov.paint.Mediator;


import by.AlexAzyavchikov.paint.Components.Component;
import by.AlexAzyavchikov.paint.Components.Draw.DrawComponent;
import by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy.AbstractFigure;
import by.AlexAzyavchikov.paint.Components.FileUtils.LoadComponent;
import by.AlexAzyavchikov.paint.Components.FileUtils.SaveComponent;
import by.AlexAzyavchikov.paint.Components.Settings.FillColorComponent;
import by.AlexAzyavchikov.paint.Components.Settings.PenColorComponent;
import by.AlexAzyavchikov.paint.Components.Settings.ShapeComponent;
import by.AlexAzyavchikov.paint.Components.Settings.SizeComponent;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Paint implements Mediator {
    private DrawComponent drawComponent;
    private FillColorComponent fillColorComponent;
    private PenColorComponent penColorComponent;
    private ShapeComponent shapeComponent;
    private SizeComponent sizeComponent;
    private SaveComponent saveComponent;
    private LoadComponent loadComponent;

    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
        switch (component.getName()) {
            case "Draw" -> drawComponent = (DrawComponent) component;
            case "FillColor" -> fillColorComponent = (FillColorComponent) component;
            case "PenColor" -> penColorComponent = (PenColorComponent) component;
            case "Shape" -> shapeComponent = (ShapeComponent) component;
            case "Size" -> sizeComponent = (SizeComponent) component;
            case "Save" -> saveComponent = (SaveComponent) component;
            case "Load" -> loadComponent = (LoadComponent) component;
        }
    }

    @Override
    public void setFillColor(Color color) {
        drawComponent.getPen().setFillColor(color);
    }

    public void setPenColor(javafx.scene.paint.Color color) {
        drawComponent.getPen().setPenColor(color);
    }

    @Override
    public void setPenSize(int size) {
        drawComponent.getPen().setSize(size);
    }

    @Override
    public void setFigure(AbstractFigure figure) {
        drawComponent.getFigure().setFigure(figure);
    }

    @Override
    public void createGUI(Stage stage) {
        loadComponent.setText("Load");
        saveComponent.setText("Save");
        shapeComponent.setItems(FXCollections.observableArrayList("Rectangle", "Circle"));
        shapeComponent.setValue("Rectangle");
        sizeComponent.setItems(FXCollections.observableArrayList("Thin pen", "Standard pen", "Thick pen"));
        sizeComponent.setValue("Thin pen");
        penColorComponent.setValue(Color.BLACK);

        setConnections();

        manageLayout(stage);
    }

    private void manageLayout(Stage stage) {
        HBox settings = new HBox();
        HBox.setHgrow(fillColorComponent, Priority.ALWAYS);
        HBox.setHgrow(penColorComponent, Priority.ALWAYS);
        HBox.setHgrow(shapeComponent, Priority.ALWAYS);
        HBox.setHgrow(sizeComponent, Priority.ALWAYS);
        fillColorComponent.setMaxWidth(Double.MAX_VALUE);
        penColorComponent.setMaxWidth(Double.MAX_VALUE);
        shapeComponent.setMaxWidth(Double.MAX_VALUE);
        sizeComponent.setMaxWidth(Double.MAX_VALUE);
        settings.getChildren().addAll(fillColorComponent, penColorComponent, shapeComponent, sizeComponent);
        HBox.setHgrow(settings, Priority.NEVER);

        HBox fileWork = new HBox();
        HBox.setHgrow(loadComponent, Priority.ALWAYS);
        HBox.setHgrow(saveComponent, Priority.ALWAYS);
        loadComponent.setMaxWidth(Double.MAX_VALUE);
        saveComponent.setMaxWidth(Double.MAX_VALUE);
        fileWork.getChildren().addAll(loadComponent, saveComponent);
        HBox.setHgrow(fileWork, Priority.NEVER);

        VBox root = new VBox();
        VBox.setVgrow(drawComponent, Priority.ALWAYS);
        root.getChildren().addAll(settings, drawComponent, fileWork);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("PAINT");
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.show();
    }

    private void setConnections() {
        fillColorComponent.setOnAction((event) -> fillColorComponent.actionPerformed(event));
        penColorComponent.setOnAction((event) -> penColorComponent.actionPerformed(event));
        shapeComponent.setOnAction((event) -> shapeComponent.actionPerformed(event));
        sizeComponent.setOnAction((event) -> sizeComponent.actionPerformed(event));
        drawComponent.setOnMousePressed((event) -> drawComponent.mousePressed(event));
//        drawComponent.setOnMouseDragged((event) -> drawComponent.mouseReleased(event));
        drawComponent.setOnMouseReleased((event) -> drawComponent.mouseReleased(event));
    }
}
