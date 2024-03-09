package Tests;

import Models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Junittest_User {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("TestUser", "TestPassword");
    }

    @Test
    public void testUsername() {
        assertEquals("TestUser", user.getUsername());
        user.setUsername("NewUsername");
        assertEquals("NewUsername", user.getUsername());
    }

    @Test
    public void testPassword() {
        assertEquals("TestPassword", user.getPassword());
        user.setPassword("NewPassword");
        assertEquals("NewPassword", user.getPassword());
    }

    @Test
    public void testWatchlist() {
        assertNull(user.getWatchlist());
        List<Movie> newWatchlist = new ArrayList<Movie>();
        user.setWatchlist(newWatchlist);
        assertEquals(newWatchlist, user.getWatchlist());
    }

    @Test
    public void testReviews() {
        assertTrue(user.getReviews().isEmpty());
        Review newReview = new Review("",0);
        user.addReview(newReview);
        assertTrue(user.getReviews().contains(newReview));
    }

    @Test
    public void testComments() {
        assertTrue(user.getComments().isEmpty());
        Comment newComment = new Comment("");
        user.addComment(newComment);
        assertTrue(user.getComments().contains(newComment));
    }

    @Test
    public void testFriendsList() {
        assertTrue(user.getFriendsList().isEmpty());
        User newFriend = new User("FriendUser", "FriendPassword");
        user.addFriend(newFriend);
        assertTrue(user.getFriendsList().contains(newFriend));
    }

    @Test
    public void testRecentlyViewed() {
        assertTrue(user.getRecentlyViewed().isEmpty());
        Movie newMovie = new Movie("Test Movie", 2024, "This is a test movie", "Test");
        user.addRecentlyViewed(newMovie);
        assertTrue(user.getRecentlyViewed().contains(newMovie));
    }
}