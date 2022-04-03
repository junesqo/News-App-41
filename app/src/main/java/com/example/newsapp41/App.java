package com.example.newsapp41;

import android.app.Application;

import androidx.room.Room;

import com.example.newsapp41.room.AppDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class App extends Application {

    private static AppDatabase database;


    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, AppDatabase.class, "database").allowMainThreadQueries().build();


    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
