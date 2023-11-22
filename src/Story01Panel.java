import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Story01Panel extends JPanel {
	private BufferedImage[] images; // 이미지 배열
	private int currentImageIndex = 0; // 현재 이미지 인덱스
    private CardLayout cardLayout; // 화면 전환
    private JPanel cardPanel; // 화면 전환

    public Story01Panel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        // 이미지 배열을 초기화합니다.
        images = new BufferedImage[4];
        try {
            // 이미지 파일을 불러와 배열에 저장합니다.
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(new File("images/story/00" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // MouseListener로 화면 전환
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 마우스 클릭 시 다음 이미지로 전환
                if (currentImageIndex < images.length - 1) {
                    currentImageIndex++;
                } else {
                    // 이미지가 마지막이면 다음 화면으로 전환
                    cardLayout.show(cardPanel, "CharacterPanel");
                }
                repaint(); // 이미지 변경 후 패널을 다시 그리도록 요청
            }
        });

        setFocusable(true); // 패널이 키 이벤트를 받을 수 있도록 설정
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
