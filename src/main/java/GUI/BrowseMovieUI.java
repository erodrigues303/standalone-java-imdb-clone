package GUI;

import Models.Movie;
import Models.User;
import GUI.Dashboard.Dashboard;
import GUI.Dashboard.MovieUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.*;

public class BrowseMovieUI extends JFrame {
    private final JPanel searchResultsPanel;
    private final ExecutorService executorService;
    private final User user;
    private final Dashboard dashboard;

    public BrowseMovieUI(User user) {
        this.user = user;
        this.dashboard = Dashboard.getInstance();
        setTitle("Movie Search Results");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);

        searchResultsPanel = new JPanel(new GridLayout(0, 3)); // 3 columns grid layout
        JScrollPane scrollPane = new JScrollPane(searchResultsPanel);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
        executorService = Executors.newCachedThreadPool();
    }

    public void displaySearchResults(List<Movie> searchResults) {
        searchResultsPanel.removeAll(); // Clear previous search results
        for (Movie movie : searchResults) {
            executorService.execute(() -> {
                JPanel moviePanel = MovieUtils.createMovieCard(movie, user);
                SwingUtilities.invokeLater(() -> {
                    searchResultsPanel.add(moviePanel);
                    searchResultsPanel.revalidate();
                    searchResultsPanel.repaint();
                });
            });
        }
    }

    @Override
    public void dispose() {
        executorService.shutdownNow();
        super.dispose();
    }
}
