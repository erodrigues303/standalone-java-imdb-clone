package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
//import Services.MovieManager;

import Models.*;
import Services.MovieService;
import org.json.JSONException;

public class DashboardUI extends JFrame{


    private final User user;

    private  JPanel contentPanel;
    private JPanel recentlyViewedMoviesPanel;
    private JPanel centerPanel;
    private MovieService movieService = new MovieService();

    public DashboardUI(User user) {
        this.user = user;

        setTitle("Dashboard");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        contentPanel = new JPanel(new BorderLayout());
        JPanel headerPanel = createHeaderPanel();
        JPanel searchPanel = createSearchPanel();
        JPanel recentlyViewedPanel = createRecentlyViewedPanel();
        JPanel friendsPanel = createFriendsPanel();

        // This new panel will contain the recently viewed, center, and friends panels.
        JPanel centralPanel = new JPanel(new BorderLayout());

        // Set up the center panel which will contain the recommended movies
        centerPanel = new JPanel(new GridLayout(2, 3, 10, 10)); // 2 rows, 3 columns, and gaps
        setupCenterPanel();

        // Add the recently viewed and friends panels to the central panel
        centralPanel.add(recentlyViewedPanel, BorderLayout.WEST);
        centralPanel.add(centerPanel, BorderLayout.CENTER);
        centralPanel.add(friendsPanel, BorderLayout.EAST);

        // Add the header and search panels to the top of the content panel
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(searchPanel, BorderLayout.CENTER);

        // Add the central panel to the content panel, it will use the remaining space
        contentPanel.add(centralPanel, BorderLayout.SOUTH);

        // Add the content panel to the frame
        add(contentPanel);

        // Pack the components within the frame
        pack();

        // Make the window visible
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel welcomeLabel = new JLabel("Welcome to the Dashboard, " + user.getUsername() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(welcomeLabel);
        return headerPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                searchMovies(searchText);
            }
        });
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        return searchPanel;
    }

    private void searchMovies(String searchText) {
        List<Movie> searchResults = movieService.getMovieByName(searchText);
        BrowseMovieUI browseMovieUI = new BrowseMovieUI(this.user, this);
        browseMovieUI.displaySearchResults(searchResults);
    }

    private JPanel createRecentlyViewedPanel() {
        JPanel recentlyViewedPanel = new JPanel(new BorderLayout());
        JLabel recentlyViewedLabel = new JLabel("Recently Viewed Movies:");
        recentlyViewedLabel.setFont(new Font("Arial", Font.BOLD, 16));
        recentlyViewedPanel.add(recentlyViewedLabel, BorderLayout.NORTH);

        // Changed FlowLayout to BoxLayout that aligns components top to bottom
        recentlyViewedMoviesPanel = new JPanel();
        recentlyViewedMoviesPanel.setLayout(new BoxLayout(recentlyViewedMoviesPanel, BoxLayout.PAGE_AXIS));
        populateRecentlyViewedMovies(); // Populate it using a separate method
        recentlyViewedPanel.add(new JScrollPane(recentlyViewedMoviesPanel), BorderLayout.CENTER); // ScrollPane added to handle overflow
        return recentlyViewedPanel;
    }

    public void populateRecentlyViewedMovies() {
        recentlyViewedMoviesPanel.removeAll(); // Clear existing content
        for (Movie movie : user.getRecentlyViewed()) {
            JButton movieButton = new JButton(movie.getTitle());
            movieButton.addActionListener(e ->
                    openMovieUI(movie));
            recentlyViewedMoviesPanel.add(movieButton);
        }
        recentlyViewedMoviesPanel.revalidate();
        recentlyViewedMoviesPanel.repaint();
    }

    private JPanel createFriendsPanel() {
        JPanel friendsPanel = new JPanel(new BorderLayout());
        JLabel friendsLabel = new JLabel("Friends:");
        friendsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        friendsPanel.add(friendsLabel, BorderLayout.NORTH);
        JPanel friendsListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (User friend : user.getFriendsList()) {
            JButton friendButton = new JButton(friend.getUsername());
            friendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(DashboardUI.this, "You clicked on " + friend.getUsername());
                }
            });
            friendsListPanel.add(friendButton);
        }
        friendsPanel.add(friendsListPanel, BorderLayout.CENTER);
        return friendsPanel;
    }

    private void setupCenterPanel() {
        centerPanel = new JPanel(new GridLayout(2, 3, 10, 10)); // 2 rows, 3 cols, horizontal and vertical gaps

        // Dummy list of recommended movies
        List<Movie> recommendedMovies = movieService.getRecommendations(user); // Replace with actual recommendation fetching method

        for (Movie movie : recommendedMovies) {
            if (centerPanel.getComponentCount() >= 6) break; // Only add up to 6 movies
            JPanel card = createMovieCard(movie);
            centerPanel.add(card);
        }

        // Add empty panels for remaining slots if necessary
        for (int i = centerPanel.getComponentCount(); i < 6; i++) {
            centerPanel.add(new JPanel());
        }
    }

    private JPanel createMovieCard(Movie movie) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.PAGE_AXIS));

        JLabel movieTitleLabel = new JLabel(movie.getTitle());
        movieTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(movieTitleLabel);

        // Add image loading and resizing logic here
        JLabel movieImageLabel = new JLabel();
        movieImageLabel.setPreferredSize(new Dimension(100, 150)); // Set preferred size for cover image
        loadImage(movie.getCoverImageUrl(), movieImageLabel); // Replace with actual image
        movieImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(movieImageLabel);

        return cardPanel;
    }

    public void openMovieUI(Movie movie) {
        MovieUI movieUI = MovieUI.getInstance(movie, user);
        movieUI.updateMovieDetails(movie);
        movieUI.setVisible(true);
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
}
