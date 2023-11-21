import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class TestPanel extends JPanel {
	
    private BufferedImage backgroundTop; // 이미지를 저장하기 위한 변수
    private BufferedImage backgroundBottom;
    private BufferedImage omr;
    private CardLayout cardLayout; // 화면 전환
    private JPanel cardPanel; // 화면 전환
    private JButton[] testbutton = new JButton[5];
    private int omr_y = 300;
    private int x[] = new int[5];
    private int x_index;
    int y = 330;
    boolean button_click = false;
    boolean count = true;
    
    public TestPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        ImageIcon black = new ImageIcon("images/black.png");
        try {
        	//이미지
            backgroundTop = ImageIO.read(new File("images/testpanel_backgroundTop.png"));
            backgroundBottom = ImageIO.read(new File("images/testPanel_backgroundBottom.png"));
            omr = ImageIO.read(new File("images/omr.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 투명한 버튼 생성
        for(int i = 0; i < 5; i++) {
        	testbutton[i] = new JButton(); // 각 요소에 JButton 생성
	        testbutton[i].setContentAreaFilled(false); // 버튼의 내용 영역을 투명하게 만듭니다
	        testbutton[i].setOpaque(false); // 버튼을 투명하게 만듭니다.
	        testbutton[i].setBorderPainted(false); // 버튼 테두리 설정해제
	        int buttonIndex = i;	//람다식에선 for문의 i를 사용할 수 없어서 복사본을 만들어 사용
	        switch(i) {
		        case 0: x[i] = 250; break;
		        case 1 : x[i] = 400; break;
		        case 2 : x[i] = 553; break;
		        case 3 : x[i] = 690; break;
		        case 4 : x[i] = 840; break;
	        }
	        testbutton[i].addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	x_index = x[buttonIndex];
	            	testbutton[buttonIndex].setIcon(black); // 버튼 이미지 변환
	            	testbutton[buttonIndex].setOpaque(true);
	            	new Thread(new Runnable() {
	            		@Override
	            		public void run() {
	            			if (count) {
	            				count = false;	//한번만 실행
		            			while(omr_y <= 300 && omr_y >= -1878) {
		            				omr_y -= 2;
		            				if(y != 150) {
		            					y -= 2;
		            					testbutton[buttonIndex].setBounds(x_index, y, 90, 150);
		            				} else {
		            					testbutton[buttonIndex].setOpaque(false);
		            					y = 330;
		            				}
		            				try {//omr 종이 이동 멈추는 지점
		            					if(omr_y == 48 || omr_y == -178 || omr_y == -438 || omr_y == -678 || omr_y == -908 || omr_y == -1158 || omr_y == -1400 || omr_y == -1638 || omr_y == -1878){
		            						Thread.sleep(1000);
		            					} else {            						
		            						Thread.sleep(5);
		            					}
		            					
		            				} catch (InterruptedException e) {
		            					e.printStackTrace();
		            				}
		            				repaint();
		            			}
	            			}
	            		}
	            	}).start();
	            }
	        });
	        testbutton[i].setBounds(x[i], 330, 90, 150); // 버튼의 위치와 크기를 설정 //150
	        setLayout(null); // 레이아웃 관리자를 사용하지 않고 직접 위치 설정
	        add(testbutton[i]); // 패널에 버튼을 추가
        }
        
        
        // Enter키 눌러서 보스패널로 이동하기
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        getActionMap().put("enterAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Move to BossPanel when Enter key is pressed
                cardLayout.show(cardPanel, "BossPanel");
            }
        });

       
    	
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(omr != null)
        	g.drawImage(omr, 0, omr_y, 1200, 2400, null);
        if (backgroundTop != null) {
            // 이미지를 패널에 그립니다.
            g.drawImage(backgroundTop, 0, 0, 1200, 300, null);
            g.drawImage(backgroundBottom, 0, 500, 1200, 300, null);
        }
    }
}
