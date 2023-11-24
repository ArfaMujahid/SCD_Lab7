import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Q8 extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private List<User> users = new ArrayList<>();

    public Q8() {
        setTitle("Login System");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        users.add(new User("user1", "password1"));
        users.add(new User("user2", "password2"));
        users.add(new User("user3", "password3"));

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                if (authenticateUser(username, password)) {
                    JOptionPane.showMessageDialog(Q8.this, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(Q8.this, "Login failed. Please check your credentials.");
                }
                passwordField.setText("");
            }
        });

        setVisible(true);
    }

    private boolean authenticateUser(String username, char[] password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(new String(password))) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        new Q8();
    }
}

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
