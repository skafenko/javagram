package overlays;

import components.GUIHelper;
import components.OverlayBackground;
import org.javagram.dao.Me;
import org.javagram.dao.proxy.TelegramProxy;
import resources.Resources;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ProfileForm extends OverlayBackground {
    private JButton logoutButton;
    private JButton closeButton;
    private JPanel rootPanel;
    private JLabel titleName;
    private JTextField firstName;
    private JTextField lastName;
    private JButton saveButton;
    private JLabel phoneNumber;
    private JPanel mainPanel;
    private JPanel bottomPanel;

    private TelegramProxy telegramProxy;

    public ProfileForm() {

        GUIHelper.clearBoth(lastName, firstName);

        titleName.setFont(Resources.getFontOpenSansLight(42f));

        GUIHelper.setBottomLineBorder(firstName, null);
        GUIHelper.setBottomLineBorder(lastName, null);

        firstName.setFont(Resources.getFontOpenSansLight(32f));
        lastName.setFont(Resources.getFontOpenSansLight(32f));
        phoneNumber.setFont(Resources.getFontOpenSansLight(21f));
        logoutButton.setFont(Resources.getFontOpenSansLight(21f));

        GUIHelper.updateContiniumButton(saveButton, Resources.getFontOpenSansRegular(24f), "сохранить");

        closeButton.setIcon(new ImageIcon(Resources.getBackIcon()));
    }

    private void createUIComponents() {
        rootPanel = this;
    }

    public TelegramProxy getTelegramProxy() {
        return telegramProxy;
    }

    public void setTelegramProxy(TelegramProxy telegramProxy) {
        this.telegramProxy = telegramProxy;
        if (telegramProxy != null) {
            Me me = telegramProxy.getMe();
            firstName.setText(me.getFirstName());
            lastName.setText(me.getLastName());
            phoneNumber.setText(me.getPhoneNumber());
        }
        repaint();
    }

    public void addCloseActionListener(ActionListener actionListener) {
        closeButton.addActionListener(actionListener);
    }

    public void removeCloseActionListener(ActionListener actionListener) {
        closeButton.removeActionListener(actionListener);
    }

    public void addLogoutActionListener(ActionListener actionListener) {
        logoutButton.addActionListener(actionListener);
    }

    public void removeLogoutActionListener(ActionListener actionListener) {
        logoutButton.removeActionListener(actionListener);
    }

    public void addSaveActionListener(ActionListener actionListener) {
        saveButton.addActionListener(actionListener);
    }

    public void removeSaveActionListener(ActionListener actionListener) {
        saveButton.removeActionListener(actionListener);
    }
}
