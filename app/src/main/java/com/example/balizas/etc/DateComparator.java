package com.example.balizas.etc;

import com.example.balizas.database.Reading;

import java.util.Comparator;

public class DateComparator implements Comparator<Reading> {
    @Override
    public int compare(Reading reading, Reading reading2) {
        return reading.datetime.compareTo(reading2.datetime);
    }
}
