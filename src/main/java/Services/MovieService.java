package Services;

import Models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieService {

    public boolean createMovie(Movie movie) {
        String sql = "INSERT INTO Movies (title, releaseYear, description, genre, rating, coverImageUrl) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbFunctions.connect(); // This method should establish a new DB connection
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, movie.getTitle());
            pstmt.setInt(2, movie.getReleaseYear());
            pstmt.setString(3, movie.getDescription());
            pstmt.setString(4, movie.getGenre());
            pstmt.setDouble(5, movie.getRating());
            pstmt.setString(6, movie.getCoverImageUrl());
//            System.out.println(pstmt);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public Movie resultSetToMovie(ResultSet rs) throws SQLException {
        Movie movie = new Movie(
                rs.getString("title"),
                rs.getInt("releaseYear"),
                rs.getString("description"),
                rs.getString("genre"),
                rs.getDouble("rating")

        );
        movie.setCoverImageUrl(rs.getString("coverImageUrl"));
        movie.setId(rs.getInt("movie_id"));
        // Assuming you have a setId method
        return movie;
    }

}
