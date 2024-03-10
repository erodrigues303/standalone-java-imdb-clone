//package GUI.Dashboard;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.List;
////import Services.MovieManager;
//
//import GUI.BrowseMovieUI;
//import GUI.MovieUI;
//import Models.*;
//import Services.MovieService;
//
//public class DashboardUI extends JFrame {
//
//    private final User user;
//    private JPanel contentPanel;
//    private JPanel recentlyViewedMoviesPanel;
//    private JPanel centerPanel;
//    private MovieService movieService = new MovieService();
//
//    public DashboardUI(User user) {
//        this.user = user;
//        setTitle("Dashboard");
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        setPreferredSize(new Dimension(800, 600));
//        setLocationRelativeTo(null);
//
//        // Use a main panel with BoxLayout to stack components vertically
//        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//
//        // Add header and search on top
//        mainPanel.add(createHeaderPanel());
//        mainPanel.add(createSearchPanel());
//
//        // Create a central panel with a new BorderLayout to hold the main content in the center
//        JPanel centralPanel = new JPanel(new BorderLayout());
//        centralPanel.add(createRecentlyViewedPanel(), BorderLayout.WEST);
//        centralPanel.add(createCenterPanel(), BorderLayout.CENTER);
//        centralPanel.add(createFriendsPanel(), BorderLayout.EAST);
//
//        // Add the central panel to the main panel
//        mainPanel.add(centralPanel);
//
//        // Add main panel to the JFrame's content pane
//        getContentPane().add(mainPanel);
//
//        pack();
//        setVisible(true);
//    }
//
//    private JPanel createHeaderPanel() {
//        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0)); // small horizontal gap, no vertical gap
//        JLabel welcomeLabel = new JLabel("Welcome to the Dashboard, " + user.getUsername() + "!");
//        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
//        headerPanel.add(welcomeLabel);
//        return headerPanel;
//    }
//
//    private JPanel createSearchPanel() {
//        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0)); // small horizontal gap, no vertical gap
//        JTextField searchField = new JTextField(20);
//        JButton searchButton = new JButton("Search");
//        searchButton.addActionListener(e -> searchMovies(searchField.getText()));
//        searchPanel.add(searchField);
//        searchPanel.add(searchButton);
//        return searchPanel;
//    }
//
//    private void searchMovies(String searchText) {
//        List<Movie> searchResults = movieService.getMovieByName(searchText);
//        new BrowseMovieUI(user, this).displaySearchResults(searchResults);
//    }
//
//    private JPanel createRecentlyViewedPanel() {
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.add(new JLabel("Recently Viewed Movies:", SwingConstants.CENTER), BorderLayout.NORTH);
//        recentlyViewedMoviesPanel = new JPanel();
//        recentlyViewedMoviesPanel.setLayout(new BoxLayout(recentlyViewedMoviesPanel, BoxLayout.PAGE_AXIS));
//        populateRecentlyViewedMovies();
//        panel.add(new JScrollPane(recentlyViewedMoviesPanel), BorderLayout.CENTER);
//        return panel;
//    }
//
//    public void populateRecentlyViewedMovies() {
//        recentlyViewedMoviesPanel.removeAll(); // Clear existing content
//        for (Movie movie : user.getRecentlyViewed()) {
//            JButton movieButton = new JButton(movie.getTitle());
//            movieButton.addActionListener(e ->
//                    openMovieUI(movie));
//            recentlyViewedMoviesPanel.add(movieButton);
//        }
//        recentlyViewedMoviesPanel.revalidate();
//        recentlyViewedMoviesPanel.repaint();
//    }
//
//    private JPanel createFriendsPanel() {
//        JPanel friendsPanel = new JPanel(new BorderLayout());
//        JLabel friendsLabel = new JLabel("Friends:");
//        friendsLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        friendsPanel.add(friendsLabel, BorderLayout.NORTH);
//        JPanel friendsListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        for (User friend : user.getFriendsList()) {
//            JButton friendButton = new JButton(friend.getUsername());
//            friendButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    JOptionPane.showMessageDialog(DashboardUI.this, "You clicked on " + friend.getUsername());
//                }
//            });
//            friendsListPanel.add(friendButton);
//        }
//        friendsPanel.add(friendsListPanel, BorderLayout.CENTER);
//        return friendsPanel;
//    }
//
//    private JPanel createCentralPanel() {
//        JPanel centralPanel = new JPanel(new BorderLayout());
//        centralPanel.add(createRecentlyViewedPanel(), BorderLayout.WEST);
//        centralPanel.add(createCenterPanel(), BorderLayout.CENTER);
//        centralPanel.add(createFriendsPanel(), BorderLayout.EAST);
//        return centralPanel;
//    }
//
//    private JPanel createCenterPanel() {
//        centerPanel = new JPanel(new GridLayout(2, 3, 10, 10));
//        setupCenterPanel();
//        return centerPanel;
//    }
//    private void setupCenterPanel() {
//        centerPanel = new JPanel(new GridLayout(2, 3, 10, 10)); // 2 rows, 3 cols, horizontal and vertical gaps
//
//        // Dummy list of recommended movies
//        List<Movie> recommendedMovies = movieService.getRecommendations(user); // Replace with actual recommendation fetching method
//
//        for (Movie movie : recommendedMovies) {
//            if (centerPanel.getComponentCount() >= 6) break; // Only add up to 6 movies
//            JPanel card = createMovieCard(movie);
//            centerPanel.add(card);
//        }
//
//        // Add empty panels for remaining slots if necessary
//        for (int i = centerPanel.getComponentCount(); i < 6; i++) {
//            centerPanel.add(new JPanel());
//        }
//    }
//
//    private JPanel createMovieCard(Movie movie) {
//        JPanel cardPanel = new JPanel();
//        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.PAGE_AXIS));
//
//        JLabel movieTitleLabel = new JLabel(movie.getTitle());
//        movieTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        cardPanel.add(movieTitleLabel);
//
//        // Add image loading and resizing logic here
//        JLabel movieImageLabel = new JLabel();
//        movieImageLabel.setPreferredSize(new Dimension(100, 150)); // Set preferred size for cover image
//        loadImage(movie.getCoverImageUrl(), movieImageLabel); // Replace with actual image
//        movieImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        cardPanel.add(movieImageLabel);
//
//        return cardPanel;
//    }
//
//    public void openMovieUI(Movie movie) {
//        MovieUI movieUI = MovieUI.getInstance(movie, user);
//        movieUI.updateMovieDetails(movie);
//        movieUI.setVisible(true);
//    }
//
//    private void loadImage(String imageUrl, JLabel imageLabel) {
//        SwingUtilities.invokeLater(() -> {
//            try {
//                URL url = new URL(imageUrl);
//                ImageIcon icon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH));
//                imageLabel.setIcon(icon);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                imageLabel.setText("Image not available");
//            }
//        });
//    }
//}
