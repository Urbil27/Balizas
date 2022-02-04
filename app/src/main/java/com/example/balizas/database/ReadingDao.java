package com.example.balizas.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReadingDao {
    @Query("SELECT * FROM reading")
    LiveData<List<Reading>> getAll();
    @Query("SELECT * FROM reading WHERE baliza_id = :balizaId" )
    List<Reading> getReadingFromBaliza(String balizaId);

    @Delete
    void delete(Reading reading);
    @Query("DELETE FROM reading WHERE baliza_id = :balizaId" )
    void deleteReadingFromBaliza(String balizaId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Reading reading);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Reading... readings);
}