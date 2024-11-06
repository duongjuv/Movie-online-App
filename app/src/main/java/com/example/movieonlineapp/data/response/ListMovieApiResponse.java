package com.example.movieonlineapp.data.response;

import com.example.movieonlineapp.domain.model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListMovieApiResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private List<Movie> movies;

    @SerializedName("message")
    private String message;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
