package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import Models.*;
import Services.MovieManager;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginUI() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                if (authenticateUser(username, password)) {
                    //to be retreived later from DB
                    User demoUser = demoUser(username);
                    JOptionPane.showMessageDialog(LoginUI.this, "Login successful");
                    MovieManager mm = new MovieManager();
                    DashboardUI dash = new DashboardUI(demoUser, mm);
                } else {
                    JOptionPane.showMessageDialog(LoginUI.this, "Invalid username or password");
                }
            }
        });
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    private boolean authenticateUser(String username, String password) {
        File file = new File("src/main/java/GUI/users.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                String[] parts = line.split(",");
                System.out.println(parts[1]);
                System.out.println(password);
                System.out.println(parts[0]);
                System.out.println(username);
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    System.out.println("inside true");
                    return true;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User demoUser(String username){
        User user = new User(username, "password");

        // Add some reviews
        Review review1 = new Review("Great movie!", 5);
        Review review2 = new Review("Not bad", 3);
        user.addReview(review1);
        user.addReview(review2);

        // Add some comments
        Comment comment1 = new Comment("Enjoyed watching it!");
        Comment comment2 = new Comment("Could have been better.");
        user.addComment(comment1);
        user.addComment(comment2);

        // Add some friends
        User friend1 = new User("friend1", "friendPassword");
        User friend2 = new User("friend2", "friendPassword");
        user.addFriend(friend1);
        user.addFriend(friend2);

        // Add some recently viewed movies
        Movie movie1 = new Movie("Movie 1", 2010, "movie 1 description", "horror");
        Movie movie2 = new Movie("Movie 2", 2021, "movie 2 description", "comedy");
        Movie movie3 = new Movie("Movie 3", 2015, "movie 3 description", "action");
        user.addRecentlyViewed(movie1);
        user.addRecentlyViewed(movie2);
        user.addRecentlyViewed(movie3);

        return user;
    }


}

