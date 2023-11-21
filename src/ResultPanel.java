import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ResultPanel extends JPanel {
    private CardLayout cardLayout; // 화면 전환
    private JPanel cardPanel; // 화면 전환
    private BufferedImage resultImage;
    private int randomNumber; // 난수를 저장할 변수
    
    

    public ResultPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        setFocusable(true); // 패널이 포커스를 받을 수 있도록 함

        // 초기화 시에 난수 생성
        randomNumber = new Random().nextInt(101);

        try {
            // Load the result image with correct image type
            resultImage = ImageIO.read(new File("images/results.png"));
            resultImage = toCompatibleImage(resultImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton runbtn = new JButton();
        runbtn.setContentAreaFilled(false); // 버튼의 내용 영역을 투명하게 만듭니다
        runbtn.setOpaque(false); // 버튼을 투명하게 만듭니다.
        runbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 화면 전환: StartPanel에서 CharacterPanel로 전환
                cardLayout.show(cardPanel, "BossPanel");
            }
        });

        // 마우스 클릭 리스너 추가하여 난수 변경 방지
        runbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                repaint(); // 다시 그리기를 통해 화면 갱신
            }
        });

        setLayout(null); // 레이아웃 관리자를 사용하지 않고 직접 위치 설정
        runbtn.setBounds(110, 330, 300, 60); // 버튼의 위치와 크기를 설정
        add(runbtn); // 패널에 버튼을 추가
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (resultImage != null) {
            // 500x500 크기로 결과 이미지 그리기
            int x = (getWidth() - 500) / 2;
            int y = (getHeight() - 500) / 2;
            g.drawImage(resultImage, x, y, 500, 500, null);
        }

        // (200, 250) 위치에 저장된 난수 그리기
        String numberText = String.valueOf(randomNumber);
        Font font = new Font("Arial", Font.BOLD, 50);

        g.setFont(font);
        g.setColor(Color.BLACK); // 원하는 색상으로 변경 가능
        g.drawString(numberText + " points", 150, 250);
        
        // 만약 numberText가 30을 넘지 못하면 Heart의 count를 감소
        if (Integer.parseInt(numberText) <= 10) {
            Heart.setCount();
        }
    }

    /// 이미지를 알파 채널이 있는 호환 가능한 이미지 유형으로 변환
    private BufferedImage toCompatibleImage(BufferedImage image) {
        BufferedImage compatibleImage = new BufferedImage(
                image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = compatibleImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return compatibleImage;
    }
}