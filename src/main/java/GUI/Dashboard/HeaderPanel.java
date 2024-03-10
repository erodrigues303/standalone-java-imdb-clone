package GUI.Dashboard;

import Models.*;

import javax.swing.*;
import java.awt.*;


public class HeaderPanel extends JPanel {
    public HeaderPanel(User user) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0)); // Small horizontal gap, no vertical gap
        JLabel welcomeLabel = new JLabel("Welcome to the Dashboard, " + user.getUsername() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(welcomeLabel);
    }
}
