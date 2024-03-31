package Services;

import Models.Review;
import Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewService {

    public Review resultSetToReview(ResultSet rs) throws SQLException {
        int reviewId = rs.getInt("review_id");
        int userId = rs.getInt("user_id");
        int movieId = rs.getInt("movie_id");
        String reviewText = rs.getString("review_text");
        int rating = rs.getInt("rating");
        int likes = rs.getInt("likes");
        int dislikes = rs.getInt("dislikes");

        // Assuming the Review model has a corresponding constructor
        Review review = new Review(reviewId, userId, movieId, reviewText, rating, likes, dislikes);
        return review;
    }

    public List<Review> getReviewsByUserId(int userId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM Reviews WHERE user_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Assuming a method that converts a ResultSet row to a Review object
                Review review = resultSetToReview(rs);
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public List<Review> getReviewsByMovieId(int movieId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM Reviews WHERE movie_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, movieId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Review review = resultSetToReview(rs);
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
    public Review getReviewByReviewId(int reviewId) {
        String sql = "SELECT * FROM Reviews WHERE review_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reviewId);
            ResultSet rs = pstmt.executeQuery();
            // Assuming a method that converts a ResultSet row to a Review object
            return resultSetToReview(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to add a Review by a User
    public boolean addReview(int userId, Review review) {
        String sql = "INSERT INTO Reviews (user_id, movie_id, review_text, rating) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, review.getMovieId());
            pstmt.setString(3, review.getReviewText());
            pstmt.setInt(4, review.getRating());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUsernameByReview(Review review) {
        int userID = review.getUserId();
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        String username = null;
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            // Assuming a method that converts a ResultSet row to a Review object
            username = rs.getString("username");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }
}