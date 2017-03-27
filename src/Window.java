import components.GUIHelper;
import gui.Undecorated;
import gui.contacts.ContactsListForm;
import gui.contacts.PlusButtonForm;
import gui.forms.BasicScreenForm;
import gui.forms.CodeForm;
import gui.forms.PhoneForm;
import gui.forms.RegistrationForm;
import gui.message.MessagesForm;
import org.javagram.dao.Contact;
import org.javagram.dao.Me;
import org.javagram.dao.Person;
import org.javagram.dao.TelegramDAO;
import org.javagram.dao.proxy.TelegramProxy;
import org.javagram.dao.proxy.changes.UpdateChanges;
import overlays.*;
import window.ComponentResizerAbstract;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

/**
 * Created by Mykhailo on 08.05.2016.
 */
public class Window extends JFrame {
    private final String title = "Javagram";
    private final int minWidth = 900;
    private final int minHeight = 550;
    private final int width = 900;
    private final int height = 600;

    private Undecorated undecoratedFrame;

    private PhoneForm phoneForm = new PhoneForm();
    private CodeForm codeForm = new CodeForm();
    private BasicScreenForm basicScreenForm = new BasicScreenForm();
    private RegistrationForm registrationForm = new RegistrationForm();
    private ContactsListForm contactsListForm = new ContactsListForm();
    private PlusButtonForm plusButtonForm = new PlusButtonForm();
    private MyLayeredPane myLayeredPane = new MyLayeredPane();

    private ProfileForm profileForm = new ProfileForm();
    private AddContactForm addContactForm = new AddContactForm();
    private EditContactForm editContactForm = new EditContactForm();
    private MyBufferedOverlayDialog mainWindowManager = new MyBufferedOverlayDialog(basicScreenForm, profileForm, addContactForm, editContactForm);
    private static final int MAIN_WINDOW = -1, PROFILE_FORM = 0, ADD_CONTACT_FORM = 1, EDIT_CONTACT_FORM = 2;

    private TelegramDAO telegramDAO;
    private TelegramProxy telegramProxy;

    private int messagesFrozen;
    private Timer timer;

    public Window(TelegramDAO telegramDAO) throws IOException {
        setTitle(title);
        undecoratedFrame = new Undecorated(this, ComponentResizerAbstract.KEEP_RATIO_CENTER);

        setMinimumSize(new Dimension(minWidth, minHeight));
        setSize(new Dimension(width, height));
        setLocationRelativeTo(null);

        this.telegramDAO = telegramDAO;

        initEvents();
        initJPane();
    }

    private void initJPane() {
        changeFormTo(phoneForm);

        basicScreenForm.setContactPanel(myLayeredPane);
        myLayeredPane.add(contactsListForm, new Integer(0));
        myLayeredPane.add(plusButtonForm, new Integer(1));

        timer = new Timer(2000, updatesListener);
        timer.start();
    }

    private void initEvents() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logOut();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                logOut();
            }
        });
        undecoratedFrame.addActionListenerForClose(closeWindow);
        undecoratedFrame.addActionListenerForMinimize(turnWindow);
        phoneForm.addContiniumActionListener(toCodeForm);
        codeForm.addContiniumActionListener(toBasicScreenForm);
        registrationForm.addContiniumActionListener(toCodeFormFromRegistration);
        basicScreenForm.addSearchButtonActionListener(searchContact);
        plusButtonForm.addPlusButtonActionListener(addContact);
        basicScreenForm.addGearButtonActionListener(meSettings);
        basicScreenForm.addEditButtonActionListener(editContact);
        basicScreenForm.addSendMessageButtonActionListener(sendMessage);
        contactsListForm.addListSelectionListener(contactsListener);
        profileForm.addCloseActionListener(comeBackToMainWindow);
        profileForm.addLogoutActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToBegin();
            }
        });
        profileForm.addSaveActionListener(saveChanging);
        addContactForm.addCloseActionListener(comeBackToMainWindow);
        addContactForm.addContactActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tryAddContact(addContactForm.getContactInfo());
            }
        });
        editContactForm.addCloseActionListener(comeBackToMainWindow);
        editContactForm.addSaveButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                trySaveContact(editContactForm.getContactInfo());
            }
        });
        editContactForm.addDeleteContactButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tryDeleteContact(editContactForm.getContactInfo());
            }
        });

    }

    private Action closeWindow = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    };

    private Action turnWindow = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setState(JFrame.ICONIFIED);
        }
    };

    private Action toCodeFormFromRegistration = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (registrationForm.isCorrectName()) {
                try {
                    sendCode();
                } catch (IOException e1) {
                    showWarningMessage("Your phone is invalid", "Invalid phone");
                }
            } else {
                showWarningMessage("Your name is uncorrect", "Uncorrect name");
            }
        }
    };

    private Action toCodeForm = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String phoneNumber = phoneForm.getPhoneNumber();

                telegramDAO.acceptNumber(phoneNumber);
                codeForm.setPhoneNumber(phoneNumber);

                if (telegramDAO.canSignIn()) {
                    sendCode();
                } else {
                    changeFormTo(registrationForm);
                    registrationForm.setFocus();
                }
            } catch (IOException e1) {
                showWarningMessage("Your phone is invalid", "Invalid phone");
            } catch (ParseException e1) {
                showWarningMessage("Your phone is uncorrect", "Uncorrect phone");
            }
        }
    };

    private Action toBasicScreenForm = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            final String smsCode = codeForm.getSMSCode();

            try {
                if (telegramDAO.canSignIn()) {
                    telegramDAO.signIn(smsCode);
                } else {
                    signUp(smsCode);
                }

                changeFormTo(mainWindowManager);
                createTelegramProxy();
            } catch (Exception e1) {
                showWarningMessage("Invalid code. Try again.", "Warning Message");
            }
        }
    };

    private Action searchContact = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchText = basicScreenForm.getSearchText();
            if (searchText.isEmpty())
                return;

            String[] words = searchText.toLowerCase().split("\\s+");
            List<Person> persons = telegramProxy.getPersons();
            Person person = contactsListForm.getSelectedValue();
            person = searchPerson(searchText.toLowerCase(), words, persons, person);

            if (person != null)
                contactsListForm.setSelectedValue(person);
            else
                showInformationMessage("Your search found nothing. Sorry", "I can't find");
        }
    };

    private Action addContact = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            addContactForm.setContactInfo(new ContactInfo());
            mainWindowManager.setIndex(ADD_CONTACT_FORM);
        }
    };

    private Action meSettings = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            profileForm.setTelegramProxy(telegramProxy);
            mainWindowManager.setIndex(PROFILE_FORM);
        }
    };

    private Action saveChanging = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showInformationMessage("Save changing about me", "Save Changing");
        }
    };

    private Action editContact = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Person person = contactsListForm.getSelectedValue();
            if (person instanceof Contact) {
                editContactForm.setContactInfo(new ContactInfo((Contact) person));
                editContactForm.setPhoto(GUIHelper.getImage(telegramProxy, person, false, true));
                mainWindowManager.setIndex(EDIT_CONTACT_FORM);
            }
        }
    };

    private Action sendMessage = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Person person = contactsListForm.getSelectedValue();
            String text = basicScreenForm.getMessageText().trim();
            if (telegramProxy != null && person != null && !text.isEmpty()) {
                try {
                    telegramProxy.sendMessage(person, text);
                    basicScreenForm.setMessageText("");
                    checkForUpdates(true);
                } catch (Exception ex) {
                    showErrorMessage("I can't send a message. Sorry", "ERROR!");
                }
            }
        }
    };

    private Action updatesListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            checkForUpdates(false);
        }
    };

    private Action comeBackToMainWindow = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            mainWindowManager.setIndex(MAIN_WINDOW);
        }
    };

    private ListSelectionListener contactsListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            if (messagesFrozen != 0)
                return;
            if (telegramProxy == null) {
                displayDialog(null);
            } else {
                displayDialog(contactsListForm.getSelectedValue());
            }
        }
    };

    private void logOut() {
        telegramDAO.close();
        System.exit(0);
    }

    private static Person searchPerson(String text, String[] words, java.util.List<? extends Person> persons, Person current) {
        int currentIndex = persons.indexOf(current);

        for (int i = 1; i <= persons.size(); i++) {
            int index = (currentIndex + i) % persons.size();
            Person person = persons.get(index);
            if (contains(person.getFirstName().toLowerCase(), words)
                    || contains(person.getLastName().toLowerCase(), words)) {
                return person;
            }
        }
        return null;
    }

    private static boolean contains(String name, String... keyWords) {
        for (String word : keyWords) {
            if (name.toLowerCase().contains(word))
                return true;
        }
        return false;
    }

    private void createTelegramProxy() {
        telegramProxy = new TelegramProxy(telegramDAO);
        updateTelegramProxy();
    }

    private void switchToBegin() {
        try {
            destroyTelegramProxy();
            codeForm.clear();
            phoneForm.clear();
            registrationForm.clear();
            mainWindowManager.setIndex(MAIN_WINDOW);
            changeFormTo(phoneForm);
            phoneForm.setFocus();
            telegramDAO.logOut();
        } catch (Exception e) {
            showErrorMessage("Can not proceed", "Critical error!");
            abort(e);
        }
    }

    private void destroyTelegramProxy() {
        telegramProxy = null;
        updateTelegramProxy();
    }

    private void abort(Exception e) {
        if (e != null)
            e.printStackTrace();
        else
            System.err.println("Unknown Error");
        telegramDAO.close();
        System.exit(-1);
    }

    private boolean tryAddContact(ContactInfo info) {
        String phone = info.getClearedPhone();

        if (phone.isEmpty()) {
            showWarningMessage("Contact's phone is uncorrect", "Uncorrect phone");
            return false;
        }

        if (isNameEmpty(info)) return false;

        for (Person person : telegramProxy.getPersons()) {
            if (person instanceof Contact) {
                if (((Contact) person).getPhoneNumber().replaceAll("\\D+", "").equals(phone)) {
                    showWarningMessage("Contact exist with this phone nomber", "Contact exist");
                    return false;
                }
            }
        }

        if (!telegramProxy.importContact(info.getPhone(), info.getFirstName(), info.getLastName())) {
            showWarningMessage("Server's error during add contact", "Server's error");
            return false;
        }

        mainWindowManager.setIndex(MAIN_WINDOW);
        checkForUpdates(true);
        return true;
    }

    private boolean trySaveContact(ContactInfo info) {
        String phone = info.getClearedPhone();

        if (isNameEmpty(info)) return false;

        if (!telegramProxy.importContact(info.getPhone(), info.getFirstName(), info.getLastName())) {
            showWarningMessage("Server's error during update contact", "Server's error");
            return false;
        }

        mainWindowManager.setIndex(MAIN_WINDOW);
        checkForUpdates(true);
        return true;
    }

    private boolean tryDeleteContact(ContactInfo info) {
        int id = info.getId();

        if (!telegramProxy.deleteContact(id)) {
            showErrorMessage("Server's error during delete contact", "Server's error");
            return false;
        }

        mainWindowManager.setIndex(MAIN_WINDOW);
        checkForUpdates(true);
        return true;
    }

    private boolean isNameEmpty(ContactInfo info) {
        if (info.getFirstName().isEmpty() && info.getLastName().isEmpty()) {
            showErrorMessage("Name or lastName must be not empty", "Uncorrect field");
            return true;
        }
        return false;
    }

    private void updateTelegramProxy() {
        messagesFrozen++;
        try {
            contactsListForm.setTelegramProxy(telegramProxy);
            displayMe(telegramProxy != null ? telegramProxy.getMe() : null);
            createMessagesForm();
            displayDialog(null);
        } finally {
            messagesFrozen--;
        }

        basicScreenForm.revalidate();
        basicScreenForm.repaint();
    }

    private void displayDialog(Person person) {
        try {
            MessagesForm messagesForm = getMessagesForm();
            messagesForm.display(person);
            displayBuddy(person);
            revalidate();
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Problem with connecting to server", "Server's error");
        }
    }

    private MessagesForm createMessagesForm() {
        MessagesForm messagesForm = new MessagesForm(telegramProxy);
        basicScreenForm.setMessagePanel(messagesForm);
        basicScreenForm.revalidate();
        basicScreenForm.repaint();
        return messagesForm;
    }

    private void displayMe(Me me) {
        if (me != null) {
            basicScreenForm.setMe(me);
            basicScreenForm.setMePhoto(GUIHelper.getImage(telegramProxy, me, true, true));
        } else {
            basicScreenForm.setMe(null);
            basicScreenForm.setMePhoto(null);
        }
    }

    private void displayBuddy(Person person) {
        if (person != null) {
            basicScreenForm.setPerson(person);
            basicScreenForm.setPersonPhoto(GUIHelper.getImage(telegramProxy, person, true, true));
        } else {
            basicScreenForm.setPerson(null);
            basicScreenForm.setPersonPhoto(null);
        }
        basicScreenForm.setPersonEditEnabled(person instanceof Contact);
    }

    protected void checkForUpdates(boolean force) {
        if (telegramProxy != null) {
            UpdateChanges updateChanges = telegramProxy.update(force ? TelegramProxy.FORCE_SYNC_UPDATE : TelegramProxy.USE_SYNC_UPDATE);

            int photosChangedCount = updateChanges.getLargePhotosChanged().size() +
                    updateChanges.getSmallPhotosChanged().size() +
                    updateChanges.getStatusesChanged().size();

            if (updateChanges.getListChanged()) {
                updateContacts();
            } else if (photosChangedCount != 0) {
                contactsListForm.repaint();
            }

            Person currentBuddy = getMessagesForm().getPerson();
            Person targetPerson = contactsListForm.getSelectedValue();

            org.javagram.dao.Dialog currentDialog = currentBuddy != null ? telegramProxy.getDialog(currentBuddy) : null;

            if (!Objects.equals(targetPerson, currentBuddy) ||
                    updateChanges.getDialogsToReset().contains(currentDialog) ||
                    updateChanges.getDialogsChanged().getDeleted().contains(currentDialog)) {
                updateMessages();
            } else if (updateChanges.getPersonsChanged().getChanged().containsKey(currentBuddy)
                    || updateChanges.getSmallPhotosChanged().contains(currentBuddy)
                    || updateChanges.getLargePhotosChanged().contains(currentBuddy)) {
                displayBuddy(targetPerson);
            }

            if (updateChanges.getPersonsChanged().getChanged().containsKey(telegramProxy.getMe())
                    || updateChanges.getSmallPhotosChanged().contains(telegramProxy.getMe())
                    || updateChanges.getLargePhotosChanged().contains(telegramProxy.getMe())) {
                displayMe(telegramProxy.getMe());
            }
        }
    }

    private MessagesForm getMessagesForm() {
        if (basicScreenForm.getMessagesPanel() instanceof MessagesForm) {
            return (MessagesForm) basicScreenForm.getMessagesPanel();
        } else {
            return createMessagesForm();
        }
    }

    private void updateMessages() {
        displayDialog(contactsListForm.getSelectedValue());
        basicScreenForm.revalidate();
        basicScreenForm.repaint();
    }

    private void updateContacts() {
        messagesFrozen++;
        try {
            Person person = contactsListForm.getSelectedValue();
            contactsListForm.setTelegramProxy(telegramProxy);
            contactsListForm.setSelectedValue(person);
        } finally {
            messagesFrozen--;
        }
    }

    private void sendCode() throws IOException {
        telegramDAO.sendCode();
        changeFormTo(codeForm);
        codeForm.setFocus();
    }

    private void showErrorMessage(Object message, String title) {
        Undecorated.showDialog(this, message, title, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null);
    }

    private void showInformationMessage(Object message, String title) {
        Undecorated.showDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null);
    }

    private void showWarningMessage(String text, String title) {
        Undecorated.showDialog(this, text, title, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null);
    }

    private void changeFormTo(Container contentPanel) {
        undecoratedFrame.setContentPanel(contentPanel);
    }

    private Me signUp(String smsCode) throws IOException {
        final String firstName = registrationForm.getFirstName();
        final String lastName = registrationForm.getLastName();

        return telegramDAO.signUp(smsCode, firstName, lastName);
    }
}
