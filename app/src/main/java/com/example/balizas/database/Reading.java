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

        @ColumnInfo(name = "mean_speed")
        public double mean_speed;

        @ColumnInfo(name = "mean_direction")
        public double mean_direction;

        @ColumnInfo(name = "max_speed")
        public double max_speed;

        @ColumnInfo(name = "speed_sigma")
        public double speed_sigma;

        @ColumnInfo(name = "direction_sigma")
        public double direction_sigma;

        @ColumnInfo(name = "temperature")
        public double temperature;

        @ColumnInfo(name = "humidity")
        public double humidity;

        @ColumnInfo(name = "precipitation")
        public double precipitation;

        @ColumnInfo(name = "irradiance")
        public double irradiance;
    }

