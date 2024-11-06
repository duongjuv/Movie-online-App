package com.example.movieonlineapp.ui.search.adapter;

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

import com.example.movieonlineapp.domain.model.Category;
import com.example.movieonlineapp.domain.model.Movie;


import java.util.ArrayList;
import java.util.List;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.ViewHolder>{
    private final Context context;
    private final SearchMovieAdapter.OnItemClickListener listener;
    private final List<Movie> mMovies;

    public SearchMovieAdapter(Context context, List<Movie> movies, OnItemClickListener listener) {
        if(movies == null){
            mMovies = new ArrayList<>();
        }
        else{
            this.mMovies = movies;
        }
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_search_item, parent, false);
        return new SearchMovieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMovieAdapter.ViewHolder holder, int position) {
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
        private final ImageView avatarMovie;
        private final TextView txtTitleMovie;
        private final TextView txtYearMovie;
        private final TextView txtCategoriesMovie;
        private final TextView txtRatingAvg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarMovie = itemView.findViewById(R.id.imageViewMovieSearch);
            txtTitleMovie = itemView.findViewById(R.id.textViewTitleSearch);
            txtYearMovie = itemView.findViewById(R.id.textViewYearSearch);
            txtCategoriesMovie = itemView.findViewById(R.id.textViewCategoriesSearch);
            txtRatingAvg = itemView.findViewById(R.id.textViewRatingSearch);
        }

        @SuppressLint("SetTextI18n")
        public void bindData(Movie movie) {
            if (movie.getYear() != 0) {
                txtYearMovie.setText(String.valueOf(movie.getYear()));
            } else {
                txtYearMovie.setText("N/A");
            }
            if (movie.getTitle() != null) {
                txtTitleMovie.setText(movie.getTitle());
            } else {
                txtTitleMovie.setText("Unknown Title");
            }
            if (movie.getUrlImage() != null) {
                Glide.with(itemView.getContext())
                        .load(movie.getUrlImage())
                        .into(avatarMovie);
            }
            if (movie.getCategories() != null && !movie.getCategories().isEmpty()) {
                StringBuilder categories = new StringBuilder();
                for (Category category : movie.getCategories()) {
                    categories.append(category.getName()).append(", ");  // Giả sử Category có thuộc tính `getName()`
                }
                // Loại bỏ dấu phẩy cuối cùng
                String categoriesText = categories.toString();
                if (categoriesText.endsWith(", ")) {
                    categoriesText = categoriesText.substring(0, categoriesText.length() - 2);
                }
                txtCategoriesMovie.setText(categoriesText);
            } else {
                txtCategoriesMovie.setText("No categories available");
            }
            //txtRatingAvg.setText(String.valueOf(movie.getRating()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }
}

