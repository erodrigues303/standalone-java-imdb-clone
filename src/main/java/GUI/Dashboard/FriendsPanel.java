package GUI.Dashboard;

import GUI.FriendRequestsUI;
import Models.User;
import Services.FriendService;
import Services.UserService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FriendsPanel extends JPanel {
    private User user;
    private FriendService friendsService;
    private UserService userService; // Assuming UserService provides methods to fetch user details
    private JTextArea friendsListArea;
    private JTextField usernameField;
    private Component CenterPanel;

    public FriendsPanel(User user) {
        this.user = user;
        this.friendsService = new FriendService();
        this.userService = new UserService();

        setLayout(new BorderLayout());
        setupTitleLabel();
        setupFriendsListArea();
        setupMainPanel();
        updateFriendsList();
    }

    private void setupTitleLabel() {
        JLabel titleLabel = new JLabel("Friends");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);
    }

    private void setupFriendsListArea() {
        friendsListArea = new JTextArea();
        friendsListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(friendsListArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.SOUTH);
        setupButtonsPanel(mainPanel);
        mainPanel.add(new JPanel(), BorderLayout.CENTER); // Empty panel for spacing
        setupFriendRequestsButton(mainPanel);
    }

    private void setupButtonsPanel(JPanel mainPanel) {
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        mainPanel.add(buttonsPanel, BorderLayout.NORTH);

        setupAddFriendButton(buttonsPanel);
        setupRemoveFriendButton(buttonsPanel);
        setupUsernameField(buttonsPanel);
    }

    private void setupAddFriendButton(JPanel buttonsPanel) {
        JButton addFriendButton = new JButton("Add Friend");
        addFriendButton.addActionListener(this::addFriendAction);
        buttonsPanel.add(addFriendButton);
    }

    private void setupRemoveFriendButton(JPanel buttonsPanel) {
        JButton removeFriendButton = new JButton("Remove Friend");
        removeFriendButton.addActionListener(this::removeFriendAction);
        buttonsPanel.add(removeFriendButton);
    }

    private void setupUsernameField(JPanel buttonsPanel) {
        usernameField = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        buttonsPanel.add(usernameLabel);
        buttonsPanel.add(usernameField);
    }

    private void setupFriendRequestsButton(JPanel mainPanel) {
        JButton friendRequestsButton = new JButton("Friend Requests");
        friendRequestsButton.addActionListener(this::openFriendRequestsUI);
        mainPanel.add(friendRequestsButton, BorderLayout.SOUTH);
    }

    private void addFriendAction(ActionEvent e) {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username field cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int friendId = getUserIdByUsername(username);
            if (!user.isFriend(friendId)) {
                friendsService.sendFriendRequest(user.getUserId(), friendId);
                JOptionPane.showMessageDialog(this, "Friend request sent successfully.");
                updateFriendsList();
            } else {
                JOptionPane.showMessageDialog(this, "This user is already your friend.");
            }
        } catch (Exception ex) { // Catching a generic exception for simplicity; consider handling specific
                                 // exceptions.
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeFriendAction(ActionEvent e) {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username field cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int friendId = getUserIdByUsername(username);
            if (user.isFriend(friendId)) {
                boolean removed = friendsService.removeFriend(user.getUserId(), friendId);
                if (removed) {
                    user.removeFriend(friendId); // Assuming this method updates the user's local friend list.
                    User friend=userService.getUserByUsername(UserService.getUserById(friendId).getUsername());
                    friend.removeFriend(user.getUserId());
                    friendsService.removeFriend(friendId,user.getUserId());
                    updateFriendsList();
                    JOptionPane.showMessageDialog(this, "Friend removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to remove friend. Please try again.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "This user is not your friend.", "Information",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) { // Generic exception handling; tailor to your needs.
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openFriendRequestsUI(ActionEvent e) {
        if (FriendService.getReceivedFriendRequests(user.getUserId()).isEmpty()) {
            JOptionPane.showMessageDialog(CenterPanel, "You have no Friend Requests right now.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFrame frame = new JFrame("Friend Requests");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new FriendRequestsUI(user, friendsService, userService, this));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void updateFriendsList() {
        List<Integer> friendIds = FriendService.getFriends(user.getUserId());
        StringBuilder sb = new StringBuilder();
        for (int friendId : friendIds) {
            // Fetch friend's name based on their ID
            User friend = UserService.getUserById(friendId);
            if (friend != null) {
                sb.append(friend.getUsername()).append("\n");
            }
        }
        friendsListArea.setText(sb.toString());
    }

    private int getUserIdByUsername(String username) {
        return userService.getUserByUsername(username).getUserId();
    }

}
