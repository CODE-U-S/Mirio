import javax.swing.*;

import Stage.GoToSchoolPanel;
import Stage.StartPanel;
import Stage.SunheeCharacterPanel;

import java.awt.*;

public class Main {
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("GUI Example");
        frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel startPanel = new StartPanel(cardLayout, cardPanel);

        GoToSchoolPanel goToSchoolPanel = new GoToSchoolPanel();
        SunheeCharacterPanel sunheeCharacterPanel = new SunheeCharacterPanel();
        
        // GoToSchoolPanel 위에 SunheeCharacterPanel 추가
        goToSchoolPanel.setLayout(new BorderLayout());
        goToSchoolPanel.add(sunheeCharacterPanel, BorderLayout.SOUTH);

        cardPanel.add(startPanel, "start");
        cardPanel.add(goToSchoolPanel, "goToSchool");

        frame.add(cardPanel);
        frame.setVisible(true);
    }
}
