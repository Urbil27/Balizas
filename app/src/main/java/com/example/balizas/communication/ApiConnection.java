package com.example.balizas.communication;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.balizas.communication.Parser;
import com.example.balizas.database.Baliza;
import com.example.balizas.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.type.DateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;

public class ApiConnection {
    Context context;
    RequestQueue queue;
    Handler handler;
    public ApiConnection(Context context, Handler handler) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
        this.handler = handler;

    }

    public void getData() {

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
                    //Create a JSON object containing information from the API.
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Parser parser = new Parser();
                            Calendar today = Calendar.getInstance();
                            int day = today.get(Calendar.DAY_OF_MONTH);
                            int month = today.get(Calendar.MONTH);
                            int year = today.get(Calendar.YEAR);

                            parser.parseDatos(response, today);
                        }
                    });

                },
                volleyError -> Toast.makeText(context, volleyError.getMessage(), Toast.LENGTH_SHORT).show()
        );
        queue.add(myRequest);
    }
}

