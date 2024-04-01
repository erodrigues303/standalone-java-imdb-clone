package Services;

import Models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentService {

    public Comment resultSetToComment(ResultSet rs) throws SQLException {
        int commentId = rs.getInt("comment_id");
        int userId = rs.getInt("user_id");
        String commentText = rs.getString("comment_text");
        int upvotes = rs.getInt("upvotes");
        int downvotes = rs.getInt("downvotes");
        int reviewId = rs.getInt("review_id");

        // Assuming the Comment model has a corresponding constructor
        Comment comment = new Comment(commentId, userId, commentText, upvotes, downvotes, reviewId);
        return comment;
    }

    public List<Comment> getCommentsByUserId(int userId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM Comments WHERE user_id = ?";
        try (Connection conn = DbFunctions.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Comment comment = resultSetToComment(rs);
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public boolean addComment(int userId, Comment comment) {
        String sql = "INSERT INTO Comments (user_id, comment_text, upvotes, downvotes, review_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbFunctions.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, comment.getCommentText());
            pstmt.setInt(3, comment.getUpvotes());
            pstmt.setInt(4, comment.getDownvotes());
            pstmt.setInt(5, comment.getReviewId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Comment> getCommentsByReviewId(int reviewId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM Comments WHERE review_id = ?";
        try (Connection conn = DbFunctions.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reviewId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Comment comment = resultSetToComment(rs);
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public String getUsernameByComment(Comment comment) {
        int userID = comment.getUserId();
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        String username = null;
        try (Connection conn = DbFunctions.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            username = rs.getString("username");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }
}
