package GUI.Reviews;

import Models.Movie;
import Models.Review;
import Models.User;
import Services.ReviewService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewsUI extends JFrame {

    private Movie movie;
    private User user;
    private JPanel reviewsPanel;
    private Map<Review, Integer> likeCounts;
    private Map<Review, Integer> dislikeCounts;

    public ReviewsUI(Movie movie, User user) {
        this.movie = movie;
        this.user = user;
        setTitle("Reviews for " + movie.getTitle());
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reviewsPanel = new JPanel();
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
        likeCounts = new HashMap<>();
        dislikeCounts = new HashMap<>();
        displayReviews(movie.getMovieId());
        JScrollPane scrollPane = new JScrollPane(reviewsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);
    }

    private void displayReviews(int movieId) {
        reviewsPanel.removeAll();
        ReviewService reviewService = new ReviewService();
        try {
            List<Review> reviews = reviewService.getReviewsByMovieId(movieId);
            for (Review review : reviews) {
                JPanel reviewPanel = new JPanel();
                reviewPanel.setLayout(new BorderLayout());
                JLabel userLabel = new JLabel("User: " + reviewService.getUsernameByReview(review) + "\n");
                JLabel ratingLabel = new JLabel("Rating: " + review.getRating() + "\n");
                JLabel reviewTextLabel = new JLabel("Review: " + review.getReviewText() + "\n\n");
                JLabel likesCountLabel = new JLabel("Likes: " + review.getLikes() + "\n");
                JLabel dislikesCountLabel = new JLabel("Dislikes: " + review.getDislikes() + "\n");
                JPanel buttonPanel = new JPanel();
                JButton likeButton = new JButton("Like");
                JButton dislikeButton = new JButton("Dislike");
                JButton openThreadButton = new JButton("Open Comments");

                likeCounts.put(review, 0); // Initialize like count for this review
                dislikeCounts.put(review, 0); // Initialize dislike count for this review

                likeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int likeCount = likeCounts.getOrDefault(review, 0);
                        if (likeCount % 2 == 0) {
                            review.addLikes();
                            likesCountLabel.setText("Likes: " + review.getLikes());
                        } else {
                            review.subtractLikes();
                            likesCountLabel.setText("Likes: " + review.getLikes());
                        }
                        likeCounts.put(review, likeCount + 1);
                    }
                });

                dislikeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int dislikeCount = dislikeCounts.getOrDefault(review, 0);
                        if (dislikeCount % 2 == 0){
                            review.addDislikes();
                            dislikesCountLabel.setText("Dislikes: " + review.getDislikes());
                        } else {
                            review.subtractDislikes();
                            dislikesCountLabel.setText("Dislikes: " + review.getDislikes());
                        }
                        dislikeCounts.put(review, dislikeCount + 1);
                    }
                });

                openThreadButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openCommentsUI(review, user);
                    }
                });

                buttonPanel.add(likeButton);
                buttonPanel.add(likesCountLabel);
                buttonPanel.add(dislikeButton);
                buttonPanel.add(dislikesCountLabel);
                buttonPanel.add(openThreadButton);
                reviewPanel.add(userLabel, BorderLayout.NORTH);
                reviewPanel.add(ratingLabel, BorderLayout.CENTER);
                reviewPanel.add(reviewTextLabel, BorderLayout.AFTER_LAST_LINE);
                reviewPanel.add(buttonPanel, BorderLayout.EAST);
                reviewsPanel.add(reviewPanel);
                reviewsPanel.add(Box.createRigidArea(new Dimension(0, 100)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching reviews: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        reviewsPanel.revalidate();
        reviewsPanel.repaint();
    }

    private void updateReviewsPanel(Movie movie) {
        reviewsPanel.removeAll();
        displayReviews(movie.getMovieId());
        reviewsPanel.revalidate();
        reviewsPanel.repaint();
    }

    public void openCommentsUI(Review review, User user) {
        CommentsUI commentsUI = new CommentsUI(review, user);
        commentsUI.setVisible(true);
    }
}