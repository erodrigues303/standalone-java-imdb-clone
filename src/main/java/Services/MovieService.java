package Services;

import Models.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

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
            // System.out.println(pstmt);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Movie> searchMovies(String namePart, String genre, Integer rating) {
        List<Movie> movies = new ArrayList<>();
        ArrayList<Object> parameters = new ArrayList<>();
        String sql = "SELECT * FROM Movies WHERE 1=1";

        if (namePart != null && !namePart.isEmpty()) {
            sql += " AND title LIKE ?";
            parameters.add("%" + namePart + "%");
        }

        if (genre != null && !genre.isEmpty()) {
            sql += " AND genre LIKE ?";
            parameters.add("%" + genre + "%");
        }

        if (rating != null) {
            sql += " AND rating >= ?";
            parameters.add(rating);
        }

        try (Connection conn = DbFunctions.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Movie movie = resultSetToMovie(rs);
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
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
        return movie;
    }

    public List<Movie> getRecommendations(User user) {
        List<Movie> recommendedMovies = new ArrayList<>();
        List<Movie> recentlyViewed = user.getRecentlyViewed();

        if (recentlyViewed.isEmpty()) {
            return recommendedMovies; // No recommendations if the user has not viewed any movies
        }

        double averageRating = recentlyViewed.stream()
                .mapToDouble(Movie::getRating)
                .average()
                .orElse(0.0);

        Map<String, Long> genreFrequency = recentlyViewed.stream()
                .map(Movie::getGenre)
                .flatMap(genre -> Arrays.stream(genre.split(", ")))
                .collect(Collectors.groupingBy(g -> g, Collectors.counting()));

        List<String> mostCommonGenres = genreFrequency.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        IntSummaryStatistics releaseYearStats = recentlyViewed.stream()
                .mapToInt(Movie::getReleaseYear)
                .summaryStatistics();

        int minReleaseYear = releaseYearStats.getMin();
        int maxReleaseYear = releaseYearStats.getMax();

        String genreInClause = mostCommonGenres.stream()
                .map(genre -> "'" + genre + "'")
                .collect(Collectors.joining(", "));

        String sql = "SELECT * FROM Movies WHERE rating > ? AND releaseYear BETWEEN ? AND ? " +
                "AND (genre LIKE ? OR genre LIKE ?) " +
                // "ORDER BY rating DESC "+
                "LIMIT 6";

        try (Connection conn = DbFunctions.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the parameters for the prepared statement
            pstmt.setDouble(1, averageRating);
            pstmt.setInt(2, minReleaseYear);
            pstmt.setInt(3, maxReleaseYear);
            pstmt.setString(4, "%" + mostCommonGenres.get(0) + "%");
            pstmt.setString(5, mostCommonGenres.size() > 1 ? "%" + mostCommonGenres.get(1) + "%"
                    : "%" + mostCommonGenres.get(0) + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Movie movie = resultSetToMovie(rs);
                recommendedMovies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recommendedMovies;
    }
}
