import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TestPanel extends JPanel {
    private BufferedImage image; // 이미지를 저장하기 위한 변수
    private CardLayout cardLayout; // 화면 전환
    private JPanel cardPanel; // 화면 전환

    public TestPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        try {
            // 이미지 파일을 불러옵니다. 이미지 파일은 images 폴더에 있어야 합니다.
            image = ImageIO.read(new File("images/explanation.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

       
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
