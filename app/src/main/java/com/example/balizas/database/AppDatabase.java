package com.example.balizas.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Baliza.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BalizaDao balizaDao();
}