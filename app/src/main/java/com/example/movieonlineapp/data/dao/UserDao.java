package com.example.movieonlineapp.data.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieonlineapp.domain.model.Category;
import com.example.movieonlineapp.domain.model.User;

import java.util.List;

public interface UserDao {
    @Insert
    void insert(User... user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM USER")
    List<User> getAllUser();
}
