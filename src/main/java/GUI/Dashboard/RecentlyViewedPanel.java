package GUI.Dashboard;

import GUI.MovieUI;
import Models.Movie;
import Models.User;

import javax.swing.*;
import java.awt.*;

public class RecentlyViewedPanel extends JPanel {
    private final JPanel recentlyViewedMoviesPanel;
    private final User user;

    public RecentlyViewedPanel(User user) {
        this.user = user;
        setLayout(new BorderLayout());
        add(new JLabel("Recently Viewed Movies:", SwingConstants.CENTER), BorderLayout.NORTH);
        recentlyViewedMoviesPanel = new JPanel();
        recentlyViewedMoviesPanel.setLayout(new BoxLayout(recentlyViewedMoviesPanel, BoxLayout.PAGE_AXIS));
        add(new JScrollPane(recentlyViewedMoviesPanel), BorderLayout.CENTER);
        populateRecentlyViewedMovies();
    }

    public void populateRecentlyViewedMovies() {
        recentlyViewedMoviesPanel.removeAll(); // Clear existing content
        for (Movie movie : user.getRecentlyViewed()) {
            JButton movieButton = new JButton(movie.getTitle());
            movieButton.addActionListener(e -> {
                openMovieUI(movie);
            });
            recentlyViewedMoviesPanel.add(movieButton);
        }
        recentlyViewedMoviesPanel.revalidate();
        recentlyViewedMoviesPanel.repaint();
    }

    public void openMovieUI(Movie movie) {
        MovieUI movieUI = MovieUI.getInstance(movie, user);
        movieUI.updateMovieDetails(movie);
        movieUI.setVisible(true);
    }
}
