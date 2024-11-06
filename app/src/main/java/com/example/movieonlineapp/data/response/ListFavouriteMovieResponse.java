package com.example.movieonlineapp.data.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListFavouriteMovieResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<FavouriteMovie> data;

    // Getter và Setter cho ListFavouriteMovieResponse
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

    public List<FavouriteMovie> getData() {
        return data;
    }

    public void setData(List<FavouriteMovie> data) {
        this.data = data;
    }

    // Inner class đại diện cho FavouriteMovie
    public static class FavouriteMovie {

        @SerializedName("id")
        private int id;

        @SerializedName("user_id")
        private int user_id;

        @SerializedName("movie_id")
        private int movie_id;

        @SerializedName("Movie")
        private Movie Movie;

        // Getter và Setter cho FavouriteMovie
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getMovie_id() {
            return movie_id;
        }

        public void setMovie_id(int movie_id) {
            this.movie_id = movie_id;
        }

        public Movie getMovie() {
            return Movie;
        }

        public void setMovie(Movie movie) {
            this.Movie = movie;
        }

        // Inner class đại diện cho Movie
        public static class Movie {

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
            private int rating;

            @SerializedName("url_image")
            private String url_image;

            @SerializedName("url_video")
            private String url_video;

            @SerializedName("slug")
            private String slug;

            @SerializedName("year")
            private int year;

            // Getter và Setter cho Movie
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

            public int getRating() {
                return rating;
            }

            public void setRating(int rating) {
                this.rating = rating;
            }

            public String getUrl_image() {
                return url_image;
            }

            public void setUrl_image(String url_image) {
                this.url_image = url_image;
            }

            public String getUrl_video() {
                return url_video;
            }

            public void setUrl_video(String url_video) {
                this.url_video = url_video;
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
}
