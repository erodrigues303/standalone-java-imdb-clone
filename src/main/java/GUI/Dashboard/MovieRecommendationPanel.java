package GUI.Dashboard;

import Models.Movie;
import Models.User;
import Services.MovieRecommendationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static GUI.Dashboard.MovieUtils.createMovieCard;

public class MovieRecommendationPanel extends JPanel {
    private User user;
    private String userName;
    private JTextArea moviesList;
    private JFrame friendWindow;



    public MovieRecommendationPanel(User user) {
        this.user = user;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());

        JButton recommendationButton = new JButton("Recommended Movies");
        recommendationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayRecommendedMovies();
            }
        });
        recommendationButton.setPreferredSize(new Dimension(249, 30));

        add(recommendationButton, BorderLayout.EAST);
    }

    private void displayRecommendedMovies() {
        List<Movie> recommendedMovies = MovieRecommendationService.getRecommendedMovies(user.getUsername());

        JPanel movieCardsPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        for (Movie movie : recommendedMovies) {
            JPanel movieCard = MovieUtils.createMovieCard(movie, user);
            movieCardsPanel.add(movieCard);
        }

        JFrame movieListFrame = new JFrame("Recommended Movies");
        movieListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        movieListFrame.add(movieCardsPanel);
        movieListFrame.pack();
        movieListFrame.setLocationRelativeTo(null);
        movieListFrame.setVisible(true);
    }

    // Method to update the current user
    public void updateUser(User user) {
        this.user = user;
        // Update friends window
        updateFriends();
    }

    private void updateFriends() {
        friendWindow.getContentPane().removeAll();
    }
}