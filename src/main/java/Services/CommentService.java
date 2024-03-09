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

        // Assuming the Comment model has a corresponding constructor
        Comment comment = new Comment(commentId, userId, commentText, upvotes, downvotes);
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
        String sql = "INSERT INTO Comments (user_id, comment_text, upvotes, downvotes) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, comment.getCommentText());
            pstmt.setInt(3, comment.getUpvotes());
            pstmt.setInt(4, comment.getDownvotes());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
