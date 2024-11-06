package com.example.movieonlineapp.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieonlineapp.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private String[] categories;

    public CategoryAdapter(String[] categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho mỗi item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        // Gán giá trị cho từng item
        holder.categoryName.setText(categories[position]);
    }

    @Override
    public int getItemCount() {
        return categories.length; // Số lượng item
    }

    // ViewHolder chứa item category
    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.textCategoryItem); // Lấy id từ item_category.xml
        }
    }
}
