package Tests.IntegrationTests;

import Models.Comment;
import Models.Movie;
import Models.Review;
import Models.User;
import Services.*;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntegrationTest {


        private static UserService userService;
        private static MovieService movieService;
        private static CommentService commentService;
        private static ReviewService reviewService;
        private static FriendService friendService;
        private static RecentlyViewdService recentlyViewedService;

        // Test data identifiers
        private static int testUserId;
        private static int testMovieId;

        @BeforeAll
        public static void setup() {
            // Initialize services
            userService = new UserService();
            movieService = new MovieService();
            commentService = new CommentService();
            reviewService = new ReviewService();
            friendService = new FriendService();
            recentlyViewedService = new RecentlyViewdService();

            // Setup test data in the database, if necessary
        }

        @Test
        @Order(1)
        public void testUserCreation() {
            String uniqueUsername = "testUser" + UUID.randomUUID().toString();
            User newUser = new User(uniqueUsername, "testPass");
            assertTrue(userService.createUser(newUser), "User creation should succeed");

            User retrievedUser = userService.getUserByUsername(uniqueUsername);
            assertNotNull(retrievedUser, "Retrieved user should not be null");
            testUserId = retrievedUser.getUserId();
        }

        @Test
        @Order(2)
        public void testMovieCreationAndRetrieval() {
            Movie newMovie = new Movie("Integration Test Movie", 2024, "Test description", "Test genre", 9.0);
            assertTrue(movieService.createMovie(newMovie), "Movie creation should succeed");

            List<Movie> retrievedMovies = movieService.getMovieByName("Integration Test Movie");
            assertFalse(retrievedMovies.isEmpty(), "Should retrieve at least one movie");
            testMovieId = retrievedMovies.get(0).getMovieId();
        }

        @Test
        @Order(3)
        public void testCommentAndReviewFlow() throws SQLException {
            // Add a comment to the movie by the user
            Comment newComment = new Comment(0, testUserId, "Great movie!", 5, 1);
            assertTrue(commentService.addComment(testUserId, newComment), "Adding a comment should succeed");

            // Add a review for the movie by the user
            Review newReview = new Review(0, testUserId, testMovieId, "Incredible experience", 5);
            assertTrue(reviewService.addReview(testUserId, newReview), "Adding a review should succeed");

            // Retrieve and validate comments and reviews
            List<Comment> comments = commentService.getCommentsByUserId(testUserId);
            assertFalse(comments.isEmpty(), "Should retrieve comments made by the user");

            List<Review> reviews = reviewService.getReviewsByMovieId(testMovieId);
            assertFalse(reviews.isEmpty(), "Should retrieve reviews for the movie");
        }

        @Test
        @Order(4)
        public void testFriendAndRecentlyViewedFlow() {
            // Add a friend to the user
            assertTrue(friendService.addFriend(testUserId, 2), "Adding a friend should succeed"); // Assuming there's another user with ID 2

            // Add the movie to recently viewed for the test user
            assertTrue(recentlyViewedService.addRecentlyViewedMovie(new User(testUserId, null, null), new Movie(testMovieId, null, 0, null, null, 0.0)),
                    "Adding a movie to recently viewed should succeed");

            // Retrieve and validate friends and recently viewed movies
            List<Integer> friendsList = friendService.getFriends(testUserId);
            assertFalse(friendsList.isEmpty(), "The user should have at least one friend");

            List<Movie> recentlyViewedMovies = recentlyViewedService.getRecentlyViewedById(testUserId);
            assertTrue(recentlyViewedMovies.isEmpty(), "Should not retrieve recently viewed movies for the user");
        }

        @AfterAll
        public static void cleanup() {
            // Clean up the database state if necessary, removing any data you inserted for tests
            // This might include deleting the test user, movies, comments, reviews, etc.
        }

}