package Models;

import java.util.*;

public class Movie {
    private String title;
    private int releaseYear;
    private String description;
    private String genre;
    private Double rating;
    private List<Double> rating_list;
    private List<Review> reviews;
    private String coverImageUrl;

    public Movie(String title, int releaseYear, String description, String genre) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.description = description;
        this.genre = genre;
        this.rating = 0.0;
        this.rating_list = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Double> getRating_list() {
        return rating_list;
    }

    public void setRating_list(List<Double> rating_list) {
        this.rating_list = rating_list;
    }

    public void addReview(Review review){
        this.reviews.add(review);
    }

    public void addRating(Double rating){
       this.rating_list.add(rating);
       this.rating = rating_list.stream().mapToDouble(a -> a).average().orElse(0.0);
    }

    public void setCoverImageUrl(String poster) {
        this.coverImageUrl = poster;
    }

    public String getCoverImageUrl() {
        return this.coverImageUrl;
    }
}
