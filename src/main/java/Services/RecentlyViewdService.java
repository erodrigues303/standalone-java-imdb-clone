package Services;

import Models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecentlyViewdService {
MovieService movieService = new MovieService();
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
    public boolean addRecentlyViewedMovie(User user, Movie movie) {
        int userId = user.getUserId();
        int movieId = movie.getMovieId();
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

    public boolean removeRecentlyViewedMovie(User user, Movie movie) {
        int userId = user.getUserId();
        int movieId = movie.getMovieId();
        String sql = "DELETE FROM RecentlyViewed WHERE user_id = ? AND movie_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, movieId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
