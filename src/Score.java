import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Score extends JPanel{
	private Image backgroundTop;
	private Image backgroundBottom;
	private Image omr;
	
	void Score() {
		setLayout(new BorderLayout());
		try {
			backgroundTop = ImageIO.read(new File("images/BackgroundTop.png"));
			backgroundBottom = ImageIO.read(new File("images/BackgroundBottom.png"));
			
			
		}catch(IOException e) {
            e.printStackTrace();
        }
	}
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(backgroundTop != null) g.drawImage(backgroundTop,  0,  -30,  1198, 100, this);
        if(backgroundBottom != null) g.drawImage(backgroundBottom, 2, 638, 1198, 100, this);
    }
}
