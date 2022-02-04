package com.example.balizas.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Baliza.class, Reading.class}, version = 25)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BalizaDao balizaDao();
    public abstract ReadingDao readingDao();
}