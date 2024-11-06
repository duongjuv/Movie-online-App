package com.example.movieonlineapp.ui.personal.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.movieonlineapp.R;
import com.example.movieonlineapp.data.response.WatchHistoryApiResponse;
import com.example.movieonlineapp.utils.Utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WatchHistoryMovieAdapter extends RecyclerView.Adapter<WatchHistoryMovieAdapter.ViewHolder> {
    private List<WatchHistoryApiResponse.WatchHistoryData> mDatas;
    private final Context context;
    private final WatchHistoryMovieAdapter.OnItemClickListener listener;

    public WatchHistoryMovieAdapter(Context context,
                                    List<WatchHistoryApiResponse.WatchHistoryData> data,
                                    WatchHistoryMovieAdapter.OnItemClickListener listener) {
        if(mDatas == null){
            mDatas = new ArrayList<>();
        }
        else{
            this.mDatas = data;
        }
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public WatchHistoryMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.watch_history_item, parent, false);
        return new WatchHistoryMovieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WatchHistoryApiResponse.WatchHistoryData data = mDatas.get(position);
        holder.bindData(data);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(data));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<WatchHistoryApiResponse.WatchHistoryData> movies) {
        mDatas.clear();
        sortList(movies);
        mDatas.addAll(movies);
        notifyDataSetChanged();
    }

    private void sortList(List<WatchHistoryApiResponse.WatchHistoryData> movies) {
        movies.sort((m1, m2) -> m2.getWatchedAt().compareTo(m1.getWatchedAt()));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgMovieAvatar;
        private final TextView movieName;
        public final View itemView;
        private final TextView txtTimeMovie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            imgMovieAvatar = itemView.findViewById(R.id.movieImageHistory);
            movieName = itemView.findViewById(R.id.movieTitleHistory);
            txtTimeMovie = itemView.findViewById(R.id.movieTimeHistory);

        }

        @SuppressLint("SetTextI18n")
        public void bindData(WatchHistoryApiResponse.WatchHistoryData data) {
            if (data.getMovie().getTitle()!= null) {
                movieName.setText(data.getMovie().getTitle());
            } else {
                movieName.setText("Unknown Title");
            }
            if (data.getMovie().getUrlImage() != null) {
                Glide.with(itemView.getContext())
                        .load(data.getMovie().getUrlImage())
                        .into(imgMovieAvatar);
            }
            if(data.getWatchedAt() != null ){
                txtTimeMovie.setText(Utils.convertIsoToDate(data.getWatchedAt()));
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(WatchHistoryApiResponse.WatchHistoryData data);
    }
}
