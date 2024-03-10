package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.net.*;
import java.util.List;
import java.util.concurrent.*;
import java.awt.event.MouseEvent;

import Models.Movie;
import Models.User;
import Services.CommentService;
import Services.MovieService;
import Services.RecentlyViewdService;
import Services.ReviewService;

public class BrowseMovieUI extends JFrame {
    public final JPanel searchResultsPanel;
    private final ExecutorService executorService;
    private final User user;
    private final DashboardUI dashboard;




    public BrowseMovieUI(User user, DashboardUI dashboard) {
        this.user = user;
        this.dashboard = dashboard;
        setTitle("Movie Search Results");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);

        searchResultsPanel = new JPanel(new GridLayout(0, 3)); // 3 columns grid layout
        JScrollPane scrollPane = new JScrollPane(searchResultsPanel);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
        executorService = Executors.newCachedThreadPool();
        System.out.println("init done");
    }

    public void displaySearchResults(List<Movie> searchResults) {
        for (Movie movie : searchResults) {
            executorService.execute(() -> addMoviePanel(movie));

        }
    }

    public void addMoviePanel(Movie movie) {
        JPanel moviePanel = new JPanel();
        moviePanel.setLayout(new BorderLayout());
        moviePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add spacing between movie entries
        moviePanel.setPreferredSize(new Dimension(200, 300)); // Set preferred size for each movie panel

        // Title with year and rating
        String titleText = "<html><b><u>" + movie.getTitle() + "</u></b> (" + movie.getReleaseYear() + ") " +
                "<font color='red'><br>" + movie.getRating() + "/10 &#9733;</b></font></html>";
        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openMovieUI(movie); // Open MovieUI when title is clicked
            }
        });
        moviePanel.add(titleLabel, BorderLayout.NORTH);

        // Cover image
        JLabel coverImageLabel = new JLabel();
        coverImageLabel.setPreferredSize(new Dimension(150, 225)); // Set preferred size for cover image
        loadImage(movie.getCoverImageUrl(), coverImageLabel);
        coverImageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand
        coverImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openMovieUI(movie); // Open MovieUI when image is clicked
            }
        });
        moviePanel.add(coverImageLabel, BorderLayout.CENTER);

        // Add moviePanel directly to the searchResultsPanel
        searchResultsPanel.add(moviePanel);
        searchResultsPanel.revalidate(); // Revalidate the searchResultsPanel
        searchResultsPanel.repaint();    // Repaint the searchResultsPanel
    }
    public void openMovieUI(Movie movie) {
        MovieUI movieUI = MovieUI.getInstance(movie, user);
        movieUI.updateMovieDetails(movie);
        movieUI.setVisible(true);
        this.user.addRecentlyViewed(movie);
        this.dashboard.populateRecentlyViewedMovies();


    }

    private void loadImage(String imageUrl, JLabel imageLabel) {
        try {
            URL url = new URL(imageUrl);
            ImageIcon icon = new ImageIcon(url);
            Image scaledImage = icon.getImage().getScaledInstance(150, -1, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImage);
            imageLabel.setIcon(icon);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void dispose() {
        executorService.shutdownNow();
        super.dispose();
    }
}
