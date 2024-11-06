package com.example.movieonlineapp.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieonlineapp.domain.model.Actor;

import java.util.List;

@Dao
public interface ActorDao {
    @Insert
    void insert(Actor actor);

    @Update
    void update(Actor actor);

    @Delete
    void delete(Actor actor);

    @Query("SELECT * FROM actor")
    List<Actor> getAllActors();
}
