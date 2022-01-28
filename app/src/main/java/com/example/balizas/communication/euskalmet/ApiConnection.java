package com.example.balizas.communication.euskalmet;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.balizas.database.Baliza;

import org.joda.time.DateTime;
import org.json.JSONArray;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class ApiConnection {
    Context context;
    RequestQueue queue;
    Handler handler;
    public ApiConnection(Context context, Handler handler) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
        this.handler = handler;

    }

    public void getBalizas() {
        String url = "https://www.euskalmet.euskadi.eus/vamet/stations/stationList/stationList.json";
        JSONArray balizas;
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Parser parser = new Parser();
                        parser.parseBalizas(jsonArray);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        queue.add(stringRequest);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getBalizaReading(Baliza baliza) {

        LocalDateTime myDateObj;
        myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedDate = myDateObj.format(myFormatObj);
        String myUrl = "https://euskalmet.euskadi.eus/vamet/stations/readings/" + baliza.id + "/" + formattedDate + "/readingsData.json";
        StringRequest myRequest = new StringRequest(Request.Method.GET, myUrl,
                response -> {

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Parser parser = new Parser();
                            DateTime today = new DateTime();
                            parser.parseDatos(response, today);
                        }
                    });

                },
                volleyError -> Toast.makeText(context, volleyError.getMessage(), Toast.LENGTH_SHORT).show()
        );
        queue.add(myRequest);
    }
}

