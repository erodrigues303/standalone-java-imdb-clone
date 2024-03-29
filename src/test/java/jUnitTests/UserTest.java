package jUnitTests;

import Models.Comment;
import Models.Movie;
import Models.Review;
import Models.User;
import org.junit.*;
import java.util.List;
import static junit.framework.TestCase.*;
import junit.framework.TestCase;
public class UserTest {
    private int userId;
    private String username;
    private String password;
    private List<Movie> watchlist;
    private List<Review> reviews;
    private List<Comment> comments;
    private List<User> friendsList;
    private List<Movie> recentlyViewed;
    @Before
    public void setUp() {
        userId = 0;
        username = "John";
        password = "123";
        watchlist = null;
        reviews = null;
        comments = null;
        friendsList = null;
        recentlyViewed = null;
    }
    @Test
    public void testGetUserId() {
        TestCase.assertEquals(0, userId);
    }
    @Test
    public void testGetUsername() {
        TestCase.assertEquals("John", username);
    }
    @Test
    public void testGetPassword() {
        TestCase.assertEquals("123", password);
    }
    @Test
    public void testGetWatchlist() {
        assertEquals(null, watchlist);
    }
    @Test
    public void testGetReviews() {
        assertEquals(null, reviews);
    }
    @Test
    public void testGetComments() {
        assertEquals(null, comments);
    }
    @Test
    public void testGetFriendsList() {
        assertEquals(null, friendsList);
    }
    @Test
    public void testGetRecentlyViewed() {
        assertEquals(null, recentlyViewed);
    }
}
