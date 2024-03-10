package GUI.Dashboard;

import GUI.MovieUI;
import Models.Movie;
import Models.User;
import Services.MovieService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CenterPanel extends JPanel {
    private final MovieService movieService;
    private final User user;
    private final Dashboard dashboard;

    public CenterPanel(MovieService movieService, User user, Dashboard dashboard) {
        this.movieService = movieService;
        this.user = user;
        this.dashboard = dashboard;
        setLayout(new GridLayout(2, 3, 10, 10)); // 2 rows, 3 cols, horizontal and vertical gaps
        setupCenterPanel();
    }

    private void setupCenterPanel() {
        List<Movie> recommendedMovies = movieService.getRecommendations(user); // Replace with actual recommendation fetching method

        for (Movie movie : recommendedMovies) {
            JPanel card = MovieUtils.createMovieCard(movie, user);
            add(card);
        }

    }

    public void refreshRecommendations() {
        removeAll(); // Remove all components from the panel
        setupCenterPanel(); // Set up the panel with new recommendations
        revalidate(); // Revalidate the panel
        repaint();    // Repaint the panel
    }


}
