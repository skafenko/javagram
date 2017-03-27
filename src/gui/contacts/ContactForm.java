package gui.contacts;

import components.GUIHelper;
import components.PhotoPanel;
import org.javagram.dao.Dialog;
import org.javagram.dao.Person;
import org.javagram.dao.proxy.TelegramProxy;
import resources.Resources;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Mykhailo on 01.06.2016.
 */
public class ContactForm extends JPanel implements ListCellRenderer<Person> {
    private JPanel rootPanel;
    private JLabel contactName;
    private JPanel photoPanel;
    private JLabel lastMessage;
    private JLabel timeLastMessage;

    private TelegramProxy telegramProxy;
    private Person person;
    private boolean isSelected;

    private int focusMarkerWidth = 3;

    public ContactForm(TelegramProxy telegramProxy) {
        this.telegramProxy = telegramProxy;

        lastMessage.setFont(Resources.getFontOpenSansSemiBold(12f));
        contactName.setFont(Resources.getFontOpenSansSemiBold(16f));
        timeLastMessage.setFont(Resources.getFontOpenSansSemiBold(8f));

        Border fieldBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray);
        Border empty = new EmptyBorder(0, 20, 0, 0);
        Border border = new CompoundBorder(fieldBorder, empty);
        rootPanel.setBorder(border);
    }

    private void createUIComponents() {
        rootPanel = this;
        photoPanel = new PhotoPanel(null, false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isSelected) {
            g.setColor(GUIHelper.BLUE);
            g.fillRect(this.getWidth() - focusMarkerWidth, 0, focusMarkerWidth, this.getHeight());
        }
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Person> list, Person person, int index, boolean isSelected, boolean hasFocus) {
        this.person = person;
        contactName.setText(person.getFirstName() + " " + person.getLastName());
        Dialog dialog = telegramProxy.getDialog(person);

        timeLastMessage.setText(GUIHelper.getOnlineUntil(telegramProxy, person));

        if (dialog != null)
            lastMessage.setText(GUIHelper.getLastMessage(dialog, person));
        else
            lastMessage.setText("");

        if (isSelected)
            setBackground(Color.white);
        else
            setBackground(GUIHelper.GRAY);

        this.isSelected = isSelected;

        ((PhotoPanel) photoPanel).setImage(GUIHelper.getImage(telegramProxy, person, false, true));
        ((PhotoPanel) photoPanel).setOnline(telegramProxy.isOnline(person));

        return this;
    }
}
