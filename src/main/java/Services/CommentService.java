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
}
