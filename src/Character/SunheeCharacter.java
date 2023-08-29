package Character;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SunheeCharacter {
    private List<ImageIcon> walkFrames;
    private int currentFrameIndex;
    private int x, y;
    private int frameDelay = 100; // 각 프레임 간의 시간 간격 (밀리초)
    private long lastFrameTime;

    public SunheeCharacter(int initialX, int initialY) {
        x = initialX;
        y = initialY;

        walkFrames = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String imagePath = "/images/char_sun/run" + i + ".png"; // 이미지 경로 생성
            ImageIcon frame = new ImageIcon(getClass().getResource(imagePath));
            walkFrames.add(frame);
        }
        currentFrameIndex = 0;
        lastFrameTime = System.currentTimeMillis();
    }

    public void moveRight() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= frameDelay) {
            currentFrameIndex = (currentFrameIndex + 1) % walkFrames.size();
            lastFrameTime = currentTime;
        }
        x += 5; // 오른쪽으로 이동
    }

    public void draw(Graphics g) {
        ImageIcon currentFrame = walkFrames.get(currentFrameIndex);
        g.drawImage(currentFrame.getImage(), x, y, null);
    }
}
