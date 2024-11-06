package com.example.movieonlineapp.ui.personal.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieonlineapp.data.repository.Repository;
import com.example.movieonlineapp.data.response.WatchHistoryApiResponse;
import com.example.movieonlineapp.data.source.DataSource;
import com.example.movieonlineapp.data.source.ResponseResult;

import java.util.ArrayList;
import java.util.List;

public class WatchHistoryViewModel extends ViewModel {

    private final Repository mRepository;
    private final MutableLiveData<List<WatchHistoryApiResponse.WatchHistoryData>> _liveDataHistory =
            new MutableLiveData<>();
    private final LiveData<List<WatchHistoryApiResponse.WatchHistoryData>> liveData = _liveDataHistory;

    public WatchHistoryViewModel(Repository repository) {
        this.mRepository = repository;
        loadData();
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        DataSource.DataSourceCallback callback = result -> {
            if (result instanceof ResponseResult.Success) {
                WatchHistoryApiResponse watchHistoryApiResponses
                        = ((ResponseResult.Success<WatchHistoryApiResponse>) result).data;
                List<WatchHistoryApiResponse.WatchHistoryData> data =
                        watchHistoryApiResponses.getData();
                if (!data.isEmpty()) {
                    _liveDataHistory.postValue(data);
                } else {
                    _liveDataHistory.postValue(new ArrayList<>());
                }
            } else {
                _liveDataHistory.postValue(new ArrayList<>());
            }
        };
        mRepository.loadData(callback);
    }

    public LiveData<List<WatchHistoryApiResponse.WatchHistoryData>> getData() {
        return liveData;
    }

}
