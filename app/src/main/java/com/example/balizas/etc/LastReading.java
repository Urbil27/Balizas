package com.example.balizas.etc;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.balizas.MainActivity;
import com.example.balizas.R;
import com.example.balizas.communication.euskalmet.ApiConnection;
import com.example.balizas.database.Baliza;
import com.example.balizas.database.Reading;
import com.example.balizas.fragmentoDatos.ReadingsViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;

public class LastReading {
    public static Reading get(Baliza baliza, List<Reading> readings){
        List<Reading> readingsFromBaliza = new ArrayList<Reading>();
        for(Reading reading : readings){
            if(reading.balizaId.equals(baliza.id)){
                readingsFromBaliza.add(reading);
            }
        }
        Collections.sort(readingsFromBaliza, new Comparator<Reading>() {
            @Override
            public int compare(Reading r1, Reading r2) {
                return new String(r1.datetime).compareTo(new String(r2.datetime));
            }
        });
        System.out.println(baliza.id);
        System.out.println(readingsFromBaliza.size());
        Reading reading = new Reading();
        try{
            reading = readingsFromBaliza.get(readingsFromBaliza.size()-1);

        } catch(Exception e){

        }
        return reading;
    }
}
