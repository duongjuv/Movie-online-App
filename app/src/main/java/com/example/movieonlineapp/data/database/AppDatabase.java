package com.example.movieonlineapp.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;


import com.example.movieonlineapp.domain.model.Actor;
import com.example.movieonlineapp.domain.model.Category;
import com.example.movieonlineapp.domain.model.Movie;
import com.example.movieonlineapp.domain.model.User;

//@Database(entities = {Actor.class, Category.class, Movie.class, User.class}, version = 1)
public class AppDatabase  {
////    public abstract ActorDao actorDao();
////    public abstract CategoryDao categoryDao();
////    public abstract MovieDao movieDao();
////    public abstract UserDao userDao();
//    private static volatile AppDatabase INSTANCE;
//
//    public static AppDatabase getInstance(Context context) {
//        if (INSTANCE == null) {
//            synchronized (AppDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                                    AppDatabase.class, "app_database")
//                            .fallbackToDestructiveMigration()
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//
//    @Override
//    public void clearAllTables() {
//
//    }
//
//    @NonNull
//    @Override
//    protected SupportSQLiteOpenHelper createOpenHelper(@NonNull DatabaseConfiguration databaseConfiguration) {
//        return null;
//    }
//
//    @NonNull
//    @Override
//    protected InvalidationTracker createInvalidationTracker() {
//        return null;
//    }
}
