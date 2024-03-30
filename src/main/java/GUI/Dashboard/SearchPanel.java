package GUI.Dashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SearchPanel extends JPanel {
    private JTextField searchField; // For searching by name
    private JTextField genreField; // For searching by genre
    private JButton searchButton;
    private JComboBox<Integer> ratingDropdown; // Dropdown for minimum rating

    public SearchPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); // Adjusted vertical gap

        searchField = new JTextField(20);
        genreField = new JTextField(20);

        searchButton = new JButton("Search");

        Integer[] ratings = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        ratingDropdown = new JComboBox<>(ratings);
        ratingDropdown.setSelectedIndex(0); // Default to the first rating value

        // Add components to the panel
        add(new JLabel("Name:"));
        add(searchField);
        add(new JLabel("Genre:"));
        add(genreField);
        add(new JLabel("Min Rating:"));
        add(ratingDropdown);
        add(searchButton);
    }

    public String getSearchText() {
        return searchField.getText();
    }

    public String getGenreText() {
        return genreField.getText();
    }

    public Integer getSelectedRating() {
        return (Integer) ratingDropdown.getSelectedItem();
    }

    public void addSearchActionListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }
}