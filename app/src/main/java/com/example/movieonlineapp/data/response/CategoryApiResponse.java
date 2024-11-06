package com.example.movieonlineapp.data.response;

import androidx.annotation.NonNull;

import com.example.movieonlineapp.domain.model.Category;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryApiResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Category> categories;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public String toString() {
        return "CategoryApiResponse{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", categories=" + categories +
                '}';
    }
}
