package jUnitTests;

import Models.Movie;
import Models.User;
import Services.RecommendationService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class RecommendationServiceTest {
    private RecommendationService recommendationService;
    private User user;

    @Before
    public void setUp() {
        recommendationService = new RecommendationService();
        user = new User("TestUser", "TestPassword");

        // Initialize your user's recently viewed movies here
        Movie movie1 = new Movie("Movie1", 2020, "This is a test movie", "Action", 4.5);
        Movie movie2 = new Movie("Movie2", 2021, "This is another test movie", "Comedy", 3.5);
        user.addRecentlyViewed(movie1);
        user.addRecentlyViewed(movie2);
    }

    @Test
    public void testGetRecommendations() {
        List<Movie> recommendations = recommendationService.getRecommendations(user);
        assertNotNull("Recommendations should not be null", recommendations);
        assertFalse("Recommendations should not be empty", recommendations.isEmpty());
    }

    @Test
    public void testGetDefaultRecommendations() {
        List<Movie> defaultRecommendations = recommendationService.getDefaultRecommendations();
        assertNotNull("Default recommendations should not be null", defaultRecommendations);
        assertFalse("Default recommendations should not be empty", defaultRecommendations.isEmpty());
    }

    @Test
    public void testGetRecommendationsWithNoRecentlyViewedMovies() {
        user.setRecentlyViewed(new ArrayList<>());
        List<Movie> recommendations = recommendationService.getRecommendations(user);
        assertNotNull("Recommendations should not be null", recommendations);
        assertFalse("Recommendations should not be empty", recommendations.isEmpty());
    }

    @Test
    public void testGetRecommendationsWithOneRecentlyViewedMovie() {
        Movie movie = new Movie("Test Movie", 2024, "This is a test movie", "Test", 5.0);
        user.setRecentlyViewed(new ArrayList<>());
        user.addRecentlyViewed(movie);
        List<Movie> recommendations = recommendationService.getRecommendations(user);
        assertNotNull("Recommendations should not be null", recommendations);
        assertFalse("Recommendations should not be empty", recommendations.isEmpty());
    }

    @Test
    public void testGetRecommendationsWithMultipleRecentlyViewedMovies() {
        Movie movie1 = new Movie("Test Movie 1", 2024, "This is a test movie", "Test", 5.0);
        Movie movie2 = new Movie("Test Movie 2", 2025, "This is another test movie", "Test", 4.0);
        user.setRecentlyViewed(new ArrayList<>());
        user.addRecentlyViewed(movie1);
        user.addRecentlyViewed(movie2);
        List<Movie> recommendations = recommendationService.getRecommendations(user);
        assertNotNull("Recommendations should not be null", recommendations);
        assertFalse("Recommendations should not be empty", recommendations.isEmpty());
    }
}