import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TimerPanel extends JPanel {
    private JLabel timerLabel;
    private int remainingTimeInSeconds = 60; // 초기 시간 설정 (1분 = 60초)

    public TimerPanel() {
        timerLabel = new JLabel(String.valueOf(remainingTimeInSeconds)); // 숫자만 보이도록 설정
        timerLabel.setFont(new Font("Arial", Font.BOLD, 48)); // 폰트 크기 조절
        add(timerLabel);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTimeInSeconds--;
                if (remainingTimeInSeconds >= 0) {
                    timerLabel.setText(String.valueOf(remainingTimeInSeconds));
                } else {
                    timerLabel.setText("시간 종료!");
                    ((Timer) e.getSource()).stop(); // 타이머 중지
                }
            }
        });
        timer.start(); // 타이머 시작
    }
}
