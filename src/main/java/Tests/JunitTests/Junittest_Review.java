package Tests;
import Models.Review;
import org.junit.*;
import static junit.framework.TestCase.*;
public class Junittest_Review {
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
        assertEquals(1, reviewId);
    }
    @Test
    public void testGetUserId() {
        assertEquals(1, userId);
    }
    @Test
    public void testGetMovieId() {
        assertEquals(1, movieId);
    }
    @Test
    public void testGetReviewText() {
        assertEquals("This movie is not good", reviewText);
    }
    @Test
    public void testGetRating() {
        assertEquals(2, rating);
    }
}
