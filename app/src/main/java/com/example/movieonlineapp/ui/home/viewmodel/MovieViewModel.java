package com.example.movieonlineapp.ui.home.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieonlineapp.data.repository.Repository;
import com.example.movieonlineapp.data.response.ListMovieApiResponse;
import com.example.movieonlineapp.data.source.DataSource;
import com.example.movieonlineapp.data.source.ResponseResult;
import com.example.movieonlineapp.domain.controller.SearchController;
import com.example.movieonlineapp.domain.controller.SearchControllerImpl;
import com.example.movieonlineapp.domain.model.Movie;
import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends ViewModel {
    private final Repository mRepository;
    private final MutableLiveData<List<Movie>> _allMovies = new MutableLiveData<>();
    private final LiveData<List<Movie>> allMovies = _allMovies;

    // Thêm MutableLiveData cho phim VIP
    private final MutableLiveData<List<Movie>> _vipMovies = new MutableLiveData<>();
    private final LiveData<List<Movie>> vipMovies = _vipMovies;

    // thêm MutableLive cho tìm kiếm phim
    private final MutableLiveData<List<Movie>> _searchMovies = new MutableLiveData<>();
    private final LiveData<List<Movie>> searchMovies = _searchMovies;
    // Constructor của ViewModel
    public MovieViewModel(Repository repository) {
        this.mRepository = repository;
        loadData();  // Load dữ liệu khi ViewModel được tạo
    }

    // Phương thức load dữ liệu từ Repository
    @SuppressWarnings("unchecked")
    private void loadData() {
        DataSource.DataSourceCallback callback = result -> {
            if (result instanceof ResponseResult.Success) {
                List<Movie> data = ((ResponseResult.Success<ListMovieApiResponse>) result).data.getMovies();
                if (!data.isEmpty()) {
                    _allMovies.postValue(data);
                    filterVipMovies(data);

                } else {
                    _allMovies.postValue(new ArrayList<>());
                }
            } else {
                _allMovies.postValue(new ArrayList<>());
            }
        };
        mRepository.loadData(callback);  // Gọi repository để lấy dữ liệu phim
    }

    // Phương thức lấy dữ liệu tất cả các phim
    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }

    // Phương thức lấy dữ liệu phim VIP
    public LiveData<List<Movie>> getVipMovies() {
        return vipMovies;
    }


    private void filterVipMovies(List<Movie> allMoviesList) {
        List<Movie> vipMovieList = new ArrayList<>();
        for (Movie movie : allMoviesList) {
            if (movie.getTypeAccount().equals("vip")) {
                vipMovieList.add(movie);
            }
        }
        _vipMovies.setValue(vipMovieList);

    }
    // Phương thức lấy dữ liệu tìm kiếm phim
    public LiveData<List<Movie>> getSearchMovies() {
        return searchMovies;
    }

    public void searchMovies(String query) {
        List<Movie> allMoviesList = _allMovies.getValue();
        if (allMoviesList != null) {
            List<Movie> filteredMovies = filterSearchMovies(allMoviesList, query);
            _searchMovies.setValue(filteredMovies);
        }
    }

    // Lọc phim theo query
    private List<Movie> filterSearchMovies(List<Movie> allMoviesList, String query) {
        SearchControllerImpl searchController = SearchControllerImpl.getInstance();
        return searchController.getMovieSearchByName(allMoviesList, query);
    }
}
