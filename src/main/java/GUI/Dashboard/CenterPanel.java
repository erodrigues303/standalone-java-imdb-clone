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
            if (getComponentCount() >= 6) break; // Only add up to 6 movies
            JPanel card = createMovieCard(movie);
            add(card);
        }

        // Add empty panels for remaining slots if necessary
        for (int i = getComponentCount(); i < 6; i++) {
            add(new JPanel());
        }
    }


    private JPanel createMovieCard(Movie movie) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add spacing between movie entries
        cardPanel.setPreferredSize(new Dimension(200, 300)); // Set preferred size for each movie panel

        // Title with year and rating
        String titleText = "<html><b><u>" + movie.getTitle() + "</u></b> (" + movie.getReleaseYear() + ") " +
                "<font color='red'><br>" + movie.getRating() + "/10 &#9733;</font></html>";
        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setHorizontalAlignment(JLabel.CENTER); // Center align the title
        titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openMovieUI(movie);
                refreshRecommendations();
            }
        });
        cardPanel.add(titleLabel, BorderLayout.NORTH);

        // Cover image
        JLabel coverImageLabel = new JLabel();
        coverImageLabel.setPreferredSize(new Dimension(150, 225)); // Set preferred size for cover image
        loadImage(movie.getCoverImageUrl(), coverImageLabel); // Load the actual image
        coverImageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand
        coverImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openMovieUI(movie); // Open MovieUI when image is clicked
            }
        });
        cardPanel.add(coverImageLabel, BorderLayout.CENTER);

        return cardPanel;
    }

    private void loadImage(String imageUrl, JLabel imageLabel) {
        SwingUtilities.invokeLater(() -> {
            try {
                URL url = new URL(imageUrl);
                ImageIcon icon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH));
                imageLabel.setIcon(icon);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                imageLabel.setText("Image not available");
            }
        });
    }



    public void openMovieUI(Movie movie) {
        MovieUI movieUI = MovieUI.getInstance(movie, user);
        movieUI.updateMovieDetails(movie);
        movieUI.setVisible(true);
        this.user.addRecentlyViewed(movie);
        this.dashboard.getRecentlyViewedPanel().populateRecentlyViewedMovies();
    }

    public void refreshRecommendations() {
        removeAll(); // Remove all components from the panel
        setupCenterPanel(); // Set up the panel with new recommendations
        revalidate(); // Revalidate the panel
        repaint();    // Repaint the panel
    }


}
