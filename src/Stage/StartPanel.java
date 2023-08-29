package Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class StartPanel extends JPanel {
    public StartPanel(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Hello, Java GUI!");
        add(label, BorderLayout.CENTER);

        JButton startButton = new JButton();
        try {
            Image img = ImageIO.read(new File("images/dd.jpg")); // 이미지 파일 로드
            Image resizedImg = img.getScaledInstance(200, 100, Image.SCALE_SMOOTH); // 이미지 크기 조정
            startButton.setIcon(new ImageIcon(resizedImg)); // 아이콘으로 이미지 설정
        } catch (IOException e) {
            e.printStackTrace();
        }

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "goToSchool");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
