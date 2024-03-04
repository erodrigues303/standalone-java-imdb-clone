package Services;

import com.sun.tools.javac.Main;
import java.sql.*;



public class DbFunctions {
    public static void main(String[] args) {
        DbFunctions db=new DbFunctions();
        db.connect_to_db("MovieManager","postgres","postgres");
    }

    public Connection connect_to_db(String dbname, String user, String pass) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
            if (conn != null) {
                System.out.println("Connection Established");
            } else {
                System.out.println("Connection Failed");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }
}

