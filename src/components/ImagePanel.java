package components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Mykhailo on 25.07.2016.
 */
public class ImagePanel extends JPanel {
    protected BufferedImage image;

    {
        setOpaque(false);
    }

    public ImagePanel(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        if (this.image != image) {
            this.image = image;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image == null) {
            super.paintComponent(g);
        } else {
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }

    @Override
    protected void paintBorder(Graphics graphics) {
        if (image == null) {
            super.paintBorder(graphics);
        }
    }
}
