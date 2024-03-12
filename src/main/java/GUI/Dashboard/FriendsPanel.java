package GUI.Dashboard;
import Models.User;
import Services.DbFunctions;
import Services.FriendService;
import Services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FriendsPanel extends JPanel {
    private User user;
    private FriendService friendsService;
    private UserService userService; // Assuming UserService provides methods to fetch user details

    private JTextArea friendsListArea;
    private JTextField usernameField;

    public FriendsPanel(User user) {
        this.user = user;
        this.friendsService = new FriendService();
        this.userService = new UserService(); // Initialize UserService

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Friends");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        friendsListArea = new JTextArea();
        friendsListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(friendsListArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        add(buttonsPanel, BorderLayout.SOUTH);

        JButton addFriendButton = new JButton("Add Friend");
        JButton removeFriendButton = new JButton("Remove Friend");

        usernameField = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");

        addFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                int friendId = getUserIdByUsername(username);
                if (friendId != -1) {
                    boolean added = friendsService.addFriend(user.getUserId(), friendId);
                    if (added) {
                        // Add the friend to the other person's friend list
                        friendsService.addFriend(friendId, user.getUserId());
                        updateFriendsList();
                        JOptionPane.showMessageDialog(null, "Friend added successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add friend.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "User not found.");
                }
            }
        });

        removeFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                int friendId = getUserIdByUsername(username);
                if (friendId != -1) {
                    boolean removed = friendsService.removeFriend(user.getUserId(), friendId);
                    if (removed) {
                        // Remove the friend from the other person's friend list
                        friendsService.removeFriend(friendId, user.getUserId());
                        updateFriendsList();
                        JOptionPane.showMessageDialog(null, "Friend removed successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to remove friend.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "User not found.");
                }
            }
        });

        buttonsPanel.add(usernameLabel);
        buttonsPanel.add(usernameField);
        buttonsPanel.add(addFriendButton);
        buttonsPanel.add(removeFriendButton);

        updateFriendsList();
    }

    private void updateFriendsList() {
        List<Integer> friendIds = friendsService.getFriends(user.getUserId());
        StringBuilder sb = new StringBuilder();
        for (int friendId : friendIds) {
            // Fetch friend's name based on their ID
            User friend = userService.getUserById(friendId);
            if (friend != null) {
                sb.append(friend.getUsername()).append("\n");
            }
        }
        friendsListArea.setText(sb.toString());
    }
    private int getUserIdByUsername(String username) {
        return userService.getUserByUsername(username).getUserId();
    }
    public String getUserNameById(int userID) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        String username = null;
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            // Assuming a method that converts a ResultSet row to a Review object
            username = rs.getString("username");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }
}


