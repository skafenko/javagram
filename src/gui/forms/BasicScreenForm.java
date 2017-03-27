package gui.forms;

import components.GUIHelper;
import components.HintTextField;
import components.ImagePanel;
import gui.contacts.PersonForm;
import org.javagram.dao.Me;
import org.javagram.dao.Person;
import resources.Resources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


/**
 * Created by Mykhailo on 10.05.2016.
 */
public class BasicScreenForm extends JPanel {
    private JPanel rootPanel;
    private JPanel messagePane;
    private JPanel titlePanel;
    private JButton gearButton;
    private JPanel mePanel;
    private JPanel contactPanel;
    private JPanel opponentPanel;
    private JPanel messageTextPanel;
    private JPanel messagesPanel;
    private JButton sendMessageButton;
    private JScrollPane messageScrollPane;
    private JTextArea messageTextArea;
    private JButton editButton;
    private JPanel penFriendPanel;
    private JPanel logoPanel;
    private JPanel searchPanel;
    private JTextField searchTextField;
    private JButton searchButton;
    private JPanel contactsPanel;

    public BasicScreenForm() {

        GUIHelper.decorateScrollPane(messageScrollPane);
        gearButton.setIcon(new ImageIcon(Resources.getSettingsIcon()));

        opponentPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.lightGray));

        sendMessageButton.setIcon(new ImageIcon((Resources.getSendButton())));
        messageTextArea.setFont(Resources.getFontOpenSansRegular(18f));

        editButton.setIcon(new ImageIcon(Resources.getEditIcon()));

        searchButton.setIcon(new ImageIcon(Resources.getSearchIcon()));
        searchPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
        searchTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        searchTextField.setFont(Resources.getFontOpenSansLight(18f));

        setKeyListener(messageTextArea);
    }

    public String getSearchText() {
        return searchTextField.getText().trim();
    }

    public void addSearchButtonActionListener(ActionListener listener) {
        searchButton.addActionListener(listener);
        searchTextField.addActionListener(listener);
    }

    public void removeSearchButtonActionListener(ActionListener listener) {
        searchButton.removeActionListener(listener);
        searchTextField.removeActionListener(listener);
    }

    public void addGearButtonActionListener(ActionListener listener) {
        gearButton.addActionListener(listener);
    }

    public void removeGearButtonActionListener(ActionListener listener) {
        gearButton.removeActionListener(listener);
    }

    public void addEditButtonActionListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void removeEditButtonActionListener(ActionListener listener) {
        editButton.removeActionListener(listener);
    }

    public void addSendMessageButtonActionListener(ActionListener listener) {
        sendMessageButton.addActionListener(listener);
    }

    public void removeSendMessageButtonActionListener(ActionListener listener) {
        sendMessageButton.removeActionListener(listener);
    }

    public void setMe(Me me) {
        ((PersonForm) mePanel).setPerson(me);
        repaint();
    }

    public void setMePhoto(BufferedImage photo) {
        ((PersonForm) mePanel).setPhoto(photo);
        repaint();
    }

    public void setPerson(Person person) {
        ((PersonForm) penFriendPanel).setPerson(person);
        repaint();

    }

    public void setPersonPhoto(BufferedImage photo) {
        ((PersonForm) penFriendPanel).setPhoto(photo);
        repaint();
    }

    public void setPersonEditEnabled(boolean enabled) {
        editButton.setEnabled(enabled);
    }

    public void setContactPanel(Component contactPanel) {
        this.contactsPanel.removeAll();
        this.contactsPanel.add(contactPanel, BorderLayout.CENTER);
    }

    public void setMessagePanel(Component messagePane1) {
        messagesPanel.removeAll();
        messagesPanel.add(messagePane1);
    }

    private void createUIComponents() {
        rootPanel = this;
        logoPanel = new ImagePanel(Resources.getLogoMicro());
        mePanel = new PersonForm(null, SwingConstants.RIGHT);
        penFriendPanel = new PersonForm(null, SwingConstants.LEFT);
        searchTextField = new HintTextField("Поиск");
    }

    public Component getMessagesPanel() {
        return this.messagesPanel.getComponent(0);
    }

    private void setKeyListener(JTextComponent component) {
        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (e.isControlDown())
                        sendMessageButton.doClick();
                }

            }
        });
    }

    public String getMessageText() {
        return messageTextArea.getText();
    }

    public void setMessageText(String text) {
        messageTextArea.setText(text);
    }
}
