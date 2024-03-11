package GUI;

import Models.Movie;
import Models.Review;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReviewsUI extends JFrame {

    private JTextArea reviewTextArea;

    public ReviewsUI(Movie movie) {
        setTitle("Reviews for " + movie.getTitle());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reviewTextArea = new JTextArea();
        reviewTextArea.setEditable(false);
        displayReviews(movie.getMovieId());
        setLayout(new BorderLayout());
        add(new JScrollPane(reviewTextArea), BorderLayout.CENTER);
    }

    private void displayReviews(int movieId) {
        reviewTextArea.setText("");
        Services.ReviewService reviewService = new Services.ReviewService();
        try {
            List<Review> reviews = reviewService.getReviewsByMovieId(movieId);
            for (Review review : reviews) {
                reviewTextArea.append("User: " + reviewService.getUsernameByReview(review) + "\n");
                reviewTextArea.append("Rating: " + review.getRating() + "\n");
                reviewTextArea.append("Review: " + review.getReviewText() + "\n\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching reviews: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
