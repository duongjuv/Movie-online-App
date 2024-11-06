package com.example.movieonlineapp.data.request;

public class FavouriteRequest {
    private int userId;
    private int movieId;

    public FavouriteRequest(int userId, int movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }
}
