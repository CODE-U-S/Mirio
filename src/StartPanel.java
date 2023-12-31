import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class StartPanel extends JPanel{
    private BufferedImage image; // 이미지를 저장할 변수
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private boolean cnt = true;
    // 이미지 파일 로드
    public StartPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
    	// 화면 전환
    	setLayout(new BorderLayout());
    	if(cnt) {
    		Sound();
    	}
        try {
            // 이미지 파일을 로드합니다. 이미지 파일은 images 폴더에 있어야 합니다.
            image = ImageIO.read(new File("images/start_background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 투명한 버튼 생성
        JButton startButton = new JButton();
        startButton.setContentAreaFilled(false); // 버튼의 내용 영역을 투명하게 만듭니다
        startButton.setOpaque(false); // 버튼을 투명하게 만듭니다.
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 화면 전환: StartPanel에서 CharacterPanel로 전환
            	SoundStop();
                cardLayout.show(cardPanel, "Story01Panel");
            }
        });

        // 패널에 버튼을 추가
        setLayout(null); // 레이아웃 관리자를 사용하지 않고 직접 위치 설정
        startButton.setBounds(445, 420, 313, 88); // 버튼의 위치와 크기를 설정
        add(startButton);
        
        
        
    }
    MusicPlayer musicPlayer = new MusicPlayer();
    public void Sound() {
    	// 음악 재생
    	musicPlayer.playMusic("audio/music01.wav");
    }
    public void SoundStop() {
    	musicPlayer.stopMusic();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // 이미지를 패널에 그립니다.
            g.drawImage(image, 0, 0, 1200, 700, null);
        }
    }
}