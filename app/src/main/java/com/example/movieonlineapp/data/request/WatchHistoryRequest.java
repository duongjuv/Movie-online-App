package com.example.movieonlineapp.data.request;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class WatchHistoryRequest {
    private int userId;

    @SuppressLint("SimpleDateFormat")
    public WatchHistoryRequest(int userId, int movieId) {
        this.userId = userId;
        Date now = new Date();
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String watchedAt = isoFormat.format(now);
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


}
