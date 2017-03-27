package gui.forms;

import components.GUIHelper;
import components.HintTextField;
import components.ImagePanel;
import resources.Resources;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by Mykhailo on 15.05.2016.
 */
public class RegistrationForm extends ImagePanel {
    private JPanel rootPanel;
    private JTextPane textPane;
    private JTextField firstName;
    private JTextField lastName;
    private JButton continium;
    private JPanel logoMiniPane;

    public RegistrationForm() {
        super(Resources.getBackground());
        GUIHelper.clearBoth(firstName, lastName);

        GUIHelper.updateContiniumButton(continium, Resources.getFontOpenSansRegular(24f), "Продолжить");

        GUIHelper.justifyTextField(textPane, -0.2f);
        textPane.setFont(Resources.getFontOpenSansRegular(16f));

        GUIHelper.setBottomLineBorder(firstName, null);
        GUIHelper.setBottomLineBorder(lastName, null);

        firstName.setFont(Resources.getFontOpenSansLight(32f));
        lastName.setFont(Resources.getFontOpenSansLight(32f));
    }

    public boolean isCorrectName() {
        return !(getFirstName().isEmpty() || getLastName().isEmpty());
    }

    public void addContiniumActionListener(ActionListener listener) {
        continium.addActionListener(listener);
        firstName.addActionListener(listener);
        lastName.addActionListener(listener);
    }

    public void removeContiniumActionListener(ActionListener listener) {
        continium.removeActionListener(listener);
        firstName.removeActionListener(listener);
        lastName.removeActionListener(listener);
    }

    public String getFirstName() {
        return firstName.getText().trim();
    }

    public String getLastName() {
        return lastName.getText().trim();
    }

    public void setFocus() {
        firstName.requestFocusInWindow();
    }

    public void clear() {
        firstName.setText("");
        lastName.setText("");
    }

    private void createUIComponents() {
        rootPanel = this;
        logoMiniPane = new ImagePanel(Resources.getLogoMini());
        firstName = new HintTextField("Имя");
        lastName = new HintTextField("Фамилия");
    }
}
