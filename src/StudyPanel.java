import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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

public class StudyPanel extends JPanel implements KeyListener, Runnable {
	private BufferedImage backgroundtop;
	private BufferedImage backgroundbottom;
    private BufferedImage image; // 이미지를 저장할 변수
    private BufferedImage character_image;
    
    private BufferedImage food_cake;
    private BufferedImage food_pang;
    private BufferedImage food_egg;
    
    private BufferedImage good_english;
    private BufferedImage good_jsp;
    private BufferedImage good_cpp;
    private BufferedImage good_luck;
    
    private BufferedImage bad_card;
    private BufferedImage bad_chip;    
    
    
    private BufferedImage[] item;
    private int itemSpeed = 5;
    private static int randoms[] = new int[9];

    private int character_y = 550;
    private static int character_x = 60;
    
    private boolean cnt = true;
    private int x;
    private int y;
    
    private JLabel text_label;
    private int initalTimer = 60;

    
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

    
    // 이미지 파일 로드
    public StudyPanel() {
    	// 화면 전환
    	setLayout(new BorderLayout());
    	TimerPanel timerPanel = new TimerPanel();
        try {
            // 이미지 파일을 로드합니다. 이미지 파일은 images 폴더에 있어야 함.
            image = ImageIO.read(new File("images/MirioClassLearnBackground.png"));
            character_image = ImageIO.read(new File("images/character02.png"));
            backgroundtop = ImageIO.read(new File("images/Mirio_backgroundtop.png"));
            backgroundbottom = ImageIO.read(new File("images/Mirio_backgroundBottom.png"));
            item = new BufferedImage[] {
                    ImageIO.read(new File("images/cake.png")),
                    ImageIO.read(new File("images/pang.png")),
                    ImageIO.read(new File("images/egg.png")),
                    ImageIO.read(new File("images/english.png")),
                    ImageIO.read(new File("images/jsp.png")),
                    ImageIO.read(new File("images/cpp.png")),
                    ImageIO.read(new File("images/luck.png")),
                    ImageIO.read(new File("images/card.png")),
                    ImageIO.read(new File("images/chip.png"))
            };
        } catch (IOException e) {
            e.printStackTrace();
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
        	Thread itemThread = new Thread(this);
        	itemThread.start();
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

    public void run() {
        while (true) {
            // 아이템의 y 좌표를 증가시켜 아이템을 아래로 떨어트림.
            y += itemSpeed;

            // 화면을 다시 그려서 아이템을 이동.
            repaint();

            // 일정 시간 동안 정지(밀리초 단위)
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
        if (character_image != null) g.drawImage(character_image, character_x, character_y, this);
    }
}
