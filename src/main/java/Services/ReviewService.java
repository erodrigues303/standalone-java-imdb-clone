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
}
