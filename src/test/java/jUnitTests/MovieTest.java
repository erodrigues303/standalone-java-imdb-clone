package jUnitTests;

import Models.Review;
import junit.framework.TestCase;
import org.junit.*;
import java.util.List;

public class MovieTest {
    private String title;
    private int releaseYear;
    private String description;
    private String genre;
    private Double rating;
    private List<Double> rating_list;
    private List<Review> reviews;
    private String coverImageUrl;
    private int movieId;

    @Before
    public void setUp() {
        title = "Animal";
        releaseYear = 2023;
        description = null;
        genre = null;
        rating = 0.0;
        rating_list = null;
        reviews = null;
        coverImageUrl = null;
        movieId = 0;
    }
    @Test
    public void testGetMovieId() {
        TestCase.assertEquals(0, movieId);
    }
    @Test
    public void testGetTitle() {
        TestCase.assertEquals("Animal", title);
    }
    @Test
    public void testGetReleaseYear() {
        TestCase.assertEquals(2023, releaseYear);
    }
    @Test
    public void testGetDescription() {
        TestCase.assertEquals(null, description);
    }
    @Test
    public void testGetGenre() {
        TestCase.assertEquals(null, genre);
    }
    @Test
    public void testGetRating() {
        TestCase.assertEquals(0.0, rating);
    }
    @Test
    public void testGetRating_list() {
        TestCase.assertEquals(null, rating_list);
    }
    @Test
    public void testGetReviews() {
        TestCase.assertEquals(null, reviews);
    }
    @Test
    public void testGetCoverImageUrl() {
        TestCase.assertEquals(null, coverImageUrl);
    }
}
