package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
//import Services.MovieManager;

import Models.*;
import Services.MovieService;
import org.json.JSONException;

public class DashboardUI extends JFrame{


    private final User user;

    private final JPanel contentPanel;
    private JPanel recentlyViewedMoviesPanel;
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

        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(searchPanel, BorderLayout.CENTER);
        contentPanel.add(recentlyViewedPanel, BorderLayout.WEST);
        contentPanel.add(friendsPanel, BorderLayout.EAST);

        add(contentPanel);
        pack();
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

    public void openMovieUI(Movie movie) {
        MovieUI movieUI = MovieUI.getInstance(movie, user);
        movieUI.updateMovieDetails(movie);
        movieUI.setVisible(true);
    }
}
