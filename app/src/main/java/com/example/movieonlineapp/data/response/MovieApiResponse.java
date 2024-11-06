package com.example.movieonlineapp.data.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieApiResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private MovieData movieData; 

    @SerializedName("message")
    private String message;

    // Getter and Setter methods
    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public MovieData getMovieData() {
        return movieData;
    }

    public void setMovieData(MovieData movieData) {
        this.movieData = movieData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public String toString() {
        return "MovieApiResponse{" +
                "error=" + error +
                ", movieData=" + movieData +
                ", message='" + message + '\'' +
                '}';
    }

    // MovieData class (thay đổi từ Movie)
    public static class MovieData {
        @SerializedName("id")
        private int id;

        @SerializedName("title")
        private String title;

        @SerializedName("year")
        private int year;

        @SerializedName("content")
        private String content;

        @SerializedName("quality")
        private String quality;

        @SerializedName("duration")
        private int duration;

        @SerializedName("rating")
        private int rating;

        @SerializedName("url_image")
        private String urlImage;

        @SerializedName("url_video")
        private String urlVideo;

        @SerializedName("slug")
        private String slug;

        @SerializedName("account_can_view")
        private String accountCanView;

        @SerializedName("createdAt")
        private String createdAt;

        @SerializedName("updatedAt")
        private String updatedAt;

        @SerializedName("deletedAt")
        private String deletedAt;

        @SerializedName("categories")
        private List<Category> categories;

        @SerializedName("actors")
        private List<Actor> actors;

        @SerializedName("comments")
        private List<Comment> comments;

        @SerializedName("average_rating")
        private String averageRating;

        public String getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(String averageRating) {
            this.averageRating = averageRating;
        }

        // Getter and Setter methods
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getUrlImage() {
            return urlImage;
        }

        public void setUrlImage(String urlImage) {
            this.urlImage = urlImage;
        }

        public String getUrlVideo() {
            return urlVideo;
        }

        public void setUrlVideo(String urlVideo) {
            this.urlVideo = urlVideo;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getAccountCanView() {
            return accountCanView;
        }

        public void setAccountCanView(String accountCanView) {
            this.accountCanView = accountCanView;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(String deletedAt) {
            this.deletedAt = deletedAt;
        }

        public List<Category> getCategories() {
            return categories;
        }

        public void setCategories(List<Category> categories) {
            this.categories = categories;
        }

        public List<Actor> getActors() {
            return actors;
        }

        public void setActors(List<Actor> actors) {
            this.actors = actors;
        }

        public List<Comment> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
        }

        @NonNull
        @Override
        public String toString() {
            return "MovieData{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", year=" + year +
                    ", content='" + content + '\'' +
                    ", quality='" + quality + '\'' +
                    ", duration=" + duration +
                    ", rating=" + rating +
                    ", urlImage='" + urlImage + '\'' +
                    ", urlVideo='" + urlVideo + '\'' +
                    ", slug='" + slug + '\'' +
                    ", accountCanView='" + accountCanView + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", updatedAt='" + updatedAt + '\'' +
                    ", deletedAt='" + deletedAt + '\'' +
                    ", categories=" + categories +
                    ", actors=" + actors +
                    ", comments=" + comments +
                    '}';
        }
    }

    // Category class
    public static class Category {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @SerializedName("slug")
        private String slug;

        // Getter and Setter methods
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        @NonNull
        @Override
        public String toString() {
            return "Category{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", slug='" + slug + '\'' +
                    '}';
        }
    }

    // Actor class
    public static class Actor {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        // Getter and Setter methods
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    // Comment class
    public static class Comment {
        @SerializedName("id")
        private int id;

        @SerializedName("content")
        private String content;

        @SerializedName("rating")
        private int rating;

        @SerializedName("user")
        private User user;

        // Getter and Setter methods
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        @NonNull
        @Override
        public String toString() {
            return "Comment{" +
                    "id=" + id +
                    ", content='" + content + '\'' +
                    ", rating=" + rating +
                    ", user=" + user +
                    '}';
        }
    }

    // User class
    public static class User {
        @SerializedName("id")
        private int id;

        @SerializedName("full_name")
        private String fullName;

        @SerializedName("email")
        private String email;

        // Constructor
        public User(int id, String fullName, String email) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
        }

        // Getter and Setter methods
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
