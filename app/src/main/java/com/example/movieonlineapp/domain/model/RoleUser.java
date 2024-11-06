package com.example.movieonlineapp.domain.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class RoleUser {
    @SerializedName("id")
    private int id;

    @SerializedName("role_id")
    private int roleId;

    @SerializedName("user_id")
    private int userId;

    public RoleUser(int id, int roleId, int userId) {
        this.id = id;
        this.roleId = roleId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @NonNull
    @Override
    public String toString() {
        return "RoleUser{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", userId=" + userId +
                '}';
    }
}
