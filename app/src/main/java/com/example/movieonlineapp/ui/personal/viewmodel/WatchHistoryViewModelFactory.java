package com.example.movieonlineapp.ui.personal.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.movieonlineapp.data.repository.Repository;


public class WatchHistoryViewModelFactory implements ViewModelProvider.Factory{
    private final Repository mRepository;

    public WatchHistoryViewModelFactory(Repository repository) {
        mRepository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WatchHistoryViewModel.class)) {
            return (T) new WatchHistoryViewModel(mRepository);
        } else {
            throw new IllegalArgumentException("Argument is not class WatchHistoryViewModel");
        }
    }
}
