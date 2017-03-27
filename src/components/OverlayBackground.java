package components;

import components.GUIHelper;

import javax.swing.*;
import java.awt.*;

public class OverlayBackground extends JPanel {

    private Color overlayColor;

    public OverlayBackground(Color overlayColor) {
        super();
        this.overlayColor = overlayColor;
    }

    public OverlayBackground() {
        this(GUIHelper.makeTransparent(Color.black, 0.7f));
    }

    public Color getOverlayColor() {
        return overlayColor;
    }

    public void setOverlayColor(Color overlayColor) {
        if (this.overlayColor == overlayColor) {
            this.overlayColor = overlayColor;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (overlayColor != null) {
            graphics.setColor(overlayColor);
            graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }
}
