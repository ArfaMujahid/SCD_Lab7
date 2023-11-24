import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Q4 extends JFrame {
    private JLabel inputLabel, outputLabel, resultLabel;
    private JTextField inputField;
    private JButton convertButton;
    private JComboBox<String> sourceUnit, targetUnit;

    public Q4() {
        setTitle("Temperature Converter");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inputLabel = new JLabel("Enter Temperature:");
        inputField = new JTextField(10);

        sourceUnit = new JComboBox<>(new String[] { "Celsius", "Fahrenheit" });
        targetUnit = new JComboBox<>(new String[] { "Celsius", "Fahrenheit" });

        outputLabel = new JLabel("Converted Temperature:");
        resultLabel = new JLabel("");

        convertButton = new JButton("Convert");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(inputLabel);
        panel.add(inputField);
        panel.add(sourceUnit);
        panel.add(targetUnit);
        panel.add(outputLabel);
        panel.add(resultLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(convertButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertTemperature();
            }
        });

        this.setVisible(true);
    }

    private void convertTemperature() {
        try {
            double inputTemp = Double.parseDouble(inputField.getText());

            if (sourceUnit.getSelectedIndex() == 0 && targetUnit.getSelectedIndex() == 1) {
                double convertedTemp = (inputTemp * 9 / 5) + 32;
                resultLabel.setText(String.format("%.2f °F", convertedTemp));
            } else if (sourceUnit.getSelectedIndex() == 1 && targetUnit.getSelectedIndex() == 0) {
                double convertedTemp = (inputTemp - 32) * 5 / 9;
                resultLabel.setText(String.format("%.2f °C", convertedTemp));
            } else {
                resultLabel.setText("Invalid conversion");
            }
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input. Please enter a numeric temperature.");
        }
    }

    public static void main(String[] args) {
        new Q4();
    }
}
