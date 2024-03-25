package Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbFunctions {

    // Private constructor to prevent instantiation.
    private DbFunctions() {
    }

    public static Connection connect() {
        // Assuming dbPath is your SQLite database path.
        String dbPath = "/Users/ahmedel-dib/Desktop/2311/src/main/database/movie.db";
        try {
            // Create a new connection to the database.
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            return connection;
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }
}
