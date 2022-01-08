package com.example.balizas.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.balizas.database.Baliza;
import java.util.List;
@Dao
public interface BalizaDao {

    @Query("SELECT * FROM baliza")
    LiveData<List<Baliza>> getAll();
    @Query("SELECT * FROM baliza WHERE id IN (:userId)")
    List<Baliza> loadAllByIds(String userId);

    @Query("SELECT * FROM baliza WHERE id LIKE :first AND " +
            "name LIKE :last LIMIT 1")
    Baliza findByName(String first, String last);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Baliza... balizas);
    @Update
    void update(Baliza baliza);
    @Delete
    void delete(Baliza baliza);
}