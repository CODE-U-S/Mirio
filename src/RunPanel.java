import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionListener;


public class RunPanel extends JPanel {
    private BufferedImage backgroundImage;
    private BufferedImage characterImage;
    private int characterX, characterY;
    private CardLayout cardLayout; //화면 전환
    private JPanel cardPanel; // 화면 전환

    public RunPanel(CardLayout cardLayout, JPanel cardPanel) {
    	this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
    	try {
            backgroundImage = ImageIO.read(new File("images/run.png"));
            characterImage = ImageIO.read(new File("images/jinseon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        characterX = 100;
        characterY = 100;

        setKeyBindings();

        Timer timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

    private void setKeyBindings() {
        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        KeyStroke leftKey = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
        inputMap.put(leftKey, "moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characterX -= 10;
                repaint();
            }
        });

        KeyStroke rightKey = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
        inputMap.put(rightKey, "moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characterX += 10;
                repaint();
            }
        });

        KeyStroke upKey = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
        inputMap.put(upKey, "moveUp");
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characterY -= 10;
                repaint();
            }
        });

        KeyStroke downKey = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
        inputMap.put(downKey, "moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characterY += 10;
                repaint();
            }
        });
        
        // 투명한 버튼 생성
        JButton runbtn = new JButton();
        runbtn.setContentAreaFilled(false); // 버튼의 내용 영역을 투명하게 만듭니다
        runbtn.setOpaque(false); // 버튼을 투명하게 만듭니다.
        runbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// 화면 전환: StartPanel에서 CharacterPanel로 전환
            	cardLayout.show(cardPanel, "StudyPanel");
            }
        });

        // 패널에 버튼을 추가
        setLayout(null); // 레이아웃 관리자를 사용하지 않고 직접 위치 설정
        runbtn.setBounds(700, 100, 425, 425); // 버튼의 위치와 크기를 설정
        add(runbtn);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this);
        }

        if (characterImage != null) {
            g.drawImage(characterImage, characterX, characterY, this);
        }
    }
}

