package com.example.movieonlineapp;


import android.content.Context;


public class DataLocalManager {
    public static final String KEY_EMAIL = "KEY_EMAIL";
    public static final String ID_MOVIE = "ID_MOVIE";
    public static final String ID_USER = "ID_USER";
    public static final String KEY_DEPOSIT = "KEY_DEPOSIT";
    public static final String KEY_CONTENT = "KEY_CONTENT";
    public static final String KEY_RATING = "KEY_RATING";
    public static final String KEY_COMMENT = "KEY_COMMENT";

    private static DataLocalManager instance;
    public MySharedPreferences mySharedPreferences;

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataLocalManager getInstance() {
        if (instance == null) {
            synchronized (DataLocalManager.class) {
                if (instance == null) {
                    instance = new DataLocalManager();
                }
            }
        }
        return instance;
    }
}
