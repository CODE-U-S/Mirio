import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TestPanel extends JPanel {
	
    private BufferedImage backgroundTop; // 이미지를 저장하기 위한 변수
    private BufferedImage backgroundBottom;
    private BufferedImage omr;
    private CardLayout cardLayout; // 화면 전환
    private JPanel cardPanel; // 화면 전환

    int y = 300;
    
    public TestPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        try {
            // 이미지 파일을 불러옵니다. 이미지 파일은 images 폴더에 있어야 합니다.
            backgroundTop = ImageIO.read(new File("images/testpanel_backgroundTop.png"));
            backgroundBottom = ImageIO.read(new File("images/testPanel_backgroundBottom.png"));
            omr = ImageIO.read(new File("images/omr.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
            	new Thread(new Runnable() {
            		@Override
            		public void run() {
            			while(y <= 300) {
            				y -= 5;    				
            				System.out.println(y);
            				try {
            					if(y / 50 == 0){//omr 종이 이동
            						Thread.sleep(1000);
            					} else {            						
            						Thread.sleep(10);
            					}
            					
            				} catch (InterruptedException e) {
            					e.printStackTrace();
            				}
            				repaint();
            			}
            		}
            	}).start();
        	}
        });
    
    	
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(omr != null)
        	g.drawImage(omr, 0, y, 1200, 2400, null);
        if (backgroundTop != null) {
            // 이미지를 패널에 그립니다.
            g.drawImage(backgroundTop, 0, 0, 1200, 300, null);
            g.drawImage(backgroundBottom, 0, 500, 1200, 300, null);
        }
    }
}
