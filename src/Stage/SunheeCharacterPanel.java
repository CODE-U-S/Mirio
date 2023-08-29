package Stage;

import javax.swing.*;
import java.awt.*;

public class SunheeCharacterPanel extends JPanel {
    public SunheeCharacterPanel() {
        setLayout(new BorderLayout());

        ImageIcon backgroundImage = new ImageIcon("images/dd.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        add(backgroundLabel);
    }
}