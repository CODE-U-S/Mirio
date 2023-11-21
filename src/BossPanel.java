import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BossPanel extends JPanel {
    private BufferedImage image; // 이미지를 저장하기 위한 변수
    private CardLayout cardLayout; // 화면 전환
    private JPanel cardPanel; // 화면 전환

    public BossPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        try {
            // 이미지 파일을 불러옵니다. 이미지 파일은 images 폴더에 있어야 합니다.
            image = ImageIO.read(new File("images/explanation.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // MouseListener로 화면 전환
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 마우스 왼쪽 버튼을 클릭했을 때 화면 전환: ExplanationPanel에서 RunPanel로 전환
                cardLayout.show(cardPanel, "RunPanel");
            }
        });

        setFocusable(true); // 패널이 키 이벤트를 받을 수 있도록 설정
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
