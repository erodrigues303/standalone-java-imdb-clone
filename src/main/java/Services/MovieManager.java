package Services;
import Models.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;

public class MovieManager {

    private static final String API_KEY = "9f09de21";
    private static final String API_URL = "http://www.omdbapi.com/";

    public MovieManager() {
    }

    public List<Movie> searchMovies(String query) throws IOException, JSONException {
        String url = String.format("%s?apikey=%s&s=%s", API_URL, API_KEY, query);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        }

        List<Movie> movies = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(response.toString());

        if (jsonResponse.getString("Response").equalsIgnoreCase("True")) {
            JSONArray searchResults = jsonResponse.getJSONArray("Search");
            for (int i = 0; i < searchResults.length(); i++) {
                JSONObject movieJson = searchResults.getJSONObject(i);
                String imdbId = movieJson.getString("imdbID");
                JSONObject detailsJson = fetchMovieDetails(imdbId);
                String yearString = detailsJson.getString("Year").substring(0, 4);
                int releaseYear = Integer.parseInt(yearString);
                if (detailsJson != null) {
                    Movie movie = new Movie(
                            detailsJson.getString("Title"),
                            releaseYear,
                            detailsJson.getString("Plot"),
                            detailsJson.getString("Genre"),
                            detailsJson.getDouble("imdbRating")
                    );
                    movie.setCoverImageUrl(detailsJson.getString("Poster"));
                    movies.add(movie);
//                    movie.setMovieId(Integer.parseInt(imdbId));
                }
            }
        }

        connection.disconnect();
        return movies;
    }

    private JSONObject fetchMovieDetails(String imdbId) throws IOException, JSONException {
        String url = String.format("%s?apikey=%s&i=%s", API_URL, API_KEY, imdbId);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        }

        JSONObject jsonResponse = new JSONObject(response.toString());
        connection.disconnect();
        return jsonResponse;
    }
}
