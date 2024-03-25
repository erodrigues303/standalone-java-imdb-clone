package GUI.Dashboard;

import Services.MovieRecommendationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MovieRecommendationPanel extends JPanel {
    private String userName;
    private JTextArea moviesList;

    public MovieRecommendationPanel(String userName) {
        this.userName = userName;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());

        JButton recommendationButton = new JButton("Recommended Movies");
        recommendationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayRecommendedMovies();
            }
        });
        add(recommendationButton, BorderLayout.CENTER);
    }

    private void displayRecommendedMovies() {
        List<String> recommendedMovies = MovieRecommendationService.getRecommendedMovies(userName);

        JFrame movieListFrame = new JFrame("Recommended Movies");
        movieListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        movieListFrame.setLayout(new BorderLayout());

        JTextArea moviesList = new JTextArea();
        moviesList.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(moviesList);
        movieListFrame.add(scrollPane, BorderLayout.CENTER);

        StringBuilder sb = new StringBuilder();
        for (String movie : recommendedMovies) {
            sb.append(movie).append("\n");
        }
        moviesList.setText(sb.toString());

        movieListFrame.pack();
        movieListFrame.setLocationRelativeTo(null);
        movieListFrame.setVisible(true);
    }
}
