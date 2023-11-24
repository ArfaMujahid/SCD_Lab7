import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Q3 {
    private static List<String> tasks = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("To-Do List");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 400);

            DefaultListModel<String> modelDisplay = new DefaultListModel<>();
            JList<String> list = new JList<String>(modelDisplay);

            JPanel panel = new JPanel(new BorderLayout());
            frame.add(panel);
            panel.add(new JScrollPane(list), BorderLayout.CENTER);

            JTextField taskField = new JTextField(30);

            JPanel buttonPanel = new JPanel();

            panel.add(buttonPanel, BorderLayout.SOUTH);
            buttonPanel.add(taskField);

            JButton addButton = new JButton("Add");
            buttonPanel.add(addButton);

            addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String taskData = taskField.getText();
                    if (!taskData.isEmpty()) {
                        taskField.setText("");
                        tasks.add(taskData);
                        modelDisplay.addElement(taskData);
                    }
                }
            });

            JButton deleteButton = new JButton("Delete");
            buttonPanel.add(deleteButton);

            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedIndex = list.getSelectedIndex();
                    if (selectedIndex >= 0) {
                        tasks.remove(selectedIndex);
                        modelDisplay.remove(selectedIndex);
                    }
                }
            });

            JButton completeButton = new JButton("Complete");
            buttonPanel.add(completeButton);

            completeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedIndex = list.getSelectedIndex();
                    if (selectedIndex >= 0) {
                        String task = tasks.get(selectedIndex);
                        tasks.set(selectedIndex, " (DONE) " + task);
                        modelDisplay.set(selectedIndex, " (DONE) " + task);
                    }
                }
            });

            frame.setVisible(true);
        });
    }
}
