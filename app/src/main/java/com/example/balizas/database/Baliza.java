package com.example.balizas.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Baliza {
    @PrimaryKey @NonNull
    public String id;
    @ColumnInfo(name = "name")
    public String balizaName;
    @ColumnInfo(name = "balizaNameEus")
    public String nameEus;
    @ColumnInfo(name = "municipality")
    public String municipality;
    @ColumnInfo(name = "altitude")
    public double altitude;
    @ColumnInfo(name = "x")
    public double x;
    @ColumnInfo(name = "y")
    public double y;
    @ColumnInfo(name = "stationType")
    public double stationType;
    @ColumnInfo(name = "activated")
    public boolean activated;

}