package Services;

import Models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
MovieService movieService = new MovieService();
ReviewService reviewService = new ReviewService();
CommentService commentService = new CommentService();
RecentlyViewdService recentlyViewdService = new RecentlyViewdService();


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

    public User authenticateUser(String username, String password) {
        try (Connection conn = DbFunctions.connect()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if(rs != null) return resultSetToUser(rs);
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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


    private User resultSetToUser(ResultSet rs) throws SQLException{
        User user = new User(
                rs.getString("username"),
                rs.getString("password")
        );
        int userId = rs.getInt("user_id");
        user.setUserId(userId);
        user.setWatchlist(getWatchlistByUserId(userId));
        user.setReviews(reviewService.getReviewsByUserId(userId));
        user.setComments(commentService.getCommentsByUserId(userId));
        user.setFriendsList(getFriendsListByUserId(userId));
        user.setRecentlyViewed(recentlyViewdService.getRecentlyViewedById(userId));
        return user;
    }

    private User resultSetToFriend(ResultSet rs) throws SQLException{
        User user = new User(rs.getString("username"));
        int userId = rs.getInt("user_id");
        user.setUserId(userId);
        user.setWatchlist(getWatchlistByUserId(userId));
        user.setRecentlyViewed(recentlyViewdService.getRecentlyViewedById(userId));

//        optional
//        user.setReviews(reviewService.getReviewsByUserId(userId));
//        user.setComments(commentService.getCommentsByUserId(userId));
//        user.setFriendsList(getFriendsListByUserId(userId));

        return user;
    }
    public static User getUserById(int userID) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        String username = null;
        String password = null;
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            // Assuming a method that converts a ResultSet row to a Review object
            username = rs.getString("username");
            password = rs.getString("password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new User(username,password);
    }
}
