package GUI.Dashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SearchPanel extends JPanel {
    private JTextField searchField;
    private JButton searchButton;

    public SearchPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0)); // Small horizontal gap, no vertical gap
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        add(searchField);
        add(searchButton);
    }

    public String getSearchText() {
        return searchField.getText();
    }

    public void addSearchActionListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }
}
