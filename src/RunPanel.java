import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.border.Border;

import java.util.ArrayList;
import java.util.List;

public class RunPanel extends JPanel {
	private CardLayout cardLayout; //화면 전환
    private JPanel cardPanel; // 화면 전환
    
    private int x = 70; // 플레이어의 x 좌표
    private int y = 600; // 플레이어의 y 좌표
    private Image backgroundImage; // 배경 이미지를 저장할 Image 변수
    private Image player; // 캐릭터 이미지를 저장할 Image 변수
    private String playerU; // 이미지 경로 저장할 변수
    private String playerD; // 이미지 경로 저장할 변수

    private boolean isRight; // 오른쪽 방향 키가 눌렸는지 확인
    private boolean isLeft; // 왼쪽 방향 키가 눌렸는지 확인
    private boolean isJump; // 점프 중인지 확인

    private static final int JUMPSPEED = 2; // 점프 속도
    private static final int SPEED = 3; // 이동 속도
    JProgressBar progressBar = new JProgressBar(0, 100); //진행바
    //private volatile boolean isRight = true; // isRight가 제어 변수라고 가정합니다
    private volatile boolean workerRunning = false; // SwingWorker가 실행 중인지를 추적하는 변수
    private SwingWorker<Void, Void> worker; // 메서드 바깥에 SwingWorker 선언
    
    private Timer movementTimer; 
    
    private List<Block> blocks = new ArrayList<>(); // 블록
    
    public void setCharacterImage(String characterSelection) {
        this.playerD = characterSelection+ ".png";
        playerU = characterSelection+ "_up.png";;
        player = new ImageIcon(playerD).getImage();
        
    }
   

    public RunPanel(CardLayout cardLayout, JPanel cardPanel) {
    	this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        setFocusable(true); // 패널이 포커스를 받을 수 있도록 함
       // setPreferredSize(new Dimension(1000, 640)); // 패널의 크기 설정
        
        // 배경 이미지 로드
        backgroundImage = new ImageIcon("images/run.png").getImage();
        
        
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
        setLayout(null); // 레이아웃 관리자를 사용하지 않고 직접 위치 설정
        runbtn.setBounds(700, 100, 425, 425); // 버튼의 위치와 크기를 설정
        add(runbtn); // 패널에 버튼을 추가
        
        // 진행바
       
        progressBar.setBounds(20, 20, 1145, 25);
        progressBar.setValue(100);
        progressBar.setForeground(Color.PINK);
        Color customYellow = new Color(254, 239, 197);
        progressBar.setBackground(customYellow);
        add(progressBar);

        // 생성자 또는 초기화 블록 내부에서 SwingWorker를 초기화합니다
        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 100; i >= 0; i--) {
                    progressBar.setValue(i);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                workerRunning = false; // SwingWorker가 작업을 완료하면 플래그를 재설정합니다
                return null;
            }
        };
        
        movementTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRight) {
                    x += SPEED;
                } else if (isLeft) {
                    x -= SPEED;
                }

                setPlayerLocation();
            }
        });
        movementTimer.start();
        
        // 블록 추가 예시
        Block block1 = new Block(200, 600, 100, 20);
        addBlock(block1);
        Block block2 = new Block(300, 500, 100, 20);
        addBlock(block2);
        Block block3 = new Block(400, 400, 100, 20);
        addBlock(block3);
        Block block4 = new Block(500, 300, 100, 20);
        addBlock(block4);
        Block block5 = new Block(600, 200, 100, 20);
        addBlock(block5);

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        // 오른쪽 키
        KeyStroke rightKeyReleased = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false);
        inputMap.put(rightKeyReleased, "RightReleased");
        actionMap.put("RightReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                isRight = true;
                moveRight();
            }
        });

        KeyStroke rightKeyPressed = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true);
        inputMap.put(rightKeyPressed, "RightPressed");
        actionMap.put("RightPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                isRight = false;
            }
        });

        // 왼쪽 키
        KeyStroke leftKeyReleased = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false);
        inputMap.put(leftKeyReleased, "LeftReleased");
        actionMap.put("LeftReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                isLeft = true;
                moveLeft();
            }
        });

        KeyStroke leftKeyPressed = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true);
        inputMap.put(leftKeyPressed, "LeftPressed");
        actionMap.put("LeftPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                isLeft = false;
            }
        });

        // 위쪽 키
        KeyStroke upKey = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false);
        inputMap.put(upKey, "Up");
        actionMap.put("Up", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!isJump) {
                    isJump = true;
                    moveJump();
                }
            }
        });
    }

    private void moveRight() {
        isRight = true;
        isLeft = false;
        // 진행바 실행
        if (!workerRunning) {
            workerRunning = true; // SwingWorker가 실행 중임을 나타내는 플래그를 설정합니다
            worker.execute(); // SwingWorker 실행
            //workerRunning = false; // 움직임이 끝나면 SwingWorker 플래그를 재설정합니다
        }
        
    }

    private void moveLeft() {
        isLeft = true;
        isRight = false;
    }
    
    private void stopMovement() {
        isLeft = false;
        isRight = false;
    }
    
    private boolean isJumping; // 캐릭터가 점프 중인지 여부를 나타내는 변수


    private void moveJump() {
        isJump = true;
        
        // Change player image
        player = new ImageIcon(playerU).getImage();

        int jumpHeight = 130;
        int jumpSpeed = JUMPSPEED;
        int jumpDuration = jumpHeight / jumpSpeed;

        Timer jumpTimer = new Timer(2, new ActionListener() {
            private int currentJumpHeight = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentJumpHeight < jumpDuration) {
                    y -= jumpSpeed;
                    setPlayerLocation();
                    currentJumpHeight++;
                } else {
                    ((Timer) e.getSource()).stop();

                    Timer fallTimer = new Timer(3, new ActionListener() {
                        private int currentFallHeight = 0;

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (currentFallHeight < jumpDuration) {
                                y += jumpSpeed;
                                setPlayerLocation();
                                currentFallHeight++;
                            } else {
                                ((Timer) e.getSource()).stop();
                                isJump = false;

                                // Change player image to default image in the swing thread
                                player = new ImageIcon(playerD).getImage();
                                repaint();
                            }
                        }
                    });
                    fallTimer.start();
                }
            }
        });
        jumpTimer.start();
    }



    private void setPlayerLocation() {
        SwingUtilities.invokeLater(() -> {
            repaint();
        });
    }
    
    // 블록을 추가하는 메서드
    private void addBlock(Block block) {
        blocks.add(block);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // 배경 이미지 그리기
        g.drawImage(backgroundImage, 0, 0, this);
        
     // 블록 그리기
        for (Block block : blocks) {
            g.setColor(Color.BLUE); // Set block color (you can customize)
            g.fillRect(block.getX(), block.getY(), block.getWidth(), block.getHeight());
        }

        // 캐릭터 이미지 그리기
        g.drawImage(player, x, y, this);
    }

}