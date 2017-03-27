package gui.forms;

import components.GUIHelper;
import components.ImagePanel;
import resources.Resources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;


/**
 * Created by Mykhailo on 08.05.2016.
 */
public class PhoneForm extends ImagePanel {
    private JPanel rootPanel;
    private JTextPane textPane;
    private JButton continium;
    private JFormattedTextField phoneNumber;
    private JPanel logoPanel;

    public void addContiniumActionListener(ActionListener listener) {
        continium.addActionListener(listener);
        phoneNumber.addActionListener(listener);
    }

    public void removeContiniumActionListener(ActionListener listener) {
        continium.removeActionListener(listener);
        phoneNumber.removeActionListener(listener);
    }

    public PhoneForm() {
        super(Resources.getBackground());

        GUIHelper.justifyTextField(textPane, -0.2f);
        textPane.setFont(Resources.getFontOpenSansRegular(18f));

        GUIHelper.clearBackground(phoneNumber);
        GUIHelper.setBottomLineBorder(phoneNumber, new EmptyBorder(0, 35, 0, 0));
        phoneNumber.setFormatterFactory(GUIHelper.getMaskFormatterFactory());

        GUIHelper.updateContiniumButton(continium, Resources.getFontOpenSansRegular(24f), "Продолжить");
    }

    public String getPhoneNumber() throws ParseException {
        phoneNumber.commitEdit();
        return (String) phoneNumber.getValue();
    }

    public void setFocus() {
        phoneNumber.requestFocusInWindow();
    }

    public void clear() {
        phoneNumber.setText("");
        phoneNumber.setValue("");
    }

    private void createUIComponents() {
        rootPanel = this;

        logoPanel = new ImagePanel(Resources.getLogo());

        phoneNumber = new JFormattedTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Resources.getPhoneIcon(), 10, 5, null);
            }
        };
    }
}
