package test;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

public class TimeProgressBar extends JFrame {
    private int timeInSeconds = 60; // 초기 시간(초)
    private JProgressBar bar;

    public TimeProgressBar() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        bar = new JProgressBar(0, 60);
        Border border = BorderFactory.createTitledBorder("진행 바");
        bar.setBorder(border); // 진행상태바에 제목 내용 표시
        bar.setValue(60); // 초기 값: 60초
        bar.setStringPainted(true); // 진행상태바에 현재 상황 표시
        add(bar, BorderLayout.NORTH);

        Timer timer = new Timer(1000, e -> {
            timeInSeconds--; // 시간 감소
            bar.setValue(timeInSeconds); // 진행바 값 설정

            if (timeInSeconds <= 0) {
                ((Timer) e.getSource()).stop(); // 타이머 중지
            }
        });

        timer.start();

        setSize(300, 100);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TimeProgressBar();
    }
}
