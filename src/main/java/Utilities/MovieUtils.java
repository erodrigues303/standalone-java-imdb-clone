package Utilities;

import GUI.MovieUI;
import Models.Movie;
import Models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

public class MovieUtils {

    // Static method for loading images
    public static void loadImage(String imageUrl, JLabel imageLabel) {
        SwingUtilities.invokeLater(() -> {
            try {
                URL url = new URL(imageUrl);
                ImageIcon icon = new ImageIcon(
                        new ImageIcon(url).getImage().getScaledInstance(150, 225, Image.SCALE_SMOOTH));
                imageLabel.setIcon(icon);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                imageLabel.setText("Image not available");
            }
        });
    }

    // Static method to create and open MovieUI
    public static void openMovieUI(Movie movie, User user) {
        MovieUI movieUI = MovieUI.getInstance(movie, user);
        movieUI.updateMovieDetails(movie);
        movieUI.setVisible(true);
        user.addRecentlyViewed(movie);
        // If Dashboard is a singleton or has a static way to access the current
        // instance, you can update it here
        GUI.Dashboard.Dashboard.getInstance().getRecentlyViewedPanel().populateRecentlyViewedMovies();
    }

    public static JPanel createMovieCard(Movie movie, User user) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add spacing between movie entries
        cardPanel.setPreferredSize(new Dimension(200, 300)); // Set preferred size for each movie panel

        // Title with year and rating
        String titleText = String.format(
                "<html><b><u>%s</u></b> (%d) <font color='red'><br>%s/10 &#9733;</font></html>",
                movie.getTitle(), movie.getReleaseYear(), movie.getRating());
        JLabel titleLabel = new JLabel(titleText, SwingConstants.CENTER);
        titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openMovieUI(movie, user);
            }
        });
        cardPanel.add(titleLabel, BorderLayout.NORTH);

        // Cover image
        JLabel coverImageLabel = new JLabel();
        coverImageLabel.setPreferredSize(new Dimension(150, 225));
        loadImage(movie.getCoverImageUrl(), coverImageLabel);
        coverImageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        coverImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openMovieUI(movie, user); // Open MovieUI when image is clicked
            }
        });
        cardPanel.add(coverImageLabel, BorderLayout.CENTER);

        return cardPanel;
    }
}
