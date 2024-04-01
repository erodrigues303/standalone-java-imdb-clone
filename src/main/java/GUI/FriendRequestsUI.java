package GUI;

import GUI.Dashboard.FriendsPanel;
import Models.User;
import Services.FriendService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import Services.UserService;

public class FriendRequestsUI extends JPanel {
    private User user;
    private UserService userService;
    private FriendService friendsService;
    private JPanel friendRequestsPanel; // Store as a member for dynamic updates
    private FriendsPanel fp;

    public FriendRequestsUI(User user, FriendService friendsService, UserService userService, FriendsPanel fp) {
        this.user = user;
        this.friendsService = friendsService;
        this.userService = userService;
        this.fp = fp;

        setLayout(new BorderLayout());
        setupTitleLabel();
        setupFriendRequestsPanel();
        updateFriendRequestsList();
    }

    private void setupTitleLabel() {
        JLabel titleLabel = new JLabel("Friend Requests");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);
    }

    private void setupFriendRequestsPanel() {
        friendRequestsPanel = new JPanel();
        friendRequestsPanel.setLayout(new BoxLayout(friendRequestsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(friendRequestsPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void updateFriendRequestsList() {
        friendRequestsPanel.removeAll();
        List<Integer> friendRequests = friendsService.getReceivedFriendRequests(user.getUserId());
        for (Integer request : friendRequests) {
            String reqName = FriendService.getSenderUsername(request);
            JPanel requestPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel requestLabel = new JLabel(reqName + " sent you a friend request");
            JButton acceptButton = new JButton("Accept");
            JButton declineButton = new JButton("Decline");

            // Set the button name to the requester's username
            acceptButton.setName(reqName);
            declineButton.setName(reqName);

            acceptButton.addActionListener(this::accept);
            declineButton.addActionListener(this::decline);

            requestPanel.add(requestLabel);
            requestPanel.add(acceptButton);
            requestPanel.add(declineButton);
            friendRequestsPanel.add(requestPanel);
        }

        friendRequestsPanel.revalidate();
        friendRequestsPanel.repaint();
    }

    public void accept(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        String senderUsername = btn.getName(); // Use getName to retrieve the username

        User sender = userService.getUserByUsername(senderUsername);
        user.addFriend(sender);
        friendsService.addFriend(user.getUserId(), sender.getUserId());
        friendsService.addFriend(sender.getUserId(), user.getUserId());
        friendsService.removeFriendRequest(sender.getUserId(), user.getUserId());
        fp.updateFriendsList();

        updateFriendRequestsList();
    }

    public void decline(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        String senderUsername = btn.getName(); // Again, use getName

        User sender = userService.getUserByUsername(senderUsername);

        friendsService.removeFriendRequest(sender.getUserId(), user.getUserId());

        updateFriendRequestsList();
    }
}
