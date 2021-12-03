package com.example.balizas.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.balizas.database.Baliza;
import java.util.List;
@Dao
public interface BalizaDao {

    @Query("SELECT * FROM baliza")
    LiveData<List<Baliza>> getAll();
    @Query("SELECT * FROM baliza WHERE id IN (:userIds)")
    List<Baliza> loadAllByIds(int[] userIds);
    @Query("SELECT * FROM baliza WHERE id LIKE :first AND " +
            "name LIKE :last LIMIT 1")
    Baliza findByName(String first, String last);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Baliza... balizas);
    @Delete
    void delete(Baliza baliza);
}