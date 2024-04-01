package GUI.Reviews;

import Models.Comment;
import Models.Review;
import Models.User;
import Services.CommentService;
import Services.ReviewService;
import Services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CommentsUI extends JFrame {
    private JTextArea parentReviewArea;
    private JTextArea commentTextArea;
    private UserService userService;
    private User user;

    public CommentsUI(Review review, User user) {
        this.user = user;
        setTitle("Comment Section");
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        parentReviewArea = new JTextArea();
        parentReviewArea.setEditable(false);
        commentTextArea = new JTextArea();
        JButton addCommentButton = new JButton("Add Comment");
        userService = new UserService();
        addCommentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLeaveCommentUI(review, user);
                dispose();
            }
        });

        JPanel commentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Arrange components horizontally
        commentPanel.add(commentTextArea);
        commentPanel.add(addCommentButton);

        setLayout(new BorderLayout());
        add(new JScrollPane(parentReviewArea), BorderLayout.NORTH);
        add(new JScrollPane(commentTextArea), BorderLayout.CENTER);
        add(commentPanel, BorderLayout.SOUTH);
        displayParentReview(review.getReviewId());
        displayComments(review.getReviewId());
    }

    private void displayParentReview(int reviewId) {
        ReviewService reviewService = new ReviewService();
        try {
            Review parentReview = reviewService.getReviewByReviewId(reviewId);
            parentReviewArea.append("Review: " + parentReview.getReviewText() + "\n\n");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching parent review: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayComments(int reviewId) {
        commentTextArea.setText("");
        CommentService commentService = new CommentService();
        try {
            List<Comment> comments = commentService.getCommentsByReviewId(reviewId);
            for (Comment comment : comments) {
                commentTextArea.append("User: " + commentService.getUsernameByComment(comment) + "\n");
                commentTextArea.append(comment.getCommentText() + "\n\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching reviews: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openLeaveCommentUI(Review review, User user) {
        LeaveCommentUI leaveCommentUIUI = new LeaveCommentUI(user, review);
        leaveCommentUIUI.setVisible(true);
    }
}