
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
    private static final int JAVA_IMAGE_SPEED = 15; // 이동 속도
    
    private ArrayList<JavaThread> javaThreads; // Java 스레드를 저장할 리스트
    
    private Timer arrowTimer;
    private ArrayList<ArrowThread> arrowThreads;

  
    private boolean isRight; // 오른쪽 방향 키가 눌렸는지 확인
    private boolean isLeft; // 왼쪽 방향 키가 눌렸는지 확인
    private boolean isJump; // 점프 중인지 확인
    private boolean isDown; // 점프 중인지 확인
    private boolean isSpacePressed; // 스페이스바가 눌렸는지 확인
    private boolean isJavaMoving; // Java 이미지가 이동 중인지 확인
    private static final int SPEED = 10; // 이동 속도
    
    private int x = 200; // 플레이어의 x 좌표
    private int y = 350; // 플레이어의 y 좌표
    
    private Timer movementTimer; 
    private Timer javaImageTimer;
    
    private Image boss; // 보스
    private int bossHealth; // 보스의 체력
    
    private Heart heartAt10x10; //하트를 관리할 객체
    private boolean soundCnt = true;
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
        	boss = ImageIO.read(new File("images/boss/Interviewer01.png"));
        	
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        javaThreads = new ArrayList<>();
        bossHealth = 100; // 초기 체력 설정
        heartAt10x10 = new Heart(10, 10);
        
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
        
        arrowThreads = new ArrayList<>();
        arrowTimer = new Timer(1500, new ActionListener() { // Timer set for every 6 seconds (6000 milliseconds)
            @Override
            public void actionPerformed(ActionEvent e) {
                shootArrows();
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
                createJavaThread();
                arrowTimer.start();
            }
        });

        
    }
    
    private void setPlayerLocation() {
        SwingUtilities.invokeLater(() -> {
            repaint();
            checkCollision();
            checkBossHealth(); // 각 프레임마다 보스 체력 확인
        });
    }
    
    private void createJavaThread() {
        JavaThread javaThread = new JavaThread(x, y);
        javaThreads.add(javaThread);
        javaThread.start();
    }
    
    private class JavaThread extends Thread {
        private int javaX;
        private int javaY;

        public JavaThread(int startX, int startY) {
            this.javaX = startX;
            this.javaY = startY;
        }

        @Override
        public void run() {
        	if(soundCnt) {
        		Sound();
        		soundCnt = false;
        	}
            while (javaX < getWidth()) {
                javaX += JAVA_IMAGE_SPEED;

                // Java 이미지가 화면을 벗어나면 스레드를 제거합니다.
                if (javaX > getWidth()) {
                    javaThreads.remove(this);
                    return;
                }

                setPlayerLocation();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public int getJavaX() {
            return javaX;
        }

        public int getJavaY() {
            return javaY;
        }
        
    }
    
    private void checkCollision() {
        // Check collision between player and arrows
        Rectangle playerBounds = new Rectangle(x, y, player.getWidth(null), player.getHeight(null));

        for (ArrowThread arrowThread : arrowThreads) {
            Rectangle arrowBounds = new Rectangle(arrowThread.getArrowX(), arrowThread.getArrowY(), 100, 30);

            if (playerBounds.intersects(arrowBounds)) {
                // Collision detected between player and arrow
                arrowThreads.remove(arrowThread);
                heartAt10x10.setCount(); // Decrease heart count
                repaint(); // Repaint the panel
                // Perform any additional actions, e.g., decrease player health or update game state
                // Add your code here
                return;
            }
        }

        // Check collision between Java threads and boss
        Rectangle bossBounds = new Rectangle(900, 30, 250, 600);

        for (JavaThread javaThread : javaThreads) {
            Rectangle javaBounds = new Rectangle(javaThread.getJavaX(), javaThread.getJavaY(), JAVA_IMAGE_SIZE, JAVA_IMAGE_SIZE);

            if (javaBounds.intersects(bossBounds)) {
                javaThreads.remove(javaThread);
                bossHealth -= 1;
                return;
            }
        }
    }

    private void checkBossHealth() {
        // 보스의 체력이 0 이하로 떨어지면 게임 오버 또는 다른 처리를 수행할 수 있습니다.
        if (bossHealth <= 0) {
            // 게임 오버 또는 다른 처리
        }
    }
    
    private void shootArrows() {
        Random random = new Random();
        int bossX = 900; // Boss x-coordinate
        int bossY = 30;  // Boss y-coordinate
        int arrowSize = 100;
        int arrowSpeed = 5;

        for (int i = 0; i < 10; i++) {
            int arrowX = bossX;
            int arrowY = random.nextInt(1200); // Randomize the vertical position within a range

            ArrowThread arrowThread = new ArrowThread(arrowX, arrowY, arrowSpeed);
            arrowThreads.add(arrowThread);
            arrowThread.start();
        }
    }
    
    private class ArrowThread extends Thread {
        private int arrowX;
        private int arrowY;
        private int arrowSpeed;

        public ArrowThread(int startX, int startY, int speed) {
            this.arrowX = startX;
            this.arrowY = startY;
            this.arrowSpeed = speed;
        }

        @Override
        public void run() {
            while (arrowX > -200) {
                arrowX -= arrowSpeed;

                // Arrow image is out of bounds, remove the thread
                if (arrowX < -200 || arrowX > getWidth()) {
                    arrowThreads.remove(this);
                    return;
                }

                setPlayerLocation();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public int getArrowX() {
            return arrowX;
        }

        public int getArrowY() {
            return arrowY;
        }
    }

    public void Sound() {
    	MusicPlayer musicPlayer = new MusicPlayer();
    	musicPlayer.playMusic("audio/boss.wav");
    }

   
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
        g.drawImage(player, x, y, this);
        g.drawImage(boss, 900, 30, 250, 600, this);
        
      
        // 히어로 텍스트 그리기
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Hero", 1000, 50);
        
        // 숫자로 보스의 체력 표시
        g.drawString(String.valueOf(bossHealth), 990, 675);
        
        // 그래픽으로 보스의 체력 그리기
        g.setColor(Color.RED);
        g.fillRect(1050, 650, bossHealth, 30);
        

        // 모든 Java 스레드를 그립니다.
        for (JavaThread javaThread : javaThreads) {
            g.drawImage(javaImage, javaThread.getJavaX(), javaThread.getJavaY(), JAVA_IMAGE_SIZE, JAVA_IMAGE_SIZE, this);
        }
        // 화살
        for (ArrowThread arrowThread : arrowThreads) {
            Image arrowImage = new ImageIcon("images/boss/arrow.png").getImage();
            g.drawImage(arrowImage, arrowThread.getArrowX(), arrowThread.getArrowY(), 100, 30, this);
        }
        
        heartAt10x10.draw(g);

        
    }
}