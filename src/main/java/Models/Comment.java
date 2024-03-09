package Models;

import java.util.*;

public class Comment {
    private User user;
    private String content;
    private Review parentReview;//comment will be under this review
    private Comment parentComment;//OR under this comment
    private int noOfUpvotes;
    private int noOfDownvotes;
    private List<Comment> replies;

    public Comment(String content){

    }

    public Comment(User user,Review parentReview,String content){
        this.content=content;
        this.parentReview=parentReview;
        this.noOfDownvotes=0;
        this.noOfUpvotes=0;
        this.user=user;
        this.replies=new ArrayList<Comment>();
        parentReview.addComment(this);
    }

    public Comment(User user,Comment parentComment,String content){
        this.content=content;
        this.parentComment=parentComment;
        this.noOfDownvotes=0;
        this.noOfUpvotes=0;
        this.user=user;
        this.replies=new ArrayList<Comment>();
        parentComment.addReply(this);
    }

    public Comment getParentComment() {
        return parentComment;
    }
    public int getNoOfDownvotes() {
        return noOfDownvotes;
    }
    public int getNoOfUpvotes() {
        return noOfUpvotes;
    }
    public String getContent() {
        return content;
    }
    public List<Comment> getReplies() {
        return replies;
    }
    public Review getParentReview() {
        return parentReview;
    }
    public User getUser() {
        return user;
    }
    public void setParentComment(Comment comment) {
        this.parentComment = comment;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setDownvotes(int noOfDownvotes) {
        this.noOfDownvotes = noOfDownvotes;
    }
    public void setUpvotes(int noOfUpvotes) {
        this.noOfUpvotes = noOfUpvotes;
    }
    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }
    public void setParentReview(Review review) {
        this.parentReview = review;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void UpvoteComment() {
        this.noOfUpvotes++;
    }
    public void DownvoteComment() {
        this.noOfDownvotes++;
    }
    public int getTotalVotes() {
        return noOfUpvotes - noOfDownvotes;
    }
    public void addReply(Comment reply) {
        this.replies.add(reply);
    }

    @Override
    public String toString() {
        return "Comment [user=" + user + ", content=" + content + ", replies=" + replies + "]";
    }

}