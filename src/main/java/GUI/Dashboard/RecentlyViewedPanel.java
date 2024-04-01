package GUI.Dashboard;

import GUI.MovieUI;
import Models.Movie;
import Models.User;
import Services.FriendService;
import Services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RecentlyViewedPanel extends JPanel {
    private final JPanel recentlyViewedMoviesPanel;
    private final User user;
    private UserService userService=new UserService();

    public RecentlyViewedPanel(User user) {
        this.user = user;
        setLayout(new BorderLayout());
        add(new JLabel("Recently Viewed Movies:", SwingConstants.CENTER), BorderLayout.NORTH);
        recentlyViewedMoviesPanel = new JPanel();
        recentlyViewedMoviesPanel.setLayout(new BoxLayout(recentlyViewedMoviesPanel, BoxLayout.PAGE_AXIS));
        add(new JScrollPane(recentlyViewedMoviesPanel), BorderLayout.CENTER);
        populateRecentlyViewedMovies();

        // Add button to view friends' recently viewed movies
        JButton viewFriendsRecentlyViewedButton = new JButton("View Friends' Recently Viewed");
        viewFriendsRecentlyViewedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFriendsList();
            }
        });
        add(viewFriendsRecentlyViewedButton, BorderLayout.SOUTH);
    }

    public void populateRecentlyViewedMovies() {
        recentlyViewedMoviesPanel.removeAll(); // Clear existing content
        for (Movie movie : user.getRecentlyViewed()) {
            JButton movieButton = new JButton(movie.getTitle());
            movieButton.addActionListener(e -> {
                MovieUtils.openMovieUI(movie, user);
            });
            recentlyViewedMoviesPanel.add(movieButton);
        }
        recentlyViewedMoviesPanel.revalidate();
        recentlyViewedMoviesPanel.repaint();
    }

    private void openFriendsList() {
        java.util.List<Integer> friendIDs = FriendService.getFriends(user.getUserId());
        JDialog friendsDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Choose Friend", true);
        friendsDialog.setLayout(new GridLayout(friendIDs.size(), 1)); // Adjust the layout

        for (Integer friendID : friendIDs) {
            JButton friendButton = new JButton(UserService.getUserById(friendID).getUsername());
            friendButton.addActionListener(e -> {
                // Handle button click (recommendation logic or any other action)
                // Display friend's recently viewed movies
                displayFriendRecentlyViewedMovies(UserService.getUserById(friendID).getUsername(),user);
                // Close the dialog after handling the action
                friendsDialog.dispose();
            });
            // Add friendButton to the dialog
            friendsDialog.add(friendButton);
        }

        // Set the size of the dialog based on the number of friends
        friendsDialog.setSize(300, friendIDs.size() * 50);
        // Set dialog location relative to the main frame
        friendsDialog.setLocationRelativeTo(this);
        // Make the dialog visible
        friendsDialog.setVisible(true);
    }

    private void displayFriendRecentlyViewedMovies(String friendUsername,User user) {
        List<Movie> friendRecentlyViewedMovies = userService.getUserByUsername(friendUsername).getRecentlyViewed();

        JPanel movieCardsPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // Change to single column layout

        for (Movie movie : friendRecentlyViewedMovies) {
            JPanel movieCardPanel = new JPanel(new BorderLayout());

            // Create movie card object using the existing method
            JPanel movieCard = MovieUtils.createMovieCard(movie,user);

            // Add movie card to the movie card panel
            movieCardPanel.add(movieCard, BorderLayout.NORTH);

            // Add movie card panel to the main panel
            movieCardsPanel.add(movieCardPanel);
        }

        JFrame friendRecentlyViewedFrame = new JFrame("Recently Viewed Movies by " + friendUsername);
        friendRecentlyViewedFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        friendRecentlyViewedFrame.add(new JScrollPane(movieCardsPanel));
        friendRecentlyViewedFrame.pack();
        friendRecentlyViewedFrame.setLocationRelativeTo(this);
        friendRecentlyViewedFrame.setVisible(true);
    }
}
