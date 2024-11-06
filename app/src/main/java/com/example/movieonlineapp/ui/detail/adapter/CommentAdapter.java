package com.example.movieonlineapp.ui.detail.adapter;

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
import com.example.movieonlineapp.data.response.MovieApiResponse;


import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private final List<MovieApiResponse.Comment> mComments;
    private Context mContext;

    public CommentAdapter(Context context, List<MovieApiResponse.Comment> comments) {
        this.mContext = context;
        if (comments == null) {
            this.mComments = new ArrayList<>();
        } else {
            this.mComments = comments;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieApiResponse.Comment comment = mComments.get(position);
        Log.d("MovieComment Bind", "Binding comment: " + comment.getContent() + " - Rating: " + comment.getRating());
        holder.bindData(comment);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateCommentData(List<MovieApiResponse.Comment> commentList){

        mComments.clear();
        mComments.addAll(commentList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgAvatar;
        private final TextView txtEmailUser;
        private final TextView rating;
        private final TextView txtComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatarComment);
            txtEmailUser = itemView.findViewById(R.id.txtEmailComment);
            rating = itemView.findViewById(R.id.txtRatingComment);
            txtComment = itemView.findViewById(R.id.txtComment);
        }

        @SuppressLint("SetTextI18n")
        public void bindData(MovieApiResponse.Comment comment) {
            Glide.with(itemView.getContext())
                    .load(R.drawable.img_default)
                    .into(imgAvatar);
            txtEmailUser.setText(comment.getUser().getEmail());
            rating.setText(comment.getRating() + "");
            txtComment.setText(comment.getContent());

        }
    }
}
