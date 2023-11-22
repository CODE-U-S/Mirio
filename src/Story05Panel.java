import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Story05Panel extends JPanel {
    private BufferedImage[] images; // 이미지 배열
    private int currentImageIndex = 0; // 현재 이미지 인덱스
    private CardLayout cardLayout; // 화면 전환
    private JPanel cardPanel; // 화면 전환

    private Image image; // 캐릭터 이미지를 저장할 Image 변수
    private String playerD;

    public Story05Panel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        // MouseListener로 화면 전환
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 마우스 클릭 시 다음 이미지로 전환
                if (currentImageIndex < images.length - 1) {
                    currentImageIndex++;
                } else {
                    // 이미지가 마지막이면 다음 화면으로 전환
                    cardLayout.show(cardPanel, "StartPanel");
                }
                repaint(); // 이미지 변경 후 패널을 다시 그리도록 요청
            }
        });

        setFocusable(true); // 패널이 키 이벤트를 받을 수 있도록 설정

        // Load images based on the count value
        int count = Heart.getCount();
        if (count >= 1) {
            images = new BufferedImage[1];
            images[0] = loadImage("images/story/015.png");
        } else {
            images = new BufferedImage[2];
            images[0] = loadImage("images/story/013.png");
            images[1] = loadImage("imgaes/story/014.png");
        }
    }

    private BufferedImage loadImage(String fileName) {
        try {
            return ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentImageIndex < images.length && images[currentImageIndex] != null) {
            // 현재 이미지를 패널에 그립니다.
            g.drawImage(images[currentImageIndex], 0, 0, 1200, 700, null);
        }
    }
}