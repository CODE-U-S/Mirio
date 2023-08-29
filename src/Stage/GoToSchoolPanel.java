package Stage;

import javax.swing.*;
import java.awt.*;

public class GoToSchoolPanel extends JPanel {
    public GoToSchoolPanel() {
        setLayout(new BorderLayout());

        ImageIcon backgroundImage = new ImageIcon("images/dd.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        add(backgroundLabel);
    }
}