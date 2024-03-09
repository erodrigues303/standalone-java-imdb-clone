package Models;

public class Comment {
    private int commentId;
    private int userId;
    private String commentText;
    private int upvotes;
    private int downvotes;

    public Comment(int commentId, int userId, String commentText, int upvotes, int downvotes) {
        this.commentId = commentId;
        this.userId = userId;
        this.commentText = commentText;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }
}