package GUI;

import GUI.Dashboard.CenterPanel;
import GUI.Reviews.LeaveReviewUI;
import GUI.Reviews.ReviewsUI;
import Models.Movie;
import Models.Review;
import Models.User;
import Services.FriendService;
import Services.RecommendToFriendService;
import Services.UserService;
import Utilities.MovieUtils;
import Utilities.RefreshRecommendationsCallback;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;
import Services.ReviewService;

import java.util.ArrayList;

public class MovieUI extends JFrame {
    private static MovieUI instance;
    private final User user;
    private Movie movie;

    private JTextArea friendsList;
    private JLabel titleLabel;
    private JLabel releaseDateLabel;
    private JLabel ratingLabel;
    private JLabel coverImageLabel;
    private JLabel descriptionLabel;
    private JLabel genreLabel;

    private JFrame friendWindow;

    private JButton leaveReviewButton;
    private JButton rateButton;
    private JButton recommendButton;
    int count = 0;

    public MovieUI(Movie movie, User user) {
        this.user = user;
        this.movie = movie;

        setTitle("Movie Details: " + movie.getTitle());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        initComponents();
        populateMovieDetails();
    }

    // public static MovieUI getInstance(Movie movie, User user) {
    // if (instance == null) {
    // instance = new MovieUI(movie, user);
    // }

    // return instance;
    // }

    private void initComponents() {
        configureMainPanel();
        initializeMovieDetailsPanel();
        initializeButtonPanel();
    }

    private void configureMainPanel() {
        setLayout(new BorderLayout());
        setSize(500, 600);
        setLocationRelativeTo(null);
    }

    private void initializeMovieDetailsPanel() {
        JPanel movieDetailsPanel = new JPanel();
        movieDetailsPanel.setLayout(new BoxLayout(movieDetailsPanel, BoxLayout.Y_AXIS));

        titleLabel = new JLabel();
        movieDetailsPanel.add(titleLabel);

        releaseDateLabel = new JLabel();
        movieDetailsPanel.add(releaseDateLabel);

        ratingLabel = new JLabel();
        movieDetailsPanel.add(ratingLabel);

        coverImageLabel = new JLabel();
        movieDetailsPanel.add(coverImageLabel);

        genreLabel = new JLabel();
        movieDetailsPanel.add(genreLabel);

        descriptionLabel = new JLabel();
        movieDetailsPanel.add(descriptionLabel);

        add(movieDetailsPanel, BorderLayout.CENTER);
    }

    private void initializeButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        leaveReviewButton = createButton("Leave Review", e -> openLeaveReviewUI(user, movie));
        buttonPanel.add(leaveReviewButton);

        rateButton = createButton("Read Reviews", e -> openReviewsUI(movie, user));
        buttonPanel.add(rateButton);

        recommendButton = createButton("Recommend to Friend", e -> openFriendsList());
        buttonPanel.add(recommendButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    private void openFriendsList() {
        java.util.List<Integer> friendIDs = FriendService.getFriends(user.getUserId());

        // Create a modal JDialog
        JDialog friendsDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Choose Friend", true);

        // Check if the friends list is empty
        if (friendIDs.isEmpty()) {
            JOptionPane.showMessageDialog(friendsDialog,
                    "Friends list is empty. Please add friends to recommend them movies.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a panel with GridLayout to hold the buttons
        JPanel panel = new JPanel(new GridLayout(friendIDs.size(), 1, 5, 5)); // with gaps
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // some padding

        // Populate the panel with friend buttons
        for (Integer friendID : friendIDs) {
            User friend = UserService.getUserById(friendID);
            if (friend != null) {
                JButton friendButton = new JButton(friend.getUsername());
                friendButton.addActionListener(e -> {
                    // Call the method to handle the recommendation
                    RecommendToFriendService.sendRecommendation(user, friend.getUsername(), movie);
                    JOptionPane.showMessageDialog(friendsDialog,
                            "Recommendation sent to " + friend.getUsername(), "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    friendsDialog.dispose(); // Close the dialog
                });
                panel.add(friendButton); // Add the button to the panel
            }
        }

        // Add a scroll pane in case the number of friends exceeds the dialog size
        JScrollPane scrollPane = new JScrollPane(panel);
        friendsDialog.setContentPane(scrollPane);

        // Set dialog size and location
        friendsDialog.pack(); // Pack the dialog to its contents' preferred size
        friendsDialog.setLocationRelativeTo(SwingUtilities.getWindowAncestor(this));
        friendsDialog.setVisible(true); // Show the dialog
    }

    private void populateMovieDetails() {
        titleLabel.setText("Title: " + movie.getTitle());
        releaseDateLabel.setText("Release Date: " + movie.getReleaseYear());
        ratingLabel.setText("Rating: " + movie.getRating());
        MovieUtils.loadImage(movie.getCoverImageUrl(), coverImageLabel);
        descriptionLabel.setText("<html>Description: " + movie.getDescription() + "</html>");
        genreLabel.setText(movie.getGenre());
        if (count++ % 4 == 0) {
            CenterPanel.getInstance().refreshRecommendations();
        }
    }

    public void updateMovieDetails(Movie movie) {
        this.movie = movie;
        setTitle("Movie Details: " + movie.getTitle());
        populateMovieDetails();
    }

    public void openLeaveReviewUI(User user, Movie movie) {
        LeaveReviewUI reviewUI = new LeaveReviewUI(user, movie);
        reviewUI.setVisible(true);
    }

    public void openReviewsUI(Movie movie, User user) {
        ReviewService reviewService = new ReviewService();
        List<Review> reviews = reviewService.getReviewsByMovieId(movie.getMovieId());
        if (reviews.isEmpty()) {
            JOptionPane.showMessageDialog(friendWindow,
                    "This movie has no reviews yet. Be the first to leave one!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        ReviewsUI reviewUI = new ReviewsUI(movie, user);
        reviewUI.setVisible(true);
    }
}
