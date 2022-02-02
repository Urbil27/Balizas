package com.example.balizas.communication.euskalmet;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.balizas.MainActivity;
import com.example.balizas.database.Baliza;
import com.example.balizas.database.Reading;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
                            System.out.println(baliza.balizaName);
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
        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        String dayString = day.toString(dateFormatter);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

        try {
            JSONObject jsonObject = new JSONObject(data);
            Iterator<String> iterator = jsonObject.keys();
            Reading reading = new Reading();
            while (iterator.hasNext()) {
                String key = iterator.next();
                System.out.println("The key is "+key);
                JSONObject dataType = jsonObject.getJSONObject(key);
                JSONObject readings = dataType.getJSONObject("data");
                String readingsKey = readings.keys().next();
                JSONObject readingValues = readings.getJSONObject(readingsKey);
                Iterator<String> timeIterator = readingValues.keys();

                while (timeIterator.hasNext()) {
                    String timeString = timeIterator.next();
                    String dateAndTime = day.toString(dateFormatter) + " " + timeString;
                    DateTime defDateTime = DateTime.parse(dateAndTime, dateTimeFormatter);
                    System.out.println(defDateTime.toString());
                    double value = readingValues.getDouble(timeString);
                    reading.balizaId = dataType.getString("station");
                    System.out.println(dataType.getString("name"));
                    switch(key){
                        case "21":
                            reading.temperature=value;
                            Log.e("temperature",value+"");
                            break;
                        case "31":
                            reading.humidity=value;
                            Log.e("humidity",value+"");
                            break;
                        case "40":
                            reading.precipitation=value;
                            Log.e("precipitation",value+"");
                            break;
                        case "70":
                            reading.irradiance=value;
                            Log.e("irradiance",value+"");
                            break;
                    }

                    reading.datetime = defDateTime.toString(dateTimeFormatter);

                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.db.readingDao().insert(reading);
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
