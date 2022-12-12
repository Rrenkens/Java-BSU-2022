package by.Dmi4er4.paint;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

class Paper extends JPanel {
    private int currentX_;
    private int oldX_;
    private int currentY_;
    private int oldY_;
    private BufferedImage image_;
    private Graphics2D graphics_;
    private Option option_ = Option.PEN;

    public Paper(int width, int height) {
        image_ = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphics_ = image_.createGraphics();
        graphics_.setColor(Color.BLACK);
        graphics_.setStroke(new BasicStroke(3));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                oldX_ = e.getX();
                oldY_ = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentX_ = e.getX();
                currentY_ = e.getY();
                if (option_ == Option.RECTANGLE) {
                    graphics_.drawRect(Math.min(oldX_, currentX_), Math.min(oldY_, currentY_),
                            Math.abs(currentX_ - oldX_), Math.abs(currentY_ - oldY_));
                } else if (option_ == Option.ELLIPSE) {
                    graphics_.drawOval(Math.min(oldX_, currentX_), Math.min(oldY_, currentY_),
                            Math.abs(currentX_ - oldX_), Math.abs(currentY_ - oldY_));
                }
                repaint();
                oldX_ = currentX_;
                oldY_ = currentY_;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (option_ == Option.PEN) {
                    currentX_ = e.getX();
                    currentY_ = e.getY();
                    graphics_.drawLine(oldX_, oldY_, currentX_, currentY_);
                    repaint();
                    oldX_ = currentX_;
                    oldY_ = currentY_;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(image_, 0, 0, this);
    }

    public void SetColor(Color color) {
        graphics_.setColor(color);
    }

    public BufferedImage GetImage() {
        return image_;
    }

    public void setOption(Option option) {
        option_ = option;
    }

    public void setImage(BufferedImage image) {
        image_ = image;
        Stroke font_size = graphics_.getStroke();
        Color color = graphics_.getColor();
        graphics_ = image.createGraphics();
        graphics_.setStroke(font_size);
        graphics_.setColor(color);
        repaint();
    }

    public void SetFontSize(int font_size) {
        graphics_.setStroke(new BasicStroke(font_size));
    }

    public void Clear() {
        Color color = graphics_.getColor();
        graphics_.setPaint (Color.WHITE);
        graphics_.fillRect( 0, 0, getWidth(), getHeight() );
        graphics_.setColor(color);
        repaint();
    }
}

