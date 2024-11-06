package com.example.movieonlineapp;

import android.app.Application;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {

    // ExecutorService cho HomeFragment
    public ExecutorService homeFragmentExecutorService = Executors.newFixedThreadPool(3);

    // ExecutorService cho DetailMovieActivity
    public ExecutorService detailMovieExecutorService = Executors.newFixedThreadPool(2);

    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(this);
    }
}
