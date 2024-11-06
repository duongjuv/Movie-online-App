package com.example.movieonlineapp.ui.detail.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieonlineapp.domain.model.Movie;

public class MovieSelectedViewModel extends ViewModel {
    private static MovieSelectedViewModel instance;
    private final MutableLiveData<Movie> _movie = new MutableLiveData<>();
    private final LiveData<Movie> movie = _movie;

    private MovieSelectedViewModel(){

    }
    public static MovieSelectedViewModel getInstance(){
        if(instance == null){
            synchronized (MovieSelectedViewModel.class){
                if(instance == null){
                    instance = new MovieSelectedViewModel();
                }
            }
        }
        return instance;
    }

    public void setMovieData(Movie movie){
        _movie.setValue(movie);
    }

    public LiveData<Movie> getMovieData(){
        return this.movie;
    }
}
