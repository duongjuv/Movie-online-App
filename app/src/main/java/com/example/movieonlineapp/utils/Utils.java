package com.example.movieonlineapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;

import com.example.movieonlineapp.data.source.SelectedDataSource;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public abstract class Utils {
    public static SelectedDataSource selectedDataSource = SelectedDataSource.LOCAL;

    public static SelectedDataSource checkInternetState(Context requireContext) {
        ConnectivityManager connMgr =
                (ConnectivityManager) requireContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr.getActiveNetwork() == null) {
            return SelectedDataSource.LOCAL;
        } else {
            return SelectedDataSource.REMOTE;
        }
    }

    // Chuyển từ String sang Timestamp
    public static Timestamp StringToTimeStamp(String dateString) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Timestamp timestamp = null;
        try {
            // Parse chuỗi thành đối tượng Date
            Date parsedDate = dateFormat.parse(dateString);
            // Chuyển từ Date sang Timestamp
            assert parsedDate != null;
            timestamp = new Timestamp(parsedDate.getTime());
            System.out.println("Timestamp: " + timestamp);
        } catch (ParseException e) {
            e.printStackTrace(); // Xử lý ngoại lệ khi parse lỗi
        }
        return timestamp;
    }

    // Chuyển từ Timestamp sang String
    public static String TimeStampToString(Timestamp timestamp) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        // Sử dụng SimpleDateFormat để chuyển từ Timestamp thành chuỗi
        return dateFormat.format(timestamp);
    }

    public static String convertIsoToDate(String isoDate) {
        // Định dạng để parse chuỗi ISO 8601 thành Date
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            // Chuyển từ chuỗi String thành Date
            Date date = isoFormat.parse(isoDate);
            // Định dạng lại ngày để hiển thị cho người dùng (theo giờ địa phương)
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat userFriendlyFormat = new SimpleDateFormat("dd/MM/yyyy");
            return userFriendlyFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
