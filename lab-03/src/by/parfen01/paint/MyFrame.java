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
        setBounds(200, 200, 500, 500);
        setResizable(false);
        ButtonGroup option = new ButtonGroup();
        JRadioButton pen = new JRadioButton("pen");
        JRadioButton circle = new JRadioButton("ellipse");
        JRadioButton rectangle = new JRadioButton("rectangle");

        option.add(pen);
        option.add(circle);
        option.add(rectangle);

        add(pen);
        add(circle);
        add(rectangle);

        pen.setBounds(10, 30, 80, 20);
        circle.setBounds(100, 30, 80, 20);
        rectangle.setBounds(190, 30, 80, 20);

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

        add(red);
        add(green);
        add(blue);

        red.setBounds(10, 10, 80, 20);
        green.setBounds(100, 10, 80, 20);
        blue.setBounds(190, 10, 80, 20);

        green.setSelected(true);

        red.addActionListener(e -> drawingPanel.setColor(Color.RED));
        blue.addActionListener(e -> drawingPanel.setColor(Color.BLUE));
        green.addActionListener(e -> drawingPanel.setColor(Color.GREEN));

        JButton clearButton = new JButton("Clear");
        add(clearButton);
        clearButton.setBounds(270, 15, 65, 40);
        clearButton.addActionListener(e -> drawingPanel.clear());

        JButton openButton = new JButton("Open");
        add(openButton);
        openButton.setBounds(345, 15, 65, 40);
        openButton.addActionListener(e -> {
            fileChooser = new JFileChooser(".");
            int ret = fileChooser.showDialog(null, "Select");
            if (ret == JFileChooser.APPROVE_OPTION){
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
        add(saveButton);
        saveButton.setBounds(420, 15, 65, 40);

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

        drawingPanel.setPreferredSize(new Dimension(600, 600));
        JScrollPane pane = new JScrollPane(drawingPanel);
        pane.setViewportView(drawingPanel);
        pane.createVerticalScrollBar();
        add(pane);
        pane.setBounds(0, 70, 486, 393);
        // для чёрной магии
        add(new JLabel(""));
    }
}