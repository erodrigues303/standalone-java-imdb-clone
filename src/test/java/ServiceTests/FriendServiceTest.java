package ServiceTests;

import Models.User;
import Services.FriendService;
import Services.UserService;
import org.junit.jupiter.api.Test;

import static Services.FriendService.*;
import static org.junit.jupiter.api.Assertions.*;

public class FriendServiceTest {
 FriendService friendService= new FriendService();
    @Test
    public void testAddFriend() {
        assertTrue(addFriend(3, 2));
        friendService.removeFriend(3,2);
    }

    @Test
    public void testRemoveFriend() {
        FriendService.addFriend(3,2);
        assertTrue(friendService.removeFriend(3, 2));
    }

    @Test
    public void testGetFriends() {
        FriendService.addFriend(3, 2);
        assertTrue(getFriends(3).contains(2));
        friendService.removeFriend(3,2);
    }

    @Test
    public void testSendFriendRequest() {
        assertTrue(friendService.sendFriendRequest(3, 2));
    }

    @Test
    public void testGetReceivedFriendRequests() {
        friendService.sendFriendRequest(3,2);
        assertTrue(friendService.getReceivedFriendRequests(2).contains(3));
        removeFriendRequest(3,2);

    }

    @Test
    public void testAcceptFriendRequest() {
        friendService.sendFriendRequest(3,2);
        int requestId = getReceivedFriendRequests(2).get(0);
        assertTrue(acceptFriendRequest(requestId));
        friendService.removeFriend(3,2);
    }

    @Test
    public void testDeclineFriendRequest() {
        int requestId = getReceivedFriendRequests(2).get(0);
        assertTrue(declineFriendRequest(requestId));
    }

    @Test
    public void testGetSenderUsername() {
        friendService.sendFriendRequest(3,2);
        int requestId = getReceivedFriendRequests(2).get(0);
        assertEquals(UserService.getUserById(3).getUsername(), getSenderUsername(requestId));
        friendService.declineFriendRequest(requestId);
    }

    @Test
    public void testGetSenderID() {
        friendService.sendFriendRequest(1,2);
        int requestId = getReceivedFriendRequests(2).get(0);
        assertEquals(1, getSenderID(requestId));
        friendService.removeFriendRequest(1,2);
    }
}
