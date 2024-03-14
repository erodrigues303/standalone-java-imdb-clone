package Tests.JunitTests;
import Models.*;
import org.junit.*;
import java.util.List;
import static junit.framework.TestCase.*;
public class Junittest_Comment {
    private int commentId;
    private int userId;
    private String commentText;
    private int upvotes;
    private int downvotes;

    @Before
    public void setUp() {
        commentId = 4;
        userId = 2;
        commentText = "This is a comment";
        upvotes = 4;
        downvotes = 1;
    }
    @Test
    public void testGetCommentId() {
        assertEquals(4, commentId);
    }
    @Test
    public void testGetUserId() {
        assertEquals(2, userId);
    }
    @Test
    public void testGetCommentText() {
        assertEquals("This is a comment", commentText);
    }
    @Test
    public void testGetUpvotes() {
        assertEquals(4, upvotes);
    }
    @Test
    public void testGetDownvotes() {
        assertEquals(1, downvotes);
    }
}