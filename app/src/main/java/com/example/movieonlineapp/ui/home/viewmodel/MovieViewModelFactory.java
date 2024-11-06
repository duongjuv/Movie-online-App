package com.example.movieonlineapp.ui.home.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.movieonlineapp.data.repository.Repository;

public class MovieViewModelFactory implements ViewModelProvider.Factory {
    private final Repository mRepository;

    public MovieViewModelFactory(Repository repository) {
        mRepository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            return (T) new MovieViewModel(mRepository);
        } else {
            throw new IllegalArgumentException("Argument is not class MovieViewModel");
        }
    }
}
