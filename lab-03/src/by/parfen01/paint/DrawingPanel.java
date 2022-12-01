package by.parfen01.paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class DrawingPanel extends JPanel {
    private Option option;
    private BufferedImage image;
    private Graphics graphics;
    private Color color;
    private int currentX;
    private int currentY;
    private int oldX;
    private int oldY;

    public DrawingPanel() {
        image = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
        graphics = image.createGraphics();
        color = Color.GREEN;
        option = Option.PEN;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                graphics.setColor(color);
                if (option == Option.RECTANGLE) {
                    graphics.drawRect(Math.min(oldX, currentX), Math.min(oldY, currentY),
                            Math.abs(currentX - oldX), Math.abs(currentY - oldY));
                } else if (option == Option.ELLIPSE) {
                    graphics.drawOval(Math.min(oldX, currentX), Math.min(oldY, currentY),
                            Math.abs(currentX - oldX), Math.abs(currentY - oldY));
                }
                repaint();
                oldX = currentX;
                oldY = currentY;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (option == Option.PEN) {
                    currentX = e.getX();
                    currentY = e.getY();
                    graphics.setColor(color);
                    graphics.drawLine(oldX, oldY, currentX, currentY);
                    repaint();
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(image, 0, 0, this);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setOption(Option option) {
        this.option = option;
    }

     public void setImage(BufferedImage image) {
        this.image = image;
     }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    public void clear() {
        graphics.clearRect(0, 0, getWidth(), getHeight());
        repaint();
    }
}

