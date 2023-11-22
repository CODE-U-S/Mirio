import java.awt.CardLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main implements Runnable {

    public void run() {
        final JFrame frame = new JFrame("Mirio");
        frame.setSize(1200, 730); // 프레임 크기를 1200x700으로 설정

        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/jinseon.png"));
        
       
        // CardLayout과 cardPanel을 생성
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        // 각 패널을 생성
        Story01Panel story01Panel = new Story01Panel(cardLayout, cardPanel);
        Story02Panel story02Panel = new Story02Panel(cardLayout, cardPanel);
        Story03Panel story03Panel = new Story03Panel(cardLayout, cardPanel);
        Story04Panel story04Panel = new Story04Panel(cardLayout, cardPanel);
        Story05Panel story05Panel = new Story05Panel(cardLayout, cardPanel);
        Story06Panel story06Panel = new Story06Panel(cardLayout, cardPanel);
        
        RunPanel runPanel = new RunPanel(cardLayout, cardPanel); // 이 부분은 수정하지 않음
        BossPanel bossPanel = new BossPanel(cardLayout, cardPanel);
        StartPanel startPanel = new StartPanel(cardLayout, cardPanel);
        StudyPanel studypanel = new StudyPanel(cardLayout, cardPanel);
        CharacterPanel characterPanel = new CharacterPanel(cardLayout, cardPanel, runPanel, bossPanel, studypanel,story02Panel,story03Panel, story04Panel,story06Panel);
        ExplanationPanel explanationPanel = new ExplanationPanel(cardLayout, cardPanel);
        TestPanel testPanel = new TestPanel(cardLayout, cardPanel);
        
        
        // 패널을 cardPanel에 추가
        cardPanel.add(startPanel, "StartPanel");
        cardPanel.add(characterPanel, "CharacterPanel");
        cardPanel.add(explanationPanel, "ExplanationPanel");
        cardPanel.add(runPanel, "RunPanel");
        cardPanel.add(studypanel, "StudyPanel");
        cardPanel.add(testPanel, "TestPanel");
        cardPanel.add(bossPanel, "BossPanel");
        
        cardPanel.add(story01Panel, "Story01Panel");
        cardPanel.add(story02Panel, "Story02Panel");
        cardPanel.add(story03Panel, "Story03Panel");
        cardPanel.add(story04Panel, "Story04Panel");
        cardPanel.add(story05Panel, "Story05Panel");
        cardPanel.add(story06Panel, "Story06Panel");
        
        cardPanel.setFocusable(true);
        cardPanel.requestFocus();
        cardPanel.addKeyListener(studypanel);
        
        // 프레임에 cardPanel 추가
        frame.add(cardPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // 화면 중앙에 위치
        frame.setLocationRelativeTo(null);


    }
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }

}
