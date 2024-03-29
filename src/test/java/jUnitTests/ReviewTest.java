package jUnitTests;

import junit.framework.TestCase;
import org.junit.*;

public class ReviewTest {
    private int reviewId;
    private int userId;
    private int movieId;
    private String reviewText;
    private int rating;

    @Before
    public void setUp() {
        reviewId = 1;
        userId = 1;
        movieId = 1;
        reviewText = "This movie is not good";
        rating = 2;
    }
    @Test
    public void testGetReviewId() {
        TestCase.assertEquals(1, reviewId);
    }
    @Test
    public void testGetUserId() {
        TestCase.assertEquals(1, userId);
    }
    @Test
    public void testGetMovieId() {
        TestCase.assertEquals(1, movieId);
    }
    @Test
    public void testGetReviewText() {
        TestCase.assertEquals("This movie is not good", reviewText);
    }
    @Test
    public void testGetRating() {
        TestCase.assertEquals(2, rating);
    }
}
