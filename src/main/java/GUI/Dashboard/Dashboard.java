package GUI.Dashboard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import GUI.BrowseMovieUI;
import Models.Movie;
import Models.User;
import Services.MovieService;
import Services.RecommendationService;

public class Dashboard extends JFrame {
    private static Dashboard instance;
    private User user;
    private MovieService movieService = new MovieService();
    private RecommendationService recommendationService = new RecommendationService();
    private HeaderPanel headerPanel;
    private SearchPanel searchPanel;
    private RecentlyViewedPanel recentlyViewedPanel;
    private FriendsPanel friendsPanel;
    private MovieRecommendationPanel mPanel;
    private CenterPanel centerPanel;

    public Dashboard(User user) {
        this.user = user;
        setTitle("Dashboard");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Initialize the panels
        headerPanel = new HeaderPanel(user);
        searchPanel = new SearchPanel();
        recentlyViewedPanel = new RecentlyViewedPanel(user);
        CenterPanel centerPanel;
        centerPanel = new CenterPanel(recommendationService, user, this);
        friendsPanel = new FriendsPanel(user);
        mPanel = new MovieRecommendationPanel(user);

        // Add the panels to the main panel
        mainPanel.add(headerPanel);

        searchPanel.addSearchActionListener(e -> {
            String searchText = searchPanel.getSearchText();
            String genreText = searchPanel.getGenreText();
            Integer selectedRating = searchPanel.getSelectedRating();

            // Check if the search text is null or empty
            if (searchText == null || searchText.trim().isEmpty()) {
                // Prompt the user to enter a movie title or search keyword
                JOptionPane.showMessageDialog(mainPanel,
                        "Please enter a movie title or search keyword.",
                        "No Search Criteria",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                // Proceed with the search if search text is provided
                searchMovies(searchText, genreText, selectedRating);
            }
        });
        mainPanel.add(searchPanel);
        // Create a central panel to hold the main content
        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(recentlyViewedPanel, BorderLayout.WEST);
        centralPanel.add(friendsPanel, BorderLayout.EAST);
        centralPanel.add(mPanel, BorderLayout.SOUTH);

        // Add CenterPanel to the center
        centerPanel = new CenterPanel(recommendationService, user, this);
        centralPanel.add(centerPanel, BorderLayout.CENTER);

        mainPanel.add(centralPanel);

        getContentPane().add(mainPanel);

        pack();
        setVisible(true);

        instance = this;
    }

    public static Dashboard getInstance() {
        return instance;
    }

    private void searchMovies(String name, String genre, Integer rating) {

        List<Movie> searchResults = movieService.searchMovies(name, genre, rating);
        new BrowseMovieUI(user).displaySearchResults(searchResults);
    }

    public RecentlyViewedPanel getRecentlyViewedPanel() {
        return recentlyViewedPanel;
    }
}
