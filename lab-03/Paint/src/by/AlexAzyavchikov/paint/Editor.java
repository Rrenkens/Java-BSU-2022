package by.AlexAzyavchikov.paint;


import by.AlexAzyavchikov.paint.Components.*;
import by.AlexAzyavchikov.paint.Components.Component;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Editor implements Mediator {
    private DrawField drawField;
    private ColorButton colorButton;
    private ShapeButton shapeButton;
    private SizeSpinner sizeSpinner;
    private SaveButton saveButton;

    /**
     * Здесь происходит регистрация компонентов посредником.
     */
    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
        switch (component.getName()) {
            case "DrawField" -> drawField = (DrawField) component;
            case "ColorButton" -> colorButton = (ColorButton) component;
            case "ShapeButton" -> shapeButton = (ShapeButton) component;
            case "SizeSpinner" -> sizeSpinner = (SizeSpinner) component;
            case "SaveButton" -> saveButton = (SaveButton) component;
        }
    }

    /**
     * Разнообразные методы общения с компонентами.
     */

    @Override
    public void createGUI() {
        JFrame application = new JFrame("Paint");
        application.setSize(960, 600);
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel picturePanel = new JPanel();
        picturePanel.setBackground(Color.MAGENTA);

        JPanel instrumentsPanel = new JPanel();
        instrumentsPanel.setBackground(Color.YELLOW);

        Container contentPane;
        contentPane = application.getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        layout.putConstraint(SpringLayout.WEST, instrumentsPanel,6,SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, instrumentsPanel,6,SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, instrumentsPanel,-6,SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, instrumentsPanel,80,SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.NORTH, picturePanel,90,SpringLayout.NORTH, instrumentsPanel);
        layout.putConstraint(SpringLayout.WEST, picturePanel,6,SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.EAST, contentPane,6,SpringLayout.EAST, picturePanel);
        layout.putConstraint(SpringLayout.SOUTH, contentPane,6,SpringLayout.SOUTH, picturePanel);

        application.add(instrumentsPanel);
        application.add(picturePanel);
        application.setVisible(true);
    }
}
