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
    
    private Image javaImage; // 추가 이미지를 저장할 Image 변수
    private int javaImageX; // 추가 이미지의 x 좌표
    private int javaImageY; // 추가 이미지의 y 좌표
    private static final int JAVA_IMAGE_SIZE = 80;
    private static final int JAVA_IMAGE_SPEED = 10; // 이동 속도

  
    private boolean isRight; // 오른쪽 방향 키가 눌렸는지 확인
    private boolean isLeft; // 왼쪽 방향 키가 눌렸는지 확인
    private boolean isJump; // 점프 중인지 확인
    private boolean isDown; // 점프 중인지 확인
    private boolean isSpacePressed; // 스페이스바가 눌렸는지 확인
    private boolean isJavaMoving; // Java 이미지가 이동 중인지 확인
    private static final int SPEED = 5; // 이동 속도
    
    private int x = 200; // 플레이어의 x 좌표
    private int y = 350; // 플레이어의 y 좌표
    
    private Timer movementTimer; 
    private Timer javaImageTimer;

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
        	javaImage = ImageIO.read(new File("images/boss/java.png"));
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
                
                if (isSpacePressed && !isJavaMoving) {
                    // If space is pressed and Java image is not moving, start moving Java image
                    isJavaMoving = true;
                    javaImageX = x; // Set initial position to player's x position
                    javaImageY = y; // Set initial position to player's y position
                    javaImageTimer.start();
                    isSpacePressed = false;
                }

                setPlayerLocation();
            }
        });
        movementTimer.start();
        
        javaImageTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isJavaMoving) {
                    // If Java image is moving, update its position
                    javaImageX += JAVA_IMAGE_SPEED;

                    // Check if Java image is out of bounds, stop the timer and reset the position
                    if (javaImageX > getWidth()) {
                        isJavaMoving = false;
                        javaImageTimer.stop();
                        javaImageX = x;
                        javaImageY = y;
                    }
                }
                setPlayerLocation();
            }
        });
        
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
        
        // 스페이스바 키
        KeyStroke spaceKeyReleased = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false);
        inputMap.put(spaceKeyReleased, "SpaceReleased");
        actionMap.put("SpaceReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                isSpacePressed = true;
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

        // Draw Java image only when it is moving (space bar is pressed)
        if (isJavaMoving) {
            g.drawImage(javaImage, javaImageX, javaImageY, JAVA_IMAGE_SIZE, JAVA_IMAGE_SIZE, this);
        }
    }
}
