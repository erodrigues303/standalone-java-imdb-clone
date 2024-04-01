package GUI.Dashboard;

import Models.Movie;
import Models.User;
import Services.RecommendToFriendService;

import javax.swing.*;
// import javax.swing.text.Utils;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MovieRecommendationPanel extends JPanel {
    private User user;

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
        List<Movie> recommendedMovies = RecommendToFriendService.getRecommendedMovies(user);

        JPanel movieCardsPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // Change to single column layout

        for (Movie movie : recommendedMovies) {
            JPanel movieCardPanel = new JPanel(new BorderLayout());

            // Create movie card object using the existing method
            JPanel card = Utilities.MovieUtils.createMovieCard(movie, user);

            // Add movie card to the movie card panel
            movieCardPanel.add(card, BorderLayout.NORTH);

            // Fetch recommenders for the current movie
            List<String> recommenders = RecommendToFriendService.getRecommendationsByMovie(movie.getTitle());

            // Create and add recommended by label
            JLabel recommendedByLabel = new JLabel("Recommended by: " + String.join(",", recommenders));
            movieCardPanel.add(recommendedByLabel, BorderLayout.CENTER);

            // Add movie card panel to the main panel
            movieCardsPanel.add(movieCardPanel);
        }

        // Create a scroll pane and add the movie cards panel to it
        JScrollPane scrollPane = new JScrollPane(movieCardsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JFrame movieListFrame = new JFrame("Recommended Movies");
        movieListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        movieListFrame.add(scrollPane); // Add the scroll pane instead of the movie cards panel
        movieListFrame.pack();
        movieListFrame.setLocationRelativeTo(null);
        movieListFrame.setVisible(true);
    }
}