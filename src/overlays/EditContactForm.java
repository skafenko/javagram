package overlays;

import components.GUIHelper;
import components.ImagePanel;
import components.OverlayBackground;
import resources.Resources;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by Mykhailo on 18.08.2016.
 */
public class EditContactForm extends OverlayBackground {
    private JPanel rootPanel;
    private JPanel mainPanel;
    private JLabel titleName;
    private JTextField firstName;
    private JButton saveContactButton;
    private JPanel bottomPanel;
    private JButton closeButton;
    private JButton deleteButton;
    private JPanel deletePanel;
    private JLabel phoneNumber;
    private JPanel photoPanel;
    private JPanel contactPanel;
    private JTextField lastName;

    private int id;

    public EditContactForm() {
        setContactInfo(new ContactInfo());
        GUIHelper.clearBoth(firstName, lastName, deleteButton);

        titleName.setFont(Resources.getFontOpenSansLight(42f));

        GUIHelper.setBottomLineBorder(firstName, null);
        GUIHelper.setBottomLineBorder(lastName, null);
        firstName.setFont(Resources.getFontOpenSansLight(32f));
        lastName.setFont(Resources.getFontOpenSansLight(32f));

        GUIHelper.updateContiniumButton(saveContactButton, Resources.getFontOpenSansRegular(24f), "сохранить");

        closeButton.setIcon(new ImageIcon(Resources.getBackIcon()));

        phoneNumber.setFont(Resources.getFontOpenSansSemiBold(18f));

        deleteButton.setFont(Resources.getFontOpenSansRegular(16f));
        Border fieldBorder = BorderFactory.createLineBorder(GUIHelper.RED, 2);
        Border border = new CompoundBorder(fieldBorder, BorderFactory.createEmptyBorder(2, 30, 2, 10));
        deleteButton.setBorder(border);
    }

    public void setContactInfo(ContactInfo info) {
        firstName.setText(info.getFirstName());
        lastName.setText(info.getLastName());
        phoneNumber.setText(info.getPhone());
        id = info.getId();
    }

    public ContactInfo getContactInfo() {
        return new ContactInfo(phoneNumber.getText(),
                firstName.getText().trim(),
                lastName.getText().trim().trim(),
                id);
    }

    public void addCloseActionListener(ActionListener actionListener) {
        closeButton.addActionListener(actionListener);
    }

    public void removeCloseActionListener(ActionListener actionListener) {
        closeButton.removeActionListener(actionListener);
    }

    public void addSaveButtonActionListener(ActionListener actionListener) {
        saveContactButton.addActionListener(actionListener);
    }

    public void removeSaveButtonActionListener(ActionListener actionListener) {
        saveContactButton.removeActionListener(actionListener);
    }

    public void addDeleteContactButtonActionListener(ActionListener actionListener) {
        deleteButton.addActionListener(actionListener);
    }

    public void removeDeleteContactButtonActionListener(ActionListener actionListener) {
        deleteButton.removeActionListener(actionListener);
    }

    private void createUIComponents() {
        rootPanel = this;

        deleteButton = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Resources.getTrashIcon(), 10, 8, null);
            }
        };

        photoPanel = new ImagePanel(null);
    }

    public void setPhoto(BufferedImage photo) {
        ((ImagePanel) photoPanel).setImage(photo);
    }
}
