package GUI.Dashboard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import GUI.BrowseMovieUI;
import Models.Movie;
import Models.User;
import Services.MovieService;
import Services.FriendService;

public class Dashboard extends JFrame {
    private static Dashboard instance;
    private User user;
    private MovieService movieService = new MovieService();

    private HeaderPanel headerPanel;
    private SearchPanel searchPanel;
    private RecentlyViewedPanel recentlyViewedPanel;
    private CenterPanel centerPanel;
    private FriendsPanel friendsPanel;

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
        centerPanel = new CenterPanel(movieService, user, this);
        friendsPanel = new FriendsPanel(user);

        // Add the panels to the main panel
        mainPanel.add(headerPanel);

        searchPanel.addSearchActionListener(e -> searchMovies(searchPanel.getSearchText()));
        mainPanel.add(searchPanel);

        // Create a central panel to hold the main content
        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(recentlyViewedPanel, BorderLayout.WEST);
        centralPanel.add(centerPanel, BorderLayout.CENTER);
        centralPanel.add(friendsPanel, BorderLayout.EAST);

        mainPanel.add(centralPanel);

        getContentPane().add(mainPanel);

        pack();
        setVisible(true);

        instance = this;
    }

    public static Dashboard getInstance() {
        return instance;
    }

    private void searchMovies(String searchText) {
        List<Movie> searchResults = movieService.getMovieByName(searchText);
        new BrowseMovieUI(user).displaySearchResults(searchResults);
    }

    public RecentlyViewedPanel getRecentlyViewedPanel() {
        return recentlyViewedPanel;
    }
}
