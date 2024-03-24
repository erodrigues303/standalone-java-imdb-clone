package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieRecommendationService {

    // Method to retrieve recommended movies from the database
    public static List<String> getRecommendedMovies(String userName) {
        List<String> recommendedMovies = new ArrayList<>();

        // SQL query to select distinct movies recommended to the user
        String sql = "SELECT DISTINCT movie_name, user_name FROM Movie_recommendation WHERE friend_name = ?";

        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName); // Set the username parameter

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Iterate through the result set and add movies to the list
            while (rs.next()) {
                String movieName = rs.getString("movie_name");
                String recommenderName = rs.getString("user_name");
                recommendedMovies.add(movieName + " (Recommended by: " + recommenderName + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recommendedMovies;
    }

}
