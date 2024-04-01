package ServiceTests;

import junit.framework.TestCase;
import org.junit.*;

public class CommentServiceTest {
    private int commentId;
    private int userId;
    private int movieId;
    private String commentText;

    @Before
    public void setUp() {
        commentId = 1;
        userId = 1;
        movieId = 1;
        commentText = "I agree with this review!";
    }
    @Test
    public void testGetCommentId() {
        TestCase.assertEquals(1, commentId);
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
    public void testGetCommentText() {
        TestCase.assertEquals("I agree with this review!", commentText);
    }
}
