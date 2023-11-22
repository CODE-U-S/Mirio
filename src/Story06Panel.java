import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Story06Panel extends JPanel {
    private Image image; // 캐릭터 이미지를 저장할 Image 변수
    private String playerD;

    private CardLayout cardLayout; // 화면 전환
    private JPanel cardPanel; // 화면 전환

    public void setCharacterImage(String characterSelection) {
        this.playerD = characterSelection + "01.png";
        try {
            image = ImageIO.read(new File(playerD));
            //image = ImageIO.read(new File("images/story/sunhee01.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Story06Panel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        // MouseListener로 화면 전환
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 마우스 클릭 시 다음 화면으로 전환
                cardLayout.show(cardPanel, "RunPanel");
            }
        });

        setFocusable(true); // 패널이 키 이벤트를 받을 수 있도록 설정
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // 현재 이미지를 패널에 그립니다.
            g.drawImage(image, 0, 0, 1200, 700, null);
        }
    }
}
