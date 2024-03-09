package GUI;

import javax.swing.*;
import Models.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

public class MovieUI extends JFrame {
    private static MovieUI instance;
    private final User user;
    private  Movie movie;

    private JLabel titleLabel;
    private JLabel releaseDateLabel;
    private JLabel ratingLabel;
    private JLabel coverImageLabel;
    private JLabel descriptionLabel;

    private JButton leaveReviewButton;
    private JButton rateButton;
    private JButton recommendButton;

    private MovieUI(Movie movie, User user) {
        this.user = user;
        this.movie = movie;

        setTitle("Movie Details: " + movie.getTitle());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 600);
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
                JOptionPane.showMessageDialog(MovieUI.this, "Leave Review button clicked");
            }
        });
        buttonPanel.add(leaveReviewButton);

        rateButton = new JButton("Rate");
        rateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to handle rating
                JOptionPane.showMessageDialog(MovieUI.this, "Rate button clicked");
            }
        });
        buttonPanel.add(rateButton);

        recommendButton = new JButton("Recommend to Friend");
        recommendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to handle recommendation
                JOptionPane.showMessageDialog(MovieUI.this, "Recommend to Friend button clicked");
            }
        });
        buttonPanel.add(recommendButton);
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
}
