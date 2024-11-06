package com.example.movieonlineapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.movieonlineapp.DataLocalManager;
import com.example.movieonlineapp.MyApplication;
import com.example.movieonlineapp.R;
import com.example.movieonlineapp.data.repository.Repository;
import com.example.movieonlineapp.data.response.MovieApiResponse;
import com.example.movieonlineapp.data.response.WatchHistoryApiResponse;
import com.example.movieonlineapp.data.source.SelectedDataSource;
import com.example.movieonlineapp.data.source.local.LocalDataSource;
import com.example.movieonlineapp.data.source.remote.RemoteDataSource;
import com.example.movieonlineapp.ui.detail.activity.DetailMovieActivity;
import com.example.movieonlineapp.ui.detail.viewmodel.MovieItemViewModel;
import com.example.movieonlineapp.ui.home.decoration.ItemOffsetDecoration;
import com.example.movieonlineapp.ui.personal.adapter.WatchHistoryMovieAdapter;
import com.example.movieonlineapp.ui.personal.user.UserItemViewModel;
import com.example.movieonlineapp.ui.personal.viewmodel.WatchHistoryViewModel;
import com.example.movieonlineapp.ui.personal.viewmodel.WatchHistoryViewModelFactory;
import com.example.movieonlineapp.utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class UserFragment extends Fragment {
    private View mView;
    private TextView txtFullName;
    private RecyclerView recyclerViewWatchHistory;
    private WatchHistoryViewModel watchHistoryViewModel;
    private UserItemViewModel model;
    private WatchHistoryMovieAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(mView);
        model = UserItemViewModel.getInstance();
        model.getUserData().observe(getViewLifecycleOwner(), v -> {
            txtFullName.setText(v.getFullName());
        });

        setupViewModels();
    }

    private void setupViewModels() {
        SelectedDataSource selectedDataSource = Utils.checkInternetState(requireContext());
        ExecutorService executorService =
                ((MyApplication) requireActivity().getApplication()).homeFragmentExecutorService;
        RemoteDataSource<WatchHistoryApiResponse> remoteDataSource =
                new RemoteDataSource<>(WatchHistoryApiResponse.class, executorService);
        LocalDataSource localDataSource = new LocalDataSource();
        Repository repository = new Repository.Builder()
                .setSelectedDataSource(selectedDataSource)
                .setLocalDataSource(localDataSource)
                .setRemoteDataSource(remoteDataSource)
                .build();
        Utils.selectedDataSource = selectedDataSource;
        watchHistoryViewModel = new ViewModelProvider(this,
                new WatchHistoryViewModelFactory(repository)).get(WatchHistoryViewModel.class);
        watchHistoryViewModel.getData().observe(getViewLifecycleOwner(), movies->{
            adapter.updateData(movies);
        });
    }

    private void setupViews(View view) {
        txtFullName = view.findViewById(R.id.txtFullName);
        recyclerViewWatchHistory = view.findViewById(R.id.recyclerWatchHistory);
        adapter = new WatchHistoryMovieAdapter(requireContext(),
                new ArrayList<>(), this::createDetailMovie);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL, false);
        int spaceInDp = 20;  // 20dp
        int spaceInPx = (int) (spaceInDp * getResources().getDisplayMetrics().density);
        recyclerViewWatchHistory.addItemDecoration(new ItemOffsetDecoration(spaceInPx));

        recyclerViewWatchHistory.setLayoutManager(layoutManager);
        recyclerViewWatchHistory.setAdapter(adapter);
        recyclerViewWatchHistory.setHasFixedSize(true);
    }

    private void createDetailMovie(WatchHistoryApiResponse.WatchHistoryData movie) {
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
}