package gui.contacts;

import resources.Resources;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by Mykhailo on 26.07.2016.
 */
public class PlusButtonForm extends JPanel {
    private JPanel rootPanel;
    private JButton plusButton;

    public PlusButtonForm() {
        plusButton.setIcon(new ImageIcon(Resources.getPlusIcon()));
    }

    public void addPlusButtonActionListener(ActionListener listener) {
        plusButton.addActionListener(listener);
    }

    public void removePlusButtonActionListener(ActionListener listener) {
        plusButton.removeActionListener(listener);
    }

    private void createUIComponents() {
        rootPanel = this;
    }
}
