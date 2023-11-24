import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Q9 extends JFrame {
    private JProgressBar progressBar;
    private JButton startButton;
    private JButton resetButton;
    private Timer timer;
    private int progressValue = 0;

    public Q9() {
        setTitle("Progress Bar Example");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        panel.add(progressBar);

        startButton = new JButton("Start");
        resetButton = new JButton("Reset");

        panel.add(startButton);
        panel.add(resetButton);

        add(panel);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startProgress();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetProgress();
            }
        });

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (progressValue < 100) {
                    progressValue++;
                    progressBar.setValue(progressValue);
                } else {
                    timer.stop();
                }
            }
        });

        setVisible(true);
    }

    private void startProgress() {
        if (!timer.isRunning()) {
            progressValue = 0;
            progressBar.setValue(progressValue);
            timer.start();
        }
    }

    private void resetProgress() {
        if (timer.isRunning()) {
            timer.stop();
        }
        progressValue = 0;
        progressBar.setValue(progressValue);
    }

    public static void main(String[] args) {
        new Q9();
    }
}
