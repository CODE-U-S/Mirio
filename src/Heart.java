import javax.swing.*;
import java.awt.*;

public class Heart {
    private static final int SIZE = 30;
    private static final int SPACING = 10;

    private static int count = 5; // 정적 변수로 변경

    private int x;
    private int y;

    public Heart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        int currentX = x;
        int currentY = y;

        if (count > 0) {
            for (int i = 0; i < count; i++) {
                Image heart = new ImageIcon("images/boss/arrow02.png").getImage();
                g.drawImage(heart, currentX, currentY, SIZE, SIZE, null);
                currentX += SIZE + SPACING;
            }
        }
    }

    public static int getCount() {
        return count;
    }

    public static void setCount() {
        count--;
        // Additional logic if needed
    }
}