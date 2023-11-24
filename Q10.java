import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Q10 extends JFrame {
    private Calendar currentCalendar;
    private JLabel monthLabel;
    private JPanel dayPanel;
    private Map<Date, String> events;

    public Q10() {
        setTitle("Monthly Calendar");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentCalendar = Calendar.getInstance();
        currentCalendar.set(Calendar.DAY_OF_MONTH, 1);

        events = new HashMap<>();

        monthLabel = new JLabel();
        updateMonthLabel();

        dayPanel = new JPanel(new GridLayout(6, 7));

        updateCalendar();

        JButton prevButton = new JButton("Previous Month");
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCalendar.add(Calendar.MONTH, -1);
                updateMonthLabel();
                updateCalendar();
            }
        });

        JButton nextButton = new JButton("Next Month");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCalendar.add(Calendar.MONTH, 1);
                updateMonthLabel();
                updateCalendar();
            }
        });

        add(monthLabel, BorderLayout.NORTH);
        add(dayPanel, BorderLayout.CENTER);
        add(prevButton, BorderLayout.WEST);
        add(nextButton, BorderLayout.EAST);

        setVisible(true);
    }

    private void updateMonthLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        monthLabel.setText(sdf.format(currentCalendar.getTime()));
    }

    private void updateCalendar() {
        dayPanel.removeAll();
        Calendar calendarCopy = (Calendar) currentCalendar.clone();
        calendarCopy.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendarCopy.get(Calendar.DAY_OF_WEEK);
        int lastDayOfMonth = calendarCopy.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i < firstDayOfWeek; i++) {
            dayPanel.add(new JLabel(""));
        }

        for (int day = 1; day <= lastDayOfMonth; day++) {
            Date date = calendarCopy.getTime();
            JButton dayButton = new JButton(Integer.toString(day));
            dayButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectDate(date);
                }
            });

            String event = events.get(date);
            if (event != null) {
                dayButton.setToolTipText(event);
                dayButton.setForeground(Color.RED);
            }

            dayPanel.add(dayButton);
            calendarCopy.add(Calendar.DAY_OF_MONTH, 1);
        }

        revalidate();
        repaint();
    }

    private void selectDate(Date date) {
        String event = events.get(date);
        String input = JOptionPane.showInputDialog("Enter event for " + date.toString() + ":");
        if (input != null && !input.isEmpty()) {
            events.put(date, input);
            updateCalendar();
        }
    }

    public static void main(String[] args) {
        new Q10();
    }
}
