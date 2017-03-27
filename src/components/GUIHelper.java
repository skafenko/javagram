package components;

import org.javagram.dao.Dialog;
import org.javagram.dao.Person;
import org.javagram.dao.proxy.TelegramProxy;
import resources.Resources;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mykhailo on 25.07.2016.
 */
public class GUIHelper {

    public static final Color GRAY = new Color(230, 230, 230);
    public static final Color BLUE = new Color(39, 170, 232);
    public static final Color DARK_BLUE = new Color(75, 65, 172);
    public static final Color RED = new Color(255, 76, 76);
    public static final Color BLACK = new Color(0, 0, 0, 0);

    public static BufferedImage getImage(TelegramProxy proxy, Person person, boolean small, boolean circle) {
        BufferedImage photo = getImage(proxy, person, small);
        if (circle)
            photo = makeCircle(photo);
        return photo;
    }

    public static void decorateScrollPane(JScrollPane scrollPane) {
        int width = 3;

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new MyScrollbarUI());
        verticalScrollBar.setPreferredSize(new Dimension(width, Integer.MAX_VALUE));

        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setUI(new MyScrollbarUI());
        horizontalScrollBar.setPreferredSize(new Dimension(Integer.MAX_VALUE, width));

        for (String corner : new String[]{ScrollPaneConstants.LOWER_RIGHT_CORNER, ScrollPaneConstants.LOWER_LEFT_CORNER,
                ScrollPaneConstants.UPPER_LEFT_CORNER, ScrollPaneConstants.UPPER_RIGHT_CORNER}) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.white);
            scrollPane.setCorner(corner, panel);
        }
    }

    public static BufferedImage getImage(TelegramProxy proxy, Person person, boolean small) {
        BufferedImage image = null;
        try {
            image = proxy.getPhoto(person, small);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (image == null)
            image = Resources.getUserIcon();
        return image;
    }

    public static BufferedImage makeCircle(BufferedImage image) {
        BufferedImage circle = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = circle.createGraphics();
        try {
            g2d.clip(new Ellipse2D.Double(0, 0, circle.getWidth(), circle.getHeight()));
            g2d.drawImage(image, 0, 0, null);
        } finally {
            g2d.dispose();
        }
        return circle;
    }

    public static void justifyTextField(JTextPane textPane, float lineSpacing) {
        SimpleAttributeSet sas = new SimpleAttributeSet();
        StyleConstants.setAlignment(sas, StyleConstants.ALIGN_CENTER);
        StyleConstants.setLineSpacing(sas, lineSpacing);
        StyledDocument textDoc = textPane.getStyledDocument();
        textDoc.setParagraphAttributes(0, textDoc.getLength(), sas, false);
        clearBackground(textPane); //for Nimbus
    }

    public static void updateContiniumButton(JButton button, Font font, String text) {
        button.setFont(font);
        button.setText(text.toUpperCase());
        button.setContentAreaFilled(false);
        button.setOpaque(true);
    }

    public static void clearBoth(JComponent... jComponents) {
        for (JComponent jComponent : jComponents) {
            clearBackground(jComponent);
            clearBorder(jComponent);
        }
    }

    public static void clearBackground(JComponent component) {
        component.setOpaque(false);
        component.setBackground(BLACK);//Для Nimbus
    }

    public static void clearBorder(JComponent component) {
        component.setBorder(BorderFactory.createEmptyBorder());
    }

    public static void setBottomLineBorder(JTextField field, Border anotherBorder) {
        Border fieldBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE);
        Border border = new CompoundBorder(fieldBorder, anotherBorder);
        field.setBorder(border);
    }

    public static String getLastMessage(Dialog dialog, Person person) {
        String lastMessage = dialog.getLastMessage().getText();
        if (dialog.getLastMessage().getReceiver().equals(person))
            return "Вы: " + lastMessage;
        return lastMessage;
    }

    public static String getOnlineUntil(TelegramProxy proxy, Person person) {
        DateFormat dataFormat = new SimpleDateFormat("dd.MM.yy");
        Date date = proxy.onlineUntil(person);
        if (date != null) {
            long time = new Date().getTime() - date.getTime();
            if (time <= 0)
                return "";

            if ((time /= 1000) < 60)
                return time + " сек.";
            else if ((time /= 60) < 60)
                return time + " мин.";
            else if ((time /= 60) < 24)
                return time + " год.";
            else
                return dataFormat.format(date);
        }
        return "";
    }

    public static Color makeTransparent(Color color, float transparency) {
        if (transparency < 0.0f || transparency > 1.0f)
            throw new IllegalArgumentException();
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(color.getAlpha() * transparency));
    }

    public static DefaultFormatterFactory getMaskFormatterFactory() {
        try {
            MaskFormatter formatter = new MaskFormatter(createMask());
            return new DefaultFormatterFactory(formatter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String createMask() {
        return "+380(##)###-##-##";//Ukraine
//        return "+7(###)###-##-##";//Russia
//        return "+45####-####"; //Danmark
    }

}
