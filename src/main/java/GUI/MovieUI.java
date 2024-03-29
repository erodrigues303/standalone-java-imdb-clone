package GUI;

import Models.Movie;
import Models.User;
import Services.DbFunctions;
import Services.FriendService;
import Services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class MovieUI extends JFrame {
    private static MovieUI instance;
    private final User user;
    private  Movie movie;

    private JTextArea friendsList;
    private JLabel titleLabel;
    private JLabel releaseDateLabel;
    private JLabel ratingLabel;
    private JLabel coverImageLabel;
    private JLabel descriptionLabel;

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
        setLayout(new BorderLayout());

        JPanel movieDetailsPanel = new JPanel();
        movieDetailsPanel.setLayout(new BoxLayout(movieDetailsPanel, BoxLayout.Y_AXIS));
        add(movieDetailsPanel, BorderLayout.CENTER);

        titleLabel = new JLabel();
        movieDetailsPanel.add(titleLabel);

        releaseDateLabel = new JLabel();
        movieDetailsPanel.add(releaseDateLabel);

        ratingLabel = new JLabel();
        movieDetailsPanel.add(ratingLabel);

        coverImageLabel = new JLabel();
        movieDetailsPanel.add(coverImageLabel);

        descriptionLabel = new JLabel();
        movieDetailsPanel.add(descriptionLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add(buttonPanel, BorderLayout.SOUTH);

        leaveReviewButton = new JButton("Leave Review");
        leaveReviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to handle leaving review
                openLeaveReviewUI(user, movie);            }
        });
        buttonPanel.add(leaveReviewButton);

        rateButton = new JButton("Read Reviews");
        rateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to handle rating
                openReviewsUI(movie);            }
        });
        buttonPanel.add(rateButton);

        recommendButton = new JButton("Recommend to Friend");
        recommendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to handle recommendation
                //JOptionPane.showMessageDialog(MovieUI.this, "Recommend to Friend button clicked");
                friendWindow = new JFrame("Friends: ");
                friendWindow.setSize(200,500);
                JLabel titleLabel = new JLabel("Friends: ");
                titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
                friendWindow.add(titleLabel,BorderLayout.NORTH);
                friendsList = new JTextArea();
                friendsList.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(friendsList);
                friendWindow.add(scrollPane, BorderLayout.CENTER);
                updateFriends();


                friendWindow.setVisible(true);
            }

        });
        buttonPanel.add(recommendButton);
    }
    private void updateFriends() {
        ArrayList<Integer> friendIds = (ArrayList<Integer>) FriendService.getFriends(user.getUserId());

        // Clear previous content
        friendWindow.getContentPane().removeAll();

        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new GridLayout(friendIds.size(), 1));

        for (int friendId : friendIds) {
            // Fetch friend's name based on their ID
            User friend = UserService.getUserById(friendId);
            if (friend != null) {
                JButton friendButton = new JButton(friend.getUsername());
                friendButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle button click (recommendation logic or any other action)
                        String sql = "INSERT INTO Movie_recommendation (user_name, friend_name, movie_name) VALUES (?, ?, ?)";

                        try (Connection conn = DbFunctions.connect();
                             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            pstmt.setString(1, user.getUsername());
                            pstmt.setString(2, friendButton.getText());
                            pstmt.setString(3, movie.getTitle());

                            // Execute the SQL statement
                            pstmt.executeUpdate();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                friendPanel.add(friendButton);
            }
        }

        JScrollPane scrollPane = new JScrollPane(friendPanel);
        friendWindow.getContentPane().add(scrollPane, BorderLayout.CENTER);
        friendWindow.revalidate(); // Revalidate the window
        friendWindow.repaint(); // Repaint the window
    }

    private void populateMovieDetails() {
        titleLabel.setText("Title: " + movie.getTitle());
        releaseDateLabel.setText("Release Date: " + movie.getReleaseYear());
        ratingLabel.setText("Rating: " + movie.getRating());
        loadImage(movie.getCoverImageUrl(), coverImageLabel);
        descriptionLabel.setText("<html>Description: " + movie.getDescription() + "</html>");
    }

    private void loadImage(String imageUrl, JLabel imageLabel) {
        try {
            URL url = new URL(imageUrl);
            ImageIcon icon = new ImageIcon(url);
            Image scaledImage = icon.getImage().getScaledInstance(150, -1, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImage);
            imageLabel.setIcon(icon);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void updateMovieDetails(Movie movie) {
        this.movie = movie;
        setTitle("Movie Details: " + movie.getTitle());
        populateMovieDetails();
    }
    public void openLeaveReviewUI (User user, Movie movie){
        LeaveReviewUI reviewUI = new LeaveReviewUI(user, movie);
        reviewUI.setVisible(true);
    }
    public void openReviewsUI (Movie movie){
        ReviewsUI reviewUI = new ReviewsUI(movie);
        reviewUI.setVisible(true);
    }
}

