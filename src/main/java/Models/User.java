package Models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int userId;
    private String username;
    private String password;
    private List<Movie> watchlist;
    private List<Review> reviews;

    private List<Comment> comments;
    private List<User> friendsList;
    private List<Movie> recentlyViewed;


    // Constructor
    public User(String username, String password) {
        this.userId = 0;
        this.username = username;
        this.password = password;
        this.watchlist = new ArrayList<>();;
        this.reviews = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.friendsList = new ArrayList<>();
        this.recentlyViewed = new ArrayList<>();
    }

    public User(String username){
        this.userId = 0;
        this.username = username;
//        this.password = "friend";
//        this.watchlist = new ArrayList<>();;
//        this.reviews = new ArrayList<>();
//        this.comments = new ArrayList<>();
//        this.friendsList = new ArrayList<>();
        this.recentlyViewed = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Movie> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(List<Movie> watchlist) {
        this.watchlist = watchlist;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<User> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<User> friendsList) {
        this.friendsList = friendsList;
    }

    public List<Movie> getRecentlyViewed() {
        return recentlyViewed;
    }

    public void setRecentlyViewed(List<Movie> recentlyViewed) {
        this.recentlyViewed = recentlyViewed;
    }

    public void addFriend(User friend){
        this.friendsList.add(friend);
    }

    public void addReview(Review review){
        this.reviews.add(review);
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public void addRecentlyViewed(Movie movie) {
        // Add the new movie to the beginning of the list
        recentlyViewed.add(0, movie);

        // Check if the list exceeds the maximum length of 5
        if (recentlyViewed.size() > 5) {
            // Remove the oldest movie (at index 5)
            recentlyViewed.remove(5);
        }
    }


}
