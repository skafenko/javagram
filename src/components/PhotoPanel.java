package components;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Mykhailo on 25.07.2016.
 */
public class PhotoPanel extends ImagePanel {
    private boolean online;
    private final double onlineSignSize = 0.3;

    public PhotoPanel(BufferedImage image, boolean online) {
        super(image);
        this.online = online;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (online) {

            int dx = (int) (this.getWidth() * onlineSignSize);
            int dy = (int) (this.getHeight() * onlineSignSize);

            int x = this.getWidth() - dx;
            int y = this.getHeight() - dy;

            dx -= 2;
            dy -= 2;

            g.setColor(new Color(0x00B000));
            g.fillRoundRect(x, y, dx, dy, dx, dy);
        }
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        if (this.online != online) {
            this.online = online;
            repaint();
        }
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
}
