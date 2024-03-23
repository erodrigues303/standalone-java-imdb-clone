package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendService {

    // Method to add a friend for a user
    public static boolean addFriend(int userId, int friendId) {
        String sql = "INSERT INTO Friends (user_id, friend_id) VALUES (?, ?)";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, friendId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to remove a friend for a user
    public boolean removeFriend(int userId, int friendId) {
        String sql = "DELETE FROM Friends WHERE user_id = ? AND friend_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, friendId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve friends for a user
    public List<Integer> getFriends(int userId) {
        List<Integer> friends = new ArrayList<>();
        String sql = "SELECT friend_id FROM Friends WHERE user_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int friendId = rs.getInt("friend_id");
                friends.add(friendId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }
    public boolean sendFriendRequest(int senderId, int receiverId) {
        String sql = "INSERT INTO FriendRequests (sender_id, receiver_id, status) VALUES (?, ?, ?)";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, senderId);
            pstmt.setInt(2, receiverId);
            pstmt.setString(3, "PENDING");
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Integer> getReceivedFriendRequests(int userId) {
        List<Integer> requestIds = new ArrayList<>();
        String sql = "SELECT request_id FROM FriendRequests WHERE receiver_id = ? AND status = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, "PENDING");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                requestIds.add(rs.getInt("request_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requestIds;
    }

    public static boolean acceptFriendRequest(int requestId) {
        String sql = "UPDATE FriendRequests SET status = ? WHERE request_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "ACCEPTED");
            pstmt.setInt(2, requestId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void declineFriendRequest(int senderUserId, int UserId) {
        String sql = "UPDATE FriendRequests SET status = ? WHERE sender_id = ? AND receiver_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "DECLINED");
            pstmt.setInt(2, senderUserId);
            pstmt.setInt(3,UserId);
            int rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getSenderUsername(int requestId) {
        String senderUsername = null;
        String sql = "SELECT sender_id FROM FriendRequests WHERE request_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, requestId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int senderID = rs.getInt("sender_id");
                senderUsername = getUsernameByUserId(senderID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return senderUsername;
    }

    private static String getUsernameByUserId(int userId) {
        String username = null;
        String sql = "SELECT username FROM Users WHERE user_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                username = rs.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }
    public static int getSenderID(int requestId) {
        int senderID= 0;
        String sql = "SELECT sender_id FROM FriendRequests WHERE request_id = ?";
        try (Connection conn = DbFunctions.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, requestId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                senderID = rs.getInt("sender_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return senderID;
    }

}