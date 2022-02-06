package com.example.balizas.communication.euskalmet;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.RequiresApi;
import com.example.balizas.MainActivity;
import com.example.balizas.database.Baliza;
import com.example.balizas.database.Reading;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


import org.joda.time.*;

public class Parser {
    HandlerThread htDB = new HandlerThread("htDB");
    Handler handler;

    public Parser() {
        htDB.start();
        handler = new Handler(htDB.getLooper());
    }

    public void parseBalizas(JSONArray balizasJsonArray) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < balizasJsonArray.length(); i++) {
                    try {
                        JSONObject object = (JSONObject) balizasJsonArray.get(i);
                        Baliza baliza = new Baliza();
                        baliza.id = object.getString("id");
                        baliza.balizaName = object.getString("name");
                        baliza.stationType = object.getString("stationType");
                        baliza.nameEus = object.getString("nameEus");
                        baliza.municipality = object.getString("municipality");
                        baliza.altitude = Double.parseDouble(object.getString("altitude"));
                        baliza.x = Double.parseDouble(object.getString("x"));
                        baliza.y = Double.parseDouble(object.getString("y"));
                        baliza.altitude = Double.parseDouble(object.getString("altitude"));
                        if (baliza.stationType.equals("METEOROLOGICAL")) {
                            MainActivity.db.balizaDao().insertAll(baliza);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void parseDatos(String data, DateTime day) {
        System.out.println("Data is " + data);
        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        Reading reading = new Reading();
        try {
            JSONObject jsonObject = new JSONObject(data);
            Iterator<String> iterator = jsonObject.keys();

            while (iterator.hasNext()) {

                String key = iterator.next();
                JSONObject dataType = jsonObject.getJSONObject(key);
                JSONObject readings = dataType.getJSONObject("data");
                String readingsKey = readings.keys().next();
                JSONObject readingValues = readings.getJSONObject(readingsKey);
                Iterator<String> timeIterator = readingValues.keys();
                ArrayList<String> timeKeys = new ArrayList<String>();

                while (timeIterator.hasNext()) {
                    timeKeys.add(timeIterator.next());
                }

                Collections.sort(timeKeys);
                String lastTimeKey = timeKeys.get(timeKeys.size() - 1);
                String dateAndTime = day.toString(dateFormatter) + " " + lastTimeKey;
                DateTime defDateTime = DateTime.parse(dateAndTime, dateTimeFormatter);
                double value = readingValues.getDouble(lastTimeKey);
                reading.balizaId = dataType.getString("station");

                switch (key) {
                    case "21":
                        reading.temperature = value;
                        break;
                    case "31":
                        reading.humidity = value;
                        break;
                    case "40":
                        reading.precipitation = value;
                        break;
                    case "70":
                        reading.irradiance = value;
                        break;
                }

                reading.datetime = defDateTime.toString(dateTimeFormatter);

            }

            handler.post(new Runnable() {
                @Override
                public void run() {

                    MainActivity.db.readingDao().insertAll(reading);

                }
            });


        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

    }

}
