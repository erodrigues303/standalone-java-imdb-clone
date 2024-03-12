package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendService {

    // Method to add a friend for a user
    public boolean addFriend(int userId, int friendId) {
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
}