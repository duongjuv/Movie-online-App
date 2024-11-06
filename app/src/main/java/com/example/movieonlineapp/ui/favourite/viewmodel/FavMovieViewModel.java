package com.example.movieonlineapp.ui.favourite.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieonlineapp.data.repository.Repository;
import com.example.movieonlineapp.data.response.ListFavouriteMovieResponse;
import com.example.movieonlineapp.data.response.ListMovieApiResponse;
import com.example.movieonlineapp.data.source.DataSource;
import com.example.movieonlineapp.data.source.ResponseResult;
import com.example.movieonlineapp.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavMovieViewModel extends ViewModel {
    private final Repository mRepository;
    private final MutableLiveData<List<ListFavouriteMovieResponse.FavouriteMovie>> _movies = new MutableLiveData<>();
    private final LiveData<List<ListFavouriteMovieResponse.FavouriteMovie>> movies = _movies;

    public FavMovieViewModel(Repository repository) {
        this.mRepository = repository;
        loadData();
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        DataSource.DataSourceCallback callback = result -> {
            if (result instanceof ResponseResult.Success) {
                List<ListFavouriteMovieResponse.FavouriteMovie> data
                        = ((ResponseResult.Success<ListFavouriteMovieResponse>) result).data.getData();
                if (!data.isEmpty()) {
                    _movies.postValue(data);
                } else {
                    _movies.postValue(new ArrayList<>());
                }
            } else {
                _movies.postValue(new ArrayList<>());
            }
        };
        mRepository.loadData(callback);
        _movies.postValue(new ArrayList<>());
    }

    public LiveData<List<ListFavouriteMovieResponse.FavouriteMovie>> getData() {
        return movies;
    }
}
