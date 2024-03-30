package GUI.Dashboard;

import GUI.MovieUI;
import Models.Movie;
import Models.User;
import Services.MovieService;
import Services.RecommendationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CenterPanel extends JPanel {
    private final RecommendationService recommendationService;
    private final User user;
    private final Dashboard dashboard;

    public CenterPanel(RecommendationService recommendationService, User user, Dashboard dashboard) {
        this.recommendationService = recommendationService;
        this.user = user;
        this.dashboard = dashboard;
        setLayout(new GridLayout(2, 3, 10, 10)); // 2 rows, 3 cols, horizontal and vertical gaps
        setupCenterPanel();
    }

    private void setupCenterPanel() {
        if (user.getRecentlyViewed().isEmpty()) {
            setLayout(new FlowLayout(FlowLayout.CENTER, 50, 200)); // Small horizontal gap, no vertical gap
            JLabel welcomeLabel = new JLabel(
                    "Search movies to start getting recommendations, " + user.getUsername() + "!");
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
            add(welcomeLabel);
        } else {
            System.out.println(user.getRecentlyViewed());
            List<Movie> recommendedMovies = recommendationService.getRecommendations(user);

            for (Movie movie : recommendedMovies) {
                JPanel card = MovieUtils.createMovieCard(movie, user);
                add(card);
            }
        }
    }

    public void refreshRecommendations() {
        removeAll(); // Remove all components from the panel
        setupCenterPanel(); // Set up the panel with new recommendations
        revalidate(); // Revalidate the panel
        repaint(); // Repaint the panel
    }

}
