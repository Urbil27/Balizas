package com.example.balizas.communication.euskalmet;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.joda.time.DateTime;
import org.json.JSONArray;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApiConnection {
    Context context;
    RequestQueue queue;
    HandlerThread handlerThread;
    Handler handler;
    public ApiConnection(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
        handlerThread = new HandlerThread("HandlerThreadApiConnection");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());

    }

    public void getBalizas() {
        String url = "https://www.euskalmet.euskadi.eus/vamet/stations/stationList/stationList.json";

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
    public void getBalizaReading(String balizaId) {

        LocalDateTime myDateObj;
        myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedDate = myDateObj.format(myFormatObj);
        String myUrl = "https://euskalmet.euskadi.eus/vamet/stations/readings/" + balizaId + "/" + formattedDate + "/readingsData.json";

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
                volleyError -> Log.e("Volley error:",volleyError.getMessage()+"")
        );

        queue.add(myRequest);
    }
}

