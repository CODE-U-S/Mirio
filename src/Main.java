import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Swing GUI 생성을 위한 메인 스레드에서 실행
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        // JFrame (창) 생성 및 설정
        JFrame frame = new JFrame("GUI Example");
        frame.setSize(1200, 700); // 크기 설정
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 동작 설정

        // 컨텐트 패널 생성 및 컨텐트 패널에 컴포넌트 추가
        JPanel contentPanel = new JPanel();
        JLabel label = new JLabel("Hello, Java G8UI!");
        contentPanel.add(label);

        // 컨텐트 패널을 프레임에 추가
        frame.add(contentPanel);

        // 프레임 표시
        frame.setVisible(true);
    }
}