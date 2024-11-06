package com.example.movieonlineapp.data.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WatchHistoryApiResponse implements Serializable {
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<WatchHistoryData> data;

    public WatchHistoryApiResponse(boolean error, String message, List<WatchHistoryData> data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    // Getters và Setters
    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<WatchHistoryData> getData() {
        return data;
    }

    public void setData(List<WatchHistoryData> data) {
        this.data = data;
    }


    // Inner class cho dữ liệu lịch sử xem
    public static class WatchHistoryData implements Serializable {
        @SerializedName("id")
        private int id;

        @SerializedName("user_id")
        private int userId;

        @SerializedName("movie_id")
        private int movieId;

        @SerializedName("watched_at")
        private String watchedAt;

        @SerializedName("Movie")
        private MovieDetail movie;

        public WatchHistoryData(int id, int userId, int movieId, String watchedAt, MovieDetail movie) {
            this.id = id;
            this.userId = userId;
            this.movieId = movieId;
            this.watchedAt = watchedAt;
            this.movie = movie;
        }

        // Getters và Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public String getWatchedAt() {
            return watchedAt;
        }

        public void setWatchedAt(String watchedAt) {
            this.watchedAt = watchedAt;
        }

        public MovieDetail getMovie() {
            return movie;
        }

        public void setMovie(MovieDetail movie) {
            this.movie = movie;
        }
    }

    // Inner class cho chi tiết phim
    public static class MovieDetail implements Serializable {
        @SerializedName("id")
        private int id;

        @SerializedName("title")
        private String title;

        @SerializedName("content")
        private String content;

        @SerializedName("duration")
        private int duration;

        @SerializedName("quality")
        private String quality;

        @SerializedName("rating")
        private double rating;

        @SerializedName("url_image")
        private String urlImage;

        @SerializedName("url_video")
        private String urlVideo;

        @SerializedName("slug")
        private String slug;

        @SerializedName("year")
        private int year;

        public MovieDetail(int id, String title, String content, int duration, String quality, double rating, String urlImage, String urlVideo, String slug, int year) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.duration = duration;
            this.quality = quality;
            this.rating = rating;
            this.urlImage = urlImage;
            this.urlVideo = urlVideo;
            this.slug = slug;
            this.year = year;
        }

        // Getters và Setters
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
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

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }
}
