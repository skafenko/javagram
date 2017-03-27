package overlays;

import components.GUIHelper;
import components.OverlayBackground;
import resources.Resources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;

/**
 * Created by Mykhailo on 17.08.2016.
 */
public class AddContactForm extends OverlayBackground {
    private JPanel rootPanel;
    private JButton closeButton;
    private JLabel titleName;
    private JTextField firstName;
    private JTextField lastName;
    private JButton addContactButton;
    private JLabel bottom;
    private JPanel mainPanel;
    private JFormattedTextField phoneNumber;
    private JTextPane textPane;
    private JPanel bottomPanel;

    public AddContactForm() {

        setContactInfo(new ContactInfo());
        GUIHelper.clearBoth(phoneNumber, firstName, lastName);

        titleName.setFont(Resources.getFontOpenSansLight(42f));

        GUIHelper.justifyTextField(textPane, -0.2f);
        textPane.setFont(Resources.getFontOpenSansRegular(18f));

        GUIHelper.setBottomLineBorder(firstName, null);
        GUIHelper.setBottomLineBorder(lastName, null);
        firstName.setFont(Resources.getFontOpenSansLight(32f));
        lastName.setFont(Resources.getFontOpenSansLight(32f));

        GUIHelper.updateContiniumButton(addContactButton, Resources.getFontOpenSansRegular(24f), "добавить");

        closeButton.setIcon(new ImageIcon(Resources.getBackIcon()));

        GUIHelper.setBottomLineBorder(phoneNumber, new EmptyBorder(0, 35, 0, 0));
        phoneNumber.setFormatterFactory(GUIHelper.getMaskFormatterFactory());
    }

    private void createUIComponents() {
        rootPanel = this;

        phoneNumber = new JFormattedTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Resources.getPhoneIcon(), 10, 5, null);
            }
        };
    }

    public void setContactInfo(ContactInfo info) {
        firstName.setText(info.getFirstName());
        lastName.setText(info.getLastName());
        phoneNumber.setText(info.getPhone());
        phoneNumber.setValue(info.getPhone());
    }

    public ContactInfo getContactInfo() {
        return new ContactInfo(getPhoneNumber(),
                firstName.getText().trim(),
                lastName.getText().trim());

    }

    private String getPhoneNumber() {
        try {
            phoneNumber.commitEdit();
            return (String) phoneNumber.getValue();
        } catch (ParseException e) {
            return "";
        }
    }

    public void addCloseActionListener(ActionListener actionListener) {
        closeButton.addActionListener(actionListener);
    }

    public void removeCloseActionListener(ActionListener actionListener) {
        closeButton.removeActionListener(actionListener);
    }

    public void addContactActionListener(ActionListener actionListener) {
        addContactButton.addActionListener(actionListener);
    }

    public void removeAddContactActionListener(ActionListener actionListener) {
        addContactButton.removeActionListener(actionListener);
    }
}
