package com.example.balizas.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Baliza.class, Reading.class}, version = 11)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BalizaDao balizaDao();
    public abstract ReadingDao readingDao();
}