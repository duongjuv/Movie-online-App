package com.example.movieonlineapp.ui.home.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int spacing;

    public HorizontalSpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // Thiết lập khoảng cách bên trái và bên phải cho mỗi item
        outRect.left = spacing;
        outRect.right = spacing;
    }
}

