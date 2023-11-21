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

    private Image player; // 캐릭터 이미지를 저장할 Image 변수
    private String playerU; // 이미지 경로 저장할 변수
    private String playerD; // 이미지 경로 저장할 변수
    private int playerX = 100; // 캐릭터의 x좌표
    private int playerY = 100; // 캐릭터의 y좌표

    public void setCharacterImage(String characterSelection) {
        this.playerD = characterSelection + ".png";
        playerU = characterSelection + "_up.png";
        player = new ImageIcon(playerD).getImage();
    }

    public BossPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        try {
            // 이미지 파일을 불러옵니다. 이미지 파일은 images 폴더에 있어야 합니다.
            image = ImageIO.read(new File("images/boss/test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setCharacterImage("yourDefaultCharacter"); // 초기 캐릭터 설정

        // 패널이 키 이벤트를 받을 수 있도록 설정
        setFocusable(true);

        // InputMap을 이용한 KeyBindings 설정
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "pressedUp");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "releasedUp");
        actionMap.put("pressedUp", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleKeyPress(KeyEvent.VK_UP);
            }
        });
        actionMap.put("releasedUp", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleKeyRelease(KeyEvent.VK_UP);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "pressedDown");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "releasedDown");
        actionMap.put("pressedDown", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleKeyPress(KeyEvent.VK_DOWN);
            }
        });
        actionMap.put("releasedDown", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleKeyRelease(KeyEvent.VK_DOWN);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "pressedLeft");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "releasedLeft");
        actionMap.put("pressedLeft", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleKeyPress(KeyEvent.VK_LEFT);
            }
        });
        actionMap.put("releasedLeft", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleKeyRelease(KeyEvent.VK_LEFT);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "pressedRight");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "releasedRight");
        actionMap.put("pressedRight", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleKeyPress(KeyEvent.VK_RIGHT);
            }
        });
        actionMap.put("releasedRight", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleKeyRelease(KeyEvent.VK_RIGHT);
            }
        });

        // 타이머를 이용하여 일정 주기로 패널을 다시 그리기
        Timer timer = new Timer(16, new ActionListener() { // 16ms 간격으로 타이머 실행 (약 60프레임/초)
            @Override
            public void actionPerformed(ActionEvent e) {
                // 패널을 다시 그리기
                repaint();
            }
        });
        timer.start();
    }

    // 상하좌우 키 이벤트 처리
    private void handleKeyPress(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                player = new ImageIcon(playerU).getImage();
                playerY -= 5;
                break;
            case KeyEvent.VK_DOWN:
                player = new ImageIcon(playerD).getImage();
                playerY += 5;
                break;
            case KeyEvent.VK_LEFT:
                player = new ImageIcon(playerD).getImage();
                playerX -= 5;
                break;
            case KeyEvent.VK_RIGHT:
                player = new ImageIcon(playerU).getImage();
                playerX += 5;
                break;
        }
    }

    // 키 릴리스 이벤트 처리 (필요에 따라 추가)
    private void handleKeyRelease(int keyCode) {
        // 키 릴리스에 대한 처리를 추가할 수 있습니다.
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null && player != null) {
            // 이미지를 패널에 그립니다.
            g.drawImage(image, 0, 0, 1200, 700, null);
            g.drawImage(player, playerX, playerY, null);
        }
    }
}
