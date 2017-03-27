package resources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Mykhailo on 29.06.2016.
 */
public class Resources {
    private static BufferedImage background;
    private static BufferedImage closeIcon;
    private static BufferedImage hideIcon;
    private static BufferedImage logo;
    private static BufferedImage logoMini;
    private static BufferedImage logoMicro;
    private static BufferedImage phoneIcon;
    private static BufferedImage lockIcon;
    private static BufferedImage settingsIcon;
    private static BufferedImage searchIcon;
    private static BufferedImage plusIcon;
    private static BufferedImage sendButton;
    private static BufferedImage editIcon;
    private static BufferedImage backIcon;
    private static BufferedImage trashIcon;

    private static BufferedImage userIcon;

    private static Font fontOpenSansLight;
    private static Font fontOpenSansRegular;
    private static Font fontOpenSansSemiBold;

    static {
        try {
            background = readImage("/img/background.png");
        } catch (Exception e) {
            e.printStackTrace();
            background = getDefaultImage();
            background.setRGB(0, 0, Color.GRAY.getRGB());
        }

        try {
            closeIcon = readImage("/img/icon-close.png");
        } catch (Exception e) {
            e.printStackTrace();
            closeIcon = getDefaultImage();
        }

        try {
            hideIcon = readImage("/img/icon-hide.png");
        } catch (Exception e) {
            e.printStackTrace();
            hideIcon = getDefaultImage();
        }

        try {
            logo = readImage("/img/logo.png");
        } catch (Exception e) {
            e.printStackTrace();
            logo = getDefaultImage();
        }

        try {
            logoMini = readImage("/img/logo-mini.png");
        } catch (Exception e) {
            e.printStackTrace();
            logoMini = getDefaultImage();
        }

        try {
            logoMicro = readImage("/img/logo-micro.png");
        } catch (Exception e) {
            e.printStackTrace();
            logoMicro = getDefaultImage();
        }

        try {
            phoneIcon = readImage("/img/icon-phone.png");
        } catch (Exception e) {
            e.printStackTrace();
            phoneIcon = getDefaultImage();
        }

        try {
            lockIcon = readImage("/img/icon-lock.png");
        } catch (Exception e) {
            e.printStackTrace();
            lockIcon = getDefaultImage();
        }

        try {
            settingsIcon = readImage("/img/icon-settings.png");
        } catch (Exception e) {
            e.printStackTrace();
            settingsIcon = getDefaultImage();
        }

        try {
            searchIcon = readImage("/img/icon-search.png");
        } catch (Exception e) {
            e.printStackTrace();
            searchIcon = getDefaultImage();
        }

        try {
            plusIcon = readImage("/img/icon-plus.png");
        } catch (Exception e) {
            e.printStackTrace();
            plusIcon = getDefaultImage();
        }

        try {
            sendButton = readImage("/img/button-send.png");
        } catch (Exception e) {
            e.printStackTrace();
            sendButton = getDefaultImage();
        }

        try {
            editIcon = readImage("/img/icon-edit.png");
        } catch (Exception e) {
            e.printStackTrace();
            editIcon = getDefaultImage();
        }

        try {
            backIcon = readImage("/img/icon-back.png");
        } catch (Exception e) {
            e.printStackTrace();
            backIcon = getDefaultImage();
        }

        try {
            trashIcon = readImage("/img/icon-trash.png");
        } catch (Exception e) {
            e.printStackTrace();
            trashIcon = getDefaultImage();
        }

        try {
            userIcon = readImage("/img/user-icon.png");
        } catch (Exception e) {
            e.printStackTrace();
            userIcon = getDefaultImage();
        }

        try {
            fontOpenSansLight = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream("/font/OpenSansLight.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            fontOpenSansLight = new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 18);
        }

        try {
            fontOpenSansRegular = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream("/font/OpenSansRegular.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            fontOpenSansRegular = new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 18);
        }

        try {
            fontOpenSansSemiBold = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream("/font/OpenSansSemiBold.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
            fontOpenSansSemiBold = new Font(Font.SANS_SERIF, Font.BOLD, 18);
        }
    }

    private Resources() {
    }

    public static BufferedImage getBackground() {
        return background;
    }

    public static BufferedImage getCloseIcon() {
        return closeIcon;
    }

    public static BufferedImage getHideIcon() {
        return hideIcon;
    }

    public static BufferedImage getLogo() {
        return logo;
    }

    public static BufferedImage getLogoMini() {
        return logoMini;
    }

    public static BufferedImage getLogoMicro() {
        return logoMicro;
    }

    public static BufferedImage getPhoneIcon() {
        return phoneIcon;
    }

    public static BufferedImage getLockIcon() {
        return lockIcon;
    }

    public static BufferedImage getSettingsIcon() {
        return settingsIcon;
    }

    public static BufferedImage getSearchIcon() {
        return searchIcon;
    }

    public static BufferedImage getPlusIcon() {
        return plusIcon;
    }

    public static BufferedImage getSendButton() {
        return sendButton;
    }

    public static Font getFontOpenSansLight() {
        return fontOpenSansLight;
    }

    public static BufferedImage getEditIcon() {
        return editIcon;
    }

    public static BufferedImage getBackIcon() {
        return backIcon;
    }

    public static BufferedImage getTrashIcon() {
        return trashIcon;
    }

    public static BufferedImage getUserIcon() {
        return userIcon;
    }

    public static Font getFontOpenSansLight(float size) {
        fontOpenSansLight = fontOpenSansLight.deriveFont(size);
        return fontOpenSansLight;
    }

    public static Font getFontOpenSansRegular() {
        return fontOpenSansRegular;
    }

    public static Font getFontOpenSansRegular(float size) {
        fontOpenSansRegular = fontOpenSansRegular.deriveFont(size);
        return fontOpenSansRegular;
    }

    public static Font getFontOpenSansSemiBold() {
        return fontOpenSansSemiBold;
    }

    public static Font getFontOpenSansSemiBold(float size) {
        fontOpenSansSemiBold = fontOpenSansSemiBold.deriveFont(size);
        return fontOpenSansSemiBold;
    }

    private static BufferedImage readImage(String resRootPath) throws IOException {
        return ImageIO.read(Resources.class.getResource(resRootPath));
    }

    private static BufferedImage getDefaultImage() {
        return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    }
}
