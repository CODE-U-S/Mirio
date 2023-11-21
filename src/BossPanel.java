import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BossPanel extends JPanel {

	private Image backgroundImage; // 배경 이미지를 저장할 Image 변수
    private CardLayout cardLayout; // 화면 전환
    private JPanel cardPanel; // 화면 전환

    private Image player; // 캐릭터 이미지를 저장할 Image 변수
    private String playerU; // 이미지 경로 저장할 변수
    private String playerD; // 이미지 경로 저장할 변수
    private int playerX = 100; // 캐릭터의 x좌표
    private int playerY = 100; // 캐릭터의 y좌표
    
    private boolean isRight; // 오른쪽 방향 키가 눌렸는지 확인
    private boolean isLeft; // 왼쪽 방향 키가 눌렸는지 확인
    private boolean isJump; // 점프 중인지 확인
    private boolean isDown; // 점프 중인지 확인
    private static final int SPEED = 5; // 이동 속도
    
    private int x = 200; // 플레이어의 x 좌표
    private int y = 350; // 플레이어의 y 좌표
    
    private Timer movementTimer; 

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
        	backgroundImage = ImageIO.read(new File("images/boss/test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        movementTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRight && x < getWidth() - player.getWidth(null)) {
                    x += SPEED;
                }
                if (isLeft && x > 0) {
                    x -= SPEED;
                }
                if (isJump && y > 0) {
                    y -= SPEED;
                }
                if (isDown && y < getHeight() - player.getHeight(null)) {
                    y += SPEED;
                }

                setPlayerLocation();
            }
        });
        movementTimer.start();
        
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        // 오른쪽 키
        KeyStroke rightKeyReleased = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false);
        inputMap.put(rightKeyReleased, "RightReleased");
        actionMap.put("RightReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                isRight = true;
                player = new ImageIcon(playerU).getImage();
          
            }
        });

        KeyStroke rightKeyPressed = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true);
        inputMap.put(rightKeyPressed, "RightPressed");
        actionMap.put("RightPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
            	player = new ImageIcon(playerD).getImage();
                isRight = false;
            }
        });

        // 왼쪽 키
        KeyStroke leftKeyReleased = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false);
        inputMap.put(leftKeyReleased, "LeftReleased");
        actionMap.put("LeftReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
            	player = new ImageIcon(playerU).getImage();
                isLeft = true;
           
            }
        });

        KeyStroke leftKeyPressed = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true);
        inputMap.put(leftKeyPressed, "LeftPressed");
        actionMap.put("LeftPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
            	player = new ImageIcon(playerD).getImage();
                isLeft = false;
            }
        });

        // 위쪽 키
        KeyStroke upKeyReleased = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false);
        inputMap.put(upKeyReleased, "UpReleased");
        actionMap.put("UpReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player = new ImageIcon(playerU).getImage();
                isJump = true;
        
            }
        });

        KeyStroke upKeyPressed = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true);
        inputMap.put(upKeyPressed, "UpPressed");
        actionMap.put("UpPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player = new ImageIcon(playerD).getImage();
                isJump = false;
            }
        });

        // 아래쪽 키
        KeyStroke downKeyReleased = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false);
        inputMap.put(downKeyReleased, "DownReleased");
        actionMap.put("DownReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player = new ImageIcon(playerU).getImage();
                isDown = true;
               
            }
        });

        KeyStroke downKeyPressed = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true);
        inputMap.put(downKeyPressed, "DownPressed");
        actionMap.put("DownPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player = new ImageIcon(playerD).getImage();
                isDown = false;
            }
        });

        
    }
    


    private void setPlayerLocation() {
        SwingUtilities.invokeLater(() -> {
            repaint();
        });
    }
   
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
        g.drawImage(player, x, y, this);
        
    }
}
