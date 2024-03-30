package Services;

import Models.Movie;
import Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecommendToFriendService {
    private User user;

    // Method to retrieve recommended movies from the database
    public static List<Movie> getRecommendedMovies(User user) {
        List<Movie> recommendedMovies = new ArrayList<>();
        String sql = "SELECT * FROM Movies WHERE title IN (SELECT movie_name FROM Movie_recommendation WHERE friend_name = ?)";

        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Movie movie=resultSetToMovie(rs);
                if (movie != null) {
                    recommendedMovies.add(movie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recommendedMovies;
    }
    public static List<String> getRecommendationsByMovie(String movieName) {
        List<String> recommenders = new ArrayList<>();
        String sql = "SELECT DISTINCT user_name FROM Movie_recommendation WHERE movie_name = ?";

        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, movieName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String recommender = rs.getString("user_name");
                recommenders.add(recommender);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recommenders;
    }

    public static Movie resultSetToMovie(ResultSet rs) throws SQLException {
        Movie movie = new Movie(
                rs.getString("title"),
                rs.getInt("releaseYear"),
                rs.getString("description"),
                rs.getString("genre"),
                rs.getDouble("rating")

        );
        movie.setCoverImageUrl(rs.getString("coverImageUrl"));
        movie.setId(rs.getInt("movie_id"));
        return movie;
    }
}


