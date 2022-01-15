package com.example.balizas;

import android.content.Context;
import android.os.Build;
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
import com.example.balizas.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class ApiConnection {
 Context context;

public ApiConnection(Context context){
 this.context = context;
 System.out.println("Soy Api Connection");
}
 @RequiresApi(api = Build.VERSION_CODES.O)
 public void getData(){
  LocalDateTime myDateObj;
   myDateObj = LocalDateTime.now();
  DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy/MM/dd");
  String formattedDate = myDateObj.format(myFormatObj);
  String myUrl = "https://euskalmet.euskadi.eus/vamet/stations/readings/C054/"+formattedDate +"/readingsData.json";
  StringRequest myRequest = new StringRequest(Request.Method.GET, myUrl,
          response -> {
           try{
            //Create a JSON object containing information from the API.
            JSONObject myJsonObject = new JSONObject(response);

           } catch (JSONException e) {
            e.printStackTrace();
           }
          },
          volleyError -> Toast.makeText(context, volleyError.getMessage(), Toast.LENGTH_SHORT).show()
  );
 }
}

