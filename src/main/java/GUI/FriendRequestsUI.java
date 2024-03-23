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
    private FriendsPanel friendsPanel;
    private UserService userService;
    private FriendService friendsService;



    public FriendRequestsUI(User user) {
        this.user = user;
        friendsPanel= new FriendsPanel(user);
        friendsService= new FriendService();
        userService= new UserService();

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Friend Requests");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        JPanel friendRequestsPanel = new JPanel();
        friendRequestsPanel.setLayout(new BoxLayout(friendRequestsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(friendRequestsPanel);
        add(scrollPane, BorderLayout.CENTER);

        updateFriendRequestsList(friendRequestsPanel);

    }

    private void updateFriendRequestsList(JPanel friendRequestsPanel) {
        List<Integer> friendRequests = FriendService.getReceivedFriendRequests(user.getUserId());
        for (Integer request : friendRequests) {
            JPanel requestPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel requestLabel = new JLabel(FriendService.getSenderUsername(request) + " sent you a friend request");
            JButton acceptButton = new JButton("Accept");
            JButton declineButton = new JButton("Decline");

            acceptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int senderID = FriendService.getSenderID(request); // Get sender's ID based on request ID
                    if (senderID != -1) { // Check if sender's ID is valid
                        boolean added = FriendService.acceptFriendRequest(request); // Add friend
                        if (added) {
                            // Successfully added friend, you can perform further actions if needed
                            JOptionPane.showMessageDialog(null, "Friend added successfully.");
                            FriendService.addFriend(user.getUserId(),senderID);
                            FriendService.addFriend(senderID,user.getUserId());
                            updateFriendRequestsList(friendRequestsPanel);
                            friendsPanel.updateFriendsList();
                        } else {
                            // Failed to add friend, handle accordingly
                            JOptionPane.showMessageDialog(null, "Failed to add friend.");
                        }
                    } else {
                        // Sender's ID not found or invalid, handle accordingly
                        JOptionPane.showMessageDialog(null, "Failed to retrieve sender's information.");
                    }
                }
            });

            declineButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int senderID= FriendService.getSenderID(request);
                    if(senderID != -1){
                        boolean declined= FriendService.declineFriendRequest(request);
                        if(declined){
                        JOptionPane.showMessageDialog(null,"Friend request declined.");
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Could not decline friend request.");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Could not find senders ID.");
                    }
                }
            });

            requestPanel.add(requestLabel);
            requestPanel.add(acceptButton);
            requestPanel.add(declineButton);
            friendRequestsPanel.add(requestPanel);
        }
    }
}


