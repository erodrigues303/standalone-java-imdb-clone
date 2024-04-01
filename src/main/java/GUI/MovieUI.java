package GUI;

import GUI.Reviews.LeaveReviewUI;
import GUI.Reviews.ReviewsUI;
import Models.Movie;
import Models.User;
import Services.FriendService;
import Services.RecommendToFriendService;
import Services.UserService;
import Utilities.MovieUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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

    private MovieUI(Movie movie, User user) {
        this.user = user;
        this.movie = movie;

        setTitle("Movie Details: " + movie.getTitle());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        initComponents();
        populateMovieDetails();
    }

    public static MovieUI getInstance(Movie movie, User user) {
        if (instance == null) {
            instance = new MovieUI(movie, user);
        }

        return instance;
    }

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

        recommendButton = createButton("Recommend to Friend", e -> showFriendWindow());
        buttonPanel.add(recommendButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    private void showFriendWindow() {
        if (friendWindow == null) {
            friendWindow = new JFrame("Friends: ");
            friendWindow.setSize(200, 500);
            friendWindow.setLocationRelativeTo(null);
        }

        JLabel titleLabel = new JLabel("Friends: ");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        friendWindow.add(titleLabel, BorderLayout.NORTH);

        friendsList = new JTextArea();
        friendsList.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(friendsList);
        friendWindow.add(scrollPane, BorderLayout.CENTER);

        friendWindow.setVisible(true);
        updateFriends(user);
    }

    public void updateFriends(User user) {
        ArrayList<Integer> friendIds = (ArrayList<Integer>) FriendService.getFriends(user.getUserId());
        refreshFriendWindow(friendIds);
    }

    private void refreshFriendWindow(ArrayList<Integer> friendIds) {
        // Ensure the friend window is initialized here (if not part of
        // showFriendWindow)

        JPanel friendPanel = new JPanel(new GridLayout(friendIds.size(), 1));

        for (int friendId : friendIds) {
            User friend = UserService.getUserById(friendId);
            if (friend != null) {
                JButton friendButton = new JButton(friend.getUsername());
                friendButton.addActionListener(
                        e -> RecommendToFriendService.sendRecommendation(user, friend.getUsername(), movie));
                friendPanel.add(friendButton);
            }
        }

        replaceFriendPanel(friendPanel);
    }

    private void replaceFriendPanel(JPanel newPanel) {
        friendWindow.getContentPane().removeAll();
        JScrollPane scrollPane = new JScrollPane(newPanel);
        friendWindow.getContentPane().add(scrollPane, BorderLayout.CENTER);
        friendWindow.revalidate();
        friendWindow.repaint();
    }

    private void populateMovieDetails() {
        titleLabel.setText("Title: " + movie.getTitle());
        releaseDateLabel.setText("Release Date: " + movie.getReleaseYear());
        ratingLabel.setText("Rating: " + movie.getRating());
        MovieUtils.loadImage(movie.getCoverImageUrl(), coverImageLabel);
        descriptionLabel.setText("<html>Description: " + movie.getDescription() + "</html>");
        genreLabel.setText(movie.getGenre());
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
        ReviewsUI reviewUI = new ReviewsUI(movie, user);
        reviewUI.setVisible(true);
    }
}
