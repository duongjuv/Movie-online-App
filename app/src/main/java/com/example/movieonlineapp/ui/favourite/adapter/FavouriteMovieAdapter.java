package com.example.movieonlineapp.ui.favourite.adapter;

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
import com.example.movieonlineapp.data.response.ListFavouriteMovieResponse;
import com.example.movieonlineapp.domain.model.Movie;
import com.example.movieonlineapp.ui.home.adapter.MovieAdapter;



import java.util.ArrayList;
import java.util.List;

public class FavouriteMovieAdapter extends RecyclerView.Adapter<FavouriteMovieAdapter.ViewHolder>{
    private List<ListFavouriteMovieResponse.FavouriteMovie> mMovies;
    private final Context context;
    private final FavouriteMovieAdapter.OnItemClickListener listener;

    public FavouriteMovieAdapter(Context context,
                                 List<ListFavouriteMovieResponse.FavouriteMovie> mMovies,
                                 FavouriteMovieAdapter.OnItemClickListener listener) {
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
    public FavouriteMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_favourite_item, parent, false);
        return new FavouriteMovieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListFavouriteMovieResponse.FavouriteMovie movie = mMovies.get(position);
        holder.bindData(movie);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(movie));
    }


    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<ListFavouriteMovieResponse.FavouriteMovie> movies) {
        mMovies.clear();
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView avatarMovie;
        private final TextView txtTitleMovie;
        private final TextView txtYearMovie;
        private final TextView txtContentMovie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarMovie = itemView.findViewById(R.id.imageViewMovie);
            txtTitleMovie = itemView.findViewById(R.id.textViewTitle);
            txtYearMovie = itemView.findViewById(R.id.textViewYear);
            txtContentMovie = itemView.findViewById(R.id.textViewDescription);
        }

        @SuppressLint("SetTextI18n")
        public void bindData(ListFavouriteMovieResponse.FavouriteMovie movie) {
            if (movie.getMovie().getYear() != 0) {
                txtYearMovie.setText(String.valueOf(movie.getMovie().getYear()));
            } else {
                txtYearMovie.setText("N/A");
            }
            if (movie.getMovie().getTitle() != null) {
                txtTitleMovie.setText(movie.getMovie().getTitle());
            } else {
                txtTitleMovie.setText("Unknown Title");
            }
            if (movie.getMovie().getUrl_image() != null) {
                Glide.with(itemView.getContext())
                        .load(movie.getMovie().getUrl_image())
                        .into(avatarMovie);
            }
            if(movie.getMovie().getContent() != null){
                txtContentMovie.setText(movie.getMovie().getContent());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ListFavouriteMovieResponse.FavouriteMovie movie);
    }
}
