import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//TODO
//1. 아이템 밑으로 떨어지게 만들기
//2. 아이템 캐릭터와 닿으면 사라지게 만들기
//4. 시험 화면으로 전환
//5. 이미지 삽입
//6. test지 위로 올라가게 움직이기
//7. test지 일정 위치에서 멈추게 만들기.
//8. 결과 화면으로 전환

public class StudyPanel extends JPanel implements KeyListener {
	private CardLayout cardLayout; // 화면 전환
    private JPanel cardPanel; // 화면 전환
    
	private BufferedImage backgroundtop;
	private BufferedImage backgroundbottom;
    private BufferedImage image; // 이미지를 저장할 변수
    private BufferedImage character_image;
    
    private BufferedImage airpods_icon;
    private BufferedImage apple_icon;
    private BufferedImage badminton_icon;
    
    private BufferedImage ball_icon;
    private BufferedImage ipad_icon;
    private BufferedImage food_icon;
    private BufferedImage pillow_icon;
    private BufferedImage joycon_icon;
    private BufferedImage youtube;    
    
    
    private BufferedImage[] item;
    private int itemSpeed = 5;
    private static int randoms[] = new int[9];

    private int character_y = 550;
    private static int character_x = 60;
    
    private boolean cnt = true;
    private int x;
    private int y = -10;
    
    public static JLabel text_time;
    private int initalTimer = 60;
    
    private Heart heartAt950x10; //하트를 관리할 객체

    
    static {//랜덤 난수 생성
    	
    	for (int i = 0; i < 9; i++) {
            int random = (int) (Math.random() * (1000 - (character_x + 120))) + character_x;// 60부터 940까지의 랜덤한 숫자 생성

            // 배열에 중복된 숫자가 있는지 확인
            boolean isDuplicate = false;
            for (int j = 0; j < i; j++) {
                if (randoms[j] == random) {
                    isDuplicate = true;
                    break;
                }
            }

            // 중복된 숫자가 없으면 배열에 추가, 있으면 현재 인덱스(i)를 감소시켜 다시 생성
            if (!isDuplicate) {
                randoms[i] = random;
            } else {
                i--;
            }
        }

        // 중복되지 않은 9개의 랜덤한 숫자가 배열에 저장됨
    }
    private Image player; // 캐릭터 이미지를 저장할 Image 변수
    private String playerU; // 이미지 경로 저장할 변수
    private String playerD; // 이미지 경로 저장할 변수
    
    public void setCharacterImage(String characterSelection) {
        this.playerD = characterSelection + ".png";
        playerU = characterSelection + "_up.png";
        player = new ImageIcon(playerD).getImage();
    }

    // 이미지 파일 로드
    public StudyPanel(CardLayout cardLayout, JPanel cardPanel) {
    	 this.cardLayout = cardLayout;
         this.cardPanel = cardPanel;
         
         // MouseListener로 화면 전환
         addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent e) {
                 // 마우스 왼쪽 버튼을 클릭했을 때 화면 전환: ExplanationPanel에서 RunPanel로 전환
                 cardLayout.show(cardPanel, "TestPanel");
             }
         });

         setFocusable(true); // 패널이 키 이벤트를 받을 수 있도록 설정
         
         // 특정 위치(300, 600)에 하트 생성
         heartAt950x10 = new Heart(950, 10);
         
    	// 화면 전환
    	setLayout(new BorderLayout());
        try {
            // 이미지 파일을 로드합니다. 이미지 파일은 images 폴더에 있어야 함.
            image = ImageIO.read(new File("images/MirioClassLearnBackground.png"));
           
            backgroundtop = ImageIO.read(new File("images/Mirio_backgroundtop.png"));
            backgroundbottom = ImageIO.read(new File("images/Mirio_backgroundBottom.png"));
            item = new BufferedImage[] {
                    ImageIO.read(new File("images/airpods_icon.png")),
                    ImageIO.read(new File("images/apple_icon.png")),
                    ImageIO.read(new File("images/badminton_icon.png")),
                    ImageIO.read(new File("images/ball_icon.png")),
                    ImageIO.read(new File("images/food_icon.png")),
                    ImageIO.read(new File("images/ipad_icon.png")),
                    ImageIO.read(new File("images/joycon_icon.png")),
                    ImageIO.read(new File("images/pillow_icon.png")),
                    ImageIO.read(new File("images/youtube_icon.png"))
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
        text_time = new JLabel(String.valueOf(initalTimer));//text_time을 60초로 초기화
        text_time.setFont(new Font("Arial", Font.BOLD, 60)); // 원하는 폰트와 크기로 설정
        text_time.setForeground(Color.WHITE); // 라벨 텍스트 색상 설정
        text_time.setOpaque(false); // 라벨 배경 투명 설정
        if (text_time != null) {
            add(text_time, BorderLayout.NORTH); // 라벨을 패널의 상단에 추가
        }
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // 왼쪽 화살표 키를 눌렀을 때 이미지의 x 좌표를 감소시켜 왼쪽으로 이동
        if (keyCode == KeyEvent.VK_LEFT) {
        	character_x -= 10;
        	
        }
        // 오른쪽 화살표 키를 눌렀을 때 이미지의 x 좌표를 증가시켜 오른쪽으로 이동
        else if (keyCode == KeyEvent.VK_RIGHT) {
        	character_x += 10;
        }
        if(cnt) {
        	new Thread(new Runnable() {
				@Override
				public void run() {
					while(initalTimer > 0) {
			            // 아이템의 y 좌표를 증가시켜 아이템을 아래로 떨어트림.
			            for (int i = 0; i < 9; i++) {
			            	y += itemSpeed;
			                if (y > getHeight()) {
			                    y = -50; // 화면 밖으로 나가면 다시 위로 이동
			                    for(int j = 0; j < 9; j++) {
			                    	
			                    	randoms[j] = (int) (Math.random() * (1000 - (80))+60);
			                    	//60은 아이템이 테두리 밖으로 나가지 못하도록 구역을 정해준 것.	                    	
			                    }
			                }			                
			                // 화면을 다시 그려서 아이템을 이동.
			                repaint();
			            }

			            // 일정 시간 동안 정지(밀리초 단위)
			            try {
			                Thread.sleep(100);
			            } catch (InterruptedException e) {
			                e.printStackTrace();
			            }
					}
				}
			}).start();
        	
        	new Thread(new Runnable() {
				@Override
				public void run() {
					while(initalTimer >= 0) {
			            text_time.setText(String.valueOf(initalTimer));
			            initalTimer -= 1;

			            // 화면을 다시 그려서 아이템을 이동.
			            repaint();

			            // 일정 시간 동안 정지(밀리초 단위)
			            try {
			                Thread.sleep(1000);
			            } catch (InterruptedException e) {
			                e.printStackTrace();
			            }
					}
				}
			}).start();
        	
        	cnt = false;
        }
        repaint(); // 이미지의 위치가 변경되었으므로 화면을 다시 그리도록 요청
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        // 사용하지 않음 무조건 넣어줘야 오류가 안 남.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // 사용하지 않음 무조건 넣어줘야 오류가 안 남.
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (image != null) g.drawImage(image, 0, 0, 1200, 700, this);// 이미지를 패널에 그림.
        for(int i = 0; i < 9; i++) {
        	if(item[i] != null) {
        		g.drawImage(item[i], randoms[i], y, this);
        	}
        }
        if(backgroundtop != null) g.drawImage(backgroundtop,  0,  -30,  1198, 100, this);
        if(backgroundbottom != null) g.drawImage(backgroundbottom, 2, 638, 1198, 100, this);
        if (player != null) g.drawImage(player, character_x, character_y, this);
        
        heartAt950x10.draw(g);
    }
}
