package com.example.movieonlineapp.ui.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieonlineapp.R;
import com.example.movieonlineapp.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie> mMovies;
    private final Context context;
    private final OnItemClickListener listener;

    public MovieAdapter(Context context, List<Movie> mMovies, OnItemClickListener listener) {
        if(mMovies == null){
            mMovies = new ArrayList<>();
        }
        else{
            this.mMovies = mMovies;
        }
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        holder.bindData(movie);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(movie));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Movie> movies) {
        mMovies.clear();
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgMovieAvatar;
        private final TextView movieName;
        private final TextView movieYear;
        public final View itemView;
        private final TextView txtTypeMovie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            imgMovieAvatar = itemView.findViewById(R.id.movieImage);
            movieName = itemView.findViewById(R.id.movieTitle);
            movieYear = itemView.findViewById(R.id.movieYear);
            txtTypeMovie = itemView.findViewById(R.id.movieTypeBadge);
        }

        @SuppressLint("SetTextI18n")
        public void bindData(Movie movie) {
            if (movie.getYear() != 0) {
                movieYear.setText(String.valueOf(movie.getYear()));
            } else {
                movieYear.setText("N/A");
            }
            if (movie.getTitle() != null) {
                movieName.setText(movie.getTitle());
            } else {
                movieName.setText("Unknown Title");
            }
            if (movie.getUrlImage() != null) {
                Glide.with(itemView.getContext())
                        .load(movie.getUrlImage())
                        .into(imgMovieAvatar);
            }
            if(movie.getTypeAccount().equals("vip")){
                txtTypeMovie.setText("VIP");
            }
            else {
                txtTypeMovie.setText("");
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }
}
