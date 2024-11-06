package com.example.movieonlineapp.ui.favourite.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.movieonlineapp.data.repository.Repository;
import com.example.movieonlineapp.ui.home.viewmodel.MovieViewModel;

public class FavMovieViewModelFactory implements ViewModelProvider.Factory{
    private final Repository mRepository;

    public FavMovieViewModelFactory(Repository repository) {
        mRepository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FavMovieViewModel.class)) {
            return (T) new FavMovieViewModel(mRepository);
        } else {
            throw new IllegalArgumentException("Argument is not class FavMovieViewModel");
        }
    }
}
