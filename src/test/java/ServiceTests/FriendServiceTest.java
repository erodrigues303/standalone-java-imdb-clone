package ServiceTests;

import Models.User;
import Services.FriendService;
import Services.UserService;
import org.junit.jupiter.api.Test;

import static Services.FriendService.*;
import static org.junit.jupiter.api.Assertions.*;

public class FriendServiceTest {

    @Test
    public void testAddFriend() {
        assertTrue(addFriend(3, 2));
        FriendService.removeFriend(3,2);
    }

    @Test
    public void testRemoveFriend() {
        FriendService.addFriend(3,2);
        assertTrue(FriendService.removeFriend(3, 2));
    }

    @Test
    public void testGetFriends() {
        FriendService.addFriend(3, 2);
        assertTrue(getFriends(3).contains(2));
        removeFriend(3,2);
    }

    @Test
    public void testSendFriendRequest() {
        assertTrue(FriendService.sendFriendRequest(3, 2));
    }

    @Test
    public void testGetReceivedFriendRequests() {
        FriendService.sendFriendRequest(1,2);
        assertTrue(getReceivedFriendRequestsFriendIds(2).contains(1));
        removeFriendRequest(1,2);

    }

    @Test
    public void testAcceptFriendRequest() {
        sendFriendRequest(3,2);
        int requestId = getReceivedFriendRequests(2).get(0);
        assertTrue(acceptFriendRequest(requestId));
        removeFriend(3,2);
    }

    @Test
    public void testDeclineFriendRequest() {
        int requestId = getReceivedFriendRequests(2).get(0);
        assertTrue(declineFriendRequest(requestId));
    }

    @Test
    public void testGetSenderUsername() {
        FriendService.sendFriendRequest(3,2);
        int requestId = getReceivedFriendRequests(2).get(0);
        assertEquals(UserService.getUserById(3).getUsername(), getSenderUsername(requestId));
        FriendService.declineFriendRequest(requestId);
    }

    @Test
    public void testGetSenderID() {
        FriendService.sendFriendRequest(1,2);
        int requestId = getReceivedFriendRequests(2).get(0);
        assertEquals(1, getSenderID(requestId));
        FriendService.removeFriendRequest(1,2);
    }
}
