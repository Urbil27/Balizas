package com.example.balizas.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.joda.time.DateTime;


@Entity (foreignKeys = @ForeignKey(entity = Baliza.class, parentColumns = "id", childColumns = "baliza_id", onDelete = ForeignKey.CASCADE))
    public class Reading {
        @PrimaryKey(autoGenerate = true)
        public int id;

        @ColumnInfo(name = "name")
        public String name;

        @ColumnInfo(name = "baliza_id")
        public String balizaId;

        @ColumnInfo(name = "data_type")
        public String dataType;

        @ColumnInfo(name = "datetime")
        public String datetime;

        @ColumnInfo(name = "reading")
        public double reading;
    }

