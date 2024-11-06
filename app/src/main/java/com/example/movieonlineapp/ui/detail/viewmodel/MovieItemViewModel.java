package com.example.movieonlineapp.ui.detail.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieonlineapp.data.repository.Repository;

import com.example.movieonlineapp.data.response.MovieApiResponse;
import com.example.movieonlineapp.data.source.DataSource;
import com.example.movieonlineapp.data.source.ResponseResult;


public class MovieItemViewModel extends ViewModel {
    private final MutableLiveData<MovieApiResponse.MovieData> _muMovieMutableLiveData = new MutableLiveData<>();
    private final LiveData<MovieApiResponse.MovieData> movieLiveData = _muMovieMutableLiveData;
    private static MovieItemViewModel instance;
    private Repository mRepository;


    private MovieItemViewModel(Repository repository){
        this.mRepository = repository;
        loadData();
    }
    public static MovieItemViewModel getInstance(Repository repository){
        if(instance == null){
            synchronized (MovieItemViewModel.class){
                if(instance == null){
                    instance = new MovieItemViewModel(repository);
                }
            }
        }
        return instance;
    }
    @SuppressWarnings("unchecked")
    public void loadData(){
        DataSource.DataSourceCallback callback = result -> {
            if (result instanceof ResponseResult.Success) {
                MovieApiResponse response =
                        ((ResponseResult.Success<MovieApiResponse>)result).data;
                MovieApiResponse.MovieData data = response.getMovieData();
                if (data != null) {
                    _muMovieMutableLiveData.postValue(data);
                } else {
                    _muMovieMutableLiveData.postValue(new MovieApiResponse.MovieData());
                }
            } else {
                _muMovieMutableLiveData.postValue(new MovieApiResponse.MovieData());
            }
        };
        mRepository.loadData(callback);
        _muMovieMutableLiveData.postValue(new MovieApiResponse.MovieData());
    }

    public void setMovieSelectedData(MovieApiResponse.MovieData movie){
        _muMovieMutableLiveData.setValue(movie);
    }

    public LiveData<MovieApiResponse.MovieData> getMovieSelected(){
        return movieLiveData;
    }
}
