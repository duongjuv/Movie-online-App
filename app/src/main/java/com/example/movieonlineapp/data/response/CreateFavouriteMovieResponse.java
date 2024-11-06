package com.example.movieonlineapp.data.response;

import com.google.gson.annotations.SerializedName;

public class CreateFavouriteMovieResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;

    public static class Data {
        @SerializedName("id")
        private int id;

        @SerializedName("user_id")
        private int userId;

        @SerializedName("movie_id")
        private int movieId;

        // Getters and setters for Data class
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
    }

    // Getters and setters for CreateFavouriteMovieResponse class
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
