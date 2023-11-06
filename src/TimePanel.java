import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimePanel extends JPanel implements Runnable {
    private JLabel timerLabel;
    private int timeRemaining;

    public TimePanel(int initialTime) {
        this.timeRemaining = initialTime;
        timerLabel = new JLabel(Integer.toString(timeRemaining));
        timerLabel.setOpaque(false);	//투명
        timerLabel.setFont(new Font("SansSerif", Font.PLAIN, 50));	//폰트 크기 조절
        Dimension labelSize = new Dimension(60, 50); // 라벨 크기
        timerLabel.setPreferredSize(labelSize);
        add(timerLabel);

        Thread timerThread = new Thread(this);
        timerThread.start();
    }

    @Override
    public void run() {
        while (timeRemaining > 0) {
            timeRemaining--;
            timerLabel.setText(Integer.toString(timeRemaining));
            try {
                Thread.sleep(1000); // 1초 동안 기다림
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 시간이 다 되면 여기에 필요한 작업을 추가하세요.
        // 예를 들어, 시험 종료 메시지 출력이나 결과 화면으로 전환하는 코드를 작성할 수 있습니다.
    }
}
