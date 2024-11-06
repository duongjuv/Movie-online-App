package com.example.movieonlineapp.domain.controller;

import com.example.movieonlineapp.domain.model.Movie;

import java.util.List;

public interface SearchController {
    List<Movie> getMovieSearchByName(List<Movie> list, String query);
}
