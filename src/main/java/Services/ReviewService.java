package Services;
import Models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ReviewService {

    public Review resultSetToReview(ResultSet rs) throws SQLException {
        int reviewId = rs.getInt("review_id");
        int userId = rs.getInt("user_id");
        int movieId = rs.getInt("movie_id");
        String reviewText = rs.getString("review_text");
        int rating = rs.getInt("rating");

        // Assuming the Review model has a corresponding constructor
        Review review = new Review(reviewId, userId, movieId, reviewText, rating);
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

}
