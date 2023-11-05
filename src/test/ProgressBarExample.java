package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProgressBarExample extends JFrame {
    public ProgressBarExample() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 100);
        setLayout(null);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(20, 20, 1145, 25);
        progressBar.setValue(100);
        progressBar.setForeground(Color.PINK);
        Color customYellow = new Color(254, 239, 197);
        progressBar.setBackground(customYellow);
        add(progressBar);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 100; i >= 0; i--) {
                    progressBar.setValue(i);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                return null;
            }
        };
        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProgressBarExample progressBarExample = new ProgressBarExample();
            progressBarExample.setVisible(true);
        });
    }
}
