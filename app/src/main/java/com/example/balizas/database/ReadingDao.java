package com.example.balizas.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReadingDao {
    @Query("SELECT * FROM reading")
    List<Reading> getAll();

    @Query("SELECT * FROM reading WHERE baliza_id = :balizaId")
    List<Reading> getFromBalizaId(String balizaId);

    @Query("SELECT * FROM reading WHERE id IN (:readingIds)")
    List<Reading> loadAllByIds(int[] readingIds);

    @Delete
    void delete(Reading reading);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Reading... readings);
}