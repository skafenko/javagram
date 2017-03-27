package gui.contacts;

import components.ImagePanel;
import org.javagram.dao.Person;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Mykhailo on 01.08.2016.
 */
public class PersonForm extends JPanel {
    private JPanel rootPanel;
    private JPanel photoPanel;
    private JLabel personName;

    private Person person;

    public PersonForm(Person person, int aligment) {
        this.person = person;
        personName.setFont(Resources.getFontOpenSansSemiBold(13f));
        setPerson(person);

        if (aligment == SwingConstants.LEFT)
            rootPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        else if (aligment == SwingConstants.RIGHT)
            rootPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    }

    public void setPerson(Person person) {
        this.person = person;
        if (person != null)
            personName.setText(person.getFirstName() + " " + person.getLastName());
        else
            personName.setText("");
    }

    public void setPhoto(BufferedImage photo) {
        ((ImagePanel) photoPanel).setImage(photo);
    }

    private void createUIComponents() {
        rootPanel = this;

        photoPanel = new ImagePanel(null);
    }
}
