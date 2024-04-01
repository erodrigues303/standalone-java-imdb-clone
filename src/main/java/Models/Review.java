package Models;

import Services.DbFunctions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review {
    private int reviewId;
    private int userId;
    private int movieId;
    private String reviewText;
    private int rating;
    private int likes;
    private int dislikes;

    public Review(int reviewId, int userId, int movieId, String reviewText, int rating, int likes, int dislikes) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.movieId = movieId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.likes = 0;
        this.dislikes = 0;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getLikes() {
        String sql = "SELECT likes FROM Reviews WHERE review_id = ?";
        try (Connection conn = DbFunctions.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reviewId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                likes = rs.getInt("likes");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        String sql = "SELECT dislikes FROM Reviews WHERE review_id = ?";
        try (Connection conn = DbFunctions.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reviewId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                dislikes = rs.getInt("dislikes");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public void addLikes() {
        int currentLikes = getLikes();
        int newLikes = currentLikes + 1;
        String sql = "UPDATE Reviews SET likes = ? WHERE review_id = ?";
        try (Connection conn = DbFunctions.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newLikes);
            pstmt.setInt(2, reviewId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDislikes() {
        int currentDislikes = getDislikes();
        int newDislikes = currentDislikes + 1;
        String sql = "UPDATE Reviews SET dislikes = ? WHERE review_id = ?";
        try (Connection conn = DbFunctions.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newDislikes);
            pstmt.setInt(2, reviewId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
