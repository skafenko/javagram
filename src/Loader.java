

import org.javagram.dao.ApiBridgeTelegramDAO;
import org.javagram.dao.TelegramDAO;
import resources.Config;

import javax.swing.*;
import java.io.IOException;


public class Loader {
    public static void main(String[] args) throws IOException {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                        if(info.getName().equals("Nimbus")) {
                            UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
//                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    TelegramDAO telegramDAO = new ApiBridgeTelegramDAO(Config.SERVER, Config.APP_ID, Config.APP_HASH);
                    Window window = new Window(telegramDAO);
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }
}
