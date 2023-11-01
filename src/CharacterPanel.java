import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CharacterPanel extends JPanel {
    private BufferedImage image; // 이미지를 저장할 변수
    private CardLayout cardLayout; //화면 전환
    private JPanel cardPanel; // 화면 전환
    private RunPanel runPanel;
    
    public CharacterPanel(CardLayout cardLayout, JPanel cardPanel, RunPanel runPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.runPanel = runPanel; // RunPanel 객체를 저장
        try {
            // 이미지 파일을 로드합니다. 이미지 파일은 images 폴더에 있어야 합니다.
            image = ImageIO.read(new File("images/character_main.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 투명한 버튼 생성
        JButton character01 = new JButton();
        character01.setContentAreaFilled(false); // 버튼의 내용 영역을 투명하게 만듭니다
        character01.setOpaque(false); // 버튼을 투명하게 만듭니다.
        character01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String characterselect = "images/jinseon.png";
                try {
                    runPanel.setCharacterImage(characterselect);
                } catch (IOException ex) {
                    ex.printStackTrace(); // 또는 다른 예외 처리 로직 구현
                }
                cardLayout.show(cardPanel, "ExplanationPanel");
            }
        });

        // 패널에 버튼을 추가
        setLayout(null); // 레이아웃 관리자를 사용하지 않고 직접 위치 설정
        character01.setBounds(85, 100, 425, 425); // 버튼의 위치와 크기를 설정
        add(character01);
        
        
        // 투명한 버튼 생성
        JButton character02 = new JButton();
        character02.setContentAreaFilled(false); // 버튼의 내용 영역을 투명하게 만듭니다
        character02.setOpaque(false); // 버튼을 투명하게 만듭니다.
        character02.addActionListener(new ActionListener() {
        	  @Override
              public void actionPerformed(ActionEvent e) {
                  String characterselect = "images/sunhee.png";
                  try {
                      runPanel.setCharacterImage(characterselect);
                  } catch (IOException ex) {
                      ex.printStackTrace(); // 또는 다른 예외 처리 로직 구현
                  }
                  cardLayout.show(cardPanel, "ExplanationPanel");
              }
        });

        // 패널에 버튼을 추가
        setLayout(null); // 레이아웃 관리자를 사용하지 않고 직접 위치 설정
        character02.setBounds(700, 100, 425, 425); // 버튼의 위치와 크기를 설정
        add(character02);
        
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
