import javax.swing.*;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CharacterPanel extends JPanel {
    private BufferedImage image; // 이미지를 저장할 변수
    private CardLayout cardLayout; //화면 전환
    private JPanel cardPanel; // 화면 전환
    private RunPanel runPanel;
    private BossPanel bossPanel;
    
    
    private ImageIcon jinseon;
    private ImageIcon jinseonC;
    private ImageIcon sunhee;
    private ImageIcon sunheeC;
    
    MusicPlayer musicPlayer = new MusicPlayer(); //효과음 음악플레이어

    
    public CharacterPanel(CardLayout cardLayout, JPanel cardPanel, RunPanel runPanel, BossPanel bossPanel, StudyPanel studyPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.runPanel = runPanel; // RunPanel 객체를 저장
        this.bossPanel = bossPanel; // BossPanel 객체를 저장
        try {
            // 이미지 파일을 로드합니다. 이미지 파일은 images 폴더에 있어야 합니다.
            image = ImageIO.read(new File("images/character_main.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // jinseon 버튼
        jinseon = new ImageIcon("images/start_jinseon.png");
        jinseonC = new ImageIcon("images/start_jinseonC.png");

        JButton character01 = new JButton(jinseon);
        character01.setBorderPainted(false);
        character01.setFocusPainted(false);
        character01.setContentAreaFilled(false);

        character01.setBounds(85, 100, jinseon.getIconWidth(), jinseon.getIconHeight());
        add(character01);

        character01.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                character01.setIcon(jinseonC);
                                musicPlayer.playMusic("audio/choice.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                character01.setIcon(jinseon);
            }
        });

        character01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String characterselect = "images/jinseon";
                runPanel.setCharacterImage(characterselect);
                bossPanel.setCharacterImage(characterselect);
                studyPanel.setCharacterImage(characterselect);
                cardLayout.show(cardPanel, "ExplanationPanel");
            }
        });
        
        // sunhee 버튼
        sunhee = new ImageIcon("images/start_sunhee.png");
        sunheeC = new ImageIcon("images/start_sunheeC.png");
        
        // 투명한 버튼 생성
        JButton character02 = new JButton(sunhee);
        character02.setBorderPainted(false);
        character02.setFocusPainted(false);
        character02.setContentAreaFilled(false);
        
        // 패널에 버튼을 추가
        character02.setBounds(700, 100, sunhee.getIconWidth(), sunhee.getIconHeight());
        add(character02);
        
        character02.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                character02.setIcon(sunheeC);
                                musicPlayer.playMusic("audio/choice.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                character02.setIcon(sunhee);
            }
        });

        character02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String characterselect = "images/sunhee";
                runPanel.setCharacterImage(characterselect);
                bossPanel.setCharacterImage(characterselect);
                studyPanel.setCharacterImage(characterselect);
                cardLayout.show(cardPanel, "ExplanationPanel");
            }
        });

       
        
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
