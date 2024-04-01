package GUI.Dashboard;

import Models.*;

import javax.swing.*;

import GUI.LoginUI;

import java.awt.*;

public class HeaderPanel extends JPanel {
    private JButton logoutButton;

    public HeaderPanel(User user) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0)); // Small horizontal gap, no vertical gap
        JLabel welcomeLabel = new JLabel("Welcome to the Dashboard, " + user.getUsername() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(welcomeLabel);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> restartApplication());
        add(logoutButton);
    }

    private void restartApplication() {
        Window[] openWindows = Window.getWindows();

        // Iterate over the open windows and dispose of each
        for (Window window : openWindows) {
            if (window instanceof JFrame || window instanceof JDialog) {
                window.dispose();
            }
        }

        EventQueue.invokeLater(() -> {
            new LoginUI();
        });
    }
}
