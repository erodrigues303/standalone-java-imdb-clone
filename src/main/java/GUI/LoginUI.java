package GUI;

import GUI.Dashboard.Dashboard;
//import GUI.Dashboard.DashboardUI;
import Models.User;
import Services.DbFunctions;
import Services.UserService;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UserService userService = new UserService();

    public LoginUI() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> showRegistrationForm());
        panel.add(registerButton);

        add(panel);
        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        User user = userService.authenticateUser(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful");
            Dashboard dash = new Dashboard(user);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password");
        }
    }

    private boolean authenticateUser(String username, String password) {
        try (Connection conn = DbFunctions.connect()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showRegistrationForm() {
        JDialog registrationDialog = new JDialog(this, "Register", true);
        registrationDialog.setLayout(new GridLayout(3, 2));
        registrationDialog.setSize(300, 150);
        registrationDialog.setLocationRelativeTo(this);

        registrationDialog.add(new JLabel("Username:"));
        JTextField regUsernameField = new JTextField();
        registrationDialog.add(regUsernameField);

        registrationDialog.add(new JLabel("Password:"));
        JPasswordField regPasswordField = new JPasswordField();
        registrationDialog.add(regPasswordField);

        JButton registerConfirmButton = new JButton("Register");
        registerConfirmButton.addActionListener(e -> registerUser(regUsernameField.getText(), new String(regPasswordField.getPassword()), registrationDialog));
        registrationDialog.add(registerConfirmButton);

        registrationDialog.setVisible(true);
    }

    private void registerUser(String username, String password, JDialog dialog) {
        User newUser = new User(username, password);

            if(userService.createUser(newUser)) {
                JOptionPane.showMessageDialog(this, "Registration successful");
                dialog.dispose(); // Close the registration dialog after successful registration
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed");
            }

    }
}
