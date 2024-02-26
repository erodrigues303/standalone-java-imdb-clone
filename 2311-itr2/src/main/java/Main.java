import GUI.LoginUI;
import Models.Movie;
import Services.MovieManager;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginUI();
            }
        });

//        MovieManager movieManager = new MovieManager();
//        try {
//            List<Movie> movies = movieManager.searchMovies("Avengers");
//            for (Movie movie : movies) {
//                System.out.println("Title: " + movie.getTitle());
//                System.out.println("Release Year: " + movie.getReleaseYear());
//                System.out.println("Description: " + movie.getDescription());
//                System.out.println("Rating: " + movie.getRating());
//                System.out.println("Cover Image URL: " + movie.getCoverImageUrl());
//                System.out.println();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}