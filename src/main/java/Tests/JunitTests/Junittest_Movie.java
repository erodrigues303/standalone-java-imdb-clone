package Tests;
import Models.Review;
import org.junit.*;
import java.util.List;
import static junit.framework.TestCase.*;
public class Junittest_Movie {
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
        assertEquals(0, movieId);
    }
    @Test
    public void testGetTitle() {
        assertEquals("Animal", title);
    }
    @Test
    public void testGetReleaseYear() {
        assertEquals(2023, releaseYear);
    }
    @Test
    public void testGetDescription() {
        assertEquals(null, description);
    }
    @Test
    public void testGetGenre() {
        assertEquals(null, genre);
    }
    @Test
    public void testGetRating() {
        assertEquals(0.0, rating);
    }
    @Test
    public void testGetRating_list() {
        assertEquals(null, rating_list);
    }
    @Test
    public void testGetReviews() {
        assertEquals(null, reviews);
    }
    @Test
    public void testGetCoverImageUrl() {
        assertEquals(null, coverImageUrl);
    }
}
