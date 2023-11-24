import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Q2 extends JFrame {
    private JTextField display;
    private String currentInput = "";
    private double firstOperand = 0;
    private String operator = "";
    private String totalInput = "";

    public Q2() {
        setTitle("Simple Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));

        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(display);

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String button : buttons) {
            JButton btn = new JButton(button);
            btn.addActionListener(new ButtonListener());
            panel.add(btn);
        }

        add(panel);
        setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String buttonText = ((JButton) e.getSource()).getText();

            if (isNumeric(buttonText) || buttonText.equals(".")) {
                currentInput += buttonText;
                totalInput += buttonText;
            } else if (isOperator(buttonText)) {
                if (!currentInput.isEmpty()) {
                    if (!operator.isEmpty()) {
                        double secondOperand = Double.parseDouble(currentInput);
                        double result = calculate(firstOperand, secondOperand, operator);
                        currentInput = Double.toString(result);
                        totalInput += operator + currentInput;
                    }
                    firstOperand = Double.parseDouble(currentInput);
                    operator = buttonText;
                    totalInput += operator;
                    currentInput = "";
                }
            } else if (buttonText.equals("=")) {
                if (!currentInput.isEmpty() && !operator.isEmpty()) {
                    double secondOperand = Double.parseDouble(currentInput);
                    double result = calculate(firstOperand, secondOperand, operator);
                    display.setText(Double.toString(result));
                    currentInput = Double.toString(result);
                    operator = "";
                    totalInput += "=" + currentInput;
                }
            }
            display.setText(totalInput);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(String str) {
        return str.matches("[+\\-*/]");
    }

    private double calculate(double num1, double num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    return 0;
                }
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        new Q2();
    }
}
