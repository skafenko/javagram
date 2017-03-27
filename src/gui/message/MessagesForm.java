package gui.message;

import components.GUIHelper;
import org.javagram.dao.Me;
import org.javagram.dao.Message;
import org.javagram.dao.Person;
import org.javagram.dao.proxy.TelegramProxy;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Mykhailo on 01.08.2016.
 */
public class MessagesForm extends JPanel {
    private JPanel rootPanel;
    private JScrollPane scrollPane;
    private JPanel messagePanel;

    private final int width = 150;
    private final int messagesCount = 50;
    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm dd MMM yyyy");

    private TelegramProxy telegramProxy;
    private Person person;

    {
        GUIHelper.decorateScrollPane(scrollPane);
    }

    public MessagesForm(TelegramProxy telegramProxy) {
        this(telegramProxy, null);
    }

    public MessagesForm(TelegramProxy telegramProxy, Person person) {
        this.telegramProxy = telegramProxy;
        display(person);
    }

    public void display(Person person) {
        messagePanel.removeAll();
        this.person = person;

        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.add(Box.createGlue());

        if (person == null)
            return;

        List<Message> messages = telegramProxy.getMessages(person, messagesCount);

        for (int i = messages.size() - 1; i >= 0; i--) {
            JPanel panel = new JPanel() {
                @Override
                public Dimension getMaximumSize() {
                    //Исправляем погань, на которую способен только BoxLayout
                    //Разрешаем растягиваться только по горизонтали
                    Dimension maxSize = super.getMaximumSize();
                    Dimension prefSize = super.getPreferredSize();
                    return new Dimension(maxSize.width, prefSize.height);
                }
            };
            Message message = messages.get(i);
            int alignment;
            Color color;
            String fontColor = "white";
            if (message.getReceiver() instanceof Me) {
                alignment = FlowLayout.LEFT;
                color = GUIHelper.BLUE;
            } else if (message.getSender() instanceof Me) {
                alignment = FlowLayout.RIGHT;
                color = GUIHelper.DARK_BLUE;
            } else {
                alignment = FlowLayout.CENTER;
                color = Color.red;
            }
            panel.setLayout(new FlowLayout(alignment));
            panel.add(new MessageForm(message.getText(), dateFormat.format(message.getDate()), width, color, fontColor));
            messagePanel.add(panel);
        }

        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }

    public TelegramProxy getTelegramProxy() {
        return telegramProxy;
    }

    private void createUIComponents() {
        rootPanel = this;
    }

    public Person getPerson() {
        return person;
    }
}
