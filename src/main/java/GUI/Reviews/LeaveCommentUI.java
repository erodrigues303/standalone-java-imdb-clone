package GUI.Reviews;

import Models.Comment;
import Models.Review;
import Models.User;
import Services.CommentService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaveCommentUI extends JFrame {
    private final JPanel contentPanel;
    private final Review review;
    private final User user;

    public LeaveCommentUI(User user, Review review) {
        this.user = user;
        this.review = review;
        setTitle("Leave Comment");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 200));
        setLocationRelativeTo(null);
        contentPanel = new JPanel(new BorderLayout());
        JPanel headerPanel = createHeaderPanel();
        JPanel commentPanel = createCommentPanel();
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(commentPanel, BorderLayout.CENTER);
        add(contentPanel);
        pack();
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel welcomeLabel = new JLabel("Leave a comment");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headerPanel.add(welcomeLabel);
        return headerPanel;
    }

    private JPanel createCommentPanel() {
        JPanel commentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextArea commentAera = new JTextArea(4, 20);
        commentAera.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JButton publishButton = new JButton("Post");
        publishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String commentText = commentAera.getText();
                Comment comment = new Comment(0, user.getUserId(), commentText, 0, 0, review.getReviewId());
                createComment(user.getUserId(), comment);
                commentAera.setText("");
                openCommentsUI(review, user);
            }
        });
        commentPanel.add(commentAera);
        commentPanel.add(publishButton);
        return commentPanel;
    }

    public void openCommentsUI(Review review, User user) {
        CommentsUI commentsUI = new CommentsUI(review, user);
        commentsUI.setVisible(true);
    }

    public void createComment(int userId, Comment comment) {
        CommentService commentService = new CommentService();
        commentService.addComment(userId, comment);
    }
}
