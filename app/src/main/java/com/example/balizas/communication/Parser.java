package com.example.balizas.communication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.balizas.MainActivity;
import com.example.balizas.database.Reading;
import com.google.type.DateTime;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Parser {
    public Parser(){

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void parseDatos(String data, Calendar today){
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
                    String timeString = timeIterator.next();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY hh:mm");
                    String date = today.get(Calendar.DAY_OF_MONTH)+"-"+today.get(Calendar.MONTH)+"-"+today.get(Calendar.YEAR);
                    calendar.setTime(dateFormat.parse(date+" "+timeString));
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
                    calendar.setTime(timeFormat.parse(timeString));
                    System.out.println(calendar.get(Calendar.YEAR)+"/"+Calendar.MONTH+"/"+Calendar.YEAR+" "+Calendar.HOUR+":"+Calendar.MINUTE);
                    double value = readingValues.getDouble(timeString);
                    Reading reading = new Reading();
                    reading.balizaId = dataType.getString("station");
                    reading.name = dataType.getString("name");
                    reading.dataType = dataType.getString("type");
                    reading.datetime = timeString;
                    reading.reading = value;

                    MainActivity.db.readingDao().insertAll(reading);
                }
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }
    public void parseJsonArray(String data){

    }
}
