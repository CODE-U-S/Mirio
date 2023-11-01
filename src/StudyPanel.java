import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class StudyPanel extends JPanel{
    private BufferedImage image; // 이미지를 저장할 변수
  
    // 이미지 파일 로드
    public StudyPanel() {
    	// 화면 전환
    	setLayout(new BorderLayout());
    	
        try {
            // 이미지 파일을 로드합니다. 이미지 파일은 images 폴더에 있어야 합니다.
            image = ImageIO.read(new File("images/start.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // 이미지를 패널에 그립니다.
            g.drawImage(image, 0, 0, 1200, 700, null);
        }
    }
}