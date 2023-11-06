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
        RunPanel runPanel = new RunPanel(cardLayout, cardPanel); // 이 부분은 수정하지 않음
        StartPanel startPanel = new StartPanel(cardLayout, cardPanel);
        CharacterPanel characterPanel = new CharacterPanel(cardLayout, cardPanel, runPanel);
        ExplanationPanel explanationPanel = new ExplanationPanel(cardLayout, cardPanel);
        
        StudyPanel studypanel = new StudyPanel();

        // 패널을 cardPanel에 추가
        cardPanel.add(startPanel, "StartPanel");
        cardPanel.add(characterPanel, "CharacterPanel");
        cardPanel.add(explanationPanel, "ExplanationPanel");
        cardPanel.add(runPanel, "RunPanel");
        cardPanel.add(studypanel, "StudyPanel");

        cardPanel.setFocusable(true);
        cardPanel.requestFocus();
        cardPanel.addKeyListener(studypanel);
        
        // 프레임에 cardPanel 추가
        frame.add(cardPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // 화면 중앙에 위치
        frame.setLocationRelativeTo(null);
        
        // 음악 재생
        MusicPlayer musicPlayer = new MusicPlayer();
        //musicPlayer.playMusic("audio/music01.wav");

    }
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }

}
