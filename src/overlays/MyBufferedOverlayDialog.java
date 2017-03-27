package overlays;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class MyBufferedOverlayDialog extends JPanel {

    private Component background;
    private Component[] foregrounds;
    private int index;
    private OverlayLayout overlayLayout;

    private BufferedImage image;

    {
        overlayLayout = new OverlayLayout(this);
        setLayout(overlayLayout);
    }

    private JPanel fakeBackground = new JPanel() {
        @Override
        public void paint(Graphics g) {
            if(image == null || image.getHeight() != background.getHeight()
                    || image.getWidth() != background.getWidth()) {
                image = new BufferedImage(background.getWidth(), background.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D g2d = image.createGraphics();
                try {
                    background.paint(g2d);
                } finally {
                    g2d.dispose();
                }
            }
            g.drawImage(image, 0, 0, null);
        }
    };

    public MyBufferedOverlayDialog(Component background, Component... foregrounds) {

        this.foregrounds = Arrays.copyOf(foregrounds, foregrounds.length);
        for(Component foreground : foregrounds) {
            foreground.setVisible(false);
            add(foreground);
        }

        fakeBackground.setVisible(false);
        add(fakeBackground);

        this.background = background;
        this.background.setVisible(true);
        add(background);

        index = -1;
    }

    public void setIndex(int index) {
        if(index < 0)
            index = -1;
        else
            foregrounds[index] = foregrounds[index];

        if(this.index != index) {
            if(this.index >= 0 && index >= 0) {
                foregrounds[this.index].setVisible(false);
                foregrounds[index].setVisible(true);
                this.index = index;
            } else if(this.index >= 0) {
                foregrounds[this.index].setVisible(false);
                fakeBackground.setVisible(false);
                background.setVisible(true);
                this.index = -1;
                image = null;
            } else if(index >= 0) {
                image = null;
                foregrounds[index].setVisible(true);
                fakeBackground.setVisible(true);
                this.background.setVisible(false);
                this.index = index;
            }
        }
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }
}
