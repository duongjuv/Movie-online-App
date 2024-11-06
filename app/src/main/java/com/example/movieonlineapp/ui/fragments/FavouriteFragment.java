package com.example.movieonlineapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieonlineapp.DataLocalManager;
import com.example.movieonlineapp.MyApplication;
import com.example.movieonlineapp.R;
import com.example.movieonlineapp.data.repository.Repository;
import com.example.movieonlineapp.data.response.ListFavouriteMovieResponse;
import com.example.movieonlineapp.data.response.ListMovieApiResponse;
import com.example.movieonlineapp.data.response.MovieApiResponse;
import com.example.movieonlineapp.data.source.SelectedDataSource;
import com.example.movieonlineapp.data.source.local.LocalDataSource;
import com.example.movieonlineapp.data.source.remote.RemoteDataSource;
import com.example.movieonlineapp.domain.model.Movie;
import com.example.movieonlineapp.ui.detail.activity.DetailMovieActivity;
import com.example.movieonlineapp.ui.detail.viewmodel.MovieItemViewModel;
import com.example.movieonlineapp.ui.favourite.adapter.FavouriteMovieAdapter;
import com.example.movieonlineapp.ui.favourite.viewmodel.FavMovieViewModel;
import com.example.movieonlineapp.ui.favourite.viewmodel.FavMovieViewModelFactory;

import com.example.movieonlineapp.utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class FavouriteFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FavouriteMovieAdapter adapter;
    private View mView;
    private FavMovieViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_favourite, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(mView);
        setupViewModel();
    }

    private void initViews(View mView) {
        mRecyclerView = mView.findViewById(R.id.recyclerViewFavMovie);
        adapter = new FavouriteMovieAdapter(requireContext(), new ArrayList<>(), this::createDetailMovie);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
    }

    private void createDetailMovie(ListFavouriteMovieResponse.FavouriteMovie favMovie) {
        ListFavouriteMovieResponse.FavouriteMovie.Movie movie = favMovie.getMovie();
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

    private void setupViewModel() {
        SelectedDataSource selectedDataSource = Utils.checkInternetState(requireContext());
        ExecutorService executorService =
                ((MyApplication) requireActivity().getApplication()).homeFragmentExecutorService;
        RemoteDataSource<ListFavouriteMovieResponse> remoteDataSource =
                new RemoteDataSource<>(ListFavouriteMovieResponse.class, executorService);
        LocalDataSource localDataSource = new LocalDataSource();
        Repository repository = new Repository.Builder()
                .setSelectedDataSource(selectedDataSource)
                .setLocalDataSource(localDataSource)
                .setRemoteDataSource(remoteDataSource)
                .build();
        Utils.selectedDataSource = selectedDataSource;
        mViewModel = new ViewModelProvider(this,
                new FavMovieViewModelFactory(repository)).get(FavMovieViewModel.class);
        mViewModel.getData().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                // Cập nhật dữ liệu cho adapter
                    adapter.updateData(movies);
            } else {
                Toast.makeText(requireContext(), "No movies found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}