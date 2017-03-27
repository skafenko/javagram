package gui.message;

import components.GUIHelper;
import resources.Resources;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mykhailo on 03.08.2016.
 */
public class MessageForm extends JPanel {
    private JEditorPane textPane = new JEditorPane();
    private JLabel dateLabel = new JLabel();
    private BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

    private Color color;

    private final int MARGIN = 5;
    private final int RADIUS = 25;

    public MessageForm(String text, String date, int width, Color color, String fontColor) {
        setLayout(boxLayout);
        setOpaque(false);
        GUIHelper.clearBoth(textPane, dateLabel);
        textPane.setAlignmentX(0.05f);
        add(textPane);

        dateLabel.setAlignmentX(0.0f);
        dateLabel.setFont(Resources.getFontOpenSansRegular(10f));
        dateLabel.setForeground(Color.gray);
        add(dateLabel);

        textPane.setFont(Resources.getFontOpenSansSemiBold(12f));
        textPane.setForeground(Color.gray);
        textPane.setContentType("text/html");
        textPane.setSize(width, Short.MAX_VALUE);
        String htmlText = "<HTML><BODY><TABLE color=\"" + fontColor + "\" style=\"table-layout: fixed; width:" +
                width + "px; max-width:" + width + "px;\"><TR><TD style=\"word-wrap: break-word; width:" +
                width + "px; max-width:" + width + "px;\">" + text.replaceAll("\n", "<br/>") + "</TD></TR></TABLE></BODY></HTML>";
        textPane.setText(htmlText);
        textPane.setOpaque(false);
        textPane.setEditable(false);
        textPane.setMargin(new Insets(MARGIN, MARGIN, MARGIN, MARGIN));

        dateLabel.setText(date);
        this.color = color;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(color);
        graphics.fillRoundRect(textPane.getX(), textPane.getY(), textPane.getWidth(), textPane.getHeight(), RADIUS, RADIUS);
    }
}
