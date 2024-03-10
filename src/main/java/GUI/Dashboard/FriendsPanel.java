package GUI.Dashboard;

import Models.User;

import javax.swing.*;
import java.awt.*;

public class FriendsPanel extends JPanel {
    public FriendsPanel(User user) {
        setLayout(new BorderLayout());
        JLabel friendsLabel = new JLabel("Friends:");
        friendsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(friendsLabel, BorderLayout.NORTH);

        JPanel friendsListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (User friend : user.getFriendsList()) {
            JButton friendButton = new JButton(friend.getUsername());
            friendButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "You clicked on " + friend.getUsername()));
            friendsListPanel.add(friendButton);
        }
        add(friendsListPanel, BorderLayout.CENTER);
    }
}
