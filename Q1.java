import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

public class Q1 extends JFrame {
    private int clickCount = 0;

    public static void main(String[] args) {
        new Q1();
    }

    Q1() {
        JFrame mainframe = new JFrame();
        JPanel outerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel innerPanel = new JPanel();

        innerPanel.setLayout((LayoutManager) new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        JLabel buttonlabel = new JLabel("CLICK BUTTON");
        JButton button = new JButton("CLICK ME");
        JLabel buttonlabelcount = new JLabel("Times Clicked");

        innerPanel.add(buttonlabel);
        innerPanel.add(button);
        innerPanel.add(buttonlabelcount);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickCount++;
                buttonlabelcount.setText("Times Clicked: " + clickCount);
            }
        });

        outerPanel.add(innerPanel);
        mainframe.add(outerPanel);
        mainframe.setSize(400, 200);

        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setVisible(true);
    }
}
