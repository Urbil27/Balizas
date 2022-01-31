package com.example.balizas.etc;

import com.example.balizas.database.Baliza;
import com.example.balizas.database.Reading;

public class BalizaAndLastReading {
    public Baliza baliza;
    public Reading lastReading;
    public BalizaAndLastReading(Baliza baliza, Reading lastReading){
        this.baliza = baliza;
        this.lastReading = lastReading;
    }
}
