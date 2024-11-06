package com.example.movieonlineapp.data.request;

public class CommentRequest {
    private int userId;
    private int movieId;
    private int rating;
    private String content;

    public CommentRequest(int userId, int movieId, int rating, String content) {
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
        this.content = content;
    }
}
