package com.example.movieonlineapp.data.source;

import android.util.Log;

public interface DataSource<T> {
    void loadData(DataSourceCallback<T> callback);

    interface DataSourceCallback<T> {
        void onCompleted(ResponseResult<?> result);
    }
}
