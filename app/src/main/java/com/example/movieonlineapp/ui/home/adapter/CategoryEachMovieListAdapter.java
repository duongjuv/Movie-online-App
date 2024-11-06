package com.example.movieonlineapp.ui.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.movieonlineapp.R;

import java.util.List;

public class CategoryEachMovieListAdapter extends RecyclerView.Adapter<CategoryEachMovieListAdapter.ViewHolder> {
    private List<String> items;
    private Context context;

    public CategoryEachMovieListAdapter(List<String> items) {
        this.items = items;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<String> listCategory){
        items.clear();
        items.addAll(listCategory);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_category, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCategory.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txt_CategoryViewHolder);
        }
    }
}
