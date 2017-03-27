package overlays;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mykhailo on 27.07.2016.
 */
public class MyLayeredPane extends JLayeredPane {
    @Override
    public void doLayout() {
        super.doLayout();
        for (Component component : getComponents()) {
            component.setBounds(0, 0, this.getWidth(), this.getHeight());
        }
    }
}
