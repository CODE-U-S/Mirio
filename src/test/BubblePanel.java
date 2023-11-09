package test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BubblePanel extends JPanel {
    private int x = 70; // Player's x-coordinate
    private int y = 535; // Player's y-coordinate

    private boolean isRight; // Is the right arrow key pressed?
    private boolean isLeft; // Is the left arrow key pressed?
    private boolean isJump; // Is the jump key pressed?

    private static final int JUMPSPEED = 2; // Jump speed
    private static final int SPEED = 4; // Movement speed

    public BubblePanel() {
        setFocusable(true); // Allows the panel to get focus for key listeners
        setPreferredSize(new Dimension(1000, 640)); // Set preferred panel size

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (!isRight) {
                        isRight = true;
                        moveRight();
                        System.out.println("Pressed right arrow key");
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (!isLeft) {
                        isLeft = true;
                        moveLeft();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (!isJump) {
                        isJump = true;
                        jump();
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // Not used
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    isRight = false;
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    isLeft = false;
                }
            }
        });
    }

    private void moveRight() {
        new Thread(() -> {
            while (isRight) {
                x += SPEED;
                setPlayerLocation();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void moveLeft() {
        new Thread(() -> {
            while (isLeft) {
                x -= SPEED;
                setPlayerLocation();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void jump() {
        isJump = true;
        new Thread(() -> {
            for (int i = 0; i < 130 / JUMPSPEED; i++) {
                y -= JUMPSPEED;
                setPlayerLocation();
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 130 / JUMPSPEED; i++) {
                y += JUMPSPEED;
                setPlayerLocation();
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isJump = false;
        }).start();
    }

    private void setPlayerLocation() {
        SwingUtilities.invokeLater(() -> {
            // Set the location of the player within the panel
            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Paint the player at the current location
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 30, 30); // For demonstration, draws a blue rectangle as the player
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bubble Panel Frame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            BubblePanel panel = new BubblePanel();
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
