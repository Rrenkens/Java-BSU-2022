package by.Dmi4er4.paint;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

class MainWindow extends JFrame {

    int kHeight = 20;
    int kSpace = 10;
    public MainWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        Paper paper = new Paper((int) screen_size.getWidth(), (int) screen_size.getHeight() - kHeight - 2*kSpace);
        paper.setBounds(0, kHeight + 2*kSpace, (int) screen_size.getWidth(), (int) screen_size.getHeight() - kHeight - 2*kSpace);
        add(paper);

        JLabel option_label = new JLabel("Drawing option:");
        option_label.setBounds(10, kSpace, 100, kHeight);
        add(option_label);

        JComboBox<String> option = new JComboBox<>(new String[]{"Pen", "Ellipse", "Rectangle"});
        option.setBounds(110, kSpace, 100, kHeight);
        option.addActionListener(e -> {
            String temp_option = (String) ((JComboBox) e.getSource()).getSelectedItem();
            switch (Objects.requireNonNull(temp_option)) {
                case ("Pen") -> {
                    paper.setOption(Option.PEN);
                }
                case ("Ellipse") -> {
                    paper.setOption(Option.ELLIPSE);
                }
                case ("Rectangle") -> {
                    paper.setOption(Option.RECTANGLE);
                }
            }
        });
        add(option);

        JButton color = new JButton("Choose color");
        add(color);
        color.setBounds(220, kSpace, 120, kHeight);
        color.addActionListener(e -> {
            Color initial_color = Color.BLACK;
            Color temp_color = JColorChooser.showDialog(this, "Select a color", initial_color);
            paper.SetColor(temp_color);
        });
        add(color);

        JLabel width_label = new JLabel("Pen width:");
        width_label.setBounds(350, kSpace, 60, kHeight);
        add(width_label);

        SpinnerNumberModel width_model = new SpinnerNumberModel(3, 1, 30, 1);
        JSpinner width = new JSpinner(width_model);
        width.setEditor(new JSpinner.DefaultEditor(width));
        width.setBounds(420, kSpace, 40, kHeight);
        width.addChangeListener(e -> paper.SetFontSize((int) width.getValue()));
        add(width);

        JButton clear_button = new JButton("Clear");
        add(clear_button);
        clear_button.setBounds(470, kSpace, 70, kHeight);
        clear_button.addActionListener(e -> paper.Clear());

        JButton open_button = new JButton("Open");
        open_button.setBounds(550, kSpace, 70, kHeight);
        open_button.addActionListener(e -> {
            JFileChooser file_chooser = new JFileChooser(".");
            if (file_chooser.showDialog(null, "Select") == JFileChooser.APPROVE_OPTION) {
                File file = file_chooser.getSelectedFile();
                try {
                    paper.setImage(ImageIO.read(file));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        add(open_button);

        JButton save_button = new JButton("Save");
        save_button.setBounds(630, kSpace, 70, kHeight);
        save_button.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(".");
            if (fileChooser.showDialog(null, "Select") == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    ImageIO.write(paper.GetImage(), "jpg", file);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        add(save_button);

        JLabel advertisement_label = new JLabel("[Place for your advertisement]");
        advertisement_label.setBounds(910, kSpace, 200, kHeight);
        add(advertisement_label);

        JButton exit_button = new JButton("Exit");
        exit_button.setBounds((int) screen_size.getWidth() - 80, kSpace, 70, kHeight);
        exit_button.addActionListener(e -> System.exit(0));
        add(exit_button);
        add(new JLabel());
        clear_button.doClick();
    }
}
