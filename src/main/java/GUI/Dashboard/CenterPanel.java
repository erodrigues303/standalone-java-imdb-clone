package GUI.Dashboard;

import Models.Movie;
import Models.User;
import Services.RecommendationService;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CenterPanel extends JPanel {
    private static CenterPanel instance;
    private final RecommendationService recommendationService;
    private final User user;
    private final Dashboard dashboard;

    public CenterPanel(RecommendationService recommendationService, User user, Dashboard dashboard) {
        this.recommendationService = recommendationService;
        this.user = user;
        this.dashboard = dashboard;
        setLayout(new GridLayout(2, 3, 10, 10)); // 2 rows, 3 cols, horizontal and vertical gaps
        setupCenterPanel();
        instance = this;
    }

    public static CenterPanel getInstance() {
        return instance;
    }

    private void setupCenterPanel() {
        List<Movie> recommendedMovies;
        if (user.getRecentlyViewed().isEmpty()) {
            recommendedMovies = recommendationService.getDefaultRecommendations();
        } else {
            recommendedMovies = recommendationService.getRecommendations(user);
        }
        for (Movie movie : recommendedMovies) {
            JPanel card = Utilities.MovieUtils.createMovieCard(movie, user);
            add(card);
        }

    }

    public void refreshRecommendations() {
        removeAll(); // Remove all components from the panel
        setupCenterPanel(); // Set up the panel with new recommendations
        revalidate(); // Revalidate the panel
        repaint(); // Repaint the panel
    }

}
