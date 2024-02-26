package Models;

import java.util.*;

public class Review {

    private static final int MAX_RATING = 5;
    private int rating;
    private Movie movie;
    private List<Comment> replies;
    private String title;
    private User user;
    private String content;

    public Review(String content, int rating){

    }

    public Review(User user,Movie movie,String content,int rating){
        title="";
        setRating(rating);
        replies=new ArrayList<Comment>();
        this.user=user;
        this.movie=movie;
        this.content=content;
        movie.addReview(this);
    }
    public Review(User user,Movie movie,String content,int rating,String title){
        this.title=title;
        this.movie=movie;
        setRating(rating);
        this.user=user;
        this.replies=new ArrayList<Comment>();
        this.content=content;
        movie.addReview(this);
    }

    public Movie getMovie() {
        return movie;
    }
    public int getRating() {
        return rating;
    }
    public String getTitle() {
        return title;
    }
    public List<Comment> getReplies() {
        return replies;
    }
    public User getUser() {
        return user;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public void setRating(int rating) {
        // Ensure that the rating value is within the range of 1 to MAX_RATING
        if (rating < 1) {
            this.rating = 1;
        } else if (rating > MAX_RATING) {
            this.rating = MAX_RATING;
        } else {
            this.rating = rating;
        }
    }
    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void addComment(Comment comment){
        this.replies.add(comment);
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    @Override
    public String toString() {
        return "Review [rating=" + rating + ", movie=" + movie + ", replies=" + replies + ", title=" + title + ", user="
                + user + ", content=" + content + "]";
    }
}