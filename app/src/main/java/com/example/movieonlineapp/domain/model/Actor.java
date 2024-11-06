package com.example.movieonlineapp.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
@Entity(tableName = "actor")
public class Actor {
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;
    @SerializedName("birthdate")
    private Date date;
    public Actor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Actor(String id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
