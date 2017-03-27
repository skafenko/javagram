package gui.contacts;

import components.GUIHelper;
import org.javagram.dao.Person;
import org.javagram.dao.proxy.TelegramProxy;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;


/**
 * Created by Mykhailo on 20.07.2016.
 */
public class ContactsListForm extends JPanel {
    private JPanel rootPanel;
    private JPanel listContactsPanel;
    private JScrollPane scrollPane;
    private JList<Person> contactsList;

    {
        GUIHelper.decorateScrollPane(scrollPane);
    }

    private TelegramProxy telegramProxy;

    public ContactsListForm() {
        contactsList.setPreferredSize(null);
        listContactsPanel.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 0, GUIHelper.GRAY));
    }

    public void addListSelectionListener(ListSelectionListener listSelectionListener) {
        contactsList.addListSelectionListener(listSelectionListener);
    }

    public void removeListSelectionListener(ListSelectionListener listSelectionListener) {
        contactsList.removeListSelectionListener(listSelectionListener);
    }

    public void setTelegramProxy(TelegramProxy telegramProxy) {
        this.telegramProxy = telegramProxy;

        if (telegramProxy != null) {
            java.util.List<Person> persons = telegramProxy.getPersons();
            contactsList.setListData(persons.toArray(new Person[persons.size()]));
            contactsList.setCellRenderer(new ContactForm(telegramProxy));
        } else {
            contactsList.setCellRenderer(new DefaultListCellRenderer());
            contactsList.setListData(new Person[0]);
        }
    }

    public void setSelectedValue(Person person) {
        if (person != null) {
            ListModel<Person> model = contactsList.getModel();
            for (int i = 0; i < model.getSize(); i++) {
                if (model.getElementAt(i).equals(person)) {
                    contactsList.setSelectedIndex(i);
                    return;
                }
            }
        }
        contactsList.clearSelection();
    }

    public Person getSelectedValue() {
        return contactsList.getSelectedValue();
    }

    private void createUIComponents() {
        rootPanel = this;
    }
}
