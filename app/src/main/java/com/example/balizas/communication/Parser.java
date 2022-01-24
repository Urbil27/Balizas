package com.example.balizas.communication;

import com.example.balizas.MainActivity;
import com.example.balizas.database.Reading;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class Parser {
    public Parser(){

    }
    public void parseDatos(String data){
        try {
            JSONObject jsonObject = new JSONObject(data);
            Iterator<String> iterator = jsonObject.keys();
            while(iterator.hasNext()){
                String key = iterator.next();
                JSONObject dataType = jsonObject.getJSONObject(key);
                JSONObject readings = dataType.getJSONObject("data");
                String readingsKey = readings.keys().next();
                JSONObject readingValues = readings.getJSONObject(readingsKey);
                Iterator<String> timeIterator = readingValues.keys();
                while (timeIterator.hasNext()){
                    String time = timeIterator.next();
                    double value = readingValues.getDouble(time);
                    Reading reading = new Reading();
                    reading.balizaId = dataType.getString("station");
                    reading.name = dataType.getString("name");
                    reading.dataType = dataType.getString("type");
                    reading.time = time;
                    reading.reading = value;

                    MainActivity.db.readingDao().insertAll(reading);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void parseJsonArray(String data){

    }
}
