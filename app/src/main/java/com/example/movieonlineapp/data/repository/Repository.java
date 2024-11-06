package com.example.movieonlineapp.data.repository;

import com.example.movieonlineapp.data.source.DataSource;
import com.example.movieonlineapp.data.source.SelectedDataSource;
import com.example.movieonlineapp.data.source.local.LocalDataSource;
import com.example.movieonlineapp.data.source.remote.RemoteDataSource;

public class Repository {
    private final SelectedDataSource mSelectedDataSource;
    private final LocalDataSource mLocalDataSource;
    private final RemoteDataSource mRemoteDataSource;

    public Repository(Builder builder) {
        mSelectedDataSource = builder.mSelectedDataSource;
        mLocalDataSource = builder.mLocalDataSource;
        mRemoteDataSource = builder.mRemoteDataSource;
    }

    @SuppressWarnings("unchecked")
    public void loadData(DataSource.DataSourceCallback callback){
        if(mSelectedDataSource == SelectedDataSource.LOCAL){
            mLocalDataSource.loadData(callback);
        }
        else{
            mRemoteDataSource.loadData(callback);
        }
    }
    public static class Builder {
        private SelectedDataSource mSelectedDataSource;
        private LocalDataSource mLocalDataSource;
        private RemoteDataSource mRemoteDataSource;

        public Builder() {

        }

        public Builder setSelectedDataSource(SelectedDataSource selectedDataSource) {
            mSelectedDataSource = selectedDataSource;
            return this;
        }

        public Builder setLocalDataSource(LocalDataSource localDataSource) {
            mLocalDataSource = localDataSource;
            return this;
        }

        public Builder setRemoteDataSource(RemoteDataSource remoteDataSource) {
            mRemoteDataSource = remoteDataSource;
            return this;
        }

        public Repository build() {
            return new Repository(this);
        }
    }
}
