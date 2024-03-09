package Services;

import Models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
MovieService movieService = new MovieService();
ReviewService reviewService = new ReviewService();
CommentService commentService = new CommentService();


    // Method to create a new User
    public boolean createUser(User user) {
        String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve a User by username
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return resultSetToUser(rs); // resultSetToUser method will handle the object mapping
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // User not found
    }

    // Method to update a User's information
//    public boolean updateUser(User user) {
//        // Update user information in the Users table
//    }

    // Method to delete a User
    public boolean deleteUser(User user) {
        String sql = "DELETE FROM Users WHERE user_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, user.getUserId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Method to retrieve a User's watchlist
    public List<Movie> getWatchlistByUserId(int userId) {
        List<Movie> watchlist = new ArrayList<>();
        String sql = "SELECT m.* FROM Watchlist w JOIN Movies m ON w.movie_id = m.movie_id WHERE w.user_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Assuming a method that converts a ResultSet row to a Movie object
                Movie movie = movieService.resultSetToMovie(rs);
                watchlist.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return watchlist;
    }

    // Method to add a Movie to a User's watchlist
    public boolean addToWatchlist(int userId, int movieId) {
        String sql = "INSERT INTO Watchlist (user_id, movie_id) VALUES (?, ?)";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, movieId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to remove a Movie from a User's watchlist
    public boolean removeFromWatchlist(int userId, int movieId) {
        String sql = "DELETE FROM Watchlist WHERE user_id = ? AND movie_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, movieId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve a list of Reviews by a User
    public List<Review> getReviewsByUserId(int userId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM Reviews WHERE user_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Assuming a method that converts a ResultSet row to a Review object
                Review review = reviewService.resultSetToReview(rs);
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

    // Method to retrieve a list of Comments by a User
    public List<Comment> getCommentsByUserId(int userId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM Comments WHERE user_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Comment comment = commentService.resultSetToComment(rs);
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    // Method to add a Comment by a User
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

    // Method to retrieve a User's friends list
    public List<User> getFriendsListByUserId(int userId) {
        List<User> friendsList = new ArrayList<>();
        String sql = "SELECT u.* FROM Friends f JOIN Users u ON f.friend_id = u.user_id WHERE f.user_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User friend = resultSetToFriend(rs);
                friendsList.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendsList;
    }


    // Method to add a friend to a User's friends list

    public boolean addFriend(int userId, int friendId) {
        String sql = "INSERT INTO Friends (user_id, friend_id) VALUES (?, ?)";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, friendId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to remove a friend from a User's friends list
    public boolean removeFriend(int userId, int friendId) {
        String sql = "DELETE FROM Friends WHERE user_id = ? AND friend_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, friendId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve a User's recently viewed movies
    public List<Movie> getRecentlyViewedById(int userId) {
        List<Movie> recentlyViewed = new ArrayList<>();
        String sql = "SELECT m.* FROM RecentlyViewed rv JOIN Movies m ON rv.movie_id = m.movie_id WHERE rv.user_id = ? ORDER BY rv.view_date DESC";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Movie movie = movieService.resultSetToMovie(rs);
                recentlyViewed.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recentlyViewed;
    }


    // Method to add a Movie to a User's recently viewed movies list
    public boolean addRecentlyViewedMovie(int userId, int movieId) {
        String sql = "INSERT INTO RecentlyViewed (user_id, movie_id) VALUES (?, ?)";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, movieId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private User resultSetToUser(ResultSet rs) throws SQLException{
        User user = new User(
                rs.getString("username"),
                rs.getString("password")
        );
        int userId = rs.getInt("user_id");
        user.setUserId(userId);
        user.setWatchlist(getWatchlistByUserId(userId));
        user.setReviews(getReviewsByUserId(userId));
        user.setComments(getCommentsByUserId(userId));
        user.setFriendsList(getFriendsListByUserId(userId));
        user.setRecentlyViewed(getRecentlyViewedById(userId));
        return user;
    }

    private User resultSetToFriend(ResultSet rs) throws SQLException{
        User user = new User(rs.getString("username"));
        int userId = rs.getInt("user_id");
        user.setUserId(userId);
//        user.setWatchlist(getWatchlistByUserId(userId));
//        user.setReviews(getReviewsByUserId(userId));
//        user.setComments(getCommentsByUserId(userId));
//        user.setFriendsList(getFriendsListByUserId(userId));
        user.setRecentlyViewed(getRecentlyViewedById(userId));
        return user;
    }
}
