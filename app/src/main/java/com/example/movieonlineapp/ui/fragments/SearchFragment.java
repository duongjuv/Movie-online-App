package com.example.movieonlineapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieonlineapp.DataLocalManager;
import com.example.movieonlineapp.MyApplication;
import com.example.movieonlineapp.R;
import com.example.movieonlineapp.data.repository.Repository;
import com.example.movieonlineapp.data.response.ListMovieApiResponse;
import com.example.movieonlineapp.data.response.MovieApiResponse;
import com.example.movieonlineapp.data.source.SelectedDataSource;
import com.example.movieonlineapp.data.source.local.LocalDataSource;
import com.example.movieonlineapp.data.source.remote.RemoteDataSource;
import com.example.movieonlineapp.domain.model.Movie;
import com.example.movieonlineapp.ui.detail.activity.DetailMovieActivity;
import com.example.movieonlineapp.ui.detail.viewmodel.MovieItemViewModel;
import com.example.movieonlineapp.ui.home.viewmodel.MovieViewModel;
import com.example.movieonlineapp.ui.home.viewmodel.MovieViewModelFactory;
import com.example.movieonlineapp.ui.search.adapter.SearchMovieAdapter;
import com.example.movieonlineapp.utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;


public class SearchFragment extends Fragment {
    private EditText edtSearchMovie;
    private RecyclerView recyclerView;
    private MovieViewModel mViewModel;
    private SearchMovieAdapter adapter;

    private View mView;
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(mView);
        setupViewModels();
    }

    private void setupViews(View mView) {
        edtSearchMovie = mView.findViewById(R.id.edtSearchMovie);
        recyclerView = mView.findViewById(R.id.recyclerViewMovies);
        adapter = new SearchMovieAdapter(requireContext(), new ArrayList<>(), this::createDetailMovie);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        searchMovie();
    }

    private void searchMovie() {
        edtSearchMovie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String query = charSequence.toString().trim();
                if (!query.isEmpty()) {
                    performSearch(query);
                } else {
                    // Nếu query trống, có thể hiển thị tất cả phim
                    performSearch("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // Hàm thực hiện tìm kiếm
    private void performSearch(String query) {
        mViewModel.searchMovies(query); // Gọi phương thức trong ViewModel để lọc dữ liệu
    }

    private void createDetailMovie(Movie movie) {
        Intent intent = new Intent(requireContext(), DetailMovieActivity.class);
        DataLocalManager.getInstance().
                mySharedPreferences.putValue(DataLocalManager.ID_MOVIE, String.valueOf(movie.getId()));

        SelectedDataSource selectedDataSource = Utils.checkInternetState(requireContext());
        ExecutorService executorService =
                ((MyApplication) requireActivity().getApplication()).homeFragmentExecutorService;
        RemoteDataSource<MovieApiResponse> remoteDataSource =
                new RemoteDataSource<>(MovieApiResponse.class, executorService);
        LocalDataSource localDataSource = new LocalDataSource();
        Repository repository = new Repository.Builder()
                .setSelectedDataSource(selectedDataSource)
                .setLocalDataSource(localDataSource)
                .setRemoteDataSource(remoteDataSource)
                .build();
        Utils.selectedDataSource = selectedDataSource;
        MovieItemViewModel model = MovieItemViewModel.getInstance(repository);
        model.loadData();
        startActivity(intent);
    }

    private void setupViewModels() {
        // Setup ViewModel
        SelectedDataSource selectedDataSource = Utils.checkInternetState(requireContext());
        ExecutorService executorService =
                ((MyApplication) requireActivity().getApplication()).homeFragmentExecutorService;
        RemoteDataSource<ListMovieApiResponse> remoteDataSource =
                new RemoteDataSource<>(ListMovieApiResponse.class, executorService);
        LocalDataSource localDataSource = new LocalDataSource();
        Repository repository = new Repository.Builder()
                .setSelectedDataSource(selectedDataSource)
                .setLocalDataSource(localDataSource)
                .setRemoteDataSource(remoteDataSource)
                .build();
        Utils.selectedDataSource = selectedDataSource;
        mViewModel = new ViewModelProvider(this,
                new MovieViewModelFactory(repository)).get(MovieViewModel.class);

        // Quan sát dữ liệu tất cả các phim
        mViewModel.getAllMovies().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                adapter.updateData(movies);
            } else {
                Toast.makeText(requireContext(), "No all movies found", Toast.LENGTH_SHORT).show();
            }
        });

        // Quan sát dữ liệu tìm kiếm
        mViewModel.getSearchMovies().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                adapter.updateData(movies);  // Cập nhật adapter với dữ liệu tìm kiếm
            } else {
                Toast.makeText(requireContext(), "No search movies found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
