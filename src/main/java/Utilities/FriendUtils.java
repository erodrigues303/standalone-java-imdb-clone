// package Utilities;

// import java.util.List;

// import Models.User;
// import Services.FriendService;
// import Services.UserService;
// import GUI.Dashboard.FriendsPanel;

// public class FriendUtils {

// static UserService userService = new UserService();

// public static void updateFriendsList(User user) {
// List<Integer> friendIds = FriendService.getFriends(user.getUserId());
// StringBuilder sb = new StringBuilder();
// for (int friendId : friendIds) {
// // Fetch friend's name based on their ID
// User friend = userService.getUserById(friendId);
// if (friend != null) {
// sb.append(friend.getUsername()).append("\n");
// }
// }
// FriendsPanel.friendsListArea.setText(sb.toString());
// }
// }
