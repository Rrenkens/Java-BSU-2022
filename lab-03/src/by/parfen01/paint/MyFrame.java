package by.parfen01.paint;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MyFrame extends JFrame {
    private final DrawingPanel drawingPanel = new DrawingPanel();
    private JFileChooser fileChooser = new JFileChooser();


    public MyFrame(String string) {
        super(string);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(200, 200, 550, 500);
        setResizable(false);

        JLabel mainPanel = new JLabel("");
        add(mainPanel);

        ButtonGroup option = new ButtonGroup();
        JRadioButton pen = new JRadioButton("pen");
        JRadioButton circle = new JRadioButton("ellipse");
        JRadioButton rectangle = new JRadioButton("rectangle");

        option.add(pen);
        option.add(circle);
        option.add(rectangle);

        mainPanel.add(pen);
        mainPanel.add(circle);
        mainPanel.add(rectangle);

        pen.setBounds(10, 30, 60, 20);
        circle.setBounds(80, 30, 60, 20);
        rectangle.setBounds(160, 30, 80, 20);

        pen.setSelected(true);

        pen.addActionListener(e -> drawingPanel.setOption(Option.PEN));
        circle.addActionListener(e -> drawingPanel.setOption(Option.ELLIPSE));
        rectangle.addActionListener(e -> drawingPanel.setOption(Option.RECTANGLE));

        ButtonGroup color = new ButtonGroup();
        JRadioButton red = new JRadioButton("red");
        JRadioButton green = new JRadioButton("green");
        JRadioButton blue = new JRadioButton("blue");

        color.add(red);
        color.add(green);
        color.add(blue);

        mainPanel.add(red);
        mainPanel.add(green);
        mainPanel.add(blue);

        red.setBounds(10, 10, 60, 20);
        green.setBounds(80, 10, 60, 20);
        blue.setBounds(160, 10, 60, 20);

        green.setSelected(true);

        red.addActionListener(e -> drawingPanel.setColor(Color.RED));
        blue.addActionListener(e -> drawingPanel.setColor(Color.BLUE));
        green.addActionListener(e -> drawingPanel.setColor(Color.GREEN));

        JButton clearButton = new JButton("Clear");
        mainPanel.add(clearButton);
        clearButton.setBounds(320, 15, 65, 40);
        clearButton.addActionListener(e -> drawingPanel.clear());

        JButton openButton = new JButton("Open");
        mainPanel.add(openButton);
        openButton.setBounds(395, 15, 65, 40);
        openButton.addActionListener(e -> {
            fileChooser = new JFileChooser(".");
            int ret = fileChooser.showDialog(null, "Select");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    drawingPanel.setImage(ImageIO.read(file));
                    setPreferredSize(new Dimension(drawingPanel.getImage().getWidth(), drawingPanel.getImage().getHeight()));
                    drawingPanel.setGraphics(drawingPanel.getImage().createGraphics());
                    repaint();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        JButton saveButton = new JButton("Save");
        mainPanel.add(saveButton);
        saveButton.setBounds(470, 15, 65, 40);

        saveButton.addActionListener(e -> {
            fileChooser = new JFileChooser(".");
            int value = fileChooser.showDialog(null, "Select");
            if (value == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    ImageIO.write(drawingPanel.getImage(), "jpg", file);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        JSpinner spinner = new JSpinner();
        spinner.setValue(1);
        ((SpinnerNumberModel) spinner.getModel()).setMinimum(1);
        ((SpinnerNumberModel) spinner.getModel()).setMaximum(10);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
        mainPanel.add(spinner);
        spinner.setBounds(260, 15, 50, 40);
        spinner.addChangeListener(e -> drawingPanel.setFontSize((int) spinner.getValue()));

        drawingPanel.setPreferredSize(new Dimension(600, 600));
        JScrollPane pane = new JScrollPane(drawingPanel);
        pane.setViewportView(drawingPanel);
        pane.createVerticalScrollBar();
        mainPanel.add(pane);
        pane.setBounds(0, 70, 536, 393);
    }
}