package com.example.movieonlineapp.ui.home.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
    private final int offset;

    public ItemOffsetDecoration(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // Áp dụng khoảng cách cho các item
        outRect.right = offset;  // Đặt khoảng cách bên phải của mỗi item
    }
}

