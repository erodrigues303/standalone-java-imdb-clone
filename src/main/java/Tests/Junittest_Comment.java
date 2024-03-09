package Tests;

import Models.Comment;
import Models.Movie;
import Models.Review;
import Models.User;
import org.junit.*;


import java.util.List;

import static junit.framework.TestCase.*;


public class Junittest_Comment {

    private User user;
    private Review review;
    private Comment parentComment;
    private Comment childComment1;
    private Comment childComment2;

    @Before
    public void setUp() {
        user = new User("John", "123");
        review = new Review(user, new Movie("Animal", 2023, null, null), "This movie is not good", 2);
        parentComment = new Comment(user, review, "I agree");
        childComment1 = new Comment(user, parentComment, "Me too!");
        childComment2 = new Comment(user, parentComment, "I disagree");
    }

    @Test
    public void testConstructorWithReview() {
        assertEquals("John", parentComment.getUser().getUsername());
        assertEquals("I agree", parentComment.getContent());
        assertEquals(review, parentComment.getParentReview());
        assertEquals(0, parentComment.getNoOfDownvotes());
        assertEquals(0, parentComment.getNoOfUpvotes());
        assertEquals(0, parentComment.getTotalVotes());
        assertEquals(2, parentComment.getReplies().size());
        assertTrue(parentComment.getReplies().contains(childComment1));
        assertTrue(parentComment.getReplies().contains(childComment2));
    }

    @Test
    public void testUpvote() {
        parentComment.UpvoteComment();
        assertEquals(1, parentComment.getNoOfUpvotes());
        assertEquals(1, parentComment.getTotalVotes());
    }

    @Test
    public void testDownvote() {
        parentComment.DownvoteComment();
        assertEquals(1, parentComment.getNoOfDownvotes());
        assertEquals(-1, parentComment.getTotalVotes());
    }

    @Test
    public void testAddReply() {
        Comment newReply = new Comment(user, parentComment, "I also agree");
        List<Comment> replies = parentComment.getReplies();
        assertEquals(3, replies.size());
        assertTrue(replies.contains(newReply));
    }

    @Test
    public void testSettersAndGetters() {
        parentComment.setContent("New content");
        assertEquals("New content", parentComment.getContent());

        parentComment.setUpvotes(5);
        parentComment.setDownvotes(3);
        assertEquals(5, parentComment.getNoOfUpvotes());
        assertEquals(3, parentComment.getNoOfDownvotes());

        Review newReview = new Review(user, new Movie("New Movie", 2024, null, null), "This movie is great", 4);
        parentComment.setParentReview(newReview);
        assertEquals(newReview, parentComment.getParentReview());

        User newUser = new User("Jane", "456");
        parentComment.setUser(newUser);
        assertEquals(newUser, parentComment.getUser());

        Comment newParentComment = new Comment(user, review, "I have a different opinion");
        parentComment.setParentComment(newParentComment);
        assertEquals(newParentComment, parentComment.getParentComment());
    }
}