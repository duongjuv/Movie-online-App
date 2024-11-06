package com.example.movieonlineapp.data.response;

public class WatchHistoryResponseRegister {
    private boolean error;
    private String message;
    private WatchHistoryData data;

    public WatchHistoryResponseRegister(boolean error, String message, WatchHistoryData data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    // Getter và Setter
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

    public WatchHistoryData getData() {
        return data;
    }

    public void setData(WatchHistoryData data) {
        this.data = data;
    }
    public static class WatchHistoryData {
        private int id;
        private int userId;
        private int movieId;
        private String watchedAt;

        public WatchHistoryData(int id, int userId, int movieId, String watchedAt) {
            this.id = id;
            this.userId = userId;
            this.movieId = movieId;
            this.watchedAt = watchedAt;
        }

        // Getter và Setter
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
    }
}
