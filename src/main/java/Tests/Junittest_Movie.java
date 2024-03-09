package Tests;

import Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Junittest_Movie {
    private Movie movie;

    @BeforeEach
    public void setUp() {
        movie = new Movie("Test Movie", 2024, "This is a test movie", "Test");
    }

    @Test
    public void testTitle() {
        assertEquals("Test Movie", movie.getTitle());
        movie.setTitle("New Title");
        assertEquals("New Title", movie.getTitle());
    }

    @Test
    public void testReleaseYear() {
        assertEquals(2024, movie.getReleaseYear());
        movie.setReleaseYear(2025);
        assertEquals(2025, movie.getReleaseYear());
    }

    @Test
    public void testDescription() {
        assertEquals("This is a test movie", movie.getDescription());
        movie.setDescription("New Description");
        assertEquals("New Description", movie.getDescription());
    }

    @Test
    public void testGenre() {
        assertEquals("Test", movie.getGenre());
        movie.setGenre("New Genre");
        assertEquals("New Genre", movie.getGenre());
    }

    @Test
    public void testRating() {
        assertEquals(0.0, movie.getRating());
        movie.addRating(4.5);
        assertEquals(4.5, movie.getRating());
        movie.addRating(3.5);
        assertEquals(4.0, movie.getRating());
    }

    @Test
    public void testCoverImageUrl() {
        assertNull(movie.getCoverImageUrl());
        movie.setCoverImageUrl("New URL");
        assertEquals("New URL", movie.getCoverImageUrl());
    }
}