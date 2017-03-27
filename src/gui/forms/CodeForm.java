package gui.forms;

import components.GUIHelper;
import components.ImagePanel;
import components.MyNumberFilter;
import resources.Resources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Mykhailo on 09.05.2016.
 */
public class CodeForm extends ImagePanel {
    private JPanel rootPanel;
    private JTextPane phoneNumber;
    private JPasswordField code;
    private JButton continium;
    private JPanel logoMiniPane;
    private JTextPane textPane;
    private int maxLength = 5;

    public CodeForm() {
        super(Resources.getBackground());
        PlainDocument doc = (PlainDocument) code.getDocument();
        doc.setDocumentFilter(new MyNumberFilter(maxLength));

        GUIHelper.justifyTextField(textPane, -0.2f);
        textPane.setFont(Resources.getFontOpenSansRegular(16f));

        GUIHelper.updateContiniumButton(continium, Resources.getFontOpenSansRegular(24f), "Продолжить");

        GUIHelper.clearBoth(phoneNumber, code);
        phoneNumber.setFont(Resources.getFontOpenSansLight(42f));

        GUIHelper.setBottomLineBorder(code, new EmptyBorder(0, 60, 0, 0));
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.setText(phoneNumber);
    }

    public void addContiniumActionListener(ActionListener listener) {
        continium.addActionListener(listener);
        code.addActionListener(listener);
    }

    public void removeContiniumActionListener(ActionListener listener) {
        continium.removeActionListener(listener);
        code.removeActionListener(listener);
    }

    public void setFocus() {
        code.requestFocusInWindow();
    }

    public String getSMSCode() {
        return String.valueOf(code.getPassword());
    }

    public void clear() {
        phoneNumber.setText("");
        code.setText("");
    }

    private void createUIComponents() {
        rootPanel = this;

        logoMiniPane = new ImagePanel(Resources.getLogoMini());

        code = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Resources.getLockIcon(), 10, 5, null);
            }
        };
    }
}
