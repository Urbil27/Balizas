package com.example.balizas.database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.joda.time.DateTime;


@Entity (primaryKeys = {"baliza_id","datetime"},foreignKeys = @ForeignKey(entity = Baliza.class, parentColumns = "id",
        childColumns = "baliza_id",  onDelete = ForeignKey.CASCADE))
    public class Reading {


        @NonNull
        @ColumnInfo(name = "baliza_id")
        public String balizaId;

        @NonNull
        @ColumnInfo(name = "datetime")
        public String datetime;

        @ColumnInfo(name = "temperature")
        public double temperature;

        @ColumnInfo(name = "humidity")
        public double humidity;

        @ColumnInfo(name = "precipitation")
        public double precipitation;

        @ColumnInfo(name = "irradiance")
        public double irradiance;
    }

