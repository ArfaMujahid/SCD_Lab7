import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Q6 extends JFrame {
    private JPanel colorPanel;
    private JComboBox<String> colorSelector;

    public Q6() {
        setTitle("Color Palette");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        colorPanel = new JPanel();
        colorPanel.setBackground(Color.WHITE);
        colorPanel.setPreferredSize(new Dimension(300, 300));

        colorSelector = new JComboBox<>(new String[] { "White", "Red", "Green", "Blue" });
        colorSelector.setSelectedIndex(0);

        JButton applyButton = new JButton("Apply Color");

        setLayout(new FlowLayout());
        add(colorSelector);
        add(applyButton);
        add(colorPanel);

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applySelectedColor();
            }
        });

        setVisible(true);
    }

    private void applySelectedColor() {
        String selectedColor = (String) colorSelector.getSelectedItem();

        if (selectedColor.equals("White")) {
            colorPanel.setBackground(Color.WHITE);
        } else if (selectedColor.equals("Red")) {
            colorPanel.setBackground(Color.RED);
        } else if (selectedColor.equals("Green")) {
            colorPanel.setBackground(Color.GREEN);
        } else if (selectedColor.equals("Blue")) {
            colorPanel.setBackground(Color.BLUE);
        }
    }

    public static void main(String[] args) {
        new Q6();
    }
}
