import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RunPanel extends JPanel {
    private int x = 70; // 플레이어의 x 좌표
    private int y = 535; // 플레이어의 y 좌표

    private boolean isRight; // 오른쪽 방향 키가 눌렸는지 확인
    private boolean isLeft; // 왼쪽 방향 키가 눌렸는지 확인
    private boolean isJump; // 점프 중인지 확인

    private static final int JUMPSPEED = 2; // 점프 속도
    private static final int SPEED = 4; // 이동 속도

    public RunPanel(CardLayout cardLayout, JPanel cardPanel) {
        setFocusable(true); // 패널이 포커스를 받을 수 있도록 함
        setPreferredSize(new Dimension(1000, 640)); // 패널의 크기 설정

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
                    jump();
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
            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 30, 30);
    }


}
