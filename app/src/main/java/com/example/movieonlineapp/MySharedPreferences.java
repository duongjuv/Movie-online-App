package com.example.movieonlineapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    public static final String MY_SHARED_REFERENCES = "MY_SHARED_REFERENCES";
    private final Context mContext;

    public MySharedPreferences(Context context) {
        this.mContext = context;
    }

    public void putValue(String key, String value){
        SharedPreferences sharedPreferences =
                mContext.getSharedPreferences(MY_SHARED_REFERENCES, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getValue(String key){
        SharedPreferences sharedPreferences =
                mContext.getSharedPreferences(MY_SHARED_REFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "No data");
    }
}
