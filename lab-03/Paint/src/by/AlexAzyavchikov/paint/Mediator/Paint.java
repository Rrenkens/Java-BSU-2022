package by.AlexAzyavchikov.paint.Mediator;


import by.AlexAzyavchikov.paint.Components.Component;
import by.AlexAzyavchikov.paint.Components.Instruments.ClearComponent;
import by.AlexAzyavchikov.paint.Components.Draw.DrawComponent;
import by.AlexAzyavchikov.paint.Components.Draw.DrawingStrategy.AbstractStrategy;
import by.AlexAzyavchikov.paint.Components.Instruments.LoadComponent;
import by.AlexAzyavchikov.paint.Components.Instruments.SaveComponent;
import by.AlexAzyavchikov.paint.Components.Settings.FillColorComponent;
import by.AlexAzyavchikov.paint.Components.Settings.PenColorComponent;
import by.AlexAzyavchikov.paint.Components.Settings.ShapeComponent;
import by.AlexAzyavchikov.paint.Components.Settings.SizeComponent;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;


public class Paint implements Mediator {
    private DrawComponent drawComponent;
    private FillColorComponent fillColorComponent;
    private PenColorComponent penColorComponent;
    private ShapeComponent shapeComponent;
    private SizeComponent sizeComponent;
    private SaveComponent saveComponent;
    private LoadComponent loadComponent;
    private ClearComponent clearComponent;

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
            case "Clear" -> clearComponent = (ClearComponent) component;
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
    public void setFigure(AbstractStrategy strategy) {
        drawComponent.getFigure().setStrategy(strategy);
    }

    @Override
    public void clear() {
        drawComponent.clear();
    }

    @Override
    public void createGUI(Stage stage) {
        loadComponent.setText("Load");
        saveComponent.setText("Save");
        clearComponent.setText("Clear");
        shapeComponent.setItems(FXCollections.observableArrayList("Rectangle", "Circle"));
        shapeComponent.setValue("Rectangle");
        sizeComponent.setItems(FXCollections.observableArrayList("Thin pen", "Standard pen", "Thick pen"));
        sizeComponent.setValue("Thin pen");
        penColorComponent.setValue(Color.BLACK);

        setConnections();
        manageLayout(stage);
        stage.show();
    }

    private void manageLayout(Stage stage) {
        VBox root = new VBox();
        root.getChildren().addAll(manageSettingsLayout(), manageDrawingField(), manageInstrumentsLayout());

        stage.setScene(new Scene(root));
        stage.setTitle("PAINT");
        stage.setMinHeight(400);
        stage.setMinWidth(600);
    }

    private HBox manageSettingsLayout() {
        HBox settings = new HBox();
        makeHResizable(fillColorComponent, penColorComponent, shapeComponent, sizeComponent);
        settings.getChildren().addAll(fillColorComponent, penColorComponent, shapeComponent, sizeComponent);
        HBox.setHgrow(settings, Priority.NEVER);
        return settings;
    }

    private HBox manageInstrumentsLayout() {
        HBox instruments = new HBox();
        makeHResizable(loadComponent, saveComponent, clearComponent);
        instruments.getChildren().addAll(loadComponent, saveComponent, clearComponent);
        HBox.setHgrow(instruments, Priority.NEVER);
        return instruments;
    }

    private StackPane manageDrawingField() {
        StackPane holder = new StackPane();
        VBox.setVgrow(holder, Priority.ALWAYS);
        holder.setStyle("-fx-background-color: white");
        VBox.setVgrow(drawComponent, Priority.ALWAYS);
        holder.getChildren().add(drawComponent);
        return holder;
    }

    private static void makeHResizable(javafx.scene.control.Control... objects) {
        Arrays.stream(objects).forEach((object) -> {
            HBox.setHgrow(object, Priority.ALWAYS);
            object.setMaxWidth(Double.MAX_VALUE);
        });
    }

    private void setConnections() {
        fillColorComponent.setOnAction((event) -> fillColorComponent.actionPerformed(event));
        penColorComponent.setOnAction((event) -> penColorComponent.actionPerformed(event));
        shapeComponent.setOnAction((event) -> shapeComponent.actionPerformed(event));
        sizeComponent.setOnAction((event) -> sizeComponent.actionPerformed(event));
        drawComponent.setOnMousePressed((event) -> drawComponent.mousePressed(event));
//        drawComponent.setOnMouseDragged((event) -> drawComponent.mouseReleased(event));
        drawComponent.setOnMouseReleased((event) -> drawComponent.mouseReleased(event));
        loadComponent.setOnAction((event) -> loadComponent.actionPerformed(event, drawComponent));
        saveComponent.setOnAction((event) -> saveComponent.actionPerformed(event, drawComponent));
        clearComponent.setOnAction((event) -> clearComponent.actionPerformed(event));
    }
}
